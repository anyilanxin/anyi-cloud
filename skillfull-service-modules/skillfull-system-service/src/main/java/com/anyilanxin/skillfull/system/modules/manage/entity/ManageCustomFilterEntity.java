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
 * 自定义过滤器(ManageCustomFilter)Entity
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
@TableName(value = "sys_manage_custom_filter", autoResultMap = true)
public class ManageCustomFilterEntity extends BaseEntity {
    private static final long serialVersionUID = 527999192551027560L;

    @TableId
    private String customFilterId;

    /**
     * 服务id
     */
    private String serviceId;

    /**
     * 过滤器名称
     */
    private String filterName;

    /**
     * 过滤器类型名称
     */
    private String filterTypeName;

    /**
     * 过滤器类型
     */
    private String filterType;

    /**
     * 过滤器状态:0-禁用,1-启用，默认0
     */
    private Integer filterStatus;

    /**
     * 过滤器规则:Map
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private Map<String, String> rules;

    /**
     * 是否有特殊url:0-没有,1-有。默认0
     */
    private Integer haveSpecial;

    /**
     * 是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)
     */
    private Integer enableDelete;

    /**
     * 备注
     */
    private String remark;
}
