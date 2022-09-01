package com.anyilanxin.skillfull.corecommon.feign.strategy.header.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.anyilanxin.skillfull.corecommon.constant.AuthConstant;
import com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant;
import com.anyilanxin.skillfull.corecommon.feign.strategy.header.ISetHeaderStrategy;
import com.anyilanxin.skillfull.corecommon.utils.ClientTokenUtils;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 系统内部实现从auth-service获取一个token
 *
 * @author zxiaozhou
 * @date 2022-08-10 09:34
 * @since JDK11
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
