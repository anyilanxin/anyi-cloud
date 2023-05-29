

package com.anyilanxin.anyicloud.message.modules.manage.mapper;

import com.anyilanxin.anyicloud.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.anyicloud.message.modules.manage.controller.vo.ManageTemplatePageVo;
import com.anyilanxin.anyicloud.message.modules.manage.entity.ManageTemplateEntity;
import com.anyilanxin.anyicloud.message.modules.manage.service.dto.ManageTemplatePageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Collection;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 消息模板(ManageTemplate)持久层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-03-29 05:23:43
 * @since 1.0.0
 */
@Repository
public interface ManageTemplateMapper extends BaseMapper<ManageTemplateEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link ManageTemplatePageVo} 查询条件
     * @param page ${@link Page< ManageTemplatePageDto >} 分页信息
     * @return IPage<ManageTemplatePageDto> ${@link IPage<ManageTemplatePageDto>} 结果
     * @author zxh
     * @date 2022-03-29 05:23:43
     */
    IPage<ManageTemplatePageDto> pageByModel(Page<ManageTemplatePageDto> page, @Param("query") ManageTemplatePageVo vo);


    /**
     * 通过模板id物理删除
     *
     * @param templateId ${@link String} 模板id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxh
     * @date 2022-03-29 05:23:43
     */
    int physicalDeleteById(@Param("id") String templateId);


    /**
     * 通过模板id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxh
     * @date 2022-03-29 05:23:43
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
