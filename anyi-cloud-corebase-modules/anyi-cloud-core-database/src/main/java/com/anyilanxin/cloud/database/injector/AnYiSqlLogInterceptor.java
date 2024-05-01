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

package com.anyilanxin.cloud.database.injector;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;

/**
 * sql日志打印拦截器
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-10-07 18:52
 * @since 1.0.0
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}), @Signature(type = StatementHandler.class, method = "getBoundSql", args = {}), @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}), @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}), @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),})
public class AnYiSqlLogInterceptor implements Interceptor {

    private static final Logger log = LoggerFactory.getLogger(AnYiSqlLogInterceptor.class);

    private static final Map<String, Object> MAP = new ConcurrentHashMap<>(2);

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 重写intercept，拦截sql，拼接完整sql语句
     *
     * @param invocation 调用
     * @return Object
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object returnValue;
        long start = System.currentTimeMillis();
        returnValue = invocation.proceed();
        long end = System.currentTimeMillis();
        long time = end - start;
        try {
            final Object[] args = invocation.getArgs();
            // 获取原始的ms
            if (!(args[0] instanceof MappedStatement)) {
                return returnValue;
            }
            MappedStatement ms = (MappedStatement) args[0];
            Object parameter = null;
            // 获取参数，if语句成立，表示sql语句有参数，参数格式是map形式
            if (invocation.getArgs().length > 1) {
                parameter = invocation.getArgs()[1];
            }
            Method method = invocation.getMethod();
            String name = method.getName();
            String commandName = ms.getSqlCommandType().name();
            if (commandName.startsWith("INSERT")) {
                name = name + "=新增";
            } else if (commandName.startsWith("UPDATE")) {
                name = name + "=修改";
            } else if (commandName.startsWith("DELETE")) {
                name = name + "=删除";
            } else if (commandName.startsWith("SELECT")) {
                name = name + "=查询";
            }
            // 获取到节点的id,即sql语句的id
            String sqlId = ms.getId();
            // BoundSql就是封装myBatis最终产生的sql类
            BoundSql boundSql = ms.getBoundSql(parameter);
            // 获取节点的配置
            Configuration configuration = ms.getConfiguration();
            // 获取到最终的sql语句
            String sql = getSql(configuration, boundSql, sqlId, time, returnValue, name);
            log.error(sql);
        } catch (Exception e) {
            log.error("拦截sql处理出错，出错原因：" + e.getMessage());
            e.printStackTrace();
        }
        return returnValue;
    }


    /**
     * 封装了一下sql语句，使得结果返回完整xml路径下的sql语句节点id + sql语句
     *
     * @param configuration 配置
     * @param boundSql      boundSql
     * @param sqlId         sqlId
     * @param time          执行时间
     * @param result        结果
     * @param name          sql操作类型
     * @return String
     */
    public static String getSql(Configuration configuration, BoundSql boundSql, String sqlId, long time, Object result, String name) {
        showSql(configuration, boundSql);
        String message = "[AnYiSqlLogInterceptor] 执行 [" + name + "] 时间 [" + formatter.format(System.currentTimeMillis()) + "] sql耗时 [" + (double) time / 1000 + "] s";
        StringBuilder str = new StringBuilder();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        str.append("\n").append("----------------------------begin【SQL Execute Message】--------------------------------\n");
        str.append("【方法】").append(sqlId).append("\n");
        str.append("【sql】").append(MAP.get("sql"));
        str.append("\n");
        str.append("【参数映射】").append(parameterMappings);
        str.append("\n");
        str.append("【参数对象】").append(String.join(", ", (List<String>) MAP.get("parameters")));
        str.append("\n");
        str.append("【结果】 ");
        if (result != null) {
            if (result instanceof List) {
                str.append("共 ").append(((List) result).size()).append(" 条记录\n");
            } else if (result instanceof Collection) {
                str.append("共 ").append(((Collection) result).size()).append(" 条记录\n");
            } else {
                str.append("共 1 条记录").append("\n");
            }
            // str.append("【结果详情】").append(result).append("\n");
        } else {
            str.append("【结果】  NULL").append("\n");
        }
        str.append("【执行信息】").append(message);
        str.append("\n");
        str.append("----------------------------end【SQL Execute Message】--------------------------------\n");
        return str.toString();
    }


    /**
     * 如果参数是String，则添加单引号，
     * 如果是日期，则转换为时间格式器并加单引号； 对参数是null和不是null的情况作了处理
     *
     * @param obj 参数
     * @return String
     */
    private static String getParameterValue(Object obj) {
        String value;
        if (obj instanceof String) {
            value = "'" + obj + "'";
        } else if (obj instanceof Date) {
            value = "'" + formatter.format(obj) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
        }
        return value;
    }


    /**
     * 进行？的替换
     *
     * @param configuration 配置
     * @param boundSql      boundSql
     * @return void
     */
    public static void showSql(Configuration configuration, BoundSql boundSql) {
        // 获取参数
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        // sql语句中多个空格都用一个空格代替
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        List<String> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(parameterMappings) && parameterObject != null) {
            // 获取类型处理器注册器，类型处理器的功能是进行java类型和数据库类型的转换
            // 如果根据parameterObject.getClass(）可以找到对应的类型，则替换
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(parameterObject)));
                list.add(parameterObject + "(" + parameterObject.getClass().getSimpleName() + ")");
            } else {
                // MetaObject主要是封装了originalObject对象，提供了get和set的方法用于获取和设置originalObject的属性值,主要支持对JavaBean、Collection、Map三种类型对象的操作
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        if (Objects.nonNull(obj)) {
                            sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
                            list.add(parameterMapping.getProperty() + "=" + obj + "(" + obj.getClass().getSimpleName() + ")");
                        } else {
                            sql = sql.replaceFirst("\\?", "null");
                            list.add(parameterMapping.getProperty() + "=null");
                        }
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        // 该分支是动态sql
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        if (Objects.nonNull(obj)) {
                            sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
                            list.add(parameterMapping.getProperty() + "=" + obj + "(" + obj.getClass().getSimpleName() + ")");
                        } else {
                            sql = sql.replaceFirst("\\?", "null");
                            list.add(parameterMapping.getProperty() + "=null");
                        }
                    } else {
                        sql = sql.replaceFirst("\\?", "缺失");
                        list.add("缺失");
                    } // 打印出缺失，提醒该参数缺失并防止错位
                }
            }
        }
        MAP.put("sql", sql);
        MAP.put("parameters", list);
    }


    @Override
    public Object plugin(Object arg0) {
        return Plugin.wrap(arg0, this);
    }


    @Override
    public void setProperties(Properties properties) {
    }
}
