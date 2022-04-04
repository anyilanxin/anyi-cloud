// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.gateway.core.constant.typeimpl;

import com.alibaba.fastjson.JSONObject;
import indi.zxiaozhou.skillfull.corecommon.annotation.ConstantType;
import indi.zxiaozhou.skillfull.corecommon.constant.ISuperType;
import indi.zxiaozhou.skillfull.corecommon.constant.model.ConstantDictModel;
import indi.zxiaozhou.skillfull.gateway.modules.filter.partial.pre.AuthorizeGatewayFilterFactory;
import indi.zxiaozhou.skillfull.gateway.modules.filter.partial.pre.BlacklistGatewayFilterFactory;
import indi.zxiaozhou.skillfull.gateway.modules.filter.partial.pre.LogRequestGatewayFilterFactory;
import indi.zxiaozhou.skillfull.gateway.modules.filter.partial.pre.VerifySignGatewayFilterFactory;
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
public enum FilterCustomPreType implements ISuperType {
    /**
     * 路由黑名单过滤器
     */
    BLACKLIST("Blacklist", "黑名单", BlacklistGatewayFilterFactory.class.getName(), 1, 2),
    /**
     * 鉴权过滤器
     */
    AUTHORIZE("Authorize", "鉴权", AuthorizeGatewayFilterFactory.class.getName(), 2, 3),

    /**
     * 验签过滤器
     */
    VERIFY_SIGN("VerifySign", "验签", VerifySignGatewayFilterFactory.class.getName(), 3, 3),

    /**
     * 解密过滤器
     */
    DECRYPT("Decrypt", "解密", DecryptGatewayFilterFactory.class.getName(), 4, 3),

    /**
     * 日志处理，必须大于LOAD_BALANCER_CLIENT_FILTER_ORDER(10150)，即负载均衡过滤器(ReactiveLoadBalancerClientFilter)的order,否则拿不到真实目标服务ip(request数据)
     */
    LOG_REQUEST("LogRequest", "日志记录(前置)", LogRequestGatewayFilterFactory.class.getName(), LOAD_BALANCER_CLIENT_FILTER_ORDER + 1, 3);

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
    private final int order;


    FilterCustomPreType(String filterType, String filterTypeDescribe, String filterTypeClassName, int order, int specialUrlType) {
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
        FilterCustomPreType[] values = FilterCustomPreType.values();
        for (FilterCustomPreType value : values) {
            if (value.filterType.equals(filterType)) {
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
        FilterCustomPreType[] values = FilterCustomPreType.values();
        StringBuilder sb = new StringBuilder();
        for (FilterCustomPreType value : values) {
            sb.append("、").append(value.filterType);
        }
        return sb.toString().replaceFirst("、", "");
    }


    /**
     * 通过class name获取类型
     *
     * @param className ${@link String} class名称
     * @return FilterCustomPreType ${@link FilterCustomPreType}
     * @author zxiaozhou
     * @date 2021-09-15 22:56
     */
    public static FilterCustomPreType getTypeByClassName(String className) {
        FilterCustomPreType[] values = FilterCustomPreType.values();
        for (FilterCustomPreType value : values) {
            if (value.filterTypeClassName.equals(className)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        FilterCustomPreType[] values = FilterCustomPreType.values();
        for (FilterCustomPreType value : values) {
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
