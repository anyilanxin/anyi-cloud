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
package com.anyilanxin.skillfull.storagerpc.utils;

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
