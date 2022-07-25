// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.modules.announcement.mapper;

import com.anyilanxin.skillfull.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.skillfull.message.modules.announcement.controller.vo.AnnouncementRecordPageVo;
import com.anyilanxin.skillfull.message.modules.announcement.controller.vo.AnnouncementRecordQueryVo;
import com.anyilanxin.skillfull.message.modules.announcement.entity.AnnouncementRecordEntity;
import com.anyilanxin.skillfull.message.modules.announcement.service.dto.AnnouncementRecordDto;
import com.anyilanxin.skillfull.message.modules.announcement.service.dto.AnnouncementRecordPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 系统通知公告阅读记录(AnnouncementRecord)持久层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-03-29 08:35:34
 * @since JDK1.8
 */
@Repository
public interface AnnouncementRecordMapper extends BaseMapper<AnnouncementRecordEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link AnnouncementRecordPageVo} 查询条件
     * @param page ${@link Page< AnnouncementRecordPageDto >} 分页信息
     * @return IPage<AnnouncementRecordPageDto> ${@link IPage<AnnouncementRecordPageDto>} 结果
     * @author zxiaozhou
     * @date 2022-03-29 08:35:34
     */
    IPage<AnnouncementRecordPageDto> pageByModel(Page<AnnouncementRecordPageDto> page, @Param("query") AnnouncementRecordPageVo vo);


    /**
     * 条件查询多条
     *
     * @param vo ${@link AnnouncementRecordQueryVo} 查询条件
     * @return List<AnnouncementRecordDto> ${@link List< AnnouncementRecordDto >} 结果
     * @author zxiaozhou
     * @date 2022-03-29 08:35:34
     */
    List<AnnouncementRecordDto> selectListByModel(AnnouncementRecordQueryVo vo);


    /**
     * 通过通知公告阅读记录id物理删除
     *
     * @param anntReadId ${@link String} 通知公告阅读记录id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2022-03-29 08:35:34
     */
    int physicalDeleteById(@Param("id") String anntReadId);


    /**
     * 通过通知公告阅读记录id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2022-03-29 08:35:34
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
