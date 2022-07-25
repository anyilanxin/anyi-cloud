// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.manage.service.mapstruct;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.anyilanxin.skillfull.corecommon.base.service.mapstruct.BaseMap;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.ServiceInstanceDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 路由(ManageRoute)Dto与Entity相互转换
 *
 * @author zxiaozhou zxiaozhou
 * @since 2020-09-12 16:33:37
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface ServiceInstanceDetailMap extends BaseMap<ServiceInstanceDto.ServiceInstanceDetail, Instance> {
}