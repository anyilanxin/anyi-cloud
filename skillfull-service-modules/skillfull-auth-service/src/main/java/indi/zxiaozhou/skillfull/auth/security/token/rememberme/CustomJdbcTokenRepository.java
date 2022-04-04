// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.security.token.rememberme;

import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

/**
 * 数据库
 *
 * @author zxiaozhou
 * @date 2020-07-17 11:34
 * @since JDK11
 */
public class CustomJdbcTokenRepository extends JdbcTokenRepositoryImpl {
    public CustomJdbcTokenRepository() {
        super();
    }
}
