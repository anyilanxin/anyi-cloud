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
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatGroupUserPageVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatGroupUserQueryVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatGroupUserVo;
import com.anyilanxin.skillfull.message.modules.chat.entity.ChatGroupUserEntity;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatGroupUserDto;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatGroupUserPageDto;

import java.util.List;

/**
 * 群成员信息(ChatGroupUser)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-03-29 23:38:27
 * @since JDK1.8
 */
public interface IChatGroupUserService extends BaseService<ChatGroupUserEntity> {
    /**
     * 保存
     *
     * @param vo ${@link ChatGroupUserVo} 群成员信息保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:27
     */
    void save(ChatGroupUserVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo          ${@link ChatGroupUserVo} 群成员信息更新
     * @param groupUserId ${@link String} 群成员id
     * @param vo          ${@link ChatGroupUserVo} 群成员信息更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:27
     */
    void updateById(String groupUserId, ChatGroupUserVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link ChatGroupUserPageVo} 群成员信息分页查询Vo
     * @return PageDto<ChatGroupUserPageDto> ${@link PageDto< ChatGroupUserPageDto >} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:27
     */
    PageDto<ChatGroupUserPageDto> pageByModel(ChatGroupUserPageVo vo) throws RuntimeException;

    /**
     * 条件查询多条
     *
     * @param vo ${@link ChatGroupUserQueryVo} 群成员信息条件查询Vo
     * @return List<ChatGroupUserDto> ${@link List< ChatGroupUserDto >} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:27
     */
    List<ChatGroupUserDto> selectListByModel(ChatGroupUserQueryVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param groupUserId ${@link String} 群成员id
     * @return ChatGroupUserDto ${@link ChatGroupUserDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:27
     */
    ChatGroupUserDto getById(String groupUserId) throws RuntimeException;


    /**
     * 通过groupUserId删除
     *
     * @param groupUserId ${@link String} 群成员id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:27
     */
    void deleteById(String groupUserId) throws RuntimeException;


    /**
     * 群成员信息批量删除
     *
     * @param groupUserIds ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:27
     */
    void deleteBatch(List<String> groupUserIds) throws RuntimeException;
}
