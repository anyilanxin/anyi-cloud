

package com.anyilanxin.skillfull.storage.core.config.properties;

import static com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant.SLASH;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文件上传本地配置
 *
 * @author zxh
 * @date 2020-10-23 13:09
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = "local")
public class LocalFileProperty {

    /**
     * 文件虚拟映射路径
     */
    private String virtualMapping;

    /**
     * 文件上传保存路径
     */
    private String uploadFolder;

    /**
     * 系统context-path
     */
    @Value("${server.servlet.context-path}")
    private String contextPath;

    public String getVirtualMapping() {
        if (StringUtils.isNotBlank(virtualMapping) && virtualMapping.endsWith(SLASH)) {
            virtualMapping = virtualMapping.substring(0, virtualMapping.length() - 1);
        }
        return virtualMapping;
    }


    public String getUploadFolder() {
        if (StringUtils.isNotBlank(uploadFolder) && uploadFolder.endsWith(SLASH)) {
            uploadFolder = uploadFolder.substring(0, uploadFolder.length() - 1);
        }
        return uploadFolder;
    }


    public String getContextPath() {
        if (StringUtils.isBlank(contextPath)) {
            contextPath = "";
        }
        return contextPath;
    }
}
