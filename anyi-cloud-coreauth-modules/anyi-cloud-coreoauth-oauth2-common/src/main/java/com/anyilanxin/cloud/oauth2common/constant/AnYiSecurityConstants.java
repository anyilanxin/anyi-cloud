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

package com.anyilanxin.cloud.oauth2common.constant;

/**
 * security 常量类
 *
 * @author vains
 */
public interface AnYiSecurityConstants {

    /**
     * resource_ids 信息
     */
    String RESOURCE_IDS = "resource_ids";

    /**
     * 返回数据格式
     */
    String DATA_FORMAT = "data_format";
    /**
     * 是否单设备登录
     */
    String SINGLE_LOGIN = "single_login";

    /**
     * 单设备登录方式
     */
    String SINGLE_LOGIN_TYPE = "single_login_type";

    /**
     * 限制授权资源
     */
    String LIMIT_RESOURCE = "limit_resource";

    /**
     * 授权模式
     */
    String AUTH_MODE = "auth_mode";

    /**
     * 微信登录相关参数——openid：用户唯一id
     */
    String OAUTH_LOGIN_TYPE = "loginType";

    /**
     * 微信登录相关参数——openid：用户唯一id
     */
    String TOKEN_UNIQUE_ID = "uniqueId";

    /**
     * 微信登录相关参数——openid：用户唯一id
     */
    String WECHAT_PARAMETER_OPENID = "openid";

    /**
     * 微信登录相关参数——forcePopup：强制此次授权需要用户弹窗确认
     */
    String WECHAT_PARAMETER_FORCE_POPUP = "forcePopup";

    /**
     * 微信登录相关参数——secret：微信的应用秘钥
     */
    String WECHAT_PARAMETER_SECRET = "secret";

    /**
     * 微信登录相关参数——appid：微信的应用id
     */
    String WECHAT_PARAMETER_APPID = "appid";

    /**
     * 三方登录类型——微信
     */
    String THIRD_LOGIN_WECHAT = "wechat";

    /**
     * 三方登录类型——Gitee
     */
    String THIRD_LOGIN_GITEE = "gitee";

    /**
     * 三方登录类型——Github
     */
    String THIRD_LOGIN_GITHUB = "github";

    /**
     * 随机字符串请求头名字
     */
    String NONCE_HEADER_NAME = "nonceId";

    /**
     * 登录方式入参名
     */
    String LOGIN_TYPE_NAME = "loginType";

    /**
     * 验证码id入参名
     */
    String CAPTCHA_ID_NAME = "captchaId";

    /**
     * 验证码值入参名
     */
    String CAPTCHA_CODE_NAME = "code";

    /**
     * 登录方式——短信验证码
     */
    String SMS_LOGIN_TYPE = "smsCaptcha";

    /**
     * 登录方式——账号密码登录
     */
    String PASSWORD_LOGIN_TYPE = "passwordLogin";

    /**
     * 权限在token中的key
     */
    String AUTHORITIES_KEY = "authorities";

    /**
     * 自定义 grant type —— 短信验证码
     */
    String GRANT_TYPE_SMS_CODE = "urn:ietf:params:oauth:grant-type:sms_code";

    /**
     * 自定义 grant type —— 短信验证码 —— 手机号的key
     */
    String OAUTH_PARAMETER_NAME_PHONE = "phone";

    /**
     * 自定义 grant type —— 短信验证码 —— 短信验证码的key
     */
    String OAUTH_PARAMETER_NAME_SMS_CAPTCHA = "sms_captcha";

}
