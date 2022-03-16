package indi.zxiaozhou.skillfull.storage.modules.local.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 本地文件配置查询Response
 *
 * @author zxiaozhou
 * @date 2021-07-21 16:10:05
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@Accessors(chain = true)
@Schema
@AllArgsConstructor
@NoArgsConstructor
public class LocalEnableConfigDto implements Serializable {
    private static final long serialVersionUID = -35134724986697840L;

    @Schema(name = "localConfigId", title = "本地配置文件id")
    private String localConfigId;

    @Schema(name = "localConfigName", title = "配置名称")
    private String localConfigName;

    @Schema(name = "saveBootDiskPath", title = "存储根路径")
    private String saveBootDiskPath;

    @Schema(name = "unifiedPrefix", title = "预览文件统一前缀")
    private String unifiedPrefix;

    @Schema(name = "maxSize", title = "最大使用空间(单位KB)")
    private Double maxSize;

    @Schema(name = "userSize", title = "已经使用空间(单位KB)")
    private Double userSize;

    @Schema(name = "enableUserSize", title = "可以使用空间(单位KB)")
    private Double enableUserSize;
}
