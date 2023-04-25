/**
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package com.anyilanxin.skillfull.corecommon.config.properties;

import com.anyilanxin.skillfull.corecommon.constant.impl.AuthorizedGrantTypes;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * skillfull oauth2配置
 *
 * @author zxiaozhou
 * @date 2022-08-10 02:44
 * @since JDK11
 */
@Component
@ConfigurationProperties(prefix = "skillfull.oauth")
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class SkillfullOauthProperty {

    /**
     * 授权类型
     */
    private AuthorizedGrantTypes grantType = AuthorizedGrantTypes.CLIENT_CREDENTIALS;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 客户端密码
     */
    private String clientSecret;

    /**
     * 授权范围
     */
    private String scope;
}
