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
 *   1.请不要删除和修改根目录下的LICENSE文件。
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 *   3.请保留源码和相关描述文件的项目出处，作者声明等。
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 *   6.若您的项目无法满足以上几点，可申请商业授权
 */

package com.anyilanxin.skillfull.system.modules.rbac.mapper;

import com.anyilanxin.skillfull.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacMenuEntity;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacOrgMenuEntity;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 机构-菜单表(RbacOrgMenu)持久层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-07-02 23:01:20
 * @since JDK1.8
 */
@Repository
public interface RbacOrgMenuMapper extends BaseMapper<RbacOrgMenuEntity> {
  /**
   * 通过资源orgId物理删除
   *
   * @param orgId 机构id
   * @return int 成功状态:0-失败,1-成功
   * @author zxiaozhou
   * @date 2022-07-02 23:01:20
   */
  int physicalDeleteById(@Param("orgId") String orgId);

  /**
   * 删除不在不存在当前列表的资源
   *
   * @param orgId 机构id
   * @param idList 资源api角色id列表
   * @return int 成功状态:0-失败,大于1-成功
   * @author zxiaozhou
   * @date 2022-07-02 23:01:20
   */
  int physicalDeleteNotInIds(
      @Param("orgId") String orgId, @Param("coll") Collection<String> idList);

  /**
   * 查询机构功能权限
   *
   * @param orgId
   * @return Set<String>
   * @author zxiaozhou
   * @date 2022-07-06 23:59
   */
  Set<String> selectMenuListById(@Param("orgId") String orgId);

  /**
   * 查询机构菜单树
   *
   * @param orgId 机构id
   * @param systemId 系统id
   * @param status 菜单状态:0-禁用,1-启用,不传所有
   * @return List<RbacMenuEntity>
   * @author zxiaozhou
   * @date 2022-07-08 08:16
   */
  List<RbacMenuEntity> selectByParams(
      @Param("orgId") String orgId,
      @Param("systemId") String systemId,
      @Param("status") Integer status);
}
