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

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.zxiaozhou.skillfull.auth.core.constant.impl.RoleCorrelateType;
import indi.zxiaozhou.skillfull.auth.core.constant.impl.UserCorrelateType;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacCorrelateRoleQueryVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacUserPageVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacUserVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacCorrelateRoleEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacCorrelateUserEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacOrgEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacUserEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.mapper.RbacCorrelateRoleMapper;
import indi.zxiaozhou.skillfull.auth.modules.rbac.mapper.RbacCorrelateUserMapper;
import indi.zxiaozhou.skillfull.auth.modules.rbac.mapper.RbacOrgMapper;
import indi.zxiaozhou.skillfull.auth.modules.rbac.mapper.RbacUserMapper;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.IRbacCorrelateRoleService;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.IRbacCorrelateUserService;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.IRbacUserService;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.ISyncProcessService;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.*;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.mapstruct.*;
import indi.zxiaozhou.skillfull.auth.utils.CryptAuthUtils;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 用户表(User)业务层实现
 *
 * @author zxiaozhou
 * @date 2020-09-26 17:16:17
 * @since JDK11
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class, Error.class})
public class RbacUserServiceImpl extends ServiceImpl<RbacUserMapper, RbacUserEntity> implements IRbacUserService {
    private final RbacUserDtoMap dtoMap;
    private final RbacUserListDtoMap listDtoMap;
    private final RbacUserPageDtoMap pageDtoMap;
    private final RbacUserVoMap voMap;
    private final RbacUserQueryVoMap queryVoMap;
    private final RbacUserPageVoMap pageVoMap;
    private final RbacOrgMapper orgMapper;
    private final RbacUserMapper mapper;
    private final RbacCorrelateUserOrgDtoMap correlateUserOrgDtoMap;
    private final RbacCorrelateUserPositionDtoMap correlateUserPositionDtoMap;
    private final IRbacCorrelateUserService correlateUserService;
    private final RbacCorrelateUserMapper correlateUserMapper;
    private final IRbacCorrelateRoleService correlateRoleService;
    private final RbacCorrelateRoleMapper rbacCorrelateRoleMapper;


    @Override
    @GlobalTransactional
    public String save(RbacUserVo vo) throws RuntimeException {
        RbacUserEntity entity = voMap.aToB(vo);
        this.checkData(entity);
        String newPassword = vo.getPassword();
        if (StringUtils.isBlank(newPassword)) {
            throw new ResponseException(Status.VERIFICATION_FAILED, "密码不能为空");
        }
        CryptAuthUtils.PasswordInfo passwordInfo = CryptAuthUtils.getPasswordInfo(newPassword);
        entity.setPassword(passwordInfo.getEncodedPassword());
        entity.setSalt(passwordInfo.getSalt());
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR);
        }
        // 管理关系表处理
        this.subData(vo, entity.getUserId(), false);
        // 同步流程引擎
        syncService.addOrUpdateUser(entity.getUserId());
        return newPassword;
    }

    /**
     * 数据校验
     *
     * @param entity ${@link RbacUserEntity}
     * @author zxiaozhou
     * @date 2021-01-19 14:33
     */
    public void checkData(RbacUserEntity entity) {
        // 验证用户名是否重复
        LambdaQueryWrapper<RbacUserEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RbacUserEntity::getUserName, entity.getUserName());
        if (StringUtils.isNotBlank(entity.getUserId())) {
            lambdaQueryWrapper.ne(RbacUserEntity::getUserId, entity.getUserId());
        }
        RbacUserEntity one = this.getOne(lambdaQueryWrapper);
        if (Objects.nonNull(one)) {
            throw new ResponseException(Status.VERIFICATION_FAILED, "用户名已经存在:" + entity.getUserName());
        }
        // 验证手机号码是否存在
        lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RbacUserEntity::getPhone, entity.getPhone());
        if (StringUtils.isNotBlank(entity.getUserId())) {
            lambdaQueryWrapper.ne(RbacUserEntity::getUserId, entity.getUserId());
        }
        one = this.getOne(lambdaQueryWrapper);
        if (Objects.nonNull(one)) {
            throw new ResponseException(Status.VERIFICATION_FAILED, "手机号已经存在:" + entity.getUserName());
        }
    }


    /**
     * 管理关系处理
     *
     * @param vo       ${@link RbacUserVo} 用户信息
     * @param userId   ${@link String} 用户id
     * @param isDelete ${@link Boolean} false-不是删除,true-删除操作
     * @author zxiaozhou
     * @date 2021-01-19 14:33
     */
    public void subData(RbacUserVo vo, String userId, boolean isDelete) {
        // 删除所有关联信息
        if (StringUtils.isNotBlank(userId)) {
            LambdaQueryWrapper<RbacCorrelateUserEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(RbacCorrelateUserEntity::getUserId, userId);
            List<RbacCorrelateUserEntity> correlateUsers = correlateUserService.list(lambdaQueryWrapper);
            if (CollectionUtil.isNotEmpty(correlateUsers)) {
                Set<String> ids = new HashSet<>(4);
                correlateUsers.forEach(v -> ids.add(v.getCorrelateUserId()));
                int i = correlateUserMapper.physicalDeleteBatchIds(ids);
                if (i <= 0) {
                    throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除关联关系历史数据失败");
                }
            }
            LambdaQueryWrapper<RbacCorrelateRoleEntity> lambdaRoleQueryWrapper = new LambdaQueryWrapper<>();
            lambdaRoleQueryWrapper.eq(RbacCorrelateRoleEntity::getCorrelateId, userId);
            List<RbacCorrelateRoleEntity> list = correlateRoleService.list(lambdaRoleQueryWrapper);
            if (CollectionUtil.isNotEmpty(list)) {
                Set<String> ids = new HashSet<>(4);
                list.forEach(v -> ids.add(v.getCorrelateRoleId()));
                int i = rbacCorrelateRoleMapper.physicalDeleteBatchIds(ids);
                if (i <= 0) {
                    throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除关联关系历史数据失败");
                }
            }
        }
        if (!isDelete && Objects.nonNull(vo)) {
            // 处理机构关联
            if (StringUtils.isNotBlank(vo.getOrgId())) {
                RbacCorrelateUserEntity correlateUserEntity = new RbacCorrelateUserEntity();
                correlateUserEntity.setUserId(userId);
                correlateUserEntity.setCorrelateId(vo.getOrgId());
                correlateUserEntity.setCorrelateType(UserCorrelateType.ORG.getType());
                boolean b = correlateUserService.save(correlateUserEntity);
                if (!b) {
                    throw new ResponseException(Status.DATABASE_BASE_ERROR, "挂接机构失败");
                }
            }
            // 处理职位关联
            if (CollectionUtil.isNotEmpty(vo.getPositionIds())) {
                List<RbacCorrelateUserEntity> correlateUserEntities = new ArrayList<>(vo.getPositionIds().size());
                vo.getPositionIds().forEach(v -> {
                    RbacCorrelateUserEntity correlateUserEntity = new RbacCorrelateUserEntity();
                    correlateUserEntity.setUserId(userId);
                    correlateUserEntity.setCorrelateId(v);
                    correlateUserEntity.setCorrelateType(UserCorrelateType.POSITION.getType());
                    correlateUserEntities.add(correlateUserEntity);
                });
                boolean b = correlateUserService.saveBatch(correlateUserEntities);
                if (!b) {
                    throw new ResponseException(Status.DATABASE_BASE_ERROR, "挂接职位失败");
                }
            }
            // 处理角色关联
            if (CollectionUtil.isNotEmpty(vo.getRoleIds())) {
                List<RbacCorrelateRoleEntity> roleEntities = new ArrayList<>(vo.getRoleIds().size());
                vo.getRoleIds().forEach(v -> {
                    RbacCorrelateRoleEntity roleEntity = new RbacCorrelateRoleEntity();
                    roleEntity.setRoleId(v);
                    roleEntity.setCorrelateType(RoleCorrelateType.PERSON.getType());
                    roleEntity.setCorrelateId(userId);
                    roleEntities.add(roleEntity);
                });
                boolean b = correlateRoleService.saveBatch(roleEntities);
                if (!b) {
                    throw new ResponseException(Status.DATABASE_BASE_ERROR, "挂接角色失败");
                }
            }
        }

    }

    @Override
    @GlobalTransactional
    public void updateById(String userId, RbacUserVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(userId);
        // 更新数据
        RbacUserEntity entity = voMap.aToB(vo);
        entity.setUserId(userId);
        // 验证数据是否重复
        this.checkData(entity);
        System.out.println("---vo---" + entity);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR);
        }
        // 管理关系表处理
        this.subData(vo, entity.getUserId(), false);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<RbacUserPageDto> pageByModel(RbacUserPageVo vo) throws RuntimeException {
        // 获取机构treeCode
        if (StringUtils.isNotBlank(vo.getOrgId())) {
            RbacOrgEntity rbacOrgEntity = orgMapper.selectById(vo.getOrgId());
            vo.setOrgId(rbacOrgEntity.getOrgSysCode());
        }
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public RbacUserDto getById(String userId) throws RuntimeException {
        RbacUserEntity byId = super.getById(userId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR);
        }
        RbacUserDto rbacUserDto = dtoMap.bToA(byId);
        // 获取关联职位信息
        List<RbacCorrelateUserPositionDto> correlatePosition = correlateUserMapper.getCorrelatePosition(rbacUserDto.getUserId());
        if (CollectionUtil.isNotEmpty(correlatePosition)) {
            List<String> positionIds = new ArrayList<>(correlatePosition.size());
            correlatePosition.forEach(v -> positionIds.add(v.getPositionId()));
            rbacUserDto.setPositionIds(positionIds);
            rbacUserDto.setPositionInfos(correlateUserPositionDtoMap.bToA(correlatePosition));
        }
        // 过去关联机构信息
        RbacCorrelateUserOrgDto correlateOrg = correlateUserMapper.getCorrelateOrg(rbacUserDto.getUserId());
        if (Objects.nonNull(correlateOrg)) {
            rbacUserDto.setOrgInfo(correlateUserOrgDtoMap.bToA(correlateOrg));
            rbacUserDto.setOrgId(correlateOrg.getOrgId());
        }
        // 获取角色信息
        RbacCorrelateRoleQueryVo vo = RbacCorrelateRoleQueryVo.builder().correlateId(userId).correlateType(RoleCorrelateType.PERSON.getType()).build();
        List<RbacCorrelateRoleDto> rbacCorrelateRoleDtos = correlateRoleService.selectListByModel(vo);
        if (CollectionUtil.isNotEmpty(rbacCorrelateRoleDtos)) {
            rbacUserDto.setRoleInfos(rbacCorrelateRoleDtos);
            Set<String> roleIds = new HashSet<>(rbacCorrelateRoleDtos.size());
            rbacCorrelateRoleDtos.forEach(v -> roleIds.add(v.getRoleId()));
            rbacUserDto.setRoleIds(new ArrayList<>(roleIds));
        }
        // 数据处理
        return rbacUserDto;
    }


    @Override
    @GlobalTransactional
    public void deleteById(String userId) throws RuntimeException {
        // 查询数据是否存在
        RbacUserEntity byId = super.getById(userId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR);
        }
        boolean b = this.removeById(userId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR);
        }
        // 管理关系表处理
        this.subData(null, byId.getUserId(), false);
    }


    @Override
    public String resetPassword(String userId) throws RuntimeException {
        // 查询用户是否存在
        this.getById(userId);
        String newPassword = RandomUtil.randomString(8);
        // 插入新密码
        CryptAuthUtils.PasswordInfo passwordInfo = CryptAuthUtils.getPasswordInfo(newPassword);
        RbacUserEntity entity = new RbacUserEntity();
        entity.setPassword(passwordInfo.getEncodedPassword());
        entity.setSalt(passwordInfo.getSalt());
        entity.setUserId(userId);
        entity.setIsInitialPassword(1);
        boolean b = this.updateById(entity);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "重置密码失败");
        }
        return newPassword;
    }


    @Override
    public void updateUserState(String userId, Integer type) throws RuntimeException {
        // 查询用户是否存在
        this.getById(userId);
        RbacUserEntity entity = new RbacUserEntity();
        entity.setUserId(userId);
        entity.setUserStatus(type);
        boolean b = super.updateById(entity);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "用户状态修改失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<RbacUserListDto> getListByIds(List<String> userIds) throws RuntimeException {
        LambdaQueryWrapper<RbacUserEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(RbacUserEntity::getUserId, userIds);
        return listDtoMap.bToA(this.list(lambdaQueryWrapper));
    }


    @Override
    public List<RbacUserListDto> getUserByRealName(String realName) throws RuntimeException {
        LambdaQueryWrapper<RbacUserEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(RbacUserEntity::getRealName, realName);
        return listDtoMap.bToA(this.list(lambdaQueryWrapper));
    }
}