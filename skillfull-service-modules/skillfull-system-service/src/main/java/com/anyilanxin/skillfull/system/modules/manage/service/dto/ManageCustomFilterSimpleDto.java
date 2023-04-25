/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
package com.anyilanxin.skillfull.system.modules.manage.service.dto;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
* 自定义过滤器查询Response
*
* @author zxiaozhou
* @copyright zxiaozhou（https://skillfull.divisu.com）
* @date 2021-12-19 00:22:14
* @since JDK1.8
*/
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class ManageCustomFilterSimpleDto implements Serializable {
    private static final long serialVersionUID = -90187804208911654L;

    @Schema(name = "customFilterId", title = "自定义过滤器id")
    private String customFilterId;

    @Schema(name = "serviceId", title = "服务id")
    private String serviceId;

    @Schema(name = "filterName", title = "过滤器名称")
    private String filterName;

    @Schema(name = "filterTypeName", title = "过滤器类型名称")
    private String filterTypeName;

    @Schema(name = "filterType", title = "过滤器类型")
    private String filterType;

    @Schema(name = "filterStatus", title = "过滤器状态:0-禁用,1-启用，默认0")
    private Integer filterStatus;

    @Schema(name = "haveSpecial", title = "是否有特殊url:0-没有,1-有。默认0")
    private Integer haveSpecial;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;
}
