/*
 * Copyright (c) 2021-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
package com.anyilanxin.anyicloud.process.modules.base.mapper;

import com.anyilanxin.anyicloud.corecommon.model.common.SelectModel;
import com.anyilanxin.anyicloud.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.anyicloud.process.modules.base.controller.vo.ProcessCategoryPageVo;
import com.anyilanxin.anyicloud.process.modules.base.controller.vo.ProcessCategoryQueryVo;
import com.anyilanxin.anyicloud.process.modules.base.entity.ProcessCategoryEntity;
import com.anyilanxin.anyicloud.process.modules.base.service.dto.ProcessCategoryDto;
import com.anyilanxin.anyicloud.process.modules.base.service.dto.ProcessCategoryPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Collection;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 流程类别(ProcessCategory)持久层
 *
 * @author zxh
 * @date 2021-11-19 10:47:01
 * @since 1.0.0
 */
@Repository
public interface ProcessCategoryMapper extends BaseMapper<ProcessCategoryEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link ProcessCategoryPageVo} 查询条件
     * @param page ${@link Page< ProcessCategoryPageDto >} 分页信息
     * @return IPage<ProcessCategoryPageDto> ${@link IPage<ProcessCategoryPageDto>} 结果
     * @author zxh
     * @date 2021-11-19 10:47:01
     */
    IPage<ProcessCategoryPageDto> pageByModel(Page<ProcessCategoryPageDto> page, @Param("query") ProcessCategoryPageVo vo);


    /**
     * 条件查询多条
     *
     * @param vo ${@link ProcessCategoryQueryVo} 查询条件
     * @return List<ProcessCategoryDto> ${@link List< ProcessCategoryDto >} 结果
     * @author zxh
     * @date 2021-11-19 10:47:01
     */
    List<ProcessCategoryDto> selectListByModel(ProcessCategoryQueryVo vo);


    /**
     * 通过类别id物理删除
     *
     * @param categoryId ${@link String} 类别id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxh
     * @date 2021-11-19 10:47:01
     */
    int physicalDeleteById(@Param("id") String categoryId);


    /**
     * 通过类别id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxh
     * @date 2021-11-19 10:47:01
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);


    /**
     * 获取建模流程类别下拉列表
     *
     * @return List<SelectModel>
     * @author zxh
     * @date 2022-10-19 07:41
     */
    List<SelectModel> getModelDesignList();
}
