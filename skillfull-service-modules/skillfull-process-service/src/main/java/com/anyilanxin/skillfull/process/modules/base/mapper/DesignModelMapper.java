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
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   8.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   9.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.skillfull.process.modules.base.mapper;

import com.anyilanxin.skillfull.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DesignModelPageVo;
import com.anyilanxin.skillfull.process.modules.base.entity.DesignModelEntity;
import com.anyilanxin.skillfull.process.modules.base.service.dto.DesignModelDeploymentStatiDto;
import com.anyilanxin.skillfull.process.modules.base.service.dto.DesignModelPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Collection;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 流程模型管理(DesignModel)持久层
 *
 * @author zxiaozhou
 * @date 2021-11-25 05:22:56
 * @since JDK1.8
 */
@Repository
public interface DesignModelMapper extends BaseMapper<DesignModelEntity> {
    /**
     * 分页查询
     *
     * @param vo ${@link DesignModelPageVo} 查询条件
     * @param page ${@link Page< DesignModelPageDto >} 分页信息
     * @return IPage<DesignModelPageDto> ${@link IPage<DesignModelPageDto>} 结果
     * @author zxiaozhou
     * @date 2021-11-25 05:22:56
     */
    IPage<DesignModelPageDto> pageByModel(
            Page<DesignModelPageDto> page, @Param("query") DesignModelPageVo vo);

    /**
     * 模型状态统计
     *
     * @return DesignModelDeploymentStatiDto ${@link DesignModelDeploymentStatiDto}
     * @author zxiaozhou
     * @date 2021-03-02 18:01
     */
    DesignModelDeploymentStatiDto statistics();

    /**
     * 通过模型id物理删除
     *
     * @param modelId ${@link String} 模型id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2021-11-25 05:22:56
     */
    int physicalDeleteById(@Param("id") String modelId);

    /**
     * 获取某个模型数量，排除指定模型id的数量
     *
     * @param modelId 需要排除的模型id
     * @param processDefinitionKey 流程定义key
     * @author zxiaozhou
     * @date 2022-06-05 14:49
     */
    int getModelNum(@Param("id") String modelId, @Param("key") String processDefinitionKey);

    /**
     * 通过模型id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2021-11-25 05:22:56
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
