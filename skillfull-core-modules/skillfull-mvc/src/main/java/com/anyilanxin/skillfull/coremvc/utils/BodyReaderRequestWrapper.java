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
package com.anyilanxin.skillfull.coremvc.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 处理request下次数据流中数据丢失
 *
 * @author zxiaozhou
 * @date 2020-06-29 12:47
 * @since JDK11
 */
public class BodyReaderRequestWrapper extends HttpServletRequestWrapper {
    private final byte[] body;
    private final String stringBody;
    private final JSONObject jsonObjectBody;

    public BodyReaderRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        stringBody = getBodyString(request);
        jsonObjectBody = JSONObject.parseObject(stringBody);
        body = stringBody.getBytes(StandardCharsets.UTF_8);
    }

    public String getStringBody() {
        return this.stringBody;
    }


    public JSONObject getJsonObjectBody() {
        return this.jsonObjectBody;
    }

    /**
     * 获取请求Body
     *
     * @param request ${@link ServletRequest}
     * @return String ${@link String}
     * @author zxiaozhou
     * @date 2019-05-18 22:59
     */
    private String getBodyString(final ServletRequest request) {
        String bodyString = "";
        try (InputStream inputStream = cloneInputStream(request.getInputStream())) {
            bodyString = IOUtils.toString(inputStream, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bodyString;
    }

    /**
     * 复制输入流
     *
     * @param inputStream ${@link ServletInputStream}
     * @return InputStream ${@link InputStream}
     * @author zxiaozhou
     * @date 2019-05-18 22:58
     */
    private InputStream cloneInputStream(ServletInputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buffer)) > -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream(), StandardCharsets.UTF_8));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };
    }
}
