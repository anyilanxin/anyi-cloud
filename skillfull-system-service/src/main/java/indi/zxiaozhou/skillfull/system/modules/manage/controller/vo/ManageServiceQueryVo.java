// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.system.modules.manage.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 服务管理条件查询Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2021-12-19 00:41:23
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ManageServiceQueryVo implements Serializable {
    private static final long serialVersionUID = -99898802554964691L;

    @Schema(name = "serviceId", title = "服务id")
    private String serviceId;

    @Schema(name = "serviceCode", title = "服务编码")
    private String serviceCode;

    @Schema(name = "serviceName", title = "服务名")
    private String serviceName;

    @Schema(name = "isLoadBalancer", title = "是否负载均衡器:0-不是,1-是，默认0。选择均衡器时监听信息才可以使用,同时该字段与路由对应")
    private Integer isLoadBalancer;

    @Schema(name = "enableSwagger", title = "聚合swagger:0-不聚合,1-聚合，默认0")
    private Integer enableSwagger;

    @Schema(name = "swaggerConfigUrl", title = "swagger配置地址")
    private String swaggerConfigUrl;

    @Schema(name = "subscribeChange", title = "是否监听系统变化:0-不订阅,1-订阅,默认0")
    private Integer subscribeChange;

    @Schema(name = "noticeChange", title = "是否发送变化通知:0-不通知,1-通知。默认0")
    private Integer noticeChange;

    @Schema(name = "noticeType", title = "通知类型:0-邮件,1-短信,2-微信消息，当选择监听服务变化并且通知时必填")
    private Integer noticeType;

    @Schema(name = "serviceState", title = "服务状态:0-禁用,1-启用。默认0")
    private Integer serviceState;

    @Schema(name = "serviceMetadataJson", title = "服务元数据,数据库json存储,入库前转为字符串")
    private String serviceMetadataJson;

    @Schema(name = "noticeTemplateId", title = "通知模板id，当选择监听服务变化并且通知时必填")
    private String noticeTemplateId;

    @Schema(name = "headUserName", title = "负责人姓名，当选择监听服务变化并且通知时必填")
    private String headUserName;

    @Schema(name = "headUserId", title = "负责人用户id，当选择监听服务变化并且通知时必填")
    private String headUserId;

    @Schema(name = "enableDelete", title = "是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)")
    private Integer enableDelete;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "uniqueHelp", title = "唯一索引帮助字段,默认1，如果删除该值为主键")
    private String uniqueHelp;

    @Schema(name = "createAreaCode", title = "创建区域编码")
    private String createAreaCode;

    @Schema(name = "createPositionCode", title = "创建职位编码")
    private String createPositionCode;

    @Schema(name = "createOrgSysCode", title = "创建机构系统编码")
    private String createOrgSysCode;

    @Schema(name = "createSystemCode", title = "创建系统编码")
    private String createSystemCode;

    @Schema(name = "createTenantId", title = "创建租户id")
    private String createTenantId;

    @Schema(name = "createUserId", title = "创建用户id")
    private String createUserId;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

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

}
