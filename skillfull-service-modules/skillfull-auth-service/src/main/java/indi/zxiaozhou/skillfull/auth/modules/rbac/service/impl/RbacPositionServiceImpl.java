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
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacPositionPageVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacPositionVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacPositionEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.mapper.RbacPositionMapper;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.IRbacPositionService;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacPositionDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacPositionPageDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.mapstruct.*;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 职位表(RbacPosition)业务层实现
 *
 * @author zxiaozhou
 * @date 2021-01-19 18:17:57
 * @since JDK11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RbacPositionServiceImpl extends ServiceImpl<RbacPositionMapper, RbacPositionEntity> implements IRbacPositionService {
    private final RbacPositionDtoMap dtoMap;
    private final RbacPositionPageDtoMap pageDtoMap;
    private final RbacPositionVoMap voMap;
    private final RbacPositionQueryVoMap queryVoMap;
    private final RbacPositionPageVoMap pageVoMap;
    private final RbacPositionMapper mapper;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(RbacPositionVo vo) throws RuntimeException {
        RbacPositionEntity entity = voMap.aToB(vo);
        // 数据重复性校验
        this.checkData(entity);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存数据失败");
        }
    }

    /**
     * 数据校验
     *
     * @param entity ${@link RbacPositionEntity}
     * @author zxiaozhou
     * @date 2021-01-30 00:07
     */
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    void checkData(RbacPositionEntity entity) {
        LambdaQueryWrapper<RbacPositionEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RbacPositionEntity::getPositionCode, entity.getPositionCode());
        if (StringUtils.isNotBlank(entity.getPositionId())) {
            lambdaQueryWrapper.ne(RbacPositionEntity::getPositionId, entity.getPositionId());
        }
        RbacPositionEntity one = this.getOne(lambdaQueryWrapper);
        if (Objects.nonNull(one)) {
            throw new ResponseException(Status.VERIFICATION_FAILED, "当前职位编码已经存在:" + entity.getPositionCode());
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String positionId, RbacPositionVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(positionId);
        // 更新数据
        RbacPositionEntity entity = voMap.aToB(vo);
        entity.setPositionId(positionId);
        // 数据重复性校验
        this.checkData(entity);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新数据失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<RbacPositionDto> getAllList() throws RuntimeException {
        LambdaQueryWrapper<RbacPositionEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RbacPositionEntity::getPositionStatus, 1);
        List<RbacPositionEntity> list = this.list(lambdaQueryWrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            return dtoMap.bToA(list);
        }
        return Collections.emptyList();
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<RbacPositionPageDto> pageByModel(RbacPositionPageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public RbacPositionDto getById(String positionId) throws RuntimeException {
        RbacPositionEntity byId = super.getById(positionId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "未找到符合条件数据");
        }
        return dtoMap.bToA(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String positionId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(positionId);
        boolean b = this.removeById(positionId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除数据失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updatePositionState(String positionId, Integer type) throws RuntimeException {
        // 查询数据是否存在
        this.getById(positionId);
        RbacPositionEntity entity = new RbacPositionEntity();
        entity.setPositionId(positionId);
        entity.setPositionStatus(type);
        boolean b = super.updateById(entity);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, type == 0 ? "职位禁用失败" : "职位启用失败");
        }
    }
}