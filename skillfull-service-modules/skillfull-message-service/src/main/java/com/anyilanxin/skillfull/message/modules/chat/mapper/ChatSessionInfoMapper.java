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
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatSessionInfoPageVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatSessionInfoQueryVo;
import com.anyilanxin.skillfull.message.modules.chat.entity.ChatSessionInfoEntity;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatSessionInfoDto;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatSessionInfoPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 聊天会话信息(ChatSessionInfo)持久层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-04-08 05:39:29
 * @since JDK1.8
 */
@Repository
public interface ChatSessionInfoMapper extends BaseMapper<ChatSessionInfoEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link ChatSessionInfoPageVo} 查询条件
     * @param page ${@link Page< ChatSessionInfoPageDto >} 分页信息
     * @return IPage<ChatSessionInfoPageDto> ${@link IPage<ChatSessionInfoPageDto>} 结果
     * @author zxiaozhou
     * @date 2022-04-08 05:39:29
     */
    IPage<ChatSessionInfoPageDto> pageByModel(Page<ChatSessionInfoPageDto> page, @Param("query") ChatSessionInfoPageVo vo);


    /**
     * 条件查询多条
     *
     * @param vo ${@link ChatSessionInfoQueryVo} 查询条件
     * @return List<ChatSessionInfoDto> ${@link List< ChatSessionInfoDto >} 结果
     * @author zxiaozhou
     * @date 2022-04-08 05:39:29
     */
    List<ChatSessionInfoDto> selectListByModel(ChatSessionInfoQueryVo vo);


    /**
     * 通过会话id物理删除
     *
     * @param sessionInfoId ${@link String} 会话id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2022-04-08 05:39:29
     */
    int physicalDeleteById(@Param("id") String sessionInfoId);


    /**
     * 通过会话id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2022-04-08 05:39:29
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
