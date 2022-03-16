// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.coredatapermflux.interceptor;


import indi.zxiaozhou.skillfull.corecommon.constant.impl.DataPermissionResultType;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.io.StringReader;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Mybatis 查询参数拦截器
 *
 * @author zhouxuanhong
 * @date 2019-11-18 11:36
 * @since JDK1.8
 */

/**
 * Mybatis 数据权限拦截器
 *
 * @author zhouxuanhong
 * @date 2019-11-18 11:36
 * @since JDK1.8
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "getBoundSql", args = {}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
})
@Slf4j
public class MybatisDataPermissionInterceptor implements Interceptor {

    /**
     * 需要数据权限的mapper
     */
    private static final ConcurrentHashMap<String, Boolean> DATA_PERMISSION = new ConcurrentHashMap<>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        Object[] args = invocation.getArgs();
        if (target instanceof Executor) {
            Object parameter = args[1];
            MappedStatement ms = (MappedStatement) args[0];
            if (ms.getSqlCommandType() == SqlCommandType.SELECT) {
                BoundSql boundSql;
                if (args.length == 4) {
                    boundSql = ms.getBoundSql(parameter);
                } else {
                    boundSql = (BoundSql) args[5];
                }
                if (this.checkDataPermission(boundSql, ms)) {
                    log.info("------------MybatisDataPermissionInterceptor------------>intercept:{}", "需要数据权限");
                    this.handleDataPermission(boundSql, invocation, ms);
                }
            }
        }
        return invocation.proceed();
    }


    private MappedStatement newMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new
                MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length > 0) {
            builder.keyProperty(ms.getKeyProperties()[0]);
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }


    /**
     * 检测是否需要数据权限
     *
     * @param boundSql ${@link BoundSql}
     * @param ms       ${@link MappedStatement}
     * @author zxiaozhou
     * @date 2021-03-02 14:23
     */
    private boolean checkDataPermission(BoundSql boundSql, MappedStatement ms) throws ClassNotFoundException {
        if (Objects.isNull(boundSql)) {
            return false;
        }
        String id = ms.getId();
        if (Objects.nonNull(DATA_PERMISSION.get(id))) {
            return DATA_PERMISSION.get(id);
        }
        // 获取执行方法
        Method method = null;
        final Class<?> clazz = Class.forName(id.substring(0, id.lastIndexOf(".")));
        for (Method meth : clazz.getMethods()) {
            if (meth.getName().equals(id.substring(id.lastIndexOf(".") + 1))) {
                method = meth;
                break;
            }
        }
        if (Objects.isNull(method)) {
            DATA_PERMISSION.put(id, false);
            return false;
        }
        Type genericReturnType = method.getGenericReturnType();
        boolean haveFirstContent = DataPermissionResultType.isHaveFirstContent(genericReturnType.getTypeName());
        DATA_PERMISSION.put(id, haveFirstContent);
        return haveFirstContent;
    }


    /**
     * 处理数据权限
     *
     * @param boundSql   ${@link BoundSql}
     * @param invocation ${@link Invocation}
     * @param ms         ${@link MappedStatement}
     * @author zxiaozhou
     * @date 2021-03-02 12:05
     */
    private void handleDataPermission(BoundSql boundSql, Invocation invocation, MappedStatement ms) throws JSQLParserException {
        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        Select select = (Select) parserManager.parse(new StringReader(boundSql.getSql()));
        PlainSelect selectBody = (PlainSelect) select.getSelectBody();
        // 组装新的 sql
        String newSql = createDataPermissionSql(boundSql.getSql());
        // 重新new一个查询语句对象
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), newSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
        // 把新的查询放到statement里
        MappedStatement newMs = newMappedStatement(ms, new BoundSqlSqlSource(newBoundSql));
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        Object[] queryArgs = invocation.getArgs();
        queryArgs[0] = newMs;
    }


    /**
     * 创建权限sql
     *
     * @param sourceSql ${@link String} 原始sql
     * @return String ${@link String}
     * @author zxiaozhou
     * @date 2021-03-02 12:12
     */
    private String createDataPermissionSql(String sourceSql) {
        return sourceSql;
    }


    /**
     * 定义一个内部辅助类，作用是包装 SQL
     *
     * @author zxiaozhou
     * @date 2021-03-02 12:06
     * @since JDK1.8
     */
    static class BoundSqlSqlSource implements SqlSource {
        private final BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }

    }


    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor || target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

}

