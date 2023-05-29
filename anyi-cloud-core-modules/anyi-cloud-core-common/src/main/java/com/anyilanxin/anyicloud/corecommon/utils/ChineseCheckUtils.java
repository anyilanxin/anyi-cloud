

package com.anyilanxin.anyicloud.corecommon.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 中文处理工具类
 *
 * @author zxh
 * @date 2022-02-13 23:20
 * @since 1.0.0
 */
public class ChineseCheckUtils {
    /**
     * 字符串是否包含中文
     *
     * @param str 待校验字符串
     * @return true 包含中文字符 false 不包含中文字符
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4E00-\u9FA5|\\！|\\，|\\。|\\（|\\）|\\《|\\》|\\“|\\”|\\？|\\：|\\；|\\【|\\】]");
        Matcher m = p.matcher(str);
        return m.find();
    }
}
