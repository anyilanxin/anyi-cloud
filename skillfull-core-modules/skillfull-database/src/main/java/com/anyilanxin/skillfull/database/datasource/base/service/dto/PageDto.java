// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.database.datasource.base.service.dto;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
