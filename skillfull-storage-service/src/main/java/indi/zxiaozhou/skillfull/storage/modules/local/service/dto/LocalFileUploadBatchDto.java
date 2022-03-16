// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.storage.modules.local.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import static indi.zxiaozhou.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * url文件上传结果
 *
 * @author zxiaozhou
 * @date 2020-10-24 04:19
 * @since JDK11
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class LocalFileUploadBatchDto implements Serializable {
    private static final long serialVersionUID = 7561219482591890756L;
    @Schema(name = "successList", title = "成功文件合集")
    private List<UploadSuccess> successList;

    @Schema(name = "type", title = "结果类型:0-全部成功,1-部分失败,2-全部失败")
    private int type;

    @Schema(name = "failFailList", title = "失败文件合集")
    private List<UploadFail> failFailList;

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    public static class UploadFail implements Serializable {
        private static final long serialVersionUID = -47966425117798554L;

        @Schema(name = "fileOriginalFullName", title = "原始文件名全称(包括扩展名)")
        private String fileOriginalFullName;

        @Schema(name = "fileDirPrefix", title = "文件存放文件夹名称")
        private String fileDirPrefix;

        @Schema(name = "remark", title = "备注")
        private String remark;

        @Schema(name = "url", title = "文件url地址")
        private String url;

        @Schema(name = "callback", title = "回调,传入原路返回")
        private String callback;

        @Schema(name = "failCause", title = "失败原因")
        private String failCause;
    }

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    public static class UploadSuccess implements Serializable {
        private static final long serialVersionUID = -45887591128358710L;

        @Schema(name = "localFileId", title = "文件id")
        private String localFileId;

        @Schema(name = "fileOriginalName", title = "原始文件名(不包括扩展名)")
        private String fileOriginalName;

        @Schema(name = "fileOriginalFullName", title = "原始文件名全称(包括扩展名)")
        private String fileOriginalFullName;

        @Schema(name = "fileFullName", title = "文件名全名称(包括扩展名)")
        private String fileFullName;

        @Schema(name = "fileType", title = "文件类型")
        private String fileType;

        @Schema(name = "fileSize", title = "文件大小")
        private String fileSize;

        @Schema(name = "fileSizeDetail", title = "文件详细大小")
        private Long fileSizeDetail;

        @Schema(name = "fileMd5", title = "文件md5值")
        private String fileMd5;

        @Schema(name = "fileDiskRelativePath", title = "文件磁盘相对路径(即系统定义文件文件存放磁盘开始路径除外)")
        private String fileDiskRelativePath;

        @Schema(name = "fileMappingPath", title = "文件预览相对路径(即加了file的映射路径)")
        private String fileMappingPath;

        @Schema(name = "fileDirPrefix", title = "文件存放文件夹名称")
        private String fileDirPrefix;

        @Schema(name = "remark", title = "备注")
        private String remark;

        @Schema(name = "createUserName", title = "创建用户姓名")
        private String createUserName;

        @Schema(name = "createTime", title = "创建时间")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = TIME_ZONE_GMT8)
        private LocalDateTime createTime;

        @Schema(name = "callback", title = "回调,传入原路返回")
        private String callback;
    }
}
