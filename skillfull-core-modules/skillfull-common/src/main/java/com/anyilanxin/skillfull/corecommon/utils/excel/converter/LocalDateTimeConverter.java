// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.corecommon.utils.excel.converter;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * LocalDateTime转换
 *
 * @author zxiaozhou
 * @date 2021-10-28 00:39
 * @since JDK1.8
 */
public class LocalDateTimeConverter implements Converter<LocalDateTime> {
    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Override
    public Class<?> supportJavaTypeKey() {
        return LocalDateTime.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * excel类型转换为java类型
     */
    @Override
    public LocalDateTime convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
                                           GlobalConfiguration globalConfiguration) {
        String stringValue = cellData.getStringValue();
        LocalDateTime localDateTime = null;
        if (StringUtils.isNotBlank(stringValue)) {
            localDateTime = LocalDateTimeUtil.parse(stringValue.trim(), DEFAULT_PATTERN);
        }
        return localDateTime;
    }

    /**
     * java类型转换为excel类型
     */
    @Override
    public WriteCellData<?> convertToExcelData(LocalDateTime value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        String stringValue = "";
        if (Objects.nonNull(value)) {
            stringValue = LocalDateTimeUtil.format(value, DEFAULT_PATTERN);
        }
        return new WriteCellData<>(stringValue);
    }
}
