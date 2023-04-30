/*
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   8.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   9.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.skillfull.system.modules.manage.service;

import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.system.modules.manage.controller.vo.ManageRouteCustomFilterVo;
import com.anyilanxin.skillfull.system.modules.manage.entity.ManageRouteCustomFilterEntity;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.ManageCustomFilterSimpleDto;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.ManageRouteCustomFilterDto;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.RouterCustomFilterDto;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 路由-自定义过滤器表(ManageRouteCustomFilter)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2021-12-19 00:22:17
 * @since JDK1.8
 */
public interface IManageRouteCustomFilterService extends BaseService<ManageRouteCustomFilterEntity> {
    /**
     * 保存
     *
     * @param customFilters ${@link List< ManageRouteCustomFilterVo >} 自定义过滤器
     * @param routerId ${@link String} 路由id
     * @param override ${@link Boolean} 是否覆盖:true-覆盖,false-不覆盖
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-12-19 00:22:17
     */
    void save(List<ManageRouteCustomFilterVo> customFilters, String routerId, boolean override) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param routeCustomFilterId ${@link String} 路由自定义过滤器id
     * @return ManageRouteCustomFilterDto ${@link ManageRouteCustomFilterDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-12-19 00:22:17
     */
    ManageRouteCustomFilterDto getById(String routeCustomFilterId) throws RuntimeException;


    /**
     * 通过routerIds获取过滤器
     *
     * @param routerIds ${@link Set<String>} 路由ids
     * @return Map<String, List < ManageCustomFilterSimpleDto>> ${@link Map<String,List<
     *     ManageCustomFilterSimpleDto >>}
     * @author zxiaozhou
     * @date 2021-12-22 04:36
     */
    Map<String, List<ManageCustomFilterSimpleDto>> getByRouterIds(Set<String> routerIds) throws RuntimeException;


    /**
     * 通过routerIds获取网关过滤器
     *
     * @param routerIds ${@link Set<String>} 路由ids
     * @return Map<String, List < ManageCustomFilterSimpleDto>> ${@link
     *     Map<String,List<ManageCustomFilterSimpleDto>>} routerId:过滤器信息
     * @author zxiaozhou
     * @date 2021-12-22 04:36
     */
    Map<String, RouterCustomFilterDto> getGatewayByRouterIds(Set<String> routerIds) throws RuntimeException;


    /**
     * 通过过routerId获取
     *
     * @param routerId ${@link String} 路由id
     * @return List<ManageCustomFilterSimpleDto> ${@link List<ManageCustomFilterSimpleDto>}
     * @author zxiaozhou
     * @date 2021-12-22 04:37
     */
    List<ManageCustomFilterSimpleDto> getByRouterId(String routerId) throws RuntimeException;


    /**
     * 通过routerId删除
     *
     * @param routerId ${@link String} 路由id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-12-19 00:22:17
     */
    void deleteByRouterId(String routerId) throws RuntimeException;


    /**
     * 通过routerIds删除
     *
     * @param routerIds ${@link Set <String>} 路由ids
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-12-19 00:22:17
     */
    void deleteByRouterIds(Set<String> routerIds) throws RuntimeException;
}
