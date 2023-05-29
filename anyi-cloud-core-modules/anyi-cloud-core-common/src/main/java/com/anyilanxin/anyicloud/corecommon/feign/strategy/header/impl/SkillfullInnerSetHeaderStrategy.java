

package com.anyilanxin.anyicloud.corecommon.feign.strategy.header.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.anyilanxin.anyicloud.corecommon.constant.AuthConstant;
import com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant;
import com.anyilanxin.anyicloud.corecommon.feign.strategy.header.ISetHeaderStrategy;
import com.anyilanxin.anyicloud.corecommon.utils.ClientTokenUtils;
import feign.RequestTemplate;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 系统内部实现从auth-service获取一个token
 *
 * @author zxh
 * @date 2022-08-10 09:34
 * @since 1.0.0
 */
@Component(value = CommonCoreConstant.SKILLFULL_INNTE_SET_TOKEN)
public class SkillfullInnerSetHeaderStrategy implements ISetHeaderStrategy {

    @Override
    public void setHeader(RequestTemplate template) {
        Collection<String> headerToken = template.headers().get(AuthConstant.BEARER_TOKEN_HEADER_NAME);
        if (CollectionUtil.isEmpty(headerToken)) {
            String tokenToAuthService = ClientTokenUtils.getTokenToAuthService();
            if (StringUtils.isNotBlank(tokenToAuthService)) {
                template.header(AuthConstant.BEARER_TOKEN_HEADER_NAME, "Bearer " + tokenToAuthService);
            }
        }
    }
}
