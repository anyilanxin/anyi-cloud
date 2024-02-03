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

package com.anyilanxin.anyicloud.message.modules.message.service.impl;

import com.anyilanxin.anyicloud.corecommon.base.AnYiResult;
import com.anyilanxin.anyicloud.message.modules.message.service.ISocketMenuService;
import com.anyilanxin.anyicloud.message.modules.message.service.dto.MenuTagSubscribeDto;
import com.anyilanxin.anyicloud.message.modules.message.service.mapstruct.MenuTagInfoModelCopyMap;
import com.anyilanxin.anyicloud.message.modules.message.service.mapstruct.MenuTagModelCopyMap;
import com.anyilanxin.anyicloud.messageadapter.model.MenuTagModel;
import com.anyilanxin.anyicloud.processadapter.model.MenuTagInfoModel;
import com.anyilanxin.anyicloud.processrpcadapter.rpc.ProcessRemoteRpcBusinessStatis;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单tag socket处理
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-03-29 05:23:43
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SocketMenuServiceImpl implements ISocketMenuService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ProcessRemoteRpcBusinessStatis rpcBusinessStatis;
    private final MenuTagModelCopyMap copyMap;
    private final MenuTagInfoModelCopyMap infoModelCopyMap;

    @Override
    public List<MenuTagSubscribeDto> subscribe(Principal principal) {
        var userId = principal.getName();
        List<MenuTagSubscribeDto> result = new ArrayList<>();
        try {
            AnYiResult<List<MenuTagInfoModel>> listResult = rpcBusinessStatis.selectUserMenuTag(userId);
            if (listResult.isSuccess() && CollUtil.isNotEmpty(listResult.getData())) {
                result = infoModelCopyMap.aToB(listResult.getData());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("------------------获取用户流程tag失败------>subscribe:\n{}", e.getMessage());
        }
        result.forEach(v -> {
            if (StringUtils.isBlank(v.getType())) {
                v.setType("primary");
            }
        });
        return result;
    }


    @Override
    public void updateMenuTag(MenuTagModel model) {
        if (StringUtils.isBlank(model.getType())) {
            model.setType("primary");
        }
        simpMessagingTemplate.convertAndSendToUser(model.getUserId(), "/queue/menu-tag-update", List.of(copyMap.aToB(model)));
    }
}