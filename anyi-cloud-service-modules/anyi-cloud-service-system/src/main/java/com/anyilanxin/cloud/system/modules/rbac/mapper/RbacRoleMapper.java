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

package com.anyilanxin.cloud.system.modules.rbac.mapper;

import com.anyilanxin.cloud.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.cloud.system.modules.rbac.controller.vo.RbacRolePageQuery;
import com.anyilanxin.cloud.system.modules.rbac.controller.vo.RbacRoleQueryVo;
import com.anyilanxin.cloud.system.modules.rbac.entity.RbacRoleEntity;
import com.anyilanxin.cloud.system.modules.rbac.service.dto.RbacRoleDto;
import com.anyilanxin.cloud.system.modules.rbac.service.dto.RbacRolePageDto;
import com.anyilanxin.cloud.systemadapter.model.UserRoleModel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 角色表(RbacRole)持久层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 19:29:58
 * @since 1.0.0
 */
@Repository
public interface RbacRoleMapper extends BaseMapper<RbacRoleEntity> {
    /**
     * 分页查询
     *
     * @param vo   查询条件
     * @param page 分页信息
     * @return IPage<RbacRolePageDto> 查询结果
     * @author zxh
     * @date 2022-05-02 19:29:58
     */
    IPage<RbacRolePageDto> pageByModel(Page<RbacRolePageDto> page, @Param("query") RbacRolePageQuery vo, @Param("superRoleCode") String superRoleCode);


    /**
     * 条件查询多条
     *
     * @param vo 查询条件
     * @return List<RbacRoleDto> 查询结果
     * @author zxh
     * @date 2022-05-02 19:29:58
     */
    List<RbacRoleDto> selectListByModel(RbacRoleQueryVo vo, @Param("superRoleCode") String superRoleCode);


    /**
     * 获取用户授权的角色
     *
     * @param userId ${@link String}
     * @param orgId  ${@link String}
     * @return Set<UserRoleModel> ${@link Set<  UserRoleModel  >}
     * @author zxh
     * @date 2022-04-06 00:08
     */
    Set<UserRoleModel> getUserAuthRole(String userId, String orgId, @Param("superRoleCode") String superRoleCode);


}
