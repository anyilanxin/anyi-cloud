// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.authcenter.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.skillfull.corecommon.auth.model.UserOrgTreeInfo;
import com.anyilanxin.skillfull.corecommon.auth.model.UserRouteMetaModel;
import com.anyilanxin.skillfull.corecommon.auth.model.UserRouteModel;
import com.anyilanxin.skillfull.corecommon.auth.model.UserRouteTreeModel;
import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.base.model.system.RouteTagModel;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.corecommon.utils.UserContextUtils;
import com.anyilanxin.skillfull.corecommon.utils.tree.TreeToolUtils;
import com.anyilanxin.skillfull.oauth2common.utils.PasswordCheck;
import com.anyilanxin.skillfull.storageapi.feign.StorageRemoteService;
import com.anyilanxin.skillfull.storageapi.model.StorageInfoModel;
import com.anyilanxin.skillfull.system.core.constant.impl.MenuType;
import com.anyilanxin.skillfull.system.modules.authcenter.controller.vo.FindPasswordVo;
import com.anyilanxin.skillfull.system.modules.authcenter.controller.vo.UpdateInfoVo;
import com.anyilanxin.skillfull.system.modules.authcenter.controller.vo.UpdatePasswordVo;
import com.anyilanxin.skillfull.system.modules.authcenter.controller.vo.UpdatePhoneVo;
import com.anyilanxin.skillfull.system.modules.authcenter.service.IUserCenterService;
import com.anyilanxin.skillfull.system.modules.authcenter.service.mapstruct.UpdateUserInfoCopyMap;
import com.anyilanxin.skillfull.system.modules.authcenter.service.mapstruct.UserMenuCopyMap;
import com.anyilanxin.skillfull.system.modules.authcenter.service.mapstruct.UserMenuThreeCopyMap;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacUserEntity;
import com.anyilanxin.skillfull.system.modules.rbac.mapper.RbacMenuMapper;
import com.anyilanxin.skillfull.system.modules.rbac.mapper.RbacOrgRoleUserMapper;
import com.anyilanxin.skillfull.system.modules.rbac.mapper.RbacRoleUserMapper;
import com.anyilanxin.skillfull.system.modules.rbac.mapper.RbacUserMapper;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacMenuDto;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户中心
 *
 * @author zxiaozhou
 * @date 2022-05-02 09:18
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserCenterServiceImpl implements IUserCenterService {
    private final RbacUserMapper userMapper;
    private final UserMenuThreeCopyMap threeCopyMap;
    private final RbacMenuMapper menuMapper;
    private final UserMenuCopyMap menuCopyMap;
    private final RbacRoleUserMapper roleUserMapper;
    private final RbacOrgRoleUserMapper orgRoleUserMapper;
    private final UpdateUserInfoCopyMap userInfoCopyMap;
    private final PasswordEncoder passwordEncoder;
    private final StorageRemoteService storageRemoteService;


    @Override
    public List<UserRouteModel> getRouterInfo(String systemCodes) {
        Set<String> systemCodeSet = new HashSet<>(64);
        if (StringUtils.isNotBlank(systemCodes)) {
            systemCodeSet.addAll(Set.of(systemCodes.split("[,，]")));
        }
        Set<RbacMenuDto> rbacMenuAllInfos = new HashSet<>(128);
        if (UserContextUtils.superRole()) {
            Set<RbacMenuDto> allMenu = menuMapper.getAllMenu(systemCodeSet);
            if (CollUtil.isNotEmpty(allMenu)) {
                rbacMenuAllInfos.addAll(allMenu);
            }
        } else {
            // 获取用户授权角色菜单
            Set<RbacMenuDto> rbacMenuInfos = roleUserMapper.selectMenuByUserId(UserContextUtils.getUserId(), systemCodeSet);
            if (CollUtil.isNotEmpty(rbacMenuInfos)) {
                rbacMenuAllInfos.addAll(rbacMenuInfos);
            }
            // 获取用户机构授权角色菜单
            if (StringUtils.isNotBlank(UserContextUtils.getCurrentOrgId())) {
                Set<RbacMenuDto> orgMenuInfos = orgRoleUserMapper.selectMenuByUserIdAndOrgId(UserContextUtils.getUserId(), UserContextUtils.getCurrentOrgId(), systemCodeSet);
                if (CollUtil.isNotEmpty(orgMenuInfos)) {
                    rbacMenuAllInfos.addAll(orgMenuInfos);
                }
            }
        }
        if (CollUtil.isEmpty(rbacMenuAllInfos)) {
            return Collections.emptyList();
        }
        // 拆分目录菜单与按钮
        Map<String, List<RbacMenuDto>> buttonMap = new HashMap<>(rbacMenuAllInfos.size());
        List<RbacMenuDto> notButtons = new ArrayList<>(rbacMenuAllInfos.size());
        rbacMenuAllInfos.forEach(v -> {
            if (MenuType.BUTTON.getType() == v.getMenuType()) {
                List<RbacMenuDto> rbacMenuDtos = buttonMap.get(v.getParentId());
                if (CollUtil.isEmpty(rbacMenuDtos)) {
                    rbacMenuDtos = new ArrayList<>(64);
                }
                buttonMap.put(v.getParentId(), rbacMenuDtos);
            } else {
                notButtons.add(v);
            }
        });
        if (CollUtil.isEmpty(notButtons)) {
            return Collections.emptyList();
        }
        // 组装最后路由信息
        List<UserRouteModel> routeModelList = new ArrayList<>(notButtons.size());
        notButtons.forEach(v -> {
            UserRouteModel userRouteModel = menuCopyMap.dToE(v);
            userRouteModel.setName(v.getPathName());
            // 处理meta
            UserRouteMetaModel metaModel = menuCopyMap.dToV(v);
            metaModel.setTitle(v.getMetaTitle());
            metaModel.setActionSet(Collections.emptySet());
            // 处理tag
            if (v.isShowTag()) {
                RouteTagModel tag = new RouteTagModel();
                tag.setContent(v.getContent());
                tag.setDot(v.isDot());
                tag.setType(v.getType());
                metaModel.setTag(tag);
            }
            // 处理授权的非角色权限指令
            List<RbacMenuDto> rbacMenuDtos = buttonMap.get(v.getMenuId());
            if (CollUtil.isNotEmpty(rbacMenuDtos)) {
                Set<String> actionSet = new HashSet<>(rbacMenuDtos.size());
                rbacMenuDtos.forEach(sv -> {
                    if (StringUtils.isNotBlank(sv.getButtonAction())) {
                        actionSet.addAll(Set.of(sv.getButtonAction().split("[,，]")));
                    }
                });
                metaModel.setActionSet(actionSet);
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
        for (UserRouteModel routeModel : routerInfo) {
            String parentId = routeModel.getParentId();
            List<UserRouteModel> collect = routerInfo.stream().filter(v -> v.getMenuId().equals(parentId)).collect(Collectors.toList());
            if (CollUtil.isEmpty(collect)) {
                UserRouteTreeModel userRouteTreeModel = threeCopyMap.aToB(routeModel);
                if (StringUtils.isBlank(userRouteTreeModel.getPath()) || !userRouteTreeModel.getPath().startsWith("/")) {
                    userRouteTreeModel.setPath("/" + (StringUtils.isNotBlank(userRouteTreeModel.getPath()) ? userRouteTreeModel.getPath() : ""));
                }
                routerInfoParents.add(userRouteTreeModel);
            }
        }
        Collections.sort(routerInfoParents);
        // 组装树形
        TreeToolUtils<UserRouteTreeModel> treeToolUtils = new TreeToolUtils<>(routerInfoParents, threeCopyMap.aToB(routerInfo), new TreeToolUtils.TreeId<>() {
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
        userEntity.setUserId(UserContextUtils.getUserId());
        int i = userMapper.updateById(userEntity);
        if (i <= 0) {
            throw new ResponseException("修改用户信息失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateUserAvatar(MultipartFile file) {
        Result<StorageInfoModel> upload = storageRemoteService.upload(file, null);
        if (!upload.isSuccess()) {
            throw new ResponseException(upload.getCode(), upload.getMessage());
        }
        RbacUserEntity userEntity = RbacUserEntity.builder()
                .avatar(upload.getData().getFileRelativePath())
                .userId(UserContextUtils.getUserId())
                .build();
        int i = userMapper.updateById(userEntity);
        if (i <= 0) {
            throw new ResponseException("修改头像失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateUserPhone(UpdatePhoneVo vo) {
        RbacUserEntity userEntity = RbacUserEntity.builder()
                .phone(vo.getPhone())
                .userId(UserContextUtils.getUserId())
                .build();
        int i = userMapper.updateById(userEntity);
        if (i <= 0) {
            throw new ResponseException("修改手机号失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updatePassword(UpdatePasswordVo vo) {
        PasswordCheck passwordCheck = PasswordCheck.getSingleton(passwordEncoder);
        PasswordCheck.PasswordInfo passwordInfo = passwordCheck.getPasswordInfo(vo.getNewPassword());
        RbacUserEntity userEntity = RbacUserEntity.builder()
                .password(passwordInfo.getEncodedPassword())
                .salt(passwordInfo.getSalt())
                .userId(UserContextUtils.getUserId())
                .build();
        int i = userMapper.updateById(userEntity);
        if (i <= 0) {
            throw new ResponseException("修改密码失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void findPassword(FindPasswordVo vo) {
        LambdaQueryWrapper<RbacUserEntity> lambdaQueryWrapper = Wrappers.<RbacUserEntity>lambdaQuery()
                .eq(RbacUserEntity::getPhone, vo.getPhone());
        RbacUserEntity userInfo = userMapper.selectOne(lambdaQueryWrapper);
        if (Objects.isNull(userInfo)) {
            throw new ResponseException("用户信息不存在");
        }
        PasswordCheck passwordCheck = PasswordCheck.getSingleton(passwordEncoder);
        PasswordCheck.PasswordInfo passwordInfo = passwordCheck.getPasswordInfo(vo.getNewPassword());
        RbacUserEntity userEntity = RbacUserEntity.builder()
                .password(passwordInfo.getEncodedPassword())
                .salt(passwordInfo.getSalt())
                .userId(userInfo.getUserId())
                .build();
        int i = userMapper.updateById(userEntity);
        if (i <= 0) {
            throw new ResponseException("找回密码失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public void sendSmsCode(String phone) {

    }


    @Override
    public List<UserOrgTreeInfo> getUserOrgInfo() {
        return null;
    }
}
