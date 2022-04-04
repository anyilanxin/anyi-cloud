// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.security.login.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.zxiaozhou.skillfull.auth.core.constant.impl.PermissionType;
import indi.zxiaozhou.skillfull.auth.modules.common.entity.CommonSystemEntity;
import indi.zxiaozhou.skillfull.auth.modules.common.mapper.CommonSystemMapper;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacPermissionEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacRolePermissionEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacUserEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.mapper.RbacCorrelateUserMapper;
import indi.zxiaozhou.skillfull.auth.modules.rbac.mapper.RbacPermissionMapper;
import indi.zxiaozhou.skillfull.auth.modules.rbac.mapper.RbacRolePermissionMapper;
import indi.zxiaozhou.skillfull.auth.modules.rbac.mapper.RbacUserMapper;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacUserRouterDto;
import indi.zxiaozhou.skillfull.auth.security.login.controller.vo.*;
import indi.zxiaozhou.skillfull.auth.security.login.service.IWebLoginUserCenterService;
import indi.zxiaozhou.skillfull.auth.security.login.service.dto.UserInfoDto;
import indi.zxiaozhou.skillfull.auth.security.login.service.mapstruct.*;
import indi.zxiaozhou.skillfull.auth.security.token.TokenStore;
import indi.zxiaozhou.skillfull.auth.security.validate.CheckDto;
import indi.zxiaozhou.skillfull.auth.security.validate.CheckModel;
import indi.zxiaozhou.skillfull.auth.security.validate.IValidate;
import indi.zxiaozhou.skillfull.auth.security.validate.impl.SmsValidate;
import indi.zxiaozhou.skillfull.auth.utils.CryptAuthUtils;
import indi.zxiaozhou.skillfull.corecommon.base.model.auth.ActionBackendModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.auth.LoginRouteModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.auth.LoginRouteTreeModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.auth.RoleModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.common.ActionModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.common.RouteMetaModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.common.RouteTagModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginUserInfoModel;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.constant.impl.SysBaseType;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import indi.zxiaozhou.skillfull.corecommon.utils.CoreCommonUtils;
import indi.zxiaozhou.skillfull.corecommon.utils.tree.TreeToolUtils;
import indi.zxiaozhou.skillfull.coremvc.utils.ContextHolderUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户信息相关
 *
 * @author zxiaozhou
 * @date 2021-01-10 16:37
 * @since JDK11
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class WebLoginUserCenterServiceImpl extends ServiceImpl<RbacUserMapper, RbacUserEntity> implements IWebLoginUserCenterService {
    private final TokenStore tokenStore;
    private static final String CHANGE_PHONE_CHECK_OLD_SMS_RESULT = "CHANGE_PHONE_CHECK_OLD_SMS_RESULT_";
    private IValidate validate;
    private final ChangeUserMap userMap;
    private final UserRouterMap routerMap;
    private final UserRouterPermissionMap routerPermissionMap;
    private final RbacCorrelateUserMapper correlateUserMapper;
    private final UserRouterTreeRouterMap treeRouterMap;
    private final UserRouterActionMap actionMap;
    private final RbacUserMapper rbacUserMapper;
    private final RbacPermissionMapper permissionMapper;
    private final UserInfoMap userInfoMap;
    private final RbacRolePermissionMapper rbacRolePermissionMapper;
    private final UserInfoToDtoMap userInfoToDtoMap;
    private final CommonSystemMapper systemMapper;
    private final RedisTemplate<String, Object> redisTemplate;


    @Autowired
    public void setValidate(SmsValidate validate) {
        this.validate = validate;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void changePassword(ChangePasswordVo vo) throws RuntimeException {
        String userId = ContextHolderUtils.getUserId();
        RbacUserEntity byId = this.getById(userId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.VERIFICATION_FAILED, "用户信息不存在或已被删除");
        }
        // 判断密码是否正确
        if (!CryptAuthUtils.matches(vo.getOldPassword(), byId.getSalt(), byId.getPassword())) {
            throw new ResponseException(Status.VERIFICATION_FAILED, "原密码不正确,请确定原密码");
        }
        // 修改密码
        CryptAuthUtils.PasswordInfo passwordInfo = CryptAuthUtils.getPasswordInfo(vo.getNewPassword());
        RbacUserEntity entity = new RbacUserEntity();
        entity.setUserId(userId);
        entity.setIsInitialPassword(0);
        entity.setPassword(passwordInfo.getEncodedPassword());
        entity.setSalt(passwordInfo.getSalt());
        boolean b = this.updateById(entity);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "修改密码失败");
        }
        // 清空用户所有登录信息
//        tokenStore.removeDataLike(USER_LOGIN_PREFIX + userId + "*");
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void changeUserInfo(ChangeUserInfoVo vo) throws RuntimeException {
        String userId = ContextHolderUtils.getUserId();
        RbacUserEntity byId = this.getById(userId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "用户信息不存在或已被删除");
        }
        RbacUserEntity entity = userMap.bToA(vo);
        entity.setUserId(byId.getUserId());
        boolean b = this.updateById(entity);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新用户信息失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void findPassword(FindPasswordVo vo) throws RuntimeException {
        // 验证短信验证码是否正确
        CheckDto checkDto = validate.checkVerification(new CheckModel(vo.getPhone(), vo.getCode()));
        if (!checkDto.isResult()) {
            throw new ResponseException(Status.VERIFICATION_FAILED, checkDto.getMsg());
        }
        // 查看用户手机号码信息
        LambdaQueryWrapper<RbacUserEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RbacUserEntity::getPhone, vo.getPhone());
        RbacUserEntity one = this.getOne(lambdaQueryWrapper);
        if (Objects.isNull(one)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "当前手机号关联的用户信息不存在");
        }
        RbacUserEntity entity = new RbacUserEntity();
        entity.setUserId(one.getUserId());
        CryptAuthUtils.PasswordInfo passwordInfo = CryptAuthUtils.getPasswordInfo(vo.getPassword());
        entity.setSalt(passwordInfo.getSalt());
        entity.setIsInitialPassword(0);
        entity.setPassword(passwordInfo.getEncodedPassword());
        boolean b = this.updateById(entity);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "重置密码失败，请稍后再试");
        }
    }


    @Override
    public void checkOldUserPhoneSms(CheckOldUserPhoneVo vo) throws RuntimeException {
        // 验证短信验证码是否正确
        CheckDto checkDto = validate.checkVerification(new CheckModel(vo.getOldPhone(), vo.getCode()));
        if (!checkDto.isResult()) {
            throw new ResponseException(Status.VERIFICATION_FAILED, checkDto.getMsg());
        }
        // 保存检测结果
        redisTemplate.opsForValue().set(CHANGE_PHONE_CHECK_OLD_SMS_RESULT + vo.getOldPhone(), 1, 60 * 30);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void changeUserPhone(ChangeUserPhoneVo vo) throws RuntimeException {
        // 验证旧手机验证码是否验证通过
        Object data = redisTemplate.opsForValue().get(CHANGE_PHONE_CHECK_OLD_SMS_RESULT + vo.getOldPhone());
        if (Objects.isNull(data)) {
            throw new ResponseException(Status.VERIFICATION_FAILED, "旧手机号码验证码验证未通过或未验证,请返回上一步");
        }
        // 验证新手机短信验证码
        CheckDto checkDto = validate.checkVerification(new CheckModel(vo.getPhone(), vo.getCode()));
        if (!checkDto.isResult()) {
            throw new ResponseException(Status.VERIFICATION_FAILED, checkDto.getMsg());
        }
        // 更新用户信息
        RbacUserEntity byId = this.getById(ContextHolderUtils.getUserId());
        if (StringUtils.isBlank(byId.getPhone())) {
            throw new ResponseException(Status.VERIFICATION_FAILED, "当前用户未绑定手机号码,请先绑定");
        }
        RbacUserEntity entity = new RbacUserEntity();
        entity.setUserId(byId.getUserId());
        entity.setPhone(vo.getPhone());
        boolean b = this.updateById(entity);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "修改手机号码失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void bindUserPhone(BindUserPhoneVo vo) throws RuntimeException {
        // 验证新手机短信验证码
        CheckDto checkDto = validate.checkVerification(new CheckModel(vo.getPhone(), vo.getCode()));
        if (!checkDto.isResult()) {
            throw new ResponseException(Status.VERIFICATION_FAILED, checkDto.getMsg());
        }
        RbacUserEntity entity = new RbacUserEntity();
        entity.setUserId(ContextHolderUtils.getUserId());
        entity.setPhone(vo.getPhone());
        boolean b = this.updateById(entity);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "绑定手机号码失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public LoginUserInfoModel getLoinUserInfo(RbacUserEntity rbacUserEntity) throws RuntimeException {
        LoginUserInfoModel loginUserInfoModel = userInfoMap.bToA(rbacUserEntity);
        // 获取当前用户所有后台按钮权限
        List<RbacUserRouterDto> userRouters = getUserRouter(null, PermissionType.BUTTON.getType(), rbacUserEntity.getUserId());
        if (CollectionUtil.isNotEmpty(userRouters)) {
            // 过滤掉不校验后端的信息
            List<RbacUserRouterDto> collect = userRouters.stream()
                    .filter(v -> v.getActionType() != 1).
                    collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(collect)) {
                List<ActionModel> actionModels = new ArrayList<>(collect.size());
                collect.forEach(v -> {
                    if (StringUtils.isNotBlank(v.getActions())) {
                        ActionModel actionModel = actionMap.aToB(v);
                        actionModel.setPath(CoreCommonUtils.getUri(v.getActionUri()));
                        actionModel.setActionSet(new HashSet<>(Arrays.asList(v.getActions().split(","))));
                        actionModel.setDescribe(v.getMetaTitle());
                        actionModel.setServiceId(v.getServiceId());
                        if (v.isActionLimitMethod() && StringUtils.isNotBlank(v.getActionMethods())) {
                            actionModel.setActionMethodSet(new HashSet<>(Arrays.asList(v.getActionMethods().split(","))));
                        }
                        actionModels.add(actionModel);
                    }
                });
                if (CollectionUtil.isNotEmpty(actionModels)) {
                    Set<String> serviceIds = actionModels.stream().map(ActionModel::getServiceId).collect(Collectors.toSet());
                    Map<String, Map<String, Set<ActionModel>>> userActions = new HashMap<>(serviceIds.size());
                    serviceIds.forEach(v -> {
                        List<ActionModel> systemActions = actionModels.stream().filter(sv -> v.equals(sv.getServiceId())).collect(Collectors.toList());
                        Map<String, Set<ActionModel>> actionModelMap = new HashMap<>();
                        if (CollectionUtil.isNotEmpty(systemActions)) {
                            systemActions.forEach(ssv -> {
                                Set<ActionModel> actionModelSet = actionModelMap.get(ssv.getPath());
                                if (CollectionUtil.isEmpty(actionModelSet)) {
                                    actionModelSet = new HashSet<>(8);
                                }
                                actionModelSet.add(ssv);
                                actionModelMap.put(ssv.getPath(), actionModelSet);
                            });
                        }
                        if (CollectionUtil.isNotEmpty(actionModelMap)) {
                            userActions.put(v, actionModelMap);
                        }
                    });
                    if (CollectionUtil.isNotEmpty(userRouters)) {
                        ActionBackendModel backendModel = new ActionBackendModel();
                        backendModel.setUserActions(userActions);
                        loginUserInfoModel.setActionInfo(backendModel);
                    }
                }
            }
        }
        // 获取当前用户所有角色信息
        Set<RoleModel> userRoleById = correlateUserMapper.getUserRoleById(rbacUserEntity.getUserId());
        loginUserInfoModel.setRoleInfo(userRoleById);
        // 获取当前用户代理人信息
        return loginUserInfoModel;
    }


    /**
     * 获取权限信息
     *
     * @param systemCodes ${@link String} 系统编码
     * @param type        ${@link Integer} 类型
     * @param userId      ${@link String} 用户id
     * @return List<RbacUserRouterDto> ${@link List<RbacUserRouterDto>}
     * @author zxiaozhou
     * @date 2021-07-26 15:35
     */
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    List<RbacUserRouterDto> getUserRouter(String systemCodes, Integer type, String userId) {
        // 获取所有系统id
        Set<String> systemIds = new HashSet<>(8);
        if (StringUtils.isNotBlank(systemCodes)) {
            LambdaQueryWrapper<CommonSystemEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            if (StringUtils.isNotBlank(systemCodes)) {
                lambdaQueryWrapper.in(CommonSystemEntity::getSystemCode, Arrays.asList(systemCodes.split(",")));
            }
            List<CommonSystemEntity> commonSystemEntities = systemMapper.selectList(lambdaQueryWrapper);
            if (CollectionUtil.isNotEmpty(commonSystemEntities)) {
                commonSystemEntities.forEach(v -> systemIds.add(v.getSystemId()));
            }
        }
        // 判断用户是否为超级用户,如果是直接查询所有路由信息
        Set<RoleModel> userRoleById = correlateUserMapper.getUserRoleById(userId);
        if (CollectionUtil.isEmpty(userRoleById)) {
            return new ArrayList<>();
        }
        boolean isSuperRole = false;
        if (CollectionUtil.isNotEmpty(userRoleById)) {
            long count = userRoleById.stream().filter(v -> SysBaseType.SUPER_ROLE.getType().equals(v.getRoleCode())).count();
            isSuperRole = count > 0;
        }
        Set<String> roleIds = new HashSet<>();
        userRoleById.forEach(v -> roleIds.add(v.getRoleId()));
        // 查询权限
        List<RbacUserRouterDto> userRouters = new ArrayList<>();
        if (isSuperRole) {
            LambdaQueryWrapper<RbacPermissionEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(RbacPermissionEntity::getPermissionStatus, 1);
            if (Objects.nonNull(type)) {
                lambdaQueryWrapper = lambdaQueryWrapper.eq(RbacPermissionEntity::getPermissionType, type);
            }
            if (CollectionUtil.isNotEmpty(systemIds)) {
                lambdaQueryWrapper = lambdaQueryWrapper.in(RbacPermissionEntity::getSystemId, systemIds);
            }
            List<RbacPermissionEntity> rbacPermissionEntities = permissionMapper.selectList(lambdaQueryWrapper);
            userRouters = routerPermissionMap.bToA(rbacPermissionEntities);
        } else {
            LambdaQueryWrapper<RbacRolePermissionEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.in(RbacRolePermissionEntity::getRoleId, roleIds);
            List<RbacRolePermissionEntity> rolePermissionEntities = rbacRolePermissionMapper.selectList(lambdaQueryWrapper);
            Set<String> permissionIds = new HashSet<>(rolePermissionEntities.size());
            if (CollectionUtil.isNotEmpty(rolePermissionEntities)) {
                rolePermissionEntities.forEach(v -> permissionIds.add(v.getPermissionId()));
            }
            if (CollectionUtil.isNotEmpty(permissionIds)) {
                LambdaQueryWrapper<RbacPermissionEntity> permissionLambdaQueryWrapper = new LambdaQueryWrapper<>();
                permissionLambdaQueryWrapper.in(RbacPermissionEntity::getPermissionId, permissionIds);
                if (Objects.nonNull(type)) {
                    permissionLambdaQueryWrapper = permissionLambdaQueryWrapper.eq(RbacPermissionEntity::getPermissionType, type);
                }
                if (CollectionUtil.isNotEmpty(systemIds)) {
                    permissionLambdaQueryWrapper = permissionLambdaQueryWrapper.in(RbacPermissionEntity::getSystemId, systemIds);
                }
                List<RbacPermissionEntity> rbacPermissionEntities = permissionMapper.selectList(permissionLambdaQueryWrapper);
                userRouters = routerPermissionMap.bToA(rbacPermissionEntities);
            }
        }
        return userRouters;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<LoginRouteModel> getRouterInfo(String userId, String systemCodes, String orgId) throws RuntimeException {
        List<RbacUserRouterDto> userRouters = getUserRouter(systemCodes, null, userId);
        log.info("------------WebLoginUserCenterServiceImpl---1111111111111111111111111--------->getRouterInfo:\n{}", JSONObject.toJSONString(userRouters));
        // 组装数据
        if (CollectionUtil.isNotEmpty(userRouters)) {
            List<LoginRouteModel> routeModels = new ArrayList<>(userRouters.size());
            Map<String, Set<RbacUserRouterDto>> actionMaps = new HashMap<>(userRouters.size());
            userRouters.forEach(v -> {
                if (v.getPermissionType() == PermissionType.BUTTON.getType()) {
                    Set<RbacUserRouterDto> userRouterDtos = actionMaps.get(v.getParentId());
                    if (CollectionUtil.isEmpty(userRouterDtos)) {
                        userRouterDtos = new HashSet<>();
                    }
                    userRouterDtos.add(v);
                    if (CollectionUtil.isNotEmpty(userRouterDtos)) {
                        actionMaps.put(v.getParentId(), userRouterDtos);
                    }
                } else {
                    LoginRouteModel model = routerMap.dToE(v);
                    RouteMetaModel routeMetaModel = routerMap.dToV(v);
                    if (v.isShowTag()) {
                        RouteTagModel tagModel = new RouteTagModel();
                        tagModel.setContent(v.getContent());
                        tagModel.setDot(v.getDot());
                        tagModel.setType(v.getType());
                        routeMetaModel.setTag(tagModel);
                    }
                    routeMetaModel.setTitle(v.getMetaTitle());
                    model.setMeta(routeMetaModel);
                    model.setName(v.getPathName());
                    routeModels.add(model);
                }
            });
            if (CollectionUtil.isNotEmpty(actionMaps) && CollectionUtil.isNotEmpty(routeModels)) {
                routeModels.forEach(v -> {
                    RouteMetaModel meta = v.getMeta();
                    if (Objects.nonNull(meta)) {
                        Set<RbacUserRouterDto> userRouterDtos = actionMaps.get(v.getPermissionId());
                        if (CollectionUtil.isNotEmpty(userRouterDtos)) {
                            Set<String> actions = new HashSet<>(16);
                            userRouterDtos.forEach(sv -> {
                                // 只保留前端型按钮权限
                                if (StringUtils.isNotBlank(sv.getActions()) && sv.getActionType() != 2) {
                                    Set<String> action = new HashSet<>(Arrays.asList(sv.getActions().split(",")));
                                    actions.addAll(action);
                                }
                            });
                            if (CollectionUtil.isNotEmpty(actions)) {
                                meta.setActionSet(actions);
                            }
                        }
                    }
                });
            }
            Collections.sort(routeModels);
            return routeModels;

        }
        return Collections.emptyList();
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<LoginRouteTreeModel> getRouterTreeInfo(String userId, String systemCodes, String orgId) throws RuntimeException {
        List<LoginRouteModel> routerInfo = this.getRouterInfo(userId, systemCodes, orgId);
        log.info("------------WebLoginUserCenterServiceImpl------------>getRouterTreeInfo:{}", routerInfo);
        if (CollectionUtil.isNotEmpty(routerInfo)) {
            List<LoginRouteTreeModel> routeTreeModels = treeRouterMap.bToA(routerInfo);
            // 构建树形
            List<LoginRouteTreeModel> rootRouteModels = routeTreeModels.stream()
                    .filter(v -> StringUtils.isBlank(v.getParentId()))
                    .collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(rootRouteModels)) {
                TreeToolUtils<LoginRouteTreeModel> utils = new TreeToolUtils<>(rootRouteModels, routeTreeModels, new TreeToolUtils.TreeId<>() {
                    @Override
                    public String getId(LoginRouteTreeModel loginRouteTreeModel) {
                        return loginRouteTreeModel.getPermissionId();
                    }

                    @Override
                    public String getParentId(LoginRouteTreeModel loginRouteTreeModel) {
                        return loginRouteTreeModel.getParentId();
                    }
                });
                List<LoginRouteTreeModel> tree = utils.getTree();
                Collections.sort(tree);
                return tree;
            }
        }
        return Collections.emptyList();
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public UserInfoDto getUserInfo() throws RuntimeException {
        // 查询用户信息
        RbacUserEntity rbacUserEntity = rbacUserMapper.selectById(ContextHolderUtils.getUserAndAuthModel().getUserId());
        LoginUserInfoModel permissionInfo = this.getLoinUserInfo(rbacUserEntity);
        // 更新用户缓存
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        tokenStore.refreshAuthenticationInfo(authentication, permissionInfo);
        // 转换为前端需要的信息
        UserInfoDto userInfoDto = userInfoToDtoMap.aToB(permissionInfo);
        // 获取角色编码
        Set<RoleModel> roleInfo = permissionInfo.getRoleInfo();
        if (CollectionUtil.isNotEmpty(roleInfo)) {
            Set<String> roleCodes = new HashSet<>(roleInfo.size());
            roleInfo.forEach(v -> roleCodes.add(v.getRoleCode()));
            userInfoDto.setRoleCodes(roleCodes);
        }
        return userInfoDto;
    }
}
