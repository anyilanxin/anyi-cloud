

package com.anyilanxin.anyicloud.message.utils;

import static freemarker.template.Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS;

import com.alibaba.fastjson.JSONObject;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * freemarker模板工具类
 *
 * @author zxh
 * @date 2021-04-25 20:25
 * @since 1.0.0
 */
@Slf4j
public class FreemarkerUtils {
    /**
     * 解析字符串模板,通用方法
     *
     * @param templateStr ${@link String} 待解析字符串
     * @param jsonObject  ${@link JSONObject} 填充内容
     * @return String ${@link String}
     * @author zxh
     * @date 2021-04-25 20:27
     */
    public static String processStr(String templateStr, JSONObject jsonObject) {
        if (StringUtils.isBlank(templateStr)) {
            return "";
        }
        Configuration configuration = new Configuration(DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        StringWriter out = new StringWriter();
        try {
            new Template("template", new StringReader(templateStr), configuration).process(jsonObject, out);
        } catch (TemplateException | IOException e) {
            e.printStackTrace();
            log.error("------------FreemarkerUtils------模板解析异常------>processStr--->\n参数:{},异常消息:{}", templateStr, e.getMessage());
        }
        return out.toString();
    }
}
