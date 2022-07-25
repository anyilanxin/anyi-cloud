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
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatMessageInfoPageVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatMessageInfoQueryVo;
import com.anyilanxin.skillfull.message.modules.chat.entity.ChatMessageInfoEntity;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatMessageInfoDto;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatMessageInfoPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 聊天消息(ChatMessageInfo)持久层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-03-29 23:38:28
 * @since JDK1.8
 */
@Repository
public interface ChatMessageInfoMapper extends BaseMapper<ChatMessageInfoEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link ChatMessageInfoPageVo} 查询条件
     * @param page ${@link Page< ChatMessageInfoPageDto >} 分页信息
     * @return IPage<ChatMessageInfoPageDto> ${@link IPage<ChatMessageInfoPageDto>} 结果
     * @author zxiaozhou
     * @date 2022-03-29 23:38:28
     */
    IPage<ChatMessageInfoPageDto> pageByModel(Page<ChatMessageInfoPageDto> page, @Param("query") ChatMessageInfoPageVo vo);


    /**
     * 条件查询多条
     *
     * @param vo ${@link ChatMessageInfoQueryVo} 查询条件
     * @return List<ChatMessageInfoDto> ${@link List< ChatMessageInfoDto >} 结果
     * @author zxiaozhou
     * @date 2022-03-29 23:38:28
     */
    List<ChatMessageInfoDto> selectListByModel(ChatMessageInfoQueryVo vo);


    /**
     * 通过聊天消息id物理删除
     *
     * @param chatMessageId ${@link String} 聊天消息id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2022-03-29 23:38:28
     */
    int physicalDeleteById(@Param("id") String chatMessageId);


    /**
     * 通过聊天消息id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2022-03-29 23:38:28
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
