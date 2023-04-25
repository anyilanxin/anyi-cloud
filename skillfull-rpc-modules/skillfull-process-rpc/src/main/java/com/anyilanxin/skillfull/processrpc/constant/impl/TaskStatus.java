package com.anyilanxin.skillfull.processrpc.constant.impl;

import com.anyilanxin.skillfull.corecommon.annotation.ConstantType;
import com.anyilanxin.skillfull.corecommon.constant.ISuperType;
import com.anyilanxin.skillfull.corecommon.constant.model.ConstantDictModel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 流程人员身份类型
 *
 * @author zxiaozhou
 * @date 2021-05-22 23:03
 * @since JDK1.8
 */
@Getter
@ConstantType
public enum TaskStatus implements ISuperType {
    /**
     * 待审批
     */
    WAIT_AUDIT(0, "wait_audit", "待审批"),

    /**
     * 审批中
     */
    AUDIT(1, "audit", "审批中"),

    /**
     * 打回待处理
     */
    BACK_TO_WAIT_HANDLE(2, "back_to_wait_handle", "打回待处理"),

    /**
     * 撤回
     */
    REVOKED(10, "revoked", "撤回"),

    /**
     * 驳回
     */
    DISMISS(11, "dismiss", "驳回"),

    /**
     * 打回(回到初始节点)
     */
    BACK_TO(12, "back_to", "打回"),

    /**
     * 不同意
     */
    NOT_AGREE(20, "not_agree", "不同意"),

    /**
     * 同意
     */
    AGREE(21, "agree", "同意"),

    /**
     * 拒绝(特权，直接取消流程)
     */
    REFUSED(30, "refused", "拒绝"),

    /**
     * 作废
     */
    INVALID(31, "invalid", "作废");


    /**
     * 类型
     */
    private final int value;

    /**
     * 类型名称
     */
    private final String status;


    /**
     * 类型描述
     */
    private final String describe;

    TaskStatus(int value, String status, String describe) {
        this.value = value;
        this.status = status;
        this.describe = describe;
    }


    /**
     * 判断某个值是否存在
     *
     * @param value ${@link Integer} 值
     * @return boolean true-存在,false-不存在
     * @author zxiaozhou
     * @date 2020-09-11 16:02
     */
    public static boolean isHaveByValue(Integer value) {
        TaskStatus[] values = TaskStatus.values();
        for (TaskStatus status : values) {
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
     * @author zxiaozhou
     * @date 2020-09-11 16:02
     */
    public static TaskStatus getByValue(int value) {
        TaskStatus[] values = TaskStatus.values();
        for (TaskStatus status : values) {
            if (status.value == value) {
                return status;
            }
        }
        return null;
    }


    /**
     * 获取某个字符串前面匹配的Lb类型
     *
     * @param status ${@link String} 待匹配的字符串
     * @return LbType ${@link TaskStatus} 匹配的类型
     * @author zxiaozhou
     * @date 2020-09-28 09:32
     */
    public static TaskStatus getByStatus(String status) {
        TaskStatus[] values = TaskStatus.values();
        for (TaskStatus taskStatus : values) {
            if (status.equalsIgnoreCase(taskStatus.status)) {
                return taskStatus;
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
    public static String getAllValue() {
        TaskStatus[] values = TaskStatus.values();
        StringBuilder sb = new StringBuilder();
        for (TaskStatus value : values) {
            sb.append("、").append(value.value).append("(").append(value.describe).append(")");
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        TaskStatus[] values = TaskStatus.values();
        for (TaskStatus value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(value.getValue() + "");
            dictDto.setTypeDescribe(value.getDescribe());
            dictDto.setTypeName(value.getDescribe());
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
