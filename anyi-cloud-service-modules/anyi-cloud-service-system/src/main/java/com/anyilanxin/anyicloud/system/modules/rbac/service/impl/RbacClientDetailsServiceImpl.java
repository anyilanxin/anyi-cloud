/*
 * Copyright (c) 2021-2023 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Zeebe EE、AnYi Cloud EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.system.modules.rbac.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.corecommon.constant.impl.CommonNotHaveType;
import com.anyilanxin.anyicloud.corecommon.exception.ResponseException;
import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.oauth2common.utils.PasswordCheck;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacClientAuthVo;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacClientDetailsPageVo;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacClientDetailsVo;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacClientDetailsEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.RbacClientDetailsMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.RbacRoleClientMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.RbacRoleMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacClientDetailsService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacRoleClientService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacClientDetailsDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacClientDetailsPageDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacRoleSimpleDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct.RbacClientDetailsCopyMap;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 授权客户端信息(RbacClientDetails)业务层实现
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:21
 * @since 1.0.0
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
     * @author zxh
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
        RbacClientDetailsEntity detailsEntity = RbacClientDetailsEntity.builder().clientDetailId(clientDetailId).clientStatus(type).build();
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
