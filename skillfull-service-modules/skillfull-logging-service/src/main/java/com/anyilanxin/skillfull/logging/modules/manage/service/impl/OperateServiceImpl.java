// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.logging.modules.manage.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.anyilanxin.skillfull.corecommon.base.model.stream.OperateLogModel;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.logging.modules.manage.controller.vo.OperatePageVo;
import com.anyilanxin.skillfull.logging.modules.manage.entity.OperateEntity;
import com.anyilanxin.skillfull.logging.modules.manage.mapper.OperateMapper;
import com.anyilanxin.skillfull.logging.modules.manage.service.IOperateService;
import com.anyilanxin.skillfull.logging.modules.manage.service.dto.OperateDto;
import com.anyilanxin.skillfull.logging.modules.manage.service.dto.OperatePageDto;
import com.anyilanxin.skillfull.logging.modules.manage.service.mapstruct.OperateCopyMap;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 操作日志(Operate)业务层实现
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-01-26 19:51:07
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OperateServiceImpl extends ServiceImpl<OperateMapper, OperateEntity> implements IOperateService {
    private final OperateCopyMap map;
    private final OperateMapper mapper;
    @Value("${app.delete-log-type:1}")
    private Integer deleteLogType;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(OperateLogModel model) throws RuntimeException {
        log.info("------------OperateServiceImpl-------收到日志----->save:\n{}", model);
        OperateEntity operateEntity = map.vToE(model);
        boolean save = this.save(operateEntity);
        if (!save) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "操作日志存储失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void saveBatch(List<OperateLogModel> models) throws RuntimeException {
        List<OperateEntity> operateEntities = map.vToE(models);
        boolean save = this.saveBatch(operateEntities);
        if (!save) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "操作日志批量存储失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<OperatePageDto> pageByModel(OperatePageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public OperateDto getById(String operateId) throws RuntimeException {
        OperateEntity byId = super.getById(operateId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return map.eToD(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String operateId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(operateId);
        // 删除数据
        if (deleteLogType == 0) {
            int i = mapper.physicalDeleteById(operateId);
            if (i <= 0) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, "物理删除数据失败");
            }
        } else {
            boolean b = this.removeById(operateId);
            if (!b) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.DeleteDataFail"));
            }
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> operateIds) throws RuntimeException {
        List<OperateEntity> entities = this.listByIds(operateIds);
        if (CollectionUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getOperateId()));
        if (deleteLogType == 0) {
            int i = mapper.physicalDeleteBatchIds(waitDeleteList);
            if (i <= 0) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, "批量物理删除数据失败");
            }
        } else {
            int i = mapper.deleteBatchIds(waitDeleteList);
            if (i <= 0) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.BatchDeleteDataFail"));
            }
        }
    }
}
