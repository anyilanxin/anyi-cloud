// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.corecommon.constant;

/**
 * stream常量
 *
 * @author zxiaozhou
 * @date 2021-05-29 17:04
 * @since JDK1.8
 */
public interface BindingStreamConstant {
    /**
     * stream out后缀
     */
    String OUT_SUFFIX = "-out-0";

    /**
     * stream out前缀
     */
    String IN_SUFFIX = "-in-0";

    /**
     * 处理路由stream
     */
    String ROUTER_PROCESS = "routerProcess";

    /**
     * 处理后台指令权限刷新
     */
    String AUTH_ACTION_PROCESS = "authActionProcess";

    /**
     * 处理socket消息
     */
    String SOCKET_PROCESS = "socketProcess";

    /**
     * 流程消息处理
     */
    String PROCESS_MSG_PROCESS = "processMsgProcess";

    /**
     * 操作日志处理
     */
    String OPERATE_LOG_PROCESS = "operateLogProcess";

    /**
     * 授权日志处理
     */
    String AUTH_LOG_PROCESS = "authLogProcess";

    /**
     * 发起流程处理
     */
    String SUBMIT_PROCESS = "submitProcess";


    /**
     * 发起流程处理后响应结果
     */
    String SUBMIT_PROCESS_RESULT = "submitProcessResult";

    /**
     * 系统配置
     */
    String BASE_CONFIG_PROCESS = "baseConfigProcess";

    /**
     * 刷新网关doc
     */
    String REFRESH_DOC_PROCESS = "refreshDocProcess";
}
