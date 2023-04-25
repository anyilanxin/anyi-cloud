package com.anyilanxin.skillfull.message.modules.manage.mapper;

import com.anyilanxin.skillfull.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.skillfull.message.modules.manage.controller.vo.ManageAnnouncementPageVo;
import com.anyilanxin.skillfull.message.modules.manage.controller.vo.ManageAnnouncementQueryVo;
import com.anyilanxin.skillfull.message.modules.manage.entity.ManageAnnouncementEntity;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageAnnouncementDto;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageAnnouncementPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 系统通告公告管理(ManageAnnouncement)持久层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-03-29 08:34:21
 * @since JDK1.8
 */
@Repository
public interface ManageAnnouncementMapper extends BaseMapper<ManageAnnouncementEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link ManageAnnouncementPageVo} 查询条件
     * @param page ${@link Page< ManageAnnouncementPageDto >} 分页信息
     * @return IPage<ManageAnnouncementPageDto> ${@link IPage<ManageAnnouncementPageDto>} 结果
     * @author zxiaozhou
     * @date 2022-03-29 08:34:21
     */
    IPage<ManageAnnouncementPageDto> pageByModel(Page<ManageAnnouncementPageDto> page, @Param("query") ManageAnnouncementPageVo vo);


    /**
     * 条件查询多条
     *
     * @param vo ${@link ManageAnnouncementQueryVo} 查询条件
     * @return List<ManageAnnouncementDto> ${@link List< ManageAnnouncementDto >} 结果
     * @author zxiaozhou
     * @date 2022-03-29 08:34:21
     */
    List<ManageAnnouncementDto> selectListByModel(ManageAnnouncementQueryVo vo);


    /**
     * 通过通知公告id物理删除
     *
     * @param anntId ${@link String} 通知公告id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2022-03-29 08:34:21
     */
    int physicalDeleteById(@Param("id") String anntId);


    /**
     * 通过通知公告id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2022-03-29 08:34:21
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
