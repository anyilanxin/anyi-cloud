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
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacUserAgentPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacUserAgentQueryVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacUserAgentVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacUserAgentEntity;
import com.anyilanxin.skillfull.system.modules.rbac.mapper.RbacUserAgentMapper;
import com.anyilanxin.skillfull.system.modules.rbac.service.IRbacUserAgentService;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacUserAgentDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacUserAgentPageDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.mapstruct.RbacUserAgentCopyMap;
import com.anyilanxin.skillfull.system.modules.rbac.service.mapstruct.RbacUserAgentPageCopyMap;
import com.anyilanxin.skillfull.system.modules.rbac.service.mapstruct.RbacUserAgentQueryCopyMap;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 用户-代理人表(RbacUserAgent)业务层实现
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-05-02 16:12:20
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RbacUserAgentServiceImpl extends ServiceImpl<RbacUserAgentMapper, RbacUserAgentEntity> implements IRbacUserAgentService {
    private final RbacUserAgentCopyMap map;
    private final RbacUserAgentPageCopyMap pageMap;
    private final RbacUserAgentQueryCopyMap queryMap;
    private final RbacUserAgentMapper mapper;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(RbacUserAgentVo vo) throws RuntimeException {
        RbacUserAgentEntity entity = map.vToE(vo);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.SaveDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String agentId, RbacUserAgentVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(agentId);
        // 更新数据
        RbacUserAgentEntity entity = map.vToE(vo);
        entity.setAgentId(agentId);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.UpdateDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<RbacUserAgentDto> selectListByModel(RbacUserAgentQueryVo vo) throws RuntimeException {
        List<RbacUserAgentDto> list = mapper.selectListByModel(vo);
        if (CollUtil.isEmpty(list)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return list;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<RbacUserAgentPageDto> pageByModel(RbacUserAgentPageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public RbacUserAgentDto getById(String agentId) throws RuntimeException {
        RbacUserAgentEntity byId = super.getById(agentId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return map.eToD(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String agentId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(agentId);
        // 删除数据
        boolean b = this.removeById(agentId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.DeleteDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> agentIds) throws RuntimeException {
        List<RbacUserAgentEntity> entities = this.listByIds(agentIds);
        if (CollUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getAgentId()));
        int i = mapper.deleteBatchIds(waitDeleteList);
        if (i <= 0) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.BatchDeleteDataFail"));
        }
    }
}
