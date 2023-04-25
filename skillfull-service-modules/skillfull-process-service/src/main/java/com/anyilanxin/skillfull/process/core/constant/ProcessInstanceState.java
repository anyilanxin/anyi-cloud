package com.anyilanxin.skillfull.process.core.constant;

import com.anyilanxin.skillfull.corecommon.annotation.ConstantType;
import com.anyilanxin.skillfull.corecommon.constant.ISuperType;
import com.anyilanxin.skillfull.corecommon.constant.model.ConstantDictModel;
import lombok.Getter;
import org.camunda.bpm.engine.history.HistoricProcessInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    /**
     * 活动
     */
    ACTIVE(1, HistoricProcessInstance.STATE_ACTIVE, "活动"),

    /**
     * 挂起
     */
    SUSPENDED(2, HistoricProcessInstance.STATE_SUSPENDED, "挂起"),

    /**
     * 完成
     */
    COMPLETED(3, HistoricProcessInstance.STATE_COMPLETED, "完成"),

    /**
     * 外部终止
     */
    EXTERNALLY_TERMINATED(6, HistoricProcessInstance.STATE_EXTERNALLY_TERMINATED, "外部终止"),

    /**
     * 内部终止
     */
    INTERNALLY_TERMINATED(7, HistoricProcessInstance.STATE_INTERNALLY_TERMINATED, "内部终止");


    /**
     * 状态
     */
    private final int state;

    /**
     * 对应删除状态
     */
    private final String strState;


    /**
     * 类型描述
     */
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
     * @param strState     ${@link String} 字符串状态
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
