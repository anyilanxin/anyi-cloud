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

package com.anyilanxin.anyicloud.system.modules.manage.controller;

import com.anyilanxin.anyicloud.corecommon.base.Result;
import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.anyicloud.coremvc.base.controller.BaseController;
import com.anyilanxin.anyicloud.system.modules.manage.controller.vo.ManageRouteVo;
import com.anyilanxin.anyicloud.system.modules.manage.service.IManageRouteService;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.ManageRouteDto;
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
import org.hibernate.validator.constraints.Range;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 路由(ManageRoute)控制层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2021-12-19 00:22:15
 * @since 1.0.0
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "GatewayManage", description = "网关管理")
@RequestMapping(value = "/manage-route", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManageRouteController extends BaseController {
    private final IManageRouteService service;

    @Operation(summary = "路由添加", tags = {"v1.0.0"}, description = "添加路由")
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid ManageRouteVo vo) {
        service.save(vo);
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过路由id修改", tags = {"v1.0.0"}, description = "修改路由")
    @Parameter(in = ParameterIn.PATH, description = "路由id", name = "routeId", required = true)
    @PutMapping(value = "/update/{routeId}")
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "路由id不能为空") String routeId, @RequestBody @Valid ManageRouteVo vo) {
        service.updateById(routeId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "路由逻辑删除", tags = {"v1.0.0"}, description = "删除路由")
    @Parameter(in = ParameterIn.PATH, description = "路由id", name = "routeId", required = true)
    @DeleteMapping(value = "/delete-one/{routeId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "路由id不能为空") String routeId) {
        service.deleteById(routeId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "通过路由id查询详情", tags = {"v1.0.0"}, description = "查询路由详情")
    @Parameter(in = ParameterIn.PATH, description = "路由id", name = "routeId", required = true)
    @GetMapping(value = "/select/one/{routeId}")
    public Result<ManageRouteDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "路由id不能为空") String routeId) {
        return ok(service.getById(routeId));
    }


    @Operation(summary = "查询服务路由", tags = {"v1.0.0"}, description = "查询所有服务自定义过滤器")
    @Parameter(in = ParameterIn.PATH, description = "服务id", name = "serviceId", required = true)
    @GetMapping(value = "/select/list/{serviceId}")
    public Result<List<ManageRouteDto>> selectList(@PathVariable(required = false) @PathNotBlankOrNull(message = "服务id不能为空") String serviceId) {
        return ok(service.selectList(serviceId));
    }


    @Operation(summary = "修改路由状态", tags = {"v1.0.0"}, description = "修改路由状态")
    @Parameters({@Parameter(in = ParameterIn.QUERY, description = "路由id", name = "routeId", required = true), @Parameter(in = ParameterIn.QUERY, description = "操作类型:0-禁止,1-启用", name = "state", required = true)})
    @GetMapping(value = "/update-status")
    public Result<String> updateStatus(@RequestParam(required = false) @NotBlank(message = "路由id不能为空") String routeId, @RequestParam(required = false) @NotNull(message = "操作类型不能为空") @Range(min = 0, max = 1, message = "操作类型只能为0、1") Integer state) {
        service.updateStatus(routeId, state);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }
}
