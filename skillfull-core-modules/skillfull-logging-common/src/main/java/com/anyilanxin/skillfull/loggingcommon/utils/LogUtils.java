// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.loggingcommon.utils;

import com.anyilanxin.skillfull.loggingcommon.model.AuthLogModel;
import com.anyilanxin.skillfull.loggingcommon.model.OperateLogModel;
import com.anyilanxin.skillfull.stream.component.BindingComponent;
import com.anyilanxin.skillfull.stream.constant.BindingStreamConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Objects;

/**
 * 鉴权工具
 *
 * @author zxiaozhou
 * @date 2021-07-28 22:44
 * @since JDK1.8
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class LogUtils {
    @Value("${spring.application.name:'unknown'}")
    private String serviceName;
    @Value("${spring.application.version:'unknown'}")
    private String applicationVersion;

    private static LogUtils utils;
    private final BindingComponent bindingComponent;


    /**
     * 保存操作日志
     *
     * @param operateLogModel
     * @author zxiaozhou
     * @date 2021-07-29 14:50
     */
    public static void sendOperateLog(OperateLogModel operateLogModel) {
        if (Objects.nonNull(operateLogModel)) {
            if (StringUtils.isBlank(operateLogModel.getDataSources())) {
                operateLogModel.setDataSources(utils.serviceName);
            }
            if (StringUtils.isBlank(operateLogModel.getDataSourcesDescribe())) {
                operateLogModel.setDataSourcesDescribe("来源于" + utils.serviceName + "(版本:" + utils.applicationVersion + ")的日志");
            }
            if (Objects.nonNull(operateLogModel.getRequestStartTime()) && Objects.nonNull(operateLogModel.getRequestEndTime())) {
                Duration duration = Duration.between(operateLogModel.getRequestStartTime(), operateLogModel.getRequestEndTime());
                operateLogModel.setCostTime(duration.toMillis());
            }
            utils.bindingComponent.out(BindingStreamConstant.OPERATE_LOG_PROCESS, operateLogModel);
        }
    }


    /**
     * 保存授权日志
     *
     * @param authLogModel
     * @author zxiaozhou
     * @date 2021-07-29 14:50
     */
    public static void sendAuthLog(AuthLogModel authLogModel) {
        if (Objects.nonNull(authLogModel)) {
            if (Objects.nonNull(authLogModel.getRequestStartTime()) && Objects.nonNull(authLogModel.getRequestEndTime())) {
                Duration duration = Duration.between(authLogModel.getRequestStartTime(), authLogModel.getRequestEndTime());
                authLogModel.setCostTime(duration.toMillis());
            }
            utils.bindingComponent.out(BindingStreamConstant.AUTH_LOG_PROCESS, authLogModel);
        }
    }


    @PostConstruct
    private void init() {
        utils = this;
    }
}
