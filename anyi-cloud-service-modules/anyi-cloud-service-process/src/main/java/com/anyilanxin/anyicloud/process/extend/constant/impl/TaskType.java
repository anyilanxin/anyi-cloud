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
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 任务类型
 *
 * @author zxh
 * @date 2021-05-22 23:03
 * @since 1.0.0
 */
@Getter
@ConstantType
public enum TaskType implements ISuperType {
    /**
     * 申请任务
     */
    APPLY_TASK("taskApply", TaskVariableType.TASK_TYPE.getVariableKey(), "申请任务(用户适配第一个任务节点才是提交节点)"),

    /**
     * 审批任务
     */
    APPROVAL_TASK("taskApproval", TaskVariableType.TASK_TYPE.getVariableKey(), "审批任务(标记当前任务非发起任务)"),

    /**
     * 委托任务
     */
    DELEGATE_TASK("taskDelegate", TaskVariableType.TASK_TYPE.getVariableKey(), "委托任务"),

    /**
     * 抄送任务
     */
    SEND_COPY_TASK("taskSendCopy", TaskVariableType.TASK_TYPE.getVariableKey(), "抄送任务"),

    /**
     * 转办任务
     */
    TURN_OVER_TASK("taskTurnOver", TaskVariableType.TASK_TYPE.getVariableKey(), "转办任务"),

    /**
     * 催办任务
     */
    URGENT_TASK("taskUrgent", TaskVariableType.TASK_TYPE.getVariableKey(), "催办任务");

    /**
     * 类型
     */
    private final String value;

    /**
     * 类型标量key
     */
    private final String variableKey;

    /**
     * 类型描述
     */
    private final String describe;

    TaskType(String value, String variableKey, String describe) {
        this.value = value;
        this.variableKey = variableKey;
        this.describe = describe;
    }


    /**
     * 判断某个类型是否存在
     *
     * @param value ${@link String} 值
     * @return boolean true-存在,false-不存在
     * @author zxh
     * @date 2020-09-11 16:02
     */
    public static boolean isHaveByType(String value) {
        TaskType[] values = TaskType.values();
        for (TaskType taskType : values) {
            if (Objects.equals(taskType.value, value)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 根据类型获取LB
     *
     * @param value ${@link String} 类型
     * @return LbType
     * @author zxh
     * @date 2020-09-11 16:02
     */
    public static TaskType getByType(String value) {
        TaskType[] values = TaskType.values();
        for (TaskType taskType : values) {
            if (Objects.equals(taskType.value, value)) {
                return taskType;
            }
        }
        return null;
    }


    /**
     * 获取某个字符串前面匹配的Lb类型
     *
     * @param typeName ${@link String} 待匹配的字符串
     * @return LbType ${@link TaskType} 匹配的类型
     * @author zxh
     * @date 2020-09-28 09:32
     */
    public static TaskType getByTypeName(String typeName) {
        TaskType[] values = TaskType.values();
        for (TaskType value : values) {
            if (typeName.equalsIgnoreCase(value.variableKey)) {
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
        TaskType[] values = TaskType.values();
        StringBuilder sb = new StringBuilder();
        for (TaskType taskType : values) {
            sb.append("、").append(taskType.value).append("(").append(taskType.describe).append(")");
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        TaskType[] values = TaskType.values();
        for (TaskType taskType : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(taskType.getValue() + "");
            dictDto.setTypeDescribe(taskType.getDescribe());
            dictDto.setTypeName(taskType.getDescribe());
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
