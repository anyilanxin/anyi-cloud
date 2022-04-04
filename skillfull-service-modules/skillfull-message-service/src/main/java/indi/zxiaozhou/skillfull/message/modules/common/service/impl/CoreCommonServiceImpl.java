// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.message.modules.common.service.impl;

import indi.zxiaozhou.skillfull.corecommon.base.service.ICoreCommonService;
import indi.zxiaozhou.skillfull.corewebflux.config.properfy.CoreWebFluxAppProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 公共接口定义实现
 *
 * @author zxiaozhou
 * @date 2021-07-27 10:10
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CoreCommonServiceImpl implements ICoreCommonService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final CoreWebFluxAppProperty property;

    @Override
    public void loadConstantDict(boolean force) {
        ICoreCommonService.loadConstantDict(force, redisTemplate, property.getServiceName());
    }
}
