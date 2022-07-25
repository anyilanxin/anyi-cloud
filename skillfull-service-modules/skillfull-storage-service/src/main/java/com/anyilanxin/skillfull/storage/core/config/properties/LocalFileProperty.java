// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.storage.core.config.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.SLASH;

/**
 * 文件上传本地配置
 *
 * @author zxiaozhou
 * @date 2020-10-23 13:09
 * @since JDK11
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
