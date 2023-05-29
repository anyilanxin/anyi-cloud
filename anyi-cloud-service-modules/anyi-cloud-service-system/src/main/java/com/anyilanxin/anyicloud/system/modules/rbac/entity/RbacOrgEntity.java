/*
 * Copyright (c) 2021-2023 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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

package com.anyilanxin.anyicloud.system.modules.rbac.entity;

import com.anyilanxin.anyicloud.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import java.util.Map;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 组织表(RbacOrg)Entity
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:39:44
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName(value = "sys_rbac_org", autoResultMap = true)
public class RbacOrgEntity extends BaseEntity {
    private static final long serialVersionUID = -26667015921374659L;

    @TableId
    private String orgId;

    /**
     * 父级组织id
     */
    private String parentId;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 英文名
     */
    private String orgNameEn;

    /**
     * 缩写
     */
    private String orgNameAbbr;

    /**
     * 排序
     */
    private Integer orgOrder;

    /**
     * 组织机构类型：1-公司,2-部门
     */
    private Integer orgType;

    /**
     * 组织编码
     */
    private String orgCode;

    /**
     * 组织编码(系统)
     */
    private String orgSysCode;

    /**
     * 组织状态：0-禁用，1-启用，默认0
     */
    private Integer orgStatus;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 传真
     */
    private String fax;

    /**
     * 地址
     */
    private String address;

    /**
     * 统一社会信用代码
     */
    private String socialCode;

    /**
     * 行政区域名称
     */
    private String areaCodeName;

    /**
     * 行政区域
     */
    private String areaCode;

    /**
     * 详细地址
     */
    private String detailAddress;

    /**
     * 经验范围
     */
    private String scopeBusiness;

    /**
     * 法人
     */
    private String legalPerson;

    /**
     * 开户姓名
     */
    private String accountsName;

    /**
     * 开户银行
     */
    private String accountsBank;

    /**
     * 银行账号
     */
    private String backCard;

    /**
     * 营业执照
     */
    private String businessLicensePicture;

    /**
     * 印章
     */
    private String sealPicture;

    /**
     * 扩展信息,json object
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private Map<String, Object> additionalInformation;

    /**
     * 备注
     */
    private String remark;

    /**
     * 唯一索引帮助字段,默认1，如果删除该值为主键
     */
    private String uniqueHelp;

    /**
     * 组织机构简称
     */
    private String orgSimpleName;
}
