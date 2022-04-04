// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.rbac.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacCorrelateUserEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.mapper.RbacCorrelateUserMapper;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.IRbacCorrelateUserService;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.mapstruct.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户关联关系表(RbacCorrelateUser)业务层实现
 *
 * @author zxiaozhou
 * @date 2020-11-02 09:37:33
 * @since JDK11
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class, Error.class})
public class RbacCorrelateUserServiceImpl extends ServiceImpl<RbacCorrelateUserMapper, RbacCorrelateUserEntity> implements IRbacCorrelateUserService {
    private final RbacCorrelateUserDtoMap dtoMap;
    private final RbacCorrelateUserPageDtoMap pageDtoMap;
    private final RbacCorrelateUserVoMap voMap;
    private final RbacCorrelateUserQueryVoMap queryVoMap;
    private final RbacCorrelateUserPageVoMap pageVoMap;
    private final RbacCorrelateUserMapper mapper;

}