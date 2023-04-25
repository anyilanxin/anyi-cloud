/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
package com.anyilanxin.skillfull.process.modules.manage.service;

import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.process.modules.manage.controller.vo.*;
import com.anyilanxin.skillfull.process.modules.manage.service.dto.DeploymentDetailDto;
import com.anyilanxin.skillfull.process.modules.manage.service.dto.ProcessDefinitionPageDto;
import com.anyilanxin.skillfull.process.modules.manage.service.dto.ProcessInfoDto;

/**
* 流程定义管理
*
* @author zxiaozhou
* @date 2020-10-14 20:48
* @since JDK11
*/
public interface IDefinitionManageService {
    /**
    * 通过流程定义key查询流程定义信息
    *
    * @param pageVo ${@link ProcessDefinitionPageVo} 查询条件
    * @return PageDto<ProcessDefinitionPageDto> ${@link PageDto< ProcessDefinitionPageDto >}
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2020-10-14 20:59
    */
    PageDto<ProcessDefinitionPageDto> selectPageDefinition(ProcessDefinitionPageVo pageVo)
            throws RuntimeException;

    /**
    * 创建部署
    *
    * @param vo ${@link DeploymentVo}
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2020-10-15 08:45
    */
    void createDeployment(DeploymentVo vo) throws RuntimeException;

    /**
    * 流程定义挂起获取激活操作
    *
    * @param vo ${@link UpdateProcessDefinitionStateVo} 流程定义操作信息
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2020-10-20 09:33
    */
    void processDefinitionUpdateState(UpdateProcessDefinitionStateVo vo) throws RuntimeException;

    /**
    * 删除流程定义
    *
    * @param vo ${@link DeleteProcessDefinitionVo} 删除参数
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2020-10-15 08:45
    */
    void deleteProcessDefinition(DeleteProcessDefinitionVo vo) throws RuntimeException;

    /**
    * 删除部署
    *
    * @param deploymentId ${@link String} 部署id
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2020-10-15 08:45
    */
    void deleteDeployment(String deploymentId) throws RuntimeException;

    /**
    * 查询部署详情
    *
    * @param processKeywordId 流程定义id或流程定义key
    * @return DeploymentResourceDto ${@link DeploymentDetailDto}
    * @author zxiaozhou
    * @date 2020-10-15 09:44
    */
    DeploymentDetailDto getDeploymentDetail(String processKeywordId);

    /**
    * 历史再次部署
    *
    * @param vo ${@link DeploymentHistoryVo}
    * @author zxiaozhou
    * @date 2021-11-26 20:59
    */
    void historyDeployment(DeploymentHistoryVo vo);

    /**
    * 获取流程详细信息
    *
    * @param vo ${@link ProcessInfoVo}
    * @return ProcessInfoDto ${@link ProcessInfoDto}
    * @author zxiaozhou
    * @date 2022-01-03 11:20
    */
    ProcessInfoDto getProcessInfo(ProcessInfoVo vo);
}
