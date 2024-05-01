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
package com.anyilanxin.cloud.auth.modules.user.controller;

import com.anyilanxin.cloud.auth.modules.user.controller.vo.RbacUserPageVo;
import com.anyilanxin.cloud.auth.modules.user.controller.vo.RbacUserQueryVo;
import com.anyilanxin.cloud.auth.modules.user.controller.vo.RbacUserVo;
import com.anyilanxin.cloud.auth.modules.user.service.IRbacUserService;
import com.anyilanxin.cloud.auth.modules.user.service.dto.RbacUserDto;
import com.anyilanxin.cloud.auth.modules.user.service.dto.RbacUserPageDto;
import com.anyilanxin.cloud.corecommon.base.AnYiResult;
import com.anyilanxin.cloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.cloud.corecommon.utils.AnYiI18nUtil;
import com.anyilanxin.cloud.corecommon.validation.annotation.NotNullSize;
import com.anyilanxin.cloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.cloud.coremvc.base.controller.AnYiBaseController;
import io.swagger.v3.oas.annotations.Hidden;
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
 * 用户表(RbacUser)控制层
 *
 * @author zhouxuanhong
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2024-01-23 10:06:19
 * @since JDK11
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Hidden
@Tag(name = "RbacUser", description = "用户表相关")
@RequestMapping(value = "/rbacUser", produces = MediaType.APPLICATION_JSON_VALUE)
public class RbacUserController extends AnYiBaseController {
    private final IRbacUserService service;

    @Operation(summary = "用户表添加", tags = {"v1.0.0"}, description = "添加用户表", hidden = true)
    @PostMapping(value = "/insert")
    public AnYiResult<String> insert(@RequestBody @Valid RbacUserVo vo) {
        service.save(vo);
        return ok(AnYiI18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过用户id修改", tags = {"v1.0.0"}, description = "修改用户表", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "用户id", name = "userId", required = true)
    @PutMapping(value = "/update/{userId}")
    public AnYiResult<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "用户id不能为空") String userId,
                                     @RequestBody @Valid RbacUserVo vo) {
        service.updateById(userId, vo);
        return ok(AnYiI18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "用户表逻辑删除", tags = {"v1.0.0"}, description = "删除用户表", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "用户id", name = "userId", required = true)
    @DeleteMapping(value = "/delete-one/{userId}")
    public AnYiResult<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "用户id不能为空") String userId) {
        service.deleteById(userId);
        return ok(AnYiI18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "用户表逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除用户表", hidden = true)
    @PostMapping(value = "/delete-batch")
    public AnYiResult<String> deleteBatchByIds(@RequestBody @NotNullSize(message = "待删除用户id不能为空") List<String> userIds) {
        service.deleteBatch(userIds);
        return ok(AnYiI18nUtil.get("Controller.BatchDeleteSuccess"));
    }


    @Operation(summary = "通过用户id查询详情", tags = {"v1.0.0"}, description = "查询用户表详情", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "用户id", name = "userId", required = true)
    @GetMapping(value = "/select/one/{userId}")
    public AnYiResult<RbacUserDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "用户id不能为空") String userId) {
        return ok(service.getById(userId));
    }


    @Operation(summary = "通过条件查询用户表多条数据", tags = {"v1.0.0"}, description = "通过条件查询用户表", hidden = true)
    @PostMapping(value = "/select/list/by-model")
    public AnYiResult<List<RbacUserDto>> selectListByModel(@RequestBody RbacUserQueryVo vo) {
        return ok(service.selectListByModel(vo));
    }


    @Operation(summary = "用户表分页查询", tags = {"v1.0.0"}, description = "分页查询用户表", hidden = true)
    @PostMapping(value = "/select/page")
    public AnYiResult<AnYiPageResult<RbacUserPageDto>> selectPage(@RequestBody RbacUserPageVo vo) {
        return ok(service.pageByModel(vo));
    }
}
