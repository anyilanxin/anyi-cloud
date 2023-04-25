/**
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package com.anyilanxin.skillfull.storage.modules.storage.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 本地文件服务查询Response
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-04-05 09:57:58
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class StorageInfoFileDto implements Serializable {
    private static final long serialVersionUID = 383853727999670385L;

    @Schema(name = "fileId", title = "文件id")
    private String fileId;

    @Schema(name = "fileOriginalName", title = "原始文件名(不包括扩展名)")
    private String fileOriginalName;

    @Schema(name = "fileType", title = "文件类型")
    private String fileType;

    @Schema(name = "fileDirPrefix", title = "存放文件夹名称")
    private String fileDirPrefix;

    @Schema(name = "fileStorageType", title = "文件引擎类型：1-本地，2-ali oss,3-minio,具体与StorageType一致")
    private Integer fileStorageType;

    @Schema(name = "contentType", title = "文件流类型")
    private String contentType;

    @Schema(name = "fileSize", title = "文件大小")
    private String fileSize;

    @Schema(name = "fileSizeDetail", title = "文件详细大小")
    private Long fileSizeDetail;

    @Schema(name = "fileMd5", title = "文件md5值")
    private String fileMd5;

    @Schema(name = "fileRelativePath", title = "文件相对路径")
    private String fileRelativePath;

    @Schema(name = "endpoint", title = "endpoint")
    private String endpoint;

    @Schema(name = "fileHost", title = "文件域名(主要用于非本地存储使用)")
    private String fileHost;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;
}
