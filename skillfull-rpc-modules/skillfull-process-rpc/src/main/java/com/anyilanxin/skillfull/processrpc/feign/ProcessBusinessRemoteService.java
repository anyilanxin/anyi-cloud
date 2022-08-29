// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.processrpc.feign;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.constant.ServiceConstant;
import com.anyilanxin.skillfull.corecommon.feign.FeignFallback;
import com.anyilanxin.skillfull.processrpc.model.CompleteTaskModel;
import com.anyilanxin.skillfull.processrpc.model.FormSubmitProcessModel;
import com.anyilanxin.skillfull.processrpc.model.MsgSubmitProcessModel;
import com.anyilanxin.skillfull.processrpc.model.ProcessCallbackResultModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * 流程业务feign接口
 *
 * @author zxiaozhou
 * @date 2022-03-07 10:54
 * @since JDK1.8
 */
@FeignClient(value = ServiceConstant.PROCESS_SERVICE, path = ServiceConstant.PROCESS_SERVICE_PATH, fallbackFactory = FeignFallback.class)
@Validated
public interface ProcessBusinessRemoteService {

    /**
     * 发起流程
     */
    @PostMapping(value = "/business-process/process/submit")
    Result<ProcessCallbackResultModel> submitProcess(@RequestBody @Valid FormSubmitProcessModel vo);


    /**
     * 发起流程(基于消息事件)
     */
    @PostMapping(value = "/business-process/process/msg-submit")
    Result<ProcessCallbackResultModel> submitProcess(@RequestBody @Valid MsgSubmitProcessModel model);


    /**
     * 审批
     */
    @PostMapping(value = "/business-task/task/complete")
    Result<String> completeTask(@RequestBody @Valid CompleteTaskModel vo);


    /**
     * 审批(并返回审批节点信息)
     */
    @PostMapping(value = "/business-task/task/complete-callback")
    Result<ProcessCallbackResultModel> completeTaskAndCallback(@RequestBody @Valid CompleteTaskModel vo);
}
