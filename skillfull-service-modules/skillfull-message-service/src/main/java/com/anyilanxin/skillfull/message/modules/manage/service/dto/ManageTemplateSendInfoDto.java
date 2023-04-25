package com.anyilanxin.skillfull.message.modules.manage.service.dto;

import com.anyilanxin.skillfull.message.modules.manage.entity.ManageTemplateEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 发送消息所需要信息
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-03-29 05:23:42
 * @since JDK1.8
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
