

package com.anyilanxin.anyicloud.systemrpc.feign;

import com.anyilanxin.anyicloud.corecommon.base.Result;
import com.anyilanxin.anyicloud.corecommon.constant.ServiceConstant;
import com.anyilanxin.anyicloud.corecommon.feign.FeignFallback;
import com.anyilanxin.anyicloud.corecommon.model.auth.RoleInfo;
import com.anyilanxin.anyicloud.systemrpc.model.SimpleUserModel;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 系统服务feign接口
 *
 * @author zxh
 * @date 2022-02-12 21:54
 * @since 1.0.0
 */
@FeignClient(value = ServiceConstant.SYSTEM_SERVICE, path = ServiceConstant.SYSTEM_SERVICE_PATH, fallbackFactory = FeignFallback.class)
public interface SystemRemoteRoleService {
    /**
     * 根据多个用户组id查询用户组信息
     *
     * @param roleIds ${@link List<String>} 角色id
     * @return Result<List < ProcessRoleModel>> ${@link Result<List< SimpleUserModel >>}
     * @author zxh
     * @date 2020-09-12 17:13
     */
    @PostMapping("/rbac-role/select/list/role-id")
    Result<List<RoleInfo>> getRoleListByIds(@RequestBody List<String> roleIds);
}
