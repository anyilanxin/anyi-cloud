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
package com.anyilanxin.skillfull.logging.core.handler;

import com.alibaba.fastjson.JSONObject;
import com.anyilanxin.skillfull.corecommon.model.auth.UserInfo;
import com.anyilanxin.skillfull.oauth2mvc.utils.UserContextUtils;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

/**
* 自定义填充公共字段处理类
*
* @author zxiaozhou
* @date 2019-04-03 18:12
* @since JDK11
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
    * @author zxiaozhou
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
