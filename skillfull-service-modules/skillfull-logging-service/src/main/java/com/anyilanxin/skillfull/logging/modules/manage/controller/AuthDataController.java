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

package com.anyilanxin.skillfull.logging.modules.manage.controller;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.corecommon.validation.annotation.NotNullSize;
import com.anyilanxin.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.skillfull.coremvc.base.controller.BaseController;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.logging.modules.manage.controller.vo.AuthDataPageVo;
import com.anyilanxin.skillfull.logging.modules.manage.service.IAuthDataService;
import com.anyilanxin.skillfull.logging.modules.manage.service.dto.AuthDataDto;
import com.anyilanxin.skillfull.logging.modules.manage.service.dto.AuthDataPageDto;
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
 * 登录日志(AuthData)控制层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-01-26 21:53:02
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "AuthData", description = "授权日志")
@RequestMapping(value = "/auth-data", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthDataController extends BaseController {
  private final IAuthDataService service;

  @Operation(
      summary = "登录日志删除",
      tags = {"v1.0.0"},
      description = "登录日志删除")
  @Parameter(in = ParameterIn.PATH, description = "授权日志id", name = "authLogId", required = true)
  @DeleteMapping(value = "/delete-one/{authLogId}")
  public Result<String> deleteById(
      @PathVariable(required = false) @PathNotBlankOrNull(message = "授权日志id不能为空")
          String authLogId) {
    service.deleteById(authLogId);
    return ok(I18nUtil.get("Controller.DeleteSuccess"));
  }

  @Operation(
      summary = "登录日志批量删除",
      tags = {"v1.0.0"},
      description = "登录日志批量删除")
  @PostMapping(value = "/delete-batch")
  public Result<String> deleteBatchByIds(
      @RequestBody @NotNullSize(message = "待删除授权日志id不能为空") List<String> authLogIds) {
    service.deleteBatch(authLogIds);
    return ok(I18nUtil.get("Controller.BatchDeleteSuccess"));
  }

  @Operation(
      summary = "通过授权日志id查询详情",
      tags = {"v1.0.0"},
      description = "查询登录日志详情")
  @Parameter(in = ParameterIn.PATH, description = "授权日志id", name = "authLogId", required = true)
  @GetMapping(value = "/select/one/{authLogId}")
  public Result<AuthDataDto> getById(
      @PathVariable(required = false) @PathNotBlankOrNull(message = "授权日志id不能为空")
          String authLogId) {
    return ok(service.getById(authLogId));
  }

  @Operation(
      summary = "登录日志分页查询",
      tags = {"v1.0.0"},
      description = "分页查询登录日志")
  @PostMapping(value = "/select/page")
  public Result<PageDto<AuthDataPageDto>> selectPage(@RequestBody AuthDataPageVo vo) {
    return ok(service.pageByModel(vo));
  }
}
