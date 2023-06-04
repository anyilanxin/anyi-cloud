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

import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.process.modules.manage.controller.vo.*;
import com.anyilanxin.anyicloud.process.modules.manage.service.dto.DeploymentDetailDto;
import com.anyilanxin.anyicloud.process.modules.manage.service.dto.ProcessDefinitionPageDto;
import com.anyilanxin.anyicloud.process.modules.manage.service.dto.ProcessInfoDto;
import com.anyilanxin.skillfull.process.modules.manage.controller.vo.*;

/**
 * 流程定义管理
 *
 * @author zxh
 * @date 2020-10-14 20:48
 * @since 1.0.0
 */
public interface IDefinitionManageService {
    /**
     * 通过流程定义key查询流程定义信息
     *
     * @param pageVo ${@link ProcessDefinitionPageVo} 查询条件
     * @return PageDto<ProcessDefinitionPageDto> ${@link PageDto< ProcessDefinitionPageDto >}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2020-10-14 20:59
     */
    PageDto<ProcessDefinitionPageDto> selectPageDefinition(ProcessDefinitionPageVo pageVo) throws RuntimeException;


    /**
     * 创建部署
     *
     * @param vo ${@link DeploymentVo}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2020-10-15 08:45
     */
    void createDeployment(DeploymentVo vo) throws RuntimeException;


    /**
     * 流程定义挂起获取激活操作
     *
     * @param vo ${@link UpdateProcessDefinitionStateVo} 流程定义操作信息
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2020-10-20 09:33
     */
    void processDefinitionUpdateState(UpdateProcessDefinitionStateVo vo) throws RuntimeException;


    /**
     * 删除流程定义
     *
     * @param vo ${@link DeleteProcessDefinitionVo} 删除参数
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2020-10-15 08:45
     */
    void deleteProcessDefinition(DeleteProcessDefinitionVo vo) throws RuntimeException;


    /**
     * 删除部署
     *
     * @param deploymentId ${@link String} 部署id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2020-10-15 08:45
     */
    void deleteDeployment(String deploymentId) throws RuntimeException;


    /**
     * 查询部署详情
     *
     * @param processKeywordId 流程定义id或流程定义key
     * @return DeploymentResourceDto ${@link DeploymentDetailDto}
     * @author zxh
     * @date 2020-10-15 09:44
     */
    DeploymentDetailDto getDeploymentDetail(String processKeywordId);


    /**
     * 历史再次部署
     *
     * @param vo ${@link DeploymentHistoryVo}
     * @author zxh
     * @date 2021-11-26 20:59
     */
    void historyDeployment(DeploymentHistoryVo vo);


    /**
     * 获取流程详细信息
     *
     * @param vo ${@link ProcessInfoVo}
     * @return ProcessInfoDto ${@link ProcessInfoDto}
     * @author zxh
     * @date 2022-01-03 11:20
     */
    ProcessInfoDto getProcessInfo(ProcessInfoVo vo);
}
