/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
package com.anyilanxin.skillfull.auth.modules.login.service.impl;

import com.anyilanxin.skillfull.auth.modules.login.mapper.ClientAuthMapper;
import com.anyilanxin.skillfull.auth.modules.login.service.IClientAuthService;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.corecommon.model.auth.RoleInfo;
import com.anyilanxin.skillfull.corecommon.model.system.ClientAndResourceAuthModel;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
public class ClientAuthServiceImpl implements IClientAuthService {
    private final ClientAuthMapper clientAuthMapper;

    @Override
    public ClientAndResourceAuthModel getByClientId(String clientId) {
        ClientAndResourceAuthModel clientDetailsModel =
                clientAuthMapper.selectClientIdByClientId(clientId);
        if (Objects.isNull(clientDetailsModel)) {
            throw new ResponseException("客户端信息不存在:" + clientId);
        }
        String clientDetailId = clientDetailsModel.getClientDetailId();
        // 获取授权角色
        Set<RoleInfo> clientAuthRole = clientAuthMapper.getClientAuthRole(clientDetailId);
        Set<String> roleIds = new HashSet<>(64);
        Set<String> roleCodes = new HashSet<>(64);
        clientAuthRole.forEach(
                v -> {
                    roleIds.add(v.getRoleId());
                    roleCodes.add(v.getRoleCode());
                });
        clientDetailsModel.setRoleInfos(clientAuthRole);
        clientDetailsModel.setRoleCodes(roleCodes);
        clientDetailsModel.setRoleIds(roleIds);
        return clientDetailsModel;
    }
}
