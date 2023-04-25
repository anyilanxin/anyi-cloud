package com.anyilanxin.skillfull.message.strategy.msgsubscribe.mapstruct;

import com.anyilanxin.skillfull.corecommon.base.service.mapstruct.BaseMap;
import com.anyilanxin.skillfull.messagerpc.model.SocketMsgModel;
import com.anyilanxin.skillfull.messagerpc.model.SubscribeMsgModel;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @since 2022-03-29 08:34:22
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface SocketMsgModelCopyMap extends BaseMap<SocketMsgModel, SubscribeMsgModel> {
}
