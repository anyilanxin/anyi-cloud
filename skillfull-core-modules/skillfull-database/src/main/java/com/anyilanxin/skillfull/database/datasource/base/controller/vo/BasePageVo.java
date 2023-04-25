/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
package com.anyilanxin.skillfull.database.datasource.base.controller.vo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.anyilanxin.skillfull.corecommon.utils.CoreCommonUtils;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
* 分页基类vo
*
* @author zxiaozhou
* @date 2020-06-22 15:27
* @since JDK11
*/
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class BasePageVo implements Serializable {
    private static final long serialVersionUID = 6447362475456626689L;
    private static final String DEFAULT_ORDER_FILED = "createTime";

    @Schema(name = "current", title = "当前页", example = "1")
    @Builder.Default
    private int current = 1;

    @Schema(name = "startTime", title = "开始时间", type = "string", example = "2020-11-12 11:23")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @Schema(name = "endTime", title = "结束时间", type = "string", example = "2020-11-12 11:23")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

    @Schema(name = "size", title = "每页条数", example = "10")
    @Builder.Default
    private int size = 10;

    @Schema(name = "descs", title = "降序字段列表")
    private Set<String> descs;

    @Schema(name = "ascs", title = "升序字段列表")
    private Set<String> ascs;

    @Schema(name = "getPage", title = "myBatisPlus分页信息", hidden = true)
    public <T> Page<T> getPage() {
        Page<T> page = new Page<>(getCurrent(), getSize());
        if (CollUtil.isNotEmpty(getAscs())) {
            Set<String> middleAscs = new LinkedHashSet<>(8);
            getAscs().forEach(v -> middleAscs.add(CoreCommonUtils.humpToUnderline(v)));
            // 时间排序添加到最后,避免排序不稳定
            if (!middleAscs.contains(DEFAULT_ORDER_FILED)) {
                middleAscs.add(CoreCommonUtils.humpToUnderline(DEFAULT_ORDER_FILED));
            }
            page.addOrder(OrderItem.ascs(middleAscs.toArray(new String[0])));
        } else {
            Set<String> middleDesc = new LinkedHashSet<>(8);
            if (CollUtil.isNotEmpty(getDescs())) {
                getDescs().forEach(v -> middleDesc.add(CoreCommonUtils.humpToUnderline(v)));
            }
            // 时间排序添加到最后,避免排序不稳定
            if (!middleDesc.contains(DEFAULT_ORDER_FILED)) {
                middleDesc.add(CoreCommonUtils.humpToUnderline(DEFAULT_ORDER_FILED));
            }
            page.addOrder(OrderItem.descs(middleDesc.toArray(new String[0])));
        }
        return page;
    }

    public Set<String> getDescs() {
        if (CollectionUtil.isEmpty(descs)) {
            descs = new HashSet<>();
        }
        return descs;
    }

    public Set<String> getAscs() {
        if (CollectionUtil.isEmpty(ascs)) {
            ascs = new HashSet<>();
        }
        return ascs;
    }
}
