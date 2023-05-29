

package com.anyilanxin.anyicloud.system.modules.manage.service.mapstruct;

import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.anyicloud.system.modules.manage.controller.vo.ManageSpecialUrlVo;
import com.anyilanxin.anyicloud.system.modules.manage.entity.ManageSpecialUrlEntity;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.ManageSpecialUrlDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @since 2021-12-19 09:34:51
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface ManageSpecialUrlCopyMap extends BaseThreeMap<ManageSpecialUrlEntity, ManageSpecialUrlDto, ManageSpecialUrlVo> {
}
