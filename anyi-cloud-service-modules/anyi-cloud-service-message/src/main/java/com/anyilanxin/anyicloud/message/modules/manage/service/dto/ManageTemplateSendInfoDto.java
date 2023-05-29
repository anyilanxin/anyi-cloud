

package com.anyilanxin.anyicloud.message.modules.manage.service.dto;

import com.anyilanxin.anyicloud.message.modules.manage.entity.ManageTemplateEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 发送消息所需要信息
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-03-29 05:23:42
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class ManageTemplateSendInfoDto implements Serializable {
    private static final long serialVersionUID = 400261450235058454L;

    @Schema(name = "templateEntity", title = "模板信息")
    private ManageTemplateEntity templateEntity;

    @Schema(name = "configInfo", title = "配置信息")
    private String configInfo;
}
