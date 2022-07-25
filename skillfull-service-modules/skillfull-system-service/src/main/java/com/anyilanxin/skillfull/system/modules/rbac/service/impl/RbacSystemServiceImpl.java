// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.rbac.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacSystemPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacSystemVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacSystemEntity;
import com.anyilanxin.skillfull.system.modules.rbac.mapper.RbacSystemMapper;
import com.anyilanxin.skillfull.system.modules.rbac.service.IRbacSystemService;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacSystemDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacSystemPageDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.mapstruct.RbacSystemCopyMap;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 系统(RbacSystem)业务层实现
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-05-02 11:46:37
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RbacSystemServiceImpl extends ServiceImpl<RbacSystemMapper, RbacSystemEntity> implements IRbacSystemService {
    private final RbacSystemCopyMap map;
    private final RbacSystemMapper mapper;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(RbacSystemVo vo) throws RuntimeException {
        RbacSystemEntity entity = map.vToE(vo);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.SaveDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String systemId, RbacSystemVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(systemId);
        // 更新数据
        RbacSystemEntity entity = map.vToE(vo);
        entity.setSystemId(systemId);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.UpdateDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<RbacSystemDto> selectList() throws RuntimeException {
        List<RbacSystemEntity> list = this.list();
        return map.eToD(list);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<RbacSystemPageDto> pageByModel(RbacSystemPageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public RbacSystemDto getById(String systemId) throws RuntimeException {
        RbacSystemEntity byId = super.getById(systemId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return map.eToD(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String systemId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(systemId);
        // 删除数据
        boolean b = this.removeById(systemId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.DeleteDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> systemIds) throws RuntimeException {
        List<RbacSystemEntity> entities = this.listByIds(systemIds);
        if (CollUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getSystemId()));
        int i = mapper.deleteBatchIds(waitDeleteList);
        if (i <= 0) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.BatchDeleteDataFail"));
        }
    }
}
