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
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacRolePageVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacRolePermissionVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacRoleVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacPermissionEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacRoleEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.mapper.RbacPermissionMapper;
import indi.zxiaozhou.skillfull.auth.modules.rbac.mapper.RbacRoleMapper;
import indi.zxiaozhou.skillfull.auth.modules.rbac.mapper.RbacRolePermissionMapper;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.IRbacRolePermissionService;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.IRbacRoleService;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.*;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.mapstruct.*;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import indi.zxiaozhou.skillfull.corecommon.utils.CodeUtil;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static indi.zxiaozhou.skillfull.corecommon.constant.SysBaseConstant.SUPER_ROLE;

/**
 * 角色表(RbacRole)业务层实现
 *
 * @author zxiaozhou
 * @date 2020-10-08 13:44:03
 * @since JDK11
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class, Error.class})
public class RbacRoleServiceImpl extends ServiceImpl<RbacRoleMapper, RbacRoleEntity> implements IRbacRoleService {
    private final RbacRoleDtoMap dtoMap;
    private final RbacRoleToRolePageMap rolePageMap;
    private final RbacRolePermissionMap rbacRolePermissionMap;
    private final RbacRoleEffectiveDtoMap effectiveDtoMap;
    private final RbacRoleBasicDtoMap basicDtoMap;
    private final RbacRoleAndPermissionDtoMap rbacRoleAndPermissionDtoMap;
    private final RbacRoleVoMap voMap;
    private final PermissionMenuActionMap menuActionMap;
    private final RbacRoleMapper mapper;
    private final RbacRolePermissionMapper rbacRolePermissionMapper;
    private final RbacPermissionMapper permissionMapper;
    private final IRbacRolePermissionService rolePermissionService;


    @Override
    @GlobalTransactional
    public void save(RbacRoleVo vo) throws RuntimeException {
        RbacRoleEntity entity = voMap.aToB(vo);
        // 数据校验
        this.checkData(entity);
        entity.setRoleSysCode(generateCode(null, vo.getParentRoleId(), false));
        // 数据保存
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存数据失败");
        }
        RbacRolePermissionVo permissionVo = new RbacRolePermissionVo();
        permissionVo.setRoleId(entity.getRoleId());
        permissionVo.setPermissionIds(vo.getPermissionIds());
        rolePermissionService.save(permissionVo);
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
            LambdaQueryWrapper<RbacRoleEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.and(v -> v.isNull(RbacRoleEntity::getParentRoleId).or().eq(RbacRoleEntity::getParentRoleId, ""))
                    .orderByDesc(RbacRoleEntity::getRoleSysCode)
                    .last("LIMIT 1");
            RbacRoleEntity one = this.getOne(lambdaQueryWrapper);
            code = CodeUtil.getSubYouBianCode(null, Objects.isNull(one) ? null : one.getRoleSysCode());
        } else {
            // 获取上级code
            RbacRoleDto byId = this.getById(newParentId);
            // 获取本级最大code
            LambdaQueryWrapper<RbacRoleEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.like(RbacRoleEntity::getRoleSysCode, byId.getRoleSysCode() + "____")
                    .orderByDesc(RbacRoleEntity::getRoleSysCode)
                    .last("LIMIT 1");
            RbacRoleEntity one = this.getOne(lambdaQueryWrapper);
            code = CodeUtil.getSubYouBianCode(byId.getRoleSysCode(), Objects.isNull(one) ? null : one.getRoleSysCode());
        }
        return code;
    }


    /**
     * 数据校验
     *
     * @param entity ${@link RbacRoleEntity}
     * @author zxiaozhou
     * @date 2021-01-19 14:33
     */
    public void checkData(RbacRoleEntity entity) {
        // 验证用户名是否重复
        LambdaQueryWrapper<RbacRoleEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RbacRoleEntity::getRoleCode, entity.getRoleCode());
        if (StringUtils.isNotBlank(entity.getRoleId())) {
            lambdaQueryWrapper.ne(RbacRoleEntity::getRoleId, entity.getRoleId());
        }
        RbacRoleEntity one = this.getOne(lambdaQueryWrapper);
        if (Objects.nonNull(one)) {
            throw new ResponseException(Status.VERIFICATION_FAILED, "角色编码已经存在:" + entity.getRoleCode());
        }
    }


    @Override
    @GlobalTransactional
    public void updateById(String roleId, RbacRoleVo vo) throws RuntimeException {
        // 查询数据是否存在
        RbacRoleDto byId = this.getById(roleId);
        RbacRoleEntity entity = voMap.aToB(vo);
        entity.setRoleSysCode(generateCode(byId.getParentRoleId(), vo.getParentRoleId(), true));
        entity.setRoleId(roleId);
        // 数据校验
        this.checkData(entity);
        // 数据保存
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新数据失败");
        }
        // 如果是超级管理员则不需要绑定权限(默认有全部页面按钮权限)
        if (!SUPER_ROLE.equals(byId.getRoleCode())) {
            RbacRolePermissionVo permissionVo = new RbacRolePermissionVo();
            permissionVo.setRoleId(entity.getRoleId());
            permissionVo.setPermissionIds(vo.getPermissionIds());
            rolePermissionService.updatePermission(permissionVo);
        } else {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "超级管理员不需要授权,默认拥有所有权限");
        }
//        // 修改下级角色编码
//        if (StringUtils.isNotBlank(entity.getRoleCode()) && !byId.getRoleSysCode().equals(entity.getRoleCode())) {
//            LambdaQueryWrapper<RbacRoleEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//            lambdaQueryWrapper.likeRight(RbacRoleEntity::getRoleSysCode, byId.getRoleSysCode());
//            List<RbacRoleEntity> list = this.list(lambdaQueryWrapper);
//            if (CollectionUtil.isNotEmpty(list)) {
//                List<RbacRoleEntity> waitUpdateList = new ArrayList<>();
//                list.forEach(v -> {
//                    RbacRoleEntity newEntity = new RbacRoleEntity();
//                    newEntity.setRoleId(v.getRoleId());
//                    newEntity.setRoleSysCode(v.getRoleSysCode().replaceFirst(byId.getRoleSysCode(), entity.getRoleSysCode()));
//                    waitUpdateList.add(newEntity);
//                });
//                boolean b = this.updateBatchById(waitUpdateList);
//                if (!b) {
//                    throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新下级失败");
//                }
//            }
//        }
    }


    @Override
    public PageDto<RbacRolePageDto> pageByModel(RbacRolePageVo vo) throws RuntimeException {
        IPage<RbacRolePageDto> rbacRolePageDtoIPage = mapper.pageByModel(vo.getPage(), vo);
        List<RbacRolePageDto> records = rbacRolePageDtoIPage.getRecords();
        return new PageDto<>(rbacRolePageDtoIPage, records);
    }


    /**
     * 获取有效的菜单按钮信息
     *
     * @param role ${@link RbacRoleEntity} 角色信息
     * @author zxiaozhou
     * @date 2020-10-09 17:36
     */
    @Override
    public Set<RbacRolePermissionInfoDto> getMenuActions(RbacRoleEntity role, String roleId) {
        if (Objects.isNull(role)) {
            role = super.getById(roleId);
        }
        // 处理普通角色
        List<RbacRolePermissionDto> rbacRolePermissionDtos = new ArrayList<>(16);
        // 如果超级管理员角色
        if (SUPER_ROLE.equals(role.getRoleCode())) {
            LambdaQueryWrapper<RbacPermissionEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.ne(RbacPermissionEntity::getPermissionType, 0);
            List<RbacPermissionEntity> rbacPermissionEntities = permissionMapper.selectList(lambdaQueryWrapper);
            if (CollectionUtil.isNotEmpty(rbacPermissionEntities)) {
                List<RbacRolePermissionDto> superRbacRolePermissionDtos = rbacRoleAndPermissionDtoMap.bToA(rbacPermissionEntities);
                for (RbacRolePermissionDto permissionDto : superRbacRolePermissionDtos) {
                    permissionDto.setRoleCode(role.getRoleCode());
                    permissionDto.setRoleId(role.getRoleId());
                    permissionDto.setRoleName(role.getRoleName());
                }
                rbacRolePermissionDtos.addAll(superRbacRolePermissionDtos);
            }
        } else {
            List<RbacRolePermissionDto> rbacRolePermission = rbacRolePermissionMapper.selectMenuAntButton("'" + role.getRoleId() + "'", 1);
            if (CollectionUtil.isNotEmpty(rbacRolePermission)) {
                rbacRolePermissionDtos.addAll(rbacRolePermission);
            }
        }
        // 组装菜单角色信息
        if (CollectionUtil.isNotEmpty(rbacRolePermissionDtos)) {
            List<RbacRolePermissionInfoDto> permissionInfos = new ArrayList<>();
            List<RbacRolePermissionInfoDto.Action> actions = new ArrayList<>();
            rbacRolePermissionDtos.forEach(v -> {
                if (v.getPermissionType() == PermissionType.MENU.getType()) {
                    permissionInfos.add(menuActionMap.vToD(v));
                } else if (v.getPermissionType() == PermissionType.BUTTON.getType()) {
                    actions.add(menuActionMap.vToE(v));
                }
            });
            if (CollectionUtil.isNotEmpty(actions) && CollectionUtil.isNotEmpty(permissionInfos)) {
                permissionInfos.forEach(v -> {
                    Set<RbacRolePermissionInfoDto.Action> finalActions = new HashSet<>();
                    String permissionId = v.getPermissionId();
                    actions.forEach(sv -> {
                        if (permissionId.equals(sv.getParentId()) && sv.getRoleId().equals(v.getRoleId())) {
                            finalActions.add(sv);
                        }
                    });
                    if (CollectionUtil.isNotEmpty(finalActions)) {
                        v.setActionSet(finalActions);
                    }
                });
            }
            if (CollectionUtil.isNotEmpty(permissionInfos)) {
                return new HashSet<>(permissionInfos);
            }
        }
        return Collections.emptySet();
    }


    @Override
    public RbacRoleDto getById(String roleId) throws RuntimeException {
        RbacRoleEntity byId = super.getById(roleId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "未找到符合条件数据");
        }
        RbacRoleDto rbacRoleDto = dtoMap.bToA(byId);
        // 获取有效的菜单、按钮权限信息
        Set<RbacRolePermissionInfoDto> menuActions = getMenuActions(byId, null);
        if (CollectionUtil.isNotEmpty(menuActions)) {
            List<RbacRoleDto.RbacRolePermissionInfoDto> rbacRolePermissionInfoDtos = rbacRolePermissionMap.bToA(new ArrayList<>(menuActions));
            rbacRoleDto.setPermissionInfos(new HashSet<>(rbacRolePermissionInfoDtos));
        }
        // 获取所有有效的目录、菜单、按钮权限id(超级管理默认所有)
        List<RbacRolePermissionDto> rbacRolePermissionDtos = new ArrayList<>();
        if (SUPER_ROLE.equals(rbacRoleDto.getRoleCode())) {
            LambdaQueryWrapper<RbacPermissionEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(RbacPermissionEntity::getPermissionStatus, 1);
            List<RbacPermissionEntity> rbacPermissionEntities = permissionMapper.selectList(lambdaQueryWrapper);
            if (CollectionUtil.isNotEmpty(rbacPermissionEntities)) {
                rbacRolePermissionDtos = rbacRoleAndPermissionDtoMap.bToA(rbacPermissionEntities);
                rbacRolePermissionDtos.forEach(v -> {
                    v.setRoleCode(rbacRoleDto.getRoleCode());
                    v.setRoleId(rbacRoleDto.getRoleId());
                    v.setRoleName(rbacRoleDto.getRoleName());
                });
            }
        } else {
            rbacRolePermissionDtos = rbacRolePermissionMapper.selectMenuAntButton("'" + rbacRoleDto.getRoleId() + "'", 0);
        }
        if (CollectionUtil.isNotEmpty(rbacRolePermissionDtos)) {
            Set<String> permissionAllIds = new HashSet<>();
            rbacRolePermissionDtos.forEach(v -> permissionAllIds.add(v.getPermissionId()));
            rbacRoleDto.setPermissionIds(permissionAllIds);
        }
        return rbacRoleDto;
    }


    @Override
    @GlobalTransactional
    public void deleteById(String roleId) throws RuntimeException {
        // 查询数据是否存在
        RbacRoleDto byId = this.getById(roleId);
        if (byId.getEnableDelete() == 0) {
            throw new ResponseException(Status.VERIFICATION_FAILED, "当前角色不可删除");
        }
        boolean b = this.removeById(roleId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除数据失败");
        }
        // 删除角色关联信息
        rolePermissionService.deleteRolePermission(roleId);
    }


    @Override
    public void updateStatus(String roleId, Integer status) throws RuntimeException {
        // 查询数据是否存在
        RbacRoleDto byId = this.getById(roleId);
        // 如果是超级管理员永远启用
        if (SUPER_ROLE.equals(byId.getRoleCode())) {
            throw new ResponseException(Status.VERIFICATION_FAILED, "超级管理员角色状态不可操作，永远启用");
        }
        RbacRoleEntity entity = new RbacRoleEntity();
        entity.setRoleId(roleId);
        entity.setRoleStatus(status);
        boolean b = this.updateById(entity);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "角色" + (status == 0 ? "禁用" : "启用") + "失败");
        }
    }


    @Override
    public List<RbacRoleEffectiveDto> getEffectiveRoles() throws RuntimeException {
        LambdaQueryWrapper<RbacRoleEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RbacRoleEntity::getRoleStatus, 1);
        List<RbacRoleEntity> list = this.list(lambdaQueryWrapper);
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        return effectiveDtoMap.bToA(list);
    }


    @Override
    public List<RbacRoleBasicDto> getListByCodes(List<String> roleCodes) {
        LambdaQueryWrapper<RbacRoleEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(RbacRoleEntity::getRoleCode, roleCodes);
        List<RbacRoleEntity> rbacRoleEntities = this.list(lambdaQueryWrapper);
        if (CollectionUtil.isNotEmpty(rbacRoleEntities)) {
            return basicDtoMap.bToA(rbacRoleEntities);
        }
        return Collections.emptyList();
    }


    @Override
    public List<RbacRoleBasicDto> getRoleListByIds(List<String> roleIds) {
        LambdaQueryWrapper<RbacRoleEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(RbacRoleEntity::getRoleId, roleIds);
        List<RbacRoleEntity> rbacRoleEntities = this.list(lambdaQueryWrapper);
        if (CollectionUtil.isNotEmpty(rbacRoleEntities)) {
            return basicDtoMap.bToA(rbacRoleEntities);
        }
        return Collections.emptyList();
    }
}