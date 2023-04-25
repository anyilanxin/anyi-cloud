package com.anyilanxin.skillfull.logging.modules.receive.service.mapstruct;

import com.anyilanxin.skillfull.corecommon.base.service.mapstruct.BaseMap;
import com.anyilanxin.skillfull.logging.modules.manage.entity.OperateEntity;
import com.anyilanxin.skillfull.loggingcommon.model.OperateLogModel;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @since 2022-08-13 10:24:41
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface OperateModelCopyMap extends BaseMap<OperateEntity, OperateLogModel> {
}
