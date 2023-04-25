/*
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE文件。
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 *   3.请保留源码和相关描述文件的项目出处，作者声明等。
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 *   6.若您的项目无法满足以上几点，可申请商业授权
 */

package com.anyilanxin.skillfull.corewebflux.config.properfy;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.GRAY_HEADER_KEY;

import cn.hutool.core.collection.CollectionUtil;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * spring doc配置
 *
 * @author zxiaozhou
 * @date 2020-09-11 03:16
 * @since JDK11
 */
@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = "springdoc")
public class SpringDocCoreWebFluxProperty {
  /** 请求头 */
  private Set<String> headers;

  /** 是否为webflux,用于spring doc打印信息路径判断使用 */
  private boolean webflux;

  /** 版本号 */
  private String version;

  /** swagger请求前缀 */
  private String apiPrefix;

  /** 联系人 */
  private String contactUser = "zxiaozhou";

  /** 联系邮箱 */
  private String contactEmail = "";

  /** 标题 */
  private String title = "";

  /** 是否启用doc */
  @Value("${springdoc.swagger-ui.enabled}")
  private boolean swaggerEnable;

  /** 扫包路径 */
  private String packagesToScan;

  public Set<String> getHeaders() {
    if (CollectionUtil.isEmpty(headers)) {
      headers = new HashSet<>();
    }
    headers.add("Authorization");
    // 灰度信息key
    headers.add(GRAY_HEADER_KEY);
    return headers;
  }
}
