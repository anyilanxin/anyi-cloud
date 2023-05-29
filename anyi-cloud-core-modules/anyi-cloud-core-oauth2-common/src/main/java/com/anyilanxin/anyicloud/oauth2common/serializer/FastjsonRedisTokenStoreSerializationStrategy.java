/*
 * Copyright (c) 2021-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Zeebe EE、AnYi Cloud EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */
package com.anyilanxin.anyicloud.oauth2common.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.TypeUtils;
import com.anyilanxin.anyicloud.oauth2common.authinfo.SkillFullAccessToken;
import com.anyilanxin.anyicloud.oauth2common.authinfo.SkillFullClientDetails;
import com.anyilanxin.anyicloud.oauth2common.authinfo.SkillFullGrantedAuthority;
import com.anyilanxin.anyicloud.oauth2common.authinfo.SkillFullUserDetails;
import com.google.common.base.Preconditions;
import java.nio.charset.StandardCharsets;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.SerializationException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStoreSerializationStrategy;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * @author zxh
 * @date 2022-02-14 16:25
 * @since 1.0.0
 */
public class FastjsonRedisTokenStoreSerializationStrategy implements RedisTokenStoreSerializationStrategy {

    private static ParserConfig config = new ParserConfig();

    static {
        init();
    }

    protected static void init() {
        // 自定义oauth2序列化：DefaultOAuth2RefreshToken 没有setValue方法，会导致JSON序列化为null
        config.setAutoTypeSupport(true); // 开启AutoType
        // 自定义DefaultOauth2RefreshTokenSerializer反序列化
        config.putDeserializer(DefaultOAuth2RefreshToken.class, new DefaultOauth2RefreshTokenSerializer());
        // 自定义OAuth2Authentication反序列化
        config.putDeserializer(OAuth2Authentication.class, new OAuth2AuthenticationSerializer());
        // 添加auto type白名单
        config.addAccept("org.springframework.security.oauth2.provider.");
        TypeUtils.addMapping("org.springframework.security.oauth2.provider.OAuth2Authentication", OAuth2Authentication.class);
        config.addAccept("org.springframework.security.oauth2.provider.authentication.");
        TypeUtils.addMapping("org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails", OAuth2Authentication.class);

        config.addAccept("org.springframework.security.oauth2.provider.client.");
        TypeUtils.addMapping("org.springframework.security.oauth2.provider.client.BaseClientDetails", BaseClientDetails.class);

        config.addAccept("org.springframework.security.oauth2.common.");
        TypeUtils.addMapping("org.springframework.security.oauth2.common.DefaultOAuth2AccessToken", DefaultOAuth2AccessToken.class);
        TypeUtils.addMapping("org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken", DefaultExpiringOAuth2RefreshToken.class);

        config.addAccept("org.springframework.security.core.userdetails.");
        TypeUtils.addMapping("org.springframework.security.core.userdetails.UserDetails", UserDetails.class);

        config.addAccept("org.springframework.security.web.authentication.preauth.");
        TypeUtils.addMapping("org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken", PreAuthenticatedAuthenticationToken.class);

        config.addAccept("org.springframework.security.core.authority.");
        TypeUtils.addMapping("org.springframework.security.core.authority.SimpleGrantedAuthority", SimpleGrantedAuthority.class);

        config.addAccept("org.springframework.security.authentication.");
        TypeUtils.addMapping("org.springframework.security.authentication.UsernamePasswordAuthenticationToken", UsernamePasswordAuthenticationToken.class);

        config.addAccept("com.anyilanxin.skillfull.oauth2common.authinfo.");
        TypeUtils.addMapping("com.anyilanxin.skillfull.oauth2common.authinfo.SkillFullGrantedAuthority", SkillFullGrantedAuthority.class);
        TypeUtils.addMapping("com.anyilanxin.skillfull.oauth2common.authinfo.SkillFullAccessToken", SkillFullAccessToken.class);
        TypeUtils.addMapping("com.anyilanxin.skillfull.oauth2common.authinfo.SkillFullClientDetails", SkillFullClientDetails.class);
        TypeUtils.addMapping("com.anyilanxin.skillfull.oauth2common.authinfo.SkillFullUserDetails", SkillFullUserDetails.class);
    }


    @Override
    public <T> T deserialize(byte[] bytes, Class<T> aClass) {
        Preconditions.checkArgument(aClass != null, "clazz can't be null");
        if (bytes == null || bytes.length == 0) {
            return null;
        }

        try {
            return JSON.parseObject(new String(bytes, IOUtils.UTF8), aClass, config);
        } catch (Exception ex) {
            throw new SerializationException("Could not serialize: " + ex.getMessage(), ex);
        }
    }


    @Override
    public String deserializeString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return new String(bytes, IOUtils.UTF8);
    }


    @Override
    public byte[] serialize(Object o) {
        if (o == null) {
            return new byte[0];
        }
        try {
            return JSON.toJSONBytes(o, SerializerFeature.WriteClassName, SerializerFeature.DisableCircularReferenceDetect);
        } catch (Exception ex) {
            throw new SerializationException("Could not serialize: " + ex.getMessage(), ex);
        }
    }


    @Override
    public byte[] serialize(String data) {
        if (data == null || data.length() == 0) {
            return new byte[0];
        }
        return data.getBytes(StandardCharsets.UTF_8);
    }
}
