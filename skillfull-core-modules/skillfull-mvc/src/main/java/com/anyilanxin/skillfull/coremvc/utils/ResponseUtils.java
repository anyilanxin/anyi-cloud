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
package com.anyilanxin.skillfull.coremvc.utils;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
* 响应工具
*
* @author zxiaozhou
* @date 2022-03-02 14:58
* @since JDK1.8
*/
public class ResponseUtils {
    private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();

    /**
    * 流写响应消息
    *
    * @param response ${@link HttpServletResponse}
    * @param msg ${@link String}
    * @param status ${@link Status}
    * @author zxiaozhou
    * @date 2022-03-02 15:01
    */
    public static void writeResult(HttpServletResponse response, String msg, Status status)
            throws IOException {
        writeResult(response, msg, status, status.getStatus());
    }

    /**
    * 流写响应消息
    *
    * @param response ${@link HttpServletResponse}
    * @param msg ${@link String}
    * @param status ${@link Status}
    * @author zxiaozhou
    * @date 2022-03-02 15:01
    */
    public static void writeResult(
            HttpServletResponse response, String msg, Status status, HttpStatus httpStatus)
            throws IOException {
        Result<String> result = new Result<>(status, msg);
        response.setStatus(httpStatus.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().print(OBJECTMAPPER.writeValueAsString(result));
        response.getWriter().flush();
    }

    /**
    * 流写响应消息
    *
    * @param response ${@link HttpServletResponse}
    * @param msg ${@link String}
    * @param status ${@link Integer}
    * @author zxiaozhou
    * @date 2022-03-02 15:01
    */
    public static void writeResult(
            HttpServletResponse response, String msg, Integer status, HttpStatus httpStatus)
            throws IOException {
        Result<String> result = new Result<>(status, msg);
        response.setStatus(httpStatus.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().print(OBJECTMAPPER.writeValueAsString(result));
        response.getWriter().flush();
    }
}
