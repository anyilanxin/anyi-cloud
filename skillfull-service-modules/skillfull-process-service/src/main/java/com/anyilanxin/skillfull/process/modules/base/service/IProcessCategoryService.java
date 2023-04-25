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

package com.anyilanxin.skillfull.process.modules.base.service;

import com.anyilanxin.skillfull.corecommon.model.common.SelectModel;
import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.ProcessCategoryPageVo;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.ProcessCategoryQueryVo;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.ProcessCategoryVo;
import com.anyilanxin.skillfull.process.modules.base.entity.ProcessCategoryEntity;
import com.anyilanxin.skillfull.process.modules.base.service.dto.ProcessCategoryDto;
import com.anyilanxin.skillfull.process.modules.base.service.dto.ProcessCategoryPageDto;
import java.util.List;
import java.util.Set;

/**
 * 流程类别(ProcessCategory)业务层接口
 *
 * @author zxiaozhou
 * @date 2021-11-19 10:47:01
 * @since JDK1.8
 */
public interface IProcessCategoryService extends BaseService<ProcessCategoryEntity> {
  /**
   * 保存
   *
   * @param vo ${@link ProcessCategoryVo} 流程类别保存
   * @throws RuntimeException ${@link RuntimeException}
   * @author zxiaozhou
   * @date 2021-11-19 10:47:01
   */
  void save(ProcessCategoryVo vo) throws RuntimeException;

  /**
   * 通过id更新
   *
   * @param vo ${@link ProcessCategoryVo} 流程类别更新
   * @param categoryId ${@link String} 类别id
   * @param vo ${@link ProcessCategoryVo} 流程类别更新
   * @throws RuntimeException ${@link RuntimeException}
   * @author zxiaozhou
   * @date 2021-11-19 10:47:01
   */
  void updateById(String categoryId, ProcessCategoryVo vo) throws RuntimeException;

  /**
   * 分页查询
   *
   * @param vo ${@link ProcessCategoryPageVo} 流程类别分页查询Vo
   * @return PageDto<ProcessCategoryPageDto> ${@link PageDto< ProcessCategoryPageDto >} 分页查询结果
   * @throws RuntimeException ${@link RuntimeException}
   * @author zxiaozhou
   * @date 2021-11-19 10:47:01
   */
  PageDto<ProcessCategoryPageDto> pageByModel(ProcessCategoryPageVo vo) throws RuntimeException;

  /**
   * 条件查询多条
   *
   * @param vo ${@link ProcessCategoryQueryVo} 流程类别条件查询Vo
   * @return List<ProcessCategoryDto> ${@link List< ProcessCategoryDto >} 查询结果
   * @throws RuntimeException ${@link RuntimeException}
   * @author zxiaozhou
   * @date 2021-11-19 10:47:01
   */
  List<ProcessCategoryDto> selectListByModel(ProcessCategoryQueryVo vo) throws RuntimeException;

  /**
   * 通过编码查询所有类表
   *
   * @param categoryCodes ${@link Set<String>}
   * @return ProcessCategoryDto> ${@link ProcessCategoryDto>}
   * @author zxiaozhou
   * @date 2021-11-19 10:58
   */
  List<ProcessCategoryDto> selectListByCodes(Set<String> categoryCodes);

  /**
   * 通过编码查询类表
   *
   * @param categoryCode ${@link String}
   * @return ProcessCategoryDto> ${@link ProcessCategoryDto>}
   * @author zxiaozhou
   * @date 2021-11-19 10:58
   */
  ProcessCategoryDto selectByCode(String categoryCode);

  /**
   * 通过id查询详情
   *
   * @param categoryId ${@link String} 类别id
   * @return ProcessCategoryDto ${@link ProcessCategoryDto} 查询结果
   * @throws RuntimeException ${@link RuntimeException}
   * @author zxiaozhou
   * @date 2021-11-19 10:47:01
   */
  ProcessCategoryDto getById(String categoryId) throws RuntimeException;

  /**
   * 通过categoryId删除
   *
   * @param categoryId ${@link String} 类别id
   * @throws RuntimeException ${@link RuntimeException}
   * @author zxiaozhou
   * @date 2021-11-19 10:47:01
   */
  void deleteById(String categoryId) throws RuntimeException;

  /**
   * 获取建模流程类别下拉列表
   *
   * @return List<SelectModel>
   * @author zxiaozhou
   * @date 2022-10-19 07:41
   */
  List<SelectModel> getModelDesignList();
}
