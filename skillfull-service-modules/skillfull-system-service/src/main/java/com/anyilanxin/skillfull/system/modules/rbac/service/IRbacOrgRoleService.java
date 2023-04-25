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


package com.anyilanxin.skillfull.system.modules.rbac.service;

import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacOrgRoleAuthVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacOrgRolePageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacOrgRoleVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacOrgRoleEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacOrgRoleDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacOrgRoleMenuButtonDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacOrgRolePageDto;

import java.util.List;
import java.util.Set;

/**
 * 机构角色表(RbacOrgRole)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-07-05 00:22:57
 * @since JDK1.8
 */
public interface IRbacOrgRoleService extends BaseService<RbacOrgRoleEntity> {
    /**
     * 保存
     *
     * @param vo 机构角色表保存数据
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-07-05 00:22:57
     */
    void save(RbacOrgRoleVo vo) throws RuntimeException;

    /**
     * 通过id更新
     *
     * @param orgRoleId 机构角色id
     * @param vo        机构角色表更新数据
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-07-05 00:22:57
     */
    void updateById(String orgRoleId, RbacOrgRoleVo vo) throws RuntimeException;

    /**
     * 分页查询
     *
     * @param vo 分页查询条件
     * @return PageDto<RbacOrgRolePageDto> 分页查询结果
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-07-05 00:22:57
     */
    PageDto<RbacOrgRolePageDto> pageByModel(RbacOrgRolePageVo vo) throws RuntimeException;

    /**
     * 通过id查询详情
     *
     * @param orgRoleId 机构角色id
     * @return RbacOrgRoleDto 查询结果
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-07-05 00:22:57
     */
    RbacOrgRoleDto getById(String orgRoleId) throws RuntimeException;

    /**
     * 通过orgRoleId删除
     *
     * @param orgRoleId 机构角色id
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-07-05 00:22:57
     */
    void deleteById(String orgRoleId) throws RuntimeException;

    /**
     * 机构角色表批量删除
     *
     * @param orgRoleIds 机构角色id列表
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-07-05 00:22:57
     */
    void deleteBatch(List<String> orgRoleIds) throws RuntimeException;

    /**
     * 更新或添加角色权限
     *
     * @param orgRoleId
     * @param vo
     * @author zxiaozhou
     * @date 2022-07-07 09:48
     */
    void updateAuth(String orgRoleId, RbacOrgRoleAuthVo vo);

    /**
     * 通过角色id查询菜单按钮权限
     *
     * @param orgRoleId
     * @return Set<RbacOrgRoleMenuButtonDto>
     * @author zxiaozhou
     * @date 2022-07-07 09:55
     */
    Set<RbacOrgRoleMenuButtonDto> getMenuActions(String orgRoleId);

    /**
     * 角色启用或禁用
     *
     * @param orgRoleId
     * @param status
     * @author zxiaozhou
     * @date 2022-07-07 09:56
     */
    void updateStatus(String orgRoleId, Integer status);
}
