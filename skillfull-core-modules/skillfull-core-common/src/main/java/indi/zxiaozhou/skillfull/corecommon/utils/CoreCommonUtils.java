// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.corecommon.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Snowflake;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.CaseFormat;
import indi.zxiaozhou.skillfull.corecommon.annotation.ConstantType;
import indi.zxiaozhou.skillfull.corecommon.constant.CommonCoreConstant;
import indi.zxiaozhou.skillfull.corecommon.constant.ISuperType;
import indi.zxiaozhou.skillfull.corecommon.constant.model.ConstantDictModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import static indi.zxiaozhou.skillfull.corecommon.constant.CommonCoreConstant.COLON;

/**
 * 系统工具类
 *
 * @author zxiaozhou
 * @date 2020-09-25 09:34
 * @since JDK11
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class CoreCommonUtils {
    private static final Map<String, List<ConstantDictModel>> CONSTANT_DICT_CACHE = new ConcurrentHashMap<>(8);
    private static CoreCommonUtils utils;
    private final Snowflake snowflake;


    @PostConstruct
    private void init() {
        utils = this;
    }


    /**
     * 获取有序唯一id
     *
     * @return String ${@link String} id
     * @author zxiaozhou
     * @date 2020-08-28 16:23
     */
    public static String getSnowflakeId() {
        return utils.snowflake.nextIdStr();
    }


    /**
     * 打印系统关键信息(webmvc)
     *
     * @param application ${@link ConfigurableApplicationContext}
     * @author zxiaozhou
     * @date 2021-06-17 19:42
     */
    public static void printSysInfo(ConfigurableApplicationContext application) {
        printSysInfo(application, false);
    }


    /**
     * 打印系统关键信息
     *
     * @param application ${@link ConfigurableApplicationContext}
     * @param webflux     ${@link Boolean} true-webflux,false-webmvc
     * @author zxiaozhou
     * @date 2021-06-17 19:42
     */
    public static void printSysInfo(ConfigurableApplicationContext application, boolean webflux) {
        String ip = "localhost";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        Environment env = application.getEnvironment();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        if (webflux) {
            path = env.getProperty("spring.webflux.base-path");
        }
        if (StringUtils.isBlank(path)) {
            path = "";
        }
        String swaggerUrl = env.getProperty("springdoc.swagger-ui.path");
        String customUrl = env.getProperty("springdoc.swagger-ui.custom-path");
        if (StringUtils.isNotBlank(customUrl)) {
            swaggerUrl = customUrl;
        }
        if (StringUtils.isBlank(swaggerUrl)) {
            swaggerUrl = path + "/swagger-ui.html";
        }
        String profilesActive = env.getProperty("spring.profiles.active");
        String version = env.getProperty("spring.application.version");
        String projectName = env.getProperty("spring.application.name");
        log.info("\n------------------------------------------------------------------------------\n"
                + "Application " + projectName + " v" + version + " " + profilesActive + " is running! Access URLs:\n"
                + "\tApi-Url:\t\t\thttp://" + ip + ":" + port + path + "\n"
                + "\tSwagger-Ui:\t\t\thttp://" + ip + ":" + port + swaggerUrl + "\n"
                + "------------------------------------------------------------------------------");
    }


    /**
     * 文件大小格式化
     *
     * @param size ${@link Long} 文件大小
     * @return String ${@link String} 返回格式化的文件大小
     * @author zxiaozhou
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
     * @author zxiaozhou
     * @date 2020-10-23 14:50
     */
    public static String getFileMd5Hex(InputStream inputStream) {
        String md5 = "";
        try {
            md5 = DigestUtils.md5Hex(inputStream);
        } catch (IOException e) {
            log.error("------------CoreCommonUtils------------>getFileMd5Hex:{}", "获取文件md5值失败");
        }
        return md5;
    }


    /**
     * 对象转json string(保留空键)
     *
     * @param object ${@link Object} 待处理对象
     * @return String ${@link String} 处理结果
     * @author zxiaozhou
     * @date 2020-12-31 15:12
     */
    public static String objectToJsonStr(Object object) {
        String result = "";
        if (Objects.nonNull(object)) {
            result = JSONObject.toJSONString(object, SerializerFeature.WriteMapNullValue);
        }
        return result;
    }


    /**
     * json字符串转实体
     *
     * @param jsonStr ${@link String} 待转换数据
     * @param cla     ${@link Class<T>} 目标对象类型类
     * @return T  处理结果
     * @author zxiaozhou
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
     * @author zxiaozhou
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
     * @author zxiaozhou
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
     * @author zxiaozhou
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
     * @author zxiaozhou
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
     * @author zxiaozhou
     * @date 2021-01-08 14:36
     */
    public static String queryToString(Map<String, String> queryMap) {
        StringBuilder stringBuilder = new StringBuilder();
        if (CollectionUtil.isNotEmpty(queryMap)) {
            queryMap.forEach((k, v) -> stringBuilder.append("&").append(k).append("=").append(v));
        }
        return stringBuilder.toString().replaceFirst("&", "");
    }


    /**
     * 获取常量字典
     *
     * @param packages ${@link String} 扫描包路径
     * @return Map<String, List < ConstantDictModel>> ${@link  Map<String, List<ConstantDictModel>>}
     * @author zxiaozhou
     * @date 2021-04-02 10:51
     */
    public static Map<String, List<ConstantDictModel>> createOrGetConstantDict(String serviceName, String... packages) {
        if (CollectionUtil.isNotEmpty(CONSTANT_DICT_CACHE)) {
            return CONSTANT_DICT_CACHE;
        }
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackages(packages)
                .addScanners(new SubTypesScanner())
                .addScanners(new FieldAnnotationsScanner())
        );
        Set<Class<? extends ISuperType>> types = reflections.getSubTypesOf(ISuperType.class);
        if (CollectionUtil.isNotEmpty(types)) {
            for (Class<?> typeClass : types) {
                ConstantType annotation = typeClass.getAnnotation(ConstantType.class);
                if (typeClass.isEnum() && Objects.nonNull(annotation)) {
                    Object[] enumConstants = typeClass.getEnumConstants();
                    try {
                        Method getCode = typeClass.getMethod("getConstantDict");
                        @SuppressWarnings("unchecked")
                        List<ConstantDictModel> gatewayConstantDictDtoList = (List<ConstantDictModel>) getCode.invoke(enumConstants[0]);
                        CONSTANT_DICT_CACHE.put(serviceName + COLON + typeClass.getSimpleName(), gatewayConstantDictDtoList);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                        log.error("------------CoreCommonUtils------获取枚举下拉字典失败------>createOrGetConstantDict:{}", e.getMessage());
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
     * @author zxiaozhou
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
     * @author zxiaozhou
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
     * @author zxiaozhou
     * @date 2019-04-01 17:02
     */
    public static String get32UUId() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return new UUID(random.nextLong(), random.nextLong()).toString().replace(CommonCoreConstant.DASH, CommonCoreConstant.EMPTY);
    }

}
