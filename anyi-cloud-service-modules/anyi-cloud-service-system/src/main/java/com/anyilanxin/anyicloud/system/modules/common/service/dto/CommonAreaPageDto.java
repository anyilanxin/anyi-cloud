/*
 * Copyright (c) 2021-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Zeebe EE、AnYi Cloud EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */
package com.anyilanxin.anyicloud.system.modules.common.service.dto;

import static com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

import com.anyilanxin.anyicloud.corecommon.utils.tree.model.BaseTree;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 区域表分页查询Response
 *
 * @author zxh
 * @date 2020-11-02 09:25:04
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema
public class CommonAreaPageDto extends BaseTree<CommonAreaPageDto> implements Serializable {
    private static final long serialVersionUID = 416249807570362423L;

    @Schema(name = "areaId", title = "区域id")
    private String areaId;

    @Schema(name = "parentId", title = "上级区域id")
    private String parentId;

    @Schema(name = "prePinYin", title = "区域名称拼音的第一个字母")
    private String prePinYin;

    @Schema(name = "simplePy", title = "首字母简拼")
    private String simplePy;

    @Schema(name = "pinYin", title = "区域名称拼音")
    private String pinYin;

    @Schema(name = "wholeName", title = "完整名称")
    private String wholeName;

    @Schema(name = "provinceId", title = "所属省级id")
    private String provinceId;

    @Schema(name = "simpleName", title = "中文简称")
    private String simpleName;

    @Schema(name = "areaLevel", title = "级别：1为省级，2为市级，3为县级, 4为乡, 5为村")
    private Integer areaLevel;

    @Schema(name = "areaName", title = "区域名称")
    private String areaName;

    @Schema(name = "areaCode", title = "区号")
    private String areaCode;

    @Schema(name = "cityId", title = "所属城市id")
    private String cityId;

    @Schema(name = "lon", title = "本区域经度")
    private String lon;

    @Schema(name = "lat", title = "本区域纬度")
    private String lat;

    @Schema(name = "zipCode", title = "邮编")
    private String zipCode;

    @Schema(name = "countyId", title = "区县id")
    private String countyId;

    @Schema(name = "remark", title = "备注/说明")
    private String remark;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;
}
