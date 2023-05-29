

package com.anyilanxin.anyicloud.auth.modules.login.service;

import com.anyilanxin.anyicloud.auth.oauth2.validate.ValidateDto;

import javax.servlet.http.HttpServletRequest;

/**
 * 授权相关
 *
 * @author zxh
 * @date 2022-02-19 09:26
 * @since 1.0.0
 */
public interface IAuthCodeService {

    /**
     * 获取图片验证码
     *
     * @param request ${@link HttpServletRequest} HttpServletRequest
     * @return ValidateDto ${@link ValidateDto}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2020-06-30 15:47
     */
    ValidateDto getPictureCode(HttpServletRequest request) throws RuntimeException;


    /**
     * 获取手机验证码(会验证手机是否存在)
     *
     * @param phone   ${@link String} 手机号码
     * @param request ${@link HttpServletRequest}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-01-13 10:37
     */
    void getPhoneSmsCode(String phone, HttpServletRequest request) throws RuntimeException;
}
