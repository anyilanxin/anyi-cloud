/*
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   8.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   9.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.loggingcommon.constant.impl;

import com.anyilanxin.anyicloud.corecommon.annotation.ConstantType;
import com.anyilanxin.anyicloud.corecommon.constant.ISuperType;
import com.anyilanxin.anyicloud.corecommon.constant.model.ConstantDictModel;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * 按钮权限检测类型
 *
 * @author 安一老厨
 * @date 2020-09-11 12:12
 * @since 1.0.0
 */
@Getter
@ConstantType
public enum OperateType implements ISuperType {

    /**
     * 查询
     */
    QUERY(1, "查询"),

    /**
     * 添加
     */
    ADD(2, "添加"),

    /**
     * 修改
     */
    UPDATE(3, "修改"),

    /**
     * 删除
     */
    DELETE(4, "删除"),

    /**
     * 其他
     */
    OTHER(5, "其他");

    /**
     * 类型
     */
    private final int type;

    /**
     * 类型描述
     */
    private final String typeDescribe;

    OperateType(int type, String typeDescribe) {
        this.type = type;
        this.typeDescribe = typeDescribe;
    }


    /**
     * 判断某个类型是否存在
     *
     * @param type ${@link String} 类型
     * @return boolean true-存在,false-不存在
     * @author 安一老厨
     * @date 2020-09-11 16:02
     */
    public static boolean isHaveByType(int type) {
        OperateType[] values = OperateType.values();
        for (OperateType value : values) {
            if (value.type == type) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断某个类型是否存在
     *
     * @param type 类型
     * @return boolean true-存在,false-不存在
     * @author 安一老厨
     * @date 2020-09-11 16:02
     */
    public static OperateType getComplainByType(int type) {
        OperateType[] values = OperateType.values();
        for (OperateType value : values) {
            if (value.type == type) {
                return value;
            }
        }
        return null;
    }


    /**
     * 获取所有的类型
     *
     * @return String ${@link String}
     * @author 安一老厨
     * @date 2020-09-11 16:45
     */
    public static String getAllType() {
        OperateType[] values = OperateType.values();
        StringBuilder sb = new StringBuilder();
        for (OperateType value : values) {
            sb.append("、").append(value.type);
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        OperateType[] values = OperateType.values();
        for (OperateType value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(String.valueOf(value.getType()));
            dictDto.setTypeName(value.getTypeDescribe());
            dictDto.setTypeDescribe(value.getTypeDescribe());
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
