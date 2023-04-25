package com.anyilanxin.skillfull.corecommon.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Set;

/**
 * 本地化工具
 *
 * @author zxiaozhou
 * @date 2022-02-16 15:18
 * @since JDK1.8
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class I18nUtil {
    private final MessageSource messageSource;
    private static I18nUtil util;

    @PostConstruct
    public void init() {
        util = this;
    }

    /**
     * 获取单个国际化翻译值
     *
     * @param code ${@link String} the code of the message
     * @return String ${@link String}
     * @author zxiaozhou
     * @date 2022-02-16 17:17
     */
    public static String get(String code) {
        try {
            return util.messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return code;
        }
    }


    /**
     * 获取单个国际化翻译值
     *
     * @param code           ${@link String} the code of the message
     * @param defaultMessage ${@link String} the String to return if the lookup fails
     * @return String ${@link String}
     * @author zxiaozhou
     * @date 2022-02-16 17:17
     */
    public static String getDefault(String code, String defaultMessage) {
        try {
            return util.messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return defaultMessage;
        }
    }


    /**
     * 获取单个国际化翻译值
     *
     * @param code ${@link String} the code of the message
     * @param args ${@link String[]} arguments for the message, or null if none
     * @return String ${@link String}
     * @author zxiaozhou
     * @date 2022-02-16 17:17
     */
    public static String get(String code, @Nullable Object... args) {
        try {
            return util.messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return code;
        }
    }


    /**
     * 获取单个国际化翻译值
     *
     * @param code           ${@link String} the code of the message
     * @param args           ${@link String[]} arguments for the message, or null if none
     * @param defaultMessage ${@link String} the String to return if the lookup fails
     * @return String ${@link String}
     * @author zxiaozhou
     * @date 2022-02-16 17:17
     */
    public static String getDefault(String code, String defaultMessage, @Nullable Object... args) {
        try {
            return util.messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return defaultMessage;
        }
    }


    /**
     * i18n兼容处理
     *
     * @param errMsg ${@link String}
     * @param local  ${@link Map <String, String>} 替换编码
     * @return String ${@link String}
     * @author zxiaozhou
     * @date 2022-02-16 11:36
     */
    public static String getLocalMessage(Map<String, String> local, String errMsg) {
        if (StringUtils.isNotBlank(errMsg) && ChineseCheckUtils.isContainChinese(errMsg)) {
            return errMsg;
        }
        Set<Map.Entry<String, String>> entries = local.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            errMsg = errMsg.replace(entry.getKey(), I18nUtil.get(entry.getValue()));
        }
        return errMsg;
    }
}
