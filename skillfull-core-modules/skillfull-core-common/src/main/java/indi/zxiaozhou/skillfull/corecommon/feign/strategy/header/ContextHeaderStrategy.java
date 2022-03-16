// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.corecommon.feign.strategy.header;

import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * token设置策略上下文
 *
 * @author zxiaozhou
 * @date 2019-02-03 21:33
 * @since JDK11
 */
@Component
@Slf4j
public class ContextHeaderStrategy {
    private final static Map<String, ISetHeaderStrategy> HEADER_STRATEGY = new ConcurrentHashMap<>();


    @Autowired
    public ContextHeaderStrategy(Map<String, ISetHeaderStrategy> headerStrategy) {
        headerStrategy.forEach(HEADER_STRATEGY::put);
    }

    public void setHeader(String strategy, RequestTemplate template) {
        ISetHeaderStrategy setTokenStrategy = HEADER_STRATEGY.get(strategy);
        if (setTokenStrategy != null) {
            setTokenStrategy.setHeader(template);
        } else {
            log.error("----------ContextStrategy---------->doStrategy:{}", "Api调用未找到设置请求头的方法");
        }
    }
}
