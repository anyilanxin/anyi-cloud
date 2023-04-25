package com.anyilanxin.skillfull.logging.modules.manage.service;

import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.logging.modules.manage.controller.vo.OperatePageVo;
import com.anyilanxin.skillfull.logging.modules.manage.entity.OperateEntity;
import com.anyilanxin.skillfull.logging.modules.manage.service.dto.OperateDto;
import com.anyilanxin.skillfull.logging.modules.manage.service.dto.OperatePageDto;

import java.util.List;

/**
 * 操作日志(Operate)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-01-26 19:51:07
 * @since JDK1.8
 */
public interface IOperateService extends BaseService<OperateEntity> {
    /**
     * 日志入库
     *
     * @author zxiaozhou
     * @date 2022-08-13 11:11
     */
    void storage();

    /**
     * 分页查询
     *
     * @param vo ${@link OperatePageVo} 操作日志分页查询Vo
     * @return PageDto<OperatePageDto> ${@link PageDto< OperatePageDto >} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-01-26 19:51:07
     */
    PageDto<OperatePageDto> pageByModel(OperatePageVo vo) throws RuntimeException;

    /**
     * 通过id查询详情
     *
     * @param operateId ${@link String} 操作日志id
     * @return OperateDto ${@link OperateDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-01-26 19:51:07
     */
    OperateDto getById(String operateId) throws RuntimeException;


    /**
     * 通过operateId删除
     *
     * @param operateId ${@link String} 操作日志id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-01-26 19:51:07
     */
    void deleteById(String operateId) throws RuntimeException;


    /**
     * 操作日志批量删除
     *
     * @param operateIds ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-01-26 19:51:07
     */
    void deleteBatch(List<String> operateIds) throws RuntimeException;
}
