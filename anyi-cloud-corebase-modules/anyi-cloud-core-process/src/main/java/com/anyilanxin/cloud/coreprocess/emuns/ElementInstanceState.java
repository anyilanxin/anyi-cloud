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

package com.anyilanxin.cloud.coreprocess.emuns;

import com.anyilanxin.cloud.corecommon.annotation.ConstantType;
import com.anyilanxin.cloud.corecommon.constant.ISuperType;
import com.anyilanxin.cloud.corecommon.constant.model.ConstantDictModel;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 元素实例状态
 *
 * @author zxh
 * @date 2021-05-22 23:03
 * @since 1.0.0
 */
@Getter
@ConstantType
public enum ElementInstanceState implements ISuperType {
    /**
     * 未知
     */
    UNKNOWN(-1, "UNKNOWN", "未知"),

    /**
     * 活动，初始创建，如果一直停留此处一般为出现了incident
     */
    ELEMENT_ACTIVATING(2, "ACTIVATING", "活动"),

    /**
     * 完成
     */
    ELEMENT_COMPLETED(5, "COMPLETED", "完成"),

    /**
     * 终止
     */
    ELEMENT_TERMINATED(7, "TERMINATED", "终止");

    /**
     * 状态
     */
    @JsonValue
    @EnumValue
    private final int state;

    /**
     * 对应删除状态
     */
    private final String strState;

    /**
     * 类型描述
     */
    private final String describe;

    ElementInstanceState(int state, String strState, String describe) {
        this.state = state;
        this.strState = strState;
        this.describe = describe;
    }


    /**
     * 判断某个类型是否存在
     *
     * @param state ${@link Integer} 状态
     * @return boolean true-存在,false-不存在
     * @author zxh
     * @date 2020-09-11 16:02
     */
    public static boolean isHaveByType(int state) {
        ElementInstanceState[] values = ElementInstanceState.values();
        for (ElementInstanceState value : values) {
            if (state == value.getState()) {
                return true;
            }
        }
        return false;
    }


    /**
     * 根据类型获取
     *
     * @param state ${@link Integer} 状态
     * @return LbType
     * @author zxh
     * @date 2020-09-11 16:02
     */
    public static ElementInstanceState getByState(int state) {
        ElementInstanceState[] values = ElementInstanceState.values();
        for (ElementInstanceState value : values) {
            if (state == value.getState()) {
                return value;
            }
        }
        return null;
    }


    /**
     * 根据类型获取
     *
     * @param deleteReason ${@link String} 删除状态
     * @return LbType
     * @author zxh
     * @date 2020-09-11 16:02
     */
    public static ElementInstanceState getByDeleteReason(String deleteReason) {
        ElementInstanceState[] values = ElementInstanceState.values();
        for (ElementInstanceState value : values) {
            if (value.strState.equals(deleteReason)) {
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
        ElementInstanceState[] values = ElementInstanceState.values();
        StringBuilder sb = new StringBuilder();
        for (ElementInstanceState value : values) {
            sb.append("、").append(value.state).append("(").append(value.describe).append(")");
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        ElementInstanceState[] values = ElementInstanceState.values();
        for (ElementInstanceState value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(value.getState() + "");
            dictDto.setTypeDescribe(value.getDescribe());
            dictDto.setTypeName(value.getDescribe());
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
