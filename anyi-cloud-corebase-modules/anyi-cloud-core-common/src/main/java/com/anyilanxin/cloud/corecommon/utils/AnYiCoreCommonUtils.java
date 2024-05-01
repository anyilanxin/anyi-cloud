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

package com.anyilanxin.cloud.corecommon.utils;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.anyilanxin.cloud.corecommon.annotation.ConstantType;
import com.anyilanxin.cloud.corecommon.constant.CommonCoreConstant;
import com.anyilanxin.cloud.corecommon.constant.ISuperType;
import com.anyilanxin.cloud.corecommon.constant.model.ConstantDictModel;
import com.google.common.base.CaseFormat;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.data.id.Snowflake;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 系统工具类
 *
 * @author zxh
 * @date 2020-09-25 09:34
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class AnYiCoreCommonUtils {
    private static final Map<String, List<ConstantDictModel>> CONSTANT_DICT_CACHE = new ConcurrentHashMap<>(8);
    private static AnYiCoreCommonUtils utils;
    private final Snowflake snowflake;

    @PostConstruct
    void init() {
        utils = this;
    }


    /**
     * 获取有序唯一id
     *
     * @return String ${@link String} id
     * @author zxh
     * @date 2020-08-28 16:23
     */
    public static String getSnowflakeId() {
        return utils.snowflake.nextIdStr();
    }


    /**
     * 文件大小格式化
     *
     * @param size ${@link Long} 文件大小
     * @return String ${@link String} 返回格式化的文件大小
     * @author zxh
     * @date 2020-10-23 14:47
     */
    public static String getFormatFileSize(long size) {
        String fileSize;
        String unit;
        DecimalFormat df = new DecimalFormat("#.00");
        if (size < (1 << 10)) {
            fileSize = df.format((double) size);
            unit = "B";
        } else if (size < (1 << 20)) {
            fileSize = df.format((double) size / (1 << 10));
            unit = "KB";
        } else if (size < (1 << 30)) {
            fileSize = df.format((double) size / (1 << 20));
            unit = "MB";
        } else {
            fileSize = df.format((double) size / (1 << 30));
            unit = "GB";
        }
        return fileSize + unit;
    }


    /**
     * 获取文件md5值
     *
     * @param inputStream ${@link InputStream}
     * @return String ${@link String}
     * @author zxh
     * @date 2020-10-23 14:50
     */
    public static String getFileMd5Hex(InputStream inputStream) {
        String md5 = "";
        try {
            md5 = DigestUtils.md5Hex(inputStream);
        } catch (IOException e) {
            log.error("------------AnYiCoreCommonUtils------------>getFileMd5Hex:{}", "获取文件md5值失败");
        }
        return md5;
    }


    /**
     * 对象转json string(保留空键)
     *
     * @param object ${@link Object} 待处理对象
     * @return String ${@link String} 处理结果
     * @author zxh
     * @date 2020-12-31 15:12
     */
    public static String objectToJsonStr(Object object) {
        String result = "";
        if (Objects.nonNull(object)) {
            result = JSONObject.toJSONString(object, JSONWriter.Feature.WriteMapNullValue);
        }
        return result;
    }


    /**
     * json字符串转实体
     *
     * @param jsonStr ${@link String} 待转换数据
     * @param cla     ${@link Class<T>} 目标对象类型类
     * @return T  处理结果
     * @author zxh
     * @date 2021-01-08 13:21
     */
    public static <T> T jsonStrToObject(String jsonStr, Class<T> cla) {
        if (StringUtils.isNotBlank(jsonStr)) {
            return JSONObject.parseObject(jsonStr, cla);
        }
        return null;
    }


    /**
     * 获取堆栈信息
     *
     * @param throwable ${@link Throwable} 异常信息
     * @return String ${@link String} 处理结果
     * @author zxh
     * @date 2020-08-27 15:22
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }


    /**
     * 驼峰命名转下划线
     *
     * @param str ${@link String} 驼峰
     * @return String ${@link String} 下划线
     * @author zxh
     * @date 2019-08-01 13:54
     */
    public static String humpToUnderline(String str) {
        if (StringUtils.isNotBlank(str)) {
            return CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.LOWER_UNDERSCORE).convert(str);
        }
        return null;
    }


    /**
     * 下划线转驼峰
     *
     * @param str ${@link String}
     * @return String ${@link String}
     * @author zxh
     * @date 2019-08-01 13:58
     */
    public static String underlineToHump(String str) {
        if (StringUtils.isNotBlank(str)) {
            return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, str);
        }
        return null;
    }


    /**
     * 获取get中参数
     *
     * @param queryStr ${@link String} 待解析内容
     * @return Map<String, String>  ${@link Map<String, String> } 解析结果
     * @author zxh
     * @date 2021-01-08 14:36
     */
    public static Map<String, String> getQueryMap(String queryStr) {
        Map<String, String> queryMap = new HashMap<>(8);
        if (StringUtils.isNotBlank(queryStr)) {
            String[] queryParam = queryStr.split("&");
            Arrays.stream(queryParam).forEach(s -> {
                String[] kv = s.split("=", 2);
                String value = kv.length == 2 ? kv[1] : "";
                queryMap.put(kv[0], value);
            });
        }
        return queryMap;
    }


    /**
     * get参数转String
     *
     * @param queryMap ${@link Map<String, String>} 待组装参数
     * @return String ${@linkString } 解析结果
     * @author zxh
     * @date 2021-01-08 14:36
     */
    public static String queryToString(Map<String, String> queryMap) {
        StringBuilder stringBuilder = new StringBuilder();
        if (CollUtil.isNotEmpty(queryMap)) {
            queryMap.forEach((k, v) -> stringBuilder.append("&").append(k).append("=").append(v));
        }
        return stringBuilder.toString().replaceFirst("&", "");
    }


    /**
     * 获取常量字典
     *
     * @param packages ${@link String} 扫描包路径
     * @return Map<String, List < ConstantDictModel>> ${@link  Map<String, List<ConstantDictModel>>}
     * @author zxh
     * @date 2021-04-02 10:51
     */
    public static Map<String, List<ConstantDictModel>> createOrGetConstantDict(String serviceName, String... packages) {
        if (CollUtil.isNotEmpty(CONSTANT_DICT_CACHE)) {
            return CONSTANT_DICT_CACHE;
        }
        Reflections reflections = new Reflections(new ConfigurationBuilder().forPackages(packages).addScanners(new SubTypesScanner()).addScanners(new FieldAnnotationsScanner()));
        Set<Class<? extends ISuperType>> types = reflections.getSubTypesOf(ISuperType.class);
        if (CollUtil.isNotEmpty(types)) {
            for (Class<?> typeClass : types) {
                ConstantType annotation = typeClass.getAnnotation(ConstantType.class);
                if (typeClass.isEnum() && Objects.nonNull(annotation)) {
                    Object[] enumConstants = typeClass.getEnumConstants();
                    try {
                        Method getCode = typeClass.getMethod("getConstantDict");
                        @SuppressWarnings("unchecked")
                        List<ConstantDictModel> constantDictDtoList = (List<ConstantDictModel>) getCode.invoke(enumConstants[0]);
                        if (CollUtil.isNotEmpty(constantDictDtoList)) {
                            CONSTANT_DICT_CACHE.put(serviceName + CommonCoreConstant.COLON + typeClass.getSimpleName(), constantDictDtoList);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("------------AnYiCoreCommonUtils------获取枚举下拉字典失败------>createOrGetConstantDict:{}", e.getMessage());
                    }
                }
            }
        }
        return CONSTANT_DICT_CACHE;
    }


    /**
     * 获取SpringBootApplication扫描路径或某个类包路径
     *
     * @param clas ${@link Class}
     * @return String[] ${@link String[]}
     * @author zxh
     * @date 2021-04-12 15:29
     */
    public static <T> String[] getPackages(Class<T> clas) {
        SpringBootApplication bootApplication = clas.getAnnotation(SpringBootApplication.class);
        String[] packages = null;
        if (Objects.nonNull(bootApplication)) {
            packages = bootApplication.scanBasePackages();
        }
        if (ArrayUtils.getLength(packages) <= 0 && Objects.nonNull(clas)) {
            packages = new String[]{clas.getPackage().getName()};
        }
        return packages;
    }


    /**
     * url中获取uri
     *
     * @param url ${@link String} url信息
     * @return String ${@link String}
     * @author zxh
     * @date 2021-07-11 23:28
     */
    public static String getUri(String url) {
        if (StringUtils.isBlank(url)) {
            return "";
        } else {
            return url.replaceAll("^(http(s?)://)([a-zA-Z0-9]+)(:\\d+)?", "");
        }
    }


    /**
     * 获取32位uuid(使用ThreadLocalRandom提高性能)
     *
     * @return String ${@link String}
     * @author zxh
     * @date 2019-04-01 17:02
     */
    public static String get32UUId() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return new UUID(random.nextLong(), random.nextLong()).toString().replace(CommonCoreConstant.DASH, CommonCoreConstant.EMPTY);
    }

    /**
     * 中文数字
     */
    private static final String[] CN_NUM = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};

    /**
     * 中文数字单位
     */
    private static final String[] CN_UNIT = {"", "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千"};

    /**
     * 特殊字符：负
     */
    private static final String CN_NEGATIVE = "负";

    /**
     * 特殊字符：点
     */
    private static final String CN_POINT = "点";

    /**
     * int 转 中文数字
     * 支持到int最大值
     *
     * @param intNum 要转换的整型数
     * @return 中文数字
     */
    public static String int2chineseNum(int intNum) {
        StringBuilder sb = new StringBuilder();
        boolean isNegative = false;
        if (intNum < 0) {
            isNegative = true;
            intNum *= -1;
        }
        int count = 0;
        while (intNum > 0) {
            sb.insert(0, CN_NUM[intNum % 10] + CN_UNIT[count]);
            intNum = intNum / 10;
            count++;
        }

        if (isNegative) {
            sb.insert(0, CN_NEGATIVE);
        }

        return sb.toString().replaceAll("零[千百十]", "零").replaceAll("零+万", "万").replaceAll("零+亿", "亿").replaceAll("亿万", "亿零").replaceAll("零+", "零").replaceAll("零$", "");
    }


    /**
     * bigDecimal 转 中文数字
     * 整数部分只支持到int的最大值
     *
     * @param bigDecimalNum 要转换的BigDecimal数
     * @return 中文数字
     */
    public static String bigDecimal2chineseNum(BigDecimal bigDecimalNum) {
        if (bigDecimalNum == null) {
            return CN_NUM[0];
        }

        StringBuilder sb = new StringBuilder();

        // 将小数点后面的零给去除
        String numStr = bigDecimalNum.abs().stripTrailingZeros().toPlainString();

        String[] split = numStr.split("\\.");
        String integerStr = int2chineseNum(Integer.parseInt(split[0]));

        sb.append(integerStr);

        // 如果传入的数有小数，则进行切割，将整数与小数部分分离
        if (split.length == 2) {
            // 有小数部分
            sb.append(CN_POINT);
            String decimalStr = split[1];
            char[] chars = decimalStr.toCharArray();
            for (char aChar : chars) {
                int index = Integer.parseInt(String.valueOf(aChar));
                sb.append(CN_NUM[index]);
            }
        }

        // 判断传入数字为正数还是负数
        int signum = bigDecimalNum.signum();
        if (signum == -1) {
            sb.insert(0, CN_NEGATIVE);
        }

        return sb.toString();
    }
}
