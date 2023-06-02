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
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
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
package com.anyilanxin.anyicloud.message.modules.manage.controller;

import com.anyilanxin.anyicloud.corecommon.base.Result;
import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotNullSize;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.anyicloud.coremvc.base.controller.BaseController;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.message.modules.manage.controller.vo.ManageAnnouncementPageVo;
import com.anyilanxin.anyicloud.message.modules.manage.controller.vo.ManageAnnouncementQueryVo;
import com.anyilanxin.anyicloud.message.modules.manage.controller.vo.ManageAnnouncementVo;
import com.anyilanxin.anyicloud.message.modules.manage.service.IManageAnnouncementService;
import com.anyilanxin.anyicloud.message.modules.manage.service.dto.ManageAnnouncementDto;
import com.anyilanxin.anyicloud.message.modules.manage.service.dto.ManageAnnouncementPageDto;
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
 * 系统通告公告管理(ManageAnnouncement)控制层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-03-29 08:34:21
 * @since 1.0.0
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Hidden
@Tag(name = "ManageAnnouncement", description = "系统通告公告管理Api接口相关")
@RequestMapping(value = "/manageAnnouncement", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManageAnnouncementController extends BaseController {
    private final IManageAnnouncementService service;

    @Operation(summary = "系统通告公告管理添加", tags = {"v1.0.0"}, description = "添加系统通告公告管理", hidden = true)
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid ManageAnnouncementVo vo) {
        service.save(vo);
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过通知公告id修改", tags = {"v1.0.0"}, description = "修改系统通告公告管理", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "通知公告id", name = "anntId", required = true)
    @PutMapping(value = "/update/{anntId}")
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "通知公告id不能为空") String anntId, @RequestBody @Valid ManageAnnouncementVo vo) {
        service.updateById(anntId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "系统通告公告管理逻辑删除", tags = {"v1.0.0"}, description = "删除系统通告公告管理", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "通知公告id", name = "anntId", required = true)
    @DeleteMapping(value = "/delete-one/{anntId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "通知公告id不能为空") String anntId) {
        service.deleteById(anntId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "系统通告公告管理逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除系统通告公告管理", hidden = true)
    @PostMapping(value = "/delete-batch")
    public Result<String> deleteBatchByIds(@RequestBody @NotNullSize(message = "待删除通知公告id不能为空") List<String> anntIds) {
        service.deleteBatch(anntIds);
        return ok(I18nUtil.get("Controller.BatchDeleteSuccess"));
    }


    @Operation(summary = "通过通知公告id查询详情", tags = {"v1.0.0"}, description = "查询系统通告公告管理详情", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "通知公告id", name = "anntId", required = true)
    @GetMapping(value = "/select/one/{anntId}")
    public Result<ManageAnnouncementDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "通知公告id不能为空") String anntId) {
        return ok(service.getById(anntId));
    }


    @Operation(summary = "通过条件查询系统通告公告管理多条数据", tags = {"v1.0.0"}, description = "通过条件查询系统通告公告管理", hidden = true)
    @PostMapping(value = "/select/list/by-model")
    public Result<List<ManageAnnouncementDto>> selectListByModel(@RequestBody ManageAnnouncementQueryVo vo) {
        return ok(service.selectListByModel(vo));
    }


    @Operation(summary = "系统通告公告管理分页查询", tags = {"v1.0.0"}, description = "分页查询系统通告公告管理", hidden = true)
    @PostMapping(value = "/select/page")
    public Result<PageDto<ManageAnnouncementPageDto>> selectPage(@RequestBody ManageAnnouncementPageVo vo) {
        return ok(service.pageByModel(vo));
    }
}
