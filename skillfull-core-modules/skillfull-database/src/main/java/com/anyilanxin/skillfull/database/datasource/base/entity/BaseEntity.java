package com.anyilanxin.skillfull.database.datasource.base.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

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
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = -7242240142513530183L;

    /**
     * 创建人用户id
     */
    @TableField(fill = FieldFill.INSERT)
    private String createUserId;

    /**
     * 创建用户姓名
     */
    @TableField(fill = FieldFill.INSERT)
    private String createUserName;

    /**
     * 创建日期
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;

    /**
     * 创建区域编码
     */
    @TableField(fill = FieldFill.INSERT)
    private String createAreaCode;

    /**
     * 创建职位编码
     */
    @TableField(fill = FieldFill.INSERT)
    private String createPositionCode;

    /**
     * 创建机构系统编码
     */
    @TableField(fill = FieldFill.INSERT)
    private String createOrgSysCode;

    /**
     * 创建系统编码
     */
    @TableField(fill = FieldFill.INSERT)
    private String createSystemCode;

    /**
     * 创建租户id
     */
    @TableField(fill = FieldFill.INSERT)
    private String createTenantId;

    /**
     * 更新人用户id
     */
    @TableField(fill = FieldFill.UPDATE)
    private String updateUserId;

    /**
     * 更新用户姓名
     */
    @TableField(fill = FieldFill.UPDATE)
    private String updateUserName;

    /**
     * 更新日期
     */
    @TableField(fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime updateTime;

    /**
     * 删除标识0-正常,1-已删除
     */
    @TableLogic
    private Integer delFlag;
}
