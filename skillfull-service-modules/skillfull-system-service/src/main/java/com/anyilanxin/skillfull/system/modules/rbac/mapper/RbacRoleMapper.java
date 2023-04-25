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

package com.anyilanxin.skillfull.system.modules.rbac.mapper;

import com.anyilanxin.skillfull.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacRolePageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacRoleQueryVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacRoleEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacRoleDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacRolePageDto;
import com.anyilanxin.skillfull.systemrpc.model.UserRoleModel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 角色表(RbacRole)持久层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-02 19:29:58
 * @since JDK1.8
 */
@Repository
public interface RbacRoleMapper extends BaseMapper<RbacRoleEntity> {
    /**
     * 分页查询
     *
     * @param vo 查询条件
     * @param page 分页信息
     * @return IPage<RbacRolePageDto> 查询结果
     * @author zxiaozhou
     * @date 2022-05-02 19:29:58
     */
    IPage<RbacRolePageDto> pageByModel(
            Page<RbacRolePageDto> page,
            @Param("query") RbacRolePageVo vo,
            @Param("superRoleCode") String superRoleCode);

    /**
     * 条件查询多条
     *
     * @param vo 查询条件
     * @return List<RbacRoleDto> 查询结果
     * @author zxiaozhou
     * @date 2022-05-02 19:29:58
     */
    List<RbacRoleDto> selectListByModel(
            RbacRoleQueryVo vo, @Param("superRoleCode") String superRoleCode);

    /**
     * 获取用户授权的角色
     *
     * @param userId ${@link String}
     * @param orgId ${@link String}
     * @return Set<UserRoleModel> ${@link Set< UserRoleModel >}
     * @author zxiaozhou
     * @date 2022-04-06 00:08
     */
    Set<UserRoleModel> getUserAuthRole(
            String userId, String orgId, @Param("superRoleCode") String superRoleCode);

    /**
     * 通过角色id物理删除
     *
     * @param roleId 角色id
     * @return int 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2022-05-02 19:29:58
     */
    int physicalDeleteById(@Param("id") String roleId);

    /**
     * 通过角色id物理批量删除
     *
     * @param idList 角色id列表
     * @return int 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2022-05-02 19:29:58
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
