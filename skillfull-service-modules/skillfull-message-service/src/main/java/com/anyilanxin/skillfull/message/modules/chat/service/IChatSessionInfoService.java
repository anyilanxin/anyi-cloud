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
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatSessionInfoPageVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatSessionInfoQueryVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatSessionInfoVo;
import com.anyilanxin.skillfull.message.modules.chat.entity.ChatSessionInfoEntity;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatSessionInfoDto;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatSessionInfoPageDto;

import java.util.List;

/**
 * 聊天会话信息(ChatSessionInfo)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-04-08 05:39:30
 * @since JDK1.8
 */
public interface IChatSessionInfoService extends BaseService<ChatSessionInfoEntity> {
    /**
     * 保存
     *
     * @param vo ${@link ChatSessionInfoVo} 聊天会话信息保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-04-08 05:39:30
     */
    void save(ChatSessionInfoVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo            ${@link ChatSessionInfoVo} 聊天会话信息更新
     * @param sessionInfoId ${@link String} 会话id
     * @param vo            ${@link ChatSessionInfoVo} 聊天会话信息更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-04-08 05:39:30
     */
    void updateById(String sessionInfoId, ChatSessionInfoVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link ChatSessionInfoPageVo} 聊天会话信息分页查询Vo
     * @return PageDto<ChatSessionInfoPageDto> ${@link PageDto< ChatSessionInfoPageDto >} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-04-08 05:39:30
     */
    PageDto<ChatSessionInfoPageDto> pageByModel(ChatSessionInfoPageVo vo) throws RuntimeException;

    /**
     * 条件查询多条
     *
     * @param vo ${@link ChatSessionInfoQueryVo} 聊天会话信息条件查询Vo
     * @return List<ChatSessionInfoDto> ${@link List< ChatSessionInfoDto >} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-04-08 05:39:30
     */
    List<ChatSessionInfoDto> selectListByModel(ChatSessionInfoQueryVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param sessionInfoId ${@link String} 会话id
     * @return ChatSessionInfoDto ${@link ChatSessionInfoDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-04-08 05:39:30
     */
    ChatSessionInfoDto getById(String sessionInfoId) throws RuntimeException;


    /**
     * 通过sessionInfoId删除
     *
     * @param sessionInfoId ${@link String} 会话id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-04-08 05:39:30
     */
    void deleteById(String sessionInfoId) throws RuntimeException;


    /**
     * 聊天会话信息批量删除
     *
     * @param sessionInfoIds ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-04-08 05:39:30
     */
    void deleteBatch(List<String> sessionInfoIds) throws RuntimeException;
}
