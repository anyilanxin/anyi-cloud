// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.logging.modules.receive.service;

import com.anyilanxin.skillfull.loggingcommon.model.AuthLogModel;
import com.anyilanxin.skillfull.loggingcommon.model.OperateLogModel;

import java.util.List;

/**
 * 日志接收service
 *
 * @author zxiaozhou
 * @date 2022-08-13 10:58
 * @since JDK11
 */
public interface IReceiveService {
    /**
     * 日志存储
     *
     * @param model
     * @author zxiaozhou
     * @date 2022-01-27 19:48
     */
    void saveAuth(AuthLogModel model) throws RuntimeException;


    /**
     * 日志批量存储
     *
     * @param models
     * @author zxiaozhou
     * @date 2022-01-27 19:48
     */
    void saveAuthBatch(List<AuthLogModel> models) throws RuntimeException;


    /**
     * 日志存储
     *
     * @param model
     * @author zxiaozhou
     * @date 2022-01-27 19:48
     */
    void saveOperate(OperateLogModel model) throws RuntimeException;


    /**
     * 日志批量存储
     *
     * @param models
     * @author zxiaozhou
     * @date 2022-01-27 19:48
     */
    void saveOperateBatch(List<OperateLogModel> models) throws RuntimeException;
}
