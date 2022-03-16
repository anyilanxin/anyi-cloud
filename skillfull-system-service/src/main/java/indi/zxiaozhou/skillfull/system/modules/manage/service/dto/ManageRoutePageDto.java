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
 * 路由分页查询Response
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2021-12-19 00:22:16
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class ManageRoutePageDto implements Serializable {
    private static final long serialVersionUID = 233482884729875689L;

    @Schema(name = "routeId", title = "路由id")
    private String routeId;

    @Schema(name = "routeCode", title = "路由编码(唯一)")
    private String routeCode;

    @Schema(name = "serviceId", title = "服务id")
    private String serviceId;

    @Schema(name = "routeName", title = "路由名称")
    private String routeName;

    @Schema(name = "url", title = "路由url地址,当选择非负载均衡器时必填")
    private String url;

    @Schema(name = "isLoadBalancer", title = "是否负载均衡器:0-不是,1-是，默认0。选择均衡器时监听信息才可以使用,同时该字段与路由对应")
    private Integer isLoadBalancer;

    @Schema(name = "loadBalancerType", title = "负载均衡器类型:0-lb,1-lb:ws,2-lb:wss,来自常量字典:gateway-service:LbType")
    private String loadBalancerType;

    @Schema(name = "metadataJson", title = "路由元数据,数据库json存储,入库前转为字符串")
    private String metadataJson;

    @Schema(name = "serviceName", title = "服务名,当选择负载均衡器时使用必填")
    private String serviceName;

    @Schema(name = "routeOrder", title = "路由排序,越小越靠前，默认0")
    private Integer routeOrder;

    @Schema(name = "enableDelete", title = "是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)")
    private Integer enableDelete;

    @Schema(name = "routeState", title = "路由状态:0-禁用,1-启用。默认0")
    private Integer routeState;

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
