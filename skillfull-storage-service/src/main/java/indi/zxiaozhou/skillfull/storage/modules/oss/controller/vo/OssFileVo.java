// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.storage.modules.oss.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * oss文件添加或修改Request
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
public class OssFileVo implements Serializable {
    private static final long serialVersionUID = 241666211161657770L;

    @Schema(name = "fileOriginalFullName", title = "原始文件名全称(包括扩展名)")
    private String fileOriginalFullName;

    @Schema(name = "fileDirPrefix", title = "文件存放文件夹名称")
    private String fileDirPrefix;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "url", title = "文件url地址", required = true)
    @NotBlank(message = "文件url地址不能为空")
    private String url;

    @Schema(name = "callback", title = "回调,传入原路返回")
    private String callback;

    @Schema(name = "fileFolder", title = "存放文件夹")
    private String fileFolder;

}