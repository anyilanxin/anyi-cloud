package indi.zxiaozhou.skillfull.storage.modules.local.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

import static indi.zxiaozhou.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 本地文件服务查询Response
 *
 * @author zxiaozhou
 * @date 2021-07-22 10:11:11
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class LocalFileDto implements Serializable {
    private static final long serialVersionUID = 555766001442775876L;

    @Schema(name = "localFileId", title = "文件id")
    private String localFileId;

    @Schema(name = "saveBootDiskPath", title = "存储根路径")
    private String saveBootDiskPath;

    @Schema(name = "unifiedPrefix", title = "预览文件统一前缀")
    private String unifiedPrefix;

    @Schema(name = "localConfigId", title = "本地配置文件id")
    private String localConfigId;

    @Schema(name = "fileOriginalName", title = "原始文件名(不包括扩展名)")
    private String fileOriginalName;

    @Schema(name = "fileName", title = "存储本地文件名(不包括扩展名)")
    private String fileName;

    @Schema(name = "fileType", title = "文件类型")
    private String fileType;

    @Schema(name = "contentType", title = "文件流类型")
    private String contentType;

    @Schema(name = "fileSize", title = "文件大小")
    private String fileSize;

    @Schema(name = "fileSizeDetail", title = "文件详细大小")
    private Long fileSizeDetail;

    @Schema(name = "fileMd5", title = "文件md5值")
    private String fileMd5;

    @Schema(name = "fileDiskRelativePath", title = "文件磁盘相对路径(即系统定义文件文件存放磁盘开始路径除外)")
    private String fileDiskRelativePath;

    @Schema(name = "previewPath", title = "文件浏览路径")
    private String previewPath;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "createTime", title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;

}
