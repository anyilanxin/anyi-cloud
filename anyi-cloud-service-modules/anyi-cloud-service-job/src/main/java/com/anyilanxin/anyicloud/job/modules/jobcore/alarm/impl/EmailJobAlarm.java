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

package com.anyilanxin.anyicloud.job.modules.jobcore.alarm.impl;

import com.anyilanxin.anyicloud.corecommon.utils.AnYiI18nUtil;
import com.anyilanxin.anyicloud.job.modules.jobcore.alarm.JobAlarm;
import com.anyilanxin.anyicloud.job.modules.jobcore.conf.XxlJobAdminConfig;
import com.anyilanxin.anyicloud.job.modules.manage.entity.JobGroupEntity;
import com.anyilanxin.anyicloud.job.modules.manage.entity.JobInfoEntity;
import com.anyilanxin.anyicloud.job.modules.manage.entity.JobLogEntity;
import com.xxl.job.core.biz.model.ReturnT;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * job alarm by email
 *
 * @author xuxueli 2020-01-19
 */
@Component
public class EmailJobAlarm implements JobAlarm {
    private static Logger logger = LoggerFactory.getLogger(EmailJobAlarm.class);

    /**
     * fail alarm
     *
     * @param jobLog
     */
    @Override
    public boolean doAlarm(JobInfoEntity info, JobLogEntity jobLog) {
        boolean alarmResult = true;

        // send monitor email
        if (info != null && info.getAlarmEmail() != null && info.getAlarmEmail().trim().length() > 0) {

            // alarmContent
            String alarmContent = "Alarm Job LogId=" + jobLog.getId();
            if (jobLog.getTriggerCode() != ReturnT.SUCCESS_CODE) {
                alarmContent += "<br>TriggerMsg=<br>" + jobLog.getTriggerMsg();
            }
            if (jobLog.getHandleCode() > 0 && jobLog.getHandleCode() != ReturnT.SUCCESS_CODE) {
                alarmContent += "<br>HandleCode=" + jobLog.getHandleMsg();
            }

            // email info
            JobGroupEntity group = XxlJobAdminConfig.getAdminConfig().getXxlJobGroupDao().load(Integer.valueOf(info.getJobGroup()));
            String personal = AnYiI18nUtil.get("admin_name_full");
            String title = AnYiI18nUtil.get("jobconf_monitor");
            String content = MessageFormat.format(loadEmailJobAlarmTemplate(), group != null ? group.getTitle() : "null", info.getId(), info.getJobDesc(), alarmContent);

            Set<String> emailSet = new HashSet<String>(Arrays.asList(info.getAlarmEmail().split("[,，]")));
            for (String email : emailSet) {

                // make mail
                try {
                    MimeMessage mimeMessage = XxlJobAdminConfig.getAdminConfig().getMailSender().createMimeMessage();

                    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                    helper.setFrom(XxlJobAdminConfig.getAdminConfig().getEmailFrom(), personal);
                    helper.setTo(email);
                    helper.setSubject(title);
                    helper.setText(content, true);

                    XxlJobAdminConfig.getAdminConfig().getMailSender().send(mimeMessage);
                } catch (Exception e) {
                    logger.error(">>>>>>>>>>> xxl-job, job fail alarm email send error, JobLogId:{}", jobLog.getId(), e);

                    alarmResult = false;
                }

            }
        }

        return alarmResult;
    }


    /**
     * load email job alarm template
     *
     * @return
     */
    private static final String loadEmailJobAlarmTemplate() {
        String mailBodyTemplate = "<h5>" + AnYiI18nUtil.get("jobconf_monitor_detail") + "：</span>" + "<table border=\"1\" cellpadding=\"3\" style=\"border-collapse:collapse; width:80%;\" >\n" + "   <thead style=\"font-weight: bold;color: #ffffff;background-color: #ff8c00;\" >" + "      <tr>\n" + "         <td width=\"20%\" >" + AnYiI18nUtil.get("jobinfo_field_jobgroup") + "</td>\n" + "         <td width=\"10%\" >" + AnYiI18nUtil.get("jobinfo_field_id") + "</td>\n" + "         <td width=\"20%\" >" + AnYiI18nUtil.get("jobinfo_field_jobdesc") + "</td>\n" + "         <td width=\"10%\" >" + AnYiI18nUtil.get("jobconf_monitor_alarm_title") + "</td>\n" + "         <td width=\"40%\" >" + AnYiI18nUtil.get("jobconf_monitor_alarm_content") + "</td>\n" + "      </tr>\n" + "   </thead>\n"
                + "   <tbody>\n" + "      <tr>\n" + "         <td>{0}</td>\n" + "         <td>{1}</td>\n" + "         <td>{2}</td>\n" + "         <td>" + AnYiI18nUtil.get("jobconf_monitor_alarm_type") + "</td>\n" + "         <td>{3}</td>\n" + "      </tr>\n" + "   </tbody>\n" + "</table>";

        return mailBodyTemplate;
    }

}
