// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
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
