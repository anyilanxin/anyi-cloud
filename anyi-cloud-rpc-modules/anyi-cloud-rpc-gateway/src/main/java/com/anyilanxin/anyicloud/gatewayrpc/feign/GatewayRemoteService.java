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
package com.anyilanxin.anyicloud.gatewayrpc.feign;

import cn.hutool.json.JSONObject;
import com.anyilanxin.anyicloud.corecommon.base.Result;
import com.anyilanxin.anyicloud.corecommon.constant.ServiceConstant;
import com.anyilanxin.anyicloud.corecommon.feign.FeignFallback;
import com.anyilanxin.anyicloud.corecommon.model.stream.router.SystemRouterModel;
import com.anyilanxin.anyicloud.corecommon.model.web.WebSecurityModel;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.anyicloud.gatewayrpc.model.RouteResponseModel;
import java.util.List;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 网关服务feign
 *
 * @author zxh
 * @date 2020-09-12 16:54
 * @since 1.0.0
 */
@FeignClient(value = ServiceConstant.GATEWAY_SERVICE, path = ServiceConstant.GATEWAY_SERVICE_PATH, fallbackFactory = FeignFallback.class)
public interface GatewayRemoteService {

    /**
     * 查询网关路由
     *
     * @return Result<List < GatewayServiceRouteDto>> ${@link Result<List<  RouteResponseModel  >>} 结果
     * @author zxh
     * @date 2020-09-15 17:03
     */
    @GetMapping("/route/select/list")
    Result<List<RouteResponseModel>> getRoutes();


    /**
     * 查询网关原始路由路由
     *
     * @return Result<List < JSONObject>> ${@link Result<List<JSONObject>>} 结果
     * @author zxh
     * @date 2020-09-15 17:03
     */
    @GetMapping("/route/select/list-original")
    Result<List<JSONObject>> getOriginalRoutes();


    /**
     * 添加路由
     *
     * @return Result<String> ${@link Result<String>} 结果
     * @author zxh
     * @date 2020-09-15 17:03
     */
    @PostMapping("/route/add")
    Result<String> addRoute(@RequestBody @Valid SystemRouterModel vo);


    /**
     * 更新路由
     *
     * @return Result<String> ${@link Result<String>} 结果
     * @author zxh
     * @date 2020-09-15 17:03
     */
    @PostMapping("/route/update")
    Result<String> updateRoute(@RequestBody @Valid SystemRouterModel vo);


    /**
     * 删除路由
     *
     * @param routeCode ${@link String} 路由编码
     * @return Result<String> ${@link Result<String>} 结果
     * @author zxh
     * @date 2020-09-15 17:03
     */
    @DeleteMapping("/route/delete/{routeCode}")
    Result<String> deleteRoute(@PathVariable @PathNotBlankOrNull(message = "路由编码不能为空") String routeCode);


    /**
     * 获取请求安全基础信息(需要路由设置使用加密传输)
     *
     * @return Result<WebSecurityModel> ${@link Result< WebSecurityModel >} 结果
     * @author zxh
     * @date 2020-09-15 17:03
     */
    @GetMapping("/tools/select/base-security")
    Result<WebSecurityModel> getBaseSecurity();


    /**
     * 获取请求安全基础信息手动刷新(需要路由设置使用加密传输)
     *
     * @return Result<WebSecurityModel> ${@link Result< WebSecurityModel >} 结果
     * @author zxh
     * @date 2020-09-15 17:03
     */
    @GetMapping("/tools/select/base-security/refresh/{serialNumber}")
    Result<WebSecurityModel> getRefreshBaseSecurity(@PathVariable @PathNotBlankOrNull(message = "请求序列不能为空") String serialNumber);
}
