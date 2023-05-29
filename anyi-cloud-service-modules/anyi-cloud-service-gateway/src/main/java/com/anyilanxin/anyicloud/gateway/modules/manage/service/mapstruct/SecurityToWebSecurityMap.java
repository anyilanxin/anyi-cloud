

package com.anyilanxin.anyicloud.gateway.modules.manage.service.mapstruct;

import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.anyicloud.corecommon.model.system.ConfigDataSecurityModel;
import com.anyilanxin.anyicloud.corecommon.model.system.UserDataSecurityModel;
import com.anyilanxin.anyicloud.corecommon.model.web.WebSecurityModel;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 系统加解密信息与web端互转
 *
 * @author zxh
 * @since 2020-09-12 16:33:37
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface SecurityToWebSecurityMap extends BaseThreeMap<UserDataSecurityModel, WebSecurityModel, ConfigDataSecurityModel> {
}
