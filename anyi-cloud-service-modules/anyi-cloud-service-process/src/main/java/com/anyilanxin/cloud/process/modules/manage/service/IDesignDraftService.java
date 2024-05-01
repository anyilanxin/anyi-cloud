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

package com.anyilanxin.cloud.process.modules.manage.service;

import com.anyilanxin.cloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.cloud.database.datasource.base.service.BaseService;
import com.anyilanxin.cloud.process.modules.manage.controller.vo.DesignDraftPageQuery;
import com.anyilanxin.cloud.process.modules.manage.controller.vo.DesignDraftVo;
import com.anyilanxin.cloud.process.modules.manage.entity.DesignDraftEntity;
import com.anyilanxin.cloud.process.modules.manage.service.dto.DesignDraftDto;
import com.anyilanxin.cloud.process.modules.manage.service.dto.DesignDraftPageDto;

import java.util.List;

/**
 * 流程模型草稿(DesignDraft)业务层接口
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2023-04-26 15:00:45
 * @since JDK11
 */
public interface IDesignDraftService extends BaseService<DesignDraftEntity> {
    /**
     * 保存
     *
     * @param vo 流程模型草稿保存数据
     * @throws RuntimeException
     * @author zxh
     * @date 2023-04-26 15:00:45
     */
    void save(DesignDraftVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param draftId 模型草稿id
     * @param vo      流程模型草稿更新数据
     * @throws RuntimeException
     * @author zxh
     * @date 2023-04-26 15:00:45
     */
    void updateById(String draftId, DesignDraftVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo 分页查询条件
     * @return AnYiPageResult<DesignDraftPageDto> 分页查询结果
     * @throws RuntimeException
     * @author zxh
     * @date 2023-04-26 15:00:45
     */
    AnYiPageResult<DesignDraftPageDto> pageByModel(DesignDraftPageQuery vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param draftId 模型草稿id
     * @return DesignDraftDto 查询结果
     * @throws RuntimeException
     * @author zxh
     * @date 2023-04-26 15:00:45
     */
    DesignDraftDto getById(String draftId) throws RuntimeException;


    /**
     * 通过draftId删除
     *
     * @param draftId 模型草稿id
     * @throws RuntimeException
     * @author zxh
     * @date 2023-04-26 15:00:45
     */
    void deleteById(String draftId) throws RuntimeException;


    /**
     * 流程模型草稿批量删除
     *
     * @param draftIds 模型草稿id列表
     * @throws RuntimeException
     * @author zxh
     * @date 2023-04-26 15:00:45
     */
    void deleteBatch(List<String> draftIds) throws RuntimeException;
}
