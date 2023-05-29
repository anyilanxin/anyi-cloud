/*
 * Copyright (c) 2021-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Zeebe EE、AnYi Cloud EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */
package com.anyilanxin.anyicloud.system.modules.manage.entity;

import com.anyilanxin.anyicloud.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import java.util.Map;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 服务管理(ManageService)Entity
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-04-09 12:02:48
 * @since 1.0.0
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
