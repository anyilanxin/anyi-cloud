package com.anyilanxin.skillfull.message.modules.manage.service;

import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.message.modules.manage.controller.vo.ManageSendRecordPageVo;
import com.anyilanxin.skillfull.message.modules.manage.controller.vo.ManageSendRecordVo;
import com.anyilanxin.skillfull.message.modules.manage.entity.ManageSendRecordEntity;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageSendRecordDto;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageSendRecordPageDto;
import com.anyilanxin.skillfull.messagerpc.model.TemplateResultModel;

import java.util.List;

/**
 * 消息发送记录表(ManageSendRecord)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-03-29 05:23:42
 * @since JDK1.8
 */
public interface IManageSendRecordService extends BaseService<ManageSendRecordEntity> {
    /**
     * 保存
     *
     * @param vo ${@link ManageSendRecordVo} 消息发送记录表保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 05:23:42
     */
    void save(ManageSendRecordVo vo) throws RuntimeException;


    /**
     * 批量保存记录
     *
     * @param recordEntities
     * @return TemplateResultModel
     * @author zxiaozhou
     * @date 2022-08-30 10:27
     */
    TemplateResultModel saveBatchRecord(List<ManageSendRecordEntity> recordEntities);


    /**
     * 分页查询
     *
     * @param vo ${@link ManageSendRecordPageVo} 消息发送记录表分页查询Vo
     * @return PageDto<ManageSendRecordPageDto> ${@link PageDto< ManageSendRecordPageDto >} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 05:23:42
     */
    PageDto<ManageSendRecordPageDto> pageByModel(ManageSendRecordPageVo vo) throws RuntimeException;

    /**
     * 通过id查询详情
     *
     * @param sendRecordId ${@link String} 消息id
     * @return ManageSendRecordDto ${@link ManageSendRecordDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 05:23:42
     */
    ManageSendRecordDto getById(String sendRecordId) throws RuntimeException;


    /**
     * 通过sendRecordId删除
     *
     * @param sendRecordId ${@link String} 消息id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 05:23:42
     */
    void deleteById(String sendRecordId) throws RuntimeException;


    /**
     * 消息发送记录表批量删除
     *
     * @param sendRecordIds ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 05:23:42
     */
    void deleteBatch(List<String> sendRecordIds) throws RuntimeException;
}
