/*
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   8.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   9.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.database.datasource.base.entity;

import static com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Entity基类
 *
 * @author 安一老厨
 * @date 2020-06-22 14:59
 * @since 1.0.0
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
