// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.rbac.entity;

import com.anyilanxin.skillfull.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 授权客户端信息(RbacClientDetails)Entity
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-05-02 16:12:21
 * @since JDK1.8
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
