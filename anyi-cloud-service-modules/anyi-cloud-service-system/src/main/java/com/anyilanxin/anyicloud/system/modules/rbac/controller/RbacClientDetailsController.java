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
import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotNullSize;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.anyicloud.coremvc.base.controller.BaseController;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacClientAuthVo;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacClientDetailsPageVo;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacClientDetailsVo;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacClientDetailsService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacClientDetailsDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacClientDetailsPageDto;
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
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 授权客户端信息(RbacClientDetails)控制层
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
@Tag(name = "RbacClientDetails", description = "授权客户端相关")
@RequestMapping(value = "/rbac-client", produces = MediaType.APPLICATION_JSON_VALUE)
public class RbacClientDetailsController extends BaseController {
    private final IRbacClientDetailsService service;

    @Operation(summary = "授权客户端信息添加", tags = {"v1.0.0"}, description = "添加授权客户端信息")
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid RbacClientDetailsVo vo) {
        service.save(vo);
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过客户端信息id修改", tags = {"v1.0.0"}, description = "修改授权客户端信息")
    @Parameter(in = ParameterIn.PATH, description = "客户端信息id", name = "clientDetailId", required = true)
    @PutMapping(value = "/update/{clientDetailId}")
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "客户端信息id不能为空") String clientDetailId, @RequestBody @Valid RbacClientDetailsVo vo) {
        service.updateById(clientDetailId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "更新或添加权限", tags = {"v1.0.0"}, description = "更新或添加权限")
    @Parameter(in = ParameterIn.PATH, description = "客户端id", name = "clientDetailId", required = true)
    @PutMapping(value = "/update-auth/{clientDetailId}")
    public Result<String> updateAuth(@PathVariable(required = false) @PathNotBlankOrNull(message = "客户端id不能为空") String clientDetailId, @RequestBody @Valid RbacClientAuthVo vo) {
        service.updateAuth(clientDetailId, vo);
        return ok("设置权限成功");
    }


    @Operation(summary = "授权客户端信息逻辑删除", tags = {"v1.0.0"}, description = "删除授权客户端信息")
    @Parameter(in = ParameterIn.PATH, description = "客户端信息id", name = "clientDetailId", required = true)
    @DeleteMapping(value = "/delete-one/{clientDetailId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "客户端信息id不能为空") String clientDetailId) {
        service.deleteById(clientDetailId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "通过客户端id修改状态", tags = {"v1.0.0"}, description = "通过客户端id修改状态")
    @Parameters({@Parameter(in = ParameterIn.QUERY, description = "客户端信息id", name = "clientDetailId", required = true), @Parameter(in = ParameterIn.QUERY, description = "类型:0-禁用,1-启用，2-锁定", name = "type", required = true)})
    @GetMapping(value = "/update/client/state")
    public Result<String> updateState(@RequestParam(required = false) @NotBlank(message = "客户端信息id不能为空") String clientDetailId, @RequestParam(required = false) @NotNull(message = "操作类型不能为空") Integer type) {
        service.updateState(clientDetailId, type);
        return ok("修改状态成功");
    }


    @Operation(summary = "授权客户端信息逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除授权客户端信息")
    @PostMapping(value = "/delete-batch")
    public Result<String> deleteBatchByIds(@RequestBody @NotNullSize(message = "待删除客户端信息id不能为空") List<String> clientDetailIds) {
        service.deleteBatch(clientDetailIds);
        return ok(I18nUtil.get("Controller.BatchDeleteSuccess"));
    }


    @Operation(summary = "通过客户端信息id查询详情", tags = {"v1.0.0"}, description = "查询授权客户端信息详情")
    @Parameter(in = ParameterIn.PATH, description = "客户端信息id", name = "clientDetailId", required = true)
    @GetMapping(value = "/select/one/{clientDetailId}")
    public Result<RbacClientDetailsDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "客户端信息id不能为空") String clientDetailId) {
        return ok(service.getById(clientDetailId));
    }


    @Operation(summary = "授权客户端信息分页查询", tags = {"v1.0.0"}, description = "分页查询授权客户端信息")
    @PostMapping(value = "/select/page")
    public Result<PageDto<RbacClientDetailsPageDto>> selectPage(@RequestBody RbacClientDetailsPageVo vo) {
        return ok(service.pageByModel(vo));
    }
}
