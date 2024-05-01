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

package com.anyilanxin.cloud.system.modules.authcenter.service.impl;

import com.anyilanxin.cloud.corecommon.base.AnYiResult;
import com.anyilanxin.cloud.corecommon.exception.AnYiResponseException;
import com.anyilanxin.cloud.corecommon.model.auth.UserOrgTreeInfo;
import com.anyilanxin.cloud.corecommon.model.auth.UserRouteMetaModel;
import com.anyilanxin.cloud.corecommon.model.auth.UserRouteModel;
import com.anyilanxin.cloud.corecommon.model.auth.UserRouteTreeModel;
import com.anyilanxin.cloud.corecommon.model.system.RouteTagModel;
import com.anyilanxin.cloud.corecommon.utils.tree.AnYiTreeToolUtils;
import com.anyilanxin.cloud.coremvc.utils.AnYiUserContextUtils;
import com.anyilanxin.cloud.oauth2common.utils.PasswordCheck;
import com.anyilanxin.cloud.storageadapter.model.StorageInfoModel;
import com.anyilanxin.cloud.storagerpcadapter.rpc.StorageRemoteRpcFile;
import com.anyilanxin.cloud.system.core.constant.impl.MenuType;
import com.anyilanxin.cloud.system.modules.authcenter.controller.vo.FindPasswordVo;
import com.anyilanxin.cloud.system.modules.authcenter.controller.vo.UpdateInfoVo;
import com.anyilanxin.cloud.system.modules.authcenter.controller.vo.UpdatePasswordVo;
import com.anyilanxin.cloud.system.modules.authcenter.controller.vo.UpdatePhoneVo;
import com.anyilanxin.cloud.system.modules.authcenter.service.IUserCenterService;
import com.anyilanxin.cloud.system.modules.authcenter.service.mapstruct.UpdateUserInfoCopyMap;
import com.anyilanxin.cloud.system.modules.authcenter.service.mapstruct.UserMenuCopyMap;
import com.anyilanxin.cloud.system.modules.authcenter.service.mapstruct.UserMenuThreeCopyMap;
import com.anyilanxin.cloud.system.modules.authcenter.service.mapstruct.UserOrgCopyMap;
import com.anyilanxin.cloud.system.modules.rbac.entity.RbacUserEntity;
import com.anyilanxin.cloud.system.modules.rbac.mapper.*;
import com.anyilanxin.cloud.system.modules.rbac.service.dto.RbacMenuDto;
import com.anyilanxin.cloud.system.modules.rbac.service.dto.RbacOrgUserDto;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * 用户中心
 *
 * @author zxh
 * @date 2022-05-02 09:18
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserCenterServiceImpl implements IUserCenterService {
    private final RbacUserMapper userMapper;
    private final RbacOrgUserMapper orgUserMapper;
    private final UserMenuThreeCopyMap threeCopyMap;
    private final RbacMenuMapper menuMapper;
    private final UserMenuCopyMap menuCopyMap;
    private final UserOrgCopyMap userOrgCopyMap;
    private final RbacRoleUserMapper roleUserMapper;
    private final RbacOrgRoleUserMapper orgRoleUserMapper;
    private final UpdateUserInfoCopyMap userInfoCopyMap;
    private final PasswordEncoder passwordEncoder;
    private final StorageRemoteRpcFile storageRemoteRpcFile;

    @Override
    public List<UserRouteModel> getRouterInfo(String systemCodes) {
        Set<String> systemCodeSet = new HashSet<>(64);
        if (StringUtils.isNotBlank(systemCodes)) {
            systemCodeSet.addAll(Set.of(systemCodes.split("[,，]")));
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<CompletableFuture<Void>> futures = new ArrayList<>(2);
        // 1. 获取非按钮菜单信息与按钮菜单信息
        Map<String, List<RbacMenuDto>> buttonMap = new HashMap<>(128);
        List<RbacMenuDto> notButton = new ArrayList<>(128);
        CompletableFuture<Void> notButtonsCompletableFuture = CompletableFuture.runAsync(() -> {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Set<RbacMenuDto> rbacMenuAllInfos = new HashSet<>(128);
            if (AnYiUserContextUtils.superRole()) {
                Set<RbacMenuDto> allMenu = menuMapper.getAllMenu(systemCodeSet);
                if (CollUtil.isNotEmpty(allMenu)) {
                    rbacMenuAllInfos.addAll(allMenu);
                }
            } else {
                // 获取用户授权角色菜单
                Set<RbacMenuDto> rbacMenuInfos = roleUserMapper.selectMenuByUserId(AnYiUserContextUtils.getUserId(), systemCodeSet);
                if (CollUtil.isNotEmpty(rbacMenuInfos)) {
                    rbacMenuAllInfos.addAll(rbacMenuInfos);
                }
                // 获取用户机构授权角色菜单
                if (StringUtils.isNotBlank(AnYiUserContextUtils.getCurrentOrgId())) {
                    Set<RbacMenuDto> orgMenuInfos = orgRoleUserMapper.selectMenuByUserIdAndOrgId(AnYiUserContextUtils.getUserId(), AnYiUserContextUtils.getCurrentOrgId(), systemCodeSet);
                    if (CollUtil.isNotEmpty(orgMenuInfos)) {
                        rbacMenuAllInfos.addAll(orgMenuInfos);
                    }
                }
            }
            if (CollUtil.isNotEmpty(rbacMenuAllInfos)) {
                // 拆分目录菜单与按钮
                rbacMenuAllInfos.forEach(v -> {
                    if (MenuType.BUTTON.getType() == v.getMenuType()) {
                        List<RbacMenuDto> rbacMenuDtos = buttonMap.get(v.getParentId());
                        if (CollUtil.isEmpty(rbacMenuDtos)) {
                            rbacMenuDtos = new ArrayList<>(64);
                        }
                        rbacMenuDtos.add(v);
                        buttonMap.put(v.getParentId(), rbacMenuDtos);
                    } else {
                        notButton.add(v);
                    }
                });
            }
        }, Executors.newVirtualThreadPerTaskExecutor());
        futures.add(notButtonsCompletableFuture);
        // 2.获取所有按钮信息并转换为map
        Map<String, List<RbacMenuDto>> allButtonMap = new HashMap<>(128);
        CompletableFuture<Void> allButtonCompletableFuture = CompletableFuture.runAsync(() -> {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Set<RbacMenuDto> allButton = menuMapper.getAllButton(systemCodeSet);
            if (CollUtil.isNotEmpty(allButton)) {
                allButton.forEach(v -> {
                    List<RbacMenuDto> rbacMenuDtos = allButtonMap.get(v.getParentId());
                    if (CollUtil.isEmpty(rbacMenuDtos)) {
                        rbacMenuDtos = new ArrayList<>(64);
                    }
                    rbacMenuDtos.add(v);
                    allButtonMap.put(v.getParentId(), rbacMenuDtos);
                });
            }
        }, Executors.newVirtualThreadPerTaskExecutor());
        futures.add(allButtonCompletableFuture);
        try {
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).get();
        } catch (ExecutionException | InterruptedException e) {
            Throwable cause = e.getCause();
            throw new AnYiResponseException(cause.getMessage());
        }
        // 3.组装最后路由信息
        List<UserRouteModel> routeModelList = new ArrayList<>(notButton.size());
        notButton.forEach(v -> {
            UserRouteModel userRouteModel = menuCopyMap.dToE(v);
            userRouteModel.setName(v.getPathName());
            // 处理meta
            UserRouteMetaModel metaModel = menuCopyMap.dToV(v);
            metaModel.setTitle(v.getMetaTitle());
            metaModel.setNoRoleActionSet(Collections.emptySet());
            metaModel.setActionTagExpression(Collections.emptyMap());
            // 处理tag
            if (v.isShowTag()) {
                RouteTagModel tag = new RouteTagModel();
                tag.setContent(v.getContent());
                tag.setDot(v.isDot());
                tag.setType(v.getType());
                metaModel.setTag(tag);
            }
            // 处理按钮权限校验映射
            List<RbacMenuDto> allMenuAuths = allButtonMap.get(v.getMenuId());
            if (CollUtil.isNotEmpty(allMenuAuths)) {
                Map<String, String> actionTagExpression = new HashMap<>(allMenuAuths.size());
                allMenuAuths.forEach(sv -> actionTagExpression.put(sv.getButtonActionTag(), sv.getButtonExpress()));
                metaModel.setActionTagExpression(actionTagExpression);
            }
            // 处理授权的非角色权限指令
            List<RbacMenuDto> rbacMenuDtos = buttonMap.get(v.getMenuId());
            if (CollUtil.isNotEmpty(rbacMenuDtos)) {
                Set<String> noRoleActionSet = new HashSet<>(rbacMenuDtos.size());
                rbacMenuDtos.forEach(sv -> {
                    if (StringUtils.isNotBlank(sv.getButtonAction())) {
                        noRoleActionSet.addAll(Set.of(sv.getButtonAction().split("[,，]")));
                    }
                });
                metaModel.setNoRoleActionSet(noRoleActionSet);
            }
            userRouteModel.setMeta(metaModel);
            routeModelList.add(userRouteModel);
        });
        Collections.sort(routeModelList);
        return routeModelList;
    }


    @Override
    public List<UserRouteTreeModel> getRouterInfoTree(String systemCodes) {
        List<UserRouteModel> routerInfo = getRouterInfo(systemCodes);
        if (CollUtil.isEmpty(routerInfo)) {
            return Collections.emptyList();
        }
        // 分离最顶级、下级
        List<UserRouteTreeModel> routerInfoParents = new ArrayList<>(routerInfo.size());
        List<UserRouteTreeModel> routerInfoChildren = new ArrayList<>(routerInfo.size());
        for (UserRouteModel routeModel : routerInfo) {
            String parentId = routeModel.getParentId();
            List<UserRouteModel> collect = routerInfo.stream().filter(v -> v.getMenuId().equals(parentId)).collect(Collectors.toList());
            if (CollUtil.isEmpty(collect)) {
                UserRouteTreeModel userRouteTreeModel = threeCopyMap.aToB(routeModel);
                if (StringUtils.isBlank(userRouteTreeModel.getPath()) || !userRouteTreeModel.getPath().startsWith("/")) {
                    userRouteTreeModel.setPath("/" + (StringUtils.isNotBlank(userRouteTreeModel.getPath()) ? userRouteTreeModel.getPath() : ""));
                }
                routerInfoParents.add(userRouteTreeModel);
            } else {
                routerInfoChildren.add(threeCopyMap.aToB(routeModel));
            }
        }
        Collections.sort(routerInfoParents);
        // 组装树形
        AnYiTreeToolUtils<UserRouteTreeModel> treeToolUtils = new AnYiTreeToolUtils<>(routerInfoParents, routerInfoChildren, new AnYiTreeToolUtils.TreeId<>() {
            @Override
            public String getId(UserRouteTreeModel model) {
                return model.getMenuId();
            }


            @Override
            public String getParentId(UserRouteTreeModel model) {
                return model.getParentId();
            }
        });
        return treeToolUtils.getTree();
    }


    @Override
    public void updateUserInfo(UpdateInfoVo vo) {
        RbacUserEntity userEntity = userInfoCopyMap.aToB(vo);
        userEntity.setUserId(AnYiUserContextUtils.getUserId());
        int i = userMapper.updateById(userEntity);
        if (i <= 0) {
            throw new AnYiResponseException("修改用户信息失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateUserAvatar(MultipartFile file) {
        AnYiResult<StorageInfoModel> upload = storageRemoteRpcFile.upload(file, null);
        if (!upload.isSuccess()) {
            throw new AnYiResponseException(upload.getCode(), upload.getMessage());
        }
        RbacUserEntity userEntity = RbacUserEntity.builder().avatar(upload.getData().getFileRelativePath()).userId(AnYiUserContextUtils.getUserId()).build();
        int i = userMapper.updateById(userEntity);
        if (i <= 0) {
            throw new AnYiResponseException("修改头像失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateUserPhone(UpdatePhoneVo vo) {
        RbacUserEntity userEntity = RbacUserEntity.builder().phone(vo.getPhone()).userId(AnYiUserContextUtils.getUserId()).build();
        int i = userMapper.updateById(userEntity);
        if (i <= 0) {
            throw new AnYiResponseException("修改手机号失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updatePassword(UpdatePasswordVo vo) {
        PasswordCheck passwordCheck = PasswordCheck.getSingleton(passwordEncoder);
        PasswordCheck.PasswordInfo passwordInfo = passwordCheck.getPasswordInfo(vo.getNewPassword());
        // @formatter:off
        RbacUserEntity userEntity = RbacUserEntity
                .builder()
                .password(passwordInfo.getEncodedPassword())
                .salt(passwordInfo.getSalt())
                .userId(AnYiUserContextUtils.getUserId())
                .build();
        // @formatter:off
        int i = userMapper.updateById(userEntity);
        if (i <= 0) {
            throw new AnYiResponseException("修改密码失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void findPassword(FindPasswordVo vo) {
        LambdaQueryWrapper<RbacUserEntity> lambdaQueryWrapper = Wrappers.<RbacUserEntity>lambdaQuery().eq(RbacUserEntity::getPhone, vo.getPhone());
        RbacUserEntity userInfo = userMapper.selectOne(lambdaQueryWrapper);
        if (Objects.isNull(userInfo)) {
            throw new AnYiResponseException("用户信息不存在");
        }
        PasswordCheck passwordCheck = PasswordCheck.getSingleton(passwordEncoder);
        PasswordCheck.PasswordInfo passwordInfo = passwordCheck.getPasswordInfo(vo.getNewPassword());
        // @formatter:off
        RbacUserEntity userEntity = RbacUserEntity
                .builder()
//                .password(passwordInfo.getEncodedPassword())
//                .salt(passwordInfo.getSalt())
                .userId(userInfo.getUserId())
                .build();
        // @formatter:off
        int i = userMapper.updateById(userEntity);
        if (i <= 0) {
            throw new AnYiResponseException("找回密码失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public void sendSmsCode(String phone) {

    }


    @Override
    public List<UserOrgTreeInfo> getUserOrgInfo() {
        List<RbacOrgUserDto> rbacOrgUserDtos = orgUserMapper.selectUserOrgListByUserId(AnYiUserContextUtils.getUserId());
        if (CollUtil.isEmpty(rbacOrgUserDtos)) {
            return Collections.emptyList();
        }
        // 分离顶级出顶级
        List<UserOrgTreeInfo> userOrgTreeInfos = userOrgCopyMap.dToE(rbacOrgUserDtos);
        List<UserOrgTreeInfo> userOrgTreeParent = new ArrayList<>(rbacOrgUserDtos.size());
        List<UserOrgTreeInfo> userOrgTreeChildren = new ArrayList<>(rbacOrgUserDtos.size());
        for (UserOrgTreeInfo userDto : userOrgTreeInfos) {
            String parentId = userDto.getParentId();
            List<UserOrgTreeInfo> collect = userOrgTreeInfos.stream().filter(v -> v.getOrgId().equals(parentId)).collect(Collectors.toList());
            if (CollUtil.isEmpty(collect)) {
                userOrgTreeParent.add(userDto);
            } else {
                userOrgTreeChildren.add(userDto);
            }
        }
        // 构建树形
        AnYiTreeToolUtils<UserOrgTreeInfo> treeToolUtils = new AnYiTreeToolUtils<>(userOrgTreeParent, userOrgTreeChildren, new AnYiTreeToolUtils.TreeId<>() {
            @Override
            public String getId(UserOrgTreeInfo model) {
                return model.getOrgId();
            }


            @Override
            public String getParentId(UserOrgTreeInfo model) {
                return model.getParentId();
            }
        });
        return treeToolUtils.getTree();
    }
}
