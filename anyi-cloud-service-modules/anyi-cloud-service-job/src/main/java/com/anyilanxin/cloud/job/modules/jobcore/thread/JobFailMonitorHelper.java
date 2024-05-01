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

package com.anyilanxin.cloud.job.modules.jobcore.thread;

import com.anyilanxin.cloud.corecommon.utils.AnYiI18nUtil;
import com.anyilanxin.cloud.job.modules.jobcore.conf.XxlJobAdminConfig;
import com.anyilanxin.cloud.job.modules.jobcore.trigger.TriggerTypeEnum;
import com.anyilanxin.cloud.job.modules.manage.entity.JobInfoEntity;
import com.anyilanxin.cloud.job.modules.manage.entity.JobLogEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * job monitor instance
 *
 * @author xuxueli 2015-9-1 18:05:56
 */
public class JobFailMonitorHelper {
    private static Logger logger = LoggerFactory.getLogger(JobFailMonitorHelper.class);

    private static JobFailMonitorHelper instance = new JobFailMonitorHelper();

    public static JobFailMonitorHelper getInstance() {
        return instance;
    }

    // ---------------------- monitor ----------------------

    private Thread monitorThread;
    private volatile boolean toStop = false;

    public void start() {
        monitorThread = new Thread(new Runnable() {

            @Override
            public void run() {

                // monitor
                while (!toStop) {
                    try {

                        List<Long> failLogIds = XxlJobAdminConfig.getAdminConfig().getXxlJobLogDao().findFailJobLogIds(1000);
                        if (failLogIds != null && !failLogIds.isEmpty()) {
                            for (long failLogId : failLogIds) {

                                // lock log
                                int lockRet = XxlJobAdminConfig.getAdminConfig().getXxlJobLogDao().updateAlarmStatus(failLogId, 0, -1);
                                if (lockRet < 1) {
                                    continue;
                                }
                                JobLogEntity log = XxlJobAdminConfig.getAdminConfig().getXxlJobLogDao().selectById(failLogId);
                                JobInfoEntity info = XxlJobAdminConfig.getAdminConfig().getXxlJobInfoDao().selectById(log.getJobId());

                                // 1、fail retry monitor
                                if (log.getExecutorFailRetryCount() > 0) {
                                    JobTriggerPoolHelper.trigger(log.getJobId(), TriggerTypeEnum.RETRY, (log.getExecutorFailRetryCount() - 1), log.getExecutorShardingParam(), log.getExecutorParam(), null);
                                    String retryMsg = "<br><br><span style=\"color:#F39C12;\" > >>>>>>>>>>>" + AnYiI18nUtil.get("jobconf_trigger_type_retry") + "<<<<<<<<<<< </span><br>";
                                    log.setTriggerMsg(log.getTriggerMsg() + retryMsg);
                                    XxlJobAdminConfig.getAdminConfig().getXxlJobLogDao().updateById(log);
                                }

                                // 2、fail alarm monitor
                                int newAlarmStatus = 0; // 告警状态：0-默认、-1=锁定状态、1-无需告警、2-告警成功、3-告警失败
                                if (info != null) {
                                    boolean alarmResult = XxlJobAdminConfig.getAdminConfig().getJobAlarmer().alarm(info, log);
                                    newAlarmStatus = alarmResult ? 2 : 3;
                                } else {
                                    newAlarmStatus = 1;
                                }

                                XxlJobAdminConfig.getAdminConfig().getXxlJobLogDao().updateAlarmStatus(failLogId, -1, newAlarmStatus);
                            }
                        }

                    } catch (Exception e) {
                        if (!toStop) {
                            logger.error(">>>>>>>>>>> xxl-job, job fail monitor thread error:{}", e);
                        }
                    }

                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (Exception e) {
                        if (!toStop) {
                            logger.error(e.getMessage(), e);
                        }
                    }

                }

                logger.info(">>>>>>>>>>> xxl-job, job fail monitor thread stop");

            }
        });
        monitorThread.setDaemon(true);
        monitorThread.setName("xxl-job, admin JobFailMonitorHelper");
        monitorThread.start();
    }


    public void toStop() {
        toStop = true;
        // interrupt and wait
        monitorThread.interrupt();
        try {
            monitorThread.join();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }

}
