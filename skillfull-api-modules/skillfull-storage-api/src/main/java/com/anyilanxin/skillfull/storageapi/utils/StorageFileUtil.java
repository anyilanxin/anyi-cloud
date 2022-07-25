package com.anyilanxin.skillfull.storageapi.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.xmlbeans.impl.common.IOUtil;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 文件处理工具类
 *
 * @author zhou
 * @date 2022-07-15 22:05
 * @since JDK11
 */
public class StorageFileUtil {


    /**
     * 本地文件转MultipartFile
     *
     * @param file            文件
     * @param requestFileName 上传接收时MultipartFile的@RequestParam参数名
     * @return MultipartFile
     * @author zxiaozhou
     * @date 2022-07-15 22:13
     */
    public static MultipartFile fileToMultipartFile(File file, String requestFileName) {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        String contentType = new MimetypesFileTypeMap().getContentType(file);
        FileItem item = factory.createItem(requestFileName, contentType, true, file.getName());
        try (OutputStream outputStream = item.getOutputStream()) {
            IOUtil.copyCompletely(new FileInputStream(file), outputStream);
            return new CommonsMultipartFile(item);
        } catch (IOException e) {
            throw new RuntimeException("文件转换为MultipartFile异常:" + e.getMessage());
        }
    }
}
