// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.message.modules.manage.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import indi.zxiaozhou.skillfull.corewebflux.utils.ServletUtils;
import indi.zxiaozhou.skillfull.message.modules.manage.controller.vo.ManageTemplatePageVo;
import indi.zxiaozhou.skillfull.message.modules.manage.controller.vo.ManageTemplateVo;
import indi.zxiaozhou.skillfull.message.modules.manage.entity.ManageTemplateEntity;
import indi.zxiaozhou.skillfull.message.modules.manage.mapper.ManageTemplateMapper;
import indi.zxiaozhou.skillfull.message.modules.manage.service.IManageTemplateService;
import indi.zxiaozhou.skillfull.message.modules.manage.service.dto.ManageTemplateDto;
import indi.zxiaozhou.skillfull.message.modules.manage.service.dto.ManageTemplatePageDto;
import indi.zxiaozhou.skillfull.message.modules.manage.service.mapstruct.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 消息模板(ManageTemplate)业务层实现
 *
 * @author zxiaozhou
 * @date 2021-04-19 17:10:56
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ManageTemplateServiceImpl extends ServiceImpl<ManageTemplateMapper, ManageTemplateEntity> implements IManageTemplateService {
    private final ManageTemplateDtoMap dtoMap;
    private final ManageTemplatePageDtoMap pageDtoMap;
    private final ManageTemplateVoMap voMap;
    private final ManageTemplateQueryVoMap queryVoMap;
    private final ManageTemplatePageVoMap pageVoMap;
    private final ManageTemplateMapper mapper;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(ManageTemplateVo vo) throws RuntimeException {
        ManageTemplateEntity entity = voMap.aToB(vo);
        boolean result = super.save(entity);

        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存数据失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String templateId, ManageTemplateVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(templateId);
        // 更新数据
        ManageTemplateEntity entity = voMap.aToB(vo);
        entity.setTemplateId(templateId);
        boolean result = super.updateById(entity);

        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新数据失败");
        }
    }
    

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<ManageTemplatePageDto> pageByModel(ManageTemplatePageVo vo) throws RuntimeException {
        IPage<ManageTemplatePageDto> manageTemplatePageDtoIPage = mapper.pageByModel(vo.getPage(), vo);

        return new PageDto<>(manageTemplatePageDtoIPage);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public ManageTemplateDto getById(String templateId) throws RuntimeException {
        ManageTemplateEntity byId = super.getById(templateId);

        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "未找到符合条件数据");
        }
        return dtoMap.bToA(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String templateId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(templateId);
        // 删除数据
        boolean b = this.removeById(templateId);

        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除数据失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> templateIds) throws RuntimeException {
        List<ManageTemplateEntity> entities = this.listByIds(templateIds);
        if (CollectionUtil.isEmpty(entities)) {

            throw new ResponseException(Status.DATABASE_BASE_ERROR, "数据不存在或已经被别人删除");
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getTemplateId()));
        boolean b = this.removeByIds(waitDeleteList);

        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "批量删除数据失败");
        }
    }

    @Override
    public List<ManageTemplateDto> getEffectiveBasicInfoByCodes(List<String> codes) {
        LambdaQueryWrapper<ManageTemplateEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(ManageTemplateEntity::getTemplateCode, codes)
                .eq(ManageTemplateEntity::getTemplateStatus, 1);
        List<ManageTemplateEntity> list = this.list(lambdaQueryWrapper);
        if (CollectionUtil.isEmpty(list)) {
            list = Collections.emptyList();
        }

        return dtoMap.bToA(list);
    }


    @Override
    public List<ManageTemplateDto> getEffectiveBasicInfoByType(Integer type) {
        LambdaQueryWrapper<ManageTemplateEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ManageTemplateEntity::getTemplateType, type)
                .eq(ManageTemplateEntity::getTemplateStatus, 1);
        List<ManageTemplateEntity> list = this.list(lambdaQueryWrapper);
        if (CollectionUtil.isEmpty(list)) {
            list = Collections.emptyList();
        }

        return dtoMap.bToA(list);
    }
}