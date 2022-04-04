package indi.zxiaozhou.skillfull.storage.modules.local.controller.vo;

import indi.zxiaozhou.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import indi.zxiaozhou.skillfull.corecommon.validation.annotation.NotInEnum;
import indi.zxiaozhou.skillfull.storage.core.constant.impl.ConfigStateType;
import indi.zxiaozhou.skillfull.storage.core.constant.impl.StorageUnitType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 本地文件配置添加或修改Request
 *
 * @author zxiaozhou
 * @date 2021-07-21 16:10:05
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@Accessors(chain = true)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class LocalConfigVo implements Serializable {
    private static final long serialVersionUID = 865129091313362818L;

    @Schema(name = "localConfigName", title = "配置名称", required = true)
    @NotBlankOrNull(message = "配置名称不能为空")
    private String localConfigName;

    @Schema(name = "localConfigDescribe", title = "配置说明")
    private String localConfigDescribe;

    @Schema(name = "saveBootDiskPath", title = "存储根路径", required = true)
    @NotBlankOrNull(message = "存储根路径不能为空")
    private String saveBootDiskPath;

    @Schema(name = "unifiedPrefix", title = "预览文件统一前缀", required = true)
    @NotBlankOrNull(message = "预览文件统一前缀不能为空")
    private String unifiedPrefix;

    @Schema(name = "configState", title = "状态：0-禁用,1-启用,2-空间满,来源于常量字典:config_state", required = true)
    @NotBlankOrNull(message = "状态不能为空")
    @NotInEnum(message = "类型只能是0、1、2", enumClass = ConfigStateType.class)
    private Integer configState;

    @Schema(name = "maxSize", title = "最大使用空间", required = true)
    @NotBlankOrNull(message = "最大使用空间不能为空")
    private Long maxSize;

    @Schema(name = "sizeUnit", title = "空间单位来源与常量字典:size_unit,0-MB,1-GB,2-TB,默认0", required = true)
    @NotBlankOrNull(message = "空间单位不能为空")
    @NotInEnum(message = "类型只能是0、1、2", enumClass = StorageUnitType.class)
    private Integer sizeUnit;

    @Schema(name = "remark", title = "备注")
    private String remark;

}
