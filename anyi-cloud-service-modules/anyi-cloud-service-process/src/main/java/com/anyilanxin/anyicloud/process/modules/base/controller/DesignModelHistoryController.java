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

package com.anyilanxin.anyicloud.process.modules.base.controller;

import com.anyilanxin.anyicloud.corecommon.base.Result;
import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.anyicloud.coremvc.base.controller.BaseController;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.process.modules.base.controller.vo.DeleteHistoryDesignModelVo;
import com.anyilanxin.anyicloud.process.modules.base.controller.vo.DesignModelHistoryPageVo;
import com.anyilanxin.anyicloud.process.modules.base.service.IDesignModelHistoryService;
import com.anyilanxin.anyicloud.process.modules.base.service.dto.DesignModelHistoryDto;
import com.anyilanxin.anyicloud.process.modules.base.service.dto.DesignModelHistoryPageDto;
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
 * 流程模型历史(DesignModelHistory)控制层
 *
 * @author zxh
 * @date 2021-11-25 09:52:36
 * @since 1.0.0
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "DesignModelHistory", description = "流程模型历史相关")
@RequestMapping(value = "/design-model/history", produces = MediaType.APPLICATION_JSON_VALUE)
public class DesignModelHistoryController extends BaseController {
    private final IDesignModelHistoryService service;

    @Operation(summary = "流程模型历史逻辑删除", tags = {"v1.0.0"}, description = "删除流程模型历史")
    @PostMapping(value = "/delete/by-model")
    public Result<String> deleteByModel(@RequestBody @Valid DeleteHistoryDesignModelVo vo) {
        service.deleteByModel(vo);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "通过历史模型id查询详情", tags = {"v1.0.0"}, description = "查询流程模型历史详情")
    @Parameter(in = ParameterIn.PATH, description = "历史模型id", name = "historyModelId", required = true)
    @GetMapping(value = "/select/one/{historyModelId}")
    public Result<DesignModelHistoryDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "历史模型id不能为空") String historyModelId) {
        return ok(service.getById(historyModelId));
    }


    @Operation(summary = "流程模型历史分页查询", tags = {"v1.0.0"}, description = "分页查询流程模型历史")
    @PostMapping(value = "/select/page")
    public Result<PageDto<DesignModelHistoryPageDto>> selectPage(@RequestBody DesignModelHistoryPageVo vo) {
        return ok(service.pageByModel(vo));
    }
}