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

package com.anyilanxin.anyicloud.auth.oauth2.jose;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;

/**
 * @author Joe Grandja
 * @since 1.1
 */
final class KeyGeneratorUtils {

    private KeyGeneratorUtils() {
    }

    static SecretKey generateSecretKey() {
        SecretKey hmacKey;
        try {
            hmacKey = KeyGenerator.getInstance("HmacSha256").generateKey();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return hmacKey;
    }

    static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    static KeyPair generateEcKey() {
        EllipticCurve ellipticCurve = new EllipticCurve(
                new ECFieldFp(
                        new BigInteger("115792089210356248762697446949407573530086143415290314195533631308867097853951")),
                new BigInteger("115792089210356248762697446949407573530086143415290314195533631308867097853948"),
                new BigInteger("41058363725152142129326129780047268409114441015993725554835256314039467401291"));
        ECPoint ecPoint = new ECPoint(
                new BigInteger("48439561293906451759052585252797914202762949526041747995844080717082404635286"),
                new BigInteger("36134250956749795798585127919587881956611106672985015071877198253568414405109"));
        ECParameterSpec ecParameterSpec = new ECParameterSpec(
                ellipticCurve,
                ecPoint,
                new BigInteger("115792089210356248762697446949407573529996955224135760342422259061068512044369"),
                1);

        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
            keyPairGenerator.initialize(ecParameterSpec);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

}
