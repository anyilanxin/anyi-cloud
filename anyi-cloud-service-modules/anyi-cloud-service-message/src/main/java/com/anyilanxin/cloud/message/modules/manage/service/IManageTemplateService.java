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

package com.anyilanxin.cloud.message.modules.manage.service;

import com.anyilanxin.cloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.cloud.database.datasource.base.service.BaseService;
import com.anyilanxin.cloud.message.modules.manage.controller.vo.ManageTemplatePageQuery;
import com.anyilanxin.cloud.message.modules.manage.controller.vo.ManageTemplateVo;
import com.anyilanxin.cloud.message.modules.manage.entity.ManageTemplateEntity;
import com.anyilanxin.cloud.message.modules.manage.service.dto.ManageTemplateDto;
import com.anyilanxin.cloud.message.modules.manage.service.dto.ManageTemplatePageDto;
import com.anyilanxin.cloud.message.modules.manage.service.dto.ManageTemplateSendInfoDto;

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
     * @param vo ${@link ManageTemplatePageQuery} 消息模板分页查询Vo
     * @return AnYiPageResult<ManageTemplatePageDto> ${@link AnYiPlusPageResult <   ManageTemplatePageDto   >} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2022-03-29 05:23:43
     */
    AnYiPageResult<ManageTemplatePageDto> pageByModel(ManageTemplatePageQuery vo) throws RuntimeException;


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
