

package com.anyilanxin.anyicloud.system.modules.rbac.controller.vo;

import com.anyilanxin.anyicloud.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 职位表分页查询Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:20
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class RbacPositionPageVo extends BasePageVo {
    private static final long serialVersionUID = -15357154830059617L;

    @Schema(name = "keyword", title = "职位编码、名称")
    private String keyword;

    @Schema(name = "positionStatus", title = "职位状态：0-无效，1-有效，默认0")
    private Integer positionStatus;
}
