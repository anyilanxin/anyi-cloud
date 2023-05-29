

package com.anyilanxin.anyicloud.messagerpc.constant.impl;

import com.anyilanxin.anyicloud.corecommon.annotation.ConstantType;
import com.anyilanxin.anyicloud.corecommon.constant.ISuperType;
import com.anyilanxin.anyicloud.corecommon.constant.model.ConstantDictModel;
import com.anyilanxin.anyicloud.messagerpc.constant.MsgTemplateEmailChannelConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Getter;

/**
 * 邮件消息渠道
 *
 * @author zxh
 * @date 2020-09-12 10:52
 * @since 1.0.0
 */
@Getter
@ConstantType
public enum MsgTemplateEmailChannelType implements ISuperType {
    /**
     * 163邮箱渠道
     */
    MAIL_163(MsgTemplateEmailChannelConstant.MAIL_163, "163邮箱渠道"),

    /**
     * qq邮箱渠道
     */
    MAIL_QQ(MsgTemplateEmailChannelConstant.MAIL_QQ, "qq邮箱渠道");

    /**
     * 类型
     */
    private final String type;

    /**
     * 描述
     */
    private final String describe;

    MsgTemplateEmailChannelType(String type, String describe) {
        this.type = type;
        this.describe = describe;
    }


    /**
     * 判断某个类型是否存在
     *
     * @param type ${@link Integer} 类型
     * @return boolean true-存在,false-不存在
     * @author zxh
     * @date 2020-09-11 16:02
     */
    public static boolean isHaveByType(String type) {
        MsgTemplateEmailChannelType[] values = MsgTemplateEmailChannelType.values();
        for (MsgTemplateEmailChannelType value : values) {
            if (Objects.equals(value.type, type)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取所有的类型
     *
     * @return String ${@link String} 拼接为字符串返回,多个顿号隔开
     * @author zxh
     * @date 2020-09-11 16:45
     */
    public static String getAllType() {
        MsgTemplateEmailChannelType[] values = MsgTemplateEmailChannelType.values();
        StringBuilder sb = new StringBuilder();
        for (MsgTemplateEmailChannelType value : values) {
            sb.append("、").append(value.type);
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        MsgTemplateEmailChannelType[] values = MsgTemplateEmailChannelType.values();
        for (MsgTemplateEmailChannelType value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(value.type);
            dictDto.setTypeDescribe(value.describe);
            dictDto.setTypeName(value.describe);
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
