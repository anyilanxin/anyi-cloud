// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.storage.modules.oss.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

import static indi.zxiaozhou.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * oss文件分页查询Response
 *
 * @author zxiaozhou
 * @date 2020-10-26 12:25:51
 * @since JDK11
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class OssFilePageDto implements Serializable {
    private static final long serialVersionUID = 237599041344729139L;

    @Schema(name = "ossFileId", title = "文件id")
    private String ossFileId;

    @Schema(name = "fileOriginalName", title = "原始文件名(不包括扩展名)")
    private String fileOriginalName;

    @Schema(name = "fileFullName", title = "文件名全名称(包括扩展名)")
    private String fileFullName;

    @Schema(name = "fileOriginalFullName", title = "原始文件名全称(包括扩展名)")
    private String fileOriginalFullName;

    @Schema(name = "fileType", title = "文件类型")
    private String fileType;

    @Schema(name = "fileSize", title = "文件大小")
    private String fileSize;

    @Schema(name = "fileSizeDetail", title = "文件详细大小")
    private Long fileSizeDetail;

    @Schema(name = "ossTag", title = "oss tag信息")
    private String ossTag;

    @Schema(name = "fileDirPrefix", title = "文件存放文件夹名称")
    private String fileDirPrefix;

    @Schema(name = "fileMd5", title = "文件md5值")
    private String fileMd5;

    @Schema(name = "endpoint", title = "endpoint")
    private String endpoint;

    @Schema(name = "bucket", title = "bucket名称")
    private String bucket;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "createUserId", title = "创建用户id")
    private String createUserId;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;
}