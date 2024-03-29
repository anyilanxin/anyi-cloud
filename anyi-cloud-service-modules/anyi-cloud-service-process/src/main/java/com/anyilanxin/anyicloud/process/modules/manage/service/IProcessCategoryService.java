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

package com.anyilanxin.anyicloud.process.modules.manage.service;

import com.anyilanxin.anyicloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.anyicloud.corecommon.model.common.AnYiSelect;
import com.anyilanxin.anyicloud.database.datasource.base.service.BaseService;
import com.anyilanxin.anyicloud.process.modules.manage.controller.vo.ProcessCategoryPageQuery;
import com.anyilanxin.anyicloud.process.modules.manage.controller.vo.ProcessCategoryQueryVo;
import com.anyilanxin.anyicloud.process.modules.manage.controller.vo.ProcessCategoryVo;
import com.anyilanxin.anyicloud.process.modules.manage.entity.ProcessCategoryEntity;
import com.anyilanxin.anyicloud.process.modules.manage.service.dto.ProcessCategoryDto;
import com.anyilanxin.anyicloud.process.modules.manage.service.dto.ProcessCategoryPageDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 流程类别(ProcessCategory)业务层接口
 *
 * @author zxh
 * @date 2021-11-19 10:47:01
 * @since 1.0.0
 */
public interface IProcessCategoryService extends BaseService<ProcessCategoryEntity> {
    /**
     * 保存
     *
     * @param vo ${@link ProcessCategoryVo} 流程类别保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-11-19 10:47:01
     */
    void save(ProcessCategoryVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo         ${@link ProcessCategoryVo} 流程类别更新
     * @param categoryId ${@link String} 类别id
     * @param vo         ${@link ProcessCategoryVo} 流程类别更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-11-19 10:47:01
     */
    void updateById(String categoryId, ProcessCategoryVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link ProcessCategoryPageQuery} 流程类别分页查询Vo
     * @return AnYiPageResult<ProcessCategoryPageDto> ${@link AnYiPlusPageResult < ProcessCategoryPageDto >} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-11-19 10:47:01
     */
    AnYiPageResult<ProcessCategoryPageDto> pageByModel(ProcessCategoryPageQuery vo) throws RuntimeException;


    /**
     * 条件查询多条
     *
     * @param vo ${@link ProcessCategoryQueryVo} 流程类别条件查询Vo
     * @return List<ProcessCategoryDto> ${@link List< ProcessCategoryDto >} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-11-19 10:47:01
     */
    List<ProcessCategoryDto> selectListByModel(ProcessCategoryQueryVo vo) throws RuntimeException;


    /**
     * 通过编码查询所有类表
     *
     * @param categoryCodes ${@link Set<String>}
     * @return ProcessCategoryDto> ${@link ProcessCategoryDto>}
     * @author zxh
     * @date 2021-11-19 10:58
     */
    List<ProcessCategoryDto> selectListByCodes(Set<String> categoryCodes);


    /**
     * 通过编码查询所有类表
     *
     * @param categoryCodes ${@link Set<String>}
     * @return ProcessCategoryDto> ${@link ProcessCategoryDto>}
     * @author zxh
     * @date 2021-11-19 10:58
     */
    Map<String, ProcessCategoryDto> selectMapByCodes(Set<String> categoryCodes);


    /**
     * 通过编码查询类表
     *
     * @param categoryCode ${@link String}
     * @return ProcessCategoryDto> ${@link ProcessCategoryDto>}
     * @author zxh
     * @date 2021-11-19 10:58
     */
    ProcessCategoryDto selectByCode(String categoryCode);


    /**
     * 通过id查询详情
     *
     * @param categoryId ${@link String} 类别id
     * @return ProcessCategoryDto ${@link ProcessCategoryDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-11-19 10:47:01
     */
    ProcessCategoryDto getById(String categoryId) throws RuntimeException;


    /**
     * 通过categoryId删除
     *
     * @param categoryId ${@link String} 类别id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-11-19 10:47:01
     */
    void deleteById(String categoryId) throws RuntimeException;


    /**
     * 获取建模流程类别下拉列表
     *
     * @return List<AnYiSelect>
     * @author zxh
     * @date 2022-10-19 07:41
     */
    List<AnYiSelect> getModelDesignList();
}
