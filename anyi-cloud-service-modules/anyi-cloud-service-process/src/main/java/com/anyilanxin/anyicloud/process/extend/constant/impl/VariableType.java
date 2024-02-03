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

package com.anyilanxin.anyicloud.process.extend.constant.impl;

import com.anyilanxin.anyicloud.corecommon.annotation.ConstantType;
import com.anyilanxin.anyicloud.corecommon.constant.ISuperType;
import com.anyilanxin.anyicloud.corecommon.constant.model.ConstantDictModel;
import com.anyilanxin.anyicloud.process.utils.StringExpressionUtils;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 表达式类型
 *
 * @author zxh
 * @date 2021-08-01 13:50
 * @since 1.0.0
 */
@Getter
@ConstantType
public enum VariableType implements ISuperType {
    /**
     * 系统流程全局变量
     */
    SYS_GLOBAL(0, "SYS_GLOBAL_", "sys_global", "系统流程全局变量(创建实例时处理)"),

    /**
     * 系统流程局部变量
     */
    SYS_LOCAL(1, "SYS_LOCAL_", "sys_local", "系统流程局部变量(处理任务时处理)"),

    /**
     * 外部全局变量
     */
    EXTERNAL_GLOBAL(2, "EXTERNAL_GLOBAL_", "", "外部全局变量(创建实例时提交)"),

    /**
     * 外部局部变量
     */
    EXTERNAL_LOCAL(3, "EXTERNAL_LOCAL_", "", "外部局部变量(处理任务时处理)");

    /**
     * 类型
     */
    private final int type;

    /**
     * 类型前缀
     */
    private final String typePrefix;

    /**
     * 类型处理策略
     */
    private final String sysHandleStrategy;

    /**
     * 类型描述
     */
    private final String describe;

    VariableType(int type, String typePrefix, String sysHandleStrategy, String describe) {
        this.type = type;
        this.typePrefix = typePrefix;
        this.sysHandleStrategy = sysHandleStrategy;
        this.describe = describe;
    }


    /**
     * 判断某个类型是否存在
     *
     * @param type ${@link String} 类型
     * @return boolean true-存在,false-不存在
     * @author zxh
     * @date 2020-09-11 16:02
     */
    public static boolean isHaveByType(int type) {
        VariableType[] values = VariableType.values();
        for (VariableType value : values) {
            if (value.type == type) {
                return true;
            }
        }
        return false;
    }


    /**
     * 根据类型获取LB
     *
     * @param type ${@link String} 类型
     * @return LbType
     * @author zxh
     * @date 2020-09-11 16:02
     */
    public static VariableType getByType(int type) {
        VariableType[] values = VariableType.values();
        for (VariableType value : values) {
            if (value.type == type) {
                return value;
            }
        }
        return null;
    }


    /**
     * 获取所有的类型
     *
     * @return String ${@link String}
     * @author zxh
     * @date 2020-09-11 16:45
     */
    public static String getAllType() {
        VariableType[] values = VariableType.values();
        StringBuilder sb = new StringBuilder();
        for (VariableType value : values) {
            sb.append("、").append(value.type).append("(").append(value.describe).append(")");
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        VariableType[] values = VariableType.values();
        for (VariableType value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(value.getType() + "");
            dictDto.setTypeDescribe(value.getDescribe());
            dictDto.setTypeName(value.getDescribe());
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }


    /**
     * 获取变量类型
     *
     * @param expression ${@link String}
     * @return VariableType ${@link VariableType}
     * @author zxh
     * @date 2021-08-01 14:01
     */
    public static VariableType getVariableType(String expression) {
        if (StringUtils.isBlank(expression)) {
            return null;
        }
        expression = StringExpressionUtils.getVariableNoSymbol(expression);
        VariableType[] values = VariableType.values();
        for (VariableType value : values) {
            if (expression.startsWith(value.typePrefix)) {
                return value;
            }
        }
        return null;
    }
}
