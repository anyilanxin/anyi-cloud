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

package com.anyilanxin.cloud.system.modules.rbac.controller;

import com.anyilanxin.cloud.corecommon.base.AnYiResult;
import com.anyilanxin.cloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.cloud.corecommon.utils.AnYiI18nUtil;
import com.anyilanxin.cloud.corecommon.validation.annotation.NotNullSize;
import com.anyilanxin.cloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.cloud.coremvc.base.controller.AnYiBaseController;
import com.anyilanxin.cloud.system.modules.rbac.controller.vo.ClientRegisteredPageQuery;
import com.anyilanxin.cloud.system.modules.rbac.controller.vo.ClientRegisteredVo;
import com.anyilanxin.cloud.system.modules.rbac.controller.vo.RbacClientAuthVo;
import com.anyilanxin.cloud.system.modules.rbac.controller.vo.RbacClientResourceApiPageQuery;
import com.anyilanxin.cloud.system.modules.rbac.service.IClientRegisteredService;
import com.anyilanxin.cloud.system.modules.rbac.service.dto.ClientRegisteredDto;
import com.anyilanxin.cloud.system.modules.rbac.service.dto.ClientRegisteredPageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 授权客户端信息(ClientRegistered)控制层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2023-09-24 14:48:15
 * @since JDK11
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "RbacClientDetails", description = "授权客户端相关")
@RequestMapping(value = "/rbac-client", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientRegisteredController extends AnYiBaseController {
    private final IClientRegisteredService service;
    private final IRbacClientResourceApiService resourceApiService;

    @Operation(summary = "授权客户端信息添加", tags = {"v1.0.0"}, description = "添加授权客户端信息")
    @PostMapping(value = "/insert")
    public AnYiResult<String> insert(@RequestBody @Valid ClientRegisteredVo vo) {
        service.save(vo);
        return ok(AnYiI18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过客户端信息id修改", tags = {"v1.0.0"}, description = "修改授权客户端信息")
    @Parameter(in = ParameterIn.PATH, description = "客户端信息id", name = "id", required = true)
    @PutMapping(value = "/update/{id}")
    public AnYiResult<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "客户端信息id不能为空") String id,
                                     @RequestBody @Valid ClientRegisteredVo vo) {
        service.updateById(id, vo);
        return ok(AnYiI18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "授权客户端信息逻辑删除", tags = {"v1.0.0"}, description = "删除授权客户端信息")
    @Parameter(in = ParameterIn.PATH, description = "客户端信息id", name = "id", required = true)
    @DeleteMapping(value = "/delete-one/{id}")
    public AnYiResult<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "客户端信息id不能为空") String id) {
        service.deleteById(id);
        return ok(AnYiI18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "授权客户端信息逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除授权客户端信息")
    @PostMapping(value = "/delete-batch")
    public AnYiResult<String> deleteBatchByIds(@RequestBody @NotNullSize(message = "待删除客户端信息id不能为空") List<String> ids) {
        service.deleteBatch(ids);
        return ok(AnYiI18nUtil.get("Controller.BatchDeleteSuccess"));
    }


    @Operation(summary = "通过客户端信息id查询详情", tags = {"v1.0.0"}, description = "查询授权客户端信息详情")
    @Parameter(in = ParameterIn.PATH, description = "客户端信息id", name = "id", required = true)
    @GetMapping(value = "/select/one/{id}")
    public AnYiResult<ClientRegisteredDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "客户端信息id不能为空") String id) {
        return ok(service.getById(id));
    }


    @Operation(summary = "授权客户端信息分页查询", tags = {"v1.0.0"}, description = "分页查询授权客户端信息")
    @PostMapping(value = "/select/page")
    public AnYiResult<AnYiPageResult<ClientRegisteredPageDto>> selectPage(@RequestBody ClientRegisteredPageQuery vo) {
        return ok(service.pageByModel(vo));
    }


    @Operation(summary = "更新或添加权限", tags = {"v1.0.0"}, description = "更新或添加权限")
    @Parameter(in = ParameterIn.PATH, description = "客户端id", name = "clientDetailId", required = true)
    @PutMapping(value = "/update-auth/{clientDetailId}")
    public AnYiResult<String> updateAuth(@PathVariable(required = false) @PathNotBlankOrNull(message = "客户端id不能为空") String clientDetailId, @RequestBody @Valid RbacClientAuthVo vo) {
        service.updateAuth(clientDetailId, vo);
        return ok("设置权限成功");
    }

    @Operation(summary = "通过客户端id修改状态", tags = {"v1.0.0"}, description = "通过客户端id修改状态")
    @Parameters({@Parameter(in = ParameterIn.QUERY, description = "客户端信息id", name = "id", required = true), @Parameter(in = ParameterIn.QUERY, description = "类型:0-禁用,1-启用，2-锁定", name = "type", required = true)})
    @GetMapping(value = "/update/client/state")
    public AnYiResult<String> updateState(@RequestParam(required = false) @NotBlank(message = "客户端信息id不能为空") String id, @RequestParam(required = false) @NotNull(message = "操作类型不能为空") Integer type) {
        service.updateState(id, type);
        return ok("修改状态成功");
    }


    @Operation(summary = "查询客户端资源列表", tags = {"v1.0.0"}, description = "查询客户端资源列表")
    @GetMapping(value = "/select-resource/list")
    @Parameters({@Parameter(description = "类型:0-禁用的,1-有效的，不传所有", name = "type"), @Parameter(description = "客户端信息id", name = "id", required = true),})
    public AnYiResult<List<RbacResourceDto>> selectResourceList(@RequestParam(required = false) Integer type, @RequestParam(required = false) @NotBlank(message = "客户端信息id不能为空") String id) {
        return ok(resourceApiService.selectList(type, id));
    }


    @Operation(summary = "客户端资源api表分页查询", tags = {"v1.0.0"}, description = "客户端资源api表分页查询")
    @PostMapping(value = "/select-resource-api/page")
    public AnYiResult<AnYiPageResult<RbacResourceApiPageDto>> selectResourcePage(@RequestBody RbacClientResourceApiPageQuery vo) {
        return ok(resourceApiService.selectResourcePage(vo));
    }
}
