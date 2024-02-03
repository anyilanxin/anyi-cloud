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

package com.anyilanxin.anyicloud.job.modules.jobcore.conf;

import com.anyilanxin.anyicloud.job.modules.jobcore.alarm.JobAlarmer;
import com.anyilanxin.anyicloud.job.modules.jobcore.scheduler.XxlJobScheduler;
import com.anyilanxin.anyicloud.job.modules.manage.mapper.*;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * xxl-job config
 *
 * @author xuxueli 2017-04-28
 */

@Component
public class XxlJobAdminConfig implements InitializingBean, DisposableBean {

    private static XxlJobAdminConfig adminConfig = null;

    public static XxlJobAdminConfig getAdminConfig() {
        return adminConfig;
    }

    // ---------------------- XxlJobScheduler ----------------------

    private XxlJobScheduler xxlJobScheduler;

    @Override
    public void afterPropertiesSet() throws Exception {
        adminConfig = this;

        xxlJobScheduler = new XxlJobScheduler();
        xxlJobScheduler.init();
    }


    @Override
    public void destroy() throws Exception {
        xxlJobScheduler.destroy();
    }

    // ---------------------- XxlJobScheduler ----------------------

    // conf

    @Value("${xxl.job.accessToken:default_token}")
    private String accessToken;

    @Value("${spring.mail.from:anyi@qq.com}")
    private String emailFrom;

    @Value("${xxl.job.triggerpool.fast.max:200}")
    private int triggerPoolFastMax;

    @Value("${xxl.job.triggerpool.slow.max:100}")
    private int triggerPoolSlowMax;

    @Value("${xxl.job.logretentiondays:30}")
    private int logretentiondays;

    // dao, service

    @Resource
    private JobLogMapper xxlJobLogDao;
    @Resource
    private JobInfoMapper xxlJobInfoDao;
    @Resource
    private JobRegistryMapper xxlJobRegistryDao;
    @Resource
    private JobGroupMapper xxlJobGroupDao;
    @Resource
    private JobLogReportMapper xxlJobLogReportDao;
    @Resource
    private JavaMailSender mailSender;
    @Resource
    private DataSource dataSource;
    @Resource
    private JobAlarmer jobAlarmer;

    public String getAccessToken() {
        return accessToken;
    }


    public String getEmailFrom() {
        return emailFrom;
    }


    public int getTriggerPoolFastMax() {
        if (triggerPoolFastMax < 200) {
            return 200;
        }
        return triggerPoolFastMax;
    }


    public int getTriggerPoolSlowMax() {
        if (triggerPoolSlowMax < 100) {
            return 100;
        }
        return triggerPoolSlowMax;
    }


    public int getLogretentiondays() {
        if (logretentiondays < 7) {
            return -1; // Limit greater than or equal to 7, otherwise close
        }
        return logretentiondays;
    }


    public JobLogMapper getXxlJobLogDao() {
        return xxlJobLogDao;
    }


    public JobInfoMapper getXxlJobInfoDao() {
        return xxlJobInfoDao;
    }


    public JobRegistryMapper getXxlJobRegistryDao() {
        return xxlJobRegistryDao;
    }


    public JobGroupMapper getXxlJobGroupDao() {
        return xxlJobGroupDao;
    }


    public JobLogReportMapper getXxlJobLogReportDao() {
        return xxlJobLogReportDao;
    }


    public JavaMailSender getMailSender() {
        return mailSender;
    }


    public DataSource getDataSource() {
        return dataSource;
    }


    public JobAlarmer getJobAlarmer() {
        return jobAlarmer;
    }

}
