// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.message.modules.message.service;


import indi.zxiaozhou.skillfull.coredatabase.base.service.BaseService;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import indi.zxiaozhou.skillfull.message.modules.message.controller.vo.MessageUserRecordPageVo;
import indi.zxiaozhou.skillfull.message.modules.message.controller.vo.MessageUserRecordVo;
import indi.zxiaozhou.skillfull.message.modules.message.entity.MessageUserRecordEntity;
import indi.zxiaozhou.skillfull.message.modules.message.service.dto.MessageUserNoReadRecordDto;
import indi.zxiaozhou.skillfull.message.modules.message.service.dto.MessageUserRecordDto;
import indi.zxiaozhou.skillfull.message.modules.message.service.dto.MessageUserRecordPageDto;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;

/**
 * 用户消息记录(MessageUserRecord)业务层接口
 *
 * @author zxiaozhou
 * @date 2021-01-26 16:48:37
 * @since JDK11
 */
public interface IMessageUserRecordService extends BaseService<MessageUserRecordEntity> {
    /**
     * 保存
     *
     * @param vo ${@link MessageUserRecordVo} 用户消息记录保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-26 16:48:37
     */
    void save(MessageUserRecordVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo    ${@link MessageUserRecordVo} 用户消息记录更新
     * @param msgId ${@link String} 消息记录id
     * @param vo    ${@link MessageUserRecordVo} 用户消息记录更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-26 16:48:37
     */
    void updateById(String msgId, MessageUserRecordVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo      ${@link MessageUserRecordPageVo} 用户消息记录分页查询Vo
     * @param request ${@link ServerHttpRequest}
     * @return PageDto<MessageUserRecordPageDto> ${@link PageDto<MessageUserRecordPageDto>} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-26 16:48:37
     */
    PageDto<MessageUserRecordPageDto> pageByModel(MessageUserRecordPageVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param msgId ${@link String} 消息记录id
     * @return MessageUserRecordDto ${@link MessageUserRecordDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-26 16:48:37
     */
    MessageUserRecordDto getById(String msgId) throws RuntimeException;


    /**
     * 通过msgId删除
     *
     * @param msgId ${@link String} 消息记录id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-08-28 12:20
     */
    void deleteById(String msgId) throws RuntimeException;


    /**
     * 用户消息记录批量删除
     *
     * @param msgIds ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-10-24 22:59
     */
    void deleteBatch(List<String> msgIds) throws RuntimeException;


    /**
     * 获取未读消息
     *
     * @return MessageUserNoReadRecordDto ${@link MessageUserNoReadRecordDto}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-26 17:47
     */
    MessageUserNoReadRecordDto getListNoRead() throws RuntimeException;


    /**
     * 清空消息
     *
     * @param type ${@link Integer} 清空类型:0-系统公告,1-代办事项,不传清空全部
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-26 19:04
     */
    void clearMsg(Integer type) throws RuntimeException;
}