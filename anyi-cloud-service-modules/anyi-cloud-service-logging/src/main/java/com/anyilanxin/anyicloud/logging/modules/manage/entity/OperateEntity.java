/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.logging.modules.manage.entity;

import com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant;
import com.anyilanxin.anyicloud.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 操作日志(Operate)Entity
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-08-30 15:38:13
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName("logging_operate")
public class OperateEntity extends BaseEntity {
    @Serial
    private static final long serialVersionUID = -75032281114473339L;

    @TableId
    private String operateId;

    /**
     * 操作类型（1查询，2添加，3修改，4删除，5其他，默认5）具体与常量字典OperateType一致
     */
    private Integer operateType;

    /**
     * 操作人用户id
     */
    private String userId;

    /**
     * 操作人用户名称
     */
    private String userName;

    /**
     * 请求客户端编号
     */
    private String requestClientCode;

    /**
     * 请求客户端名称
     */
    private String requestClientName;

    /**
     * 日志编号
     */
    private String logCode;

    /**
     * 请求ip
     */
    private String requestIp;

    /**
     * 请求ip属地
     */
    private String ipAddress;

    /**
     * 目标服务
     */
    private String targetServiceCode;

    /**
     * 目标地址
     */
    private String targetUrl;

    /**
     * 请求路径
     */
    private String requestUrl;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 请求结果
     */
    private String requestResult;

    /**
     * 日志其余内容
     */
    private String logOtherData;

    /**
     * 异常消息
     */
    private String exceptionMessage;

    /**
     * 操作状态：0-失败,1-成功
     */
    private Integer operateStatus;

    /**
     * 数据来源
     */
    private String dataSources;

    /**
     * 数据来源说明
     */
    private String dataSourcesDescribe;

    /**
     * 耗时
     */
    private Long costTime;

    /**
     * 请求开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = CommonCoreConstant.TIME_ZONE_GMT8)
    private LocalDateTime requestStartTime;

    /**
     * 请求结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = CommonCoreConstant.TIME_ZONE_GMT8)
    private LocalDateTime requestEndTime;

    /**
     * 创建职位编码
     */
    private String createPositionCode;

    /**
     * 创建机构系统编码
     */
    private String createOrgSysCode;

    /**
     * 创建系统编码
     */
    private String createSystemCode;

    /**
     * 创建用户id
     */
    private String createUserId;

    /**
     * 创建租户id
     */
    private String createTenantId;

    /**
     * 创建用户姓名
     */
    private String createUserName;

    /**
     * 更新用户id
     */
    private String updateUserId;

    /**
     * 更新用户姓名
     */
    private String updateUserName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 请求contentType
     */
    private String contentType;

    /**
     * 操作token
     */
    private String operateToken;

    /**
     * 格式化后耗时
     */
    private String costTimeStr;
}
