

package com.anyilanxin.anyicloud.database.datasource.base.service.dto;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 分页结果
 *
 * @author zxh
 * @date 2020-06-23 15:36
 * @since 1.0.0
 */
@Setter
@Getter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@Schema
public class PageDto<T> implements Serializable {
    private static final long serialVersionUID = -460269613252565409L;

    @Schema(name = "records", title = "分页数据")
    private List<T> records;

    @Schema(name = "total", title = "总条数")
    private long total;

    public PageDto(IPage<T> page) {
        this.total = page.getTotal();
        this.records = Collections.emptyList();
        if (CollUtil.isNotEmpty(page.getRecords())) {
            this.records = page.getRecords();
        }
    }


    public PageDto() {
        this.records = Collections.emptyList();
    }


    public PageDto(long total, List<T> records) {
        this.total = total;
        this.records = Collections.emptyList();
        if (CollUtil.isNotEmpty(records)) {
            this.records = records;
        }
    }


    public PageDto(IPage<?> page, List<T> records) {
        this.total = page.getTotal();
        this.records = Collections.emptyList();
        if (CollUtil.isNotEmpty(records)) {
            this.records = records;
        }
    }
}
