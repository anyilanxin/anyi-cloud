// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.system.core.handler;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginUserInfoModel;
import indi.zxiaozhou.skillfull.coremvc.utils.ContextHolderUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Objects;

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
        LoginUserInfoModel user = getUser();
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
        LoginUserInfoModel user = getUser();
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
    private LoginUserInfoModel getUser() {
        try {
            return ContextHolderUtils.getUserAndAuthModel();
        } catch (Exception e) {
            log.error("------------MyMetaObjectHandler------获取用户信息失败------>getUser:{}", e.getMessage());
        }
        return null;
    }
}
