// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.security.token;

import indi.zxiaozhou.skillfull.coremvc.utils.ServletUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 处理security上下文
 *
 * @author zxiaozhou
 * @date 2020-07-17 18:08
 * @since JDK11
 */
@Slf4j
@RequiredArgsConstructor
@Component
@Order(1)
public class TokenOnceFilter extends OncePerRequestFilter {
    private final TokenStore tokenStore;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String subToken = ServletUtils.getSubToken();
        if (StringUtils.isNotBlank(subToken)) {
            LoginSuccessAuthenticationToken token = tokenStore.getAuthentication(subToken);
            if (Objects.nonNull(token)) {
                SecurityContextHolder.getContext().setAuthentication(token);

            }
        }
        filterChain.doFilter(request, response);
    }
}
