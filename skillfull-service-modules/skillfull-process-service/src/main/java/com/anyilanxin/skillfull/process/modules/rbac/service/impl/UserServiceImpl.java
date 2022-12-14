// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.rbac.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.corecommon.utils.CoreCommonUtils;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.process.modules.rbac.controller.vo.*;
import com.anyilanxin.skillfull.process.modules.rbac.service.IUserService;
import com.anyilanxin.skillfull.process.modules.rbac.service.dto.UserDetailDto;
import com.anyilanxin.skillfull.process.modules.rbac.service.dto.UserDto;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.Tenant;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.identity.UserQuery;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 用户相关
 *
 * @author zxiaozhou
 * @date 2021-11-05 17:30
 * @since JDK1.8
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private static final String DEFAULT_PASSWORD = "skillfull_cloud_kkk13689";
    private final static String DEFAULT_USER_ID = "admin";
    private final IdentityService identityService;

    @Override
    @GlobalTransactional
    public void saveOrUpdate(UserVo vo) throws RuntimeException {
        // 创建或更新用户
        User user = identityService.createUserQuery().userId(vo.getUserId()).singleResult();
        if (Objects.isNull(user)) {
            user = vo.getCamundaUser();
            user.setPassword(DEFAULT_PASSWORD);
        } else {
            user.setEmail(vo.getEmail());
            user.setFirstName(vo.getRealName());
            user.setLastName(vo.getUserName());
        }
        identityService.saveUser(user);
        // 保存详细信息
        setDetailInfo(vo.getUserId(), vo.getDetailInfo());
    }


    @Override
    @GlobalTransactional
    public void deleteOrAddGroup(UserGroupVo vo) throws RuntimeException {
        String userId = vo.getUserId();
        List<Group> list = identityService.createGroupQuery().list();
        if (CollUtil.isNotEmpty(list)) {
            // 删除历史信息
            list.forEach(v -> {
                User user = identityService.createUserQuery()
                        .userId(userId)
                        .memberOfGroup(v.getId())
                        .singleResult();
                if (Objects.nonNull(user)) {
                    identityService.deleteMembership(userId, v.getId());
                }
            });
            // 添加新信息
            if (CollUtil.isNotEmpty(vo.getGroupIds())) {
                vo.getGroupIds().forEach(v -> identityService.createMembership(userId, v));
            }
        }
    }


    @Override
    @GlobalTransactional
    public void deleteOrAddTenant(UserTenantVo vo) throws RuntimeException {
        String userId = vo.getUserId();
        List<Tenant> list = identityService.createTenantQuery().list();
        if (CollUtil.isNotEmpty(list)) {
            // 删除历史信息
            list.forEach(v -> {
                User user = identityService.createUserQuery()
                        .userId(userId)
                        .memberOfTenant(v.getId())
                        .singleResult();
                if (Objects.nonNull(user)) {
                    identityService.deleteTenantUserMembership(v.getId(), userId);
                }
            });
            // 添加新信息
            if (CollUtil.isNotEmpty(vo.getTenantIds())) {
                vo.getTenantIds().forEach(v -> identityService.createTenantUserMembership(v, userId));
            }
        }
    }


    @Override
    public UserDto getUser(String userId) throws RuntimeException {
        User user = getCamundaUser(userId);
        UserDetailDto detailInfo = this.getDetailInfo(user.getId());
        return new UserDto().getUser(user, detailInfo);
    }


    @Override
    public List<UserDto> getUserList(UserQueryVo vo) throws RuntimeException {
        UserQuery userQuery = identityService.createUserQuery();
        if (StringUtils.isNotBlank(vo.getEmail())) {
            userQuery.userEmailLike("%" + vo.getEmail() + "%");
        }
        if (StringUtils.isNotBlank(vo.getRealName())) {
            userQuery.userFirstNameLike("%" + vo.getRealName() + "%");
        }
        if (StringUtils.isNotBlank(vo.getUserName())) {
            userQuery.userLastName("%" + vo.getUserName() + "%");
        }
        if (StringUtils.isNotBlank(vo.getGroupId())) {
            userQuery.memberOfGroup(vo.getGroupId());
        }
        if (StringUtils.isNotBlank(vo.getTenantId())) {
            userQuery.memberOfTenant(vo.getTenantId());
        }
        List<User> list = userQuery.list();
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<UserDto> userList = new ArrayList<>(list.size());
        list.forEach(v -> {
            UserDetailDto detailInfo = this.getDetailInfo(v.getId());
            UserDto userModel = new UserDto().getUser(v, detailInfo);
            userList.add(userModel);
        });
        return userList;
    }


    @Override
    public PageDto<UserDto> getUserPage(UserQueryPageVoCamunda vo) throws RuntimeException {
        UserQuery userQuery = identityService.createUserQuery();
        if (StringUtils.isNotBlank(vo.getRealName())) {
            userQuery.userFirstNameLike("%" + vo.getRealName() + "%");
        }
        if (StringUtils.isNotBlank(vo.getEmail())) {
            userQuery.userEmailLike("%" + vo.getEmail() + "%");
        }
        if (StringUtils.isNotBlank(vo.getUserName())) {
            userQuery.userLastName("%" + vo.getUserName() + "%");
        }
        if (StringUtils.isNotBlank(vo.getGroupId())) {
            userQuery.memberOfGroup(vo.getGroupId());
        }
        if (StringUtils.isNotBlank(vo.getTenantId())) {
            userQuery.memberOfTenant(vo.getTenantId());
        }
        if (CollUtil.isNotEmpty(vo.getAscs())) {
            vo.getAscs().forEach(v -> {
                if (v.equals("realName")) {
                    userQuery.orderByUserFirstName().asc();
                }
                if (v.equals("userName")) {
                    userQuery.orderByUserLastName().asc();
                }
                if (v.equals("email")) {
                    userQuery.orderByUserEmail().asc();
                }
            });
        }
        if (CollUtil.isNotEmpty(vo.getDescs())) {
            vo.getAscs().forEach(v -> {
                if (v.equals("realName")) {
                    userQuery.orderByUserFirstName().desc();
                }
                if (v.equals("userName")) {
                    userQuery.orderByUserLastName().desc();
                }
                if (v.equals("email")) {
                    userQuery.orderByUserEmail().desc();
                }
            });
        }
        long count = userQuery.count();
        if (count == 0L) {
            return new PageDto<>(0, Collections.emptyList());
        }
        List<User> list = userQuery.listPage(vo.getCurrent(), vo.getSize());
        List<UserDto> userList = new ArrayList<>(list.size());
        list.forEach(v -> {
            UserDetailDto detailInfo = this.getDetailInfo(v.getId());
            UserDto userModel = new UserDto().getUser(v, detailInfo);
            userList.add(userModel);
        });
        return new PageDto<>(count, userList);
    }


    @Override
    @GlobalTransactional
    public void deleteUser(String userId) throws RuntimeException {
        // 查询用户是否存在
        getCamundaUser(userId);
        // 删除用户
        identityService.deleteUser(userId);
        // 删除详细信息
        deleteDetailInfo(userId);
        // 删除关联关系
        List<Group> groups = identityService.createGroupQuery().list();
        if (CollUtil.isNotEmpty(groups)) {
            groups.forEach(v -> identityService.deleteMembership(userId, v.getId()));
        }
        List<Tenant> tenants = identityService.createTenantQuery().list();
        if (CollUtil.isNotEmpty(tenants)) {
            tenants.forEach(v -> identityService.deleteTenantUserMembership(v.getId(), userId));
        }
    }


    /**
     * 获取详细信息
     *
     * @param userId ${@link String} 用户id
     * @return UserDetailDto ${@link UserDetailDto}
     * @author zxiaozhou
     * @date 2021-11-07 00:03
     */
    private UserDetailDto getDetailInfo(String userId) {
        UserDetailDto detailInfo = null;
        List<String> userInfoKeys = identityService.getUserInfoKeys(userId);
        if (CollUtil.isNotEmpty(userInfoKeys)) {
            JSONObject jsonObject = new JSONObject();
            userInfoKeys.forEach(v -> jsonObject.put(v, identityService.getUserInfo(userId, v)));
            detailInfo = CoreCommonUtils.jsonStrToObject(jsonObject.toJSONString(), UserDetailDto.class);
        }
        return detailInfo;
    }

    /**
     * 保存或更新详细信息
     *
     * @param userId     ${@link String} 用户id
     * @param detailInfo ${@link UserDetailVo} 详细信息
     * @author zxiaozhou
     * @date 2021-11-07 00:03
     */
    private void setDetailInfo(String userId, UserDetailVo detailInfo) {
        // 删除历史详细信息
        deleteDetailInfo(userId);
        // 添加新的详细信息
        if (Objects.nonNull(detailInfo)) {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(detailInfo));
            jsonObject.forEach((k, v) -> {
                if (StringUtils.isNotBlank(k) && Objects.nonNull(v)) {
                    identityService.setUserInfo(userId, k, v.toString());
                }
            });
        }
    }


    /**
     * 删除用户详细信息
     *
     * @param userId ${@link String} 用户id
     * @author zxiaozhou
     * @date 2021-11-07 00:06
     */
    private void deleteDetailInfo(String userId) {
        List<String> userInfoKeys = identityService.getUserInfoKeys(userId);
        if (CollUtil.isNotEmpty(userInfoKeys)) {
            userInfoKeys.forEach(v -> identityService.deleteUserInfo(userId, v));
        }
    }


    /**
     * 获取用户信息
     *
     * @param userId ${@link String} 用户id
     * @return User ${@link User}
     * @author zxiaozhou
     * @date 2021-11-07 00:42
     */
    private User getCamundaUser(String userId) {
        // 查询用户信息
        User user = identityService.createUserQuery().userId(userId).singleResult();
        if (Objects.isNull(user)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "用户不存在");
        }
        return user;
    }


    @Override
    @GlobalTransactional
    public void syncUser(Set<SyncUserVo> voSet) throws RuntimeException {
        List<Group> groups = identityService.createGroupQuery().list();
        List<Tenant> tenants = identityService.createTenantQuery().list();
        List<User> list = identityService.createUserQuery().list();
        if (CollUtil.isNotEmpty(list)) {
            // 删除历史数据
            list.forEach(v -> {
                if (!DEFAULT_USER_ID.equals(v.getId())) {
                    // 删除关联关系
                    if (CollUtil.isNotEmpty(tenants)) {
                        tenants.forEach(sv -> {
                            User user = identityService.createUserQuery().userId(v.getId()).memberOfTenant(sv.getId()).singleResult();
                            if (Objects.nonNull(user)) {
                                identityService.deleteTenantUserMembership(sv.getId(), v.getId());
                            }
                        });
                    }
                    if (CollUtil.isNotEmpty(groups)) {
                        groups.forEach(sv -> {
                            User user = identityService.createUserQuery().userId(v.getId()).memberOfGroup(sv.getId()).singleResult();
                            if (Objects.nonNull(user)) {
                                identityService.deleteMembership(v.getId(), sv.getId());
                            }
                        });
                    }
                    // 删除用户
                    identityService.deleteUser(v.getId());
                    // 删除附加信息
                    deleteDetailInfo(v.getId());
                }
            });
            // 添加新数据
            voSet.forEach(v -> {
                User camundaUser = v.getCamundaUser();
                camundaUser.setPassword(DEFAULT_PASSWORD);
                identityService.saveUser(camundaUser);
                // 保存附加信息
                setDetailInfo(v.getUserId(), v.getDetailInfo());
                // 添加关联关系
                if (CollUtil.isNotEmpty(v.getGroupIds())) {
                    v.getGroupIds().forEach(sv -> identityService.createMembership(v.getUserId(), sv));
                }
                if (CollUtil.isNotEmpty(v.getTenantIds())) {
                    v.getTenantIds().forEach(sv -> identityService.createTenantUserMembership(sv, v.getUserId()));
                }
            });
        }
    }
}
