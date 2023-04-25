/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
package com.anyilanxin.skillfull.system.modules.manage.service;

import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.system.modules.manage.controller.vo.ManageRoutePredicateVo;
import com.anyilanxin.skillfull.system.modules.manage.entity.ManageRoutePredicateEntity;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.ManageRoutePredicateDto;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
* 路由断言(ManageRoutePredicate)业务层接口
*
* @author zxiaozhou
* @copyright zxiaozhou（https://skillfull.divisu.com）
* @date 2021-12-19 10:37:42
* @since JDK1.8
*/
public interface IManageRoutePredicateService extends BaseService<ManageRoutePredicateEntity> {
    /**
    * 保存
    *
    * @param vos ${@link List< ManageRoutePredicateVo >} 待保存数据
    * @param serviceId ${@link String} 服务id
    * @param override ${@link Boolean} 是否覆盖:true-覆盖,false-不覆盖
    * @param routerId ${@link String} 路由id
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2021-12-19 10:37:42
    */
    void save(List<ManageRoutePredicateVo> vos, String routerId, String serviceId, boolean override)
            throws RuntimeException;

    /**
    * 通过id查询详情
    *
    * @param predicateId ${@link String} 断言id
    * @return ManageRoutePredicateDto ${@link ManageRoutePredicateDto} 查询结果
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2021-12-19 10:37:42
    */
    ManageRoutePredicateDto getById(String predicateId) throws RuntimeException;

    /**
    * 通过routeId查询详情
    *
    * @param routeId ${@link String} 路由id
    * @return List<ManageRoutePredicateDto> ${@link List<ManageRoutePredicateDto>} 查询结果
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2021-12-19 10:37:42
    */
    List<ManageRoutePredicateDto> getByRouteId(String routeId) throws RuntimeException;

    /**
    * 通过routeIds查询详情
    *
    * @param routeIds ${@link Set<String>} 路由ids
    * @return Map<String, List < ManageRoutePredicateDto>> ${@link Map<String,
    *     List<ManageRoutePredicateDto>>} 查询结果
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2021-12-19 10:37:42
    */
    Map<String, List<ManageRoutePredicateDto>> getByRouteId(Set<String> routeIds)
            throws RuntimeException;

    /**
    * 通过routerId删除
    *
    * @param routerId ${@link String} 路由id
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2021-12-19 10:37:42
    */
    void deleteByRouterId(String routerId) throws RuntimeException;

    /**
    * 通过routerIds删除
    *
    * @param routerIds ${@link Set<String>} 路由ids
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2021-12-19 10:37:42
    */
    void deleteByRouterIds(Set<String> routerIds) throws RuntimeException;
}
