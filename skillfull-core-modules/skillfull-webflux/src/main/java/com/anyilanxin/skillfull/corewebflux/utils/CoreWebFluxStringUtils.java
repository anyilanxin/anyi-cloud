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
package com.anyilanxin.skillfull.corewebflux.utils;

import cn.hutool.core.lang.Snowflake;
import com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
* core mvc字符串处理工具类
*
* @author zxiaozhou
* @date 2019-04-01 16:54
* @since JDK11
*/
@Slf4j
@RequiredArgsConstructor
@Component
public class CoreWebFluxStringUtils {
    private static CoreWebFluxStringUtils util;
    private final Snowflake snowflake;

    @PostConstruct
    private void init() {
        util = this;
    }

    /**
    * 获取32位uuid(使用ThreadLocalRandom提高性能)
    *
    * @return String ${@link String}
    * @author zxiaozhou
    * @date 2019-04-01 17:02
    */
    public static String get32UUId() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return new UUID(random.nextLong(), random.nextLong())
                .toString()
                .replace(CommonCoreConstant.DASH, CommonCoreConstant.EMPTY);
    }

    /**
    * 获取有序唯一id
    *
    * @return String ${@link String} id
    * @author zxiaozhou
    * @date 2020-08-28 16:23
    */
    public static String getSnowflakeId() {
        return util.snowflake.nextIdStr();
    }
}
