

package com.anyilanxin.anyicloud.auth.oauth2.orror;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义异常序列化
 *
 * @author zxh
 * @date 2022-02-13 22:32
 * @since 1.0.0
 */
@Slf4j
public class CustomExceptionJacksonSerializer extends StdSerializer<CustomOauth2Exception> {
    public CustomExceptionJacksonSerializer() {
        super(CustomOauth2Exception.class);
    }


    @Override
    public void serialize(CustomOauth2Exception value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeObject(value.getResult());
    }
}
