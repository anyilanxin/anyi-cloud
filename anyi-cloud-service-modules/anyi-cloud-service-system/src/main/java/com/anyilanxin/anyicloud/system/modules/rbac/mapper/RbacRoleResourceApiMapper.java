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

package com.anyilanxin.anyicloud.system.modules.rbac.mapper;

import com.anyilanxin.anyicloud.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacRoleResourceApiEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 角色-资源表(RbacRoleResourceApi)持久层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:21
 * @since 1.0.0
 */
@Repository
public interface RbacRoleResourceApiMapper extends BaseMapper<RbacRoleResourceApiEntity> {
    /**
     * 获取所有资源权限
     *
     * @return List<String>
     * @author zxh
     * @date 2022-07-04 01:18
     */
    @Select("""
            SELECT api_id
            FROM sys_rbac_resource_api
            WHERE del_flag = 0
                AND permission_action IS NOT NULL
                AND permission_action !=''
            """)
    List<String> selectAllResource();


    /**
     * 获取角色资源权限
     *
     * @param roleId
     * @return List<String>
     * @author zxh
     * @date 2022-07-04 01:18
     */
    @Select("""
            SELECT srrra.api_id
            FROM sys_rbac_role_resource_api srrra
            INNER JOIN  sys_rbac_resource_api ali ON ali.api_id = srrra.api_id
            WHERE srrra.role_id = #{id, jdbcType=VARCHAR}
              AND ali.permission_action IS NOT NULL
              AND ali.permission_action !=''
            """)
    List<String> selectResourceApiListById(@Param("id") String roleId);


    /**
     * 获取角色资源权限(完整数据)
     *
     * @param roleId
     * @return List<String>
     * @author zxh
     * @date 2022-07-04 01:18
     */
    List<RbacResourceApiPageDto> selectResourceApiAllInfoListById(@Param("id") String roleId);


    /**
     * 获取用户授权的资源权限
     *
     * @param userId
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
            FROM sys_rbac_role_resource_api srrra
            INNER JOIN sys_rbac_resource_api srra ON srra.api_id = srrra.api_id
            INNER JOIN sys_rbac_role srr ON srrra.role_id = srr.role_id
            INNER JOIN sys_rbac_role_user srru ON srru.role_id = srr.role_id
            INNER JOIN sys_rbac_user sru ON sru.user_id = srru.user_id
            WHERE
                sru.user_id = #{userId, jdbcType=VARCHAR}
              AND srra.permission_action IS NOT NULL
              AND srra.permission_action != ''
              AND srra.del_flag = 0
              AND srr.role_status = 1
              AND srr.del_flag =0
            """)
    Set<RbacResourceApiSimpleDto> selectResourceApiByUserId(@Param("userId") String userId);


    /**
     * 获取所有资源权限(完整数据)
     *
     * @return List<String>
     * @author zxh
     * @date 2022-07-04 01:18
     */
    List<RbacResourceApiPageDto> selectAllAllInfoResource();


}
