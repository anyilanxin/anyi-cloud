

package com.anyilanxin.skillfull.storage.modules.storage.service.mapstruct;

import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.anyicloud.storagerpc.model.StorageInfoModel;
import com.anyilanxin.skillfull.storage.modules.storage.controller.vo.StorageInfoFileVo;
import com.anyilanxin.skillfull.storage.modules.storage.entity.StorageInfoFileEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @since 2022-04-05 09:57:59
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface StorageInfoFileCopyMap extends BaseThreeMap<StorageInfoFileEntity, StorageInfoModel, StorageInfoFileVo> {
}
