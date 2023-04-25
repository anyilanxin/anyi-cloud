/**
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package com.anyilanxin.skillfull.system.modules.manage.service;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.system.modules.manage.controller.vo.InstancePageVo;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.NacosNamespacesDto;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.NacosServiceInfoDto;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.ServiceInstancePageDto;

import java.util.List;

/**
 * @author zxiaozhou zxiaozhou
 * @date 2020-10-11 19:34
 * @since JDK1.8
 */
public interface ICustomNacosNamingService {


    /**
     * 查询所有注册的服务实例信息
     *
     * @param serviceCode ${@link String} 服务编码(必填)
     * @param groupName   ${@link String} 组，不填使用默认
     * @param clusters    ${@link List<String>} 集群信息，不变默认
     * @return List<Instance> ${@link List<Instance>} 服务实例
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou zxiaozhou
     * @date 2020-10-11 21:51
     */
    List<Instance> getAllInstances(String serviceCode, String groupName, List<String> clusters) throws RuntimeException;


    /**
     * 获取当前所有命名空间
     *
     * @return List<NacosNamespacesDto> ${@link List< NacosNamespacesDto >} 命名空间
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou zxiaozhou
     * @date 2020-10-11 21:52
     */
    List<NacosNamespacesDto> getAllNamespaces() throws RuntimeException;

    /**
     * 服务实例分页查询
     *
     * @param vo ${@link InstancePageVo}
     * @return PageDto<ServiceInstancePageDto> ${@link PageDto < ServiceInstancePageDto >}
     * @author zxiaozhou
     * @date 2021-12-28 05:54
     */
    PageDto<ServiceInstancePageDto> selectInstancePage(InstancePageVo vo);

    /**
     * 查询某个组的所有服务
     *
     * @param pageNo    ${@link Integer} 查询页(不填默认1)
     * @param pageSize  ${@link Integer} 显示数量(不变默认最大)
     * @param groupName ${@link String} 组名
     * @return List<NacosServiceInfoDto> ${@link  List< NacosServiceInfoDto >}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou zxiaozhou
     * @date 2020-10-11 21:54
     */
    List<NacosServiceInfoDto> getServicesOfServer(Integer pageNo, Integer pageSize, String groupName) throws RuntimeException;

}
