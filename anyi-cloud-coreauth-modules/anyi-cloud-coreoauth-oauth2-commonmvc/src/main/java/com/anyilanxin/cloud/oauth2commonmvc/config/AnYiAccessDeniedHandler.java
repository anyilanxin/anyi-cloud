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

package com.anyilanxin.cloud.oauth2commonmvc.config;

import com.anyilanxin.cloud.corecommon.constant.AnYiResultStatus;
import com.anyilanxin.cloud.oauth2common.constant.AnYiSecurityConstants;
import com.anyilanxin.cloud.oauth2common.enums.DataFormatType;
import com.anyilanxin.cloud.oauth2common.model.DataTypeModel;
import com.anyilanxin.cloud.oauth2commonmvc.utils.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.anyilanxin.cloud.corecommon.utils.AnYiI18nUtil.getLocalMessage;

/**
 * 授权异常
 *
 * @author zxh
 * @date 2022-03-02 14:36
 * @since 1.0.0
 */
@Slf4j
@Component
public class AnYiAccessDeniedHandler implements AccessDeniedHandler {
    private final static Map<String, String> LOCAL = new HashMap<>(64);

    static {
        LOCAL.put("Access is denied", "CustomAccessDeniedHandler.accessIsDenied");
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        log.error("------------AnYiAccessDeniedHandler------------>handle:异常消息:\n{}", accessDeniedException.getLocalizedMessage());
        DataTypeModel dataTypeModel = new DataTypeModel();
        Object dataType = request.getAttribute(AnYiSecurityConstants.DATA_FORMAT);
        if (Objects.nonNull(dataType) && dataType instanceof DataTypeModel typeModel) {
            dataTypeModel = typeModel;
        }
        if (dataTypeModel.getType() == DataFormatType.NORMAL) {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("error", accessDeniedException.getMessage());
            ResponseUtils.writeResult(response, parameters, HttpStatus.FORBIDDEN);
            return;
        }
        ResponseUtils.writeResult(response, getLocalMessage(LOCAL, accessDeniedException.getMessage()), AnYiResultStatus.ACCESS_DENIED);
    }
}