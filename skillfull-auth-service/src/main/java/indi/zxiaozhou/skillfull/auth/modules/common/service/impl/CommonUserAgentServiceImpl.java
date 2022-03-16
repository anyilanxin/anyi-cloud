// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.common.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.zxiaozhou.skillfull.auth.modules.common.controller.vo.CommonUserAgentPageVo;
import indi.zxiaozhou.skillfull.auth.modules.common.controller.vo.CommonUserAgentQueryVo;
import indi.zxiaozhou.skillfull.auth.modules.common.controller.vo.CommonUserAgentVo;
import indi.zxiaozhou.skillfull.auth.modules.common.entity.CommonUserAgentEntity;
import indi.zxiaozhou.skillfull.auth.modules.common.mapper.CommonUserAgentMapper;
import indi.zxiaozhou.skillfull.auth.modules.common.service.ICommonUserAgentService;
import indi.zxiaozhou.skillfull.auth.modules.common.service.dto.CommonUserAgentDto;
import indi.zxiaozhou.skillfull.auth.modules.common.service.dto.CommonUserAgentPageDto;
import indi.zxiaozhou.skillfull.auth.modules.common.service.mapstruct.*;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 用户-代理人表(CommonUserAgent)业务层实现
 *
 * @author zxiaozhou
 * @date 2021-05-17 23:12:37
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommonUserAgentServiceImpl extends ServiceImpl<CommonUserAgentMapper, CommonUserAgentEntity> implements ICommonUserAgentService {
    private final CommonUserAgentDtoMap dtoMap;
    private final CommonUserAgentPageDtoMap pageDtoMap;
    private final CommonUserAgentVoMap voMap;
    private final CommonUserAgentQueryVoMap queryVoMap;
    private final CommonUserAgentPageVoMap pageVoMap;
    private final CommonUserAgentMapper mapper;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(CommonUserAgentVo vo) throws RuntimeException {
        CommonUserAgentEntity entity = voMap.aToB(vo);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存数据失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String agentId, CommonUserAgentVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(agentId);
        // 更新数据
        CommonUserAgentEntity entity = voMap.aToB(vo);
        entity.setAgentId(agentId);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新数据失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<CommonUserAgentDto> selectListByModel(CommonUserAgentQueryVo vo) throws RuntimeException {
        List<CommonUserAgentDto> list = mapper.selectListByModel(vo);
        if (CollectionUtils.isEmpty(list)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "未找到符合条件数据");
        }
        return list;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<CommonUserAgentPageDto> pageByModel(CommonUserAgentPageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public CommonUserAgentDto getById(String agentId) throws RuntimeException {
        CommonUserAgentEntity byId = super.getById(agentId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "未找到符合条件数据");
        }
        return dtoMap.bToA(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String agentId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(agentId);
        // 删除数据
        boolean b = this.removeById(agentId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除数据失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> agentIds) throws RuntimeException {
        List<CommonUserAgentEntity> entities = this.listByIds(agentIds);
        if (CollectionUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "数据不存在或已经被别人删除");
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getAgentId()));
        int i = mapper.deleteBatchIds(waitDeleteList);
        if (i <= 0) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "批量删除数据失败");
        }
    }
}