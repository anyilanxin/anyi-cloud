// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.modules.chat.service;

import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatMsgSessionAssociatedPageVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatMsgSessionAssociatedQueryVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatMsgSessionAssociatedVo;
import com.anyilanxin.skillfull.message.modules.chat.entity.ChatMsgSessionAssociatedEntity;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatMsgSessionAssociatedDto;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatMsgSessionAssociatedPageDto;

import java.util.List;

/**
 * 聊天会话关系表(ChatMsgSessionAssociated)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-03-29 23:38:30
 * @since JDK1.8
 */
public interface IChatMsgSessionAssociatedService extends BaseService<ChatMsgSessionAssociatedEntity> {
    /**
     * 保存
     *
     * @param vo ${@link ChatMsgSessionAssociatedVo} 聊天会话关系表保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:30
     */
    void save(ChatMsgSessionAssociatedVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo                      ${@link ChatMsgSessionAssociatedVo} 聊天会话关系表更新
     * @param correlationMsgSessionId ${@link String} 消息会话关联id
     * @param vo                      ${@link ChatMsgSessionAssociatedVo} 聊天会话关系表更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:30
     */
    void updateById(String correlationMsgSessionId, ChatMsgSessionAssociatedVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link ChatMsgSessionAssociatedPageVo} 聊天会话关系表分页查询Vo
     * @return PageDto<ChatMsgSessionAssociatedPageDto> ${@link PageDto< ChatMsgSessionAssociatedPageDto >} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:30
     */
    PageDto<ChatMsgSessionAssociatedPageDto> pageByModel(ChatMsgSessionAssociatedPageVo vo) throws RuntimeException;

    /**
     * 条件查询多条
     *
     * @param vo ${@link ChatMsgSessionAssociatedQueryVo} 聊天会话关系表条件查询Vo
     * @return List<ChatMsgSessionAssociatedDto> ${@link List< ChatMsgSessionAssociatedDto >} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:30
     */
    List<ChatMsgSessionAssociatedDto> selectListByModel(ChatMsgSessionAssociatedQueryVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param correlationMsgSessionId ${@link String} 消息会话关联id
     * @return ChatMsgSessionAssociatedDto ${@link ChatMsgSessionAssociatedDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:30
     */
    ChatMsgSessionAssociatedDto getById(String correlationMsgSessionId) throws RuntimeException;


    /**
     * 通过correlationMsgSessionId删除
     *
     * @param correlationMsgSessionId ${@link String} 消息会话关联id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:30
     */
    void deleteById(String correlationMsgSessionId) throws RuntimeException;


    /**
     * 聊天会话关系表批量删除
     *
     * @param correlationMsgSessionIds ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:30
     */
    void deleteBatch(List<String> correlationMsgSessionIds) throws RuntimeException;
}
