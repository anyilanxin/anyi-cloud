// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.common.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.zxiaozhou.skillfull.auth.modules.common.controller.vo.CommonUserIdentityPageVo;
import indi.zxiaozhou.skillfull.auth.modules.common.controller.vo.CommonUserIdentityQueryVo;
import indi.zxiaozhou.skillfull.auth.modules.common.controller.vo.CommonUserIdentityVo;
import indi.zxiaozhou.skillfull.auth.modules.common.entity.CommonUserIdentityEntity;
import indi.zxiaozhou.skillfull.auth.modules.common.mapper.CommonUserIdentityMapper;
import indi.zxiaozhou.skillfull.auth.modules.common.service.ICommonUserIdentityService;
import indi.zxiaozhou.skillfull.auth.modules.common.service.dto.CommonUserIdentityDto;
import indi.zxiaozhou.skillfull.auth.modules.common.service.dto.CommonUserIdentityPageDto;
import indi.zxiaozhou.skillfull.auth.modules.common.service.mapstruct.*;
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
 * 实名信息表(CommonUserIdentity)业务层实现
 *
 * @author zxiaozhou
 * @date 2021-02-12 19:41:42
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommonUserIdentityServiceImpl extends ServiceImpl<CommonUserIdentityMapper, CommonUserIdentityEntity> implements ICommonUserIdentityService {
    private final CommonUserIdentityDtoMap dtoMap;
    private final CommonUserIdentityPageDtoMap pageDtoMap;
    private final CommonUserIdentityVoMap voMap;
    private final CommonUserIdentityQueryVoMap queryVoMap;
    private final CommonUserIdentityPageVoMap pageVoMap;
    private final CommonUserIdentityMapper mapper;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(CommonUserIdentityVo vo) throws RuntimeException {
        CommonUserIdentityEntity entity = voMap.aToB(vo);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存数据失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String identityId, CommonUserIdentityVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(identityId);
        // 更新数据
        CommonUserIdentityEntity entity = voMap.aToB(vo);
        entity.setIdentityId(identityId);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新数据失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<CommonUserIdentityDto> selectListByModel(CommonUserIdentityQueryVo vo) throws RuntimeException {
        List<CommonUserIdentityDto> list = mapper.selectListByModel(vo);
        if (CollectionUtils.isEmpty(list)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "未找到符合条件数据");
        }
        return list;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<CommonUserIdentityPageDto> pageByModel(CommonUserIdentityPageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<CommonUserIdentityPageDto> page(CommonUserIdentityPageVo vo) throws RuntimeException {
        LambdaQueryWrapper<CommonUserIdentityEntity> queryWrapper = new LambdaQueryWrapper<>(pageVoMap.aToB(vo));
        if (Objects.nonNull(vo.getStartTime())) {
            queryWrapper.gt(CommonUserIdentityEntity::getCreateTime, vo.getStartTime());
        }
        if (Objects.nonNull(vo.getEndTime())) {
            queryWrapper.lt(CommonUserIdentityEntity::getCreateTime, vo.getEndTime());
        }
        Page<CommonUserIdentityEntity> page = this.page(vo.getPage(), queryWrapper);
        return new PageDto<>(page, pageDtoMap.bToA(page.getRecords()));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public CommonUserIdentityDto getById(String identityId) throws RuntimeException {
        CommonUserIdentityEntity byId = super.getById(identityId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "未找到符合条件数据");
        }
        return dtoMap.bToA(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String identityId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(identityId);
        boolean b = this.removeById(identityId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除数据失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> identityIds) throws RuntimeException {
        List<CommonUserIdentityEntity> entities = this.listByIds(identityIds);
        if (CollectionUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "数据不存在或已经被别人删除");
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getIdentityId()));
        int i = mapper.deleteBatchIds(waitDeleteList);
        if (i <= 0) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "批量删除数据失败");
        }
    }
}