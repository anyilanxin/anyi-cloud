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
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacOrgPageVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacOrgVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacOrgEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.mapper.RbacOrgMapper;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.IRbacOrgService;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacOrgDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacOrgHasChildrenDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacOrgTreeDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacOrgTreePageDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.mapstruct.*;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import indi.zxiaozhou.skillfull.corecommon.utils.CodeUtil;
import indi.zxiaozhou.skillfull.corecommon.utils.tree.TreeToolUtils;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 组织表(RbacOrg)业务层实现
 *
 * @author zxiaozhou
 * @date 2021-01-19 12:59:55
 * @since JDK11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RbacOrgServiceImpl extends ServiceImpl<RbacOrgMapper, RbacOrgEntity> implements IRbacOrgService {
    private final RbacOrgDtoMap dtoMap;
    private final RbacOrgTreeHasChildrenDtoMap hasChildrenDtoMap;
    private final RbacOrgTreeDtoMap treeDtoMap;
    private final RbacOrgPageDtoMap pageDtoMap;
    private final RbacOrgVoMap voMap;
    private final RbacOrgMapper mapper;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(RbacOrgVo vo) throws RuntimeException {
        RbacOrgEntity entity = voMap.aToB(vo);
        this.checkData(entity);
        entity.setOrgSysCode(generateCode(null, vo.getParentId(), false));
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存数据失败");
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
            LambdaQueryWrapper<RbacOrgEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.and(v -> v.isNull(RbacOrgEntity::getParentId).or().eq(RbacOrgEntity::getParentId, ""))
                    .orderByDesc(RbacOrgEntity::getOrgSysCode)
                    .last("LIMIT 1");
            RbacOrgEntity one = this.getOne(lambdaQueryWrapper);
            code = CodeUtil.getSubYouBianCode(null, Objects.isNull(one) ? null : one.getOrgSysCode());
        } else {
            // 获取上级code
            RbacOrgDto byId = this.getById(newParentId);
            // 获取本级最大code
            LambdaQueryWrapper<RbacOrgEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.like(RbacOrgEntity::getOrgSysCode, byId.getOrgSysCode() + "____")
                    .orderByDesc(RbacOrgEntity::getOrgSysCode)
                    .last("LIMIT 1");
            RbacOrgEntity one = this.getOne(lambdaQueryWrapper);
            code = CodeUtil.getSubYouBianCode(byId.getOrgSysCode(), Objects.isNull(one) ? null : one.getOrgSysCode());
        }
        return code;
    }


    /**
     * 入库前数据校验以及生产系统机构编码
     *
     * @param entity ${@link RbacOrgEntity} 待入库数据
     * @author zxiaozhou
     * @date 2021-01-30 23:53
     */
    @Transactional(rollbackFor = {Exception.class, Error.class})
    void checkData(RbacOrgEntity entity) {
        // 查询自定义机构编码是否重复
        LambdaQueryWrapper<RbacOrgEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RbacOrgEntity::getOrgCode, entity.getOrgCode());
        if (StringUtils.isNotBlank(entity.getOrgId())) {
            lambdaQueryWrapper.ne(RbacOrgEntity::getOrgId, entity.getOrgId());
        }
        RbacOrgEntity one = this.getOne(lambdaQueryWrapper);
        if (Objects.nonNull(one)) {
            throw new ResponseException(Status.VERIFICATION_FAILED, "当前机构编码已经存在:" + entity.getOrgCode());
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String orgId, RbacOrgVo vo) throws RuntimeException {
        // 查询数据是否存在
        RbacOrgDto byId = this.getById(orgId);
        // 更新数据
        RbacOrgEntity entity = voMap.aToB(vo);
        entity.setOrgId(orgId);
        entity.setOrgSysCode(generateCode(byId.getParentId(), vo.getParentId(), true));
        this.checkData(entity);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新数据失败");
        }
        // 更新下级
        if (StringUtils.isNotBlank(entity.getOrgSysCode()) && !byId.getOrgSysCode().equals(entity.getOrgSysCode())) {
            LambdaQueryWrapper<RbacOrgEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.likeRight(RbacOrgEntity::getOrgSysCode, byId.getOrgSysCode());
            List<RbacOrgEntity> list = this.list(lambdaQueryWrapper);
            if (CollectionUtil.isNotEmpty(list)) {
                List<RbacOrgEntity> waitUpdateList = new ArrayList<>();
                list.forEach(v -> {
                    RbacOrgEntity newEntity = new RbacOrgEntity();
                    newEntity.setOrgId(v.getOrgId());
                    newEntity.setOrgSysCode(v.getOrgSysCode().replaceFirst(byId.getOrgSysCode(), entity.getOrgSysCode()));
                    waitUpdateList.add(newEntity);
                });
                boolean b = this.updateBatchById(waitUpdateList);
                if (!b) {
                    throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新下级失败");
                }
            }
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<RbacOrgTreePageDto> pageByModel(RbacOrgPageVo vo) throws RuntimeException {
        // 分页查询
        vo.getAscs().add("orgSysCode");
        IPage<RbacOrgTreePageDto> pageInfo = mapper.pageByModel(vo.getPage(), vo);
        /*
         * 1. 获取根节点
         * 2. 构建树形
         */
        // 获取根节点
        List<RbacOrgTreePageDto> records = pageInfo.getRecords();
        if (CollectionUtil.isNotEmpty(records)) {
            List<RbacOrgTreePageDto> rootRecords = new ArrayList<>(records.size());
            List<RbacOrgTreePageDto> childRecords = new ArrayList<>(records.size());
            while (records.size() > 0) {
                RbacOrgTreePageDto pageDto = records.remove(0);
                String orgSysCode = pageDto.getOrgSysCode();
                boolean isParent = true;
                for (RbacOrgTreePageDto dto : records) {
                    if (orgSysCode.startsWith(dto.getOrgSysCode())) {
                        isParent = false;
                    }
                }
                if (isParent) {
                    rootRecords.add(pageDto);
                    // 添加所有子类
                    List<RbacOrgTreePageDto> collect = records.stream().filter(v -> v.getOrgSysCode().startsWith(orgSysCode)).collect(Collectors.toList());
                    if (CollectionUtil.isNotEmpty(collect)) {
                        records.removeAll(collect);
                        childRecords.addAll(collect);
                    }
                } else {
                    childRecords.add(pageDto);
                }
            }
            // 构建树形
            TreeToolUtils<RbacOrgTreePageDto> treeToolUtils = new TreeToolUtils<>(rootRecords, childRecords, new TreeToolUtils.TreeId<>() {
                @Override
                public String getId(RbacOrgTreePageDto orgTreePageDto) {
                    return orgTreePageDto.getOrgId();
                }

                @Override
                public String getParentId(RbacOrgTreePageDto orgTreePageDto) {
                    return orgTreePageDto.getParentId();
                }
            });
            records = treeToolUtils.getTree();
        }
        return new PageDto<>(pageInfo, records);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public RbacOrgDto getById(String orgId) throws RuntimeException {
        RbacOrgEntity byId = super.getById(orgId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "未找到符合条件数据");
        }
        return dtoMap.bToA(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String orgId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(orgId);
        // 删除数据
        boolean b = this.removeById(orgId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除数据失败");
        }
    }


    @Override
    public List<RbacOrgHasChildrenDto> selectOrgList(Integer type, String parentId) {
        LambdaQueryWrapper<RbacOrgEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (type == 1) {
            lambdaQueryWrapper.eq(RbacOrgEntity::getOrgStatus, 1);
        }
        if (StringUtils.isNotBlank(parentId)) {
            lambdaQueryWrapper.eq(RbacOrgEntity::getParentId, parentId);
        }
        List<RbacOrgEntity> list = this.list(lambdaQueryWrapper);
        List<RbacOrgHasChildrenDto> rbacOrgTreeDtos = hasChildrenDtoMap.bToA(list);
        // 处理是否有下级的问题
        if (CollectionUtil.isNotEmpty(rbacOrgTreeDtos)) {
            Set<String> parentIds = new HashSet<>();
            rbacOrgTreeDtos.forEach(v -> parentIds.add(v.getOrgId()));
            lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.in(RbacOrgEntity::getParentId, parentIds);
            List<RbacOrgEntity> children = this.list(lambdaQueryWrapper);
            if (CollectionUtil.isNotEmpty(children)) {
                rbacOrgTreeDtos.forEach(v -> {
                    String orgId = v.getOrgId();
                    for (RbacOrgEntity entity : children) {
                        if (orgId.equals(entity.getParentId())) {
                            v.setHasChildren(true);
                            break;
                        }
                    }
                });
            }
        }
        return rbacOrgTreeDtos;
    }


    @Override
    public void updateOrgState(String orgId, Integer type) throws RuntimeException {
        // 查询数据是否存在
        this.getById(orgId);
        // 更新数据
        RbacOrgEntity entity = new RbacOrgEntity();
        entity.setOrgId(orgId);
        entity.setOrgStatus(type);
        boolean b = super.updateById(entity);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, type == 0 ? "机构禁用失败" : "机构启用失败");
        }
    }


    @Override
    public List<RbacOrgTreeDto> selectOrgTreeList(Integer type) throws RuntimeException {
        // 获取父级
        LambdaQueryWrapper<RbacOrgEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.and(v -> v.isNull(RbacOrgEntity::getParentId).or().eq(RbacOrgEntity::getParentId, ""));
        if (type == 1) {
            lambdaQueryWrapper.eq(RbacOrgEntity::getOrgStatus, 1);
        }
        List<RbacOrgEntity> parentList = this.list(lambdaQueryWrapper);
        // 获取所有非父级
        lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.isNotNull(RbacOrgEntity::getParentId).ne(RbacOrgEntity::getParentId, "");
        if (type == 1) {
            lambdaQueryWrapper.eq(RbacOrgEntity::getOrgStatus, 1);
        }
        List<RbacOrgEntity> children = this.list(lambdaQueryWrapper);
        // 组装树形
        if (CollectionUtil.isNotEmpty(parentList)) {
            TreeToolUtils<RbacOrgTreeDto> utils = new TreeToolUtils<>(treeDtoMap.bToA(parentList), treeDtoMap.bToA(children), new TreeToolUtils.TreeId<>() {
                @Override
                public String getId(RbacOrgTreeDto rbacOrgTreeDto) {
                    return rbacOrgTreeDto.getOrgId();
                }

                @Override
                public String getParentId(RbacOrgTreeDto rbacOrgTreeDto) {
                    return rbacOrgTreeDto.getParentId();
                }
            });
            return utils.getTree();
        }
        return Collections.emptyList();
    }

}