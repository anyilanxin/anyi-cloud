// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.modules.manage.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.message.modules.manage.controller.vo.ManageSendRecordPageVo;
import com.anyilanxin.skillfull.message.modules.manage.controller.vo.ManageSendRecordVo;
import com.anyilanxin.skillfull.message.modules.manage.entity.ManageSendRecordEntity;
import com.anyilanxin.skillfull.message.modules.manage.mapper.ManageSendRecordMapper;
import com.anyilanxin.skillfull.message.modules.manage.service.IManageSendRecordService;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageSendRecordDto;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageSendRecordPageDto;
import com.anyilanxin.skillfull.message.modules.manage.service.mapstruct.ManageSendRecordCopyMap;
import com.anyilanxin.skillfull.messagerpc.model.TemplateResultModel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 消息发送记录表(ManageSendRecord)业务层实现
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-03-29 05:23:42
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ManageSendRecordServiceImpl extends ServiceImpl<ManageSendRecordMapper, ManageSendRecordEntity> implements IManageSendRecordService {
    private final ManageSendRecordCopyMap map;
    private final ManageSendRecordMapper mapper;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(ManageSendRecordVo vo) throws RuntimeException {
        ManageSendRecordEntity entity = map.vToE(vo);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.SaveDataFail"));
        }
    }


    @Override
    public TemplateResultModel saveBatchRecord(List<ManageSendRecordEntity> recordEntities) {
        return null;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<ManageSendRecordPageDto> pageByModel(ManageSendRecordPageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public ManageSendRecordDto getById(String sendRecordId) throws RuntimeException {
        ManageSendRecordEntity byId = super.getById(sendRecordId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return map.eToD(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String sendRecordId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(sendRecordId);
        // 删除数据
        boolean b = this.removeById(sendRecordId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.DeleteDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> sendRecordIds) throws RuntimeException {
        List<ManageSendRecordEntity> entities = this.listByIds(sendRecordIds);
        if (CollectionUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getSendRecordId()));
        int i = mapper.deleteBatchIds(waitDeleteList);
        if (i <= 0) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.BatchDeleteDataFail"));
        }
    }
}
