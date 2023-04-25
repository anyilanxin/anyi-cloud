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

package com.anyilanxin.skillfull.message.modules.manage.mapper;

import com.anyilanxin.skillfull.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.skillfull.message.modules.manage.controller.vo.ManageTemplatePageVo;
import com.anyilanxin.skillfull.message.modules.manage.entity.ManageTemplateEntity;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageTemplatePageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Collection;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 消息模板(ManageTemplate)持久层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-03-29 05:23:43
 * @since JDK1.8
 */
@Repository
public interface ManageTemplateMapper extends BaseMapper<ManageTemplateEntity> {
  /**
   * 分页查询
   *
   * @param vo ${@link ManageTemplatePageVo} 查询条件
   * @param page ${@link Page< ManageTemplatePageDto >} 分页信息
   * @return IPage<ManageTemplatePageDto> ${@link IPage<ManageTemplatePageDto>} 结果
   * @author zxiaozhou
   * @date 2022-03-29 05:23:43
   */
  IPage<ManageTemplatePageDto> pageByModel(
      Page<ManageTemplatePageDto> page, @Param("query") ManageTemplatePageVo vo);

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
