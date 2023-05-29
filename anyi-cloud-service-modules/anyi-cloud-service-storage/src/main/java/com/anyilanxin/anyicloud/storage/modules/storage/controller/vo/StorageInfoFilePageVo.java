

package com.anyilanxin.skillfull.storage.modules.storage.controller.vo;

import com.anyilanxin.anyicloud.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 本地文件服务分页查询Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-04-05 09:57:59
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class StorageInfoFilePageVo extends BasePageVo {
    private static final long serialVersionUID = 674308192265446304L;

    @Schema(name = "fileOriginalName", title = "原始文件名(不包括扩展名)")
    private String fileOriginalName;

    @Schema(name = "fileStorageType", title = "文件引擎类型：1-本地，2-ali oss,3-minio,具体与StorageType一致")
    private Integer fileStorageType;
}
