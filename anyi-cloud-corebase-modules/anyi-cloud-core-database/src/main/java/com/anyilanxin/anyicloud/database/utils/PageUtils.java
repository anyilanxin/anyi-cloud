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

package com.anyilanxin.anyicloud.database.utils;

import com.anyilanxin.anyicloud.corecommon.model.common.AnYiPageQuery;
import com.anyilanxin.anyicloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.anyicloud.corecommon.utils.AnYiCoreCommonUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.dromara.hutool.core.collection.CollUtil;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 分页信息转换
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2023-02-22 22:38
 * @since 1.0.0
 */
public class PageUtils {
    private static final String DEFAULT_ORDER_FILED = "createTime";

    /**
     * zeebe分页查询转mybatis plus分页信息
     */
    public static <T> Page<T> getPage(AnYiPageQuery pageQuery) {
        Page<T> page = new Page<>(pageQuery.getCurrent(), pageQuery.getSize());
        if (CollUtil.isNotEmpty(pageQuery.getAscs())) {
            Set<String> middleAscs = new LinkedHashSet<>(8);
            pageQuery.getAscs().forEach(v -> middleAscs.add(AnYiCoreCommonUtils.humpToUnderline(v)));
            // 时间排序添加到最后,避免排序不稳定
            if (!middleAscs.contains(DEFAULT_ORDER_FILED)) {
                middleAscs.add(AnYiCoreCommonUtils.humpToUnderline(DEFAULT_ORDER_FILED));
            }
            page.addOrder(OrderItem.ascs(middleAscs.toArray(new String[0])));
        } else {
            Set<String> middleDesc = new LinkedHashSet<>(8);
            if (CollUtil.isNotEmpty(pageQuery.getDescs())) {
                pageQuery.getDescs().forEach(v -> middleDesc.add(AnYiCoreCommonUtils.humpToUnderline(v)));
            }
            // 时间排序添加到最后,避免排序不稳定
            if (!middleDesc.contains(DEFAULT_ORDER_FILED)) {
                middleDesc.add(AnYiCoreCommonUtils.humpToUnderline(DEFAULT_ORDER_FILED));
            }
            page.addOrder(OrderItem.descs(middleDesc.toArray(new String[0])));
        }
        return page;
    }


    /**
     * mybatis plus分页查询转分页返回信息
     */
    public static <T> AnYiPageResult<T> toPageData(IPage<T> page) {
        return new AnYiPageResult<>(page.getTotal(), page.getRecords());
    }


    /**
     * 普通分页查询返回信息
     */
    public static <T> AnYiPageResult<T> toPageData(long total, List<T> records, String lastDataId) {
        return new AnYiPageResult<>(total, records, lastDataId);
    }

    /**
     * 普通分页查询返回信息
     */
    public static <T> AnYiPageResult<T> toPageData() {
        return new AnYiPageResult<>(0, Collections.emptyList());
    }

    /**
     * 普通分页查询返回信息
     */
    public static <T> AnYiPageResult<T> toPageData(long total, List<T> records) {
        return new AnYiPageResult<>(total, records);
    }


    /**
     * mybatis plus分页查询转分页返回信息
     */
    public static <T> AnYiPageResult<T> toPageData(IPage<T> page, String lastDataId) {
        return new AnYiPageResult<>(page.getTotal(), page.getRecords(), lastDataId);
    }

    /**
     * mybatis plus分页查询转分页返回信息
     */
    public static <T, B> AnYiPageResult<B> toPageData(IPage<T> page, List<B> records) {
        return new AnYiPageResult<>(page.getTotal(), records);
    }


    /**
     * mybatis plus分页查询转分页返回信息
     */
    public static <T, B> AnYiPageResult<B> toPageData(IPage<T> page, List<B> records, String lastDataId) {
        return new AnYiPageResult<>(page.getTotal(), records, lastDataId);
    }

}
