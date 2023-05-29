

package com.anyilanxin.anyicloud.message.modules.manage.service.mapstruct;

import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.anyicloud.message.modules.manage.controller.vo.ManageTemplateVo;
import com.anyilanxin.anyicloud.message.modules.manage.entity.ManageTemplateEntity;
import com.anyilanxin.anyicloud.message.modules.manage.service.dto.ManageTemplateDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @since 2022-03-29 05:23:43
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface ManageTemplateCopyMap extends BaseThreeMap<ManageTemplateEntity, ManageTemplateDto, ManageTemplateVo> {
}
