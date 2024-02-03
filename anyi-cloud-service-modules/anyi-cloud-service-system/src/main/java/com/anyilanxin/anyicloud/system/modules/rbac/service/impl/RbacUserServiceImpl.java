/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.system.modules.rbac.service.impl;

import com.anyilanxin.anyicloud.corecommon.constant.AnYiResultStatus;
import com.anyilanxin.anyicloud.corecommon.exception.AnYiResponseException;
import com.anyilanxin.anyicloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.anyicloud.corecommon.utils.AnYiI18nUtil;
import com.anyilanxin.anyicloud.coremvc.utils.AnYiUserContextUtils;
import com.anyilanxin.anyicloud.database.utils.PageUtils;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacEnalbeUserPageQuery;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacUserPageQuery;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacUserVo;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacUserEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.RbacOrgRoleUserMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.RbacOrgUserMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.RbacRoleUserMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.RbacUserMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacOrgRoleUserService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacRoleUserService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacUserService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacProcessCommonDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacRoleSimpleDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacUserDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacUserPageDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct.RbacUserCopyMap;
import com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct.RbacUserSimpleCopyMap;
import com.anyilanxin.anyicloud.systemadapter.model.SimpleUserModel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.util.RandomUtil;
import org.dromara.hutool.swing.captcha.generator.RandomGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户表(RbacUser)业务层实现
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:21
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RbacUserServiceImpl extends ServiceImpl<RbacUserMapper, RbacUserEntity> implements IRbacUserService {
    private final RbacUserCopyMap map;
    private final RbacUserSimpleCopyMap userSimpleCopyMap;
    private final RbacOrgUserMapper orgUserMapper;
    private final IRbacRoleUserService roleUserService;
    private final RbacOrgRoleUserMapper orgRoleUserMapper;
    private final RbacRoleUserMapper rbacRoleUserMapper;
    // private final PasswordEncoder passwordEncoder;
    private final IRbacOrgRoleUserService orgRoleUserService;
    private final RbacUserMapper mapper;

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(RbacUserVo vo) throws RuntimeException {
        RbacUserEntity entity = map.vToE(vo);
        check(entity);
        boolean result = super.save(entity);
        if (!result) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.SaveDataFail"));
        }
        // 处理权限
        String userId = entity.getUserId();
        handleAuth(userId, vo);
    }


    /**
     * 数据检查
     *
     * @param entity
     * @author zxh
     * @date 2022-06-02 22:20
     */
    private void check(RbacUserEntity entity) {
        // 如果新增密码必填
        if (StringUtils.isBlank(entity.getUserId())) {
            if (StringUtils.isBlank(entity.getPassword())) {
                throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "密码不能为空");
            }
            // // 设置密码信息
            // PasswordCheck passwordCheck = PasswordCheck.getSingleton(passwordEncoder);
            // PasswordCheck.PasswordInfo passwordInfo =
            // passwordCheck.getPasswordInfo(entity.getPassword());
            // entity.setPassword(passwordInfo.getEncodedPassword());
            // entity.setSalt(passwordInfo.getSalt());
            entity.setIsInitialPassword(1);
        }
        // 修改时密码不可修改
        else {
            entity.setPassword(null);
        }
        // 检查数据唯一性
        LambdaQueryWrapper<RbacUserEntity> lambdaQueryWrapper = Wrappers.<RbacUserEntity>lambdaQuery().and(v -> v.eq(RbacUserEntity::getUserName, entity.getUserName()).or().eq(RbacUserEntity::getPhone, entity.getPhone()));
        if (StringUtils.isNotBlank(entity.getUserId())) {
            lambdaQueryWrapper.ne(RbacUserEntity::getUserId, entity.getUserId());
        }
        List<RbacUserEntity> list = this.list(lambdaQueryWrapper);
        if (CollUtil.isNotEmpty(list)) {
            throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "当前手机号或用户名已经存在");
        }
    }


    /**
     * 处理用户权限
     *
     * @param userId
     * @param vo
     * @author zxh
     * @date 2022-07-02 23:19
     */
    void handleAuth(String userId, RbacUserVo vo) {
        // 处理用户角色(只有超级管理员可操作)
        if (!AnYiUserContextUtils.superRole() && Objects.nonNull(vo.getRoleIds())) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, "只有超级管理员可设置用户角色");
        }
        // 处理机构角色
        if (StringUtils.isNotBlank(vo.getOrgId())) {
            orgRoleUserService.deleteByUserId(userId, vo.getOrgId());
            orgRoleUserService.saveBatch(userId, vo.getOrgId(), vo.getOrgRoleIds());
        }
        // 处理用户角色
        roleUserService.deleteBatch(List.of(userId));
        roleUserService.saveBatch(userId, vo.getRoleIds());
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String userId, RbacUserVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(userId);
        // 更新数据
        RbacUserEntity entity = map.vToE(vo);
        entity.setUserId(userId);
        check(entity);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.UpdateDataFail"));
        }
        // 处理权限
        handleAuth(userId, vo);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public AnYiPageResult<RbacUserPageDto> pageByModel(RbacUserPageQuery vo) throws RuntimeException {
        return PageUtils.toPageData(mapper.pageByModel(PageUtils.getPage(vo), vo));
    }


    @Override
    public AnYiPageResult<RbacUserPageDto> selectEnableUserPage(RbacEnalbeUserPageQuery vo) {
        return PageUtils.toPageData(mapper.selectEnableUserPage(PageUtils.getPage(vo), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public RbacUserDto getById(String userId, String orgId) throws RuntimeException {
        RbacUserEntity byId = super.getById(userId);
        if (Objects.isNull(byId)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        RbacUserDto rbacUserDto = map.eToD(byId);
        rbacUserDto.setOrgId(orgId);
        // 1. 查询用户角色
        Set<String> roleIds = rbacRoleUserMapper.selectUserRoleListById(userId);
        if (CollUtil.isEmpty(roleIds)) {
            roleIds = Collections.emptySet();
        }
        rbacUserDto.setRoleIds(roleIds);
        Set<RbacRoleSimpleDto> roleInfos = rbacRoleUserMapper.selectUserRoleAllInfoListById(userId);
        if (CollUtil.isEmpty(roleInfos)) {
            roleInfos = Collections.emptySet();
        }
        rbacUserDto.setRoleInfos(roleInfos);
        // 2. 如果机构存在则查询机构角色
        if (StringUtils.isNotBlank(orgId)) {
            Set<String> orgRoleIds = orgRoleUserMapper.selectUserOrgRoleListById(userId, orgId);
            if (CollUtil.isEmpty(orgRoleIds)) {
                orgRoleIds = Collections.emptySet();
            }
            rbacUserDto.setOrgRoleIds(orgRoleIds);
            Set<RbacRoleSimpleDto> orgRoleInfos = orgRoleUserMapper.selectUserOrgRoleAllInfoListById(userId, orgId);
            if (CollUtil.isEmpty(orgRoleInfos)) {
                orgRoleInfos = Collections.emptySet();
            }
            rbacUserDto.setOrgRoleInfos(orgRoleInfos);
        }
        return rbacUserDto;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String userId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(userId);
        // 删除数据
        boolean b = this.removeById(userId);
        if (!b) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.DeleteDataFail"));
        }
        // 删除机构挂接
        orgUserMapper.physicalDeleteByUserId(userId);
        // 删除机构角色
        orgRoleUserMapper.physicalDeleteByUserId(userId);
        // 删除用户角色
        rbacRoleUserMapper.physicalDeleteByUserId(userId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> userIds) throws RuntimeException {
        userIds.forEach(this::deleteById);
    }


    @Override
    public void updateState(String userId, Integer type) {
        // 查询数据是否存在
        this.getById(userId);
        RbacUserEntity entity = RbacUserEntity.builder().userId(userId).userStatus(type).build();
        boolean b = this.updateById(entity);
        if (!b) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, "修改用户状态失败");
        }
    }


    @Override
    public String resetPassword(String userId) {
        // 查询数据是否存在
        this.getById(userId);
        // 随机生成新密码
        RandomGenerator randomGenerator = new RandomGenerator(RandomUtil.BASE_CHAR_NUMBER, 8);
        String password = randomGenerator.generate();
        // PasswordCheck passwordCheck = PasswordCheck.getSingleton(passwordEncoder);
        // PasswordCheck.PasswordInfo passwordInfo =
        // passwordCheck.getPasswordInfo(password);
        // @formatter:off
        RbacUserEntity entity = RbacUserEntity
                .builder()
                .userId(userId)
//                .password(passwordInfo.getEncodedPassword())
//                .salt(passwordInfo.getSalt())
                .build();
        // @formatter:off
        boolean b = this.updateById(entity);
        if (!b) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, "重置密码失败");
        }
        return password;
    }


    @Override
    public List<SimpleUserModel> selectUserByIds(List<String> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<RbacUserEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(RbacUserEntity::getUserId, userIds);
        List<RbacUserEntity> list = this.list(lambdaQueryWrapper);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        return userSimpleCopyMap.aToB(list);
    }


    @Override
    public SimpleUserModel getUserById(String userId) {
        return userSimpleCopyMap.aToB(this.getById(userId));
    }


    @Override
    public List<RbacProcessCommonDto> selectProcessDesignerByIds(List<String> ids) {
        List<RbacUserEntity> rbacUserEntities = this.listByIds(ids);
        if(CollUtil.isEmpty(rbacUserEntities)){
            return Collections.emptyList();
        }
       return rbacUserEntities.stream().map(v->{
            RbacProcessCommonDto dto = new RbacProcessCommonDto();
            dto.setIndex(v.getUserId());
            dto.setName(v.getRealName());
            dto.setState(v.getUserStatus()==1?1:0);
            return dto;
        }).collect(Collectors.toList());
    }


    @Override
    public List<SimpleUserModel> getUserList() {
        List<RbacUserEntity> list = this.list();
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        return userSimpleCopyMap.aToB(list);
    }
}
