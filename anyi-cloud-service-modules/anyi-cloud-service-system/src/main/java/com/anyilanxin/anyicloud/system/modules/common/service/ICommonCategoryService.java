/*
 * Copyright (c) 2021-2023 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Zeebe EE、AnYi Cloud EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.system.modules.common.service;

import com.anyilanxin.anyicloud.database.datasource.base.service.BaseService;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.system.modules.common.controller.vo.CommonCategoryPageVo;
import com.anyilanxin.anyicloud.system.modules.common.controller.vo.CommonCategoryVo;
import com.anyilanxin.anyicloud.system.modules.common.entity.CommonCategoryEntity;
import com.anyilanxin.anyicloud.system.modules.common.service.dto.CommonCategoryDto;
import com.anyilanxin.anyicloud.system.modules.common.service.dto.CommonCategoryPageDto;
import com.anyilanxin.anyicloud.system.modules.common.service.dto.CommonCategoryTreeDto;
import java.util.List;

/**
 * 分类字典表(CommonCategory)业务层接口
 *
 * @author zxh
 * @date 2021-01-07 23:40:21
 * @since 1.0.0
 */
public interface ICommonCategoryService extends BaseService<CommonCategoryEntity> {
    /**
     * 保存
     *
     * @param vo ${@link CommonCategoryVo} 分类字典表保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-01-07 23:40:21
     */
    void save(CommonCategoryVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo         ${@link CommonCategoryVo} 分类字典表更新
     * @param categoryId ${@link String} 分类id
     * @param vo         ${@link CommonCategoryVo} 分类字典表更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-01-07 23:40:21
     */
    void updateById(String categoryId, CommonCategoryVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link CommonCategoryPageVo} 分类字典表分页查询Vo
     * @return PageDto<CommonCategoryPageDto> ${@link PageDto< CommonCategoryPageDto >} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-01-07 23:40:21
     */
    PageDto<CommonCategoryPageDto> pageByModel(CommonCategoryPageVo vo) throws RuntimeException;


    /**
     * 根据统一分类id查询分类
     *
     * @param categoryCommonCode ${@link String} 统一分类编码
     * @return List<CommonCategoryDto> ${@link List< CommonCategoryDto >} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-01-07 23:40:21
     */
    List<CommonCategoryDto> selectListByCommonCode(String categoryCommonCode) throws RuntimeException;


    /**
     * 根据统一分类id查询分类(树形)
     *
     * @param categoryCommonCode ${@link String} 统一分类编码
     * @return List<CommonCategoryTreeDto> ${@link List< CommonCategoryTreeDto >} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-01-07 23:40:21
     */
    List<CommonCategoryTreeDto> selectTreeListByCommonCode(String categoryCommonCode) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param categoryId ${@link String} 分类id
     * @return CommonCategoryDto ${@link CommonCategoryDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-01-07 23:40:21
     */
    CommonCategoryDto getById(String categoryId) throws RuntimeException;


    /**
     * 通过categoryId删除
     *
     * @param categoryId ${@link String} 分类id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2020-08-28 12:20
     */
    void deleteById(String categoryId) throws RuntimeException;


    /**
     * 分类字典表批量删除
     *
     * @param categoryIds ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2020-10-24 22:59
     */
    void deleteBatch(List<String> categoryIds) throws RuntimeException;


    /**
     * 分类查询下级
     *
     * @param parentId ${@link String} 上级id
     * @return List<CommonCategoryPageDto> ${@link List<CommonCategoryPageDto> }
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-01-08 00:01
     */
    List<CommonCategoryPageDto> selectPageChildren(String parentId) throws RuntimeException;


    /**
     * 查询所有分类编码
     *
     * @return List<CommonCategoryTreeDto> ${@link List<CommonCategoryTreeDto>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-03-12 07:44
     */
    List<CommonCategoryTreeDto> selectAllTree() throws RuntimeException;
}
