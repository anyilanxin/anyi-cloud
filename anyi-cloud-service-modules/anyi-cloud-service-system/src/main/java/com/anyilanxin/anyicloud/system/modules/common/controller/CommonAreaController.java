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
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */
package com.anyilanxin.anyicloud.system.modules.common.controller;

import com.anyilanxin.anyicloud.corecommon.base.Result;
import com.anyilanxin.anyicloud.corecommon.constant.CoreCommonCacheConstant;
import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotNullSize;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.anyicloud.coremvc.base.controller.BaseController;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.system.modules.common.controller.vo.CommonAreaPageVo;
import com.anyilanxin.anyicloud.system.modules.common.controller.vo.CommonAreaVo;
import com.anyilanxin.anyicloud.system.modules.common.service.ICommonAreaService;
import com.anyilanxin.anyicloud.system.modules.common.service.dto.CommonAreaDto;
import com.anyilanxin.anyicloud.system.modules.common.service.dto.CommonAreaPageDto;
import com.anyilanxin.anyicloud.system.modules.common.service.dto.CommonAreaTreeDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 区域表(CommonArea)控制层
 *
 * @author zxh
 * @date 2020-11-02 09:25:00
 * @since 1.0.0
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "CommonArea", description = "区域表Api接口相关")
@RequestMapping(value = "/common-area", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommonAreaController extends BaseController {
    private final ICommonAreaService service;

    @Operation(summary = "区域表添加", tags = {"v1.0.0"}, description = "添加区域表")
    @PostMapping(value = "/insert")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_AREA_CACHE, allEntries = true)
    public Result<String> insert(@RequestBody @Valid CommonAreaVo vo) {
        service.save(vo);
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过区域id修改", tags = {"v1.0.0"}, description = "修改区域表")
    @Parameter(in = ParameterIn.PATH, description = "区域id", name = "areaId", required = true)
    @PutMapping(value = "/update/{areaId}")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_AREA_CACHE, allEntries = true)
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "区域id不能为空") String areaId, @RequestBody @Valid CommonAreaVo vo) {
        service.updateById(areaId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "区域表分页查询", tags = {"v1.0.0"}, description = "分页查询区域表")
    @PostMapping(value = "/select/page")
    public Result<PageDto<CommonAreaPageDto>> selectPage(@RequestBody CommonAreaPageVo vo) {
        return ok(service.pageByModel(vo));
    }


    @Operation(summary = "区域表逻辑删除", tags = {"v1.0.0"}, description = "删除区域表")
    @Parameter(in = ParameterIn.PATH, description = "区域id", name = "areaId", required = true)
    @DeleteMapping(value = "/delete-one/{areaId}")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_AREA_CACHE, allEntries = true)
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "区域id不能为空") String areaId) {
        service.deleteById(areaId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "通过区域id查询详情", tags = {"v1.0.0"}, description = "查询路由详情")
    @Parameter(in = ParameterIn.PATH, description = "区域id", name = "areaId", required = true)
    @GetMapping(value = "/select/one/{areaId}")
    public Result<CommonAreaDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "区域id不能为空") String areaId) {
        return ok(service.getById(areaId));
    }


    @Operation(summary = "区域表逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除区域表")
    @PostMapping(value = "/delete-batch")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_AREA_CACHE, allEntries = true)
    public Result<String> deleteBatch(@RequestBody @NotNullSize(message = "待删除区域id不能为空") List<String> areaIds) {
        service.deleteBatch(areaIds);
        return ok(I18nUtil.get("Controller.BatchDeleteSuccess"));
    }


    @Operation(summary = "通过上级区域编码获取下级", tags = {"v1.0.0"}, description = "通过上级区域编码获取下级(如果为空则查询顶级)")
    @GetMapping(value = "/select/list")
    @Parameters({@Parameter(in = ParameterIn.QUERY, description = "上级区域编码", name = "parentId"), @Parameter(in = ParameterIn.QUERY, description = "需要激活的区域id", name = "activateAreaId")})
    @Cacheable(value = CoreCommonCacheConstant.ENGINE_AREA_CACHE, key = "#parentId+#activateAreaId")
    public Result<List<CommonAreaTreeDto>> getList(@RequestParam(required = false, defaultValue = "") String parentId, @RequestParam(required = false, defaultValue = "") String activateAreaId) {
        return ok(service.selectList(parentId, activateAreaId));
    }
}
