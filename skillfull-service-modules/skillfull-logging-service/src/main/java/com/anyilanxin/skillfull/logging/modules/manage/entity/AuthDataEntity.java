// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.logging.modules.manage.entity;

import com.anyilanxin.skillfull.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 授权日志(AuthData)Entity
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-08-13 10:24:40
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName("logging_auth_data")
public class AuthDataEntity extends BaseEntity {
    private static final long serialVersionUID = 515180726702392212L;

    @TableId
    private String authLogId;

    /**
     * 日志编号
     */
    private String logCode;

    /**
     * 请求ip
     */
    private String requestIp;

    /**
     * 授权类型，具体参考常量字典AuthorizedGrantTypes
     */
    private String authType;

    /**
     * 授权类型描述，具体参考常量字典AuthorizedGrantTypes
     */
    private String authTypeDescribe;

    /**
     * 授权用户id
     */
    private String authUserId;

    /**
     * 授权用户名称
     */
    private String authUserName;

    /**
     * 授权客户端编号
     */
    private String authClientCode;

    /**
     * 授权客户端名称
     */
    private String authClientName;

    /**
     * 授权状态：0-失败,1-成功
     */
    private Integer authStatus;

    /**
     * 日志内容
     */
    private String logData;

    /**
     * 日志其余内容
     */
    private String logOtherData;

    /**
     * 异常消息
     */
    private String exceptionMessage;

    /**
     * 请求开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime requestStartTime;

    /**
     * 耗时
     */
    private Long costTime;

    /**
     * 请求结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime requestEndTime;

    /**
     * 备注
     */
    private String remark;
}
