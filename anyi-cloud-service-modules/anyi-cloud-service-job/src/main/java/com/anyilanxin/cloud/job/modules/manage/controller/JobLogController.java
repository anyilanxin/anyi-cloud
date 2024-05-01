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

package com.anyilanxin.cloud.job.modules.manage.controller;

import com.anyilanxin.cloud.corecommon.base.AnYiResult;
import com.anyilanxin.cloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.cloud.corecommon.utils.AnYiI18nUtil;
import com.anyilanxin.cloud.corecommon.validation.annotation.NotNullSize;
import com.anyilanxin.cloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.cloud.coremvc.base.controller.AnYiBaseController;
import com.anyilanxin.cloud.job.modules.manage.controller.vo.JobClearLogVo;
import com.anyilanxin.cloud.job.modules.manage.controller.vo.JobLogPageQuery;
import com.anyilanxin.cloud.job.modules.manage.service.IJobLogService;
import com.anyilanxin.cloud.job.modules.manage.service.dto.JobLogDto;
import com.anyilanxin.cloud.job.modules.manage.service.dto.JobLogPageDto;
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
 * job日志(JobLog)控制层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-06-25 00:41:52
 * @since 1.0.0
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "JobLog", description = "job日志相关")
@RequestMapping(value = "/job-log", produces = MediaType.APPLICATION_JSON_VALUE)
public class JobLogController extends AnYiBaseController {
    private final IJobLogService service;

    @Operation(summary = "job日志逻辑删除", tags = {"v1.0.0"}, description = "删除job日志")
    @Parameter(in = ParameterIn.PATH, description = "job日志id", name = "id", required = true)
    @DeleteMapping(value = "/delete-one/{id}")
    public AnYiResult<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "job日志id不能为空") Long id) {
        service.deleteById(id);
        return ok(AnYiI18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "job日志逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除job日志")
    @PostMapping(value = "/delete-batch")
    public AnYiResult<String> deleteBatchByIds(@RequestBody @NotNullSize(message = "待删除job日志id不能为空") List<Long> ids) {
        service.deleteBatch(ids);
        return ok(AnYiI18nUtil.get("Controller.BatchDeleteSuccess"));
    }


    @Operation(summary = "通过job日志id查询详情", tags = {"v1.0.0"}, description = "查询job日志详情")
    @Parameter(in = ParameterIn.PATH, description = "job日志id", name = "id", required = true)
    @GetMapping(value = "/select/one/{id}")
    public AnYiResult<JobLogDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "job日志id不能为空") Long id) {
        return ok(service.getById(id));
    }


    @Operation(summary = "通过条件清理日志", tags = {"v1.0.0"}, description = "通过条件清理日志")
    @PostMapping(value = "/clear-log")
    public AnYiResult<String> clearLog(@RequestBody @Valid JobClearLogVo vo) {
        service.clearLog(vo);
        return ok("日志清理成功");
    }


    @Operation(summary = "job日志分页查询", tags = {"v1.0.0"}, description = "分页查询job日志")
    @PostMapping(value = "page-list")
    public AnYiResult<AnYiPageResult<JobLogPageDto>> selectPage(@RequestBody JobLogPageQuery vo) {
        return ok(service.pageByModel(vo));
    }
}
