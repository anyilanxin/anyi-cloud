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
package com.anyilanxin.anyicloud.database.dataprivacy.utils;

import com.anyilanxin.anyicloud.database.dataprivacy.exception.FieldShowException;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;

/**
 * 字段处理工具类
 *
 * @author zhouxuanhong
 * @date 2018-11-15 18:56
 * @since JDK1.8
 */
public class FiledUtils {
    private static final String FILED_FORMAT = "[h,s]\\w+-([h,s])([\\w+,?])-[h,s]\\w+";
    private static final String FILED_SHOW_ALL = "s\\w+-s[\\w+,?]-s\\w+";
    private static final String PREFIX = "[h,s]";
    private static final String UNKNOWN = "?";


    /**
     * 字段显示处理
     *
     * @param object        ${@link Object} 待处理内容(只能为String类型)
     * @param format        ${@link String} 处理格式
     * @param replaceSymbol ${@link String} 替换的符号
     * @return Object ${@link Object} 处理后的内容
     * @author zhouxuanhong
     * @date 2019-11-15 18:58
     */
    public static Object hiddenFiled(Object object, String format, String replaceSymbol) {
        if (object instanceof String str) {
            if (StringUtils.isNotBlank(str) && StringUtils.isNotBlank(format)) {
                // 如果规则为全部显示则不处理
                if (format.matches(FILED_SHOW_ALL)) {
                    return object;
                }
                if (!format.matches(FILED_FORMAT)) {
                    throw new FieldShowException("未识别到字段处理规则:处理字段:" + object.getClass().getName() + "(" + object + "),规则:" + format);
                }
                // 计算规则所需要字符串长度
                String[] formats = format.split("-");
                String start = formats[0].replaceFirst(PREFIX, "").trim();
                String middle = formats[1].replaceFirst(PREFIX, "").trim();
                String end = formats[2].replaceFirst(PREFIX, "").trim();
                int startNum = Integer.parseInt(start);
                int middleNum = 0;
                int endNum = Integer.parseInt(end);
                int totalLen = startNum + endNum;
                boolean middleKnown = false;
                if (!middle.contains(UNKNOWN)) {
                    middleNum += Integer.parseInt(middle);
                    middleKnown = true;
                } else {
                    middleNum = str.length() - totalLen;
                }
                totalLen += middleNum;
                // 如果中间长度已知并且字符串总长度不等于处理长度||中间长度未知并且字符串长度小于总处理长度则不处理字段隐藏
                boolean check = middleNum < 0 || (middleKnown && str.length() != totalLen) || (!middleKnown && (str.length() < totalLen));
                if (check) {
                    return object;
                }
                // 字符串替换
                String startReplacement = formats[0].startsWith("s") ? "$1" : String.join("", Collections.nCopies(startNum, replaceSymbol));
                String middleReplacement = formats[1].startsWith("s") ? "$2" : String.join("", Collections.nCopies(middleNum, replaceSymbol));
                String endReplacement = formats[2].startsWith("s") ? "$3" : String.join("", Collections.nCopies(endNum, replaceSymbol));
                String replacement = startReplacement + middleReplacement + endReplacement;
                String regex = "(.{" + startNum + "})(.{" + middleNum + "})(.{" + endNum + "})";
                return str.replaceAll(regex, replacement);
            }
        }
        return object;
    }
}
