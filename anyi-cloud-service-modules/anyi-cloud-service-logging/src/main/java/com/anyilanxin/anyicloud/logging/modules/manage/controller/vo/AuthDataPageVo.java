

package com.anyilanxin.anyicloud.logging.modules.manage.controller.vo;

import com.anyilanxin.anyicloud.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 登录日志分页查询Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-01-26 21:53:03
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class AuthDataPageVo extends BasePageVo {
    private static final long serialVersionUID = 107385952712755796L;

    @Schema(name = "logCode", title = "日志编号")
    private String logCode;

    @Schema(name = "requestIp", title = "请求ip")
    private String requestIp;

    @Schema(name = "authType", title = "授权类型，具体参考常量字典AuthorizedGrantTypes")
    private String authType;

    @Schema(name = "authUserName", title = "授权用户名称")
    private String authUserName;

    @Schema(name = "authClientName", title = "授权客户端名称")
    private String authClientName;

    @Schema(name = "authStatus", title = "授权状态：0-失败,1-成功")
    private Integer authStatus;
}
