// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.processrpc.model;

import com.anyilanxin.skillfull.processrpc.constant.impl.ParameterInType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpMethod;

import java.io.Serializable;

/**
 * 回调信息
 *
 * @author zxiaozhou
 * @date 2022-03-07 10:06
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class CallbackHttpModel implements Serializable {

    @Schema(name = "sync", title = "异步还是同步,true-同步(会等待返回后再提交事务),false-异步(不等待返回即提交事务),默认同步")
    @Builder.Default
    private boolean sync = true;

    @Schema(name = "innerService", title = "是否内部服务，内部服务使用负载均衡器template,非内部服务使用普通template,默认true")
    @Builder.Default
    private boolean innerService = true;

    @Schema(name = "callbackUrl", title = "回调地址")
    private String callbackUrl;

    @Schema(name = "callbackMethod", title = "回调请求方法，默认POST")
    @Builder.Default
    private HttpMethod callbackMethod = HttpMethod.POST;

    @Schema(name = "parameterIn", title = "回调参数存放位置，默认body")
    @Builder.Default
    private ParameterInType parameterIn = ParameterInType.BODY;
}
