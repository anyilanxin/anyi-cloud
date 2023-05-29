/*
 * Copyright (c) 2021-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Zeebe EE、AnYi Cloud EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.system.modules.rbac.controller;

import com.anyilanxin.anyicloud.corecommon.base.Result;
import com.anyilanxin.anyicloud.corecommon.constant.CoreCommonCacheConstant;
import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.anyicloud.coremvc.base.controller.BaseController;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacOrgPageVo;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacOrgVo;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacOrgMenuService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacOrgService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 组织表(RbacOrg)控制层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:39:44
 * @since 1.0.0
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "RbacOrg", description = "组织相关")
@RequestMapping(value = "/rbac-org", produces = MediaType.APPLICATION_JSON_VALUE)
public class RbacOrgController extends BaseController {
    private final IRbacOrgService service;
    private final IRbacOrgMenuService menuService;

    @Operation(summary = "组织表添加", tags = {"v1.0.0"}, description = "添加组织表")
    @PostMapping(value = "/insert")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_ORG_CACHE, allEntries = true)
    public Result<String> insert(@RequestBody @Valid RbacOrgVo vo) {
        service.save(vo);
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }

    // @Operation(summary = "更新机构默认菜单权限", tags = {"v1.0.0"}, description =
    // "更新机构默认菜单权限")
    // @PostMapping(value = "/update/org-auth")
    // public Result<String> updateOrgAuth(@RequestBody @Valid RbacDefaultAuthVo vo)
    // {
    // service.updateOrgAuth(vo);
    // return ok("修改成功");
    // }


    @Operation(summary = "通过机构id修改机构状态", tags = {"v1.0.0"}, description = "通过机构id修改机构状态")
    @Parameters({@Parameter(in = ParameterIn.QUERY, description = "机构id", name = "orgId", required = true), @Parameter(in = ParameterIn.QUERY, description = "类型:0-禁用,1-启用", name = "type", required = true)})
    @GetMapping(value = "/update/org/state")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_ORG_CACHE, allEntries = true)
    public Result<String> updateOrgState(@RequestParam(required = false) @NotBlank(message = "机构id不能为空") String orgId, @RequestParam(required = false) @NotNull(message = "操作类型不能为空") Integer type) {
        service.updateOrgState(orgId, type);
        return ok(type == 0 ? "禁用成功" : "启用成功");
    }


    @Operation(summary = "通过组织id修改", tags = {"v1.0.0"}, description = "修改组织表")
    @Parameter(in = ParameterIn.PATH, description = "组织id", name = "orgId", required = true)
    @PutMapping(value = "/update/{orgId}")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_ORG_CACHE, allEntries = true)
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "组织id不能为空") String orgId, @RequestBody @Valid RbacOrgVo vo) {
        service.updateById(orgId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "组织表逻辑删除", tags = {"v1.0.0"}, description = "删除组织表")
    @Parameter(in = ParameterIn.PATH, description = "组织id", name = "orgId", required = true)
    @DeleteMapping(value = "/delete-one/{orgId}")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_ORG_CACHE, allEntries = true)
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "组织id不能为空") String orgId) {
        service.deleteById(orgId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "查询机构树(异步)", tags = {"v1.0.0"}, description = "查询机构树(异步)")
    @PostMapping(value = "/select/async/org-tree")
    @Parameters({@Parameter(in = ParameterIn.QUERY, description = "上级组织id", name = "parentId"), @Parameter(in = ParameterIn.QUERY, description = "需要激活的组织id", name = "activateOrgId")})
    @Cacheable(value = CoreCommonCacheConstant.ENGINE_ORG_CACHE, key = "'asyncdeparttree:'+#parentId+#activateOrgId")
    public Result<List<RbacOrgTreeDto>> selectOrgTreeAsync(@RequestParam(required = false, defaultValue = "") String parentId, @RequestParam(required = false, defaultValue = "") String activateOrgId) {
        return ok(service.selectOrgTreeAsync(parentId, activateOrgId));
    }


    @Operation(summary = "组织列表查询", tags = {"v1.0.0"}, description = "组织列表查询")
    @GetMapping(value = "/select/org-list")
    @Parameters({@Parameter(description = "类型:0-所有,1-有效,默认1", name = "type"), @Parameter(description = "父级id", name = "parentId")})
    @Cacheable(value = CoreCommonCacheConstant.ENGINE_ORG_CACHE, key = "'list:'+#parentId+#type")
    public Result<List<RbacOrgHasChildrenDto>> selectOrgList(@RequestParam(defaultValue = "1", required = false) Integer type, @RequestParam(defaultValue = "", required = false) String parentId) {
        return ok(service.selectOrgList(type, parentId));
    }


    @Operation(summary = "获取组织机构树", tags = {"v1.0.0"}, description = "获取组织机构树")
    @GetMapping(value = "/select/org-tree-list")
    @Parameter(description = "类型:0-所有,1-有效,默认1", name = "type")
    @Cacheable(value = CoreCommonCacheConstant.ENGINE_ORG_CACHE, key = "'listtree:'+#type")
    public Result<List<RbacOrgTreeDto>> selectOrgTreeList(@RequestParam(defaultValue = "1", required = false) Integer type) {
        return ok(service.selectOrgTreeList(type));
    }


    @Operation(summary = "通过组织id查询详情", tags = {"v1.0.0"}, description = "查询组织表详情")
    @Parameter(in = ParameterIn.PATH, description = "组织id", name = "orgId", required = true)
    @GetMapping(value = "/select/one/{orgId}")
    public Result<RbacOrgDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "组织id不能为空") String orgId) {
        return ok(service.getById(orgId));
    }


    @Operation(summary = "组织表分页查询", tags = {"v1.0.0"}, description = "分页查询组织表")
    @PostMapping(value = "/select/page")
    public Result<PageDto<RbacOrgTreePageDto>> selectPage(@RequestBody RbacOrgPageVo vo) {
        return ok(service.pageByModel(vo));
    }


    @Operation(summary = "获取机构菜单权限树", tags = {"v1.0.0"}, description = "获取机构菜单权限树")
    @Parameters({@Parameter(description = "机构id", name = "orgId", required = true), @Parameter(description = "系统id", name = "systemId"), @Parameter(description = "菜单状态:0-禁用,1-启用,不传所有", name = "status")})
    @GetMapping(value = "/select/tree")
    public Result<List<RbacMenuTreeDto>> getMenuTree(@RequestParam(required = false) @NotBlank(message = "机构id不能为空") String orgId, @RequestParam(required = false) String systemId, @RequestParam(required = false) Integer status) {
        return ok(menuService.getMenuTree(orgId, systemId, status));
    }
}
