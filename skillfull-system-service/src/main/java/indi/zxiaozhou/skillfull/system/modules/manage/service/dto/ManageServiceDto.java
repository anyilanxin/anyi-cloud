// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.system.modules.manage.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 服务管理查询Response
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2021-12-19 00:22:19
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ManageServiceDto implements Serializable {
    private static final long serialVersionUID = 945013865080704392L;

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

    @Schema(name = "createUserId", title = "创建用户id")
    private String createUserId;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间")
    private LocalDateTime createTime;
}
