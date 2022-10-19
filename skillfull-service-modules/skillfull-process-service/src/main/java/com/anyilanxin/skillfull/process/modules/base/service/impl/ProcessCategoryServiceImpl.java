// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------

package com.anyilanxin.skillfull.process.modules.base.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.corecommon.model.common.SelectModel;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.ProcessCategoryPageVo;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.ProcessCategoryQueryVo;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.ProcessCategoryVo;
import com.anyilanxin.skillfull.process.modules.base.entity.ProcessCategoryEntity;
import com.anyilanxin.skillfull.process.modules.base.mapper.ProcessCategoryMapper;
import com.anyilanxin.skillfull.process.modules.base.service.IProcessCategoryService;
import com.anyilanxin.skillfull.process.modules.base.service.dto.ProcessCategoryDto;
import com.anyilanxin.skillfull.process.modules.base.service.dto.ProcessCategoryPageDto;
import com.anyilanxin.skillfull.process.modules.base.service.mapstruct.ProcessCategoryCopyMap;
import com.anyilanxin.skillfull.process.modules.base.service.mapstruct.ProcessCategoryPageCopyMap;
import com.anyilanxin.skillfull.process.modules.base.service.mapstruct.ProcessCategoryQueryCopyMap;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 流程类别(ProcessCategory)业务层实现
 *
 * @author zxiaozhou
 * @date 2021-11-19 10:47:01
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessCategoryServiceImpl extends ServiceImpl<ProcessCategoryMapper, ProcessCategoryEntity> implements IProcessCategoryService {
    private final ProcessCategoryCopyMap map;
    private final ProcessCategoryPageCopyMap pageMap;
    private final ProcessCategoryQueryCopyMap queryMap;
    private final ProcessCategoryMapper mapper;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(ProcessCategoryVo vo) throws RuntimeException {
        ProcessCategoryEntity entity = map.vToE(vo);
        // 数据验证
        checkData(entity);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.SaveDataFail"));
        }
    }

    /**
     * 数据校验
     *
     * @param entity ${@link ProcessCategoryEntity}
     * @author zxiaozhou
     * @date 2021-11-19 14:25
     */
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    void checkData(ProcessCategoryEntity entity) {
        LambdaQueryWrapper<ProcessCategoryEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ProcessCategoryEntity::getCategoryCode, entity.getCategoryCode());
        if (StringUtils.isNotBlank(entity.getCategoryId())) {
            lambdaQueryWrapper.ne(ProcessCategoryEntity::getCategoryId, entity.getCategoryId());
        }
        ProcessCategoryEntity one = this.getOne(lambdaQueryWrapper);
        if (Objects.nonNull(one)) {
            throw new ResponseException(Status.VERIFICATION_FAILED, "当前类别编码已经存在:" + entity.getCategoryCode());
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String categoryId, ProcessCategoryVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(categoryId);
        // 更新数据
        ProcessCategoryEntity entity = map.vToE(vo);
        entity.setCategoryId(categoryId);
        // 数据验证
        checkData(entity);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.UpdateDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<ProcessCategoryDto> selectListByModel(ProcessCategoryQueryVo vo) throws RuntimeException {
        List<ProcessCategoryDto> list = mapper.selectListByModel(vo);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list;
    }


    @Override
    public ProcessCategoryDto selectByCode(String categoryCode) {
        LambdaQueryWrapper<ProcessCategoryEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ProcessCategoryEntity::getCategoryCode, categoryCode);
        return map.eToD(this.getOne(lambdaQueryWrapper));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<ProcessCategoryPageDto> pageByModel(ProcessCategoryPageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public ProcessCategoryDto getById(String categoryId) throws RuntimeException {
        ProcessCategoryEntity byId = super.getById(categoryId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return map.eToD(byId);
    }


    @Override
    public List<ProcessCategoryDto> selectListByCodes(Set<String> categoryCodes) {
        if (CollUtil.isNotEmpty(categoryCodes)) {
            LambdaQueryWrapper<ProcessCategoryEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.in(ProcessCategoryEntity::getCategoryCode, categoryCodes);
            List<ProcessCategoryEntity> list = this.list(lambdaQueryWrapper);
            return map.eToD(list);
        }
        return Collections.emptyList();
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String categoryId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(categoryId);
        // 删除数据
        boolean b = this.removeById(categoryId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.DeleteDataFail"));
        }
    }


    @Override
    public List<SelectModel> getModelDesignList() {
        List<SelectModel> result = mapper.getModelDesignList();
        if (CollUtil.isEmpty(result)) {
            return Collections.emptyList();
        }
        return result;
    }
}
