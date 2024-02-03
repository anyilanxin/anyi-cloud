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

package com.anyilanxin.anyicloud.auth.modules.login.mapper;

import com.anyilanxin.anyicloud.auth.modules.login.service.dto.RbacOrgUserDto;
import com.anyilanxin.anyicloud.auth.modules.login.service.dto.RbacResourceApiSimpleDto;
import com.anyilanxin.anyicloud.auth.modules.login.service.dto.RbacUserDto;
import com.anyilanxin.anyicloud.corecommon.model.auth.OrgSimpleInfo;
import com.anyilanxin.anyicloud.corecommon.model.auth.RoleInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * 用户授权mapper
 *
 * @author zhou
 * @date 2022-07-23 18:00
 * @since 1.0.0
 */
public interface UserAuthMapper {

    /**
     * 通过openId查询用户信息
     *
     * @param openId
     * @return RbacUserDto
     * @author zxh
     * @date 2022-07-23 18:33
     */
    RbacUserDto selectByOpenId(@Param("openId") String openId);


    /**
     * 通过账号或者电话号码查询用户
     *
     * @param userName
     * @return RbacUserDto
     * @author zxh
     * @date 2022-07-23 18:33
     */
    RbacUserDto selectByPhoneOrAccount(@Param("userName") String userName);


    /**
     * 通过电话号码查询用户信息
     *
     * @param phone
     * @return RbacUserDto
     * @author zxh
     * @date 2022-07-23 18:33
     */
    RbacUserDto selectByPhone(@Param("phone") String phone);


    /**
     * 通过用户id查询用户信息
     *
     * @param userId
     * @return RbacUserDto
     * @author zxh
     * @date 2022-07-23 20:10
     */
    RbacUserDto selectUserInfoByUserId(@Param("userId") String userId);


    /**
     * 更新用户登录机构
     *
     * @param userId
     * @param orgId
     * @author zxh
     * @date 2022-07-23 18:36
     */
    int updateLoginOrgId(@Param("userId") String userId, @Param("orgId") String orgId);


    /**
     * 查询用户关联的机构id
     *
     * @param userId 用户id
     * @return List<RbacOrgUserDto> 查询结果
     * @author zxh
     * @date 2022-07-02 23:01:20
     */
    @Select("""
            SELECT
              ali.org_id,
              ali.parent_id,
              ali.org_name,
              ali.org_name_en,
              ali.org_name_abbr,
              ali.org_order,
              ali.org_type,
              ali.org_code,
              ali.org_sys_code,
              ali.org_status,
              ali.social_code,
              ali.area_code_name,
              ali.area_code,
              ali.org_simple_name,
              srou.org_user_id,
              srou.user_id
            FROM sys_rbac_org ali
            INNER JOIN sys_rbac_org_user srou ON ali.org_id = srou.org_id
            WHERE srou.user_id = #{userId, jdbcType=VARCHAR}
            """)
    List<RbacOrgUserDto> selectUserOrgListByUserId(@Param("userId") String userId);


    /**
     * 获取所有资源权限
     *
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
            FROM sys_rbac_resource_api srra
            WHERE
              srra.permission_action IS NOT NULL
              AND srra.permission_action != ''
              AND srra.del_flag = 0
            """)
    Set<RbacResourceApiSimpleDto> selectResourceApiAll();


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
     * 获取用户机构授权的资源权限
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
            FROM sys_rbac_org_role ali
            INNER JOIN sys_rbac_org_role_user sroru ON ali.org_role_id = sroru.org_role_id
            INNER JOIN sys_rbac_org_role_resource_api srorra ON srorra.org_role_id = ali.org_role_id
            INNER JOIN sys_rbac_org_resource_api srora ON srorra.api_id = srora.api_id
            INNER JOIN sys_rbac_resource_api srra ON srra.api_id = srora.api_id
            WHERE
              ali.org_id = #{orgId, jdbcType=VARCHAR}
              AND ali.del_flag = 0
              AND ali.role_status = 1
              AND srra.del_flag = 0
              AND srra.permission_action != ''
              AND srra.permission_action IS NOT NULL
              AND sroru.user_id =  #{userId, jdbcType=VARCHAR}
            """)
    Set<RbacResourceApiSimpleDto> selectOrgResourceApiByUserId(@Param("userId") String userId, @Param("orgId") String orgId);


    /**
     * 查询用户在某个机构下的角色信息
     *
     * @param userId 用户id
     * @param orgId  机构id
     * @return RoleInfo>
     * @author zxh
     * @date 2022-07-05 00:36
     */
    @Select("""
            SELECT
              sror.role_name,
              sror.role_code,
              sror.org_role_id AS role_id,
              sror.data_auth_type,
              sror.custom_data_auth_data,
              1 AS role_type
            FROM sys_rbac_org_role_user sroru
            INNER JOIN sys_rbac_org_role sror ON sroru.org_role_id = sror.org_role_id
            WHERE
              sroru.user_id = #{userId, jdbcType=VARCHAR}
              AND sror.org_id = #{orgId, jdbcType=VARCHAR}
              AND sror.role_status = 1
              AND sror.del_flag = 0
            """)
    Set<RoleInfo> selectByUserIdAndOrgId(@Param("userId") String userId, @Param("orgId") String orgId);


    /**
     * 查询用户角色
     *
     * @param userId
     * @return RoleInfo>
     * @author zxh
     * @date 2022-07-05 00:42
     */
    @Select("""
            SELECT
              srr.role_name,
              srr.role_code,
              CASE WHEN srr.role_code = #{superRoleCode, jdbcType=VARCHAR} THEN 1 ELSE 0 END super_role,
              srr.role_id,
              srr.data_auth_type,
              srr.custom_data_auth_data,
              0 AS role_type
            FROM sys_rbac_role_user srru
            INNER JOIN sys_rbac_role srr ON srru.role_id = srr.role_id
            WHERE srru.user_id = #{userId, jdbcType=VARCHAR}
                  AND srr.role_status = 1
                  AND srr.del_flag = 0
            """)
    Set<RoleInfo> selectByUserId(@Param("userId") String userId, @Param("superRoleCode") String superRoleCode);


    /**
     * 通过机构id查询机构信息
     *
     * @param orgId
     * @return OrgSimpleInfo
     * @author zxh
     * @date 2022-07-23 19:46
     */
    OrgSimpleInfo selectOrgInfoById(@Param("orgId") String orgId);
}
