

package com.anyilanxin.anyicloud.process.modules.manage.service.mapstruct;

import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseMap;
import com.anyilanxin.anyicloud.process.modules.manage.service.dto.ProcessDefinitionPageDto;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * ProcessDefinitionEntity转ProcessDefinitionPageDto
 *
 * @author zxh
 * @since 2020-10-15 22:17:55
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface ProcessDefinitionPageMap extends BaseMap<ProcessDefinitionPageDto, ProcessDefinitionEntity> {
}
