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

package com.anyilanxin.anyicloud.corecommon.constant;

import com.anyilanxin.anyicloud.corecommon.utils.AnYiI18nUtil;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 * 通用状态码
 *
 * @author zxh
 * @date 2020-06-22 16:35
 * @since 1.0.0
 */
@Getter
@ToString
public enum AnYiResultStatus {
    // ------------------------成功----------------
    /**
     * 操作成功！
     */
    SUCCESS(0, AnYiI18nUtil.get("AnYiResultStatus.SUCCESS"), HttpStatus.OK),

    /**
     * 退出成功！
     */
    LOGOUT(0, AnYiI18nUtil.get("AnYiResultStatus.LOGOUT"), HttpStatus.OK),

    // ------------------------需要重新登录----------------

    /**
     * 未授权
     */
    TOKEN_EXPIRED(4001, AnYiI18nUtil.get("AnYiResultStatus.TOKEN_EXPIRED"), HttpStatus.UNAUTHORIZED),

    /**
     * 被提下线
     */
    TOKEN_KICKED_OUT(4002, AnYiI18nUtil.get("AnYiResultStatus.TOKEN_KICKED_OUT"), HttpStatus.UNAUTHORIZED),

    /**
     * 禁止访问,权限不足
     */
    TOKEN_LOGIN_ELSEWHERE(4003, AnYiI18nUtil.get("AnYiResultStatus.TOKEN_LOGIN_ELSEWHERE"), HttpStatus.FORBIDDEN),

    // ------------------------操作异常------------------
    /**
     * 操作异常！
     */
    ERROR(5000, AnYiI18nUtil.get("AnYiResultStatus.ERROR"), HttpStatus.INTERNAL_SERVER_ERROR),

    /**
     * 暂无权限访问！
     */
    ACCESS_DENIED(4003, AnYiI18nUtil.get("AnYiResultStatus.ACCESS_DENIED"), HttpStatus.FORBIDDEN),

    /**
     * 授权异常！
     */
    ACCESS_ERROR(4001, AnYiI18nUtil.get("AnYiResultStatus.ACCESS_ERROR"), HttpStatus.UNAUTHORIZED),

    /**
     * 授权异常！
     */
    ACCESS_INFO_ERROR(4012, AnYiI18nUtil.get("AnYiResultStatus.ACCESS_ERROR"), HttpStatus.PRECONDITION_FAILED),

    /**
     * 请求不存在！
     */
    REQUEST_NOT_FOUND(4004, AnYiI18nUtil.get("AnYiResultStatus.REQUEST_NOT_FOUND"), HttpStatus.NOT_FOUND),

    /**
     * 数据库操作失败
     */
    DATABASE_BASE_ERROR(5000, AnYiI18nUtil.get("AnYiResultStatus.DATABASE_BASE_ERROR"), HttpStatus.INTERNAL_SERVER_ERROR),

    /**
     * 验证失败
     */
    VERIFICATION_FAILED(5000, AnYiI18nUtil.get("AnYiResultStatus.VERIFICATION_FAILED"), HttpStatus.INTERNAL_SERVER_ERROR),

    /**
     * 需要刷新
     */
    NEED_REFRESH(4006, AnYiI18nUtil.get("AnYiResultStatus.NEED_REFRESH"), HttpStatus.NOT_ACCEPTABLE),

    /**
     * 调用第三方接口失败
     */
    API_ERROR(5003, AnYiI18nUtil.get("AnYiResultStatus.API_ERROR"), HttpStatus.SERVICE_UNAVAILABLE);

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 返回信息
     */
    private final String message;

    /**
     * http状态码
     */
    private final HttpStatus status;

    AnYiResultStatus(Integer code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
