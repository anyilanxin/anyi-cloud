// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.corecommon.exception;


import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.constant.Status;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 响应异常
 *
 * @author zxiaozhou
 * @date 2020-06-22 16:24
 * @since JDK11
 */
public class ResponseException extends RuntimeException {
    private static final long serialVersionUID = 7207809155561786625L;

    /**
     * 错误异常结果
     */
    private final Result<Object> result;

    public ResponseException() {
        super(Status.ERROR.getMessage());
        this.result = new Result<>(Status.ERROR);
    }

    /**
     * 构造函数
     *
     * @param status 响应代码
     */
    public ResponseException(Status status) {
        super(status.getMessage());
        this.result = new Result<>(status);
    }

    /**
     * 构造函数
     *
     * @param status 响应代码
     * @param data   业务数据
     */
    public ResponseException(Status status, Object data) {
        super(status.getMessage());
        this.result = new Result<>(status, data);
    }


    /**
     * 构造函数
     *
     * @param status  响应代码
     * @param message 异常消息
     */
    public ResponseException(Status status, String message) {
        super(message);
        this.result = new Result<>(status, message);
    }

    /**
     * 构造函数
     *
     * @param message 消息提示
     */
    public ResponseException(String message) {
        super(message);
        this.result = new Result<>(Status.ERROR, message);
    }

    /**
     * 构造函数
     *
     * @param code    响应代码
     * @param message 消息提示
     */
    public ResponseException(int code, String message) {
        super(message);
        this.result = new Result<>(code, message);
    }

    /**
     * 构造函数
     *
     * @param result HTTP响应接口输出结果实体
     */
    public ResponseException(Result<Object> result) {
        super(result.getMessage());
        this.result = result;
    }

    /**
     * 获取错误堆栈信息
     *
     * @param throwable
     * @return
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }

    public Result<Object> getResult() {
        return result;
    }


    /**
     * 获取错误堆栈信息
     *
     * @return
     */
    public String getStackTraceString() {
        return getStackTrace(this);
    }

}
