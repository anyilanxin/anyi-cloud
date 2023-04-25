package com.anyilanxin.skillfull.system.modules.manage.service.mapstruct;

import com.anyilanxin.skillfull.corecommon.base.service.mapstruct.BaseMap;
import com.anyilanxin.skillfull.system.modules.manage.controller.vo.ManageSpecialUrlQueryVo;
import com.anyilanxin.skillfull.system.modules.manage.entity.ManageSpecialUrlEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @since 2021-12-19 09:34:51
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface ManageSpecialUrlQueryCopyMap extends BaseMap<ManageSpecialUrlQueryVo, ManageSpecialUrlEntity> {
}
