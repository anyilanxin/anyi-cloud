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

package com.anyilanxin.anyicloud.system.modules.rbac.controller;

import com.anyilanxin.anyicloud.corecommon.base.AnYiResult;
import com.anyilanxin.anyicloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.anyicloud.corecommon.utils.AnYiI18nUtil;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotNullSize;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.anyicloud.coremvc.base.controller.AnYiBaseController;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacMenuPageQuery;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacMenuVo;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacMenuService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacMenuDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacMenuPageDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacMenuTreeDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单表(RbacMenu)控制层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:21
 * @since 1.0.0
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "RbacMenu", description = "菜单相关")
@RequestMapping(value = "/rbac-menu", produces = MediaType.APPLICATION_JSON_VALUE)
public class RbacMenuController extends AnYiBaseController {
    private final IRbacMenuService service;

    @Operation(summary = "菜单表添加", tags = {"v1.0.0"}, description = "添加菜单表")
    @PostMapping(value = "/insert")
    public AnYiResult<String> insert(@RequestBody @Valid RbacMenuVo vo) {
        service.save(vo);
        return ok(AnYiI18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过权限id修改", tags = {"v1.0.0"}, description = "修改菜单表")
    @Parameter(in = ParameterIn.PATH, description = "权限id", name = "menuId", required = true)
    @PutMapping(value = "/update/{menuId}")
    public AnYiResult<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "权限id不能为空") String menuId, @RequestBody @Valid RbacMenuVo vo) {
        service.updateById(menuId, vo);
        return ok(AnYiI18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "菜单表逻辑删除", tags = {"v1.0.0"}, description = "删除菜单表")
    @Parameter(in = ParameterIn.PATH, description = "权限id", name = "menuId", required = true)
    @DeleteMapping(value = "/delete-one/{menuId}")
    public AnYiResult<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "权限id不能为空") String menuId) {
        service.deleteById(menuId);
        return ok(AnYiI18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "菜单表逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除菜单表")
    @PostMapping(value = "/delete-batch")
    public AnYiResult<String> deleteBatchByIds(@RequestBody @NotNullSize(message = "待删除权限id不能为空") List<String> menuIds) {
        service.deleteBatch(menuIds);
        return ok(AnYiI18nUtil.get("Controller.BatchDeleteSuccess"));
    }


    @Operation(summary = "通过权限id查询详情", tags = {"v1.0.0"}, description = "查询菜单表详情")
    @Parameter(in = ParameterIn.PATH, description = "权限id", name = "menuId", required = true)
    @GetMapping(value = "/select/one/{menuId}")
    public AnYiResult<RbacMenuDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "权限id不能为空") String menuId) {
        return ok(service.getById(menuId));
    }


    @Operation(summary = "获取菜单权限树", tags = {"v1.0.0"}, description = "获取菜单权限树")
    @Parameters({@Parameter(description = "类型:0-目录,1-菜单,2-按钮，多个英文逗号隔开", name = "type"), @Parameter(description = "系统id", name = "systemId"), @Parameter(description = "状态:1-有效、2-所有,默认2", name = "status")})
    @GetMapping(value = "/select/tree")
    public AnYiResult<List<RbacMenuTreeDto>> getMenuTree(@RequestParam(defaultValue = "0", required = false) String type, @RequestParam(required = false) String systemId, @RequestParam(defaultValue = "2", required = false) Integer status) {
        return ok(service.getMenuTree(type, systemId, status));
    }


    @Operation(summary = "菜单表分页查询", tags = {"v1.0.0"}, description = "分页查询菜单表")
    @PostMapping(value = "/select/page")
    public AnYiResult<AnYiPageResult<RbacMenuPageDto>> selectPage(@RequestBody RbacMenuPageQuery vo) {
        return ok(service.pageByModel(vo));
    }
}
