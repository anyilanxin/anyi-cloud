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

package com.anyilanxin.anyicloud.gatewayrpcadapter.adapter;

import com.alibaba.fastjson2.JSONObject;
import com.anyilanxin.anyicloud.corecommon.base.AnYiResult;
import com.anyilanxin.anyicloud.corecommon.constant.AnYiResultStatus;
import com.anyilanxin.anyicloud.corecommon.exception.AnYiResponseException;
import com.anyilanxin.anyicloud.corecommon.model.stream.router.SystemRouterModel;
import com.anyilanxin.anyicloud.corecommon.model.web.WebSecurityModel;
import com.anyilanxin.anyicloud.gatewayadapter.adapter.IGatewayAdapter;
import com.anyilanxin.anyicloud.gatewayadapter.model.RouteResponseModel;
import com.anyilanxin.anyicloud.gatewayrpcadapter.rpc.GatewayRemoteRpc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 网关服务feign
 *
 * @author zxh
 * @date 2020-09-12 16:54
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class GatewayAdapterRpcImpl implements IGatewayAdapter {
    private final GatewayRemoteRpc remoteRpc;

    /**
     * 查询网关路由
     *
     * @return {@link List }<{@link RouteResponseModel }>
     * @author zxh
     * @date 2023-12-30 09:39:05
     */
    @Override
    public List<RouteResponseModel> getRoutes() {
        AnYiResult<List<RouteResponseModel>> mapAnYiResult = remoteRpc.getRoutes();
        if (!mapAnYiResult.isSuccess()) {
            throw new AnYiResponseException(AnYiResultStatus.API_ERROR, "查询网关路由:" + mapAnYiResult.getMessage());
        }
        return mapAnYiResult.getData();
    }


    /**
     * 查询网关原始路由路由
     *
     * @return {@link List }<{@link JSONObject }>
     * @author zxh
     * @date 2023-12-30 09:39:06
     */
    @Override
    public List<JSONObject> getOriginalRoutes() {
        AnYiResult<List<JSONObject>> mapAnYiResult = remoteRpc.getOriginalRoutes();
        if (!mapAnYiResult.isSuccess()) {
            throw new AnYiResponseException(AnYiResultStatus.API_ERROR, "查询网关原始路由路由:" + mapAnYiResult.getMessage());
        }
        return mapAnYiResult.getData();
    }


    /**
     * 添加路由
     *
     * @param vo
     * @author zxh
     * @date 2023-12-30 09:39:08
     */
    @Override
    public void addRoute(SystemRouterModel vo) {
        AnYiResult<String> mapAnYiResult = remoteRpc.addRoute(vo);
        if (!mapAnYiResult.isSuccess()) {
            throw new AnYiResponseException(AnYiResultStatus.API_ERROR, "添加路由:" + mapAnYiResult.getMessage());
        }
    }


    /**
     * 更新路由
     *
     * @param vo
     * @author zxh
     * @date 2023-12-30 09:39:10
     */
    @Override
    public void updateRoute(SystemRouterModel vo) {
        AnYiResult<String> mapAnYiResult = remoteRpc.updateRoute(vo);
        if (!mapAnYiResult.isSuccess()) {
            throw new AnYiResponseException(AnYiResultStatus.API_ERROR, "更新路由:" + mapAnYiResult.getMessage());
        }
    }


    /**
     * 删除路由
     *
     * @param routeCode $
     * @author zxh
     * @date 2023-12-30 09:39:12
     */
    @Override
    public void deleteRoute(String routeCode) {
        AnYiResult<String> mapAnYiResult = remoteRpc.deleteRoute(routeCode);
        if (!mapAnYiResult.isSuccess()) {
            throw new AnYiResponseException(AnYiResultStatus.API_ERROR, "删除路由:" + mapAnYiResult.getMessage());
        }
    }


    /**
     * 获取请求安全基础信息(需要路由设置使用加密传输)
     *
     * @return {@link WebSecurityModel }
     * @author zxh
     * @date 2023-12-30 09:39:14
     */
    @Override
    public WebSecurityModel getBaseSecurity() {
        AnYiResult<WebSecurityModel> mapAnYiResult = remoteRpc.getBaseSecurity();
        if (!mapAnYiResult.isSuccess()) {
            throw new AnYiResponseException(AnYiResultStatus.API_ERROR, "获取请求安全基础信息(需要路由设置使用加密传输):" + mapAnYiResult.getMessage());
        }
        return mapAnYiResult.getData();
    }


    /**
     * 获取请求安全基础信息手动刷新(需要路由设置使用加密传输)
     *
     * @param serialNumber
     * @return {@link WebSecurityModel }
     * @author zxh
     * @date 2023-12-30 09:39:16
     */
    @Override
    public WebSecurityModel getRefreshBaseSecurity(String serialNumber) {
        AnYiResult<WebSecurityModel> mapAnYiResult = remoteRpc.getRefreshBaseSecurity(serialNumber);
        if (!mapAnYiResult.isSuccess()) {
            throw new AnYiResponseException(AnYiResultStatus.API_ERROR, "获取请求安全基础信息手动刷新(需要路由设置使用加密传输):" + mapAnYiResult.getMessage());
        }
        return mapAnYiResult.getData();
    }
}
