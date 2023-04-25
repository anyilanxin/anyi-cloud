/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
package com.anyilanxin.skillfull.corecommon.constant;

/**
* stream常量
*
* @author zxiaozhou
* @date 2021-05-29 17:04
* @since JDK1.8
*/
public interface BindingStreamConstant {
    /** stream out后缀 */
    String OUT_SUFFIX = "-out-0";

    /** stream out前缀 */
    String IN_SUFFIX = "-in-0";

    /** 处理路由stream */
    String ROUTER_PROCESS = "routerProcess";

    /** 处理后台指令权限刷新 */
    String AUTH_URL_PROCESS = "authUrlProcess";

    /** 处理socket消息 */
    String SOCKET_PROCESS = "socketProcess";

    /** 流程消息处理 */
    String PROCESS_MSG_PROCESS = "processMsgProcess";

    /** 操作日志处理 */
    String OPERATE_LOG_PROCESS = "operateLogProcess";

    /** 授权日志处理 */
    String AUTH_LOG_PROCESS = "authLogProcess";

    /** 发起流程处理 */
    String SUBMIT_PROCESS = "submitProcess";

    /** 发起流程处理后响应结果 */
    String SUBMIT_PROCESS_RESULT = "submitProcessResult";

    /** 系统配置 */
    String BASE_CONFIG_PROCESS = "baseConfigProcess";

    /** 刷新网关doc */
    String REFRESH_DOC_PROCESS = "refreshDocProcess";
}
