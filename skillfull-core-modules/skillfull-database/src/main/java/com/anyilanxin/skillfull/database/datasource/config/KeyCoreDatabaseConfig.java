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

package com.anyilanxin.skillfull.database.datasource.config;

import cn.hutool.core.lang.Snowflake;
import com.anyilanxin.skillfull.corecommon.utils.CoreCommonUtils;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * key配置
 *
 * @author zxiaozhou
 * @date 2020-08-28 10:23
 * @since JDK11
 */
@Configuration
@RequiredArgsConstructor
public class KeyCoreDatabaseConfig {
  private final Snowflake snowflake;

  /**
   * 自定义mybatis plus id生成器
   *
   * @return IdentifierGenerator ${@link IdentifierGenerator}
   * @author zxiaozhou
   * @date 2020-08-29 01:34
   */
  @Bean
  @Primary
  public IdentifierGenerator idGenerator() {
    return new CustomIdGenerator(snowflake);
  }

  static class CustomIdGenerator implements IdentifierGenerator {
    private final Snowflake generatorSnowflake;

    public CustomIdGenerator(Snowflake snowflake) {
      this.generatorSnowflake = snowflake;
    }

    @Override
    public Number nextId(Object entity) {
      return generatorSnowflake.nextId();
    }

    @Override
    public String nextUUID(Object entity) {
      return CoreCommonUtils.get32UUId();
    }
  }
}
