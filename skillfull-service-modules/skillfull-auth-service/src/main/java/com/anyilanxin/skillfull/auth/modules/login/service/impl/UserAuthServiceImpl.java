// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.auth.modules.login.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.skillfull.auth.modules.login.mapper.UserAuthMapper;
import com.anyilanxin.skillfull.auth.modules.login.service.IUserAuthService;
import com.anyilanxin.skillfull.auth.modules.login.service.dto.RbacOrgUserDto;
import com.anyilanxin.skillfull.auth.modules.login.service.dto.RbacUserDto;
import com.anyilanxin.skillfull.auth.modules.login.service.mapstruct.UserAuthCopyMap;
import com.anyilanxin.skillfull.corecommon.constant.SysBaseConstant;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.corecommon.model.auth.OrgSimpleInfo;
import com.anyilanxin.skillfull.corecommon.model.auth.RoleInfo;
import com.anyilanxin.skillfull.corecommon.model.system.UserAndResourceAuthModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
public class UserAuthServiceImpl implements IUserAuthService {
    private final UserAuthCopyMap authCopyMap;
    private final UserAuthMapper userAuthMapper;

    @Override
    public UserAndResourceAuthModel getUserByOpenId(String openId) {
        RbacUserDto entity = userAuthMapper.selectByOpenId(openId);
        return getUserInfo(entity, null, true);
    }


    @Override
    public UserAndResourceAuthModel getUserByAccountPhone(String userName) {
        RbacUserDto entity = userAuthMapper.selectByPhoneOrAccount(userName);
        return getUserInfo(entity, null, true);
    }


    @Override
    public UserAndResourceAuthModel getUserByPhone(String phone) {
        RbacUserDto entity = userAuthMapper.selectByPhone(phone);
        return getUserInfo(entity, null, true);
    }


    /**
     * 处理机构相关
     *
     * @param userAndResourceAuthModel
     * @param orgId
     * @param roleInfos
     * @author zxiaozhou
     * @date 2022-07-12 18:39
     */
    void handleOrgInfo(UserAndResourceAuthModel userAndResourceAuthModel, String orgId, Set<RoleInfo> roleInfos) {
        // 如果机构未空，则选择最近一个，并且设置用户最近登录机构信息
        if (StringUtils.isBlank(orgId)) {
            List<RbacOrgUserDto> rbacOrgUserDtos = userAuthMapper.selectUserOrgListByUserId(userAndResourceAuthModel.getUserId());
            if (CollUtil.isNotEmpty(rbacOrgUserDtos)) {
                orgId = rbacOrgUserDtos.get(0).getOrgId();
                userAuthMapper.updateLoginOrgId(userAndResourceAuthModel.getUserId(), orgId);
            }
        }
        if (StringUtils.isNotBlank(orgId)) {
            OrgSimpleInfo orgDto = userAuthMapper.selectOrgInfoById(orgId);
            if (Objects.nonNull(orgDto)) {
                userAndResourceAuthModel.setCurrentOrgCode(orgDto.getOrgCode());
                userAndResourceAuthModel.setCurrentOrgId(orgDto.getOrgId());
                userAndResourceAuthModel.setCurrentOrgName(orgDto.getOrgName());
                userAndResourceAuthModel.setCurrentAreaCode(orgDto.getAreaCode());
                userAndResourceAuthModel.setCurrentAreaName(orgDto.getAreaCodeName());
                userAndResourceAuthModel.setOrgInfo(orgDto);
                // 获取机构授权角色
                Set<RoleInfo> orgRoleInfos = userAuthMapper.selectByUserIdAndOrgId(userAndResourceAuthModel.getUserId(), orgId);
                if (CollUtil.isNotEmpty(orgRoleInfos)) {
                    roleInfos.addAll(orgRoleInfos);
                }
            }
        }
    }


    @Override
    public UserAndResourceAuthModel getUserInfo(RbacUserDto entity, String orgId, boolean havePassword) {
        if (Objects.isNull(entity)) {
            throw new ResponseException("用户信息不存在");
        }
        if (StringUtils.isBlank(orgId)) {
            orgId = entity.getCurrentOrgId();
        }
        UserAndResourceAuthModel userAndResourceAuthModel = authCopyMap.bToA(entity);
        // 获取用户角色信息
        Set<RoleInfo> roleInfos = userAuthMapper.selectByUserId(entity.getUserId(), SysBaseConstant.SUPER_ROLE);
        if (CollUtil.isEmpty(roleInfos)) {
            roleInfos = new HashSet<>(64);
        } else {
            for (RoleInfo roleInfo : roleInfos) {
                if (SysBaseConstant.SUPER_ROLE.equals(roleInfo.getRoleCode())) {
                    userAndResourceAuthModel.setSuperAdmin(true);
                    break;
                }
            }
        }
        // 处理机构相关
        handleOrgInfo(userAndResourceAuthModel, orgId, roleInfos);
        // 如果不需要保留密码则去掉
        if (!havePassword) {
            userAndResourceAuthModel.setPassword(null);
            userAndResourceAuthModel.setSalt(null);
        }
        // 处理角色
        Set<String> roleCodes = new HashSet<>(64);
        Set<String> roleIds = new HashSet<>(64);
        roleInfos.forEach(v -> {
            roleCodes.add(v.getRoleCode());
            roleIds.add(v.getRoleId());
        });
        userAndResourceAuthModel.setRoleInfos(roleInfos);
        userAndResourceAuthModel.setRoleCodes(roleCodes);
        userAndResourceAuthModel.setRoleIds(roleIds);
        return userAndResourceAuthModel;
    }
}
