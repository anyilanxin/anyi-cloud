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
package com.anyilanxin.skillfull.auth.oauth2.validate;

import com.alibaba.fastjson.JSONObject;
import javax.servlet.http.HttpServletRequest;

/**
* 验证
*
* @author zxiaozhou
* @date 2020-06-29 02:23
* @since JDK11
*/
public interface IValidate {
    /**
    * 获取验证码信息 redis key构建:picture:codeKey+system,sms:codeKey+system+phone
    *
    * @param parameter ${@link JSONObject} 获取验证码参数
    * @param request ${@link HttpServletRequest} HttpServletRequest
    * @return ValidateDto ${@link ValidateDto} 验证码结果
    * @author zxiaozhou
    * @date 2020-06-29 02:25
    */
    ValidateDto getVerification(JSONObject parameter, HttpServletRequest request);

    /**
    * 验证码验证
    *
    * @param parameter ${@link CheckModel} 参数
    * @return CheckDto 验证结果
    * @author zxiaozhou
    * @date 2020-06-29 02:25
    */
    CheckDto checkVerification(CheckModel parameter);
}
