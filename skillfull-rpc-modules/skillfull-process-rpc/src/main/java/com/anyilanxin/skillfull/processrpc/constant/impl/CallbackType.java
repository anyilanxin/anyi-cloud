// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.processrpc.constant.impl;

import com.anyilanxin.skillfull.corecommon.annotation.ConstantType;
import com.anyilanxin.skillfull.corecommon.constant.ISuperType;
import com.anyilanxin.skillfull.corecommon.constant.model.ConstantDictModel;
import com.anyilanxin.skillfull.processrpc.model.CallbackHttpModel;
import com.anyilanxin.skillfull.processrpc.model.CallbackMqMsgModel;
import com.anyilanxin.skillfull.processrpc.model.CallbackSocketMsgModel;
import com.anyilanxin.skillfull.processrpc.model.CallbackStreamModel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 参数位置
 *
 * @author zxiaozhou
 * @date 2020-09-12 10:52
 * @since JDK11
 */
@Getter
@ConstantType
public enum CallbackType implements ISuperType {
    /**
     * socket消息
     */
    SOCKET_MSG("SOCKET_MSG", "socket消息", CallbackSocketMsgModel.class.getName()),

    /**
     * MQ消息
     */
    MQ_MSG("MQ_MSG", "MQ消息", CallbackMqMsgModel.class.getName()),

    /**
     * stream流(也是基于mq)
     */
    STREAM("STREAM", "stream流", CallbackStreamModel.class.getName()),

    /**
     * http请求
     */
    HTTP("HTTP", "http请求", CallbackHttpModel.class.getName());

    /**
     * 类型
     */
    private final String type;

    /**
     * 描述
     */
    private final String describe;

    /**
     * 回调参数类型
     */
    private final String callParamType;


    CallbackType(String type, String describe, String callParamType) {
        this.type = type;
        this.describe = describe;
        this.callParamType = callParamType;
    }


    /**
     * 判断某个类型是否存在
     *
     * @param type ${@link String} 类型
     * @return boolean true-存在,false-不存在
     * @author zxiaozhou
     * @date 2020-09-11 16:02
     */
    public static boolean isHaveByType(String type) {
        CallbackType[] values = CallbackType.values();
        for (CallbackType value : values) {
            if (value.type.equals(type)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取所有的类型
     *
     * @return String ${@link String} 拼接为字符串返回,多个顿号隔开
     * @author zxiaozhou
     * @date 2020-09-11 16:45
     */
    public static String getAllType() {
        CallbackType[] values = CallbackType.values();
        StringBuilder sb = new StringBuilder();
        for (CallbackType value : values) {
            sb.append("、").append(value.type);
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        CallbackType[] values = CallbackType.values();
        for (CallbackType value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(value.type);
            dictDto.setTypeDescribe(value.getDescribe());
            dictDto.setTypeName(value.getDescribe());
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
