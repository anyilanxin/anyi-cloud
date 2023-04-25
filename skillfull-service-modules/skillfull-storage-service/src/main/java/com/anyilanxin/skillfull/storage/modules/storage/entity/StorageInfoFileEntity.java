package com.anyilanxin.skillfull.storage.modules.storage.entity;

import com.anyilanxin.skillfull.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 本地文件服务(StorageInfoFile)Entity
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-04-09 11:53:36
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName("storage_info_file")
public class StorageInfoFileEntity extends BaseEntity {
    private static final long serialVersionUID = 614383241708803778L;

    @TableId
    private String fileId;

    /**
     * 原始文件名(不包括扩展名)
     */
    private String fileOriginalName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 存放文件夹名称
     */
    private String fileDirPrefix;

    /**
     * 文件引擎类型：1-本地，2-oss,3-minio
     */
    private Integer fileStorageType;

    /**
     * 文件流类型
     */
    private String contentType;

    /**
     * 文件大小
     */
    private String fileSize;

    /**
     * 文件详细大小
     */
    private Long fileSizeDetail;

    /**
     * 文件md5值
     */
    private String fileMd5;

    /**
     * 文件相对路径
     */
    private String fileRelativePath;

    /**
     * endpoint
     */
    private String endpoint;

    /**
     * 文件域名(主要用于非本地存储使用)
     */
    private String fileHost;

    /**
     * 备注
     */
    private String remark;
}
