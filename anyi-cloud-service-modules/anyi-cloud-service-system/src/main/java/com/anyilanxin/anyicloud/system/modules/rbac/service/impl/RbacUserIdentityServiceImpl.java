

package com.anyilanxin.anyicloud.system.modules.rbac.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.corecommon.exception.ResponseException;
import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacUserIdentityPageVo;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacUserIdentityVo;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacUserIdentityEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.RbacUserIdentityMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacUserIdentityService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacUserIdentityDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacUserIdentityPageDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct.RbacUserIdentityCopyMap;
import com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct.RbacUserIdentityPageCopyMap;
import com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct.RbacUserIdentityQueryCopyMap;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 实名信息表(RbacUserIdentity)业务层实现
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:21
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RbacUserIdentityServiceImpl extends ServiceImpl<RbacUserIdentityMapper, RbacUserIdentityEntity> implements IRbacUserIdentityService {
    private final RbacUserIdentityCopyMap map;
    private final RbacUserIdentityPageCopyMap pageMap;
    private final RbacUserIdentityQueryCopyMap queryMap;
    private final RbacUserIdentityMapper mapper;

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(RbacUserIdentityVo vo) throws RuntimeException {
        RbacUserIdentityEntity entity = map.vToE(vo);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.SaveDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void audit(String identityId, RbacUserIdentityVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(identityId);
        // 更新数据
        RbacUserIdentityEntity entity = map.vToE(vo);
        entity.setIdentityId(identityId);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.UpdateDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<RbacUserIdentityPageDto> pageByModel(RbacUserIdentityPageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public RbacUserIdentityDto getById(String identityId) throws RuntimeException {
        RbacUserIdentityEntity byId = super.getById(identityId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return map.eToD(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String identityId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(identityId);
        // 删除数据
        boolean b = this.removeById(identityId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.DeleteDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> identityIds) throws RuntimeException {
        List<RbacUserIdentityEntity> entities = this.listByIds(identityIds);
        if (CollUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getIdentityId()));
        int i = mapper.deleteBatchIds(waitDeleteList);
        if (i <= 0) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.BatchDeleteDataFail"));
        }
    }
}
