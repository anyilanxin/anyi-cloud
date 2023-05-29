

package com.anyilanxin.anyicloud.message.modules.announcement.entity;

import static com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

import com.anyilanxin.anyicloud.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 系统通知公告阅读记录(AnnouncementRecord)Entity
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-04-09 11:50:29
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName("msg_announcement_record")
public class AnnouncementRecordEntity extends BaseEntity {
    private static final long serialVersionUID = 980923426359724708L;

    @TableId
    private String anntReadId;

    /**
     * 通知公告id
     */
    private String anntId;

    /**
     * 阅读状态：0-未读，1-已经。默认0
     */
    private Integer readStatus;

    /**
     * 阅读时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime readTime;

    /**
     * 备注
     */
    private String remark;
}
