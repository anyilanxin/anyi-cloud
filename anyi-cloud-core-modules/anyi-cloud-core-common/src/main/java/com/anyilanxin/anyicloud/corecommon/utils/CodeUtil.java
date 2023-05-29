

package com.anyilanxin.anyicloud.corecommon.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 编码生成工具类
 *
 * @author zxh
 * @date 2021-03-08 10:53
 * @since 1.0.0
 */
public class CodeUtil {
    // 数字位数(默认生成3位的数字)

    private static final int numLength = 3; // 代表数字位数

    /**
     * 根据父亲code,获取下级的下一个code
     *
     * <p>例如：父亲CODE:A01 当前CODE:A01B03 获取的code:A01B04
     *
     * @param parentCode 上级code
     * @param localCode  同级code
     * @return
     */
    public static synchronized String getSubYouBianCode(String parentCode, String localCode) {
        if (StringUtils.isBlank(parentCode)) {
            parentCode = "";
        }
        if (StringUtils.isNotBlank(localCode)) {
            return getNextYouBianCode(localCode);
        } else {
            parentCode = parentCode + "A" + getNextStrNum(0);
        }
        return parentCode;
    }


    /**
     * 根据前一个code，获取同级下一个code 例如:当前最大code为D01A04，下一个code为：D01A05
     *
     * @param code
     * @return
     */
    private static synchronized String getNextYouBianCode(String code) {
        String newcode;
        if (code == null || code.equals("")) {
            String zimu = "A";
            String num = getStrNum(1);
            newcode = zimu + num;
        } else {
            String before_code = code.substring(0, code.length() - 1 - numLength);
            String after_code = code.substring(code.length() - 1 - numLength, code.length());
            char after_code_zimu = after_code.substring(0, 1).charAt(0);
            int after_code_num = Integer.parseInt(after_code.substring(1));
            String nextNum;
            char nextZimu;
            // 先判断数字等于999*，则计数从1重新开始，递增
            if (after_code_num == getMaxNumByLength(numLength)) {
                nextNum = getNextStrNum(0);
            } else {
                nextNum = getNextStrNum(after_code_num);
            }
            // 先判断数字等于999*，则字母从A重新开始,递增
            if (after_code_num == getMaxNumByLength(numLength)) {
                nextZimu = getNextZiMu(after_code_zimu);
            } else {
                nextZimu = after_code_zimu;
            }

            // 例如Z99，下一个code就是Z99A01
            if ('Z' == after_code_zimu && getMaxNumByLength(numLength) == after_code_num) {
                newcode = code + (nextZimu + nextNum);
            } else {
                newcode = before_code + (nextZimu + nextNum);
            }
        }
        return newcode;
    }


    /**
     * 将数字前面位数补零
     *
     * @param num
     * @return
     */
    private static String getNextStrNum(int num) {
        return getStrNum(getNextNum(num));
    }


    /**
     * 将数字前面位数补零
     *
     * @param num
     * @return
     */
    private static String getStrNum(int num) {
        return String.format("%0" + numLength + "d", num);
    }


    /**
     * 根据数字位数获取最大值
     *
     * @param length
     * @return
     */
    private static int getMaxNumByLength(int length) {
        if (length == 0) {
            return 0;
        }
        String max_num = "";
        for (int i = 0; i < length; i++) {
            max_num = max_num + "9";
        }
        return Integer.parseInt(max_num);
    }


    /**
     * 递增获取下个数字
     *
     * @param num
     * @return
     */
    private static int getNextNum(int num) {
        num++;
        return num;
    }


    /**
     * 递增获取下个字母
     *
     * @return
     */
    private static char getNextZiMu(char zimu) {
        if (zimu == 'Z') {
            return 'A';
        }
        zimu++;
        return zimu;
    }
}
