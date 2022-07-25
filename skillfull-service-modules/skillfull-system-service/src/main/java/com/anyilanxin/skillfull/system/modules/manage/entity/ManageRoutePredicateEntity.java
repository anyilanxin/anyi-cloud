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
 * 路由断言(ManageRoutePredicate)Entity
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
@TableName(value = "sys_manage_route_predicate", autoResultMap = true)
public class ManageRoutePredicateEntity extends BaseEntity {
    private static final long serialVersionUID = 635114207423655775L;

    @TableId
    private String predicateId;

    /**
     * 服务id
     */
    private String serviceId;

    /**
     * 路由id
     */
    private String routeId;

    /**
     * 断言类型
     */
    private String predicateType;

    /**
     * 断言类型名称
     */
    private String predicateTypeName;

    /**
     * 断言名称
     */
    private String predicateName;

    /**
     * 断言规则:Map
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private Map<String, String> rules;

    /**
     * 是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)
     */
    private Integer enableDelete;

    /**
     * 备注
     */
    private String remark;
}
