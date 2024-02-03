/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.job.modules.jobcore.thread;

import com.anyilanxin.anyicloud.corecommon.utils.AnYiI18nUtil;
import com.anyilanxin.anyicloud.job.modules.jobcore.complete.XxlJobCompleter;
import com.anyilanxin.anyicloud.job.modules.jobcore.conf.XxlJobAdminConfig;
import com.anyilanxin.anyicloud.job.modules.manage.entity.JobLogEntity;
import com.xxl.job.core.biz.model.HandleCallbackParam;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * job lose-monitor instance
 *
 * @author xuxueli 2015-9-1 18:05:56
 */
public class JobCompleteHelper {
    private static Logger logger = LoggerFactory.getLogger(JobCompleteHelper.class);

    private static JobCompleteHelper instance = new JobCompleteHelper();

    public static JobCompleteHelper getInstance() {
        return instance;
    }

    // ---------------------- monitor ----------------------

    private ThreadPoolExecutor callbackThreadPool = null;
    private Thread monitorThread;
    private volatile boolean toStop = false;

    public void start() {

        // for callback
        callbackThreadPool = new ThreadPoolExecutor(2, 20, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(3000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "xxl-job, admin JobLosedMonitorHelper-callbackThreadPool-" + r.hashCode());
            }
        }, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                r.run();
                logger.warn(">>>>>>>>>>> xxl-job, callback too fast, match threadpool rejected handler(run now).");
            }
        });

        // for monitor
        monitorThread = new Thread(new Runnable() {

            @Override
            public void run() {

                // wait for JobTriggerPoolHelper-init
                try {
                    TimeUnit.MILLISECONDS.sleep(50);
                } catch (InterruptedException e) {
                    if (!toStop) {
                        logger.error(e.getMessage(), e);
                    }
                }

                // monitor
                while (!toStop) {
                    try {
                        // 任务结果丢失处理：调度记录停留在 "运行中" 状态超过10min，且对应执行器心跳注册失败不在线，则将本地调度主动标记失败；
                        Date losedTime = DateUtil.addMinutes(new Date(), -10);
                        List<Long> losedJobIds = XxlJobAdminConfig.getAdminConfig().getXxlJobLogDao().findLostJobIds(losedTime);

                        if (losedJobIds != null && losedJobIds.size() > 0) {
                            for (Long logId : losedJobIds) {

                                JobLogEntity jobLog = new JobLogEntity();
                                jobLog.setId(logId);

                                jobLog.setHandleTime(LocalDateTime.now());
                                jobLog.setHandleCode(ReturnT.FAIL_CODE);
                                jobLog.setHandleMsg(AnYiI18nUtil.get("joblog_lost_fail"));

                                XxlJobCompleter.updateHandleInfoAndFinish(jobLog);
                            }

                        }
                    } catch (Exception e) {
                        if (!toStop) {
                            logger.error(">>>>>>>>>>> xxl-job, job fail monitor thread error:{}", e);
                        }
                    }

                    try {
                        TimeUnit.SECONDS.sleep(60);
                    } catch (Exception e) {
                        if (!toStop) {
                            logger.error(e.getMessage(), e);
                        }
                    }

                }

                logger.info(">>>>>>>>>>> xxl-job, JobLosedMonitorHelper stop");

            }
        });
        monitorThread.setDaemon(true);
        monitorThread.setName("xxl-job, admin JobLosedMonitorHelper");
        monitorThread.start();
    }


    public void toStop() {
        toStop = true;

        // stop registryOrRemoveThreadPool
        callbackThreadPool.shutdownNow();

        // stop monitorThread (interrupt and wait)
        monitorThread.interrupt();
        try {
            monitorThread.join();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }

    // ---------------------- helper ----------------------


    public ReturnT<String> callback(List<HandleCallbackParam> callbackParamList) {

        callbackThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                for (HandleCallbackParam handleCallbackParam : callbackParamList) {
                    ReturnT<String> callbackResult = callback(handleCallbackParam);
                    logger.debug(">>>>>>>>> JobApiController.callback {}, handleCallbackParam={}, callbackResult={}", (callbackResult.getCode() == ReturnT.SUCCESS_CODE ? "success" : "fail"), handleCallbackParam, callbackResult);
                }
            }
        });

        return ReturnT.SUCCESS;
    }


    private ReturnT<String> callback(HandleCallbackParam handleCallbackParam) {
        // valid log item
        JobLogEntity log = XxlJobAdminConfig.getAdminConfig().getXxlJobLogDao().selectById(handleCallbackParam.getLogId());
        if (log == null) {
            return new ReturnT<>(ReturnT.FAIL_CODE, "log item not found.");
        }
        if (log.getHandleCode() > 0) {
            return new ReturnT<>(ReturnT.FAIL_CODE, "log repeate callback."); // avoid repeat callback, trigger child
            // job etc
        }

        // handle msg
        StringBuffer handleMsg = new StringBuffer();
        if (log.getHandleMsg() != null) {
            handleMsg.append(log.getHandleMsg()).append("<br>");
        }
        if (handleCallbackParam.getHandleMsg() != null) {
            handleMsg.append(handleCallbackParam.getHandleMsg());
        }

        // success, save log
        log.setHandleTime(LocalDateTime.now());
        log.setHandleCode(handleCallbackParam.getHandleCode());
        log.setHandleMsg(handleMsg.toString());
        XxlJobCompleter.updateHandleInfoAndFinish(log);

        return ReturnT.SUCCESS;
    }

}
