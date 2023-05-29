

package com.anyilanxin.anyicloud.logging.modules.receive.service;

import com.anyilanxin.anyicloud.loggingcommon.model.AuthLogModel;
import com.anyilanxin.anyicloud.loggingcommon.model.OperateLogModel;

import java.util.List;

/**
 * 日志接收service
 *
 * @author zxh
 * @date 2022-08-13 10:58
 * @since 1.0.0
 */
public interface IReceiveService {
    /**
     * 日志存储
     *
     * @param model
     * @author zxh
     * @date 2022-01-27 19:48
     */
    void saveAuth(AuthLogModel model) throws RuntimeException;


    /**
     * 日志批量存储
     *
     * @param models
     * @author zxh
     * @date 2022-01-27 19:48
     */
    void saveAuthBatch(List<AuthLogModel> models) throws RuntimeException;


    /**
     * 日志存储
     *
     * @param model
     * @author zxh
     * @date 2022-01-27 19:48
     */
    void saveOperate(OperateLogModel model) throws RuntimeException;


    /**
     * 日志批量存储
     *
     * @param models
     * @author zxh
     * @date 2022-01-27 19:48
     */
    void saveOperateBatch(List<OperateLogModel> models) throws RuntimeException;
}
