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
import lombok.Getter;

/**
* 流程人员身份类型
*
* @author zxiaozhou
* @date 2021-05-22 23:03
* @since JDK1.8
*/
@Getter
@ConstantType
public enum ModelStateType implements ISuperType {
    /** 未部署 */
    NO_DEPLOYMENT(0, "未部署"),

    /** 已经部署 */
    DEPLOYMENT(1, "已经部署"),

    /** 新版本待部署 */
    NEW_VERSION(2, "新版本待部署");

    /** 类型 */
    private final int type;

    /** 类型描述 */
    private final String describe;

    ModelStateType(int type, String describe) {
        this.type = type;
        this.describe = describe;
    }

    /**
    * 判断某个类型是否存在
    *
    * @param type ${@link String} 类型
    * @return boolean true-存在,false-不存在
    * @author zxiaozhou
    * @date 2020-09-11 16:02
    */
    public static boolean isHaveByType(int type) {
        ModelStateType[] values = ModelStateType.values();
        for (ModelStateType value : values) {
            if (value.type == type) {
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
    * @author zxiaozhou
    * @date 2020-09-11 16:02
    */
    public static ModelStateType getByType(int type) {
        ModelStateType[] values = ModelStateType.values();
        for (ModelStateType value : values) {
            if (value.type == type) {
                return value;
            }
        }
        return null;
    }

    /**
    * 获取某个字符串前面匹配的Lb类型
    *
    * @param describe ${@link String} 待匹配的字符串
    * @return LbType ${@link ModelStateType} 匹配的类型
    * @author zxiaozhou
    * @date 2020-09-28 09:32
    */
    public static ModelStateType getByTypeName(String describe) {
        ModelStateType[] values = ModelStateType.values();
        for (ModelStateType value : values) {
            if (value.describe.equalsIgnoreCase(describe)) {
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
        ModelStateType[] values = ModelStateType.values();
        StringBuilder sb = new StringBuilder();
        for (ModelStateType value : values) {
            sb.append("、").append(value.type).append("(").append(value.describe).append(")");
        }
        return sb.toString().replaceFirst("、", "");
    }

    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        ModelStateType[] values = ModelStateType.values();
        for (ModelStateType value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(value.getType() + "");
            dictDto.setTypeDescribe(value.getDescribe());
            dictDto.setTypeName(value.getDescribe());
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
