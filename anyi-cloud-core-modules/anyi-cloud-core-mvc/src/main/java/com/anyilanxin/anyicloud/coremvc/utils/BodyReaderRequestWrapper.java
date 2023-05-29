

package com.anyilanxin.anyicloud.coremvc.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;

/**
 * 处理request下次数据流中数据丢失
 *
 * @author zxh
 * @date 2020-06-29 12:47
 * @since 1.0.0
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
     * @author zxh
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
     * @author zxh
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
