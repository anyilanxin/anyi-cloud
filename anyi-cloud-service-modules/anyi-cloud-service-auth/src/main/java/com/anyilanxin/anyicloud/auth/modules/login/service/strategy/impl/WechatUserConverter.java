/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */
package com.anyilanxin.anyicloud.auth.modules.login.service.strategy.impl;

import com.anyilanxin.anyicloud.auth.modules.login.model.security.BasicOAuth2User;
import com.anyilanxin.anyicloud.auth.modules.login.service.strategy.Oauth2UserConverterStrategy;
import com.anyilanxin.anyicloud.auth.oauth2.constant.SecurityConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * 微信用户信息转换器
 *
 * @author vains
 */
@RequiredArgsConstructor
@Component(SecurityConstants.THIRD_LOGIN_WECHAT)
public class WechatUserConverter implements Oauth2UserConverterStrategy {

    @Override
    public BasicOAuth2User convert(OAuth2User oAuth2User) {
        // 获取三方用户信息
        Map<String, Object> attributes = oAuth2User.getAttributes();
        // 转换至 BasicOAuth2User
        BasicOAuth2User basicOauth2User = new BasicOAuth2User(oAuth2User, null);
        basicOauth2User.setUniqueId(String.valueOf(attributes.get("openid")));
        basicOauth2User.setThirdUsername(oAuth2User.getName());
        basicOauth2User.setType(SecurityConstants.THIRD_LOGIN_WECHAT);
        basicOauth2User.setLocation(attributes.get("province") + " " + attributes.get("city"));
        // 设置基础用户信息
        basicOauth2User.setName(oAuth2User.getName());
        basicOauth2User.setAvatarUrl(String.valueOf(attributes.get("headimgurl")));
        return basicOauth2User;
    }
}
