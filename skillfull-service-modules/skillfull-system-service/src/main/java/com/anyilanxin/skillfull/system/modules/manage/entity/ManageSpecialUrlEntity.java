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
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 路由特殊地址(ManageSpecialUrl)Entity
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
@TableName("sys_manage_special_url")
public class ManageSpecialUrlEntity extends BaseEntity {
    private static final long serialVersionUID = -14556683450209175L;

    @TableId
    private String specialUrlId;

    /**
     * 自定义过滤器id
     */
    private String customFilterId;

    /**
     * 接口名称
     */
    private String urlName;

    /**
     * 接口描述
     */
    private String urlDescribe;

    /**
     * 地址类型:0-系统,1-自定义,默认0
     */
    private Integer urlType;

    /**
     * 特殊地址状态:0-禁用,1-启用，默认0
     */
    private Integer specialStatus;

    /**
     * 特殊url类型:1-白名单(放行url),2-黑名单(只处理url)
     */
    private Integer specialUrlType;

    /**
     * 限制请求方法：0-不限制,1-限制，boolean类型
     */
    private Integer limitMethod;

    /**
     * 允许的请求方法,多个英文逗号隔开
     */
    private String requestMethod;

    /**
     * 地址,服务地址或http地址
     */
    private String url;

    /**
     * 是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)
     */
    private Integer enableDelete;

    /**
     * 备注
     */
    private String remark;
}
