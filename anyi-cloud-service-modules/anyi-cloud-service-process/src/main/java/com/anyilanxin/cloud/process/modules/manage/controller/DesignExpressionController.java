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

package com.anyilanxin.cloud.process.modules.manage.controller;

import com.anyilanxin.cloud.corecommon.base.AnYiResult;
import com.anyilanxin.cloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.cloud.corecommon.utils.AnYiI18nUtil;
import com.anyilanxin.cloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.cloud.coremvc.base.controller.AnYiBaseController;
import com.anyilanxin.cloud.process.modules.manage.controller.vo.DesignExpressionPageQuery;
import com.anyilanxin.cloud.process.modules.manage.controller.vo.DesignExpressionVo;
import com.anyilanxin.cloud.process.modules.manage.service.IDesignExpressionService;
import com.anyilanxin.cloud.process.modules.manage.service.dto.DesignExpressionPageDto;
import io.swagger.v3.oas.annotations.Hidden;
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

/**
 * 流程表达式(DesignExpression)控制层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-03-27 00:09:25
 * @since 1.0.0
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Hidden
@Tag(name = "DesignExpression", description = "流程表达式相关")
@RequestMapping(value = "/design-expression", produces = MediaType.APPLICATION_JSON_VALUE)
public class DesignExpressionController extends AnYiBaseController {
    private final IDesignExpressionService service;

    @Operation(summary = "流程表达式添加", tags = {"v1.0.0"}, description = "添加流程表达式")
    @PostMapping(value = "/insert")
    public AnYiResult<String> insert(@RequestBody @Valid DesignExpressionVo vo) {
        service.save(vo);
        return ok(AnYiI18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过表达式id修改", tags = {"v1.0.0"}, description = "修改流程表达式")
    @Parameter(in = ParameterIn.PATH, description = "表达式id", name = "expressionId", required = true)
    @PutMapping(value = "/update/{expressionId}")
    public AnYiResult<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "表达式id不能为空") String expressionId, @RequestBody @Valid DesignExpressionVo vo) {
        service.updateById(expressionId, vo);
        return ok(AnYiI18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "修改状态", tags = {"v1.0.0"}, description = "修改状态")
    @Parameters({@Parameter(in = ParameterIn.QUERY, description = "表达式id", name = "expressionId", required = true), @Parameter(in = ParameterIn.QUERY, description = "修改类型：0-禁用,1-启用", name = "type", required = true)})
    @PutMapping(value = "/update-state")
    public AnYiResult<String> updateState(@RequestParam(required = false) @NotBlank(message = "表达式id不能为空") String expressionId, @RequestParam(required = false) @NotNull(message = "表达式id不能为空") Integer type) {
        service.updateState(expressionId, type);
        return ok("更新状态成功");
    }


    @Operation(summary = "流程表达式逻辑删除", tags = {"v1.0.0"}, description = "删除流程表达式")
    @Parameter(in = ParameterIn.PATH, description = "表达式id", name = "expressionId", required = true)
    @DeleteMapping(value = "/delete-one/{expressionId}")
    public AnYiResult<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "表达式id不能为空") String expressionId) {
        service.deleteById(expressionId);
        return ok(AnYiI18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "流程表达式分页查询", tags = {"v1.0.0"}, description = "分页查询流程表达式")
    @PostMapping(value = "/select/page")
    public AnYiResult<AnYiPageResult<DesignExpressionPageDto>> selectPage(@RequestBody DesignExpressionPageQuery vo) {
        return ok(service.pageByModel(vo));
    }
}
