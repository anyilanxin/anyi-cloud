// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.modules.manage.mapper;

import com.anyilanxin.skillfull.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.skillfull.message.modules.manage.controller.vo.ManageTemplatePageVo;
import com.anyilanxin.skillfull.message.modules.manage.entity.ManageTemplateEntity;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageTemplatePageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * 消息模板(ManageTemplate)持久层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-03-29 05:23:43
 * @since JDK1.8
 */
@Repository
public interface ManageTemplateMapper extends BaseMapper<ManageTemplateEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link ManageTemplatePageVo} 查询条件
     * @param page ${@link Page< ManageTemplatePageDto >} 分页信息
     * @return IPage<ManageTemplatePageDto> ${@link IPage<ManageTemplatePageDto>} 结果
     * @author zxiaozhou
     * @date 2022-03-29 05:23:43
     */
    IPage<ManageTemplatePageDto> pageByModel(Page<ManageTemplatePageDto> page, @Param("query") ManageTemplatePageVo vo);


    /**
     * 通过模板id物理删除
     *
     * @param templateId ${@link String} 模板id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2022-03-29 05:23:43
     */
    int physicalDeleteById(@Param("id") String templateId);


    /**
     * 通过模板id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2022-03-29 05:23:43
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
