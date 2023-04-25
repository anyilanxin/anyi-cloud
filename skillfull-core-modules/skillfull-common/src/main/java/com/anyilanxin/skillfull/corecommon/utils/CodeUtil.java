/*
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   8.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   9.若您的项目无法满足以上几点，可申请商业授权。
 */


package com.anyilanxin.skillfull.corecommon.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 编码生成工具类
 *
 * @author zxiaozhou
 * @date 2021-03-08 10:53
 * @since JDK1.8
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
