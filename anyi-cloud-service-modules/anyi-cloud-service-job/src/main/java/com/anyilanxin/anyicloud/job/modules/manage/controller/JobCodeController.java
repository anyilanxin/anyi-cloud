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

package com.anyilanxin.anyicloud.job.modules.manage.controller;

import com.anyilanxin.anyicloud.corecommon.base.AnYiResult;
import com.anyilanxin.anyicloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.anyicloud.corecommon.utils.AnYiI18nUtil;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotNullSize;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.anyicloud.coremvc.base.controller.AnYiBaseController;
import com.anyilanxin.anyicloud.job.modules.manage.controller.vo.JobLogReportPageQuery;
import com.anyilanxin.anyicloud.job.modules.manage.controller.vo.JobLogReportQueryVo;
import com.anyilanxin.anyicloud.job.modules.manage.controller.vo.JobLogReportVo;
import com.anyilanxin.anyicloud.job.modules.manage.service.IJobLogReportService;
import com.anyilanxin.anyicloud.job.modules.manage.service.dto.JobLogReportDto;
import com.anyilanxin.anyicloud.job.modules.manage.service.dto.JobLogReportPageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
 * JobCode控制层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-06-25 00:41:55
 * @since 1.0.0
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "JobCode", description = "job编码相关")
@RequestMapping(value = "/job-code", produces = MediaType.APPLICATION_JSON_VALUE)
public class JobCodeController extends AnYiBaseController {
    private final IJobLogReportService service;

    @Operation(summary = "添加", tags = {"v1.0.0"}, description = "添加", hidden = true)
    @PostMapping(value = "/insert")
    public AnYiResult<String> insert(@RequestBody @Valid JobLogReportVo vo) {
        service.save(vo);
        return ok(AnYiI18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过job报告日志id修改", tags = {"v1.0.0"}, description = "修改", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "job报告日志id", name = "id", required = true)
    @PutMapping(value = "/update/{id}")
    public AnYiResult<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "job报告日志id不能为空") Integer id, @RequestBody @Valid JobLogReportVo vo) {
        service.updateById(id, vo);
        return ok(AnYiI18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "逻辑删除", tags = {"v1.0.0"}, description = "删除", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "job报告日志id", name = "id", required = true)
    @DeleteMapping(value = "/delete-one/{id}")
    public AnYiResult<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "job报告日志id不能为空") Integer id) {
        service.deleteById(id);
        return ok(AnYiI18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除", hidden = true)
    @PostMapping(value = "/delete-batch")
    public AnYiResult<String> deleteBatchByIds(@RequestBody @NotNullSize(message = "待删除job报告日志id不能为空") List<Integer> ids) {
        service.deleteBatch(ids);
        return ok(AnYiI18nUtil.get("Controller.BatchDeleteSuccess"));
    }


    @Operation(summary = "通过job报告日志id查询详情", tags = {"v1.0.0"}, description = "查询详情", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "job报告日志id", name = "id", required = true)
    @GetMapping(value = "/select/one/{id}")
    public AnYiResult<JobLogReportDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "job报告日志id不能为空") Integer id) {
        return ok(service.getById(id));
    }


    @Operation(summary = "通过条件查询多条数据", tags = {"v1.0.0"}, description = "通过条件查询", hidden = true)
    @PostMapping(value = "/select/list/by-model")
    public AnYiResult<List<JobLogReportDto>> selectListByModel(@RequestBody JobLogReportQueryVo vo) {
        return ok(service.selectListByModel(vo));
    }


    @Operation(summary = "分页查询", tags = {"v1.0.0"}, description = "分页查询", hidden = true)
    @PostMapping(value = "/select/page")
    public AnYiResult<AnYiPageResult<JobLogReportPageDto>> selectPage(@RequestBody JobLogReportPageQuery vo) {
        return ok(service.pageByModel(vo));
    }
}
