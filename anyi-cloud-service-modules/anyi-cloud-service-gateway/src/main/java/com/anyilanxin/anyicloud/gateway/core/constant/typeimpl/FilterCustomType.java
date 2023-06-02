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
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Zeebe EE、AnYi Cloud EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */
package com.anyilanxin.anyicloud.gateway.core.constant.typeimpl;

import static org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter.LOAD_BALANCER_CLIENT_FILTER_ORDER;

import com.alibaba.fastjson.JSONObject;
import com.anyilanxin.anyicloud.corecommon.annotation.ConstantType;
import com.anyilanxin.anyicloud.corecommon.constant.CoreCommonGatewayConstant;
import com.anyilanxin.anyicloud.corecommon.constant.ISuperType;
import com.anyilanxin.anyicloud.corecommon.constant.model.ConstantDictModel;
import com.anyilanxin.anyicloud.gateway.filter.partial.post.CorsWebGatewayFilterFactory;
import com.anyilanxin.anyicloud.gateway.filter.partial.post.LogResponseGatewayFilterFactory;
import com.anyilanxin.anyicloud.gateway.filter.partial.pre.AuthorizeGatewayFilterFactory;
import com.anyilanxin.anyicloud.gateway.filter.partial.pre.LogRequestGatewayFilterFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * 自定义过滤器类型
 *
 * @author zxh
 * @date 2020-09-12 10:52
 * @since 1.0.0
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
     * @author zxh
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
     * @author zxh
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
     * @author zxh
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
