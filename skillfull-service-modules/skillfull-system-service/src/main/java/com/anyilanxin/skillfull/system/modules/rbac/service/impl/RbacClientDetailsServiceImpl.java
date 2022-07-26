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

import cn.hutool.core.collection.CollectionUtil;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.constant.impl.CommonNotHaveType;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.oauth2common.utils.PasswordCheck;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacClientAuthVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacClientDetailsPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacClientDetailsVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacClientDetailsEntity;
import com.anyilanxin.skillfull.system.modules.rbac.mapper.RbacClientDetailsMapper;
import com.anyilanxin.skillfull.system.modules.rbac.mapper.RbacRoleClientMapper;
import com.anyilanxin.skillfull.system.modules.rbac.mapper.RbacRoleMapper;
import com.anyilanxin.skillfull.system.modules.rbac.service.IRbacClientDetailsService;
import com.anyilanxin.skillfull.system.modules.rbac.service.IRbacRoleClientService;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacClientDetailsDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacClientDetailsPageDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacRoleSimpleDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.mapstruct.RbacClientDetailsCopyMap;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 授权客户端信息(RbacClientDetails)业务层实现
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-05-02 16:12:21
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RbacClientDetailsServiceImpl extends ServiceImpl<RbacClientDetailsMapper, RbacClientDetailsEntity> implements IRbacClientDetailsService {
    private final RbacClientDetailsCopyMap map;
    private final RbacRoleClientMapper rbacRoleClientMapper;
    private final RbacRoleMapper rbacRoleMapper;
    private final IRbacRoleClientService roleClientService;
    private final PasswordEncoder passwordEncoder;
    private final RbacClientDetailsMapper mapper;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(RbacClientDetailsVo vo) throws RuntimeException {
        // 数据校验
        this.check(vo);
        PasswordCheck passwordCheck = PasswordCheck.getSingleton(passwordEncoder);
        RbacClientDetailsEntity entity = map.vToE(vo);
        String encodePassword = passwordCheck.getEncodePassword(vo.getClientSecurity());
        entity.setClientSecurity(encodePassword);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.SaveDataFail"));
        }
    }


    /**
     * 数据校验
     *
     * @param vo 客户端信息
     * @author zxiaozhou
     * @date 2022-02-10 16:19
     */
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    void check(RbacClientDetailsVo vo) {
        if (StringUtils.isBlank(vo.getClientId())) {
            if (StringUtils.isBlank(vo.getClientId())) {
                throw new ResponseException(Status.VERIFICATION_FAILED, "客户端id不能为空");
            }
            if (StringUtils.isBlank(vo.getClientSecurity())) {
                throw new ResponseException(Status.VERIFICATION_FAILED, "客户端密码不能为空");
            }
            LambdaQueryWrapper<RbacClientDetailsEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(RbacClientDetailsEntity::getClientId, vo.getClientId());
            RbacClientDetailsEntity one = this.getOne(lambdaQueryWrapper);
            if (Objects.nonNull(one)) {
                throw new ResponseException(Status.VERIFICATION_FAILED, "客户端编码已经存在，请换一个");
            }
        } else {
            vo.setClientId(null);
            vo.setClientSecurity(null);
        }
        if (vo.getLimitError() == CommonNotHaveType.HAVE.getType() && (Objects.isNull(vo.getMaxErrorNum()) || vo.getMaxErrorNum() <= 0)) {
            throw new ResponseException(Status.VERIFICATION_FAILED, "当前设置了需要限制验证错误次数，请填写最大允许错误次数");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String clientDetailId, RbacClientDetailsVo vo) throws RuntimeException {
        // 数据校验
        this.check(vo);
        // 查询数据是否存在
        this.getById(clientDetailId);
        // 更新数据
        RbacClientDetailsEntity entity = map.vToE(vo);
        entity.setClientDetailId(clientDetailId);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.UpdateDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<RbacClientDetailsPageDto> pageByModel(RbacClientDetailsPageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public RbacClientDetailsDto getById(String clientDetailId) throws RuntimeException {
        RbacClientDetailsEntity byId = super.getById(clientDetailId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        RbacClientDetailsDto rbacClientDetailsDto = map.eToD(byId);
        // 获取管关联角色
        Set<String> roleIds = rbacRoleClientMapper.selectRoleListById(clientDetailId);
        if (CollectionUtil.isEmpty(roleIds)) {
            roleIds = Collections.emptySet();
        }
        Set<RbacRoleSimpleDto> roleInfos = rbacRoleClientMapper.selectRoleAllInfoListById(clientDetailId);
        if (CollectionUtil.isEmpty(roleInfos)) {
            roleInfos = Collections.emptySet();
        }
        rbacClientDetailsDto.setRoleIds(roleIds);
        rbacClientDetailsDto.setRoleInfos(roleInfos);
        return rbacClientDetailsDto;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String clientDetailId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(clientDetailId);
        // 删除数据
        boolean b = this.removeById(clientDetailId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.DeleteDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> clientDetailIds) throws RuntimeException {
        List<RbacClientDetailsEntity> entities = this.listByIds(clientDetailIds);
        if (CollectionUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getClientDetailId()));
        int i = mapper.deleteBatchIds(waitDeleteList);
        if (i <= 0) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.BatchDeleteDataFail"));
        }
    }


    @Override
    public void updateState(String clientDetailId, Integer type) {
        // 查询数据是否存在
        this.getById(clientDetailId);
        RbacClientDetailsEntity detailsEntity = RbacClientDetailsEntity.builder()
                .clientDetailId(clientDetailId)
                .clientStatus(type)
                .build();
        boolean result = super.updateById(detailsEntity);
        if (!result) {
            throw new ResponseException("修改状态失败");
        }
    }


    @Override
    public void updateAuth(String clientDetailId, RbacClientAuthVo vo) {
        // 查询客户端是否存在
        this.getById(clientDetailId);
        // 更新角色关联
        roleClientService.deleteBatch(List.of(clientDetailId));
        roleClientService.saveBatch(clientDetailId, vo.getRoleIds());
    }
}
