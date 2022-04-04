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
import indi.zxiaozhou.skillfull.gateway.modules.filter.partial.post.CorsWebGatewayFilterFactory;
import indi.zxiaozhou.skillfull.gateway.modules.filter.partial.post.EncryptGatewayFilterFactory;
import indi.zxiaozhou.skillfull.gateway.modules.filter.partial.post.LogResponseGatewayFilterFactory;
import indi.zxiaozhou.skillfull.gateway.modules.filter.partial.post.TokenRefreshGatewayFilterFactory;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义过滤器类型
 *
 * @author zxiaozhou
 * @date 2020-09-12 10:52
 * @since JDK11
 */
@Getter
@ConstantType
public enum FilterCustomPostType implements ISuperType {
    /**
     * 跨域处理过滤器(后置)
     */
    CORS_WEB("CorsWeb", "跨域处理", CorsWebGatewayFilterFactory.class.getName(), 1, 0),

    /**
     * 日志处理(response数据)
     */
    LOG_RESPONSE("LogResponse", "日志记录(后置)", LogResponseGatewayFilterFactory.class.getName(), -2, 3),

    /**
     * 加密过滤器(后置)
     */
    ENCRYPT("Encrypt", "加密", EncryptGatewayFilterFactory.class.getName(), -3, 3),

    /**
     * token刷新过滤器
     */
    TOKEN_REFRESH("TokenRefresh", "token刷新", TokenRefreshGatewayFilterFactory.class.getName(), -1000, 0);

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
     * 执行顺序
     */
    private final int order;


    FilterCustomPostType(String filterType, String filterTypeDescribe, String filterTypeClassName, int order, int specialUrlType) {
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
        FilterCustomPostType[] values = FilterCustomPostType.values();
        for (FilterCustomPostType value : values) {
            if (value.filterType.equals(filterType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 通过class name获取类型
     *
     * @param className ${@link String} class名称
     * @return FilterCustomPostType ${@link FilterCustomPostType}
     * @author zxiaozhou
     * @date 2021-09-15 22:56
     */
    public static FilterCustomPostType getTypeByClassName(String className) {
        FilterCustomPostType[] values = FilterCustomPostType.values();
        for (FilterCustomPostType value : values) {
            if (value.filterTypeClassName.equals(className)) {
                return value;
            }
        }
        return null;
    }

    /**
     * 获取所有的类型
     *
     * @return String ${@link String} 拼接为字符串返回,多个顿号隔开
     * @author zxiaozhou
     * @date 2020-09-11 16:45
     */
    public static String getAllType() {
        FilterCustomPostType[] values = FilterCustomPostType.values();
        StringBuilder sb = new StringBuilder();
        for (FilterCustomPostType value : values) {
            sb.append("、").append(value.filterType);
        }
        return sb.toString().replaceFirst("、", "");
    }


    @Override
    public List<ConstantDictModel> getConstantDict() {
        List<ConstantDictModel> constantDictDtoList = new ArrayList<>();
        FilterCustomPostType[] values = FilterCustomPostType.values();
        for (FilterCustomPostType value : values) {
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
