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

package com.anyilanxin.anyicloud.process.modules.rbac.controller;

import com.anyilanxin.anyicloud.corecommon.base.AnYiResult;
import com.anyilanxin.anyicloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.anyicloud.corecommon.utils.AnYiI18nUtil;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.anyicloud.coremvc.base.controller.AnYiBaseController;
import com.anyilanxin.anyicloud.process.modules.rbac.controller.vo.TenantQueryPageVoCamunda;
import com.anyilanxin.anyicloud.process.modules.rbac.controller.vo.TenantQueryVo;
import com.anyilanxin.anyicloud.process.modules.rbac.controller.vo.TenantVo;
import com.anyilanxin.anyicloud.process.modules.rbac.service.ITenantService;
import com.anyilanxin.anyicloud.process.modules.rbac.service.dto.TenantDto;
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
import java.util.Set;

/**
 * 租户相关
 *
 * @author zxh
 * @date 2021-11-05 17:30
 * @since 1.0.0
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "RbacTenant", description = "租户相关")
@RequestMapping(value = "/rbac-tenant", produces = MediaType.APPLICATION_JSON_VALUE)
public class TenantController extends AnYiBaseController {
    private final ITenantService service;

    @Operation(summary = "添加或更新租户", tags = {"v1.0.0"}, description = "添加或更新租户")
    @PostMapping(value = "/insert-or-update")
    public AnYiResult<String> saveOrUpdate(@RequestBody @Valid TenantVo vo) {
        service.saveOrUpdate(vo);
        return ok("租户操作成功");
    }


    @Operation(summary = "删除租户", tags = {"v1.0.0"}, description = "删除租户")
    @Parameter(in = ParameterIn.PATH, description = "租户id", name = "tenantId", required = true)
    @DeleteMapping(value = "/delete-one/{tenantId}")
    public AnYiResult<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "租户id不能为空") String tenantId) {
        service.deleteTenant(tenantId);
        return ok(AnYiI18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "全量同步租户信息", tags = {"v1.0.0"}, description = "全量同步租户信息")
    @PostMapping(value = "/all")
    public AnYiResult<String> syncTenant(@RequestBody @Valid Set<TenantVo> voSet) {
        service.syncTenant(voSet);
        return ok("租户信息操作成功");
    }


    @Operation(summary = "查询租户详情", tags = {"v1.0.0"}, description = "查询租户详情")
    @Parameter(in = ParameterIn.PATH, description = "租户id", name = "tenantId", required = true)
    @GetMapping(value = "/select/one/{tenantId}")
    public AnYiResult<TenantDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "租户id不能为空") String tenantId) {
        return ok(service.getTenant(tenantId));
    }


    @Operation(summary = "查询租户列表", tags = {"v1.0.0"}, description = "查询租户列表")
    @PostMapping(value = "/select/list")
    public AnYiResult<List<TenantDto>> getList(@RequestBody TenantQueryVo vo) {
        return ok(service.getTenantList(vo));
    }


    @Operation(summary = "分页查询租户", tags = {"v1.0.0"}, description = "分页查询租户")
    @PostMapping(value = "/select/page")
    public AnYiResult<AnYiPageResult<TenantDto>> selectPage(@RequestBody TenantQueryPageVoCamunda vo) {
        return ok(service.getTenantPage(vo));
    }
}
