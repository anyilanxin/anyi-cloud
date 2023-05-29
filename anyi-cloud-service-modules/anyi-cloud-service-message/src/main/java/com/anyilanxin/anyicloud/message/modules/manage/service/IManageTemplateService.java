

package com.anyilanxin.anyicloud.message.modules.manage.service;

import com.anyilanxin.anyicloud.database.datasource.base.service.BaseService;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.message.modules.manage.controller.vo.ManageTemplatePageVo;
import com.anyilanxin.anyicloud.message.modules.manage.controller.vo.ManageTemplateVo;
import com.anyilanxin.anyicloud.message.modules.manage.entity.ManageTemplateEntity;
import com.anyilanxin.anyicloud.message.modules.manage.service.dto.ManageTemplateDto;
import com.anyilanxin.anyicloud.message.modules.manage.service.dto.ManageTemplatePageDto;
import com.anyilanxin.anyicloud.message.modules.manage.service.dto.ManageTemplateSendInfoDto;

import java.util.List;

/**
 * 消息模板(ManageTemplate)业务层接口
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-03-29 05:23:43
 * @since 1.0.0
 */
public interface IManageTemplateService extends BaseService<ManageTemplateEntity> {
    /**
     * 保存
     *
     * @param vo ${@link ManageTemplateVo} 消息模板保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2022-03-29 05:23:43
     */
    void save(ManageTemplateVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo         ${@link ManageTemplateVo} 消息模板更新
     * @param templateId ${@link String} 模板id
     * @param vo         ${@link ManageTemplateVo} 消息模板更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2022-03-29 05:23:43
     */
    void updateById(String templateId, ManageTemplateVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link ManageTemplatePageVo} 消息模板分页查询Vo
     * @return PageDto<ManageTemplatePageDto> ${@link PageDto<  ManageTemplatePageDto  >} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2022-03-29 05:23:43
     */
    PageDto<ManageTemplatePageDto> pageByModel(ManageTemplatePageVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param templateId ${@link String} 模板id
     * @return ManageTemplateDto ${@link ManageTemplateDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2022-03-29 05:23:43
     */
    ManageTemplateDto getById(String templateId) throws RuntimeException;


    /**
     * 通过templateId删除
     *
     * @param templateId ${@link String} 模板id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2022-03-29 05:23:43
     */
    void deleteById(String templateId) throws RuntimeException;


    /**
     * 消息模板批量删除
     *
     * @param templateIds ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2022-03-29 05:23:43
     */
    void deleteBatch(List<String> templateIds) throws RuntimeException;


    /**
     * 根据模板编码查询发送配置信息
     *
     * @param templateCode
     * @return ManageTemplateSendInfoDto
     * @author zxh
     * @date 2022-08-30 10:17
     */
    ManageTemplateSendInfoDto getSendInfo(String templateCode);
}
