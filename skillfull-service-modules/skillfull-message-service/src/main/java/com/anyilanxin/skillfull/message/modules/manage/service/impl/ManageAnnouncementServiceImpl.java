package com.anyilanxin.skillfull.message.modules.manage.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.message.modules.manage.controller.vo.ManageAnnouncementPageVo;
import com.anyilanxin.skillfull.message.modules.manage.controller.vo.ManageAnnouncementQueryVo;
import com.anyilanxin.skillfull.message.modules.manage.controller.vo.ManageAnnouncementVo;
import com.anyilanxin.skillfull.message.modules.manage.entity.ManageAnnouncementEntity;
import com.anyilanxin.skillfull.message.modules.manage.mapper.ManageAnnouncementMapper;
import com.anyilanxin.skillfull.message.modules.manage.service.IManageAnnouncementService;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageAnnouncementDto;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageAnnouncementPageDto;
import com.anyilanxin.skillfull.message.modules.manage.service.mapstruct.ManageAnnouncementCopyMap;
import com.anyilanxin.skillfull.message.modules.manage.service.mapstruct.ManageAnnouncementPageCopyMap;
import com.anyilanxin.skillfull.message.modules.manage.service.mapstruct.ManageAnnouncementQueryCopyMap;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 系统通告公告管理(ManageAnnouncement)业务层实现
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-03-29 08:34:22
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ManageAnnouncementServiceImpl extends ServiceImpl<ManageAnnouncementMapper, ManageAnnouncementEntity> implements IManageAnnouncementService {
    private final ManageAnnouncementCopyMap map;
    private final ManageAnnouncementPageCopyMap pageMap;
    private final ManageAnnouncementQueryCopyMap queryMap;
    private final ManageAnnouncementMapper mapper;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(ManageAnnouncementVo vo) throws RuntimeException {
        ManageAnnouncementEntity entity = map.vToE(vo);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.SaveDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String anntId, ManageAnnouncementVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(anntId);
        // 更新数据
        ManageAnnouncementEntity entity = map.vToE(vo);
        entity.setAnntId(anntId);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.UpdateDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<ManageAnnouncementDto> selectListByModel(ManageAnnouncementQueryVo vo) throws RuntimeException {
        List<ManageAnnouncementDto> list = mapper.selectListByModel(vo);
        if (CollectionUtils.isEmpty(list)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return list;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<ManageAnnouncementPageDto> pageByModel(ManageAnnouncementPageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public ManageAnnouncementDto getById(String anntId) throws RuntimeException {
        ManageAnnouncementEntity byId = super.getById(anntId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return map.eToD(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String anntId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(anntId);
        // 删除数据
        boolean b = this.removeById(anntId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.DeleteDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> anntIds) throws RuntimeException {
        List<ManageAnnouncementEntity> entities = this.listByIds(anntIds);
        if (CollectionUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getAnntId()));
        int i = mapper.deleteBatchIds(waitDeleteList);
        if (i <= 0) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.BatchDeleteDataFail"));
        }
    }
}
