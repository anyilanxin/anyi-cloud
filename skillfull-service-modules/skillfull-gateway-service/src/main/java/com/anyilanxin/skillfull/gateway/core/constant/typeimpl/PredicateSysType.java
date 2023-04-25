package com.anyilanxin.skillfull.gateway.core.constant.typeimpl;

import com.anyilanxin.skillfull.corecommon.annotation.ConstantType;
import com.anyilanxin.skillfull.corecommon.constant.ISuperType;
import com.anyilanxin.skillfull.corecommon.constant.model.ConstantDictModel;
import lombok.Getter;
import org.springframework.cloud.gateway.handler.predicate.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 断言常量
 *
 * @author zxiaozhou
 * @date 2020-09-11 12:12
 * @since JDK11
 */
@Getter
@ConstantType
public enum PredicateSysType implements ISuperType {
    /**
     * 路径匹配
     */
    PATH("Path", "请求路径正则匹配", PathRoutePredicateFactory.class.getName()),
    /**
     * host
     */
    HOST("Host", "Host匹配", HostRoutePredicateFactory.class.getName()),
//    /**
//     * 请求体参数匹配
//     */
//    READ_BODY("ReadBody", "请求体参数匹配", ReadBodyRoutePredicateFactory.class.getName()),

    /**
     * 远程地址
     */
    REMOTE_ADDR("RemoteAddr", "请求远程地址匹配", RemoteAddrRoutePredicateFactory.class.getName()),
//    /**
//     * 某时间后
//     */
//    AFTER("After", "指定时间点之后", AfterRoutePredicateFactory.class.getName()),
//
//    /**
//     * 某时间前
//     */
//    BEFORE("Before", "指定时间点之前", BeforeRoutePredicateFactory.class.getName()),
//
//    /**
//     * 某时间段之间
//     */
//    BETWEEN("Between", "指定时间点之间", BetweenRoutePredicateFactory.class.getName()),

    /**
     * cookie匹配
     */
    COOKIE("Cookie", "Cookie正则匹配", CookieRoutePredicateFactory.class.getName()),

    /**
     * 请求头匹配
     */
    HEADER("Header", "请求头属性正则匹配", HeaderRoutePredicateFactory.class.getName()),

//    /**
//     * 路由
//     */
//    CLOUD_FOUNDRY("CloudFoundry", "请求头包含指定属性", CloudFoundryRouteServiceRoutePredicateFactory.class.getName()),

    /**
     * 请求方法
     */
    METHOD("Method", "请求方法匹配", MethodRoutePredicateFactory.class.getName()),

    /**
     * get参数匹配
     */
    QUERY("Query", "请求参数正则匹配", QueryRoutePredicateFactory.class.getName());
//
//
//    /**
//     * 权重
//     */
//    WEIGHT("Weight", "路由组指定权重", WeightRoutePredicateFactory.class.getName());


    /**
     * 断言类型
     */
    private final String predicateType;

    /**
     * 断言描述
     */
    private final String predicateTypeDescribe;

    /**
     * 断言类型类名称
     */
    private final String predicateTypeClassName;

    PredicateSysType(String predicateType, String predicateTypeDescribe, String predicateTypeClassName) {
        this.predicateType = predicateType;
        this.predicateTypeDescribe = predicateTypeDescribe;
        this.predicateTypeClassName = predicateTypeClassName;
    }


    /**
     * 判断某个类型是否存在
     *
     * @param predicateType ${@link String} 类型
     * @return boolean true-存在,false-不存在
     * @author zxiaozhou
     * @date 2020-09-11 16:02
     */
    public static boolean isHaveByType(String predicateType) {
        PredicateSysType[] values = PredicateSysType.values();
        for (PredicateSysType value : values) {
            if (value.predicateType.equals(predicateType)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取所有的类型
     *
     * @return String ${@link String}
     * @author zxiaozhou
     * @date 2020-09-11 16:45
     */
    public static String getAllType() {
        PredicateSysType[] values = PredicateSysType.values();
        StringBuilder sb = new StringBuilder();
        for (PredicateSysType value : values) {
            sb.append("、").append(value.predicateType);
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        PredicateSysType[] values = PredicateSysType.values();
        for (PredicateSysType value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(value.getPredicateType());
            dictDto.setTypeDescribe(value.getPredicateTypeDescribe());
            dictDto.setTypeName(value.getPredicateTypeClassName());
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
