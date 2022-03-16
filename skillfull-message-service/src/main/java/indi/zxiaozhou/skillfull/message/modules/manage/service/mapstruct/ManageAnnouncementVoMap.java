// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.message.modules.manage.service.mapstruct;


import indi.zxiaozhou.skillfull.corecommon.base.service.mapstruct.BaseMap;
import indi.zxiaozhou.skillfull.message.modules.manage.controller.vo.ManageAnnouncementVo;
import indi.zxiaozhou.skillfull.message.modules.manage.entity.ManageAnnouncementEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 系统通告管理(ManageAnnouncement)Vo与Entity相互转换
 *
 * @author zxiaozhou
 * @since 2021-02-12 19:57:12
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface ManageAnnouncementVoMap extends BaseMap<ManageAnnouncementVo, ManageAnnouncementEntity> {
}