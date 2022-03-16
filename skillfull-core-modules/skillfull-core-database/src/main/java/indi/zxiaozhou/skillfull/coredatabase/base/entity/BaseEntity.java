// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.coredatabase.base.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

import static indi.zxiaozhou.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * Entity基类
 *
 * @author zxiaozhou
 * @date 2020-06-22 14:59
 * @since JDK11
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = -7242240142513530183L;

    @Schema(name = "createUserId", title = "创建人用户id")
    @TableField(fill = FieldFill.INSERT)
    private String createUserId;

    @Schema(name = "createUserName", title = "创建用户姓名")
    @TableField(fill = FieldFill.INSERT)
    private String createUserName;

    @Schema(name = "createTime", title = "创建日期")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;

    @Schema(name = "updateUserId", title = "更新人用户id")
    @TableField(fill = FieldFill.UPDATE)
    private String updateUserId;

    @Schema(name = "updateUserName", title = "更新用户姓名")
    @TableField(fill = FieldFill.UPDATE)
    private String updateUserName;

    @Schema(name = "updateTime", title = "更新日期")
    @TableField(fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime updateTime;

    @Schema(name = "delFlag", title = "删除标识0-正常,1-已删除")
    @TableLogic
    private Integer delFlag;
}
