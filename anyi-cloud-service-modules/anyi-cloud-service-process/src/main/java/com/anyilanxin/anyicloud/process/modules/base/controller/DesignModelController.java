/*
 * Copyright (c) 2021-2023 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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

package com.anyilanxin.anyicloud.process.modules.base.controller;

import com.anyilanxin.anyicloud.corecommon.base.Result;
import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.anyicloud.coremvc.base.controller.BaseController;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.process.modules.base.controller.vo.DeleteDesignModelVo;
import com.anyilanxin.anyicloud.process.modules.base.controller.vo.DesignModelPageVo;
import com.anyilanxin.anyicloud.process.modules.base.controller.vo.DesignModelVo;
import com.anyilanxin.anyicloud.process.modules.base.service.IDesignModelService;
import com.anyilanxin.anyicloud.process.modules.base.service.dto.DesignModelDeploymentStatiDto;
import com.anyilanxin.anyicloud.process.modules.base.service.dto.DesignModelDto;
import com.anyilanxin.anyicloud.process.modules.base.service.dto.DesignModelPageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 流程模型管理(DesignModel)控制层
 *
 * @author zxh
 * @date 2021-11-25 05:22:56
 * @since 1.0.0
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "DesignModel", description = "流程模型管理")
@RequestMapping(value = "/design-model", produces = MediaType.APPLICATION_JSON_VALUE)
public class DesignModelController extends BaseController {
    private final IDesignModelService service;

    @Operation(summary = "流程模型管理添加", tags = {"v1.0.0"}, description = "添加流程模型管理")
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid DesignModelVo vo) {
        service.save(vo);
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过模型id修改", tags = {"v1.0.0"}, description = "修改流程模型管理")
    @Parameter(in = ParameterIn.PATH, description = "模型id", name = "modelId", required = true)
    @PutMapping(value = "/update/{modelId}")
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "模型id不能为空") String modelId, @RequestBody @Valid DesignModelVo vo) {
        service.updateById(modelId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "流程模型管理逻辑删除", tags = {"v1.0.0"}, description = "删除流程模型管理")
    @PostMapping(value = "/delete/by-model")
    public Result<String> deleteByModel(@RequestBody @Valid DeleteDesignModelVo vo) {
        service.deleteByModel(vo);
        return ok("删除模型成功");
    }


    @Operation(summary = "通过模型id查询详情", tags = {"v1.0.0"}, description = "查询流程模型管理详情")
    @Parameter(in = ParameterIn.PATH, description = "模型id", name = "modelId", required = true)
    @GetMapping(value = "/select/one/{modelId}")
    public Result<DesignModelDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "模型id不能为空") String modelId) {
        return ok(service.getById(modelId));
    }


    @Operation(summary = "模型部署情况统计", tags = {"v1.0.0"}, description = "模型部署情况统计")
    @GetMapping(value = "/stati")
    public Result<DesignModelDeploymentStatiDto> statistics() {
        return ok(service.statistics());
    }


    @Operation(summary = "流程模型管理分页查询", tags = {"v1.0.0"}, description = "分页查询流程模型管理")
    @PostMapping(value = "/select/page")
    public Result<PageDto<DesignModelPageDto>> selectPage(@RequestBody DesignModelPageVo vo) {
        return ok(service.pageByModel(vo));
    }
}
