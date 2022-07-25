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
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatGroupPageVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatGroupQueryVo;
import com.anyilanxin.skillfull.message.modules.chat.entity.ChatGroupEntity;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatGroupDto;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatGroupPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 消息群(ChatGroup)持久层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-03-29 23:38:23
 * @since JDK1.8
 */
@Repository
public interface ChatGroupMapper extends BaseMapper<ChatGroupEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link ChatGroupPageVo} 查询条件
     * @param page ${@link Page< ChatGroupPageDto >} 分页信息
     * @return IPage<ChatGroupPageDto> ${@link IPage<ChatGroupPageDto>} 结果
     * @author zxiaozhou
     * @date 2022-03-29 23:38:23
     */
    IPage<ChatGroupPageDto> pageByModel(Page<ChatGroupPageDto> page, @Param("query") ChatGroupPageVo vo);


    /**
     * 条件查询多条
     *
     * @param vo ${@link ChatGroupQueryVo} 查询条件
     * @return List<ChatGroupDto> ${@link List< ChatGroupDto >} 结果
     * @author zxiaozhou
     * @date 2022-03-29 23:38:23
     */
    List<ChatGroupDto> selectListByModel(ChatGroupQueryVo vo);


    /**
     * 通过群id物理删除
     *
     * @param chatGroupId ${@link String} 群id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2022-03-29 23:38:23
     */
    int physicalDeleteById(@Param("id") String chatGroupId);


    /**
     * 通过群id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2022-03-29 23:38:23
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
