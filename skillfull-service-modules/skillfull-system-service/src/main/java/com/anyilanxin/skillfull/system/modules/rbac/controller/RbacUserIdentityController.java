/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
package com.anyilanxin.skillfull.system.modules.rbac.controller;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.skillfull.coremvc.base.controller.BaseController;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacUserIdentityPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacUserIdentityVo;
import com.anyilanxin.skillfull.system.modules.rbac.service.IRbacUserIdentityService;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacUserIdentityPageDto;
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
* 实名信息表(RbacUserIdentity)控制层
*
* @author zxiaozhou
* @copyright zxiaozhou（https://skillfull.divisu.com）
* @date 2022-05-02 16:12:21
* @since JDK1.8
*/
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "RbacUserIdentity", description = "实名信息表Api接口相关")
@RequestMapping(value = "/rbac-user-identity", produces = MediaType.APPLICATION_JSON_VALUE)
public class RbacUserIdentityController extends BaseController {
    private final IRbacUserIdentityService service;

    @Operation(
            summary = "实名信息表添加",
            tags = {"v1.0.0"},
            description = "添加实名信息表")
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid RbacUserIdentityVo vo) {
        service.save(vo);
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }

    @Operation(
            summary = "实名信息审核",
            tags = {"v1.0.0"},
            description = "实名信息审核")
    @Parameter(in = ParameterIn.PATH, description = "实名信息id", name = "identityId", required = true)
    @PutMapping(value = "/audit/{identityId}")
    public Result<String> audit(
            @PathVariable(required = false) @PathNotBlankOrNull(message = "实名信息id不能为空") String identityId,
            @RequestBody @Valid RbacUserIdentityVo vo) {
        service.audit(identityId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }

    @Operation(
            summary = "实名信息表分页查询",
            tags = {"v1.0.0"},
            description = "分页查询实名信息表")
    @PostMapping(value = "/select/page")
    public Result<PageDto<RbacUserIdentityPageDto>> selectPage(
            @RequestBody RbacUserIdentityPageVo vo) {
        return ok(service.pageByModel(vo));
    }
}
