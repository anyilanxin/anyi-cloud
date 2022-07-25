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
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.FriendPageVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.FriendQueryVo;
import com.anyilanxin.skillfull.message.modules.chat.entity.FriendEntity;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.FriendDto;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.FriendPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 好友列表(Friend)持久层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-03-29 23:38:24
 * @since JDK1.8
 */
@Repository
public interface FriendMapper extends BaseMapper<FriendEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link FriendPageVo} 查询条件
     * @param page ${@link Page< FriendPageDto >} 分页信息
     * @return IPage<FriendPageDto> ${@link IPage<FriendPageDto>} 结果
     * @author zxiaozhou
     * @date 2022-03-29 23:38:24
     */
    IPage<FriendPageDto> pageByModel(Page<FriendPageDto> page, @Param("query") FriendPageVo vo);


    /**
     * 条件查询多条
     *
     * @param vo ${@link FriendQueryVo} 查询条件
     * @return List<FriendDto> ${@link List< FriendDto >} 结果
     * @author zxiaozhou
     * @date 2022-03-29 23:38:24
     */
    List<FriendDto> selectListByModel(FriendQueryVo vo);


    /**
     * 通过模板id物理删除
     *
     * @param friendId ${@link String} 模板id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2022-03-29 23:38:24
     */
    int physicalDeleteById(@Param("id") String friendId);


    /**
     * 通过模板id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2022-03-29 23:38:24
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
