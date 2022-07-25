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
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.FriendPageVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.FriendQueryVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.FriendVo;
import com.anyilanxin.skillfull.message.modules.chat.entity.FriendEntity;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.FriendDto;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.FriendPageDto;

import java.util.List;

/**
 * 好友列表(Friend)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-03-29 23:38:25
 * @since JDK1.8
 */
public interface IFriendService extends BaseService<FriendEntity> {
    /**
     * 保存
     *
     * @param vo ${@link FriendVo} 好友列表保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:25
     */
    void save(FriendVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo       ${@link FriendVo} 好友列表更新
     * @param friendId ${@link String} 模板id
     * @param vo       ${@link FriendVo} 好友列表更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:25
     */
    void updateById(String friendId, FriendVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link FriendPageVo} 好友列表分页查询Vo
     * @return PageDto<FriendPageDto> ${@link PageDto< FriendPageDto >} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:25
     */
    PageDto<FriendPageDto> pageByModel(FriendPageVo vo) throws RuntimeException;

    /**
     * 条件查询多条
     *
     * @param vo ${@link FriendQueryVo} 好友列表条件查询Vo
     * @return List<FriendDto> ${@link List< FriendDto >} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:25
     */
    List<FriendDto> selectListByModel(FriendQueryVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param friendId ${@link String} 模板id
     * @return FriendDto ${@link FriendDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:25
     */
    FriendDto getById(String friendId) throws RuntimeException;


    /**
     * 通过friendId删除
     *
     * @param friendId ${@link String} 模板id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:25
     */
    void deleteById(String friendId) throws RuntimeException;


    /**
     * 好友列表批量删除
     *
     * @param friendIds ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 23:38:25
     */
    void deleteBatch(List<String> friendIds) throws RuntimeException;
}
