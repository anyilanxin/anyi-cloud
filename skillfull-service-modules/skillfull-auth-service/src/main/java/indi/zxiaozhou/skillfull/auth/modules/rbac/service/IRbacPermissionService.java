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

import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacPermissionPageVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacPermissionVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacPermissionEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacPermissionDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacPermissionPageDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacPermissionTreeDto;
import indi.zxiaozhou.skillfull.corecommon.base.model.common.ActionModel;
import indi.zxiaozhou.skillfull.coredatabase.base.service.BaseService;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 权限表(Permission)业务层接口
 *
 * @author zxiaozhou
 * @date 2020-09-26 17:16:15
 * @since JDK11
 */
public interface IRbacPermissionService extends BaseService<RbacPermissionEntity> {
    /**
     * 保存
     *
     * @param vo ${@link RbacPermissionVo} 权限表保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-09-26 17:16:15
     */
    void save(RbacPermissionVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo           ${@link RbacPermissionVo} 权限表更新
     * @param permissionId ${@link String} 权限id
     * @param vo           ${@link RbacPermissionVo} 权限表更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-09-26 17:16:15
     */
    void updateById(String permissionId, RbacPermissionVo vo) throws RuntimeException;

    /**
     * 通过id查询详情
     *
     * @param permissionId ${@link String} 权限id
     * @return RbacPermissionDto ${@link RbacPermissionDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-09-26 17:16:15
     */
    RbacPermissionDto getById(String permissionId) throws RuntimeException;

    /**
     * 通过权限id修改状态
     *
     * @param permissionId ${@link String} 权限id
     * @param type         ${@link Integer} 操作类型:0-禁用,1-启用
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-10-31 20:57
     */
    void updatePermissionState(String permissionId, Integer type) throws RuntimeException;

    /**
     * 通过permissionId删除
     *
     * @param permissionId ${@link String} 权限id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-08-28 12:20
     */
    void deleteById(String permissionId) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link RbacPermissionPageVo} 权限表分页查询Vo
     * @return PageDto<RbacPermissionPageDto> ${@link PageDto< RbacPermissionPageDto >} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-09-26 17:16:15
     */
    PageDto<RbacPermissionPageDto> pageByModel(RbacPermissionPageVo vo) throws RuntimeException;


    /**
     * 获取权限树
     *
     * @param type     ${@link String} 类型:0-目录,1-菜单,2-按钮，多个英文逗号隔开
     * @param systemId ${@link String} 系统id
     * @param status   ${@link Integer} 状态:1-有效、2-所有,默认2
     * @return List<RbacPermissionTreeDto> ${@link List< RbacPermissionTreeDto >}
     * @author zxiaozhou
     * @date 2020-10-07 20:23
     */
    List<RbacPermissionTreeDto> getPermissionTree(String type, String systemId, Integer status);


    /**
     * 获取所有后台权限指令
     *
     * @return Map<String, Map < String, Set < ActionModel>>> ${@link Map<String, Map<String, Set<ActionModel>>>}
     * @author zxiaozhou
     * @date 2021-07-09 16:02
     */
    Map<String, Map<String, Set<ActionModel>>> getBackgroundAllActions();


    /**
     * 刷新权限
     *
     * @param force ${@link Boolean} 是否强制刷新:true-强制立马刷新,false-如果上次刷新距离本次5分钟,当前刷新无效
     * @author zxiaozhou
     * @date 2021-07-09 20:09
     */
    void refreshAuth(boolean force);
}