// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.message.modules.manage.service;

import indi.zxiaozhou.skillfull.coredatabase.base.service.BaseService;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import indi.zxiaozhou.skillfull.message.modules.manage.controller.vo.ManageTemplatePageVo;
import indi.zxiaozhou.skillfull.message.modules.manage.controller.vo.ManageTemplateVo;
import indi.zxiaozhou.skillfull.message.modules.manage.entity.ManageTemplateEntity;
import indi.zxiaozhou.skillfull.message.modules.manage.service.dto.ManageTemplateDto;
import indi.zxiaozhou.skillfull.message.modules.manage.service.dto.ManageTemplatePageDto;

import java.util.List;

/**
 * 消息模板(ManageTemplate)业务层接口
 *
 * @author zxiaozhou
 * @date 2021-04-19 17:10:56
 * @since JDK1.8
 */
public interface IManageTemplateService extends BaseService<ManageTemplateEntity> {
    /**
     * 保存
     *
     * @param vo ${@link ManageTemplateVo} 消息模板保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-04-19 17:10:56
     */
    void save(ManageTemplateVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo         ${@link ManageTemplateVo} 消息模板更新
     * @param templateId ${@link String} 模板id
     * @param vo         ${@link ManageTemplateVo} 消息模板更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-04-19 17:10:56
     */
    void updateById(String templateId, ManageTemplateVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link ManageTemplatePageVo} 消息模板分页查询Vo
     * @return PageDto<ManageTemplatePageDto> ${@link PageDto<ManageTemplatePageDto>} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-04-19 17:10:56
     */
    PageDto<ManageTemplatePageDto> pageByModel(ManageTemplatePageVo vo) throws RuntimeException;
    

    /**
     * 通过id查询详情
     *
     * @param templateId ${@link String} 模板id
     * @return ManageTemplateDto ${@link ManageTemplateDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-04-19 17:10:56
     */
    ManageTemplateDto getById(String templateId) throws RuntimeException;


    /**
     * 通过templateId删除
     *
     * @param templateId ${@link String} 模板id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-04-19 17:10:56
     */
    void deleteById(String templateId) throws RuntimeException;


    /**
     * 消息模板批量删除
     *
     * @param templateIds ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-04-19 17:10:56
     */
    void deleteBatch(List<String> templateIds) throws RuntimeException;


    /**
     * 通过模板编码获取有效的模板基本信息
     *
     * @param codes ${@link List<String>} 模板编码
     * @return List<ManageTemplateDto> ${@link  List<ManageTemplateDto>}
     * @author zxiaozhou
     * @date 2021-04-19 18:13
     */
    List<ManageTemplateDto> getEffectiveBasicInfoByCodes(List<String> codes);


    /**
     * 通过模板编码获取有效的模板基本信息
     *
     * @param type ${@link Integer} 模板类型:1-微信模板,2-短信,3-邮件,4-系统公告
     * @return List<ManageTemplateDto> ${@link  List<ManageTemplateDto>}
     * @author zxiaozhou
     * @date 2021-04-19 18:13
     */
    List<ManageTemplateDto> getEffectiveBasicInfoByType(Integer type);
}