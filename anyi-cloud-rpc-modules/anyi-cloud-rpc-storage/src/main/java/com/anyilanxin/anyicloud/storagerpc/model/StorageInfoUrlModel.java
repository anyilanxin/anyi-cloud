

package com.anyilanxin.anyicloud.storagerpc.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 路由查询响应
 *
 * @author zxh
 * @date 2020-09-10 22:48
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema
public class StorageInfoUrlModel extends StorageInfoModel implements Serializable {
    private static final long serialVersionUID = 8405189730553034504L;

    @Schema(name = "fileOriginalUrl", title = "原始文件url地址")
    private String fileOriginalUrl;

    @Schema(name = "status", title = "上传状态:0-成功,1-失败")
    private Integer status;

    @Schema(name = "errorMsg", title = "失败原因")
    private String errorMsg;
}
