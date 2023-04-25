/*
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   8.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   9.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.skillfull.corecommon.utils.excel.listener;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * excel通用导入Listener
 *
 * @author zxiaozhou
 * @date 2021-10-27 22：10
 * @since JDK1.8
 */
@Getter
@Slf4j
public class BasicExcelImportListener<I, R> extends AnalysisEventListener<I> {
    /** 读取后数据处理类 */
    private final ImportService<I, R> service;

    /** 读取结果集 */
    private final List<I> importData = new ArrayList<>(16);

    /** 处理后结果集 */
    private List<R> saveData;

    public BasicExcelImportListener(ImportService<I, R> service) {
        this.service = service;
    }

    @Override
    public void invoke(I i, AnalysisContext analysisContext) {
        log.debug(
                "------------当前读取到的数据------{}------>\n{}",
                analysisContext.readRowHolder().getRowIndex(),
                JSONObject.toJSONString(i, SerializerFeature.WriteMapNullValue));
        if (Objects.nonNull(i)) {
            importData.add(i);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info(
                "------------读取到的全部数据------------>\n{}",
                JSONObject.toJSONString(importData, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(importData)) {
            log.error("------------------------>当前读取数据结果为0，不进行处理");
            saveData = Collections.emptyList();
            return;
        }
        saveData = service.saveData(importData);
        log.debug(
                "------------数据处理返回结果------------>\n{}",
                JSONObject.toJSONString(saveData, SerializerFeature.WriteMapNullValue));
    }

    /**
     * 读取数据后处理逻辑
     *
     * @author zxiaozhou
     * @date 2021-10-27 22：10
     */
    public interface ImportService<I, R> {
        List<R> saveData(List<I> exportData);
    }
}
