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
package com.anyilanxin.skillfull.system.modules.manage.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
* 路由特殊地址条件查询Request
*
* @author zxiaozhou
* @copyright zxiaozhou（https://skillfull.divisu.com）
* @date 2021-12-19 09:34:50
* @since JDK1.8
*/
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class ManageSpecialUrlQueryVo implements Serializable {
    private static final long serialVersionUID = -56226471417588073L;

    @Schema(name = "specialUrlId", title = "特殊过滤器id")
    private String specialUrlId;

    @Schema(name = "urlName", title = "接口名称")
    private String urlName;

    @Schema(name = "urlDescribe", title = "接口描述")
    private String urlDescribe;

    @Schema(name = "urlType", title = "地址类型:0-系统,1-自定义,默认0")
    private Integer urlType;

    @Schema(name = "specialStatus", title = "特殊地址状态:0-禁用,1-启用，默认0")
    private Integer specialStatus;

    @Schema(name = "limitMethod", title = "限制请求方法：0-不限制,1-限制")
    private Integer limitMethod;

    @Schema(name = "requestMethod", title = "允许的请求方法,多个英文逗号隔开")
    private String requestMethod;

    @Schema(name = "url", title = "地址,服务地址或http地址")
    private String url;

    @Schema(name = "enableDelete", title = "是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)")
    private Integer enableDelete;

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

    @Schema(name = "createUserId", title = "创建用户id")
    private String createUserId;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTenantId", title = "创建租户id")
    private String createTenantId;

    @Schema(name = "createTime", title = "创建时间")
    private LocalDateTime createTime;

    @Schema(name = "updateUserId", title = "更新用户id")
    private String updateUserId;

    @Schema(name = "updateUserName", title = "更新用户姓名")
    private String updateUserName;

    @Schema(name = "updateTime", title = "更新时间")
    private LocalDateTime updateTime;

    @Schema(name = "delFlag", title = "删除状态:0-正常,1-已删除,默认0")
    private Integer delFlag;

    @Schema(name = "customFilterId", title = "自定义过滤器id")
    private String customFilterId;
}
