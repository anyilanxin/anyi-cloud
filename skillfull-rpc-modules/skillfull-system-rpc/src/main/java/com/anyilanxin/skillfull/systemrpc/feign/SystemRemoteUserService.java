// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.systemrpc.feign;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.constant.ServiceConstant;
import com.anyilanxin.skillfull.corecommon.feign.FeignFallback;
import com.anyilanxin.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.skillfull.systemrpc.model.SimpleUserModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统服务feign接口
 *
 * @author zxiaozhou
 * @date 2022-02-12 21:54
 * @since JDK1.8
 */
@FeignClient(value = ServiceConstant.SYSTEM_SERVICE, path = ServiceConstant.SYSTEM_SERVICE_PATH, fallbackFactory = FeignFallback.class)
public interface SystemRemoteUserService {

    /**
     * 根据多个用户id查询用户信息
     *
     * @param userIds ${@link List <String>} 用户id
     * @return Result<List < SystemSimpleUserModel>> ${@link Result<List<  SimpleUserModel  >>}
     * @author zxiaozhou
     * @date 2020-09-12 17:13
     */
    @PostMapping("/rbac-user/select/list")
    Result<List<SimpleUserModel>> getUserListByIds(@RequestBody List<String> userIds);


    /**
     * 根据用户id查询用户信息
     *
     * @param userId ${@link String} 用户id
     * @return Result<SystemSimpleUserModel> ${@link Result< SimpleUserModel >}
     * @author zxiaozhou
     * @date 2020-09-12 17:13
     */
    @GetMapping("/rbac-user/select/one/{userId}")
    Result<SimpleUserModel> getUserById(@PathVariable(required = false) @PathNotBlankOrNull(message = "用户id不能为空") String userId);


    /**
     * 更具真实姓名模糊查询用户信息
     *
     * @param realName ${@link String} 用户真实姓名
     * @return Result<List < SystemSimpleUserModel>> ${@link Result<List<  SimpleUserModel  >>}
     * @author zxiaozhou
     * @date 2020-09-12 17:13
     */
    @GetMapping("/rbac-user/select/list/real-name")
    Result<List<SimpleUserModel>> getUserByRealName(@RequestParam String realName);

}
