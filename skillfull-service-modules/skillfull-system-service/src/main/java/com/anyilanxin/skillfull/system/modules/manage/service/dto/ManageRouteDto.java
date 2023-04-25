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
 *   1.请不要删除和修改根目录下的LICENSE文件。
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 *   3.请保留源码和相关描述文件的项目出处，作者声明等。
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 *   6.若您的项目无法满足以上几点，可申请商业授权
 */

package com.anyilanxin.skillfull.system.modules.manage.service.dto;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

import com.anyilanxin.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 路由查询Response
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2021-12-19 00:22:15
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class ManageRouteDto implements Serializable {
  private static final long serialVersionUID = 580435386416324010L;

  @Schema(name = "routeId", title = "路由id")
  private String routeId;

  @Schema(name = "routeCode", title = "路由编码(唯一)")
  private String routeCode;

  @Schema(name = "serviceId", title = "服务id")
  private String serviceId;

  @Schema(name = "serviceCode", title = "服务编码,当选择负载均衡器时使用必填")
  private String serviceCode;

  @Schema(name = "routeName", title = "路由名称")
  private String routeName;

  @Schema(name = "url", title = "路由url地址,当选择非负载均衡器时必填")
  private String url;

  @Schema(name = "isLoadBalancer", title = "是否负载均衡器:0-不是,1-是，默认0。选择均衡器时监听信息才可以使用,同时该字段与路由对应")
  private Integer isLoadBalancer;

  @Schema(
      name = "loadBalancerType",
      title = "负载均衡器类型:0-lb,1-lb:ws,2-lb:wss,来自常量字典:gateway-service:LbType")
  private String loadBalancerType;

  @Schema(name = "metadataJson", title = "路由元数据,数据库json存储,入库前转为字符串")
  private Map<String, String> metadataJson;

  @Schema(name = "routeOrder", title = "路由排序,越小越靠前，默认0")
  private Integer routeOrder;

  @Schema(name = "enableDelete", title = "是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)")
  private Integer enableDelete;

  @Schema(name = "routeState", title = "路由状态:0-禁用,1-启用。默认0")
  private Integer routeState;

  @Schema(name = "remark", title = "备注")
  private String remark;

  @Schema(name = "createUserId", title = "创建用户id")
  private String createUserId;

  @Schema(name = "createUserName", title = "创建用户姓名")
  private String createUserName;

  @Schema(name = "createTime", title = "创建时间")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = TIME_ZONE_GMT8)
  private LocalDateTime createTime;

  @Schema(name = "routeFilters", title = "过滤器")
  private List<ManageRouteFilterDto> routeFilters;

  @Schema(name = "routePredicates", title = "断言", required = true)
  @NotBlankOrNull(message = "断言不能为空")
  @Valid
  private List<ManageRoutePredicateDto> routePredicates;

  @Schema(name = "customFilterIds", title = "自定义过滤器ids")
  @Builder.Default
  private List<String> customFilterIds = Collections.emptyList();

  @Schema(name = "customFilters", title = "自定义过滤器")
  private List<ManageCustomFilterSimpleDto> customFilters;
}
