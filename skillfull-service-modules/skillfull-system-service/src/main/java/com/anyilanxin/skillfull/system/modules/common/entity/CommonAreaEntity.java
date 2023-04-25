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


package com.anyilanxin.skillfull.system.modules.common.entity;

import com.anyilanxin.skillfull.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 区域表(CommonArea)Entity
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-04-09 11:59:50
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName("sys_common_area")
public class CommonAreaEntity extends BaseEntity {
    private static final long serialVersionUID = -25020041841128565L;

    @TableId
    private String areaId;

    /**
     * 所属省级id
     */
    private String provinceId;

    /**
     * 中文简称
     */
    private String simpleName;

    /**
     * 区域级别:1为省级，2为市级，3为县级
     */
    private Integer areaLevel;

    /**
     * 区域名称
     */
    private String areaName;

    /**
     * 区号
     */
    private String areaCode;

    /**
     * 所属城市id
     */
    private String cityId;

    /**
     * 上级区域id
     */
    private String parentId;

    /**
     * 本区域经度
     */
    private String lon;

    /**
     * 本区域纬度
     */
    private String lat;

    /**
     * 邮编
     */
    private String zipCode;

    /**
     * 完整名称
     */
    private String wholeName;

    /**
     * 区域名称拼音的第一个字母
     */
    private String prePinYin;

    /**
     * 名称全拼
     */
    private String pinYin;

    /**
     * 首字母简拼
     */
    private String simplePy;

    /**
     * 区县id
     */
    private String countyId;

    /**
     * 备注
     */
    private String remark;
}
