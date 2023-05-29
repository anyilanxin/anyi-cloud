

package com.anyilanxin.anyicloud.process.modules.base.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.corecommon.exception.ResponseException;
import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.process.core.constant.ModelStateType;
import com.anyilanxin.anyicloud.process.modules.base.controller.vo.DeleteDesignModelVo;
import com.anyilanxin.anyicloud.process.modules.base.controller.vo.DesignModelPageVo;
import com.anyilanxin.anyicloud.process.modules.base.controller.vo.DesignModelVo;
import com.anyilanxin.anyicloud.process.modules.base.entity.DesignModelEntity;
import com.anyilanxin.anyicloud.process.modules.base.entity.DesignModelHistoryEntity;
import com.anyilanxin.anyicloud.process.modules.base.entity.ProcessCategoryEntity;
import com.anyilanxin.anyicloud.process.modules.base.mapper.DesignModelHistoryMapper;
import com.anyilanxin.anyicloud.process.modules.base.mapper.DesignModelMapper;
import com.anyilanxin.anyicloud.process.modules.base.mapper.ProcessCategoryMapper;
import com.anyilanxin.anyicloud.process.modules.base.service.IDesignModelService;
import com.anyilanxin.anyicloud.process.modules.base.service.dto.DesignModelDeploymentStatiDto;
import com.anyilanxin.anyicloud.process.modules.base.service.dto.DesignModelDto;
import com.anyilanxin.anyicloud.process.modules.base.service.dto.DesignModelPageDto;
import com.anyilanxin.anyicloud.process.modules.base.service.mapstruct.DesignModelCopyMap;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.model.bpmn.impl.BpmnParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 流程模型管理(DesignModel)业务层实现
 *
 * @author zxh
 * @date 2021-11-25 05:22:56
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DesignModelServiceImpl extends ServiceImpl<DesignModelMapper, DesignModelEntity> implements IDesignModelService {
    private final DesignModelCopyMap map;
    private final BpmnParser bpmnParser;
    private final DesignModelMapper mapper;
    private final RepositoryService repositoryService;
    private final DesignModelHistoryMapper historyMapper;
    private final ProcessCategoryMapper categoryMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(DesignModelVo vo) throws RuntimeException {
        DesignModelEntity entity = map.vToE(vo);
        check(null, entity.getProcessDefinitionKeys());
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.SaveDataFail"));
        }
    }


    /**
     * 数据验证
     *
     * @param modelId               模型id
     * @param processDefinitionKeys 流程定义key
     * @author zxh
     * @date 2022-06-05 14:47
     */
    private void check(String modelId, String processDefinitionKeys) {
        StringBuffer sb = new StringBuffer("");
        String[] keys = processDefinitionKeys.split("[,，]");
        for (String key : keys) {
            int modelNum = mapper.getModelNum(modelId, key);
            if (modelNum > 0) {
                sb.append(key).append("、");
            }
        }
        String repeatKeys = sb.toString();
        if (StringUtils.isNotBlank(repeatKeys)) {
            throw new ResponseException(Status.VERIFICATION_FAILED, "流程定义key重复，请修改：" + StringUtils.removeEnd(repeatKeys, "、"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String modelId, DesignModelVo vo) throws RuntimeException {
        // 查询数据是否存在
        DesignModelDto byId = this.getById(modelId);
        String[] oldProcessDefinitionKeys = byId.getProcessDefinitionKeys().split("[,，]");
        String[] newProcessDefinitionKeys = vo.getProcessDefinitionKeys().split("[,，]");
        // if (!Arrays.equals(oldProcessDefinitionKeys, newProcessDefinitionKeys)) {
        // throw new ResponseException(Status.VERIFICATION_FAILED, "修改时不允许改变流程定义key");
        // }
        // 更新数据
        DesignModelEntity entity = map.vToE(vo);
        check(modelId, entity.getProcessDefinitionKeys());
        // 如果存在历史，则为新版本待部署
        LambdaQueryWrapper<DesignModelHistoryEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DesignModelHistoryEntity::getModelId, modelId);
        Long count = historyMapper.selectCount(lambdaQueryWrapper);
        entity.setModelState(count > 0 ? ModelStateType.NEW_VERSION.getType() : ModelStateType.NO_DEPLOYMENT.getType());
        entity.setModelId(modelId);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.UpdateDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<DesignModelPageDto> pageByModel(DesignModelPageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public DesignModelDto getById(String modelId) throws RuntimeException {
        DesignModelEntity byId = super.getById(modelId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        DesignModelDto designModelDto = map.eToD(byId);
        // 查询类别
        LambdaQueryWrapper<ProcessCategoryEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ProcessCategoryEntity::getCategoryCode, byId.getCategory());
        ProcessCategoryEntity processCategoryEntity = categoryMapper.selectOne(lambdaQueryWrapper);
        if (Objects.nonNull(processCategoryEntity)) {
            designModelDto.setCategoryName(processCategoryEntity.getCategoryName());
        }
        return designModelDto;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteByModel(DeleteDesignModelVo vo) throws RuntimeException {
        DesignModelDto byId = this.getById(vo.getModelId());
        // 删除流程部署信息
        if (StringUtils.isNotBlank(byId.getDeploymentId())) {
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(byId.getDeploymentId()).singleResult();
            if (Objects.nonNull(deployment)) {
                repositoryService.deleteDeployment(deployment.getId(), vo.getCascade(), vo.getSkipCustomListeners(), vo.getSkipIoMappings());
            }
        }
        // 删除自存历史部署信息以及引擎部署信息
        LambdaQueryWrapper<DesignModelHistoryEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DesignModelHistoryEntity::getModelId, vo.getModelId());
        List<DesignModelHistoryEntity> designModelHistoryEntities = historyMapper.selectList(lambdaQueryWrapper);
        if (CollUtil.isNotEmpty(designModelHistoryEntities)) {
            Set<String> deploymentIds = new HashSet<>(designModelHistoryEntities.size());
            designModelHistoryEntities.forEach(v -> deploymentIds.add(v.getDeploymentId()));
            deploymentIds.forEach(v -> {
                Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(v).singleResult();
                if (Objects.nonNull(deployment)) {
                    repositoryService.deleteDeployment(deployment.getId(), vo.getCascade(), vo.getSkipCustomListeners(), vo.getSkipIoMappings());
                }
            });
            int delete = historyMapper.delete(lambdaQueryWrapper);
            if (delete <= 0) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除模型历史数据失败");
            }
        }
        // 删除模型
        boolean b = this.removeById(vo.getModelId());
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除模型失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public DesignModelDeploymentStatiDto statistics() throws RuntimeException {
        DesignModelDeploymentStatiDto statistics = mapper.statistics();
        if (Objects.isNull(statistics)) {
            statistics = new DesignModelDeploymentStatiDto();
        }
        return statistics;
    }
}
