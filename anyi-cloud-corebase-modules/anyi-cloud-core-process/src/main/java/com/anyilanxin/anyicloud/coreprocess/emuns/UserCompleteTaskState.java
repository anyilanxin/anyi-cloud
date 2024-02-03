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

package com.anyilanxin.anyicloud.coreprocess.emuns;

import com.anyilanxin.anyicloud.corecommon.annotation.ConstantType;
import com.anyilanxin.anyicloud.corecommon.constant.ISuperType;
import com.anyilanxin.anyicloud.corecommon.constant.model.ConstantDictModel;
import com.anyilanxin.anyicloud.coreprocess.constant.AnYiTaskVariableConstant;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 流程人员身份类型
 *
 * @author zxh
 * @date 2021-05-22 23:03
 * @since 1.0.0
 */
@Getter
@ConstantType
public enum UserCompleteTaskState implements ISuperType {
    /**
     * 撤回
     */
    REVOKED(10, "revoked", AnYiTaskVariableConstant.TASK_STATUS, "撤回"),

    /**
     * 驳回
     */
    DISMISS(11, "dismiss", AnYiTaskVariableConstant.TASK_STATUS, "驳回"),

    /**
     * 打回(回到初始节点)
     */
    BACK_TO(12, "back_to", AnYiTaskVariableConstant.TASK_STATUS, "打回"),

    /**
     * 不同意
     */
    NOT_AGREE(20, "not_agree", AnYiTaskVariableConstant.TASK_STATUS, "不同意"),

    /**
     * 同意
     */
    AGREE(21, "agree", AnYiTaskVariableConstant.TASK_STATUS, "同意"),

    /**
     * 拒绝(特权，直接取消流程)
     */
    REFUSED(30, "refused", AnYiTaskVariableConstant.TASK_STATUS, "拒绝"),

    /**
     * 作废
     */
    INVALID(31, "invalid", AnYiTaskVariableConstant.TASK_STATUS, "作废");

    /**
     * 类型
     */
    @JsonValue
    @EnumValue
    private final int value;
    /**
     * 类型名称
     */
    private final String status;
    /**
     * 变量存储key
     */
    private final String variableKey;

    /**
     * 类型描述
     */
    private final String describe;

    UserCompleteTaskState(int value, String status, String variableKey, String describe) {
        this.value = value;
        this.status = status;
        this.variableKey = variableKey;
        this.describe = describe;
    }


    /**
     * 获取某个字符串前面匹配的Lb类型
     *
     * @param status ${@link String} 待匹配的字符串
     * @return LbType ${@link UserCompleteTaskState} 匹配的类型
     * @author zxh
     * @date 2020-09-28 09:32
     */
    public static UserCompleteTaskState getByStatus(String status) {
        UserCompleteTaskState[] values = UserCompleteTaskState.values();
        for (UserCompleteTaskState taskStatus : values) {
            if (status.equalsIgnoreCase(taskStatus.status)) {
                return taskStatus;
            }
        }
        return null;
    }


    /**
     * 判断某个值是否存在
     *
     * @param value ${@link Integer} 值
     * @return boolean true-存在,false-不存在
     * @author zxh
     * @date 2020-09-11 16:02
     */
    public static boolean isHaveByValue(Integer value) {
        UserCompleteTaskState[] values = UserCompleteTaskState.values();
        for (UserCompleteTaskState status : values) {
            if (status.value == value) {
                return true;
            }
        }
        return false;
    }


    /**
     * 根据类型获取LB
     *
     * @param value ${@link Integer} 类型
     * @return LbType
     * @author zxh
     * @date 2020-09-11 16:02
     */
    public static UserCompleteTaskState getByValue(int value) {
        UserCompleteTaskState[] values = UserCompleteTaskState.values();
        for (UserCompleteTaskState status : values) {
            if (status.value == value) {
                return status;
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
    public static String getAllValue() {
        UserCompleteTaskState[] values = UserCompleteTaskState.values();
        StringBuilder sb = new StringBuilder();
        for (UserCompleteTaskState value : values) {
            sb.append("、").append(value.value).append("(").append(value.describe).append(")");
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        UserCompleteTaskState[] values = UserCompleteTaskState.values();
        for (UserCompleteTaskState value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(value.getValue() + "");
            dictDto.setTypeDescribe(value.getDescribe());
            dictDto.setTypeName(value.getDescribe());
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
