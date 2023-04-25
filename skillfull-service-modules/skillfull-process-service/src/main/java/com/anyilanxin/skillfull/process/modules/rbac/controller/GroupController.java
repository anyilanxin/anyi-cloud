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

package com.anyilanxin.skillfull.process.modules.rbac.controller;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.skillfull.coremvc.base.controller.BaseController;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.process.modules.rbac.controller.vo.*;
import com.anyilanxin.skillfull.process.modules.rbac.service.IGroupService;
import com.anyilanxin.skillfull.process.modules.rbac.service.dto.GroupDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户组相关
 *
 * @author zxiaozhou
 * @date 2021-11-05 17:30
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "RbacGroup", description = "用户组相关")
@RequestMapping(value = "/rbac-group", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupController extends BaseController {
  private final IGroupService service;

  @Operation(
      summary = "添加或更新用户组",
      tags = {"v1.0.0"},
      description = "添加或更新用户组")
  @PostMapping(value = "/insert-or-update")
  public Result<String> saveOrUpdate(@RequestBody @Valid GroupVo vo) {
    service.saveOrUpdate(vo);
    return ok("用户组操作成功");
  }

  @Operation(
      summary = "删除或添加租户关联关系",
      tags = {"v1.0.0"},
      description = "删除或添加租户关联关系")
  @PostMapping(value = "/delete-or-tenant")
  public Result<String> deleteOrAddTenant(@RequestBody @Valid GroupTenantVo vo) {
    service.deleteOrAddTenant(vo);
    return ok("关联关系操作成功");
  }

  @Operation(
      summary = "删除用户组",
      tags = {"v1.0.0"},
      description = "删除用户组")
  @Parameter(in = ParameterIn.PATH, description = "用户组id", name = "groupId", required = true)
  @DeleteMapping(value = "/delete-one/{groupId}")
  public Result<String> deleteById(
      @PathVariable(required = false) @PathNotBlankOrNull(message = "用户组id不能为空") String groupId) {
    service.deleteGroup(groupId);
    return ok(I18nUtil.get("Controller.DeleteSuccess"));
  }

  @Operation(
      summary = "全量同步用户组信息",
      tags = {"v1.0.0"},
      description = "全量同步用户组信息")
  @PostMapping(value = "/all")
  public Result<String> syncGroup(@RequestBody @Valid Set<SyncGroupVo> voSet) {
    service.syncGroup(voSet);
    return ok("用户组信息操作成功");
  }

  @Operation(
      summary = "查询用户组详情",
      tags = {"v1.0.0"},
      description = "查询用户组详情")
  @Parameter(in = ParameterIn.PATH, description = "用户组id", name = "groupId", required = true)
  @GetMapping(value = "/select/one/{groupId}")
  public Result<GroupDto> getById(
      @PathVariable(required = false) @PathNotBlankOrNull(message = "用户组id不能为空") String groupId) {
    return ok(service.getGroup(groupId));
  }

  @Operation(
      summary = "查询用户组列表",
      tags = {"v1.0.0"},
      description = "查询用户组列表")
  @PostMapping(value = "/select/list")
  public Result<List<GroupDto>> getList(@RequestBody GroupQueryVo vo) {
    return ok(service.getGroupList(vo));
  }

  @Operation(
      summary = "分页查询用户组",
      tags = {"v1.0.0"},
      description = "分页查询用户组")
  @PostMapping(value = "/select/page")
  public Result<PageDto<GroupDto>> selectPage(@RequestBody GroupQueryPageVoCamunda vo) {
    return ok(service.getGroupPage(vo));
  }
}
