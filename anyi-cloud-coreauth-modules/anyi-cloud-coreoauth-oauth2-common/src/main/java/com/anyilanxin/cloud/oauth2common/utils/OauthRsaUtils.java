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

package com.anyilanxin.cloud.oauth2common.utils;

import com.nimbusds.jose.jwk.RSAKey;
import lombok.*;
import org.dromara.hutool.crypto.asymmetric.RSA;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Objects;
import java.util.UUID;

/**
 * @author zxh
 * @date 2023-09-25 23:12
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Component
public class OauthRsaUtils implements CommandLineRunner {
    private final static String SECURITY_KEY = "ANYI-CLOUD-EE:GROBAL-SECURITY-KEY:";
    private static OauthRsaUtils utils;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void run(String... args) throws Exception {
        utils = this;
    }


    public static RSAKey generateRsa() {
        RSA rsa;
        Object securityKey = utils.redisTemplate.opsForValue().get(SECURITY_KEY);
        String keyId;
        if (Objects.nonNull(securityKey)) {
            RSAKeyInfo rsaKeyInfo = (RSAKeyInfo) securityKey;
            rsa = new RSA(rsaKeyInfo.privateKeyBase64, rsaKeyInfo.publicKeyBase64);
            keyId = rsaKeyInfo.keyId;
        } else {
            KeyPair keyPair;
            try {
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
                keyPairGenerator.initialize(2048);
                keyPair = keyPairGenerator.generateKeyPair();
            } catch (Exception ex) {
                throw new IllegalStateException(ex);
            }
            rsa = new RSA(keyPair.getPrivate(), keyPair.getPublic());
            RSAKeyInfo rsaKeyInfo = new RSAKeyInfo();
            rsaKeyInfo.setPrivateKeyBase64(rsa.getPrivateKeyBase64());
            rsaKeyInfo.setPublicKeyBase64(rsa.getPublicKeyBase64());
            rsaKeyInfo.setKeyId(UUID.randomUUID().toString());
            keyId = rsaKeyInfo.keyId;
            utils.redisTemplate.opsForValue().set(SECURITY_KEY, rsaKeyInfo);
        }
        PublicKey publicKey = rsa.getPublicKey();
        PrivateKey privateKey = rsa.getPrivateKey();
        // @formatter:off
        return new RSAKey.Builder((RSAPublicKey)publicKey)
                .privateKey((RSAPrivateKey)privateKey)
                .keyID(keyId)
                .build();
        // @formatter:on
    }


    @Setter
    @Getter
    @ToString
    @EqualsAndHashCode
    private static class RSAKeyInfo implements Serializable {
        @Serial
        private static final long serialVersionUID = 1695655586874L;
        String privateKeyBase64;
        String publicKeyBase64;
        String keyId;
    }
}
