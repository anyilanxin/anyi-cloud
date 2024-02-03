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
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 流程任务变量
 *
 * @author zxh
 * @date 2021-05-22 23:03
 * @since 1.0.0
 */
@Getter
@ConstantType
public enum UserTaskVariableType implements ISuperType {

    /**
     * 任务审批人所有信息字符串
     */
    TASK_ASSIGNEE_USER_INFO_STR("taskUserStr", "taskUserStr", "任务审批人所有信息字符串"),

    /**
     * 跳过第一个用户任务
     */
    SKIP_FIRST_USER_TASK("taskSkipFirst", "taskSkipFirst", "跳过第一个用户任务"),

    /**
     * 流程任务标题
     */
    TASK_TITLE("taskTitle", "taskTitle", "任务标题"),

    /**
     * 任务表单数据
     */
    TASK_FORM("taskForm", "taskForm", "任务表单数据"),

    /**
     * 抄送任务阅读状态
     */
    COPY_TASK_READ_STATUS("taskCopyReadStatus", "taskCopyReadStatus", "阅读状态:0-未阅读,1-已阅读"),

    /**
     * 任务类型
     */
    TASK_TYPE("taskType", "taskType", "任务类型,具体参考:TaskType"),

    /**
     * 会签同意实例数
     */
    NR_OF_APPROVAL_INSTANCES("nrOfApprovalInstances", "nrOfApprovalInstances", "会签同意实例数"),
    /**
     * 会签不同意实例数
     */
    NR_OF_NOT_APPROVAL_INSTANCES("nrOfNotApprovalInstances", "nrOfNotApprovalInstances", "会签不同意实例数"),
    /**
     * 任务审批状态
     */
    NR_OF_INSTANCE_STATUS("nrOfInstanceStatus", "nrOfInstanceStatus", "审批任务状态，具体值参照UserTaskState"),
    /**
     * 任务审批状态完成信息
     */
    NR_OF_INSTANCE_APPROVE_INFO("nrOfInstanceApproveInfo", "nrOfInstanceApproveInfo", "任务审批状态完成信息"),
    /**
     * 任务审批用户信息
     */
    NR_OF_INSTANCE_USER_INFO("nrOfInstanceUserInfo", "nrOfInstanceUserInfo", "任务审批用户信息,json字符串，序列化后为:ProcessUserModel"),

    /**
     * 任务抄送用户信息
     */
    TASK_COPY_USER_INFO("taskCopyUserInfo", "taskCopyUserInfo", "任务抄送用户信息,json字符串，序列化后为:AnYiProcessSimpleUserModel"),

    /**
     * 委托任务审批用户信息
     */
    NR_OF_INSTANCE_DELEGATE_USER_INFO("nrOfInstanceDelegateUserInfo", "nrOfInstanceDelegateUserInfo", "委托任务审批用户信息,json字符串，序列化后为:ProcessUserModel"),

    /**
     * 委托任务审批状态
     */
    NR_OF_INSTANCE_DELEGATE_STATUS("nrOfInstanceDelegateStatus", "nrOfInstanceDelegateStatus", "委托审批任务状态，具体值参照UserTaskState"),

    /**
     * 委托任务审批状态完成信息
     */
    NR_OF_INSTANCE_DELEGATE_APPROVE_INFO("nrOfInstanceDelegateApproveInfo", "nrOfInstanceDelegateApproveInfo", "委托任务审批状态完成信息"),

    /**
     * 最近用户任务定义key
     */
    LAST_TASK_DEFINITION_KEY("lastTaskDefinitionKey", "lastTaskDefinitionKey", "最近用户任务定义key"),

    /**
     * 任务操作类型
     */
    NR_OF_INSTANCE_HANDLE_TYPE("nrOfInstanceHandleType", "nrOfInstanceHandleType", "任务操作类型，具体参照TaskHandleType");
    /**
     * 类型
     */
    private final String type;

    /**
     * 类型名称
     */
    private final String variableKey;

    /**
     * 类型描述
     */
    private final String describe;

    UserTaskVariableType(String type, String variableKey, String describe) {
        this.type = type;
        this.variableKey = variableKey;
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
    public static boolean isHaveByType(String type) {
        UserTaskVariableType[] values = UserTaskVariableType.values();
        for (UserTaskVariableType value : values) {
            if (value.type.equals(type)) {
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
    public static UserTaskVariableType getByType(String type) {
        UserTaskVariableType[] values = UserTaskVariableType.values();
        for (UserTaskVariableType value : values) {
            if (value.type.equals(type)) {
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
        UserTaskVariableType[] values = UserTaskVariableType.values();
        StringBuilder sb = new StringBuilder();
        for (UserTaskVariableType value : values) {
            sb.append("、").append(value.type).append("(").append(value.describe).append(")");
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        UserTaskVariableType[] values = UserTaskVariableType.values();
        for (UserTaskVariableType value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(value.getType());
            dictDto.setTypeDescribe(value.getDescribe());
            dictDto.setTypeName(value.getDescribe());
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
