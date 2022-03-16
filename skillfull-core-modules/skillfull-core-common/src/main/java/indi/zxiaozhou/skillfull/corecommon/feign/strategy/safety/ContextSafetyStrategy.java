// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.corecommon.feign.strategy.safety;

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
public class ContextSafetyStrategy {
    private Map<String, ISafetyStrategy> safetyStrategy = new ConcurrentHashMap<>();


    @Autowired
    public ContextSafetyStrategy(Map<String, ISafetyStrategy> safetyStrategy) {
        safetyStrategy.forEach((k, v) -> this.safetyStrategy.put(k, v));
    }

    public void handleSafety(String strategy, RequestTemplate template) {
        ISafetyStrategy handle = safetyStrategy.get(strategy);
        if (handle != null) {
            handle.handleSafety(template);
        } else {
            log.error("----------ContextStrategy---------->doStrategy:{}", "Api调用未找到处理数据安全的方法");
        }
    }
}
