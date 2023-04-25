/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
package com.anyilanxin.skillfull.process.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

/**
* base64与文件处理
*
* @author zxiaozhou
* @date 2020-10-15 23:42
* @since JDK11
*/
@Slf4j
public class Base64FileUtils {
    /**
    * base64转InputStream
    *
    * @param base64 ${@link String}
    * @return InputStream ${@link InputStream}
    * @author zxiaozhou
    * @date 2020-10-17 22:24
    */
    public static InputStream base64ToInputStream(String base64) {
        byte[] bytes = Base64.decodeBase64(base64);
        return new ByteArrayInputStream(bytes);
    }

    /**
    * 字符串转Base64字符串
    *
    * @param str ${@link String}
    * @return InputStream ${@link InputStream}
    * @author zxiaozhou
    * @date 2020-10-17 22:24
    */
    public static String strToBase64Str(String str) {
        return Base64.encodeBase64String(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
    * InputStream转base64
    *
    * @param inputStream ${@link InputStream}
    * @return String ${@link String}
    * @author zxiaozhou
    * @date 2020-10-17 22:30
    */
    public static String inputStreamToBase64(InputStream inputStream) {
        byte[] bytes = new byte[0];
        try {
            bytes = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(
                    "------------Base64FileUtils------InputStream转byte[]异常------>inputStreamToBase64:{}",
                    e.getMessage());
        }
        return Base64.encodeBase64String(bytes);
    }
}
