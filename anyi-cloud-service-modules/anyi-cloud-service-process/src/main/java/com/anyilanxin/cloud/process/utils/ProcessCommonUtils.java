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

package com.anyilanxin.cloud.process.utils;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.anyilanxin.cloud.corecommon.model.auth.AnYiUserInfo;
import com.anyilanxin.cloud.coremvc.utils.AnYiUserContextUtils;
import com.anyilanxin.cloud.coreprocess.emuns.UserTaskState;
import com.anyilanxin.cloud.process.extend.constant.impl.ProcessInstanceState;
import com.anyilanxin.cloud.process.extend.constant.impl.ProcessUserVariableType;
import com.anyilanxin.cloud.process.extend.constant.impl.TaskUserVariableType;
import com.anyilanxin.cloud.process.extend.model.mapstruct.ProcessUserCopyMap;
import com.anyilanxin.cloud.process.modules.manage.service.IProcessCategoryService;
import com.anyilanxin.cloud.process.modules.manage.service.dto.ProcessCategoryDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.el.Expression;
import org.camunda.bpm.engine.impl.el.ExpressionManager;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 流程公共工具类
 *
 * @author zxh
 * @date 2021-12-06 18:10
 * @since 1.0.0
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ProcessCommonUtils {
    private static ProcessCommonUtils utils;
    private final ProcessUserCopyMap userCopyMap;
    private final IProcessCategoryService categoryService;

    /**
     * 获取处理用户信息
     *
     * @return {@link Map }<{@link String }, {@link Object }>
     * @author zxh
     * @date 2022-11-18 15:59:35
     */
    public static Map<String, Object> getTaskUserInfo() {
        Map<String, Object> userInfo = new HashMap<>();
        AnYiUserInfo userAndAuthModel = null;
        try {
            userAndAuthModel = AnYiUserContextUtils.getUserInfo();
        } catch (Exception e) {
            log.error("--------------获取用户信息失败----------getTaskUserInfo--->{}", e.getMessage());
        }
        String realName = Objects.nonNull(userAndAuthModel) ? userAndAuthModel.getRealName() : "";
        String phone = Objects.nonNull(userAndAuthModel) ? userAndAuthModel.getPhone() : "";
        userInfo.put(TaskUserVariableType.REAL_NAME.getVariableKey(), StringUtils.isNotBlank(realName) ? realName : "");
        userInfo.put(TaskUserVariableType.PHONE.getVariableKey(), StringUtils.isNotBlank(phone) ? phone : "");
        userInfo.put(TaskUserVariableType.ALL_USER_INFO_STR.getVariableKey(), JSONObject.toJSONString(utils.userCopyMap.bToA(userAndAuthModel), JSONWriter.Feature.WriteMapNullValue));
        return userInfo;
    }


    /**
     * 获取处理用户信息
     *
     * @return {@link Map }<{@link String }, {@link Object }>
     * @author zxh
     * @date 2022-11-18 15:59:40
     */
    public static Map<String, Object> getProcessUserInfo() {
        Map<String, Object> userInfo = new HashMap<>();
        AnYiUserInfo userAndAuthModel = null;
        try {
            userAndAuthModel = AnYiUserContextUtils.getUserInfo();
        } catch (Exception e) {
            log.error("--------------获取用户信息失败----------getProcessUserInfo--->{}", e.getMessage());
        }
        String realName = Objects.nonNull(userAndAuthModel) ? userAndAuthModel.getRealName() : "";
        String phone = Objects.nonNull(userAndAuthModel) ? userAndAuthModel.getPhone() : "";
        userInfo.put(ProcessUserVariableType.REAL_NAME.getVariableKey(), StringUtils.isNotBlank(realName) ? realName : "");
        userInfo.put(ProcessUserVariableType.PHONE.getVariableKey(), StringUtils.isNotBlank(phone) ? phone : "");
        userInfo.put(ProcessUserVariableType.ALL_USER_INFO_STR.getVariableKey(), JSONObject.toJSONString(utils.userCopyMap.bToA(userAndAuthModel), JSONWriter.Feature.WriteMapNullValue));
        return userInfo;
    }


    /**
     * 根据流程类型编码或者流程类型信息
     *
     * @param processCategory ${@link String}
     * @return ProcessCategoryDto ${@link ProcessCategoryDto}
     * @author zxh
     * @date 2022-03-27 11:13
     */
    public static ProcessCategoryDto getProcessCategory(String processCategory) {
        return utils.categoryService.selectByCode(processCategory);
    }


    /**
     * 自定义删除流程
     */
    public static void customDeleteProcess(ProcessInstanceState instanceState, RuntimeService runtimeService, String processInstanceId, String executionId) {
        runtimeService.setVariable(executionId, instanceState.getStrState(), instanceState.getState());
        runtimeService.deleteProcessInstance(processInstanceId, instanceState.getStrState(), false, false, false);
    }


    /**
     * 自定义删除流程
     */
    public static void customDeleteProcess(UserTaskState taskStatus, RuntimeService runtimeService, String processInstanceId) {
        runtimeService.deleteProcessInstance(processInstanceId, taskStatus.getStatus(), true, false, false);
    }


    /**
     * 解析表达式
     */
    public static String handleExpression(String expressionValue, VariableScope variableScope) {
        if (StringUtils.isBlank(expressionValue)) {
            return "";
        }
        ExpressionManager expressionManager = Context.getProcessEngineConfiguration().getExpressionManager();
        Expression expression = expressionManager.createExpression(expressionValue);
        Object value = expression.getValue(variableScope);
        return Objects.nonNull(value) ? value.toString() : "";
    }


    @PostConstruct
    void init() {
        utils = this;
    }
}
