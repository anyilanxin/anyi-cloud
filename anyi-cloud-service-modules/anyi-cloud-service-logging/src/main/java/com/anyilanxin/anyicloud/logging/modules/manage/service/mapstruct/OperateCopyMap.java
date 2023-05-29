

package com.anyilanxin.anyicloud.logging.modules.manage.service.mapstruct;

import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.anyicloud.logging.modules.manage.controller.vo.OperateVo;
import com.anyilanxin.anyicloud.logging.modules.manage.entity.OperateEntity;
import com.anyilanxin.anyicloud.logging.modules.manage.service.dto.OperateDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @since 2022-08-13 11:27:29
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface OperateCopyMap extends BaseThreeMap<OperateEntity, OperateDto, OperateVo> {
}
