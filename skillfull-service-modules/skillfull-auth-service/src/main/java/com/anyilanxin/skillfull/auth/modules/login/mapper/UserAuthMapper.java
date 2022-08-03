package com.anyilanxin.skillfull.auth.modules.login.mapper;

import com.anyilanxin.skillfull.auth.modules.login.service.dto.RbacOrgUserDto;
import com.anyilanxin.skillfull.auth.modules.login.service.dto.RbacUserDto;
import com.anyilanxin.skillfull.corecommon.auth.model.OrgSimpleInfo;
import com.anyilanxin.skillfull.corecommon.auth.model.RoleInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 用户授权mapper
 *
 * @author zhou
 * @date 2022-07-23 18:00
 * @since JDK11
 */
public interface UserAuthMapper {


    /**
     * 通过openId查询用户信息
     *
     * @param openId
     * @return RbacUserDto
     * @author zxiaozhou
     * @date 2022-07-23 18:33
     */
    RbacUserDto selectByOpenId(@Param("openId") String openId);


    /**
     * 通过账号或者电话号码查询用户
     *
     * @param userName
     * @return RbacUserDto
     * @author zxiaozhou
     * @date 2022-07-23 18:33
     */
    RbacUserDto selectByPhoneOrAccount(@Param("userName") String userName);


    /**
     * 通过电话号码查询用户信息
     *
     * @param phone
     * @return RbacUserDto
     * @author zxiaozhou
     * @date 2022-07-23 18:33
     */
    RbacUserDto selectByPhone(@Param("phone") String phone);


    /**
     * 通过用户id查询用户信息
     *
     * @param userId
     * @return RbacUserDto
     * @author zxiaozhou
     * @date 2022-07-23 20:10
     */
    RbacUserDto selectUserInfoByUserId(@Param("userId") String userId);


    /**
     * 更新用户登录机构
     *
     * @param userId
     * @param orgId
     * @author zxiaozhou
     * @date 2022-07-23 18:36
     */
    int updateLoginOrgId(@Param("userId") String userId,
                         @Param("orgId") String orgId);


    /**
     * 查询用户关联的机构id
     *
     * @param userId 用户id
     * @return List<RbacOrgUserDto> 查询结果
     * @author zxiaozhou
     * @date 2022-07-02 23:01:20
     */
    List<RbacOrgUserDto> selectUserOrgListByUserId(@Param("userId") String userId);

    /**
     * 查询用户在某个机构下的角色信息
     *
     * @param userId 用户id
     * @param orgId  机构id
     * @return RoleInfo>
     * @author zxiaozhou
     * @date 2022-07-05 00:36
     */
    Set<RoleInfo> selectByUserIdAndOrgId(@Param("userId") String userId, @Param("orgId") String orgId);


    /**
     * 查询用户角色
     *
     * @param userId
     * @return RoleInfo>
     * @author zxiaozhou
     * @date 2022-07-05 00:42
     */
    Set<RoleInfo> selectByUserId(@Param("userId") String userId, @Param("superRoleCode") String superRoleCode);


    /**
     * 通过机构id查询机构信息
     *
     * @param orgId
     * @return OrgSimpleInfo
     * @author zxiaozhou
     * @date 2022-07-23 19:46
     */
    OrgSimpleInfo selectOrgInfoById(@Param("orgId") String orgId);
}
