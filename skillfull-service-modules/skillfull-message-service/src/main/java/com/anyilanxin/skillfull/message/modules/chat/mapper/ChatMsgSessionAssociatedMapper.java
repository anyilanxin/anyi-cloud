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
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatMsgSessionAssociatedPageVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatMsgSessionAssociatedQueryVo;
import com.anyilanxin.skillfull.message.modules.chat.entity.ChatMsgSessionAssociatedEntity;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatMsgSessionAssociatedDto;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatMsgSessionAssociatedPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 聊天会话关系表(ChatMsgSessionAssociated)持久层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-03-29 23:38:30
 * @since JDK1.8
 */
@Repository
public interface ChatMsgSessionAssociatedMapper extends BaseMapper<ChatMsgSessionAssociatedEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link ChatMsgSessionAssociatedPageVo} 查询条件
     * @param page ${@link Page< ChatMsgSessionAssociatedPageDto >} 分页信息
     * @return IPage<ChatMsgSessionAssociatedPageDto> ${@link IPage<ChatMsgSessionAssociatedPageDto>} 结果
     * @author zxiaozhou
     * @date 2022-03-29 23:38:30
     */
    IPage<ChatMsgSessionAssociatedPageDto> pageByModel(Page<ChatMsgSessionAssociatedPageDto> page, @Param("query") ChatMsgSessionAssociatedPageVo vo);


    /**
     * 条件查询多条
     *
     * @param vo ${@link ChatMsgSessionAssociatedQueryVo} 查询条件
     * @return List<ChatMsgSessionAssociatedDto> ${@link List< ChatMsgSessionAssociatedDto >} 结果
     * @author zxiaozhou
     * @date 2022-03-29 23:38:30
     */
    List<ChatMsgSessionAssociatedDto> selectListByModel(ChatMsgSessionAssociatedQueryVo vo);


    /**
     * 通过消息会话关联id物理删除
     *
     * @param correlationMsgSessionId ${@link String} 消息会话关联id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2022-03-29 23:38:30
     */
    int physicalDeleteById(@Param("id") String correlationMsgSessionId);


    /**
     * 通过消息会话关联id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2022-03-29 23:38:30
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
