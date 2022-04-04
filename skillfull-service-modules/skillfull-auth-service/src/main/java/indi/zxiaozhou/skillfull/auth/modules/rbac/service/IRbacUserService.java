// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.rbac.service;

import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacUserPageVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacUserVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacUserEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacUserDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacUserListDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacUserPageDto;
import indi.zxiaozhou.skillfull.coredatabase.base.service.BaseService;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;

import java.util.List;

/**
 * 用户表(User)业务层接口
 *
 * @author zxiaozhou
 * @date 2020-09-26 17:16:17
 * @since JDK11
 */
public interface IRbacUserService extends BaseService<RbacUserEntity> {
    /**
     * 保存
     *
     * @param vo ${@link RbacUserVo} 用户表保存
     * @return String ${@link String} 密码
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-09-26 17:16:17
     */
    String save(RbacUserVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo     ${@link RbacUserVo} 用户表更新
     * @param userId ${@link String} 用户id
     * @param vo     ${@link RbacUserVo} 用户表更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-09-26 17:16:17
     */
    void updateById(String userId, RbacUserVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link RbacUserPageVo} 用户表分页查询Vo
     * @return PageDto<RbacUserPageDto> ${@link PageDto< RbacUserPageDto >} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-09-26 17:16:17
     */
    PageDto<RbacUserPageDto> pageByModel(RbacUserPageVo vo) throws RuntimeException;

    /**
     * 通过id查询详情
     *
     * @param userId ${@link String} 用户id
     * @return RbacUserDto ${@link RbacUserDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-09-26 17:16:17
     */
    RbacUserDto getById(String userId) throws RuntimeException;


    /**
     * 通过userId删除
     *
     * @param userId ${@link String} 用户id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-08-28 12:20
     */
    void deleteById(String userId) throws RuntimeException;


    /**
     * 重置用户密码
     *
     * @param userId ${@link String} 用户id
     * @return String ${@link String}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-19 18:34
     */
    String resetPassword(String userId) throws RuntimeException;

    /**
     * 用户解冻或冻结
     *
     * @param userId ${@link String} 用户id
     * @param type   ${@link Integer} 类型:2-冻结,1-解冻
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-29 17:49
     */
    void updateUserState(String userId, Integer type) throws RuntimeException;

    /**
     * 根据多个用户id查询用户信息
     *
     * @param userIds ${@link List<String>} 用户ids
     * @return List<RbacUserListDto> ${@link List<RbacUserListDto>}
     * @author zxiaozhou
     * @date 2021-05-21 01:47
     */
    List<RbacUserListDto> getListByIds(List<String> userIds) throws RuntimeException;


    /**
     * 更具真实姓名模糊查询用户信息
     *
     * @param realName ${@link String} 用户真实姓名
     * @return List<RbacUserListDto> ${@link List<RbacUserListDto>}
     * @author zxiaozhou
     * @date 2021-05-21 01:47
     */
    List<RbacUserListDto> getUserByRealName(String realName) throws RuntimeException;
}