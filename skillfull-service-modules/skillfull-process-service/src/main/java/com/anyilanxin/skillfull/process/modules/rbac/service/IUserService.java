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

package com.anyilanxin.skillfull.process.modules.rbac.service;

import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.process.modules.rbac.controller.vo.*;
import com.anyilanxin.skillfull.process.modules.rbac.service.dto.UserDto;
import java.util.List;
import java.util.Set;

/**
 * 用户相关
 *
 * @author zxiaozhou
 * @date 2021-11-05 17:30
 * @since JDK1.8
 */
public interface IUserService {
    /**
     * 添加或更新用户
     *
     * @param vo ${@link UserVo}
     * @author zxiaozhou
     * @date 2021-11-05 17:51
     */
    void saveOrUpdate(UserVo vo) throws RuntimeException;

    /**
     * 用户与用户组关联
     *
     * @param vo ${@link UserGroupVo}
     * @author zxiaozhou
     * @date 2021-11-07 21:09
     */
    void deleteOrAddGroup(UserGroupVo vo) throws RuntimeException;

    /**
     * 用户与租户关联
     *
     * @param vo ${@link UserTenantVo}
     * @author zxiaozhou
     * @date 2021-11-07 21:10
     */
    void deleteOrAddTenant(UserTenantVo vo) throws RuntimeException;

    /**
     * 获取用户
     *
     * @param userId ${@link String}
     * @return UserModel ${@link UserDto}
     * @author zxiaozhou
     * @date 2021-11-05 17:51
     */
    UserDto getUser(String userId) throws RuntimeException;

    /**
     * 获取用户信息
     *
     * @param vo ${@link UserQueryVo}
     * @return List<UserDto> ${@link List<UserDto>}
     * @author zxiaozhou
     * @date 2021-11-05 17:51
     */
    List<UserDto> getUserList(UserQueryVo vo) throws RuntimeException;

    /**
     * 分页获取用户信息
     *
     * @param vo ${@link UserQueryPageVoCamunda}
     * @return PageDto<UserDto>${@link PageDto<UserDto>}
     * @author zxiaozhou
     * @date 2021-11-05 17:51
     */
    PageDto<UserDto> getUserPage(UserQueryPageVoCamunda vo) throws RuntimeException;

    /**
     * 删除用户
     *
     * @param userId ${@link String}
     * @author zxiaozhou
     * @date 2021-11-05 17:51
     */
    void deleteUser(String userId) throws RuntimeException;

    /**
     * 全量同步用户信息
     *
     * @param voSet ${@link List<SyncUserVo>}
     * @author zxiaozhou
     * @date 2021-11-07 21:37
     */
    void syncUser(Set<SyncUserVo> voSet) throws RuntimeException;
}
