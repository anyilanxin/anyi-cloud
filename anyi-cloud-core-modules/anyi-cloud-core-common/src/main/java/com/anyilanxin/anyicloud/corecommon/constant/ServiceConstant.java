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

package com.anyilanxin.anyicloud.corecommon.constant;

/**
 * 系统服务常量
 *
 * @author zxh
 * @date 2022-02-12 21:55
 * @since 1.0.0
 */
public interface ServiceConstant {
    /**
     * 授权服务名
     */
    String AUTH_SERVICE = "auth-service";

    /**
     * 授权服务PATH
     */
    String AUTH_SERVICE_PATH = "/auth";
    /**
     * 案例服务名
     */
    String EXAMPLE_SERVICE = "example-service";

    /**
     * 案例服务PATH
     */
    String EXAMPLE_SERVICE_PATH = "/example";
    /**
     * 网关服务名
     */
    String GATEWAY_SERVICE = "gateway-service";

    /**
     * 网关服务PATH
     */
    String GATEWAY_SERVICE_PATH = "";
    /**
     * 调度服务名
     */
    String JOB_SERVICE = "job-service";

    /**
     * 调度服务PATH
     */
    String JOB_SERVICE_PATH = "/job";
    /**
     * 日志服务名
     */
    String LOGGING_SERVICE = "logging-service";

    /**
     * 日志服务PATH
     */
    String LOGGING_SERVICE_PATH = "/logging";
    /**
     * 消息服务名
     */
    String MESSAGE_SERVICE = "message-service";

    /**
     * 消息服务PATH
     */
    String MESSAGE_SERVICE_PATH = "/message";
    /**
     * 监控服务名
     */
    String MONITOR_SERVICE = "monitor-service";

    /**
     * 监控服务PATH
     */
    String MONITOR_SERVICE_PATH = "/monitor";
    /**
     * 在线开发服务名
     */
    String ONLINE_DES_SERVICE = "onlinedes-service";

    /**
     * 在线开发服务PATH
     */
    String ONLINE_DES_SERVICE_PATH = "/onlinedes";
    /**
     * 流程服务名
     */
    String PROCESS_SERVICE = "process-service";

    /**
     * 流程服务PATH
     */
    String PROCESS_SERVICE_PATH = "/process";

    /**
     * 存储服务名
     */
    String STORAGE_SERVICE = "storage-service";

    /**
     * 存储服务PATH
     */
    String STORAGE_SERVICE_PATH = "/storage";
    /**
     * 系统服务名
     */
    String SYSTEM_SERVICE = "system-service";

    /**
     * 系统服务PATH
     */
    String SYSTEM_SERVICE_PATH = "/system";

    /**
     * 工具服务名
     */
    String TOOLS_SERVICE = "tools-service";

    /**
     * 工具服务PATH
     */
    String TOOLS_SERVICE_PATH = "/tools";
}
