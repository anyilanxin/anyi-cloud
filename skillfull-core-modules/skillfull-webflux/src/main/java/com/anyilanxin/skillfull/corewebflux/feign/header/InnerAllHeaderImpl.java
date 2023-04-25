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
package com.anyilanxin.skillfull.corewebflux.feign.header;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.skillfull.corecommon.constant.SysBaseConstant;
import com.anyilanxin.skillfull.corecommon.feign.strategy.header.ISetHeaderStrategy;
import com.anyilanxin.skillfull.corewebflux.utils.ServletUtils;
import feign.RequestTemplate;
import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

/**
* @author zxiaozhou
* @date 2021-05-23 01:18
* @since JDK1.8
*/
@Component(value = SysBaseConstant.FEIGN_DEFAULT)
public class InnerAllHeaderImpl implements ISetHeaderStrategy {
    private static final String CONTENT_LENGTH = "Content-Length";

    @Override
    public void setHeader(RequestTemplate template) {
        ServerHttpRequest serverHttpRequest = ServletUtils.getServerHttpRequest();
        if (Objects.isNull(serverHttpRequest)) {
            return;
        }
        Map<String, Set<String>> headers = getHeaders(serverHttpRequest);
        if (CollUtil.isNotEmpty(headers)) {
            headers.forEach(
                    (key, value) -> {
                        // 需要排除Content-Length，否则造成数据传输长度异常
                        if (StringUtils.isNotBlank(key)
                                && CollUtil.isNotEmpty(value)
                                && !key.equalsIgnoreCase(CONTENT_LENGTH)) {
                            template.header(key, value);
                        }
                    });
        }
    }

    /**
    * 获取请求头信息
    *
    * @param request ${@link ServerHttpRequest}
    * @return Map<String, Set < String>> ${@link Map<String, Set<String>>}
    * @author zxiaozhou
    * @date 2021-05-23 20:54
    */
    private Map<String, Set<String>> getHeaders(ServerHttpRequest request) {
        Map<String, Set<String>> map = new LinkedHashMap<>();
        HttpHeaders headers = request.getHeaders();
        headers.forEach((k, v) -> map.put(k, new HashSet<>(v)));
        return map;
    }
}
