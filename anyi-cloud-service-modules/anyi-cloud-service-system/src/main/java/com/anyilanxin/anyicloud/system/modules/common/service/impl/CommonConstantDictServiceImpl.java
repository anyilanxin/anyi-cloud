

package com.anyilanxin.anyicloud.system.modules.common.service.impl;

import com.anyilanxin.anyicloud.corecommon.constant.CoreCommonCacheConstant;
import com.anyilanxin.anyicloud.corecommon.constant.model.ConstantDictModel;
import com.anyilanxin.anyicloud.system.modules.common.service.ICommonConstantDictService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 获取常量字典实现
 *
 * @author zxh
 * @date 2021-07-27 11:37
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommonConstantDictServiceImpl implements ICommonConstantDictService {
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<ConstantDictModel> getListByConstantTypes(String constantTypes) {
        List<ConstantDictModel> constantList = new ArrayList<>(16);
        for (String constantType : constantTypes.split("[,，]")) {
            Object constantObject = redisTemplate.opsForValue().get(CoreCommonCacheConstant.ENGINE_CONSTANT_DICT_CACHE + constantType);
            if (Objects.nonNull(constantObject)) {
                List<ConstantDictModel> constantDictList = (List<ConstantDictModel>) constantObject;
                constantList.addAll(constantDictList);
            }
        }
        return constantList;
    }
}
