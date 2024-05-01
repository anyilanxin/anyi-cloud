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
import java.util.Objects;

/**
 * 任务操作类型
 *
 * @author zxh
 * @date 2021-05-22 23:03
 * @since 1.0.0
 */
@Getter
@ConstantType
public enum UserTaskHandleType implements ISuperType {
    /**
     * 正常办理
     */
    NORMAL("taskHandleNormal", "正常办理"),

    /**
     * 跳过第一个用户任务
     *
     * @param null
     * @author zxh
     * @date 2022-06-12 18:28
     */
    SKIP_FIRST_USER_TASK("taskHandleSkipFirstUserTask", "跳过第一个用户任务"),

    /**
     * 打回待处理
     */
    BACK_TO_WAIT_HANDLE_FIRST_USER_TASK("taskHandleBackToWaitHandle", "打回待处理"),

    /**
     * 自动完成用户任务
     */
    AUTOMATIC_COMPLETE_USER_TASK("taskHandleAutomaticCompleteUserTask", "自动完成用户任务"),

    /**
     * 驳回
     */
    DISMISS("taskHandleDismiss", "驳回任务"),

    /**
     * 撤回
     */
    REVOKED("taskHandleRevoked", "撤回任务"),

    /**
     * 取回
     */
    CANCEL("taskHandleCancel", "任务被取回"),

    /**
     * 退回
     */
    BACK_TO("taskHandleBackTo", "打回任务"),

    /**
     * 通过
     */
    AGREE("taskHandleAgree", "通过任务"),

    /**
     * 拒绝
     */
    REFUSED("taskHandleRefused", "审批拒绝"),

    ;

    /**
     * 类型
     */
    @JsonValue
    @EnumValue
    private final String type;

    /**
     * 类型描述
     */
    private final String describe;

    UserTaskHandleType(String type, String describe) {
        this.type = type;
        this.describe = describe;
    }


    /**
     * 判断某个类型是否存在
     *
     * @param type ${@link String} 值
     * @return boolean true-存在,false-不存在
     * @author zxh
     * @date 2020-09-11 16:02
     */
    public static boolean isHaveByType(String type) {
        UserTaskHandleType[] values = UserTaskHandleType.values();
        for (UserTaskHandleType taskType : values) {
            if (Objects.equals(taskType.type, type)) {
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
    public static UserTaskHandleType getByType(String type) {
        UserTaskHandleType[] values = UserTaskHandleType.values();
        for (UserTaskHandleType taskType : values) {
            if (Objects.equals(taskType.type, type)) {
                return taskType;
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
        UserTaskHandleType[] values = UserTaskHandleType.values();
        StringBuilder sb = new StringBuilder();
        for (UserTaskHandleType taskType : values) {
            sb.append("、").append(taskType.type).append("(").append(taskType.describe).append(")");
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        UserTaskHandleType[] values = UserTaskHandleType.values();
        for (UserTaskHandleType taskType : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(taskType.type);
            dictDto.setTypeDescribe(taskType.getDescribe());
            dictDto.setTypeName(taskType.getDescribe());
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
