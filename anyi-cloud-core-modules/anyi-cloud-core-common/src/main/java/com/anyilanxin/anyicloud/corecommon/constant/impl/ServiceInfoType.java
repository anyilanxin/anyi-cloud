/*
 * Copyright (c) 2021-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
package com.anyilanxin.anyicloud.corecommon.constant.impl;

import com.anyilanxin.anyicloud.corecommon.annotation.ConstantType;
import com.anyilanxin.anyicloud.corecommon.constant.ISuperType;
import com.anyilanxin.anyicloud.corecommon.constant.ServiceConstant;
import com.anyilanxin.anyicloud.corecommon.constant.model.ConstantDictModel;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * 鉴权类型
 *
 * @author zxh
 * @date 2020-09-12 10:52
 * @since 1.0.0
 */
@Getter
@ConstantType
public enum ServiceInfoType implements ISuperType {
    /**
     * 授权服务
     */
    AUTH_SERVICE(ServiceConstant.AUTH_SERVICE, "授权服务", ServiceConstant.AUTH_SERVICE_PATH),
    /**
     * 案例服务
     */
    EXAMPLE_SERVICE(ServiceConstant.EXAMPLE_SERVICE, "案例服务", ServiceConstant.EXAMPLE_SERVICE_PATH),
    /**
     * 网关服务
     */
    GATEWAY_SERVICE(ServiceConstant.GATEWAY_SERVICE, "网关服务", ServiceConstant.GATEWAY_SERVICE_PATH),
    /**
     * 调度服务
     */
    JOB_SERVICE(ServiceConstant.JOB_SERVICE, "调度服务", ServiceConstant.JOB_SERVICE_PATH),
    /**
     * 日志服务
     */
    LOGGING_SERVICE(ServiceConstant.LOGGING_SERVICE, "日志服务", ServiceConstant.LOGGING_SERVICE_PATH),
    /**
     * 消息服务
     */
    MESSAGE_SERVICE(ServiceConstant.MESSAGE_SERVICE, "消息服务", ServiceConstant.MESSAGE_SERVICE_PATH),
    /**
     * 监控服务
     */
    MONITOR_SERVICE(ServiceConstant.MONITOR_SERVICE, "监控服务", ServiceConstant.MONITOR_SERVICE_PATH),
    /**
     * 在线开发服务
     */
    ONLINE_DES_SERVICE(ServiceConstant.ONLINE_DES_SERVICE, "在线开发服务", ServiceConstant.ONLINE_DES_SERVICE_PATH),
    /**
     * 流程服务
     */
    PROCESS_SERVICE(ServiceConstant.PROCESS_SERVICE, "流程服务", ServiceConstant.PROCESS_SERVICE_PATH),
    /**
     * 存储服务
     */
    STORAGE_SERVICE(ServiceConstant.STORAGE_SERVICE, "存储服务", ServiceConstant.STORAGE_SERVICE_PATH),
    /**
     * 系统服务
     */
    SYSTEM_SERVICE(ServiceConstant.SYSTEM_SERVICE, "系统服务", ServiceConstant.SYSTEM_SERVICE_PATH);

    /**
     * 服务编码
     */
    private final String serviceCode;

    /**
     * 服务编码
     */
    private final String serviceName;

    /**
     * 服务请求前缀
     */
    private final String serviceUrlPrefix;

    ServiceInfoType(String serviceCode, String serviceName, String serviceUrlPrefix) {
        this.serviceCode = serviceCode;
        this.serviceName = serviceName;
        this.serviceUrlPrefix = serviceUrlPrefix;
    }


    /**
     * 判断某个类型是否存在
     *
     * @param serviceCode ${@link String} 编码
     * @return boolean true-存在,false-不存在
     * @author zxh
     * @date 2020-09-11 16:02
     */
    public static boolean isHaveByType(String serviceCode) {
        ServiceInfoType[] values = ServiceInfoType.values();
        for (ServiceInfoType value : values) {
            if (value.serviceCode.equals(serviceCode)) {
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
        ServiceInfoType[] values = ServiceInfoType.values();
        StringBuilder sb = new StringBuilder();
        for (ServiceInfoType value : values) {
            sb.append("、").append(value.serviceCode);
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        ServiceInfoType[] values = ServiceInfoType.values();
        for (ServiceInfoType value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(value.getServiceCode());
            dictDto.setTypeDescribe(value.getServiceName());
            dictDto.setTypeName(value.getServiceName());
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
