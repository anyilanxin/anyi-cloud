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
package com.anyilanxin.cloud.database.dataprivacy.interceptor;


import com.alibaba.fastjson2.JSONObject;
import com.anyilanxin.cloud.database.annotation.ShowProcessClass;
import com.anyilanxin.cloud.database.annotation.ShowProcessField;
import com.anyilanxin.cloud.database.dataprivacy.exception.FieldShowException;
import com.anyilanxin.cloud.database.dataprivacy.extend.IDataPrivacyCheck;
import com.anyilanxin.cloud.database.dataprivacy.extend.config.DataPrivacyCheckProperty;
import com.anyilanxin.cloud.database.dataprivacy.model.DataPrivacyModel;
import com.anyilanxin.cloud.database.dataprivacy.model.EntityInfo;
import com.anyilanxin.cloud.database.dataprivacy.utils.FiledUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Mybatis 结果拦截器
 *
 * @author zhouxuanhong
 * @date 2018-11-18 11:36
 * @since JDK1.8
 */
@Intercepts({
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})
})
@Slf4j
public class MybatisResultInterceptor implements Interceptor {

    private IDataPrivacyCheck dynamicCheck;
    private DataPrivacyCheckProperty property;

    @Autowired(required = false)
    public void setDynamicCheck(IDataPrivacyCheck dynamicCheck) {
        this.dynamicCheck = dynamicCheck;
    }

    @Autowired
    public void setProperty(DataPrivacyCheckProperty property) {
        this.property = property;
    }

    /**
     * 参数注解缓存
     */
    private static final ConcurrentHashMap<String, DataPrivacyModel> DATA_PRIVACY_INFO = new ConcurrentHashMap<>();


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Field mappedStatement = invocation.getTarget().getClass().getDeclaredField("mappedStatement");
        mappedStatement.setAccessible(true);
        Object result = invocation.proceed();
        MappedStatement ms = (MappedStatement) mappedStatement.get(invocation.getTarget());
        DataPrivacyModel dataPrivacyInfo = getDataPrivacyInfo(ms);
        if (dataPrivacyInfo.isMustProcess()) {
            // 数据隐私处理
            if (result instanceof List<?> resultList) {
                if (!CollectionUtils.isEmpty(resultList)) {
                    for (Object obj : resultList) {
                        showProcess(obj, dataPrivacyInfo);
                    }
                }
            } else if (result instanceof Set<?> resultList) {
                if (!CollectionUtils.isEmpty(resultList)) {
                    for (Object obj : resultList) {
                        showProcess(obj, dataPrivacyInfo);
                    }
                }
            } else {
                showProcess(result, dataPrivacyInfo);
            }
        }
        return result;
    }


    /**
     * 数据隐私处理
     *
     * @param object ${@link Object}
     * @author zhou
     * @date 2019-11-19 12:44
     */
    public void showProcess(Object object, DataPrivacyModel securityModel) throws FieldShowException, IllegalAccessException {
        if (Objects.nonNull(object) && object.getClass().equals(securityModel.getAClass())) {
            Set<String> fetchDataKey = property.getFetchDataKey();
            if (Objects.nonNull(this.dynamicCheck) && !CollectionUtils.isEmpty(fetchDataKey)) {
                JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(object));
                Map<String, Object> fetchMap = new HashMap<>();
                for (String key : fetchDataKey) {
                    fetchMap.put(key, jsonObject.get(key));
                }
                boolean enableShow = dynamicCheck.enableShow(fetchMap);
                if (enableShow) {
                    return;
                }
            }
            List<EntityInfo> entityInfoList = securityModel.getEntityInfoList();
            for (EntityInfo entityInfo : entityInfoList) {
                ShowProcessField annotation = entityInfo.getAnnotation();
                Field field = entityInfo.getField();
                field.setAccessible(true);
                Object showProcessObject = field.get(object);
                if (Objects.isNull(showProcessObject) || !(showProcessObject instanceof String)) {
                    continue;
                }
                String format = annotation.format();
                String replaceSymbol = annotation.replaceSymbol();
                if (annotation.multiData()) {
                    String str = String.valueOf(showProcessObject);
                    String[] split = str.split(annotation.multiDataSplit());
                    int length = split.length;
                    for (int i = 0; i < length; i++) {
                        split[i] = (String) FiledUtils.hiddenFiled(split[i], format, replaceSymbol);
                    }
                    showProcessObject = String.join(annotation.multiDataSplit(), split);
                } else {
                    showProcessObject = FiledUtils.hiddenFiled(showProcessObject, format, replaceSymbol);
                }

                field.set(object, showProcessObject);
            }
        }

    }


    @Override
    public Object plugin(Object target) {
        // 只处理ResultSetHandler
        if (target instanceof ResultSetHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }


    @Override
    public void setProperties(Properties properties) {
    }


    /**
     * 解析mapper数据隐私处理信息
     *
     * @param statement MappedStatement
     * @return {@link DataPrivacyModel }
     * @author zxh
     * @date 2023-12-19 09:46:38
     */
    private DataPrivacyModel getDataPrivacyInfo(MappedStatement statement) {
        // 执行ID
        final String id = statement.getId();
        DataPrivacyModel dataPrivacyModel = DATA_PRIVACY_INFO.get(id);
        if (Objects.nonNull(dataPrivacyModel)) {
            return dataPrivacyModel;
        }
        dataPrivacyModel = new DataPrivacyModel();
        List<ResultMap> resultMaps = statement.getResultMaps();
        if (!CollectionUtils.isEmpty(resultMaps)) {
            Class<?> aClass = resultMaps.getFirst().getType();
            ShowProcessClass encryptDecryptClass = AnnotationUtils.findAnnotation(aClass, ShowProcessClass.class);
            if (Objects.nonNull(encryptDecryptClass)) {
                dataPrivacyModel = new DataPrivacyModel();
                dataPrivacyModel.setAnnotation(encryptDecryptClass);
                dataPrivacyModel.setAClass(aClass);
                dataPrivacyModel.setMustProcess(true);
                Field[] fields = aClass.getDeclaredFields();
                List<EntityInfo> entityInfos = new ArrayList<>();
                for (Field field : fields) {
                    // 判断字段是否需要解密
                    ShowProcessField showProcessField = AnnotationUtils.findAnnotation(field, ShowProcessField.class);
                    if (Objects.isNull(showProcessField)) {
                        continue;
                    }
                    EntityInfo entityInfo = new EntityInfo();
                    entityInfo.setAnnotation(showProcessField);
                    entityInfo.setField(field);
                    entityInfo.setFiledName(field.getName());
                    entityInfos.add(entityInfo);
                }
                dataPrivacyModel.setEntityInfoList(entityInfos);
            }
        }
        // 存入缓存
        DATA_PRIVACY_INFO.put(id, dataPrivacyModel);
        return dataPrivacyModel;
    }
}

