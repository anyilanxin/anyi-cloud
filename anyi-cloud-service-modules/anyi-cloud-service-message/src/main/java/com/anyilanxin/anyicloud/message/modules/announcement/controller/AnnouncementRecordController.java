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

package com.anyilanxin.anyicloud.message.modules.announcement.controller;

import com.anyilanxin.anyicloud.corecommon.base.Result;
import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotNullSize;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.anyicloud.coremvc.base.controller.BaseController;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.message.modules.announcement.controller.vo.AnnouncementRecordPageVo;
import com.anyilanxin.anyicloud.message.modules.announcement.controller.vo.AnnouncementRecordQueryVo;
import com.anyilanxin.anyicloud.message.modules.announcement.controller.vo.AnnouncementRecordVo;
import com.anyilanxin.anyicloud.message.modules.announcement.service.IAnnouncementRecordService;
import com.anyilanxin.anyicloud.message.modules.announcement.service.dto.AnnouncementRecordDto;
import com.anyilanxin.anyicloud.message.modules.announcement.service.dto.AnnouncementRecordPageDto;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 系统通知公告阅读记录(AnnouncementRecord)控制层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-03-29 08:35:33
 * @since 1.0.0
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Hidden
@Tag(name = "AnnouncementRecord", description = "系统通知公告阅读记录Api接口相关")
@RequestMapping(value = "/announcementRecord", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnnouncementRecordController extends BaseController {
    private final IAnnouncementRecordService service;

    @Operation(summary = "系统通知公告阅读记录添加", tags = {"v1.0.0"}, description = "添加系统通知公告阅读记录", hidden = true)
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid AnnouncementRecordVo vo) {
        service.save(vo);
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过通知公告阅读记录id修改", tags = {"v1.0.0"}, description = "修改系统通知公告阅读记录", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "通知公告阅读记录id", name = "anntReadId", required = true)
    @PutMapping(value = "/update/{anntReadId}")
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "通知公告阅读记录id不能为空") String anntReadId, @RequestBody @Valid AnnouncementRecordVo vo) {
        service.updateById(anntReadId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "系统通知公告阅读记录逻辑删除", tags = {"v1.0.0"}, description = "删除系统通知公告阅读记录", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "通知公告阅读记录id", name = "anntReadId", required = true)
    @DeleteMapping(value = "/delete-one/{anntReadId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "通知公告阅读记录id不能为空") String anntReadId) {
        service.deleteById(anntReadId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "系统通知公告阅读记录逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除系统通知公告阅读记录", hidden = true)
    @PostMapping(value = "/delete-batch")
    public Result<String> deleteBatchByIds(@RequestBody @NotNullSize(message = "待删除通知公告阅读记录id不能为空") List<String> anntReadIds) {
        service.deleteBatch(anntReadIds);
        return ok(I18nUtil.get("Controller.BatchDeleteSuccess"));
    }


    @Operation(summary = "通过通知公告阅读记录id查询详情", tags = {"v1.0.0"}, description = "查询系统通知公告阅读记录详情", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "通知公告阅读记录id", name = "anntReadId", required = true)
    @GetMapping(value = "/select/one/{anntReadId}")
    public Result<AnnouncementRecordDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "通知公告阅读记录id不能为空") String anntReadId) {
        return ok(service.getById(anntReadId));
    }


    @Operation(summary = "通过条件查询系统通知公告阅读记录多条数据", tags = {"v1.0.0"}, description = "通过条件查询系统通知公告阅读记录", hidden = true)
    @PostMapping(value = "/select/list/by-model")
    public Result<List<AnnouncementRecordDto>> selectListByModel(@RequestBody AnnouncementRecordQueryVo vo) {
        return ok(service.selectListByModel(vo));
    }


    @Operation(summary = "系统通知公告阅读记录分页查询", tags = {"v1.0.0"}, description = "分页查询系统通知公告阅读记录", hidden = true)
    @PostMapping(value = "/select/page")
    public Result<PageDto<AnnouncementRecordPageDto>> selectPage(@RequestBody AnnouncementRecordPageVo vo) {
        return ok(service.pageByModel(vo));
    }
}
