

package com.anyilanxin.anyicloud.logging.modules.receive.service.mapstruct;

import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseMap;
import com.anyilanxin.anyicloud.logging.modules.manage.entity.AuthDataEntity;
import com.anyilanxin.anyicloud.loggingcommon.model.AuthLogModel;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @since 2022-08-13 10:24:40
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface AuthModelCopyMap extends BaseMap<AuthDataEntity, AuthLogModel> {
}
