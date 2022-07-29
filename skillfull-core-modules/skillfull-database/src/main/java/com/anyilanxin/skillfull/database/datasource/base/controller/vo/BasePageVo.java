// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.database.datasource.base.controller.vo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.anyilanxin.skillfull.corecommon.utils.CoreCommonUtils;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;


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
