package com.anyilanxin.skillfull.storage.modules.storage.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 本地文件服务分页查询Response
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-04-05 09:57:59
 * @since JDK1.8
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class StorageInfoFilePageDto implements Serializable {
    private static final long serialVersionUID = -48395495292418903L;

    @Schema(name = "fileId", title = "文件id")
    private String fileId;

    @Schema(name = "fileOriginalName", title = "原始文件名(不包括扩展名)")
    private String fileOriginalName;

    @Schema(name = "fileType", title = "文件类型")
    private String fileType;

    @Schema(name = "fileDirPrefix", title = "存放文件夹名称")
    private String fileDirPrefix;

    @Schema(name = "fileStorageType", title = "文件引擎类型：1-本地，2-ali oss,3-minio,具体与StorageType一致")
    private Integer fileStorageType;

    @Schema(name = "contentType", title = "文件流类型")
    private String contentType;

    @Schema(name = "fileSize", title = "文件大小")
    private String fileSize;

    @Schema(name = "fileSizeDetail", title = "文件详细大小")
    private Long fileSizeDetail;

    @Schema(name = "fileMd5", title = "文件md5值")
    private String fileMd5;

    @Schema(name = "fileRelativePath", title = "文件相对路径")
    private String fileRelativePath;

    @Schema(name = "endpoint", title = "endpoint")
    private String endpoint;

    @Schema(name = "fileHost", title = "文件域名(主要用于非本地存储使用)")
    private String fileHost;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;
}
