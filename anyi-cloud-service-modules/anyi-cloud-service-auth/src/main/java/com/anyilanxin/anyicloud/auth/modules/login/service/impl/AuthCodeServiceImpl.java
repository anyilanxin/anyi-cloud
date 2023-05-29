

package com.anyilanxin.anyicloud.auth.modules.login.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.anyilanxin.anyicloud.auth.modules.login.service.IAuthCodeService;
import com.anyilanxin.anyicloud.auth.modules.login.service.IUserAuthService;
import com.anyilanxin.anyicloud.auth.oauth2.validate.IValidate;
import com.anyilanxin.anyicloud.auth.oauth2.validate.ValidateDto;
import com.anyilanxin.anyicloud.auth.oauth2.validate.impl.PictureValidate;
import com.anyilanxin.anyicloud.auth.oauth2.validate.impl.SmsValidate;
import com.anyilanxin.anyicloud.corecommon.exception.ResponseException;

import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 授权相关
 *
 * @author zxh
 * @date 2022-02-19 10:12
 * @since 1.0.0
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
