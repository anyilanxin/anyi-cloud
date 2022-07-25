// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.modules.chat.mapper;

import com.anyilanxin.skillfull.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatGroupUserPageVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatGroupUserQueryVo;
import com.anyilanxin.skillfull.message.modules.chat.entity.ChatGroupUserEntity;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatGroupUserDto;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatGroupUserPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 群成员信息(ChatGroupUser)持久层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-03-29 23:38:26
 * @since JDK1.8
 */
@Repository
public interface ChatGroupUserMapper extends BaseMapper<ChatGroupUserEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link ChatGroupUserPageVo} 查询条件
     * @param page ${@link Page< ChatGroupUserPageDto >} 分页信息
     * @return IPage<ChatGroupUserPageDto> ${@link IPage<ChatGroupUserPageDto>} 结果
     * @author zxiaozhou
     * @date 2022-03-29 23:38:26
     */
    IPage<ChatGroupUserPageDto> pageByModel(Page<ChatGroupUserPageDto> page, @Param("query") ChatGroupUserPageVo vo);


    /**
     * 条件查询多条
     *
     * @param vo ${@link ChatGroupUserQueryVo} 查询条件
     * @return List<ChatGroupUserDto> ${@link List< ChatGroupUserDto >} 结果
     * @author zxiaozhou
     * @date 2022-03-29 23:38:26
     */
    List<ChatGroupUserDto> selectListByModel(ChatGroupUserQueryVo vo);


    /**
     * 通过群成员id物理删除
     *
     * @param groupUserId ${@link String} 群成员id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2022-03-29 23:38:26
     */
    int physicalDeleteById(@Param("id") String groupUserId);


    /**
     * 通过群成员id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2022-03-29 23:38:26
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
