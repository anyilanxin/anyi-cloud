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

import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
* 通用状态码
*
* @author zxiaozhou
* @date 2020-06-22 16:35
* @since JDK11
*/
@Getter
@ToString
public enum Status {
    // ------------------------成功----------------
    /** 操作成功！ */
    SUCCESS(0, I18nUtil.get("Status.SUCCESS"), HttpStatus.OK),

    /** 退出成功！ */
    LOGOUT(0, I18nUtil.get("Status.LOGOUT"), HttpStatus.OK),

    // ------------------------需要重新登录----------------

    /** 登录过期 */
    TOKEN_EXPIRED(4001, I18nUtil.get("Status.TOKEN_EXPIRED"), HttpStatus.UNAUTHORIZED),

    /** 被提下线 */
    TOKEN_KICKED_OUT(4002, I18nUtil.get("Status.TOKEN_KICKED_OUT"), HttpStatus.UNAUTHORIZED),

    /** 其他地方登录下线 */
    TOKEN_LOGIN_ELSEWHERE(
            4003, I18nUtil.get("Status.TOKEN_LOGIN_ELSEWHERE"), HttpStatus.UNAUTHORIZED),

    // ------------------------操作异常------------------
    /** 操作异常！ */
    ERROR(5000, I18nUtil.get("Status.ERROR"), HttpStatus.INTERNAL_SERVER_ERROR),

    /** 暂无权限访问！ */
    ACCESS_DENIED(4003, I18nUtil.get("Status.ACCESS_DENIED"), HttpStatus.FORBIDDEN),

    /** 授权异常！ */
    ACCESS_ERROR(4001, I18nUtil.get("Status.ACCESS_ERROR"), HttpStatus.UNAUTHORIZED),

    /** 授权异常！ */
    ACCESS_INFO_ERROR(4012, I18nUtil.get("Status.ACCESS_ERROR"), HttpStatus.PRECONDITION_FAILED),

    /** 请求不存在！ */
    REQUEST_NOT_FOUND(4004, I18nUtil.get("Status.REQUEST_NOT_FOUND"), HttpStatus.NOT_FOUND),

    /** 数据库操作失败 */
    DATABASE_BASE_ERROR(
            5000, I18nUtil.get("Status.DATABASE_BASE_ERROR"), HttpStatus.INTERNAL_SERVER_ERROR),

    /** 验证失败 */
    VERIFICATION_FAILED(
            5000, I18nUtil.get("Status.VERIFICATION_FAILED"), HttpStatus.INTERNAL_SERVER_ERROR),

    /** 需要刷新 */
    NEED_REFRESH(4006, I18nUtil.get("Status.NEED_REFRESH"), HttpStatus.NOT_ACCEPTABLE),

    /** 调用第三方接口失败 */
    API_ERROR(5003, I18nUtil.get("Status.API_ERROR"), HttpStatus.SERVICE_UNAVAILABLE);

    /** 状态码 */
    private final Integer code;

    /** 返回信息 */
    private final String message;

    /** http状态码 */
    private final HttpStatus status;

    Status(Integer code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
