// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.corewebflux.base.service;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant;
import com.anyilanxin.skillfull.corecommon.constant.CoreCommonCacheConstant;
import com.anyilanxin.skillfull.corecommon.constant.model.ConstantDictModel;
import com.anyilanxin.skillfull.corecommon.utils.CoreCommonUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.LOCK_EXPIRES;
import static com.anyilanxin.skillfull.corecommon.constant.SysBaseConstant.BOOT_BASE_SCAN_PACKAGE;

/**
 * 公共接口定义
 *
 * @author zxiaozhou
 * @date 2021-07-27 10:09
 * @since JDK1.8
 */
public interface ICoreWebfluxService {
    String REDIS_LOCK_KEY = CommonCoreConstant.SYSTEM_PREFIX + "CONSTANT_DICT_LOCK_";

    /**
     * 加载常量字典
     *
     * @param force ${@link Boolean} 是否强制刷新:true-强制立马刷新,false-如果上次刷新距离本次5分钟,当前刷新无效
     * @author zxiaozhou
     * @date 2021-07-27 10:10
     */
    void loadConstantDict(boolean force);


    /**
     * 加载常量字典默认实现
     *
     * @param force         ${@link Boolean} 是否强制刷新:true-强制立马刷新,false-如果上次刷新距离本次5分钟,当前刷新无效
     * @param redisTemplate ${@link RedisTemplate}  RedisTemplate实例
     * @param serviceName   ${@link String} 服务名
     * @author zxiaozhou
     * @date 2021-09-15 22:02
     */
    static void loadConstantDict(boolean force, RedisTemplate<String, Object> redisTemplate, String serviceName) {
        if (!force) {
            Object redisLockValue = redisTemplate.opsForValue().get(ICoreWebfluxService.REDIS_LOCK_KEY + serviceName);
            if (Objects.nonNull(redisLockValue)) {
                return;
            }
        }
        Map<String, List<ConstantDictModel>> constantDict = CoreCommonUtils.createOrGetConstantDict(serviceName, BOOT_BASE_SCAN_PACKAGE);
        if (CollUtil.isNotEmpty(constantDict)) {
            constantDict.forEach((k, v) -> redisTemplate.opsForValue().set(CoreCommonCacheConstant.ENGINE_CONSTANT_DICT_CACHE + k, v));
        }
        redisTemplate.opsForValue().set(ICoreWebfluxService.REDIS_LOCK_KEY + serviceName, true, LOCK_EXPIRES, TimeUnit.SECONDS);
    }
}
