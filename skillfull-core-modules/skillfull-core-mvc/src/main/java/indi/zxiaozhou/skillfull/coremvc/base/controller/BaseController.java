// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.coremvc.base.controller;

import indi.zxiaozhou.skillfull.corecommon.base.Result;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;

/**
 * Controller基类
 *
 * @author zxiaozhou
 * @date 2020-06-22 17:19
 * @since JDK11
 */
@Slf4j
@ApiResponses({
        @ApiResponse(responseCode = "401", description = "未授权"),
        @ApiResponse(responseCode = "200", description = "成功"),
        @ApiResponse(responseCode = "403", description = "拒绝访问"),
        @ApiResponse(responseCode = "404", description = "请求路径不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
})
public class BaseController {


    /**
     * 成功响应
     *
     * @return Result<T> ${@link Result<T>} 响应信息
     * @author zxiaozhou
     * @date 2020-06-22 17:16
     */
    public static <T> Result<T> ok() {
        return new Result<>(Status.SUCCESS);
    }

    /**
     * 成功响应
     *
     * @return Result<String> ${@link Result<String>} 响应信息
     * @author zxiaozhou
     * @date 2020-06-22 17:16
     */
    public static Result<String> ok(String message) {
        return new Result<>(Status.SUCCESS, message);
    }


    /**
     * 成功响应
     *
     * @param data ${@link Object} 成功响应数据
     * @return Result<T> ${@link Result<T>} 响应信息
     * @author zxiaozhou
     * @date 2020-06-22 17:16
     */
    public static <T> Result<T> ok(T data) {
        return new Result<>(Status.SUCCESS, data);
    }

    /**
     * 成功响应
     *
     * @param status ${@link Object} 成功响应数据
     * @return Result<T> ${@link Result< T >} 响应信息
     * @author zxiaozhou
     * @date 2020-06-22 17:16
     */
    public static Result<Object> ok(Status status) {
        return new Result<>(status);
    }


    /**
     * 成功响应
     *
     * @param data    ${@link Object} 成功响应数据
     * @param message ${@link String} 成功响应消息
     * @return Result<T> ${@link Result<T>} 响应信息
     * @author zxiaozhou
     * @date 2020-06-22 17:16
     */
    public static <T> Result<T> ok(T data, String message) {
        Result<T> result = new Result<>(Status.SUCCESS, data);
        result.setMessage(message);
        return result;
    }


    /**
     * 响应失败
     *
     * @return Result<T> ${@link Result<T>} 响应信息
     * @author zxiaozhou
     * @date 2020-06-22 17:17
     */
    public static <T> Result<T> fail() {
        return new Result<>(Status.ERROR);
    }


    /**
     * 响应失败
     *
     * @param status ${@link Status} 失败状态
     * @return Result<T> ${@link Result<T>} 响应信息
     * @author zxiaozhou
     * @date 2020-06-22 17:17
     */
    public static <T> Result<T> fail(Status status) {
        return new Result<>(status);
    }

    /**
     * 响应失败
     *
     * @param message ${@link String} 失败消息
     * @return Result<T> ${@link Result<T>} 响应信息
     * @author zxiaozhou
     * @date 2020-06-22 17:17
     */
    public static <T> Result<T> fail(String message) {
        return new Result<>(Status.ERROR, message);
    }


    /**
     * 响应失败
     *
     * @param status  ${@link Status} 失败状态
     * @param message ${@link String} 失败消息
     * @return Result<T> ${@link Result<T>} 响应信息
     * @author zxiaozhou
     * @date 2020-06-22 17:17
     */
    public static <T> Result<T> fail(Status status, String message) {
        Result<T> result = new Result<>(status);
        result.setMessage(message);
        return result;
    }


    /**
     * 响应失败
     *
     * @param code    ${@link Integer} 失败状态码
     * @param message ${@link String} 失败消息
     * @return Result<T> ${@link Result<T>} 响应信息
     * @author zxiaozhou
     * @date 2020-06-22 17:17
     */
    public static <T> Result<T> fail(Integer code, String message) {
        return new Result<>(code, message);
    }
}
