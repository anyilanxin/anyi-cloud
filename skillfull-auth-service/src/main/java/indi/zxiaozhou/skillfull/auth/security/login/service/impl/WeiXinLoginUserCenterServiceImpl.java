// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.security.login.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacUserEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.mapper.RbacUserMapper;
import indi.zxiaozhou.skillfull.auth.security.login.service.IWeiXinLoginUserCenterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户信息相关
 *
 * @author zxiaozhou
 * @date 2021-01-10 16:37
 * @since JDK11
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class WeiXinLoginUserCenterServiceImpl extends ServiceImpl<RbacUserMapper, RbacUserEntity> implements IWeiXinLoginUserCenterService {

}
