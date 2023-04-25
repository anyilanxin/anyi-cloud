/**
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package com.anyilanxin.skillfull.auth.modules.login.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.anyilanxin.skillfull.auth.modules.login.service.IAuthCodeService;
import com.anyilanxin.skillfull.auth.modules.login.service.IUserAuthService;
import com.anyilanxin.skillfull.auth.oauth2.validate.IValidate;
import com.anyilanxin.skillfull.auth.oauth2.validate.ValidateDto;
import com.anyilanxin.skillfull.auth.oauth2.validate.impl.PictureValidate;
import com.anyilanxin.skillfull.auth.oauth2.validate.impl.SmsValidate;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 授权相关
 *
 * @author zxiaozhou
 * @date 2022-02-19 10:12
 * @since JDK1.8
 */
@Service
@RequiredArgsConstructor
public class AuthCodeServiceImpl implements IAuthCodeService {

    private IValidate pictureValidate;
    private final IUserAuthService userAuthService;
    private IValidate smsValidate;

    @Autowired
    public void setPictureValidate(PictureValidate validate) {
        this.pictureValidate = validate;
    }

    @Autowired
    public void setSmsPictureValidate(SmsValidate validate) {
        this.smsValidate = validate;
    }


    @Override
    public ValidateDto getPictureCode(HttpServletRequest request) throws RuntimeException {
        ValidateDto verification = pictureValidate.getVerification(null, request);
        if (!verification.isStatus()) {
            throw new ResponseException(verification.getMsg());
        }
        return verification;
    }

    @Override
    public void getPhoneSmsCode(String phone, HttpServletRequest request) throws RuntimeException {
        userAuthService.getUserByPhone(phone);
        // 发送验证码
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(SmsValidate.PHONE, phone);
        smsValidate.getVerification(jsonObject, request);
    }
}
