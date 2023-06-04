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
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */
package com.anyilanxin.anyicloud.system.modules.manage.service;

import com.anyilanxin.anyicloud.database.datasource.base.service.BaseService;
import com.anyilanxin.anyicloud.system.modules.manage.controller.vo.ManageRoutePredicateVo;
import com.anyilanxin.anyicloud.system.modules.manage.entity.ManageRoutePredicateEntity;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.ManageRoutePredicateDto;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 路由断言(ManageRoutePredicate)业务层接口
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2021-12-19 10:37:42
 * @since 1.0.0
 */
public interface IManageRoutePredicateService extends BaseService<ManageRoutePredicateEntity> {
    /**
     * 保存
     *
     * @param vos       ${@link List< ManageRoutePredicateVo >} 待保存数据
     * @param serviceId ${@link String} 服务id
     * @param override  ${@link Boolean} 是否覆盖:true-覆盖,false-不覆盖
     * @param routerId  ${@link String} 路由id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-12-19 10:37:42
     */
    void save(List<ManageRoutePredicateVo> vos, String routerId, String serviceId, boolean override) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param predicateId ${@link String} 断言id
     * @return ManageRoutePredicateDto ${@link ManageRoutePredicateDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-12-19 10:37:42
     */
    ManageRoutePredicateDto getById(String predicateId) throws RuntimeException;


    /**
     * 通过routeId查询详情
     *
     * @param routeId ${@link String} 路由id
     * @return List<ManageRoutePredicateDto> ${@link List<ManageRoutePredicateDto>} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-12-19 10:37:42
     */
    List<ManageRoutePredicateDto> getByRouteId(String routeId) throws RuntimeException;


    /**
     * 通过routeIds查询详情
     *
     * @param routeIds ${@link Set<String>} 路由ids
     * @return Map<String, List < ManageRoutePredicateDto>> ${@link Map<String,
     * List<ManageRoutePredicateDto>>} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-12-19 10:37:42
     */
    Map<String, List<ManageRoutePredicateDto>> getByRouteId(Set<String> routeIds) throws RuntimeException;


    /**
     * 通过routerId删除
     *
     * @param routerId ${@link String} 路由id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-12-19 10:37:42
     */
    void deleteByRouterId(String routerId) throws RuntimeException;


    /**
     * 通过routerIds删除
     *
     * @param routerIds ${@link Set<String>} 路由ids
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-12-19 10:37:42
     */
    void deleteByRouterIds(Set<String> routerIds) throws RuntimeException;
}
