// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.modules.manage.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.message.modules.manage.controller.vo.ManageTemplatePageVo;
import com.anyilanxin.skillfull.message.modules.manage.controller.vo.ManageTemplateVo;
import com.anyilanxin.skillfull.message.modules.manage.entity.ManageTemplateEntity;
import com.anyilanxin.skillfull.message.modules.manage.mapper.ManageTemplateMapper;
import com.anyilanxin.skillfull.message.modules.manage.service.IManageTemplateService;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageTemplateDto;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageTemplatePageDto;
import com.anyilanxin.skillfull.message.modules.manage.service.mapstruct.ManageTemplateCopyMap;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 消息模板(ManageTemplate)业务层实现
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-03-29 05:23:43
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ManageTemplateServiceImpl extends ServiceImpl<ManageTemplateMapper, ManageTemplateEntity> implements IManageTemplateService {
    private final ManageTemplateCopyMap map;
    private final ManageTemplateMapper mapper;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(ManageTemplateVo vo) throws RuntimeException {
        ManageTemplateEntity entity = map.vToE(vo);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.SaveDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String templateId, ManageTemplateVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(templateId);
        // 更新数据
        ManageTemplateEntity entity = map.vToE(vo);
        entity.setTemplateId(templateId);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.UpdateDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<ManageTemplatePageDto> pageByModel(ManageTemplatePageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public ManageTemplateDto getById(String templateId) throws RuntimeException {
        ManageTemplateEntity byId = super.getById(templateId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return map.eToD(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String templateId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(templateId);
        // 删除数据
        boolean b = this.removeById(templateId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.DeleteDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> templateIds) throws RuntimeException {
        List<ManageTemplateEntity> entities = this.listByIds(templateIds);
        if (CollectionUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getTemplateId()));
        int i = mapper.deleteBatchIds(waitDeleteList);
        if (i <= 0) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.BatchDeleteDataFail"));
        }
    }
}
