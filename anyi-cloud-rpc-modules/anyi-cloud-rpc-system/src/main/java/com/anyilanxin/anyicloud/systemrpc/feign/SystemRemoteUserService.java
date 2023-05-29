

package com.anyilanxin.anyicloud.systemrpc.feign;

import com.anyilanxin.anyicloud.corecommon.base.Result;
import com.anyilanxin.anyicloud.corecommon.constant.ServiceConstant;
import com.anyilanxin.anyicloud.corecommon.feign.FeignFallback;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.anyicloud.systemrpc.model.SimpleUserModel;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 系统服务feign接口
 *
 * @author zxh
 * @date 2022-02-12 21:54
 * @since 1.0.0
 */
@FeignClient(value = ServiceConstant.SYSTEM_SERVICE, path = ServiceConstant.SYSTEM_SERVICE_PATH, fallbackFactory = FeignFallback.class)
public interface SystemRemoteUserService {

    /**
     * 根据多个用户id查询用户信息
     *
     * @param userIds ${@link List <String>} 用户id
     * @return Result<List < SystemSimpleUserModel>> ${@link Result<List<  SimpleUserModel  >>}
     * @author zxh
     * @date 2020-09-12 17:13
     */
    @PostMapping("/rbac-user/select/list")
    Result<List<SimpleUserModel>> getUserListByIds(@RequestBody List<String> userIds);


    /**
     * 根据用户id查询用户信息
     *
     * @param userId ${@link String} 用户id
     * @return Result<SystemSimpleUserModel> ${@link Result< SimpleUserModel >}
     * @author zxh
     * @date 2020-09-12 17:13
     */
    @GetMapping("/rbac-user/select/one/{userId}")
    Result<SimpleUserModel> getUserById(@PathVariable(required = false) @PathNotBlankOrNull(message = "用户id不能为空") String userId);


    /**
     * 更具真实姓名模糊查询用户信息
     *
     * @param realName ${@link String} 用户真实姓名
     * @return Result<List < SystemSimpleUserModel>> ${@link Result<List< SimpleUserModel >>}
     * @author zxh
     * @date 2020-09-12 17:13
     */
    @GetMapping("/rbac-user/select/list/real-name")
    Result<List<SimpleUserModel>> getUserByRealName(@RequestParam String realName);
}
