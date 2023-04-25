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
import com.anyilanxin.skillfull.system.modules.manage.controller.vo.ManageCustomFilterVo;
import com.anyilanxin.skillfull.system.modules.manage.entity.ManageCustomFilterEntity;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.ManageCustomFilterDetailDto;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.ManageCustomFilterListDto;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.ManageCustomFilterSimpleDto;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
* 自定义过滤器(ManageCustomFilter)业务层接口
*
* @author zxiaozhou
* @copyright zxiaozhou（https://skillfull.divisu.com）
* @date 2021-12-19 00:22:15
* @since JDK1.8
*/
public interface IManageCustomFilterService extends BaseService<ManageCustomFilterEntity> {
    /**
    * 保存
    *
    * @param vo ${@link ManageCustomFilterVo} 自定义过滤器保存
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2021-12-19 00:22:15
    */
    void save(ManageCustomFilterVo vo) throws RuntimeException;

    /**
    * 通过id更新
    *
    * @param vo ${@link ManageCustomFilterVo} 自定义过滤器更新
    * @param customFilterId ${@link String} 自定义过滤器id
    * @param vo ${@link ManageCustomFilterVo} 自定义过滤器更新
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2021-12-19 00:22:15
    */
    void updateById(String customFilterId, ManageCustomFilterVo vo) throws RuntimeException;

    /**
    * 通过id查询详情
    *
    * @param customFilterId ${@link String} 自定义过滤器id
    * @return ManageCustomFilterDetailDto ${@link ManageCustomFilterDetailDto} 查询结果
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2021-12-19 00:22:15
    */
    ManageCustomFilterDetailDto getById(String customFilterId) throws RuntimeException;

    /**
    * 通过customFilterId删除
    *
    * @param customFilterId ${@link String} 自定义过滤器id
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2021-12-19 00:22:15
    */
    void deleteById(String customFilterId) throws RuntimeException;

    /**
    * 通过serviceId删除
    *
    * @param serviceId ${@link String} 服务id
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2021-12-19 00:22:15
    */
    void deleteByServiceId(String serviceId) throws RuntimeException;

    /**
    * 查询所有服务自定义过滤器
    *
    * @param serviceId ${@link String} 服务id
    * @return List<ManageCustomFilterDto> ${@link List< ManageCustomFilterListDto >}
    * @author zxiaozhou zxiaozhou
    * @date 2021-12-19 09:21
    */
    List<ManageCustomFilterListDto> selectList(String serviceId);

    /**
    * 查询所有服务自定义过滤器(有效的)，并组装为routerId为键的map
    *
    * @param serviceId ${@link String} 服务id
    * @param routerIds ${@link Set<String>} 路由id
    * @return List<ManageCustomFilterDto> ${@link List< ManageCustomFilterListDto >}
    * @author zxiaozhou zxiaozhou
    * @date 2021-12-19 09:21
    */
    Map<String, List<ManageCustomFilterSimpleDto>> selectListRouterIds(
            Set<String> routerIds, String serviceId);

    /**
    * 修改过滤器状态
    *
    * @param customFilterId ${@link String} 过滤器id
    * @param state ${@link Integer} 操作类型:0-禁止,1-启用
    * @author zxiaozhou
    * @date 2021-12-19 15:23
    */
    void updateStatus(String customFilterId, Integer state);

    /**
    * 查询服务自定义过滤器(无特殊url信息)
    *
    * @param serviceId ${@link String} 服务id
    * @return List<ManageCustomFilterSimpleDto> ${@link List<ManageCustomFilterSimpleDto>} 返回结果
    * @author zxiaozhou
    * @date 2021-12-19 17:28
    */
    List<ManageCustomFilterSimpleDto> selectSimpleList(String serviceId);
}
