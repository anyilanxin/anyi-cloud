package com.anyilanxin.skillfull.storagerpc.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 路由查询响应
 *
 * @author zxiaozhou
 * @date 2020-09-10 22:48
 * @since JDK11
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
