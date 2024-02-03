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

import com.anyilanxin.anyicloud.corecommon.utils.AnYiDateUtils;
import com.anyilanxin.anyicloud.job.modules.jobcore.conf.XxlJobAdminConfig;
import com.anyilanxin.anyicloud.job.modules.manage.entity.JobLogReportEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * job log report helper
 *
 * @author xuxueli 2019-11-22
 */
public class JobLogReportHelper {
    private static Logger logger = LoggerFactory.getLogger(JobLogReportHelper.class);

    private static JobLogReportHelper instance = new JobLogReportHelper();

    public static JobLogReportHelper getInstance() {
        return instance;
    }

    private Thread logrThread;
    private volatile boolean toStop = false;

    public void start() {
        logrThread = new Thread(new Runnable() {

            @Override
            public void run() {

                // last clean log time
                long lastCleanLogTime = 0;

                while (!toStop) {

                    // 1、log-report refresh: refresh log report in 3 days
                    try {

                        for (int i = 0; i < 3; i++) {

                            // today
                            Calendar itemDay = Calendar.getInstance();
                            itemDay.add(Calendar.DAY_OF_MONTH, -i);
                            itemDay.set(Calendar.HOUR_OF_DAY, 0);
                            itemDay.set(Calendar.MINUTE, 0);
                            itemDay.set(Calendar.SECOND, 0);
                            itemDay.set(Calendar.MILLISECOND, 0);

                            Date todayFrom = itemDay.getTime();

                            itemDay.set(Calendar.HOUR_OF_DAY, 23);
                            itemDay.set(Calendar.MINUTE, 59);
                            itemDay.set(Calendar.SECOND, 59);
                            itemDay.set(Calendar.MILLISECOND, 999);

                            Date todayTo = itemDay.getTime();

                            // refresh log-report every minute
                            JobLogReportEntity xxlJobLogReport = new JobLogReportEntity();
                            xxlJobLogReport.setTriggerDay(AnYiDateUtils.toLocalDateTime(todayFrom));
                            xxlJobLogReport.setRunningCount(0);
                            xxlJobLogReport.setSucCount(0);
                            xxlJobLogReport.setFailCount(0);

                            Map<String, Object> triggerCountMap = XxlJobAdminConfig.getAdminConfig().getXxlJobLogDao().findLogReport(todayFrom, todayTo);
                            if (triggerCountMap != null && triggerCountMap.size() > 0) {
                                int triggerDayCount = triggerCountMap.containsKey("triggerDayCount") ? Integer.valueOf(String.valueOf(triggerCountMap.get("triggerDayCount"))) : 0;
                                int triggerDayCountRunning = triggerCountMap.containsKey("triggerDayCountRunning") ? Integer.valueOf(String.valueOf(triggerCountMap.get("triggerDayCountRunning"))) : 0;
                                int triggerDayCountSuc = triggerCountMap.containsKey("triggerDayCountSuc") ? Integer.valueOf(String.valueOf(triggerCountMap.get("triggerDayCountSuc"))) : 0;
                                int triggerDayCountFail = triggerDayCount - triggerDayCountRunning - triggerDayCountSuc;

                                xxlJobLogReport.setRunningCount(triggerDayCountRunning);
                                xxlJobLogReport.setSucCount(triggerDayCountSuc);
                                xxlJobLogReport.setFailCount(triggerDayCountFail);
                            }

                            // do refresh
                            int ret = XxlJobAdminConfig.getAdminConfig().getXxlJobLogReportDao().updateById(xxlJobLogReport);
                            if (ret < 1) {
                                XxlJobAdminConfig.getAdminConfig().getXxlJobLogReportDao().insert(xxlJobLogReport);
                            }
                        }

                    } catch (Exception e) {
                        if (!toStop) {
                            logger.error(">>>>>>>>>>> xxl-job, job log report thread error:{}", e);
                        }
                    }

                    // 2、log-clean: switch open & once each day
                    if (XxlJobAdminConfig.getAdminConfig().getLogretentiondays() > 0 && System.currentTimeMillis() - lastCleanLogTime > 24 * 60 * 60 * 1000) {

                        // expire-time
                        Calendar expiredDay = Calendar.getInstance();
                        expiredDay.add(Calendar.DAY_OF_MONTH, -1 * XxlJobAdminConfig.getAdminConfig().getLogretentiondays());
                        expiredDay.set(Calendar.HOUR_OF_DAY, 0);
                        expiredDay.set(Calendar.MINUTE, 0);
                        expiredDay.set(Calendar.SECOND, 0);
                        expiredDay.set(Calendar.MILLISECOND, 0);
                        Date clearBeforeTime = expiredDay.getTime();

                        // clean expired log
                        List<Long> logIds = null;
                        do {
                            logIds = XxlJobAdminConfig.getAdminConfig().getXxlJobLogDao().findClearLogIds(0, 0, clearBeforeTime, 0, 1000);
                            if (logIds != null && logIds.size() > 0) {
                                XxlJobAdminConfig.getAdminConfig().getXxlJobLogDao().anyiPhysicalDeleteBatchIds(logIds);
                            }
                        } while (logIds != null && logIds.size() > 0);

                        // update clean time
                        lastCleanLogTime = System.currentTimeMillis();
                    }

                    try {
                        TimeUnit.MINUTES.sleep(1);
                    } catch (Exception e) {
                        if (!toStop) {
                            logger.error(e.getMessage(), e);
                        }
                    }

                }

                logger.info(">>>>>>>>>>> xxl-job, job log report thread stop");

            }
        });
        logrThread.setDaemon(true);
        logrThread.setName("xxl-job, admin JobLogReportHelper");
        logrThread.start();
    }


    public void toStop() {
        toStop = true;
        // interrupt and wait
        logrThread.interrupt();
        try {
            logrThread.join();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }

}
