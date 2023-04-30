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

package com.anyilanxin.anyicloud.auth.modules.login.mapper;

import com.anyilanxin.anyicloud.auth.modules.login.service.dto.RbacOrgUserDto;
import com.anyilanxin.anyicloud.auth.modules.login.service.dto.RbacUserDto;
import com.anyilanxin.anyicloud.corecommon.model.auth.OrgSimpleInfo;
import com.anyilanxin.anyicloud.corecommon.model.auth.RoleInfo;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

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
     * @author 安一老厨
     * @date 2022-07-23 18:33
     */
    RbacUserDto selectByOpenId(@Param("openId") String openId);


    /**
     * 通过账号或者电话号码查询用户
     *
     * @param userName
     * @return RbacUserDto
     * @author 安一老厨
     * @date 2022-07-23 18:33
     */
    RbacUserDto selectByPhoneOrAccount(@Param("userName") String userName);


    /**
     * 通过电话号码查询用户信息
     *
     * @param phone
     * @return RbacUserDto
     * @author 安一老厨
     * @date 2022-07-23 18:33
     */
    RbacUserDto selectByPhone(@Param("phone") String phone);


    /**
     * 通过用户id查询用户信息
     *
     * @param userId
     * @return RbacUserDto
     * @author 安一老厨
     * @date 2022-07-23 20:10
     */
    RbacUserDto selectUserInfoByUserId(@Param("userId") String userId);


    /**
     * 更新用户登录机构
     *
     * @param userId
     * @param orgId
     * @author 安一老厨
     * @date 2022-07-23 18:36
     */
    int updateLoginOrgId(@Param("userId") String userId, @Param("orgId") String orgId);


    /**
     * 查询用户关联的机构id
     *
     * @param userId 用户id
     * @return List<RbacOrgUserDto> 查询结果
     * @author 安一老厨
     * @date 2022-07-02 23:01:20
     */
    List<RbacOrgUserDto> selectUserOrgListByUserId(@Param("userId") String userId);


    /**
     * 查询用户在某个机构下的角色信息
     *
     * @param userId 用户id
     * @param orgId  机构id
     * @return RoleInfo>
     * @author 安一老厨
     * @date 2022-07-05 00:36
     */
    Set<RoleInfo> selectByUserIdAndOrgId(@Param("userId") String userId, @Param("orgId") String orgId);


    /**
     * 查询用户角色
     *
     * @param userId
     * @return RoleInfo>
     * @author 安一老厨
     * @date 2022-07-05 00:42
     */
    Set<RoleInfo> selectByUserId(@Param("userId") String userId, @Param("superRoleCode") String superRoleCode);


    /**
     * 通过机构id查询机构信息
     *
     * @param orgId
     * @return OrgSimpleInfo
     * @author 安一老厨
     * @date 2022-07-23 19:46
     */
    OrgSimpleInfo selectOrgInfoById(@Param("orgId") String orgId);
}
