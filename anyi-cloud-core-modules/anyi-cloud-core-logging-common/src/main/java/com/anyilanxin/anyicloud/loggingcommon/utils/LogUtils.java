

package com.anyilanxin.anyicloud.loggingcommon.utils;

import com.anyilanxin.anyicloud.loggingcommon.model.AuthLogModel;
import com.anyilanxin.anyicloud.loggingcommon.model.OperateLogModel;
import com.anyilanxin.anyicloud.stream.component.BindingComponent;
import com.anyilanxin.anyicloud.stream.constant.BindingStreamConstant;

import java.time.Duration;
import java.util.Objects;
import javax.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 鉴权工具
 *
 * @author zxh
 * @date 2021-07-28 22:44
 * @since 1.0.0
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
     * @author zxh
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
     * @author zxh
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
