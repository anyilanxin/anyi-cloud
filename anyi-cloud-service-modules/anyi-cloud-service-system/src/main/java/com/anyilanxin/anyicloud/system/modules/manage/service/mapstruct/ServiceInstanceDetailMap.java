

package com.anyilanxin.anyicloud.system.modules.manage.service.mapstruct;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseMap;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.ServiceInstanceDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 路由(ManageRoute)Dto与Entity相互转换
 *
 * @author zxh zxiaozhou
 * @since 2020-09-12 16:33:37
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface ServiceInstanceDetailMap extends BaseMap<ServiceInstanceDto.ServiceInstanceDetail, Instance> {
}
