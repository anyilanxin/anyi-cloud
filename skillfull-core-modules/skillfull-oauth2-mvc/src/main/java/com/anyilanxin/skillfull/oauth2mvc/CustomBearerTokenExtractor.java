// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.oauth2mvc;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取token逻辑
 *
 * @author zxiaozhou
 * @date 2022-02-23 21:28
 * @since JDK1.8
 */
public class CustomBearerTokenExtractor extends BearerTokenExtractor {

    @Override
    public Authentication extract(HttpServletRequest request) {
        return super.extract(request);
    }

    @Override
    protected String extractToken(HttpServletRequest request) {
        return super.extractToken(request);
    }

    @Override
    protected String extractHeaderToken(HttpServletRequest request) {
        return super.extractHeaderToken(request);
    }
}
