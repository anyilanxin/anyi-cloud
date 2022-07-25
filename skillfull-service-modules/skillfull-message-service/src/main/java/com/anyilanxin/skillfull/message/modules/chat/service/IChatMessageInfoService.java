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
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatMessageInfoPageVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatMessageInfoQueryVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatMessageInfoVo;
import com.anyilanxin.skillfull.message.modules.chat.entity.ChatMessageInfoEntity;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatMessageInfoDto;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatMessageInfoPageDto;

import java.util.List;

/**
 * 聊天消息(ChatMessageInfo)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-03-29 23:38:28
 * @since JDK1.8
 */
public interface IChatMessageInfoService extends BaseService<ChatMessageInfoEntity> {
    /**
     * 保存
     *
     * @param vo ${@link ChatMessageInfoVo} 聊天消息保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:28
     */
    void save(ChatMessageInfoVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo            ${@link ChatMessageInfoVo} 聊天消息更新
     * @param chatMessageId ${@link String} 聊天消息id
     * @param vo            ${@link ChatMessageInfoVo} 聊天消息更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:28
     */
    void updateById(String chatMessageId, ChatMessageInfoVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link ChatMessageInfoPageVo} 聊天消息分页查询Vo
     * @return PageDto<ChatMessageInfoPageDto> ${@link PageDto< ChatMessageInfoPageDto >} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:28
     */
    PageDto<ChatMessageInfoPageDto> pageByModel(ChatMessageInfoPageVo vo) throws RuntimeException;

    /**
     * 条件查询多条
     *
     * @param vo ${@link ChatMessageInfoQueryVo} 聊天消息条件查询Vo
     * @return List<ChatMessageInfoDto> ${@link List< ChatMessageInfoDto >} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:28
     */
    List<ChatMessageInfoDto> selectListByModel(ChatMessageInfoQueryVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param chatMessageId ${@link String} 聊天消息id
     * @return ChatMessageInfoDto ${@link ChatMessageInfoDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:28
     */
    ChatMessageInfoDto getById(String chatMessageId) throws RuntimeException;


    /**
     * 通过chatMessageId删除
     *
     * @param chatMessageId ${@link String} 聊天消息id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:28
     */
    void deleteById(String chatMessageId) throws RuntimeException;


    /**
     * 聊天消息批量删除
     *
     * @param chatMessageIds ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:28
     */
    void deleteBatch(List<String> chatMessageIds) throws RuntimeException;
}
