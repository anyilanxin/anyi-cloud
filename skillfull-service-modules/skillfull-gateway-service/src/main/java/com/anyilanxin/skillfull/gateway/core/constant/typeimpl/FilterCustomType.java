// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.gateway.core.constant.typeimpl;

import com.alibaba.fastjson.JSONObject;
import com.anyilanxin.skillfull.corecommon.annotation.ConstantType;
import com.anyilanxin.skillfull.corecommon.constant.CoreCommonGatewayConstant;
import com.anyilanxin.skillfull.corecommon.constant.ISuperType;
import com.anyilanxin.skillfull.corecommon.constant.model.ConstantDictModel;
import com.anyilanxin.skillfull.gateway.filter.partial.post.CorsWebGatewayFilterFactory;
import com.anyilanxin.skillfull.gateway.filter.partial.post.LogResponseGatewayFilterFactory;
import com.anyilanxin.skillfull.gateway.filter.partial.pre.AuthorizeGatewayFilterFactory;
import com.anyilanxin.skillfull.gateway.filter.partial.pre.LogRequestGatewayFilterFactory;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter.LOAD_BALANCER_CLIENT_FILTER_ORDER;

/**
 * 自定义过滤器类型
 *
 * @author zxiaozhou
 * @date 2020-09-12 10:52
 * @since JDK11
 */
@Getter
@ConstantType
public enum FilterCustomType implements ISuperType {
    /**
     * 鉴权过滤器
     */
    AUTHORIZE(CoreCommonGatewayConstant.AUTHORIZE_FILTER, "鉴权", AuthorizeGatewayFilterFactory.class.getName(), "2", 3),

    /**
     * 日志处理(前置+后置)，前置必须大于LOAD_BALANCER_CLIENT_FILTER_ORDER(10150)，即负载均衡过滤器(ReactiveLoadBalancerClientFilter)的order,否则拿不到真实目标服务ip(request数据)
     */
    LOG_REQUEST("LogRequest,LogResponse", "日志记录", LogRequestGatewayFilterFactory.class.getName() + "," + LogResponseGatewayFilterFactory.class.getName(), LOAD_BALANCER_CLIENT_FILTER_ORDER + 1 + ",-2", 1),

    /**
     * 跨域处理过滤器(后置)
     */
    CORS_WEB("CorsWeb", "跨域处理", CorsWebGatewayFilterFactory.class.getName(), "1", 0);

    /**
     * 过滤器类型
     */
    private final String filterType;

    /**
     * 过滤器描述
     */
    private final String filterTypeDescribe;

    /**
     * 过滤器类型类名称
     */
    private final String filterTypeClassName;

    /**
     * 可添加特殊url类型:0-不可添加,1-白名单,2-黑名单,3-黑白名单
     */
    private final int specialUrlType;

    /**
     * 过滤器顺序
     */
    private final String order;


    FilterCustomType(String filterType, String filterTypeDescribe, String filterTypeClassName, String order, int specialUrlType) {
        this.filterType = filterType;
        this.filterTypeDescribe = filterTypeDescribe;
        this.filterTypeClassName = filterTypeClassName;
        this.order = order;
        this.specialUrlType = specialUrlType;
    }


    /**
     * 判断某个类型是否存在
     *
     * @param filterType ${@link String} 类型
     * @return boolean true-存在,false-不存在
     * @author zxiaozhou
     * @date 2020-09-11 16:02
     */
    public static boolean isHaveByType(String filterType) {
        FilterCustomType[] values = FilterCustomType.values();
        for (FilterCustomType value : values) {
            if (value.filterType.contains(filterType)) {
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
        FilterCustomType[] values = FilterCustomType.values();
        StringBuilder sb = new StringBuilder();
        for (FilterCustomType value : values) {
            sb.append("、").append(value.filterType);
        }
        return sb.toString().replaceFirst("、", "");
    }


    /**
     * 通过class name获取类型
     *
     * @param className ${@link String} class名称
     * @return FilterCustomPreType ${@link FilterCustomType}
     * @author zxiaozhou
     * @date 2021-09-15 22:56
     */
    public static FilterCustomType getTypeByClassName(String className) {
        FilterCustomType[] values = FilterCustomType.values();
        for (FilterCustomType value : values) {
            if (value.filterTypeClassName.equals(className)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        FilterCustomType[] values = FilterCustomType.values();
        for (FilterCustomType value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(value.filterType);
            dictDto.setTypeDescribe(value.getFilterTypeDescribe());
            dictDto.setTypeName(value.getFilterTypeClassName());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("specialUrlType", value.getSpecialUrlType());
            dictDto.setExtendInfo(jsonObject);
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
