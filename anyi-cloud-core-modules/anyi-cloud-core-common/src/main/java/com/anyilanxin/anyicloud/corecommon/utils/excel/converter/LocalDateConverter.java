

package com.anyilanxin.anyicloud.corecommon.utils.excel.converter;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.time.LocalDate;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

/**
 * LocalDate转换
 *
 * @author zxh
 * @date 2021-10-28 00:39
 * @since 1.0.0
 */
public class LocalDateConverter implements Converter<LocalDate> {
    private static final String DEFAULT_PATTERN = "yyyy-MM-dd";

    @Override
    public Class<?> supportJavaTypeKey() {
        return LocalDate.class;
    }


    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }


    /**
     * excel类型转换为java类型
     */
    @Override
    public LocalDate convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        String stringValue = cellData.getStringValue();
        LocalDate localDate = null;
        if (StringUtils.isNotBlank(stringValue)) {
            localDate = LocalDateTimeUtil.parseDate(stringValue.trim(), DEFAULT_PATTERN);
        }
        return localDate;
    }


    /**
     * java类型转换为excel类型
     */
    @Override
    public WriteCellData<?> convertToExcelData(LocalDate value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        String stringValue = "";
        if (Objects.nonNull(value)) {
            stringValue = LocalDateTimeUtil.format(value, DEFAULT_PATTERN);
        }
        return new WriteCellData<>(stringValue);
    }
}
