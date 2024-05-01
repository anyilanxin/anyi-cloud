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

package com.anyilanxin.cloud.auth.modules.login.mapper;

import com.anyilanxin.cloud.auth.modules.login.service.dto.ClientAndResourceAuthDto;
import com.anyilanxin.cloud.auth.modules.login.service.dto.RbacResourceApiSimpleDto;
import com.anyilanxin.cloud.corecommon.model.auth.RoleInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * 客户端授权mapper
 *
 * @author zhou
 * @date 2022-07-23 17:59
 * @since 1.0.0
 */
public interface ClientAuthMapper {

    /**
     * 获取客户端关联的资源权限
     *
     * @param clientDetailId
     * @return List<RbacResourceApiSimpleDto>
     * @author zxh
     * @date 2022-07-12 12:18
     */
    @Select("""
            SELECT
              srra.api_id,
              srra.resource_id,
              srra.resource_code,
              srra.permission_express,
              srra.permission_action
            FROM sys_rbac_client_registered srcd
            INNER JOIN sys_rbac_client_resource_api srcra ON srcd.id = srcra.client_detail_id
            INNER JOIN sys_rbac_resource_api srra ON srra.api_id = srcra.api_id
            WHERE
              srcd.del_flag = 0
              AND srra.permission_action IS NOT NULL
              AND srra.permission_action != ''
              AND srcd.id = #{clientDetailId, jdbcType=VARCHAR}
            """)
    Set<RbacResourceApiSimpleDto> selectResourceApiByClientDetailId(@Param("clientDetailId") String clientDetailId);


    /**
     * 获取客户端角色
     *
     * @param clientDetailId 客户端明细id
     * @return Set<ClientRoleModel> 查询结果
     * @author zxh
     * @date 2022-04-06 00:08
     */
    @Select("""
            SELECT
              srr.role_id,
              srr.role_name,
              srr.role_code,
              srcd.id,
              srcd.client_id
            FROM sys_rbac_client_registered srcd
            INNER JOIN sys_rbac_role_client srrc ON srcd.id = srrc.client_detail_id
            INNER JOIN sys_rbac_role srr ON srr.role_id = srrc.role_id
            WHERE
              srr.role_status = 1
              AND srr.del_flag = 0
              AND srcd.del_flag = 0
              AND srcd.id = #{clientDetailId, jdbcType=VARCHAR}
            """)
    Set<RoleInfo> getClientAuthRole(@Param("clientDetailId") String clientDetailId);


    /**
     * 获取客户端授权角色关联的资源权限
     *
     * @param clientDetailId
     * @return List<RbacResourceApiSimpleDto>
     * @author zxh
     * @date 2022-07-12 12:18
     */
    @Select("""
            SELECT
              srra.api_id,
              srra.resource_id,
              srra.resource_code,
              srra.permission_express,
              srra.permission_action
            FROM sys_rbac_client_registered srcd
            INNER JOIN sys_rbac_role_client srrc ON srcd.id = srrc.client_detail_id
            INNER JOIN sys_rbac_role srr ON srr.role_id = srrc.role_id
            INNER JOIN sys_rbac_role_resource_api srrra ON srrra.api_id = srrra.api_id
            INNER JOIN sys_rbac_resource_api srra ON srra.api_id = srrra.api_id
            WHERE
              srr.role_status = 1
              AND srr.del_flag = 0
              AND srcd.del_flag = 0
              AND srra.permission_action IS NOT NULL
              AND srra.permission_action != ''
              AND srcd.id = #{clientDetailId, jdbcType=VARCHAR}
            """)
    Set<RbacResourceApiSimpleDto> selectResourceRoleApiByClientDetailId(@Param("clientDetailId") String clientDetailId);


    /**
     * 通过客户端id查询客户端信息
     *
     * @param clientId
     * @return ClientAndResourceAuthModel
     * @author zxh
     * @date 2022-07-12 12:18
     */
    ClientAndResourceAuthDto selectClientIdByClientId(@Param("clientId") String clientId);

    /**
     * 通过客户端主键id查询客户端信息
     *
     * @param Id
     * @return ClientAndResourceAuthModel
     * @author zxh
     * @date 2022-07-12 12:18
     */
    ClientAndResourceAuthDto selectClientIdById(@Param("id") String Id);
}
