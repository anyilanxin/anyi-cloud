/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
package com.anyilanxin.skillfull.process.core.constant;

import com.anyilanxin.skillfull.corecommon.annotation.ConstantType;
import com.anyilanxin.skillfull.corecommon.constant.ISuperType;
import com.anyilanxin.skillfull.corecommon.constant.model.ConstantDictModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import org.camunda.bpm.engine.history.HistoricProcessInstance;

/**
* 流程实例状态
*
* @author zxiaozhou
* @date 2021-05-22 23:03
* @since JDK1.8
*/
@Getter
@ConstantType
public enum ProcessInstanceState implements ISuperType {

    /** 活动 */
    ACTIVE(1, HistoricProcessInstance.STATE_ACTIVE, "活动"),

    /** 挂起 */
    SUSPENDED(2, HistoricProcessInstance.STATE_SUSPENDED, "挂起"),

    /** 完成 */
    COMPLETED(3, HistoricProcessInstance.STATE_COMPLETED, "完成"),

    /** 外部终止 */
    EXTERNALLY_TERMINATED(6, HistoricProcessInstance.STATE_EXTERNALLY_TERMINATED, "外部终止"),

    /** 内部终止 */
    INTERNALLY_TERMINATED(7, HistoricProcessInstance.STATE_INTERNALLY_TERMINATED, "内部终止");

    /** 状态 */
    private final int state;

    /** 对应删除状态 */
    private final String strState;

    /** 类型描述 */
    private final String describe;

    ProcessInstanceState(int state, String strState, String describe) {
        this.state = state;
        this.strState = strState;
        this.describe = describe;
    }

    /**
    * 判断某个类型是否存在
    *
    * @param state ${@link Integer} 状态
    * @return boolean true-存在,false-不存在
    * @author zxiaozhou
    * @date 2020-09-11 16:02
    */
    public static boolean isHaveByType(int state) {
        ProcessInstanceState[] values = ProcessInstanceState.values();
        for (ProcessInstanceState value : values) {
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
    * @author zxiaozhou
    * @date 2020-09-11 16:02
    */
    public static ProcessInstanceState getByState(int state) {
        ProcessInstanceState[] values = ProcessInstanceState.values();
        for (ProcessInstanceState value : values) {
            if (state == value.getState()) {
                return value;
            }
        }
        return null;
    }

    /**
    * 根据类型获取
    *
    * @param strState ${@link String} 字符串状态
    * @param deleteReason ${@link String} 删除状态
    * @return LbType
    * @author zxiaozhou
    * @date 2020-09-11 16:02
    */
    public static ProcessInstanceState getByStrState(String strState, String deleteReason) {
        ProcessInstanceState[] values = ProcessInstanceState.values();
        for (ProcessInstanceState value : values) {
            if (value.strState.equals(strState)) {
                if (value == EXTERNALLY_TERMINATED || value == INTERNALLY_TERMINATED) {
                    ProcessInstanceState byDeleteReason = getByDeleteReason(deleteReason);
                    if (Objects.nonNull(byDeleteReason)) {
                        return byDeleteReason;
                    }
                }
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
    * @author zxiaozhou
    * @date 2020-09-11 16:02
    */
    public static ProcessInstanceState getByDeleteReason(String deleteReason) {
        ProcessInstanceState[] values = ProcessInstanceState.values();
        for (ProcessInstanceState value : values) {
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
    * @author zxiaozhou
    * @date 2020-09-11 16:45
    */
    public static String getAllType() {
        ProcessInstanceState[] values = ProcessInstanceState.values();
        StringBuilder sb = new StringBuilder();
        for (ProcessInstanceState value : values) {
            sb.append("、").append(value.state).append("(").append(value.describe).append(")");
        }
        return sb.toString().replaceFirst("、", "");
    }

    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        ProcessInstanceState[] values = ProcessInstanceState.values();
        for (ProcessInstanceState value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(value.getState() + "");
            dictDto.setTypeDescribe(value.getDescribe());
            dictDto.setTypeName(value.getDescribe());
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
