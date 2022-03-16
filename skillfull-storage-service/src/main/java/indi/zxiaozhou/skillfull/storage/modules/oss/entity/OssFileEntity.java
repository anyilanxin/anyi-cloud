// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.storage.modules.oss.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import indi.zxiaozhou.skillfull.coredatabase.base.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * oss文件(OssFile)Entity
 *
 * @author zxiaozhou
 * @date 2021-02-12 20:21:10
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("storage_oss_file")
public class OssFileEntity extends BaseEntity {
    private static final long serialVersionUID = -91555873637884425L;

    @TableId
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

    @Schema(name = "createAreaCode", title = "创建区域编码")
    private String createAreaCode;

    @Schema(name = "createPositionCode", title = "创建职位编码")
    private String createPositionCode;

    @Schema(name = "createOrgSysCode", title = "创建机构系统编码")
    private String createOrgSysCode;

    @Schema(name = "createSystemCode", title = "创建系统编码")
    private String createSystemCode;

    @Schema(name = "createTenantId", title = "创建租户id")
    private String createTenantId;
}