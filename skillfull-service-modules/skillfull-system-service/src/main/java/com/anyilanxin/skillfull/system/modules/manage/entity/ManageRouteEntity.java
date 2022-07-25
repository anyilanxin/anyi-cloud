// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.manage.entity;

import com.anyilanxin.skillfull.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * 路由(ManageRoute)Entity
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-04-09 12:02:48
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)


@NoArgsConstructor
@TableName(value = "sys_manage_route", autoResultMap = true)
public class ManageRouteEntity extends BaseEntity {
    private static final long serialVersionUID = -50439627452634148L;

    @TableId
    private String routeId;

    /**
     * 路由编码(唯一)
     */
    private String routeCode;

    /**
     * 服务id
     */
    private String serviceId;

    /**
     * 服务编码,当选择负载均衡器时使用必填
     */
    private String serviceCode;

    /**
     * 路由名称
     */
    private String routeName;

    /**
     * 路由url地址,当选择非负载均衡器时必填
     */
    private String url;

    /**
     * 是否负载均衡器:0-不是,1-是，默认0。选择均衡器时监听信息才可以使用,同时该字段与路由对应
     */
    private Integer isLoadBalancer;

    /**
     * 负载均衡器类型:0-lb,1-lb:ws,2-lb:wss,来自常量字典:gateway-service:LbType
     */
    private String loadBalancerType;

    /**
     * 路由元数据,数据库json存储,入库前转为字符串
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private Map<String, String> metadataJson;

    /**
     * 路由排序,越小越靠前，默认0
     */
    private Integer routeOrder;

    /**
     * 是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)
     */
    private Integer enableDelete;

    /**
     * 路由状态:0-禁用,1-启用。默认0
     */
    private Integer routeState;

    /**
     * 备注
     */
    private String remark;

    /**
     * 唯一索引帮助字段,默认1，如果删除该值为主键
     */
    private String uniqueHelp;
}
