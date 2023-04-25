package com.anyilanxin.skillfull.corecommon.constant.impl;

import com.anyilanxin.skillfull.corecommon.annotation.ConstantType;
import com.anyilanxin.skillfull.corecommon.constant.ISuperType;
import com.anyilanxin.skillfull.corecommon.constant.ServiceConstant;
import com.anyilanxin.skillfull.corecommon.constant.model.ConstantDictModel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 鉴权类型
 *
 * @author zxiaozhou
 * @date 2020-09-12 10:52
 * @since JDK11
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
     * @author zxiaozhou
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
     * @author zxiaozhou
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
