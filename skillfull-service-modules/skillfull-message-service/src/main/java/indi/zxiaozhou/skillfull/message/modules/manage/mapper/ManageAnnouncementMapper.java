// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.message.modules.manage.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.zxiaozhou.skillfull.coredatabase.base.mapper.BaseMapper;
import indi.zxiaozhou.skillfull.message.modules.manage.controller.vo.ManageAnnouncementPageVo;
import indi.zxiaozhou.skillfull.message.modules.manage.controller.vo.ManageAnnouncementQueryVo;
import indi.zxiaozhou.skillfull.message.modules.manage.entity.ManageAnnouncementEntity;
import indi.zxiaozhou.skillfull.message.modules.manage.service.dto.ManageAnnouncementDto;
import indi.zxiaozhou.skillfull.message.modules.manage.service.dto.ManageAnnouncementPageDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 系统通告管理(ManageAnnouncement)持久层
 *
 * @author zxiaozhou
 * @date 2021-02-12 19:56:59
 * @since JDK1.8
 */
@Repository
public interface ManageAnnouncementMapper extends BaseMapper<ManageAnnouncementEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link ManageAnnouncementPageVo} 查询条件
     * @param page ${@link Page<ManageAnnouncementPageDto>} 分页信息
     * @return IPage<ManageAnnouncementPageDto> ${@link IPage<ManageAnnouncementPageDto>} 结果
     * @author zxiaozhou
     * @date 2021-02-12 19:56:59
     */
    IPage<ManageAnnouncementPageDto> pageByModel(Page<ManageAnnouncementPageDto> page, @Param("query") ManageAnnouncementPageVo vo);


    /**
     * 条件查询多条
     *
     * @param vo ${@link ManageAnnouncementQueryVo} 查询条件
     * @return List<ManageAnnouncementDto> ${@link List<ManageAnnouncementDto>} 结果
     * @author zxiaozhou
     * @date 2021-02-12 19:56:59
     */
    List<ManageAnnouncementDto> selectListByModel(ManageAnnouncementQueryVo vo);


    /**
     * 通过公告id物理删除
     *
     * @param anntId ${@link String} 公告id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2020-08-28 11:36
     */
    int physicalDeleteById(@Param("id") String anntId);


    /**
     * 通过公告id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2020-08-28 11:36
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}