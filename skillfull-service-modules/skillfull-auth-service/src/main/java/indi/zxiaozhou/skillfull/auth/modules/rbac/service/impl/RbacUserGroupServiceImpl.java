// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.rbac.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacUserGroupPageVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacUserGroupQueryVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacUserGroupVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacUserGroupEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.mapper.RbacUserGroupMapper;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.IRbacUserGroupService;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacUserGroupDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacUserGroupPageDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.mapstruct.*;
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
 * 用户组(RbacUserGroup)业务层实现
 *
 * @author zxiaozhou
 * @date 2021-05-17 23:13:08
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RbacUserGroupServiceImpl extends ServiceImpl<RbacUserGroupMapper, RbacUserGroupEntity> implements IRbacUserGroupService {
    private final RbacUserGroupDtoMap dtoMap;
    private final RbacUserGroupPageDtoMap pageDtoMap;
    private final RbacUserGroupVoMap voMap;
    private final RbacUserGroupQueryVoMap queryVoMap;
    private final RbacUserGroupPageVoMap pageVoMap;
    private final RbacUserGroupMapper mapper;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(RbacUserGroupVo vo) throws RuntimeException {
        RbacUserGroupEntity entity = voMap.aToB(vo);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存数据失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String userGroupId, RbacUserGroupVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(userGroupId);
        // 更新数据
        RbacUserGroupEntity entity = voMap.aToB(vo);
        entity.setUserGroupId(userGroupId);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新数据失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<RbacUserGroupDto> selectListByModel(RbacUserGroupQueryVo vo) throws RuntimeException {
        List<RbacUserGroupDto> list = mapper.selectListByModel(vo);
        if (CollectionUtils.isEmpty(list)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "未找到符合条件数据");
        }
        return list;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<RbacUserGroupPageDto> pageByModel(RbacUserGroupPageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public RbacUserGroupDto getById(String userGroupId) throws RuntimeException {
        RbacUserGroupEntity byId = super.getById(userGroupId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "未找到符合条件数据");
        }
        return dtoMap.bToA(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String userGroupId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(userGroupId);
        // 删除数据
        boolean b = this.removeById(userGroupId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除数据失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> userGroupIds) throws RuntimeException {
        List<RbacUserGroupEntity> entities = this.listByIds(userGroupIds);
        if (CollectionUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "数据不存在或已经被别人删除");
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getUserGroupId()));
        int i = mapper.deleteBatchIds(waitDeleteList);
        if (i <= 0) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "批量删除数据失败");
        }
    }
}