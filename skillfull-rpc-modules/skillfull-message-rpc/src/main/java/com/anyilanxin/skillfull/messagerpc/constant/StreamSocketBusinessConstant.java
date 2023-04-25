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

package com.anyilanxin.skillfull.messagerpc.constant;

/**
 * stream与socket消息类型
 *
 * @author zxiaozhou
 * @date 2020-09-12 10:52
 * @since JDK11
 */
public interface StreamSocketBusinessConstant {

  /** 流程消息 */
  String PROCESS_MSG = "PROCESS_MSG";

  /** 授权消息 */
  String AUTH_MSG = "AUTH_MSG";

  /** 异常消息 */
  String ERROR_MSG = "ERROR_MSG";

  /** 系统消息 */
  String SYSTEM_NOTICE = "SYSTEM_NOTICE";

  /** 业务消息 */
  String BUSINESS_MSG = "BUSINESS_MSG";

  /** 聊天消息 */
  String CHART_MSG = "CHART_MSG";

  /** 聊天消息stream广播(消息服务内部使用) */
  String CHART_RADIO_MSG = "CHART_RADIO_MSG";
}
