// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.message.modules.manage.service;


import indi.zxiaozhou.skillfull.coredatabase.base.service.BaseService;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import indi.zxiaozhou.skillfull.message.modules.manage.controller.vo.ManageSendRecordPageVo;
import indi.zxiaozhou.skillfull.message.modules.manage.controller.vo.ManageSendRecordVo;
import indi.zxiaozhou.skillfull.message.modules.manage.entity.ManageSendRecordEntity;
import indi.zxiaozhou.skillfull.message.modules.manage.service.dto.ManageSendRecordDto;
import indi.zxiaozhou.skillfull.message.modules.manage.service.dto.ManageSendRecordPageDto;

import java.util.List;

/**
 * 消息发送记录表(ManageSendRecord)业务层接口
 *
 * @author zxiaozhou
 * @date 2021-02-12 19:57:38
 * @since JDK1.8
 */
public interface IManageSendRecordService extends BaseService<ManageSendRecordEntity> {
    /**
     * 保存
     *
     * @param vo ${@link ManageSendRecordVo} 消息发送记录表保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-02-12 19:57:38
     */
    void save(ManageSendRecordVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo   ${@link ManageSendRecordVo} 消息发送记录表更新
     * @param esId ${@link String} 消息id
     * @param vo   ${@link ManageSendRecordVo} 消息发送记录表更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-02-12 19:57:38
     */
    void updateById(String esId, ManageSendRecordVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link ManageSendRecordPageVo} 消息发送记录表分页查询Vo
     * @return PageDto<ManageSendRecordPageDto> ${@link PageDto<ManageSendRecordPageDto>} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-02-12 19:57:38
     */
    PageDto<ManageSendRecordPageDto> pageByModel(ManageSendRecordPageVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param esId ${@link String} 消息id
     * @return ManageSendRecordDto ${@link ManageSendRecordDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-02-12 19:57:38
     */
    ManageSendRecordDto getById(String esId) throws RuntimeException;


    /**
     * 通过esId删除
     *
     * @param esId ${@link String} 消息id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-08-28 12:20
     */
    void deleteById(String esId) throws RuntimeException;


    /**
     * 消息发送记录表批量删除
     *
     * @param esIds ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-10-24 22:59
     */
    void deleteBatch(List<String> esIds) throws RuntimeException;
}