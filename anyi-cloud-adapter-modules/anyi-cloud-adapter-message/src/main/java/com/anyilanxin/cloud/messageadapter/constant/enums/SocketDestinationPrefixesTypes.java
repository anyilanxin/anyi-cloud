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

package com.anyilanxin.cloud.messageadapter.constant.enums;

import com.anyilanxin.cloud.corecommon.annotation.ConstantType;
import com.anyilanxin.cloud.corecommon.constant.ISuperType;
import com.anyilanxin.cloud.corecommon.constant.model.ConstantDictModel;
import com.anyilanxin.cloud.messageadapter.constant.SocketDestinationPrefixes;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 授权类型
 *
 * @author zxh
 * @date 2020-09-12 10:52
 * @since 1.0.0
 */
@Getter
@ConstantType
public enum SocketDestinationPrefixesTypes implements ISuperType {

    /**
     * 流程引擎
     */
    TOPIC_PROCESS(SocketDestinationPrefixes.TOPIC_PROCESS, "流程引擎"),


    /**
     * 菜单相关
     */
    TOPIC_MENU(SocketDestinationPrefixes.TOPIC_MENU, "菜单相关"),


    /**
     * 通知提醒
     */
    TOPIC_NOTICE(SocketDestinationPrefixes.TOPIC_NOTICE, "通知提醒"),


    /**
     * 全局系统
     */
    TOPIC_GROBAL(SocketDestinationPrefixes.TOPIC_GROBAL, "全局系统"),

    /**
     * 聊天
     */
    TOPIC_CHAT(SocketDestinationPrefixes.TOPIC_CHAT, "聊天"),

    ;

    /**
     * 类型
     */
    private final String type;

    /**
     * 描述
     */
    private final String describe;

    SocketDestinationPrefixesTypes(String type, String describe) {
        this.type = type;
        this.describe = describe;
    }


    /**
     * 获取授权类型
     *
     * @param type ${@link String} 类型
     * @return boolean true-存在,false-不存在
     * @author zxh
     * @date 2020-09-11 16:02
     */
    public static SocketDestinationPrefixesTypes getByType(String type) {
        SocketDestinationPrefixesTypes[] values = SocketDestinationPrefixesTypes.values();
        for (SocketDestinationPrefixesTypes value : values) {
            if (value.type.equals(type)) {
                return value;
            }
        }
        return null;
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
        SocketDestinationPrefixesTypes[] values = SocketDestinationPrefixesTypes.values();
        for (SocketDestinationPrefixesTypes value : values) {
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
     * @author zxh
     * @date 2020-09-11 16:45
     */
    public static String getAllType() {
        SocketDestinationPrefixesTypes[] values = SocketDestinationPrefixesTypes.values();
        StringBuilder sb = new StringBuilder();
        for (SocketDestinationPrefixesTypes value : values) {
            sb.append("、").append(value.type);
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        SocketDestinationPrefixesTypes[] values = SocketDestinationPrefixesTypes.values();
        for (SocketDestinationPrefixesTypes value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(value.type);
            dictDto.setTypeDescribe(value.getDescribe());
            dictDto.setTypeName(value.getDescribe());
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}