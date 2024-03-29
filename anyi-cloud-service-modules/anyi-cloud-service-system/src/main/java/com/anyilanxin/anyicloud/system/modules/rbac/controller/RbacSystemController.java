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

package com.anyilanxin.anyicloud.system.modules.rbac.controller;

import com.anyilanxin.anyicloud.corecommon.base.AnYiResult;
import com.anyilanxin.anyicloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.anyicloud.corecommon.utils.AnYiI18nUtil;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.anyicloud.coremvc.base.controller.AnYiBaseController;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacSystemPageQuery;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacSystemVo;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacSystemService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacSystemDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacSystemPageDto;
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
 * 系统(RbacSystem)控制层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 11:46:37
 * @since 1.0.0
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "RbacSystem", description = "系统Api接口相关")
@RequestMapping(value = "/rbac-system", produces = MediaType.APPLICATION_JSON_VALUE)
public class RbacSystemController extends AnYiBaseController {
    private final IRbacSystemService service;

    @Operation(summary = "系统添加", tags = {"v1.0.0"}, description = "添加系统")
    @PostMapping(value = "/insert")
    public AnYiResult<String> insert(@RequestBody @Valid RbacSystemVo vo) {
        service.save(vo);
        return ok(AnYiI18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过系统id修改", tags = {"v1.0.0"}, description = "修改系统")
    @Parameter(in = ParameterIn.PATH, description = "系统id", name = "systemId", required = true)
    @PutMapping(value = "/update/{systemId}")
    public AnYiResult<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "系统id不能为空") String systemId, @RequestBody @Valid RbacSystemVo vo) {
        service.updateById(systemId, vo);
        return ok(AnYiI18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "系统逻辑删除", tags = {"v1.0.0"}, description = "删除系统")
    @Parameter(in = ParameterIn.PATH, description = "系统id", name = "systemId", required = true)
    @DeleteMapping(value = "/delete-one/{systemId}")
    public AnYiResult<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "系统id不能为空") String systemId) {
        service.deleteById(systemId);
        return ok(AnYiI18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "通过系统id查询详情", tags = {"v1.0.0"}, description = "查询系统详情")
    @Parameter(in = ParameterIn.PATH, description = "系统id", name = "systemId", required = true)
    @GetMapping(value = "/select/one/{systemId}")
    public AnYiResult<RbacSystemDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "系统id不能为空") String systemId) {
        return ok(service.getById(systemId));
    }


    @Operation(summary = "查询有效的系统信息", tags = {"v1.0.0"}, description = "查询有效的系统信息")
    @GetMapping(value = "/select/list")
    public AnYiResult<List<RbacSystemDto>> getList() {
        return ok(service.selectList());
    }


    @Operation(summary = "系统分页查询", tags = {"v1.0.0"}, description = "分页查询系统")
    @PostMapping(value = "/select/page")
    public AnYiResult<AnYiPageResult<RbacSystemPageDto>> selectPage(@RequestBody RbacSystemPageQuery vo) {
        return ok(service.pageByModel(vo));
    }
}
