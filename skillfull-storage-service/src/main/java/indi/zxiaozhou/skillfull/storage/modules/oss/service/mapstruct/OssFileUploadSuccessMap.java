// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.storage.modules.oss.service.mapstruct;

import indi.zxiaozhou.skillfull.corecommon.base.service.mapstruct.BaseMap;
import indi.zxiaozhou.skillfull.storage.modules.oss.service.dto.OssFileDto;
import indi.zxiaozhou.skillfull.storage.modules.oss.service.dto.OssFileUploadBatchDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 本地文件服务(OssFileDto)Dto与UploadSuccess相互转换
 *
 * @author zxiaozhou
 * @since 2020-10-23 22:25:31
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface OssFileUploadSuccessMap extends BaseMap<OssFileDto, OssFileUploadBatchDto.UploadSuccess> {
}