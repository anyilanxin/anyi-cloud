package indi.zxiaozhou.skillfull.auth.modules.common.controller;

import indi.zxiaozhou.skillfull.auth.modules.common.controller.vo.CommonSystemPageVo;
import indi.zxiaozhou.skillfull.auth.modules.common.controller.vo.CommonSystemVo;
import indi.zxiaozhou.skillfull.auth.modules.common.service.ICommonSystemService;
import indi.zxiaozhou.skillfull.auth.modules.common.service.dto.CommonSystemDto;
import indi.zxiaozhou.skillfull.auth.modules.common.service.dto.CommonSystemPageDto;
import indi.zxiaozhou.skillfull.corecommon.base.Result;
import indi.zxiaozhou.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import indi.zxiaozhou.skillfull.coremvc.base.controller.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 系统(CommonSystem)控制层
 *
 * @author zxiaozhou
 * @date 2021-07-28 09:35:45
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "CommonSystem", description = "系统相关")
@RequestMapping(value = "/common-system", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommonSystemController extends BaseController {
    private final ICommonSystemService service;


    @Operation(summary = "系统添加", tags = {"v1.0.0"}, description = "添加系统")
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid CommonSystemVo vo) {
        service.save(vo);
        return ok("添加成功");
    }


    @Operation(summary = "通过系统id修改", tags = {"v1.0.0"}, description = "修改系统")
    @Parameter(in = ParameterIn.PATH, description = "系统id", name = "systemId", required = true)
    @PutMapping(value = "/update/{systemId}")
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "系统id不能为空") String systemId,
                                 @RequestBody @Valid CommonSystemVo vo) {
        service.updateById(systemId, vo);
        return ok("修改成功");
    }


    @Operation(summary = "系统逻辑删除", tags = {"v1.0.0"}, description = "删除系统")
    @Parameter(in = ParameterIn.PATH, description = "系统id", name = "systemId", required = true)
    @DeleteMapping(value = "/delete-one/{systemId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "系统id不能为空") String systemId) {
        service.deleteById(systemId);
        return ok("删除成功");
    }


    @Operation(summary = "通过系统id查询详情", tags = {"v1.0.0"}, description = "查询系统详情")
    @Parameter(in = ParameterIn.PATH, description = "系统id", name = "systemId", required = true)
    @GetMapping(value = "/select/one/{systemId}")
    public Result<CommonSystemDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "系统id不能为空") String systemId) {
        return ok(service.getById(systemId));
    }


    @Operation(summary = "查询有效的系统信息", tags = {"v1.0.0"}, description = "查询有效的系统信息")
    @GetMapping(value = "/select/list")
    public Result<List<CommonSystemDto>> getList() {
        return ok(service.selectList());
    }


    @Operation(summary = "系统分页查询", tags = {"v1.0.0"}, description = "分页查询系统")
    @PostMapping(value = "/select/page")
    public Result<PageDto<CommonSystemPageDto>> selectPage(@RequestBody CommonSystemPageVo vo) {
        return ok(service.pageByModel(vo));
    }
}
