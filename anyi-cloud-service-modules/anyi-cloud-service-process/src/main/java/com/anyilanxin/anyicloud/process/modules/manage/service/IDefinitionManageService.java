

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
