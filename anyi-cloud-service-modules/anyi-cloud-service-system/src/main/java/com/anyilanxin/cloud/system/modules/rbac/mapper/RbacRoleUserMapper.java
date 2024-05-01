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

// +----------------------------------------------------------------------
// | AnYi快速开发平台 [ AnYi ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2022 zhouxuanhong
// +----------------------------------------------------------------------
// | 官方网站: https://anyilanxin.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.cloud.system.modules.rbac.mapper;

import com.anyilanxin.cloud.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.cloud.system.modules.rbac.entity.RbacRoleUserEntity;
import com.anyilanxin.cloud.system.modules.rbac.service.dto.RbacMenuDto;
import com.anyilanxin.cloud.system.modules.rbac.service.dto.RbacRoleSimpleDto;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

/**
 * 角色-客户端(RbacRoleUser)持久层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-07-02 23:01:21
 * @since 1.0.0
 */
@Repository
public interface RbacRoleUserMapper extends BaseMapper<RbacRoleUserEntity> {
    /**
     * 获取用户角色
     *
     * @param userId 用户id
     * @return List<String>
     * @author zxh
     * @date 2022-07-04 01:18
     */
    @Select("""
            SELECT ali.role_id
            FROM sys_rbac_role_user srru
            INNER JOIN sys_rbac_role ali ON ali.role_id = srru.role_id
            WHERE
                ali.del_flag = 0
                AND srru.user_id = #{userId, jdbcType=VARCHAR}
            """)
    Set<String> selectUserRoleListById(@Param("userId") String userId);


    /**
     * 获取用户角色(完整数据)
     *
     * @param userId 用户id
     * @return List<RbacRoleSimpleDto>
     * @author zxh
     * @date 2022-07-04 01:18
     */
    Set<RbacRoleSimpleDto> selectUserRoleAllInfoListById(@Param("userId") String userId);


    /**
     * 查询用户角色授权菜单信息
     *
     * @param userId        用户id
     * @param systemCodeSet 系统编码
     * @return Set<RbacMenuDto>
     * @author zxh
     * @date 2022-07-05 00:42
     */
    Set<RbacMenuDto> selectMenuByUserId(@Param("userId") String userId, @Param("systemCodes") Set<String> systemCodeSet);


    /**
     * 通过用户id物理删除
     *
     * @param userId 用户id
     * @return int 成功状态:0-失败,1-成功
     * @author zxh
     * @date 2022-07-02 23:01:20
     */
    @Delete("""
            DELETE
            FROM sys_rbac_role_user
            WHERE user_id = #{id, jdbcType=VARCHAR}
            """)
    int physicalDeleteByUserId(@Param("id") String userId);


    /**
     * 通过角色id删除关联信息
     *
     * @param idList 角色用户id列表
     * @return int 成功状态:0-失败,大于1-成功
     * @author zxh
     * @date 2022-07-02 23:01:21
     */
    @Delete("""
             <script>
             DELETE
             FROM  sys_rbac_role_user
             WHERE role_id IN
             <foreach collection="coll" item="item" open="(" separator="," close=")">
                 #{item}
             </foreach>
             </script>
            """)
    int physicalDeleteRoleUserBatchIds(@Param("coll") Collection<String> idList);

}