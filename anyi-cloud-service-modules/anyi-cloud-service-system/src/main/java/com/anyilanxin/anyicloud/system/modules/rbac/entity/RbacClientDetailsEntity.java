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
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */
package com.anyilanxin.anyicloud.system.modules.rbac.entity;

import static com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

import com.anyilanxin.anyicloud.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 授权客户端信息(RbacClientDetails)Entity
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:21
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName(value = "sys_rbac_client_details", autoResultMap = true)
public class RbacClientDetailsEntity extends BaseEntity {
    private static final long serialVersionUID = -42956852623383270L;

    @TableId
    private String clientDetailId;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 客户端名称
     */
    private String clientName;

    /**
     * 客户端图标
     */
    private String clientIco;

    /**
     * 客户端密码
     */
    private String clientSecurity;

    /**
     * 限制授权资源：0-不限制，1-限制。默认1
     */
    private Integer limitResource;

    /**
     * 授权资源ids,json array，json array
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private List<String> resourceIds;

    /**
     * 是否验签:0-不验签，1-验签，默认1
     */
    private Integer signatureRequired;

    /**
     * 数据签名key，当需要验签时必填
     */
    private String signatureKey;

    /**
     * 允许授权类型，来源与授权中心常量字典AuthorizedGrantType,json array
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private List<String> authorizedGrantTypes;

    /**
     * 是否领域，0-不是,1-是。默认0
     */
    private Integer havaScoped;

    /**
     * 领域,json array
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private List<String> scopes;

    /**
     * 允许登录端点,json array
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private List<String> endpoints;

    /**
     * 是否单设备登录：0-不是,1-是，默认0
     */
    private Integer singleLogin;

    /**
     * 单设备登录方式：1-同一用户只能在一个endpoint登录,2-同一用户可以在不同endpoint登录
     */
    private Integer singleLoginType;

    /**
     * 授权后跳转的URI（授权码模式必填）
     */
    private String webRegisteredRedirectUri;

    /**
     * 授权权限,json array
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private Object authorityInfos;

    /**
     * 是否内部系统：0-不是，1-是，默认0
     */
    private Integer innerSystem;

    /**
     * 上次授权时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime lastAuthTime;

    /**
     * 限制授权错误次数:0-不限制,1-限制。默认0
     */
    private Integer limitError;

    /**
     * 允许最大授权错误次数，当限制授权错误时必填
     */
    private Integer maxErrorNum;

    /**
     * 状态：0-未启用,1-启用，2-锁定，默认0
     */
    private Integer clientStatus;

    /**
     * 是否自动批准：0-不自动，1-自动,默认0
     */
    private Integer havaAutoApprove;

    /**
     * 访问token的有效时长(单位s)，默认1800秒
     */
    private Integer accessTokenValiditySeconds;

    /**
     * 刷新token的有效时长(单位s)，默认604800秒,即7天
     */
    private Integer refreshTokenValiditySeconds;

    /**
     * 授权码有效时常(单位s)，默认300秒
     */
    private Integer codeValiditySeconds;

    /**
     * 扩展信息,json object
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private Map<String, Object> additionalInformation;

    /**
     * 唯一索引帮助字段,默认1，如果删除该值为主键
     */
    private String uniqueHelp;

    /**
     * 备注
     */
    private String remark;
}
