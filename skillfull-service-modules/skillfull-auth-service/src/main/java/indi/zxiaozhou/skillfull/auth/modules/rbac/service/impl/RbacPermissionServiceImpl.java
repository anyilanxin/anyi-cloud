// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.rbac.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.zxiaozhou.skillfull.auth.core.constant.impl.PermissionType;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacPermissionPageVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacPermissionVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacPermissionEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.mapper.RbacPermissionMapper;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.IRbacPermissionService;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacPermissionDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacPermissionPageDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacPermissionTreeDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.mapstruct.PermissionVoMap;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.mapstruct.RbacPermissionDtoMap;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.mapstruct.RbacPermissionPageDtoMap;
import indi.zxiaozhou.skillfull.corecommon.base.model.common.ActionModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.stream.router.AuthActionModel;
import indi.zxiaozhou.skillfull.corecommon.constant.BindingStreamConstant;
import indi.zxiaozhou.skillfull.corecommon.constant.CoreCommonCacheConstant;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import indi.zxiaozhou.skillfull.corecommon.utils.CodeUtil;
import indi.zxiaozhou.skillfull.corecommon.utils.CoreCommonUtils;
import indi.zxiaozhou.skillfull.corecommon.utils.tree.TreeToolUtils;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import indi.zxiaozhou.skillfull.coremvc.component.BindingComponent;
import indi.zxiaozhou.skillfull.coremvc.config.properties.CoreWebMvcProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static indi.zxiaozhou.skillfull.corecommon.constant.CommonCoreProcessConstant.LOCK_EXPIRES;

/**
 * 权限表(Permission)业务层实现
 *
 * @author zxiaozhou
 * @date 2020-09-26 17:16:15
 * @since JDK11
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class, Error.class})
public class RbacPermissionServiceImpl extends ServiceImpl<RbacPermissionMapper, RbacPermissionEntity> implements IRbacPermissionService {
    private final RbacPermissionDtoMap dtoMap;
    private final RbacPermissionPageDtoMap pageDtoMap;
    private final PermissionVoMap voMap;
    private final CoreWebMvcProperty property;
    private final RbacPermissionMapper mapper;
    private final BindingComponent bindingComponent;
    private final RedisTemplate<String, Object> redisTemplate;


    @Override
    public void save(RbacPermissionVo vo) throws RuntimeException {
        RbacPermissionEntity entity = voMap.aToB(vo);
        // 数据校验
        checkData(entity);
        entity.setPermissionSysCode(generateCode(null, vo.getParentId(), false));
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存失败");
        }
        // 刷新系统缓存权限
        this.refreshAuth(true);
    }

    /**
     * 入库前数据校验
     *
     * @param entity ${@link RbacPermissionEntity} 待交易数据
     * @author zxiaozhou
     * @date 2020-10-06 23:45
     */
    private void checkData(RbacPermissionEntity entity) {
        // 如果是目录或菜单:基础组件(组件路径),路由地址
        if (entity.getPermissionType() == PermissionType.CATALOGUE.getType() || entity.getPermissionType() == PermissionType.MENU.getType()) {
            if (StringUtils.isBlank(entity.getComponent())) {
                throw new ResponseException(Status.VERIFICATION_FAILED, "基础组件或组件路径不能为空");
            }
            if (StringUtils.isBlank(entity.getPathName())) {
                throw new ResponseException(Status.VERIFICATION_FAILED, "路由名称不能为空");
            }
            // 菜单:上级
            if (entity.getPermissionType() == PermissionType.MENU.getType()) {
                if (StringUtils.isBlank(entity.getParentId())) {
                    throw new ResponseException(Status.VERIFICATION_FAILED, "上级不能为空");
                }
            }
            // 判断是否显示tag
            if (entity.isShowTag()) {
                if (StringUtils.isBlank(entity.getType()) || StringUtils.isBlank(entity.getContent()) || Objects.isNull(entity.getDot())) {
                    throw new ResponseException(Status.VERIFICATION_FAILED, "tag类型或者tag内容或者是否圆点不能为空");
                }
            }

        } else {
            // 如果是按钮:上级,授权策略,按钮权限类型不能为空，如果是后端型需要校验后端
            if (StringUtils.isBlank(entity.getParentId())) {
                throw new ResponseException(Status.VERIFICATION_FAILED, "上级不能为空");
            }
            if (StringUtils.isBlank(entity.getActions())) {
                throw new ResponseException(Status.VERIFICATION_FAILED, "按钮权限指令不能为空");
            }
            if (Objects.isNull(entity.getActionType())) {
                throw new ResponseException(Status.VERIFICATION_FAILED, "按钮权限类型不能为空");
            }
            if (entity.getActionType() != 1) {
                if (StringUtils.isBlank(entity.getActionUri())) {
                    throw new ResponseException(Status.VERIFICATION_FAILED, "当按钮需要校验后端时，后端uri不能为空");
                }
                if (StringUtils.isBlank(entity.getServiceId())) {
                    throw new ResponseException(Status.VERIFICATION_FAILED, "当按钮需要校验后端时，所属服务不能为空");
                }
            }
//            if (StringUtils.isBlank(entity.getCheckStrategy())) {
//                throw new ResponseException(Status.VERIFICATION_FAILED, "按钮检测策略不能为空");
//            }
        }
    }


    /**
     * 生成系统code
     *
     * @param oldParentId ${@link String} 历史上级id
     * @param newParentId ${@link String} 现在上级id
     * @param isUpdate    ${@link Boolean} false-新建,true-更新
     * @return String ${@link String}
     * @author zxiaozhou
     * @date 2021-03-08 12:00
     */
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    synchronized String generateCode(String oldParentId, String newParentId, boolean isUpdate) {
        oldParentId = StringUtils.isBlank(oldParentId) ? "" : oldParentId;
        newParentId = StringUtils.isBlank(newParentId) ? "" : newParentId;
        if (isUpdate && oldParentId.equals(newParentId)) {
            return null;
        }
        String code;
        if (StringUtils.isBlank(newParentId)) {
            LambdaQueryWrapper<RbacPermissionEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.and(v -> v.isNull(RbacPermissionEntity::getParentId).or().eq(RbacPermissionEntity::getParentId, ""))
                    .orderByDesc(RbacPermissionEntity::getPermissionSysCode)
                    .last("LIMIT 1");
            RbacPermissionEntity one = this.getOne(lambdaQueryWrapper);
            code = CodeUtil.getSubYouBianCode(null, Objects.isNull(one) ? null : one.getPermissionSysCode());
        } else {
            // 获取上级code
            RbacPermissionDto byId = this.getById(newParentId);
            // 获取本级最大code
            LambdaQueryWrapper<RbacPermissionEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.like(RbacPermissionEntity::getPermissionSysCode, byId.getPermissionSysCode() + "____")
                    .orderByDesc(RbacPermissionEntity::getPermissionSysCode)
                    .last("LIMIT 1");
            RbacPermissionEntity one = this.getOne(lambdaQueryWrapper);
            code = CodeUtil.getSubYouBianCode(byId.getPermissionSysCode(), Objects.isNull(one) ? null : one.getPermissionSysCode());
        }
        return code;
    }


    @Override
    public void updateById(String permissionId, RbacPermissionVo vo) throws RuntimeException {
        if (StringUtils.isBlank(vo.getParentId())) {
            vo.setParentId("");
        }
        // 查询数据是否存在
        RbacPermissionDto byId = this.getById(permissionId);
        RbacPermissionEntity entity = voMap.aToB(vo);
        entity.setPermissionId(permissionId);
        entity.setPermissionSysCode(generateCode(byId.getParentId(), vo.getParentId(), true));
        // 数据校验
        checkData(entity);
        // 更新数据
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新失败");
        }
        if (StringUtils.isNotBlank(entity.getPermissionSysCode()) && !byId.getPermissionSysCode().equals(entity.getPermissionSysCode())) {
            // 修改下级系统编码
            LambdaQueryWrapper<RbacPermissionEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.likeRight(RbacPermissionEntity::getPermissionSysCode, byId.getPermissionSysCode());
            List<RbacPermissionEntity> list = this.list(lambdaQueryWrapper);
            if (CollectionUtil.isNotEmpty(list)) {
                List<RbacPermissionEntity> waitUpdateList = new ArrayList<>();
                list.forEach(v -> {
                    RbacPermissionEntity newEntity = new RbacPermissionEntity();
                    newEntity.setPermissionId(v.getPermissionId());
                    newEntity.setPermissionSysCode(v.getPermissionSysCode().replaceFirst(byId.getPermissionSysCode(), entity.getPermissionSysCode()));
                    waitUpdateList.add(newEntity);
                });
                boolean b = this.updateBatchById(waitUpdateList);
                if (!b) {
                    throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新下级失败");
                }
            }
        }
        // 刷新系统缓存权限
        this.refreshAuth(true);
    }


    @Override
    public PageDto<RbacPermissionPageDto> pageByModel(RbacPermissionPageVo vo) throws RuntimeException {
        vo.getAscs().add("permissionSysCode");
        IPage<RbacPermissionPageDto> pageInfo = mapper.pageByModel(vo.getPage(), vo);
        /*
         * 1. 获取根节点
         * 2. 构建树形
         */
        // 获取根节点
        List<RbacPermissionPageDto> records = pageInfo.getRecords();
        if (CollectionUtil.isNotEmpty(records)) {
            List<RbacPermissionPageDto> rootRecords = new ArrayList<>(records.size());
            List<RbacPermissionPageDto> childRecords = new ArrayList<>(records.size());
            while (records.size() > 0) {
                RbacPermissionPageDto pageDto = records.remove(0);
                // 只要当前值是其他数据最小开头那么该值就是根节点，否则是子节点
                String permissionSysCode = pageDto.getPermissionSysCode();
                boolean isParent = true;
                for (RbacPermissionPageDto dto : records) {
                    if (permissionSysCode.startsWith(dto.getPermissionSysCode())) {
                        isParent = false;
                        break;
                    }
                }
                if (isParent) {
                    rootRecords.add(pageDto);
                    // 添加所有子类
                    List<RbacPermissionPageDto> collect = records.stream().filter(v -> v.getPermissionSysCode().startsWith(permissionSysCode)).collect(Collectors.toList());
                    if (CollectionUtil.isNotEmpty(collect)) {
                        records.removeAll(collect);
                        childRecords.addAll(collect);
                    }
                } else {
                    childRecords.add(pageDto);
                }
            }
            // 构建树形
            TreeToolUtils<RbacPermissionPageDto> treeToolUtils = new TreeToolUtils<>(rootRecords, childRecords, new TreeToolUtils.TreeId<>() {
                @Override
                public String getId(RbacPermissionPageDto rbacPermissionPageDto) {
                    return rbacPermissionPageDto.getPermissionId();
                }

                @Override
                public String getParentId(RbacPermissionPageDto rbacPermissionPageDto) {
                    return rbacPermissionPageDto.getParentId();
                }
            });
            records = treeToolUtils.getTree();
        }
        return new PageDto<>(pageInfo, records);
    }


    @Override
    public RbacPermissionDto getById(String permissionId) throws RuntimeException {
        RbacPermissionEntity byId = super.getById(permissionId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "未查询到信息");
        }
        if (StringUtils.isBlank(byId.getParentId())) {
            byId.setParentId(null);
        }
        return dtoMap.dToE(byId);
    }


    @Override
    public void updatePermissionState(String permissionId, Integer type) throws RuntimeException {
        this.getById(permissionId);
        RbacPermissionEntity entity = new RbacPermissionEntity();
        entity.setPermissionId(permissionId);
        entity.setPermissionStatus(type);
        boolean b = this.updateById(entity);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新数据失败");
        }
        // 刷新系统缓存权限
        this.refreshAuth(true);
    }


    @Override
    public void deleteById(String permissionId) throws RuntimeException {
        // 查询是否有下级
        LambdaQueryWrapper<RbacPermissionEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RbacPermissionEntity::getParentId, permissionId);
        List<RbacPermissionEntity> list = this.list(lambdaQueryWrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            throw new ResponseException(Status.VERIFICATION_FAILED, "当前菜单存在下级,请先删除下级");
        }
        // 查询数据是否存在
        RbacPermissionDto byId = this.getById(permissionId);
        if (byId.getEnableDelete() == 0) {
            throw new ResponseException(Status.VERIFICATION_FAILED, "当前菜单不可删除");
        }
        boolean b = this.removeById(permissionId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除菜单失败");
        }
        // 刷新系统缓存权限
        this.refreshAuth(true);
    }


    @Override
    public List<RbacPermissionTreeDto> getPermissionTree(String type, String systemId, Integer status) {
        LambdaQueryWrapper<RbacPermissionEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByAsc(RbacPermissionEntity::getOrderNo);
        if (StringUtils.isNotBlank(type)) {
            lambdaQueryWrapper.in(RbacPermissionEntity::getPermissionType, Arrays.asList(type.split(",")));
        }
        if (status == 1) {
            lambdaQueryWrapper.le(RbacPermissionEntity::getPermissionStatus, 1);
        }
        if (StringUtils.isNotBlank(systemId)) {
            lambdaQueryWrapper.eq(RbacPermissionEntity::getSystemId, systemId);
        }
        List<RbacPermissionEntity> list = this.list(lambdaQueryWrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            List<RbacPermissionTreeDto> rootList = new ArrayList<>();
            List<RbacPermissionTreeDto> subList = new ArrayList<>();
            list.forEach(v -> {
                RbacPermissionTreeDto dto = dtoMap.dToV(v);
                if (StringUtils.isBlank(v.getParentId())) {
                    rootList.add(dto);
                } else {
                    subList.add(dto);
                }
            });
            TreeToolUtils<RbacPermissionTreeDto> toolUtils = new TreeToolUtils<>(rootList, subList, new TreeToolUtils.TreeId<>() {
                @Override
                public String getId(RbacPermissionTreeDto permissionTreeDto) {
                    return permissionTreeDto.getPermissionId();
                }

                @Override
                public String getParentId(RbacPermissionTreeDto permissionTreeDto) {
                    return permissionTreeDto.getParentId();
                }
            });
            return toolUtils.getTree();
        }
        return Collections.emptyList();
    }


    @Override
    public Map<String, Map<String, Set<ActionModel>>> getBackgroundAllActions() {
        LambdaQueryWrapper<RbacPermissionEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RbacPermissionEntity::getPermissionStatus, 1)
                .eq(RbacPermissionEntity::getPermissionType, PermissionType.BUTTON.getType())
                .ne(RbacPermissionEntity::getActionType, 1);
        List<RbacPermissionEntity> list = this.list(lambdaQueryWrapper);
        // 获取所有系统id
        Set<String> serviceIds = list.stream().map(RbacPermissionEntity::getServiceId).collect(Collectors.toSet());
        Map<String, Map<String, Set<ActionModel>>> actions = new HashMap<>(serviceIds.size());
        serviceIds.forEach(v -> {
            Map<String, Set<ActionModel>> action = new HashMap<>();
            list.forEach(sv -> {
                if (v.equals(sv.getServiceId())) {
                    String actionUri = CoreCommonUtils.getUri(sv.getActionUri());
                    ActionModel model = new ActionModel();
                    model.setDescribe(sv.getMetaTitle());
                    model.setPath(actionUri);
                    model.setActionLimitMethod(sv.isActionLimitMethod());
                    if (sv.isActionLimitMethod() && StringUtils.isNotBlank(sv.getActionMethods())) {
                        model.setActionMethodSet(new HashSet<>(Arrays.asList(sv.getActionMethods().split(","))));
                    }
                    model.setServiceId(sv.getServiceId());
                    model.setSystemId(sv.getSystemId());
                    model.setCheckStrategy(sv.getCheckStrategy());
                    String[] split = sv.getActions().split(",");
                    model.setActionSet(new HashSet<>(Arrays.asList(split)));
                    Set<ActionModel> actionModels = action.get(actionUri);
                    if (CollectionUtil.isEmpty(actionModels)) {
                        actionModels = new HashSet<>(8);
                    }
                    actionModels.add(model);
                    action.put(actionUri, actionModels);
                }
            });
            if (CollectionUtil.isNotEmpty(action)) {
                actions.put(v, action);
            }
        });
        return CollectionUtil.isNotEmpty(actions) ? actions : Collections.emptyMap();
    }


    @Override
    public void refreshAuth(boolean force) {
        if (!force) {
            Object redisLockValue = redisTemplate.opsForValue().get(CoreCommonCacheConstant.SYSTEM_AUTH_ACTION_CACHE + "_LOCK");
            if (Objects.nonNull(redisLockValue)) {
                return;
            }
        }
        // 权限信息写入redis
        Map<String, Map<String, Set<ActionModel>>> backgroundAllActions = getBackgroundAllActions();
        redisTemplate.opsForValue().set(CoreCommonCacheConstant.SYSTEM_AUTH_ACTION_CACHE, new AuthActionModel(backgroundAllActions));
        // 通知系统服务刷新权限
        bindingComponent.out(BindingStreamConstant.AUTH_ACTION_PROCESS, "需要拉取权限");
        redisTemplate.opsForValue().set(CoreCommonCacheConstant.SYSTEM_AUTH_ACTION_CACHE + "_LOCK", true, LOCK_EXPIRES, TimeUnit.SECONDS);
    }
}