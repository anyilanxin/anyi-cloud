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
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacSystemPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacSystemVo;
import com.anyilanxin.skillfull.system.modules.rbac.service.IRbacSystemService;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacSystemDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacSystemPageDto;
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
 * 系统(RbacSystem)控制层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-02 11:46:37
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "RbacSystem", description = "系统Api接口相关")
@RequestMapping(value = "/rbac-system", produces = MediaType.APPLICATION_JSON_VALUE)
public class RbacSystemController extends BaseController {
  private final IRbacSystemService service;

  @Operation(
      summary = "系统添加",
      tags = {"v1.0.0"},
      description = "添加系统")
  @PostMapping(value = "/insert")
  public Result<String> insert(@RequestBody @Valid RbacSystemVo vo) {
    service.save(vo);
    return ok(I18nUtil.get("Controller.InsertSuccess"));
  }

  @Operation(
      summary = "通过系统id修改",
      tags = {"v1.0.0"},
      description = "修改系统")
  @Parameter(in = ParameterIn.PATH, description = "系统id", name = "systemId", required = true)
  @PutMapping(value = "/update/{systemId}")
  public Result<String> update(
      @PathVariable(required = false) @PathNotBlankOrNull(message = "系统id不能为空") String systemId,
      @RequestBody @Valid RbacSystemVo vo) {
    service.updateById(systemId, vo);
    return ok(I18nUtil.get("Controller.UpdateSuccess"));
  }

  @Operation(
      summary = "系统逻辑删除",
      tags = {"v1.0.0"},
      description = "删除系统")
  @Parameter(in = ParameterIn.PATH, description = "系统id", name = "systemId", required = true)
  @DeleteMapping(value = "/delete-one/{systemId}")
  public Result<String> deleteById(
      @PathVariable(required = false) @PathNotBlankOrNull(message = "系统id不能为空") String systemId) {
    service.deleteById(systemId);
    return ok(I18nUtil.get("Controller.DeleteSuccess"));
  }

  @Operation(
      summary = "通过系统id查询详情",
      tags = {"v1.0.0"},
      description = "查询系统详情")
  @Parameter(in = ParameterIn.PATH, description = "系统id", name = "systemId", required = true)
  @GetMapping(value = "/select/one/{systemId}")
  public Result<RbacSystemDto> getById(
      @PathVariable(required = false) @PathNotBlankOrNull(message = "系统id不能为空") String systemId) {
    return ok(service.getById(systemId));
  }

  @Operation(
      summary = "查询有效的系统信息",
      tags = {"v1.0.0"},
      description = "查询有效的系统信息")
  @GetMapping(value = "/select/list")
  public Result<List<RbacSystemDto>> getList() {
    return ok(service.selectList());
  }

  @Operation(
      summary = "系统分页查询",
      tags = {"v1.0.0"},
      description = "分页查询系统")
  @PostMapping(value = "/select/page")
  public Result<PageDto<RbacSystemPageDto>> selectPage(@RequestBody RbacSystemPageVo vo) {
    return ok(service.pageByModel(vo));
  }
}
