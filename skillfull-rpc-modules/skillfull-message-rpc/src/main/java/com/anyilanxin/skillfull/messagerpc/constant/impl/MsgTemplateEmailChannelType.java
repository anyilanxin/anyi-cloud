// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.messagerpc.constant.impl;

import com.anyilanxin.skillfull.corecommon.annotation.ConstantType;
import com.anyilanxin.skillfull.corecommon.constant.ISuperType;
import com.anyilanxin.skillfull.corecommon.constant.model.ConstantDictModel;
import com.anyilanxin.skillfull.messagerpc.constant.MsgTemplateEmailChannelConstant;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 邮件消息渠道
 *
 * @author zxiaozhou
 * @date 2020-09-12 10:52
 * @since JDK11
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
     * @author zxiaozhou
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
     * @author zxiaozhou
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
