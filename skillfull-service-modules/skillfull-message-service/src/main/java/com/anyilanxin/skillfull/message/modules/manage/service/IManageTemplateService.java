/*
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE文件。
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 *   3.请保留源码和相关描述文件的项目出处，作者声明等。
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 *   6.若您的项目无法满足以上几点，可申请商业授权
 */

package com.anyilanxin.skillfull.message.modules.manage.service;

import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.message.modules.manage.controller.vo.ManageTemplatePageVo;
import com.anyilanxin.skillfull.message.modules.manage.controller.vo.ManageTemplateVo;
import com.anyilanxin.skillfull.message.modules.manage.entity.ManageTemplateEntity;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageTemplateDto;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageTemplatePageDto;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageTemplateSendInfoDto;
import java.util.List;

/**
 * 消息模板(ManageTemplate)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-03-29 05:23:43
 * @since JDK1.8
 */
public interface IManageTemplateService extends BaseService<ManageTemplateEntity> {
  /**
   * 保存
   *
   * @param vo ${@link ManageTemplateVo} 消息模板保存
   * @throws RuntimeException ${@link RuntimeException}
   * @author zxiaozhou
   * @date 2022-03-29 05:23:43
   */
  void save(ManageTemplateVo vo) throws RuntimeException;

  /**
   * 通过id更新
   *
   * @param vo ${@link ManageTemplateVo} 消息模板更新
   * @param templateId ${@link String} 模板id
   * @param vo ${@link ManageTemplateVo} 消息模板更新
   * @throws RuntimeException ${@link RuntimeException}
   * @author zxiaozhou
   * @date 2022-03-29 05:23:43
   */
  void updateById(String templateId, ManageTemplateVo vo) throws RuntimeException;

  /**
   * 分页查询
   *
   * @param vo ${@link ManageTemplatePageVo} 消息模板分页查询Vo
   * @return PageDto<ManageTemplatePageDto> ${@link PageDto< ManageTemplatePageDto >} 分页查询结果
   * @throws RuntimeException ${@link RuntimeException}
   * @author zxiaozhou
   * @date 2022-03-29 05:23:43
   */
  PageDto<ManageTemplatePageDto> pageByModel(ManageTemplatePageVo vo) throws RuntimeException;

  /**
   * 通过id查询详情
   *
   * @param templateId ${@link String} 模板id
   * @return ManageTemplateDto ${@link ManageTemplateDto} 查询结果
   * @throws RuntimeException ${@link RuntimeException}
   * @author zxiaozhou
   * @date 2022-03-29 05:23:43
   */
  ManageTemplateDto getById(String templateId) throws RuntimeException;

  /**
   * 通过templateId删除
   *
   * @param templateId ${@link String} 模板id
   * @throws RuntimeException ${@link RuntimeException}
   * @author zxiaozhou
   * @date 2022-03-29 05:23:43
   */
  void deleteById(String templateId) throws RuntimeException;

  /**
   * 消息模板批量删除
   *
   * @param templateIds ${@link List<String>}
   * @throws RuntimeException ${@link RuntimeException}
   * @author zxiaozhou
   * @date 2022-03-29 05:23:43
   */
  void deleteBatch(List<String> templateIds) throws RuntimeException;

  /**
   * 根据模板编码查询发送配置信息
   *
   * @param templateCode
   * @return ManageTemplateSendInfoDto
   * @author zxiaozhou
   * @date 2022-08-30 10:17
   */
  ManageTemplateSendInfoDto getSendInfo(String templateCode);
}
