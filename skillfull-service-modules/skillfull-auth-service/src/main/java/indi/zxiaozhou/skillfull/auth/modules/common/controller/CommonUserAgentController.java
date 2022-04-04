// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.common.controller;

import indi.zxiaozhou.skillfull.auth.modules.common.controller.vo.CommonUserAgentPageVo;
import indi.zxiaozhou.skillfull.auth.modules.common.controller.vo.CommonUserAgentQueryVo;
import indi.zxiaozhou.skillfull.auth.modules.common.controller.vo.CommonUserAgentVo;
import indi.zxiaozhou.skillfull.auth.modules.common.service.ICommonUserAgentService;
import indi.zxiaozhou.skillfull.auth.modules.common.service.dto.CommonUserAgentDto;
import indi.zxiaozhou.skillfull.auth.modules.common.service.dto.CommonUserAgentPageDto;
import indi.zxiaozhou.skillfull.corecommon.base.Result;
import indi.zxiaozhou.skillfull.corecommon.validation.annotation.NotNullSize;
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
 * 用户-代理人表(CommonUserAgent)控制层
 *
 * @author zxiaozhou
 * @date 2021-05-17 23:12:37
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "CommonUserAgent", description = "用户-代理人表Api接口相关")
@RequestMapping(value = "/common-user-agent", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommonUserAgentController extends BaseController {
    private final ICommonUserAgentService service;


    @Operation(summary = "用户-代理人表添加", tags = {"v1.0.0"}, description = "添加用户-代理人表", hidden = true)
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid CommonUserAgentVo vo) {
        service.save(vo);
        return ok("添加成功");
    }


    @Operation(summary = "通过代理id修改", tags = {"v1.0.0"}, description = "修改用户-代理人表", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "代理id", name = "agentId", required = true)
    @PutMapping(value = "/update/{agentId}")
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "代理id不能为空") String agentId,
                                 @RequestBody @Valid CommonUserAgentVo vo) {
        service.updateById(agentId, vo);
        return ok("修改成功");
    }


    @Operation(summary = "用户-代理人表逻辑删除", tags = {"v1.0.0"}, description = "删除用户-代理人表", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "代理id", name = "agentId", required = true)
    @DeleteMapping(value = "/delete-one/{agentId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "代理id不能为空") String agentId) {
        service.deleteById(agentId);
        return ok("删除成功");
    }


    @Operation(summary = "用户-代理人表逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除用户-代理人表", hidden = true)
    @PostMapping(value = "/delete-batch")
    public Result<String> deleteBatch(@RequestBody @NotNullSize(message = "待删除代理id不能为空") List<String> agentIds) {
        service.deleteBatch(agentIds);
        return ok("批量删除成功");
    }


    @Operation(summary = "通过代理id查询详情", tags = {"v1.0.0"}, description = "查询用户-代理人表详情", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "代理id", name = "agentId", required = true)
    @GetMapping(value = "/select/one/{agentId}")
    public Result<CommonUserAgentDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "代理id不能为空") String agentId) {
        return ok(service.getById(agentId));
    }


    @Operation(summary = "通过条件查询用户-代理人表多条数据", tags = {"v1.0.0"}, description = "通过条件查询用户-代理人表", hidden = true)
    @PostMapping(value = "/select/list")
    public Result<List<CommonUserAgentDto>> getList(@RequestBody CommonUserAgentQueryVo vo) {
        return ok(service.selectListByModel(vo));
    }


    @Operation(summary = "用户-代理人表分页查询", tags = {"v1.0.0"}, description = "分页查询用户-代理人表", hidden = true)
    @PostMapping(value = "/select/page")
    public Result<PageDto<CommonUserAgentPageDto>> selectPage(@RequestBody CommonUserAgentPageVo vo) {
        return ok(service.pageByModel(vo));
    }
}