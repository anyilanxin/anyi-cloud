/*
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   8.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   9.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.skillfull.corecommon.utils;

import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

/**
 * 时间处理工具类
 *
 * @author zxiaozhou
 * @date 2019-04-01 16:55
 * @since JDK11
 */
public class CoreCommonDateUtils {
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String FORMATYYYYLMMYDDR = "yyyy年MM月dd日";
    public static final String FORMATYYYYLMMYDDRHHMMSS = "yyyy年MM月dd日 HH时mm分ss秒";
    public static final String FORMAT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * Date转字符串
     *
     * @param date ${@link Date}
     * @param format ${@link String}不传默认：yyyy年MM月dd日 HH时mm分ss秒
     * @return String ${@link String}
     * @author zxiaozhou
     * @date 2021-12-08 11:49
     */
    public static String dateToString(Date date, String format) {
        if (StringUtils.isBlank(format)) {
            format = FORMATYYYYLMMYDDRHHMMSS;
        }
        return DateUtil.format(date, format);
    }

    /**
     * Date转字符串
     *
     * @param dateStr 字符串日期
     * @param format ${@link String}不传默认：yyyy年MM月dd日 HH时mm分ss秒
     * @return Date
     * @author zxiaozhou
     * @date 2021-12-08 11:49
     */
    public static Date stringToDate(String dateStr, String format) {
        if (StringUtils.isBlank(format)) {
            format = FORMATYYYYLMMYDDRHHMMSS;
        }
        return DateUtil.parse(dateStr, format);
    }

    /**
     * Date转LocalDateTime
     *
     * @param date ${@link Date}
     * @return LocalDateTime ${@link LocalDateTime}
     * @author zxiaozhou
     * @date 2020-10-19 19:56
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }
        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    /**
     * LocalDateTime转Date
     *
     * @param localDateTime ${@link LocalDateTime} LocalDateTime时间
     * @return Date ${@link Date}
     * @author zxiaozhou
     * @date 2020-10-19 19:57
     */
    public static Date toDateTime(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * 获取今天的字符串日期
     *
     * @param format ${@link String} 格式
     * @return String ${@link String} 结果
     * @author zxiaozhou
     * @date 2020-08-28 15:01
     */
    public static String getNowStrDate(String format) {
        LocalDateTime localDateTime = LocalDateTime.now();
        return dateToStr(localDateTime, format);
    }

    /**
     * 时间格式化为字符串
     *
     * @param localDateTime ${@link LocalDateTime} 时间
     * @param format ${@link String} 格式
     * @return String ${@link String}
     * @author zxiaozhou
     * @date 2020-08-28 15:10
     */
    public static String dateToStr(LocalDateTime localDateTime, String format) {
        return DateTimeFormatter.ofPattern(format).format(localDateTime);
    }

    /**
     * 字符串转时间
     *
     * @param strDate ${@link String} 日期
     * @param format ${@link String} 格式
     * @return LocalDateTime ${@link LocalDateTime} 时间
     * @author zxiaozhou
     * @date 2020-08-28 15:11
     */
    public static LocalDateTime strToDate(String strDate, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(strDate, dateTimeFormatter);
    }

    /**
     * 获取时间戳并格式化为字符串
     *
     * @param startTime ${@link LocalDateTime}
     * @param endTime ${@link LocalDateTime}
     * @return String ${@link String}
     * @author zxiaozhou
     * @date 2021-09-01 09:27
     */
    public static String timeDifferenceToStr(LocalDateTime startTime, LocalDateTime endTime) {
        if (Objects.isNull(startTime) || Objects.isNull(endTime)) {
            return "";
        }
        Duration between = LocalDateTimeUtil.between(startTime, endTime);
        return durationToStr(between);
    }

    /**
     * 获取时间戳并格式化为字符串
     *
     * @param startTime ${@link LocalDate}
     * @param endTime ${@link LocalDate}
     * @return String ${@link String}
     * @author zxiaozhou
     * @date 2021-09-01 09:27
     */
    public static String timeDifferenceToStr(LocalDate startTime, LocalDate endTime) {
        if (Objects.isNull(startTime) || Objects.isNull(endTime)) {
            return "";
        }
        Period period = LocalDateTimeUtil.betweenPeriod(startTime, endTime);
        StringBuilder sb = new StringBuilder();
        int years = period.getYears();
        if (years > 0) {
            sb.append(years).append("年");
        }
        int months = period.getMonths();
        if (months > 0) {
            sb.append(months).append("月");
        }
        int days = period.getDays();
        if (days > 0) {
            sb.append(days).append("天");
        }
        return sb.toString();
    }

    /**
     * 获取时间戳并格式化为字符串
     *
     * @param startTime ${@link LocalTime}
     * @param endTime ${@link LocalTime}
     * @return String ${@link String}
     * @author zxiaozhou
     * @date 2021-09-01 09:27
     */
    public static String timeDifferenceToStr(LocalTime startTime, LocalTime endTime) {
        if (Objects.isNull(startTime) || Objects.isNull(endTime)) {
            return "";
        }
        Duration between = Duration.between(startTime, endTime);
        return durationToStr(between);
    }

    /**
     * Duration转字符串
     *
     * @param between ${@link Duration}
     * @return String ${@link String}
     * @author zxiaozhou
     * @date 2021-09-01 09:35
     */
    private static String durationToStr(Duration between) {
        StringBuilder sb = new StringBuilder();
        long days = between.toDaysPart();
        if (days > 0) {
            sb.append(days).append("天");
        }
        long hours = between.toHoursPart();
        if (hours > 0) {
            sb.append(hours).append("时");
        }
        long minutes = between.toMinutesPart();
        if (minutes > 0) {
            sb.append(minutes).append("分");
        }
        long seconds = between.toSecondsPart();
        if (seconds > 0) {
            sb.append(seconds).append("秒");
        }
        return sb.toString();
    }

    /**
     * 获取时间戳并格式化为字符串
     *
     * @param startTime ${@link Date}
     * @param endTime ${@link Date}
     * @return String ${@link String}
     * @author zxiaozhou
     * @date 2021-09-01 09:27
     */
    public static String timeDifferenceToStr(Date startTime, Date endTime) {
        if (Objects.isNull(startTime) || Objects.isNull(endTime)) {
            return "";
        }
        return DateUtil.formatBetween(startTime, endTime, BetweenFormatter.Level.SECOND);
    }

    /**
     * 计算年龄
     *
     * @param birthDay ${@link LocalDateTime} 日期
     * @author zxiaozhou
     * @date 2019-12-24 19:33
     */
    public static int getAge(LocalDateTime birthDay) {
        LocalDate birthLocalDate = birthDay.toLocalDate();
        LocalDate nowLocalDate = LocalDate.now();
        return birthLocalDate.until(nowLocalDate).getYears();
    }

    /**
     * LocalDateTime转毫秒时间戳
     *
     * @param localDateTime ${@link LocalDateTime}
     * @author zxiaozhou
     * @date 2021-12-25 13:11
     */
    public static long localDateTimeToStamp(LocalDateTime localDateTime) {
        if (Objects.nonNull(localDateTime)) {
            return localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        }
        return 0L;
    }

    /**
     * LocalDate转毫秒时间戳
     *
     * @param localDate ${@link LocalDate}
     * @author zxiaozhou
     * @date 2021-12-25 13:11
     */
    public static long localDateToStamp(LocalDate localDate) {
        if (Objects.nonNull(localDate)) {
            return localDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
        }
        return 0L;
    }

    /**
     * 分钟加减
     *
     * @param localDateTime ${@link LocalDateTime} 时间
     * @param num ${@link Integer} 加或减分钟数
     * @return Date ${@link Date} 结果
     * @author zxiaozhou
     * @date 2019-12-15 01:56
     */
    public static LocalDateTime minuteAdd(LocalDateTime localDateTime, int num) {
        return localDateTime.plusMinutes(num);
    }

    /**
     * 小时加减
     *
     * @param localDateTime ${@link LocalDateTime} 时间
     * @param num ${@link Integer} 加或减小时数
     * @return LocalDateTime ${@link LocalDateTime} 结果
     * @author zxiaozhou
     * @date 2019-12-15 01:56
     */
    public static LocalDateTime hoursAdd(LocalDateTime localDateTime, int num) {
        return localDateTime.plusHours(num);
    }

    /**
     * 天加减
     *
     * @param localDateTime ${@link LocalDateTime} 时间
     * @param num ${@link Integer} 加或减天数
     * @return LocalDateTime ${@link LocalDateTime} 结果
     * @author zxiaozhou
     * @date 2019-12-15 01:56
     */
    public static LocalDateTime dateAdd(LocalDateTime localDateTime, int num) {
        return localDateTime.plusDays(num);
    }

    /**
     * 周加减
     *
     * @param localDateTime ${@link LocalDateTime} 时间
     * @param num ${@link Integer} 加或减周数
     * @return LocalDateTime ${@link LocalDateTime} 结果
     * @author zxiaozhou
     * @date 2019-12-15 01:56
     */
    public static LocalDateTime weekAdd(LocalDateTime localDateTime, int num) {
        return localDateTime.plusWeeks(num);
    }

    /**
     * 月加减
     *
     * @param localDateTime ${@link LocalDateTime} 时间
     * @param num ${@link Integer} 加或减月数
     * @return LocalDateTime ${@link LocalDateTime} 结果
     * @author zxiaozhou
     * @date 2019-12-15 01:56
     */
    public static LocalDateTime monthAdd(LocalDateTime localDateTime, int num) {
        return localDateTime.plusMonths(num);
    }

    /**
     * 年加减
     *
     * @param localDateTime ${@link LocalDateTime} 时间
     * @param num ${@link Integer} 加或减年数
     * @return LocalDateTime ${@link LocalDateTime} 结果
     * @author zxiaozhou
     * @date 2019-12-15 01:56
     */
    public static LocalDateTime yearsAdd(LocalDateTime localDateTime, int num) {
        return localDateTime.plusYears(num);
    }
}
