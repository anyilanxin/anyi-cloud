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
import com.anyilanxin.cloud.process.modules.manage.controller.vo.*;
import com.anyilanxin.cloud.process.modules.manage.service.dto.InstanceDetailDto;
import com.anyilanxin.cloud.process.modules.manage.service.dto.InstanceHistoryPageDto;
import com.anyilanxin.cloud.process.modules.manage.service.dto.InstancePageDto;
import com.anyilanxin.cloud.process.modules.manage.service.dto.InstanceStaticDto;

/**
 * 流程逻辑处理
 *
 * @author zxh
 * @date 2020-10-15 19:46
 * @since 1.0.0
 */
public interface IInstanceManageService {

    /**
     * 流程实例删除
     *
     * @param vo ${@link DeleteProcessInstanceVo} 流程实例删除
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2020-10-20 09:32
     */
    void deleteProcessInstance(DeleteProcessInstanceVo vo) throws RuntimeException;


    /**
     * 流程实例批量挂起
     *
     * @param vo ${@link ProcessInstanceBatchVo} 流程实例操作信息
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2020-10-20 09:33
     */
    void suspendProcessInstance(ProcessInstanceBatchVo vo) throws RuntimeException;


    /**
     * 流动实例批量激活
     *
     * @param vo ${@link ProcessInstanceBatchVo} 流程实例操作信息
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2020-10-20 09:34
     */
    void activateProcessInstance(ProcessInstanceBatchVo vo) throws RuntimeException;


    /**
     * 分页查询流程实例
     *
     * @param vo ${@link ProcessInstancePageVo} 查询条件
     * @return AnYiPageResult<InstancePageDto> ${@link AnYiPlusPageResult < InstancePageDto >}
     * @author zxh
     * @date 2021-05-21 04:25
     */
    AnYiPageResult<InstancePageDto> selectProcessInstance(ProcessInstancePageVo vo);


    /**
     * 分页查询历史流程实例
     *
     * @param vo ${@link ProcessInstanceHistoryPageQuery} 查询条件
     * @return AnYiPageResult<InstanceHistoryPageDto> ${@link AnYiPlusPageResult < InstanceHistoryPageDto >}
     * @author zxh
     * @date 2021-05-21 04:25
     */
    AnYiPageResult<InstanceHistoryPageDto> selectHistoryProcessInstance(ProcessInstanceHistoryPageQuery vo);


    /**
     * 获取流程实例详情
     *
     * @param processInstanceId ${@link String} 流程实例id
     * @return InstanceDetailDto ${@link InstanceDetailDto}
     * @author zxh
     * @date 2021-07-30 20:30
     */
    InstanceDetailDto getInstanceDetail(String processInstanceId) throws RuntimeException;


    /**
     * 流程实例迁移
     *
     * @param vo ${@link MigrationVo} 迁移条件
     * @return String ${@link String} 当异步时存在,值为批处理作业id
     * @author zxh
     * @date 2021-07-26 11:21
     */
    String instanceMigration(MigrationVo vo);


    /**
     * 流程实例统计
     *
     * @return InstanceStaticDto ${@link InstanceStaticDto}
     * @author zxh
     * @date 2021-09-01 11:22
     */
    InstanceStaticDto instanceStatic();
}
