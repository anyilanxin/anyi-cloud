// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.base.service.mapstruct;

import com.anyilanxin.skillfull.corecommon.base.service.mapstruct.BaseMap;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DesignFormVo;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 流程表单(DesignForm)Vo与Entity相互转换
 *
 * @author zxiaozhou
 * @since 2021-02-12 20:16:00
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface DesignFormVoMap extends BaseMap<DesignFormVo, DesignFormEntity> {
}
