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

package com.anyilanxin.cloud.processadapter.constant.impl;

import com.anyilanxin.cloud.corecommon.annotation.ConstantType;
import com.anyilanxin.cloud.corecommon.constant.ISuperType;
import com.anyilanxin.cloud.corecommon.constant.model.ConstantDictModel;
import com.anyilanxin.cloud.processadapter.model.CallbackHttpModel;
import com.anyilanxin.cloud.processadapter.model.CallbackMqMsgModel;
import com.anyilanxin.cloud.processadapter.model.CallbackSocketMsgModel;
import com.anyilanxin.cloud.processadapter.model.CallbackStreamModel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 参数位置
 *
 * @author zxh
 * @date 2020-09-12 10:52
 * @since 1.0.0
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
     * @author zxh
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
     * @author zxh
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
