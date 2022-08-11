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
 * 服务管理(ManageService)Entity
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
@TableName(value = "sys_manage_service", autoResultMap = true)
public class ManageServiceEntity extends BaseEntity {
    private static final long serialVersionUID = 307682001232473844L;

    @TableId
    private String serviceId;

    /**
     * 服务编码
     */
    private String serviceCode;

    /**
     * 服务名
     */
    private String serviceName;

    /**
     * 是否负载均衡器:0-不是,1-是，默认0。选择均衡器时监听信息才可以使用,同时该字段与路由对应
     */
    private Integer isLoadBalancer;

    /**
     * 聚合swagger:0-不聚合,1-聚合，默认0
     */
    private Integer enableSwagger;

    /**
     * swagger配置地址
     */
    private String swaggerConfigUrl;

    /**
     * 是否监听系统变化:0-不订阅,1-订阅,默认0
     */
    private Integer subscribeChange;

    /**
     * 是否发送变化通知:0-不通知,1-通知。默认0
     */
    private Integer noticeChange;

    /**
     * 通知类型:0-邮件,1-短信,2-微信消息，当选择监听服务变化并且通知时必填
     */
    private Integer noticeType;

    /**
     * 服务状态:0-禁用,1-启用。默认0
     */
    private Integer serviceState;

    /**
     * 服务元数据,数据库json存储,入库前转为字符串
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private Map<String, String> serviceMetadataJson;

    /**
     * 通知模板id，当选择监听服务变化并且通知时必填
     */
    private String noticeTemplateId;

    /**
     * 负责人姓名，当选择监听服务变化并且通知时必填
     */
    private String headUserName;

    /**
     * 负责人用户id，当选择监听服务变化并且通知时必填
     */
    private String headUserId;

    /**
     * 是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)
     */
    private Integer enableDelete;

    /**
     * 备注
     */
    private String remark;

    /**
     * 唯一索引帮助字段,默认1，如果删除该值为主键
     */
    private String uniqueHelp;
}
