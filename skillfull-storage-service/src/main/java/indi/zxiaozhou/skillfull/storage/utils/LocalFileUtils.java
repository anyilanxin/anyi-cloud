// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.storage.utils;

import indi.zxiaozhou.skillfull.storage.core.config.properties.LocalFileProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 文件处理工具类
 *
 * @author zxiaozhou
 * @date 2021-02-23 09:48
 * @since JDK1.8
 */
@RequiredArgsConstructor
@Component
public class LocalFileUtils {
    private final LocalFileProperty fileProperty;
    private static LocalFileUtils utils;

    @PostConstruct
    private void init() {
        utils = this;
    }


    /**
     * 映射路径转磁盘路径
     *
     * @param mapPath ${@link String} 映射路径
     * @return String ${@link String}
     * @author zxiaozhou
     * @date 2021-02-23 09:51
     */
    public static String mapPathToDiskPath(String mapPath) {
        String path = mapPath.replaceFirst(utils.fileProperty.getVirtualMapping(), "");
        return utils.fileProperty.getUploadFolder() + path;
    }


    /**
     * 磁盘路径转映射路径
     *
     * @param diskPath ${@link String}  磁盘路径
     * @return String ${@link String}
     * @author zxiaozhou
     * @date 2021-02-23 09:51
     */
    public static String diskPathToMapPath(String diskPath) {
        String path = diskPath.replaceFirst(utils.fileProperty.getUploadFolder(), "");
        return utils.fileProperty.getVirtualMapping() + path;
    }
}
