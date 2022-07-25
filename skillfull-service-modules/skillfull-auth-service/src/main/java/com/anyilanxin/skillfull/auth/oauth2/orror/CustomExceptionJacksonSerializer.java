// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.auth.oauth2.orror;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 自定义异常序列化
 *
 * @author zxiaozhou
 * @date 2022-02-13 22:32
 * @since JDK1.8
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
