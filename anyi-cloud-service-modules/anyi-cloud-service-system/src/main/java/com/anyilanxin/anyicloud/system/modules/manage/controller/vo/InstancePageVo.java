

package com.anyilanxin.anyicloud.system.modules.manage.controller.vo;

import com.anyilanxin.anyicloud.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 分页
 *
 * @author zxh
 * @date 2021-12-28 05:49
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class InstancePageVo extends BasePageVo {

    @Schema(name = "serviceCode", title = "服务code")
    private String serviceCode;

    @Schema(name = "groupName", title = "服务组")
    private String groupName;
}
