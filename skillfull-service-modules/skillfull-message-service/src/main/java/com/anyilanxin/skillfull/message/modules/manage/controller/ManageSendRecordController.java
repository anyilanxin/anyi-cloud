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

package com.anyilanxin.skillfull.message.modules.manage.controller;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.corecommon.validation.annotation.NotNullSize;
import com.anyilanxin.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.skillfull.coremvc.base.controller.BaseController;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.message.modules.manage.controller.vo.ManageSendRecordPageVo;
import com.anyilanxin.skillfull.message.modules.manage.service.IManageSendRecordService;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageSendRecordDto;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageSendRecordPageDto;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 消息发送记录表(ManageSendRecord)控制层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-03-29 05:23:40
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Hidden
@Tag(name = "ManageSendRecord", description = "消息发送记录表相关")
@RequestMapping(value = "/manageSendRecord", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManageSendRecordController extends BaseController {
    private final IManageSendRecordService service;

    @Operation(
            summary = "消息发送记录表逻辑删除",
            tags = {"v1.0.0"},
            description = "删除消息发送记录表")
    @Parameter(in = ParameterIn.PATH, description = "消息id", name = "sendRecordId", required = true)
    @DeleteMapping(value = "/delete-one/{sendRecordId}")
    public Result<String> deleteById(
            @PathVariable(required = false) @PathNotBlankOrNull(message = "消息id不能为空")
                    String sendRecordId) {
        service.deleteById(sendRecordId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }

    @Operation(
            summary = "消息发送记录表逻辑批量删除",
            tags = {"v1.0.0"},
            description = "批量删除消息发送记录表")
    @PostMapping(value = "/delete-batch")
    public Result<String> deleteBatchByIds(
            @RequestBody @NotNullSize(message = "待删除消息id不能为空") List<String> sendRecordIds) {
        service.deleteBatch(sendRecordIds);
        return ok(I18nUtil.get("Controller.BatchDeleteSuccess"));
    }

    @Operation(
            summary = "通过消息id查询详情",
            tags = {"v1.0.0"},
            description = "查询消息发送记录表详情")
    @Parameter(in = ParameterIn.PATH, description = "消息id", name = "sendRecordId", required = true)
    @GetMapping(value = "/select/one/{sendRecordId}")
    public Result<ManageSendRecordDto> getById(
            @PathVariable(required = false) @PathNotBlankOrNull(message = "消息id不能为空")
                    String sendRecordId) {
        return ok(service.getById(sendRecordId));
    }

    @Operation(
            summary = "消息发送记录表分页查询",
            tags = {"v1.0.0"},
            description = "分页查询消息发送记录表")
    @PostMapping(value = "/select/page")
    public Result<PageDto<ManageSendRecordPageDto>> selectPage(
            @RequestBody ManageSendRecordPageVo vo) {
        return ok(service.pageByModel(vo));
    }
}
