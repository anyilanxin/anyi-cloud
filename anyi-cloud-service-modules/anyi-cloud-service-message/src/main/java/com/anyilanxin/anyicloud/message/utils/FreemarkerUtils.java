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

package com.anyilanxin.anyicloud.message.utils;

import com.alibaba.fastjson2.JSONObject;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import static freemarker.template.Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS;

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
