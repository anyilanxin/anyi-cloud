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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacPermissionDataRulePageVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacPermissionDataRuleQueryVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacPermissionDataRuleVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacPermissionDataRuleEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.mapper.RbacPermissionDataRuleMapper;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.IRbacPermissionDataRuleService;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacPermissionDataRuleDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacPermissionDataRulePageDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.mapstruct.*;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 权限数据填值规则表(RbacPermissionDataRule)业务层实现
 *
 * @author zxiaozhou
 * @date 2020-11-02 09:41:28
 * @since JDK11
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class, Error.class})
public class RbacPermissionDataRuleServiceImpl extends ServiceImpl<RbacPermissionDataRuleMapper, RbacPermissionDataRuleEntity> implements IRbacPermissionDataRuleService {
    private final RbacPermissionDataRuleDtoMap dtoMap;
    private final RbacPermissionDataRulePageDtoMap pageDtoMap;
    private final RbacPermissionDataRuleVoMap voMap;
    private final RbacPermissionDataRuleQueryVoMap queryVoMap;
    private final RbacPermissionDataRulePageVoMap pageVoMap;
    private final RbacPermissionDataRuleMapper mapper;


    @Override
    public void save(RbacPermissionDataRuleVo vo) throws RuntimeException {
        RbacPermissionDataRuleEntity entity = voMap.aToB(vo);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存数据失败");
        }
    }


    @Override
    public void updateById(String permissionDataRuleId, RbacPermissionDataRuleVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(permissionDataRuleId);
        // 更新数据
        RbacPermissionDataRuleEntity entity = voMap.aToB(vo);
        entity.setPermissionDataRuleId(permissionDataRuleId);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新数据失败");
        }
    }


    @Override
    public List<RbacPermissionDataRuleDto> selectListByModel(RbacPermissionDataRuleQueryVo vo) throws RuntimeException {
        List<RbacPermissionDataRuleDto> list = mapper.selectListByModel(vo);
        if (CollectionUtils.isEmpty(list)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "未找到符合条件数据");
        }
        return list;
    }


    @Override
    public PageDto<RbacPermissionDataRulePageDto> pageByModel(RbacPermissionDataRulePageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    public PageDto<RbacPermissionDataRulePageDto> page(RbacPermissionDataRulePageVo vo) throws RuntimeException {
        LambdaQueryWrapper<RbacPermissionDataRuleEntity> queryWrapper = new LambdaQueryWrapper<>(pageVoMap.aToB(vo));
        if (Objects.nonNull(vo.getStartTime())) {
            queryWrapper.gt(RbacPermissionDataRuleEntity::getCreateTime, vo.getStartTime());
        }
        if (Objects.nonNull(vo.getEndTime())) {
            queryWrapper.lt(RbacPermissionDataRuleEntity::getCreateTime, vo.getEndTime());
        }
        Page<RbacPermissionDataRuleEntity> page = this.page(vo.getPage(), queryWrapper);
        return new PageDto<>(page, pageDtoMap.bToA(page.getRecords()));
    }


    @Override
    public RbacPermissionDataRuleDto getById(String permissionDataRuleId) throws RuntimeException {
        RbacPermissionDataRuleEntity byId = super.getById(permissionDataRuleId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "未找到符合条件数据");
        }
        return dtoMap.bToA(byId);
    }


    @Override
    public void deleteById(String permissionDataRuleId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(permissionDataRuleId);
        boolean b = this.removeById(permissionDataRuleId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除数据失败");
        }
    }


    @Override
    public void deleteBatch(List<String> permissionDataRuleIds) throws RuntimeException {
        List<RbacPermissionDataRuleEntity> entities = this.listByIds(permissionDataRuleIds);
        if (CollectionUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "数据不存在或已经被别人删除");
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getPermissionDataRuleId()));
        int i = mapper.deleteBatchIds(waitDeleteList);
        if (i <= 0) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "批量删除数据失败");
        }
    }
}