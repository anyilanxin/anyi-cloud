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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import indi.zxiaozhou.skillfull.message.modules.manage.controller.vo.ManageAnnouncementPageVo;
import indi.zxiaozhou.skillfull.message.modules.manage.controller.vo.ManageAnnouncementQueryVo;
import indi.zxiaozhou.skillfull.message.modules.manage.controller.vo.ManageAnnouncementVo;
import indi.zxiaozhou.skillfull.message.modules.manage.entity.ManageAnnouncementEntity;
import indi.zxiaozhou.skillfull.message.modules.manage.mapper.ManageAnnouncementMapper;
import indi.zxiaozhou.skillfull.message.modules.manage.service.IManageAnnouncementService;
import indi.zxiaozhou.skillfull.message.modules.manage.service.dto.ManageAnnouncementDto;
import indi.zxiaozhou.skillfull.message.modules.manage.service.dto.ManageAnnouncementPageDto;
import indi.zxiaozhou.skillfull.message.modules.manage.service.mapstruct.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 系统通告管理(ManageAnnouncement)业务层实现
 *
 * @author zxiaozhou
 * @date 2021-02-12 19:57:09
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ManageAnnouncementServiceImpl extends ServiceImpl<ManageAnnouncementMapper, ManageAnnouncementEntity> implements IManageAnnouncementService {
    private final ManageAnnouncementDtoMap dtoMap;
    private final ManageAnnouncementPageDtoMap pageDtoMap;
    private final ManageAnnouncementVoMap voMap;
    private final ManageAnnouncementQueryVoMap queryVoMap;
    private final ManageAnnouncementPageVoMap pageVoMap;
    private final ManageAnnouncementMapper mapper;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(ManageAnnouncementVo vo) throws RuntimeException {
        ManageAnnouncementEntity entity = voMap.aToB(vo);
        boolean result = super.save(entity);

        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存数据失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String anntId, ManageAnnouncementVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(anntId);
        // 更新数据
        ManageAnnouncementEntity entity = voMap.aToB(vo);
        entity.setAnntId(anntId);
        boolean result = super.updateById(entity);

        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新数据失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<ManageAnnouncementDto> selectListByModel(ManageAnnouncementQueryVo vo) throws RuntimeException {
        List<ManageAnnouncementDto> list = mapper.selectListByModel(vo);

        if (CollectionUtils.isEmpty(list)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "未找到符合条件数据");
        }
        return list;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<ManageAnnouncementPageDto> pageByModel(ManageAnnouncementPageVo vo) throws RuntimeException {
        IPage<ManageAnnouncementPageDto> manageAnnouncementPageDtoIPage = mapper.pageByModel(vo.getPage(), vo);

        return new PageDto<>(manageAnnouncementPageDtoIPage);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<ManageAnnouncementPageDto> page(ManageAnnouncementPageVo vo) throws RuntimeException {
        LambdaQueryWrapper<ManageAnnouncementEntity> queryWrapper = new LambdaQueryWrapper<>(pageVoMap.aToB(vo));
        if (Objects.nonNull(vo.getStartTime())) {
            queryWrapper.gt(ManageAnnouncementEntity::getCreateTime, vo.getStartTime());
        }
        if (Objects.nonNull(vo.getEndTime())) {
            queryWrapper.lt(ManageAnnouncementEntity::getCreateTime, vo.getEndTime());
        }
        Page<ManageAnnouncementEntity> page = this.page(vo.getPage(), queryWrapper);

        return new PageDto<>(page, pageDtoMap.bToA(page.getRecords()));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public ManageAnnouncementDto getById(String anntId) throws RuntimeException {
        ManageAnnouncementEntity byId = super.getById(anntId);

        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "未找到符合条件数据");
        }
        return dtoMap.bToA(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String anntId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(anntId);
        boolean b = this.removeById(anntId);

        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除数据失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> anntIds) throws RuntimeException {
        List<ManageAnnouncementEntity> entities = this.listByIds(anntIds);
        if (CollectionUtil.isEmpty(entities)) {

            throw new ResponseException(Status.DATABASE_BASE_ERROR, "数据不存在或已经被别人删除");
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getAnntId()));
        boolean b = this.removeByIds(waitDeleteList);

        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "批量删除数据失败");
        }
    }
}