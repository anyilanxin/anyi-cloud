

package com.anyilanxin.anyicloud.gateway.core.constant.typeimpl;

import com.anyilanxin.anyicloud.corecommon.annotation.ConstantType;
import com.anyilanxin.anyicloud.corecommon.constant.ISuperType;
import com.anyilanxin.anyicloud.corecommon.constant.model.ConstantDictModel;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import org.springframework.cloud.gateway.filter.factory.PrefixPathGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.RewritePathGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.StripPrefixGatewayFilterFactory;

/**
 * 系统过滤器类型
 *
 * @author zxh
 * @date 2020-09-12 10:52
 * @since 1.0.0
 */
@Getter
@ConstantType
public enum FilterSysType implements ISuperType {
    /**
     * 路由重写
     */
    REWRITE_PATH("RewritePath", "重写路由", RewritePathGatewayFilterFactory.class.getName()),

    /**
     * 前缀剥离
     */
    STRIP_PREFIX("StripPrefix", "前缀剥离", StripPrefixGatewayFilterFactory.class.getName()),

    /**
     * 前缀追加
     */
    PREFIX_PATH("PrefixPath", "前缀追加", PrefixPathGatewayFilterFactory.class.getName());

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

    FilterSysType(String filterType, String filterTypeDescribe, String filterTypeClassName) {
        this.filterType = filterType;
        this.filterTypeDescribe = filterTypeDescribe;
        this.filterTypeClassName = filterTypeClassName;
    }


    /**
     * 判断某个类型是否存在
     *
     * @param filterType ${@link String} 类型
     * @return boolean true-存在,false-不存在
     * @author zxh
     * @date 2020-09-11 16:02
     */
    public static boolean isHaveByType(String filterType) {
        FilterSysType[] values = FilterSysType.values();
        for (FilterSysType value : values) {
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
     * @author zxh
     * @date 2020-09-11 16:45
     */
    public static String getAllType() {
        FilterSysType[] values = FilterSysType.values();
        StringBuilder sb = new StringBuilder();
        for (FilterSysType value : values) {
            sb.append("、").append(value.filterType);
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        FilterSysType[] values = FilterSysType.values();
        for (FilterSysType value : values) {
            ConstantDictModel dictDto = new ConstantDictModel();
            dictDto.setType(value.filterType);
            dictDto.setTypeDescribe(value.getFilterTypeDescribe());
            dictDto.setTypeName(value.getFilterTypeClassName());
            constantDictDtoList.add(dictDto);
        }
        return constantDictDtoList;
    }
}
