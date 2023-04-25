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
 *   1.请不要删除和修改根目录下的LICENSE文件。
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 *   3.请保留源码和相关描述文件的项目出处，作者声明等。
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 *   6.若您的项目无法满足以上几点，可申请商业授权
 */

package com.anyilanxin.skillfull.system.modules.rbac.controller;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.skillfull.coremvc.base.controller.BaseController;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacPositionPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacPositionVo;
import com.anyilanxin.skillfull.system.modules.rbac.service.IRbacPositionService;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacPositionDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacPositionPageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 职位表(RbacPosition)控制层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-02 16:12:20
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "RbacPosition", description = "职位表相关")
@RequestMapping(value = "/rbac-position", produces = MediaType.APPLICATION_JSON_VALUE)
public class RbacPositionController extends BaseController {
  private final IRbacPositionService service;

  @Operation(
      summary = "职位表添加",
      tags = {"v1.0.0"},
      description = "添加职位表")
  @PostMapping(value = "/insert")
  public Result<String> insert(@RequestBody @Valid RbacPositionVo vo) {
    service.save(vo);
    return ok(I18nUtil.get("Controller.InsertSuccess"));
  }

  @Operation(
      summary = "通过职位id修改",
      tags = {"v1.0.0"},
      description = "修改职位表")
  @Parameter(in = ParameterIn.PATH, description = "职位id", name = "positionId", required = true)
  @PutMapping(value = "/update/{positionId}")
  public Result<String> update(
      @PathVariable(required = false) @PathNotBlankOrNull(message = "职位id不能为空") String positionId,
      @RequestBody @Valid RbacPositionVo vo) {
    service.updateById(positionId, vo);
    return ok(I18nUtil.get("Controller.UpdateSuccess"));
  }

  @Operation(
      summary = "职位表逻辑删除",
      tags = {"v1.0.0"},
      description = "删除职位表")
  @Parameter(in = ParameterIn.PATH, description = "职位id", name = "positionId", required = true)
  @DeleteMapping(value = "/delete-one/{positionId}")
  public Result<String> deleteById(
      @PathVariable(required = false) @PathNotBlankOrNull(message = "职位id不能为空") String positionId) {
    service.deleteById(positionId);
    return ok(I18nUtil.get("Controller.DeleteSuccess"));
  }

  @Operation(
      summary = "通过职位id修改职位状态",
      tags = {"v1.0.0"},
      description = "通过职位id修改职位状态")
  @Parameters({
    @Parameter(in = ParameterIn.PATH, description = "职位id", name = "positionId", required = true),
    @Parameter(description = "类型:0-禁用,1-启用", name = "type", required = true)
  })
  @GetMapping(value = "/update/position/state")
  public Result<String> updatePositionState(
      @RequestParam(required = false) @NotBlank(message = "职位id不能为空") String positionId,
      @RequestParam(required = false)
          @NotNull(message = "操作类型不能为空")
          @Min(value = 0, message = "操作类型只能为0、1")
          @Max(value = 1, message = "操作类型只能为0、1")
          Integer type) {
    service.updatePositionState(positionId, type);
    return ok(type == 0 ? "职位禁用成功" : "职位启用成功");
  }

  @Operation(
      summary = "通过条件查询职位表多条数据",
      tags = {"v1.0.0"},
      description = "通过条件查询职位表")
  @GetMapping(value = "/select/list-all")
  public Result<List<RbacPositionDto>> getAllList() {
    return ok(service.getAllList());
  }

  @Operation(
      summary = "职位表分页查询",
      tags = {"v1.0.0"},
      description = "分页查询职位表")
  @PostMapping(value = "/select/page")
  public Result<PageDto<RbacPositionPageDto>> selectPage(@RequestBody RbacPositionPageVo vo) {
    return ok(service.pageByModel(vo));
  }
}
