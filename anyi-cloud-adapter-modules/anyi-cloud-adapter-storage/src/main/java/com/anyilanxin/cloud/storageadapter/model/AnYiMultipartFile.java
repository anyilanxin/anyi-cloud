/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.cloud.storageadapter.model;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * MultipartFile转换实现
 *
 * @author zxh
 * @date 2023-06-28 11:16
 * @since 1.0.0
 */
public class AnYiMultipartFile implements MultipartFile, Serializable {
    @Serial
    private static final long serialVersionUID = 1687923421798L;
    private final String name;

    private final String originalFilename;

    @Nullable
    private final String contentType;

    private final byte[] content;

    /**
     * 创建AnYiMultipartFile
     *
     * @param name        上传文件参数名
     * @param file        文件
     * @param contentType 内容类型
     * @return
     * @throws IOException ioexception
     * @author zxh
     * @date 2023-06-28 11:35:30
     */
    public AnYiMultipartFile(String name, File file, @Nullable String contentType) throws IOException {
        this(name, file.getName(), contentType, FileCopyUtils.copyToByteArray(new FileInputStream(file)));
    }


    /**
     * 创建AnYiMultipartFile
     *
     * @param file 文件
     * @return
     * @throws IOException ioexception
     * @author zxh
     * @date 2023-06-28 11:32:26
     */
    public AnYiMultipartFile(File file) throws IOException {
        this("file", file.getName(), null, FileCopyUtils.copyToByteArray(new FileInputStream(file)));
    }


    /**
     * 创建AnYiMultipartFile
     *
     * @param file 文件
     * @return
     * @throws IOException ioexception
     * @author zxh
     * @date 2023-06-28 11:32:26
     */
    public AnYiMultipartFile(File file, @Nullable String contentType) throws IOException {
        this("file", file.getName(), contentType, FileCopyUtils.copyToByteArray(new FileInputStream(file)));
    }


    /**
     * 创建AnYiMultipartFile
     *
     * @param name             上传文件参数名
     * @param originalFilename 文件名称
     * @param contentType      传输content type
     * @param content          文件二进制内容
     */
    public AnYiMultipartFile(String name, @Nullable String originalFilename, @Nullable String contentType, @Nullable byte[] content) {
        Assert.hasLength(name, "Name must not be empty");
        this.name = name;
        this.originalFilename = (originalFilename != null ? originalFilename : "");
        this.contentType = contentType;
        this.content = (content != null ? content : new byte[0]);
    }


    @Override
    public String getName() {
        return this.name;
    }


    @Override
    @NonNull
    public String getOriginalFilename() {
        return this.originalFilename;
    }


    @Override
    @Nullable
    public String getContentType() {
        return this.contentType;
    }


    @Override
    public boolean isEmpty() {
        return (this.content.length == 0);
    }


    @Override
    public long getSize() {
        return this.content.length;
    }


    @Override
    public byte[] getBytes() throws IOException {
        return this.content;
    }


    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(this.content);
    }


    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        FileCopyUtils.copy(this.content, dest);
    }

}
