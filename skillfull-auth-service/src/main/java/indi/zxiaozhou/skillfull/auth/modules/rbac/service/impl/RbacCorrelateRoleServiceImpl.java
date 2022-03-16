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
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacCorrelateRoleQueryVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacCorrelateRoleVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacCorrelateRoleEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.mapper.RbacCorrelateRoleMapper;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.IRbacCorrelateRoleService;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacCorrelateRoleDto;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 角色关联关系表(RbacCorrelateRole)业务层实现
 *
 * @author zxiaozhou
 * @date 2020-11-02 09:37:27
 * @since JDK11
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class, Error.class})
public class RbacCorrelateRoleServiceImpl extends ServiceImpl<RbacCorrelateRoleMapper, RbacCorrelateRoleEntity> implements IRbacCorrelateRoleService {
    private final RbacCorrelateRoleMapper mapper;


    @Override
    public void save(RbacCorrelateRoleVo vo) throws RuntimeException {
        String correlateId = vo.getCorrelateId();
        Integer correlateType = vo.getCorrelateType();
        if (CollectionUtil.isNotEmpty(vo.getRoleIds())) {
            List<RbacCorrelateRoleEntity> list = new ArrayList<>();
            vo.getRoleIds().forEach(v -> {
                RbacCorrelateRoleEntity entity = new RbacCorrelateRoleEntity();
                entity.setCorrelateId(correlateId);
                entity.setCorrelateType(correlateType);
                entity.setRoleId(v);
                list.add(entity);
            });
            boolean b = this.saveBatch(list);
            if (!b) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存数据失败");
            }
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<RbacCorrelateRoleDto> selectListByModel(RbacCorrelateRoleQueryVo vo) throws RuntimeException {
        List<RbacCorrelateRoleDto> list = mapper.selectListByModel(vo);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list;
    }


    @Override
    public void updateRoleCorrelate(RbacCorrelateRoleVo vo) throws RuntimeException {
        this.deleteRolePermission(vo.getCorrelateId());
        this.save(vo);
    }


    @Override
    public void deleteRolePermission(String correlateId) throws RuntimeException {
        LambdaQueryWrapper<RbacCorrelateRoleEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RbacCorrelateRoleEntity::getCorrelateId, correlateId);
        List<RbacCorrelateRoleEntity> list = this.list(lambdaQueryWrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            List<String> waiteDeleteIds = new ArrayList<>();
            list.forEach(v -> waiteDeleteIds.add(v.getCorrelateRoleId()));
            int i = mapper.physicalDeleteBatchIds(waiteDeleteIds);
            if (i <= 0) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除角色管理关系失败");
            }
        }
    }
}