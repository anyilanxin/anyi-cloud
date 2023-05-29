

package com.anyilanxin.anyicloud.process.modules.auxiliary.service.mapstruct;

import com.anyilanxin.anyicloudee.corecommon.base.service.mapstruct.BaseMap;
import com.anyilanxin.anyicloudee.process.modules.auxiliary.entity.ExtendPropertyEntity;
import com.anyilanxin.anyicloudee.processrpc.model.ExtendPropertyModel;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-11-18 10:38:07
 * @since 1.0.0
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface ExtendPropertyCopyMap extends BaseMap<ExtendPropertyEntity, ExtendPropertyModel> {
}
