

package com.anyilanxin.anyicloud.message.core.handler;

import com.alibaba.fastjson.JSONObject;
import com.anyilanxin.anyicloud.corecommon.model.auth.UserInfo;
import com.anyilanxin.anyicloud.oauth2mvc.utils.UserContextUtils;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import java.time.LocalDateTime;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

/**
 * 自定义填充公共字段处理类
 *
 * @author zxh
 * @date 2019-04-03 18:12
 * @since 1.0.0
 */
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 不需要获取登录信息
        Object createTime = getFieldValByName("createTime", metaObject);
        if (Objects.isNull(createTime)) {
            this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        }

        // 需要获取登录信息
        UserInfo user = getUser();
        if (Objects.nonNull(user)) {
            Object createUserId = getFieldValByName("createUserId", metaObject);
            if (Objects.isNull(createUserId)) {
                this.setFieldValByName("createUserId", user.getUserId(), metaObject);
            }
            Object createUserName = getFieldValByName("createUserName", metaObject);
            if (Objects.isNull(createUserName)) {
                this.setFieldValByName("createUserName", user.getRealName(), metaObject);
            }
        }
    }


    @Override
    public void updateFill(MetaObject metaObject) {
        // 不需要获取登录信息
        Object updateTime = getFieldValByName("updateTime", metaObject);
        if (Objects.isNull(updateTime)) {
            this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }

        // 需要获取登录信息
        UserInfo user = getUser();
        if (Objects.nonNull(user)) {
            Object updateUserId = getFieldValByName("updateUserId", metaObject);
            if (Objects.isNull(updateUserId)) {
                this.setFieldValByName("updateUserId", user.getUserId(), metaObject);
            }
            Object updateUserName = getFieldValByName("updateUserName", metaObject);
            if (Objects.isNull(updateUserName)) {
                this.setFieldValByName("updateUserName", user.getRealName(), metaObject);
            }
        }
    }


    /**
     * 获取用户信息
     *
     * @return JSONObject ${@link JSONObject}
     * @author zxh
     * @date 2020-08-26 18:43
     */
    private UserInfo getUser() {
        try {
            return UserContextUtils.getUserInfo();
        } catch (Exception e) {
            log.error("------------MyMetaObjectHandler------获取用户信息失败------>getUser:{}", e.getMessage());
        }
        return null;
    }
}
