// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.systemapi.feign;

import com.anyilanxin.skillfull.corecommon.auth.model.RoleInfo;
import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.constant.ServiceConstant;
import com.anyilanxin.skillfull.corecommon.feign.FeignFallback;
import com.anyilanxin.skillfull.systemapi.model.SimpleUserModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 系统服务feign接口
 *
 * @author zxiaozhou
 * @date 2022-02-12 21:54
 * @since JDK1.8
 */
@FeignClient(value = ServiceConstant.SYSTEM_SERVICE, path = ServiceConstant.SYSTEM_SERVICE_PATH, fallbackFactory = FeignFallback.class)
public interface SystemRemoteRoleService {
    /**
     * 根据多个用户组id查询用户组信息
     *
     * @param roleIds ${@link List<String>} 角色id
     * @return Result<List < ProcessRoleModel>> ${@link Result<List<  SimpleUserModel  >>}
     * @author zxiaozhou
     * @date 2020-09-12 17:13
     */
    @PostMapping("/rbac-role/select/list/role-id")
    Result<List<RoleInfo>> getRoleListByIds(@RequestBody List<String> roleIds);
}
