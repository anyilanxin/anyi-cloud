

package com.anyilanxin.anyicloud.process.modules.auxiliary.service.mapstruct;

import com.anyilanxin.anyicloudee.corecommon.base.service.mapstruct.BaseMap;
import com.anyilanxin.anyicloudee.process.modules.auxiliary.entity.SequenceFlowEntity;
import com.anyilanxin.anyicloudee.processrpc.model.SequenceFlowModel;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2023-04-26 15:15:53
 * @since JDK11
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface SequenceFlowCopyMap extends BaseMap<SequenceFlowEntity, SequenceFlowModel> {
}
