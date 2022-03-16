// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.security.login.service;

import indi.zxiaozhou.skillfull.auth.security.login.controller.vo.OnlineUserPageVo;
import indi.zxiaozhou.skillfull.auth.security.login.service.dto.OnlineUserInfoDto;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;

/**
 * 用户公共接口服务层
 *
 * @author zxiaozhou
 * @date 2021-06-03 23:57
 * @since JDK1.8
 */
public interface ILoginCommonService {
    /**
     * 分页查询在线用户信息
     *
     * @param vo ${@link OnlineUserPageVo}
     * @return PageDto<OnlineUserInfoDto> ${@link PageDto <OnlineUserInfoDto>}
     * @author zxiaozhou
     * @date 2021-02-25 17:24
     */
    PageDto<OnlineUserInfoDto> selectOnlinePage(OnlineUserPageVo vo);


    /**
     * 踢用户下线
     *
     * @param loginUnique ${@link String} 登录唯一标识
     * @author zxiaozhou
     * @date 2021-02-25 17:49
     */
    void kickOut(String loginUnique);
}
