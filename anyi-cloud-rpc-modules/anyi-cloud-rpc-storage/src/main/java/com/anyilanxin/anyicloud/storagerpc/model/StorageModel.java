

package com.anyilanxin.anyicloud.storagerpc.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;

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
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class StorageModel implements Serializable {
    private static final long serialVersionUID = 8405189730553034504L;

    @Schema(name = "fileDirPrefix", title = "存放文件夹名称")
    private String fileDirPrefix;

    @Schema(name = "urls", title = "文件列表")
    private List<String> urls;
}
