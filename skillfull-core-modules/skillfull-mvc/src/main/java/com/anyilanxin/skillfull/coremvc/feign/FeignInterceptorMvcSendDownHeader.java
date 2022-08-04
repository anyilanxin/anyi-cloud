// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.coremvc.feign;


import cn.hutool.core.collection.CollUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Feign拦截器
 *
 * @author zxiaozhou
 * @date 2019-04-24 14:19
 * @since JDK11
 */
@Component
@Slf4j
public class FeignInterceptorMvcSendDownHeader implements RequestInterceptor {

    /**
     * token设置
     *
     * @param template ${@link RequestTemplate}
     * @author zxiaozhou
     * @date 2019-05-15 17:52
     */
    @Override
    public void apply(RequestTemplate template) {
        try {
            HttpServletRequest request = getHttpServletRequest();
            if (Objects.isNull(request)) {
                return;
            }
            Map<String, Set<String>> headers = getHeaders(request);
            if (CollUtil.isNotEmpty(headers)) {
                headers.forEach((key, value) -> {
                    // 需要排除Content-Length，否则造成数据传输长度异常
                    if (StringUtils.isNotBlank(key) && CollUtil.isNotEmpty(value) && !key.equalsIgnoreCase(HttpHeaders.CONTENT_LENGTH)) {
                        template.header(key, value);
                    }
                });
            }
        } catch (Exception e) {
            log.error("------------apply--->设置下传请求头异常");
        }

    }


    /**
     * 获取HttpServletRequest
     * <p>
     * 用户当前请求的时候对应,如果开启了feign的熔断后会用新的线程,需要将熔断策略换成信号量,此时不会开启新的线程
     * </p>
     *
     * @return HttpServletRequest ${@link HttpServletRequest}
     * @author zxiaozhou
     * @date 2021-05-23 20:55
     */
    private HttpServletRequest getHttpServletRequest() {
        try {
            // 这种方式获取的HttpServletRequest是线程安全的
            return ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        } catch (Exception e) {

            return null;
        }
    }


    /**
     * 获取请求头信息
     *
     * @param request ${@link HttpServletRequest}
     * @return Map<String, Set < String>> ${@link Map<String, Set<String>>}
     * @author zxiaozhou
     * @date 2021-05-23 20:54
     */
    private Map<String, Set<String>> getHeaders(HttpServletRequest request) {
        Map<String, Set<String>> map = new LinkedHashMap<>();
        Enumeration<String> enums = request.getHeaderNames();
        while (enums.hasMoreElements()) {
            String key = enums.nextElement();
            Enumeration<String> headers = request.getHeaders(key);
            Set<String> headerValues = new HashSet<>(16);
            while (headers.hasMoreElements()) {
                headerValues.add(headers.nextElement());
            }
            map.put(key, headerValues);
        }
        return map;
    }
}
