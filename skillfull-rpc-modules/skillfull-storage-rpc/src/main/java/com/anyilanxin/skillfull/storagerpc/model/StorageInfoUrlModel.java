package com.anyilanxin.skillfull.storagerpc.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 路由查询响应
 *
 * @author zxiaozhou
 * @date 2020-09-10 22:48
 * @since JDK11
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
