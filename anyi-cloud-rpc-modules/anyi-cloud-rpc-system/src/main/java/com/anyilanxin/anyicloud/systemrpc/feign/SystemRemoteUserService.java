/*
 * Copyright (c) 2021-2023 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Zeebe EE、AnYi Cloud EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

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
