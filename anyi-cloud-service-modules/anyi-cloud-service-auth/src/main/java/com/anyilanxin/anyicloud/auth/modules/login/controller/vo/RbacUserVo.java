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
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
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
package com.anyilanxin.anyicloud.auth.modules.login.controller.vo;

import static com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 用户表(RbacUser)Entity
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:21
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class RbacUserVo implements Serializable {
    private static final long serialVersionUID = -40613723241141610L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 开放id
     */
    private String openId;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码盐
     */
    private String salt;

    /**
     * 个人简介
     */
    private String shortProfile;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = TIME_ZONE_GMT8)
    private LocalDate birthday;

    /**
     * 性别:0-默认未知,1-男,2-女,默认0
     */
    private Integer sex;

    /**
     * 邮件
     */
    private String email;

    /**
     * 是否初始密码:0-不是,1-是,默认1
     */
    private Integer isInitialPassword;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 来源
     */
    private String registerSource;

    /**
     * 状态:0-未激活,1-正常,2-冻结,默认1
     */
    private Integer userStatus;

    /**
     * 工号，唯一键
     */
    private String workNo;

    /**
     * 座机号
     */
    private String telephone;

    /**
     * 是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)
     */
    private Integer enableDelete;

    /**
     * 扩展信息,json object
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private Map<String, Object> additionalInformation;

    /**
     * 职位,org_id array
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private Set<String> positionIds;

    /**
     * 负责机构,org_id array
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private Set<String> managerOrgIds;

    /**
     * 连续登录错误次数
     */
    private Integer loginFailErrorNum;

    /**
     * 当前登录部门id
     */
    private String currentOrgId;

    /**
     * 最后登录IP
     */
    private String currentLoginIp;

    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime currentLoginDate;

    /**
     * 备注
     */
    private String remark;
}
