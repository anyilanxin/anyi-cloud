// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.coremvc.feign.header;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.skillfull.corecommon.constant.SysBaseConstant;
import com.anyilanxin.skillfull.corecommon.feign.strategy.header.ISetHeaderStrategy;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author zxiaozhou
 * @date 2021-05-23 01:18
 * @since JDK1.8
 */
@Component(value = SysBaseConstant.FEIGN_DEFAULT)
public class InnerAllHeaderImpl implements ISetHeaderStrategy {
    private static final String CONTENT_LENGTH = "Content-Length";

    @Override
    public void setHeader(RequestTemplate template) {
        HttpServletRequest request = getHttpServletRequest();
        if (Objects.isNull(request)) {
            return;
        }
        Map<String, Set<String>> headers = getHeaders(request);
        if (CollUtil.isNotEmpty(headers)) {
            headers.forEach((key, value) -> {
                // 需要排除Content-Length，否则造成数据传输长度异常
                if (StringUtils.isNotBlank(key) && CollUtil.isNotEmpty(value) && !key.equalsIgnoreCase(CONTENT_LENGTH)) {
                    template.header(key, value);
                }
            });
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
