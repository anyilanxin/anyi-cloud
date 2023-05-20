/*
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   7.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   8.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   9.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.system.modules.manage.entity;

import com.anyilanxin.anyicloud.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 路由特殊地址(ManageSpecialUrl)Entity
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