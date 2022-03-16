// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.corecommon.utils.excel.listener;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
    /**
     * 读取后数据处理类
     */
    private final ImportService<I, R> service;

    /**
     * 读取结果集
     */
    private final List<I> importData = new ArrayList<>(16);

    /**
     * 处理后结果集
     */
    private List<R> saveData;

    public BasicExcelImportListener(ImportService<I, R> service) {
        this.service = service;
    }


    @Override
    public void invoke(I i, AnalysisContext analysisContext) {
        log.debug("------------当前读取到的数据------{}------>\n{}", analysisContext.readRowHolder().getRowIndex(), JSONObject.toJSONString(i, SerializerFeature.WriteMapNullValue));
        if (Objects.nonNull(i)) {
            importData.add(i);
        }
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("------------读取到的全部数据------------>\n{}", JSONObject.toJSONString(importData, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(importData)) {
            log.error("------------------------>当前读取数据结果为0，不进行处理");
            saveData = Collections.emptyList();
            return;
        }
        saveData = service.saveData(importData);
        log.debug("------------数据处理返回结果------------>\n{}", JSONObject.toJSONString(saveData, SerializerFeature.WriteMapNullValue));
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
