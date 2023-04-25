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
 *   1.请不要删除和修改根目录下的LICENSE文件。
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 *   3.请保留源码和相关描述文件的项目出处，作者声明等。
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 *   6.若您的项目无法满足以上几点，可申请商业授权
 */

package com.anyilanxin.skillfull.logging.modules.manage.entity;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

import com.anyilanxin.skillfull.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 授权日志(AuthData)Entity
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-08-13 10:24:40
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName("logging_auth_data")
public class AuthDataEntity extends BaseEntity {
  private static final long serialVersionUID = 515180726702392212L;

  @TableId private String authLogId;

  /** 日志编号 */
  private String logCode;

  /** 请求ip */
  private String requestIp;

  /** 授权类型，具体参考常量字典AuthorizedGrantTypes */
  private String authType;

  /** 授权类型描述，具体参考常量字典AuthorizedGrantTypes */
  private String authTypeDescribe;

  /** 授权用户id */
  private String authUserId;

  /** 授权用户名称 */
  private String authUserName;

  /** 授权客户端编号 */
  private String authClientCode;

  /** 授权客户端名称 */
  private String authClientName;

  /** 授权状态：0-失败,1-成功 */
  private Integer authStatus;

  /** 日志内容 */
  private String logData;

  /** 日志其余内容 */
  private String logOtherData;

  /** 异常消息 */
  private String exceptionMessage;

  /** 请求开始时间 */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
  private LocalDateTime requestStartTime;

  /** 耗时 */
  private Long costTime;

  /** 请求结束时间 */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
  private LocalDateTime requestEndTime;

  /** 备注 */
  private String remark;
}
