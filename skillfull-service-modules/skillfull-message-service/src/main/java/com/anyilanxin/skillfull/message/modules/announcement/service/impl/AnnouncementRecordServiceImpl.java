// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.modules.announcement.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.message.modules.announcement.controller.vo.AnnouncementRecordPageVo;
import com.anyilanxin.skillfull.message.modules.announcement.controller.vo.AnnouncementRecordQueryVo;
import com.anyilanxin.skillfull.message.modules.announcement.controller.vo.AnnouncementRecordVo;
import com.anyilanxin.skillfull.message.modules.announcement.entity.AnnouncementRecordEntity;
import com.anyilanxin.skillfull.message.modules.announcement.mapper.AnnouncementRecordMapper;
import com.anyilanxin.skillfull.message.modules.announcement.service.IAnnouncementRecordService;
import com.anyilanxin.skillfull.message.modules.announcement.service.dto.AnnouncementRecordDto;
import com.anyilanxin.skillfull.message.modules.announcement.service.dto.AnnouncementRecordPageDto;
import com.anyilanxin.skillfull.message.modules.announcement.service.mapstruct.AnnouncementRecordCopyMap;
import com.anyilanxin.skillfull.message.modules.announcement.service.mapstruct.AnnouncementRecordPageCopyMap;
import com.anyilanxin.skillfull.message.modules.announcement.service.mapstruct.AnnouncementRecordQueryCopyMap;
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
 * 系统通知公告阅读记录(AnnouncementRecord)业务层实现
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-03-29 08:35:34
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AnnouncementRecordServiceImpl extends ServiceImpl<AnnouncementRecordMapper, AnnouncementRecordEntity> implements IAnnouncementRecordService {
    private final AnnouncementRecordCopyMap map;
    private final AnnouncementRecordPageCopyMap pageMap;
    private final AnnouncementRecordQueryCopyMap queryMap;
    private final AnnouncementRecordMapper mapper;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(AnnouncementRecordVo vo) throws RuntimeException {
        AnnouncementRecordEntity entity = map.vToE(vo);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.SaveDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String anntReadId, AnnouncementRecordVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(anntReadId);
        // 更新数据
        AnnouncementRecordEntity entity = map.vToE(vo);
        entity.setAnntReadId(anntReadId);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.UpdateDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<AnnouncementRecordDto> selectListByModel(AnnouncementRecordQueryVo vo) throws RuntimeException {
        List<AnnouncementRecordDto> list = mapper.selectListByModel(vo);
        if (CollectionUtils.isEmpty(list)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return list;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<AnnouncementRecordPageDto> pageByModel(AnnouncementRecordPageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public AnnouncementRecordDto getById(String anntReadId) throws RuntimeException {
        AnnouncementRecordEntity byId = super.getById(anntReadId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return map.eToD(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String anntReadId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(anntReadId);
        // 删除数据
        boolean b = this.removeById(anntReadId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.DeleteDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> anntReadIds) throws RuntimeException {
        List<AnnouncementRecordEntity> entities = this.listByIds(anntReadIds);
        if (CollectionUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getAnntReadId()));
        int i = mapper.deleteBatchIds(waitDeleteList);
        if (i <= 0) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.BatchDeleteDataFail"));
        }
    }
}
