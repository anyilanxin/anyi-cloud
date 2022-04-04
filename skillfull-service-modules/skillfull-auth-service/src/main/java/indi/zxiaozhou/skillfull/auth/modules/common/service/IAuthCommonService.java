// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.common.service;

import indi.zxiaozhou.skillfull.auth.security.login.controller.vo.SmsCodeVo;
import indi.zxiaozhou.skillfull.auth.security.validate.ValidateDto;

import javax.servlet.http.HttpServletRequest;

/**
 * 权限系统公共service接口
 *
 * @author zxiaozhou
 * @date 2020-11-02 10:26
 * @since JDK11
 */
public interface IAuthCommonService {
    /**
     * 刷新系统基础信息
     *
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-10-08 14:34
     */
    void refreshSystemBaseInfo() throws RuntimeException;


    /**
     * 新手机号码获取验证码(会验证手机是否重复)
     *
     * @param phone   ${@link String} 手机号码
     * @param request ${@link HttpServletRequest}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-13 10:37
     */
    void getNewPhoneSmsCode(String phone, HttpServletRequest request) throws RuntimeException;


    /**
     * 旧手机号码获取验证码(会验证手机号码是否绑定)
     *
     * @param phone   ${@link String} 手机号码
     * @param request ${@link HttpServletRequest}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-13 10:37
     */
    void getOldPhoneSmsCode(String phone, HttpServletRequest request) throws RuntimeException;


    /**
     * 旧手机号码获取验证码(会验证手机号码是否绑定,同时验证手机号是否与当前登录用户一致)
     *
     * @param phone   ${@link String} 手机号码
     * @param request ${@link HttpServletRequest}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-13 10:37
     */
    void getOldPhoneSmsCodeAndAuth(String phone, HttpServletRequest request) throws RuntimeException;


    /**
     * 新手机号码获取验证码(会验证手机是否重复)
     *
     * @param smsCodeVo ${@link SmsCodeVo} 图片验证码信息
     * @param request   ${@link HttpServletRequest}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-13 10:37
     */
    void getNewPhoneSmsCode(SmsCodeVo smsCodeVo, HttpServletRequest request) throws RuntimeException;


    /**
     * 旧手机号码获取验证码(会验证手机号码是否绑定)
     *
     * @param smsCodeVo ${@link SmsCodeVo} 图片验证码信息
     * @param request   ${@link HttpServletRequest}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-13 10:37
     */
    void getOldPhoneSmsCode(SmsCodeVo smsCodeVo, HttpServletRequest request) throws RuntimeException;


    /**
     * 旧手机号码获取验证码(会验证手机号码是否绑定,同时验证手机号是否与当前登录用户一致)
     *
     * @param smsCodeVo ${@link SmsCodeVo} 图片验证码信息
     * @param request   ${@link HttpServletRequest}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-13 10:37
     */
    void getOldPhoneSmsCodeAndAuth(SmsCodeVo smsCodeVo, HttpServletRequest request) throws RuntimeException;


    /**
     * 获取图片验证码
     *
     * @param request ${@link HttpServletRequest} HttpServletRequest
     * @return ValidateDto ${@link ValidateDto}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-06-30 15:47
     */
    ValidateDto getPictureCode(HttpServletRequest request) throws RuntimeException;
}
