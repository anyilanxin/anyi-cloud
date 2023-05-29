

package com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct;

import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacOrgMenuVo;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacOrgMenuEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacOrgMenuDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @since 2022-07-02 23:01:20
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface RbacOrgMenuCopyMap extends BaseThreeMap<RbacOrgMenuEntity, RbacOrgMenuDto, RbacOrgMenuVo> {
}
