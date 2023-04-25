/**
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package com.anyilanxin.skillfull.logging.modules.manage.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 操作日志查询Response
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-08-13 10:24:41
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class OperateDto implements Serializable {
    private static final long serialVersionUID = -46766321203174323L;

    @Schema(name = "operateId", title = "操作日志id")
    private String operateId;

    @Schema(name = "operateType", title = " 操作类型（1查询，2添加，3修改，4删除，5其他）具体与常量字典OperateType一致")
    private Integer operateType;

    @Schema(name = "userId", title = "操作人用户id")
    private String userId;

    @Schema(name = "userName", title = "操作人用户名称")
    private String userName;

    @Schema(name = "requestClientCode", title = "请求客户端编号")
    private String requestClientCode;

    @Schema(name = "requestClientName", title = "请求客户端名称")
    private String requestClientName;

    @Schema(name = "logCode", title = "日志编号")
    private String logCode;

    @Schema(name = "requestIp", title = "请求ip")
    private String requestIp;

    @Schema(name = "targetServiceCode", title = "目标服务")
    private String targetServiceCode;

    @Schema(name = "targetUrl", title = "目标地址")
    private String targetUrl;

    @Schema(name = "requestUrl", title = "请求路径")
    private String requestUrl;

    @Schema(name = "requestMethod", title = "请求方法")
    private String requestMethod;

    @Schema(name = "operateStatus", title = "操作状态：0-失败,1-成功")
    private Integer operateStatus;

    @Schema(name = "dataSources", title = "数据来源")
    private String dataSources;

    @Schema(name = "dataSourcesDescribe", title = "数据来源说明")
    private String dataSourcesDescribe;

    @Schema(name = "costTime", title = "耗时")
    private Long costTime;

    @Schema(name = "requestStartTime", title = "请求开始时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime requestStartTime;

    @Schema(name = "requestEndTime", title = "请求结束时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime requestEndTime;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;

    @Schema(name = "remark", title = "备注")
    private String remark;
}
