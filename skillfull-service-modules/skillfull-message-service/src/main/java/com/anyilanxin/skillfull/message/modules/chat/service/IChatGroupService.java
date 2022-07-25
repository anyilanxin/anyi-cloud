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
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatGroupPageVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatGroupQueryVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatGroupVo;
import com.anyilanxin.skillfull.message.modules.chat.entity.ChatGroupEntity;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatGroupDto;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatGroupPageDto;

import java.util.List;

/**
 * 消息群(ChatGroup)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-03-29 23:38:23
 * @since JDK1.8
 */
public interface IChatGroupService extends BaseService<ChatGroupEntity> {
    /**
     * 保存
     *
     * @param vo ${@link ChatGroupVo} 消息群保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:23
     */
    void save(ChatGroupVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo          ${@link ChatGroupVo} 消息群更新
     * @param chatGroupId ${@link String} 群id
     * @param vo          ${@link ChatGroupVo} 消息群更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:23
     */
    void updateById(String chatGroupId, ChatGroupVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link ChatGroupPageVo} 消息群分页查询Vo
     * @return PageDto<ChatGroupPageDto> ${@link PageDto< ChatGroupPageDto >} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:23
     */
    PageDto<ChatGroupPageDto> pageByModel(ChatGroupPageVo vo) throws RuntimeException;

    /**
     * 条件查询多条
     *
     * @param vo ${@link ChatGroupQueryVo} 消息群条件查询Vo
     * @return List<ChatGroupDto> ${@link List< ChatGroupDto >} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:23
     */
    List<ChatGroupDto> selectListByModel(ChatGroupQueryVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param chatGroupId ${@link String} 群id
     * @return ChatGroupDto ${@link ChatGroupDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:23
     */
    ChatGroupDto getById(String chatGroupId) throws RuntimeException;


    /**
     * 通过chatGroupId删除
     *
     * @param chatGroupId ${@link String} 群id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:23
     */
    void deleteById(String chatGroupId) throws RuntimeException;


    /**
     * 消息群批量删除
     *
     * @param chatGroupIds ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:23
     */
    void deleteBatch(List<String> chatGroupIds) throws RuntimeException;
}
