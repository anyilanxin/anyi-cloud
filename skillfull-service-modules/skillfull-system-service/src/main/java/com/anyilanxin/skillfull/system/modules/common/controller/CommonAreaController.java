// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.common.controller;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.constant.CoreCommonCacheConstant;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.corecommon.validation.annotation.NotNullSize;
import com.anyilanxin.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.skillfull.coremvc.base.controller.BaseController;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.system.modules.common.controller.vo.CommonAreaPageVo;
import com.anyilanxin.skillfull.system.modules.common.controller.vo.CommonAreaVo;
import com.anyilanxin.skillfull.system.modules.common.service.ICommonAreaService;
import com.anyilanxin.skillfull.system.modules.common.service.dto.CommonAreaDto;
import com.anyilanxin.skillfull.system.modules.common.service.dto.CommonAreaPageDto;
import com.anyilanxin.skillfull.system.modules.common.service.dto.CommonAreaTreeDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 区域表(CommonArea)控制层
 *
 * @author zxiaozhou
 * @date 2020-11-02 09:25:00
 * @since JDK11
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
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "区域id不能为空") String areaId,
                                 @RequestBody @Valid CommonAreaVo vo) {
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
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, description = "上级区域编码", name = "parentId"),
            @Parameter(in = ParameterIn.QUERY, description = "需要激活的区域id", name = "activateAreaId")
    })
    @Cacheable(value = CoreCommonCacheConstant.ENGINE_AREA_CACHE, key = "#parentId+#activateAreaId")
    public Result<List<CommonAreaTreeDto>> getList(@RequestParam(required = false, defaultValue = "") String parentId,
                                                   @RequestParam(required = false, defaultValue = "") String activateAreaId) {
        return ok(service.selectList(parentId, activateAreaId));
    }
}
