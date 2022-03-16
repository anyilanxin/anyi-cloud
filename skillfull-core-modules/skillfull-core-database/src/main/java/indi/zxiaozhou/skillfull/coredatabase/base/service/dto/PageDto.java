// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.coredatabase.base.service.dto;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页结果
 *
 * @author zxiaozhou
 * @date 2020-06-23 15:36
 * @since JDK11
 */
@Setter
@Getter
@ToString
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PageDto<T> implements Serializable {
    private static final long serialVersionUID = -460269613252565409L;

    /**
     * 数据列表
     */
    @Builder.Default
    private List<T> records = Collections.emptyList();

    /**
     * 总数
     */
    private long total;


    public PageDto(IPage<T> page) {
        this.total = page.getTotal();
        if (CollectionUtil.isNotEmpty(page.getRecords())) {
            this.records = page.getRecords();
        }
    }


    public PageDto(long total, List<T> records) {
        this.total = total;
        if (CollectionUtil.isNotEmpty(records)) {
            this.records = records;
        }
    }


    public PageDto(IPage<?> page, List<T> records) {
        this.total = page.getTotal();
        if (CollectionUtil.isNotEmpty(records)) {
            this.records = records;
        }
    }

}
