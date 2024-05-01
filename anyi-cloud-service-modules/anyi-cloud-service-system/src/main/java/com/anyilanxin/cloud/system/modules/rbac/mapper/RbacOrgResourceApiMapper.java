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
import com.anyilanxin.cloud.system.modules.rbac.controller.vo.RbacOrgResourceApiPageQuery;
import com.anyilanxin.cloud.system.modules.rbac.entity.RbacOrgResourceApiEntity;
import com.anyilanxin.cloud.system.modules.rbac.service.dto.RbacOrgResourceApiPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

/**
 * 机构-资源表(RbacOrgResourceApi)持久层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-07-02 23:01:20
 * @since 1.0.0
 */
@Repository
public interface RbacOrgResourceApiMapper extends BaseMapper<RbacOrgResourceApiEntity> {

    /**
     * 分页查询
     *
     * @param vo   查询条件
     * @param page 分页信息
     * @return IPage<RbacOrgResourceApiPageDto> 查询结果
     * @author zxh
     * @date 2022-05-02 16:12:20
     */
    IPage<RbacOrgResourceApiPageDto> selectResourcePage(Page<RbacOrgResourceApiPageDto> page, @Param("query") RbacOrgResourceApiPageQuery vo);


    /**
     * 通过资源orgId物理删除
     *
     * @param orgId 机构id
     * @return int 成功状态:0-失败,1-成功
     * @author zxh
     * @date 2022-07-02 23:01:20
     */
    int physicalDeleteById(@Param("orgId") String orgId);


    /**
     * 删除不在不存在当前列表的资源
     *
     * @param orgId  机构id
     * @param idList 资源api角色id列表
     * @return int 成功状态:0-失败,大于1-成功
     * @author zxh
     * @date 2022-07-02 23:01:20
     */
    int physicalDeleteNotInIds(@Param("orgId") String orgId, @Param("coll") Collection<String> idList);


    /**
     * 查询某个机构的资源权限
     *
     * @param orgId 机构id
     * @return Set<String>
     * @author zxh
     * @date 2022-07-06 23:58
     */
    @Select("""
            SELECT api_id
            FROM sys_rbac_org_resource_api
            WHERE org_id = #{orgId, jdbcType=VARCHAR}
            """)
    Set<String> selectAllResource(@Param("orgId") String orgId);


    /**
     * 查询机构资源权限所有信息
     *
     * @param orgId
     * @return Set<RbacResourceApiPageDto>
     * @author zxh
     * @date 2022-07-06 23:58
     */
    Set<RbacResourceApiPageDto> selectAllAllInfoResource(@Param("orgId") String orgId);
}
