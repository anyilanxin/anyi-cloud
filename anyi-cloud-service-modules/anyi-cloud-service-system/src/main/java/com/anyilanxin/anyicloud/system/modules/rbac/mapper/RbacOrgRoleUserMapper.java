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

import com.anyilanxin.anyicloud.corecommon.model.auth.RoleInfo;
import com.anyilanxin.anyicloud.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacOrgRoleUserEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacMenuDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacRoleSimpleDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * 机构角色-用户(RbacOrgRoleUser)持久层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-07-05 00:22:57
 * @since 1.0.0
 */
@Repository
public interface RbacOrgRoleUserMapper extends BaseMapper<RbacOrgRoleUserEntity> {
    /**
     * 获取用户机构角色
     *
     * @param userId 用户id
     * @param orgId  机构id
     * @return List<String>
     * @author zxh
     * @date 2022-07-04 01:18
     */
    @Select("""
            SELECT ali.org_role_id
            FROM sys_rbac_org_role ali
            INNER JOIN sys_rbac_org_role_user sroru ON ali.org_role_id = sroru.org_role_id
            WHERE
                ali.org_id = #{orgId, jdbcType=VARCHAR}
              AND ali.del_flag = 0
              AND sroru.user_id = #{userId, jdbcType=VARCHAR}
            """)
    Set<String> selectUserOrgRoleListById(@Param("userId") String userId, @Param("orgId") String orgId);


    /**
     * 获取用户机构角色(完整数据)
     *
     * @param userId 用户id
     * @param orgId  机构id
     * @return List<RbacRoleSimpleDto>
     * @author zxh
     * @date 2022-07-04 01:18
     */
    Set<RbacRoleSimpleDto> selectUserOrgRoleAllInfoListById(@Param("userId") String userId, @Param("orgId") String orgId);


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
     * 查询用户在某个机构下的角色关联菜单信息
     *
     * @param userId        用户id
     * @param orgId         机构id
     * @param systemCodeSet 系统编码
     * @return Set<RbacMenuDto>
     * @author zxh
     * @date 2022-07-05 00:36
     */
    Set<RbacMenuDto> selectMenuByUserIdAndOrgId(@Param("userId") String userId, @Param("orgId") String orgId, @Param("systemCodes") Set<String> systemCodeSet);

    /**
     * 通过用户id物理删除
     *
     * @param userId 用户id
     * @return int 成功状态:0-失败,1-成功
     * @author zxh
     * @date 2022-07-02 23:01:20
     */
    int physicalDeleteByUserId(@Param("id") String userId);


}
