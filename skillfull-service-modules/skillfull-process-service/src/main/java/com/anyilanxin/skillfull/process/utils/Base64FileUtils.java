// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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
            log.error("------------Base64FileUtils------InputStream转byte[]异常------>inputStreamToBase64:{}", e.getMessage());
        }
        return Base64.encodeBase64String(bytes);
    }
}
