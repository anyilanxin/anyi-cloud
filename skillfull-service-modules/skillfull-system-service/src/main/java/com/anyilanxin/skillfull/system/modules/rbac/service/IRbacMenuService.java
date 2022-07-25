// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.rbac.service;

import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacMenuPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacMenuVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacMenuEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacMenuDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacMenuPageDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacMenuTreeDto;

import java.util.List;

/**
 * 菜单表(RbacMenu)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-05-02 16:12:21
 * @since JDK1.8
 */
public interface IRbacMenuService extends BaseService<RbacMenuEntity> {
    /**
     * 保存
     *
     * @param vo 菜单表保存数据
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    void save(RbacMenuVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param menuId 权限id
     * @param vo     菜单表更新数据
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    void updateById(String menuId, RbacMenuVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo 分页查询条件
     * @return PageDto<RbacMenuPageDto> 分页查询结果
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    PageDto<RbacMenuPageDto> pageByModel(RbacMenuPageVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param menuId 权限id
     * @return RbacMenuDto 查询结果
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    RbacMenuDto getById(String menuId) throws RuntimeException;


    /**
     * 通过menuId删除
     *
     * @param menuId 权限id
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    void deleteById(String menuId) throws RuntimeException;


    /**
     * 菜单表批量删除
     *
     * @param menuIds 权限id列表
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    void deleteBatch(List<String> menuIds) throws RuntimeException;


    /**
     * 获取权限树
     *
     * @param type     类型:0-目录,1-菜单,2-按钮，多个英文逗号隔开
     * @param systemId 系统id
     * @param status   状态:1-有效、2-所有,默认2
     * @return List<RbacMenuTreeDto>
     * @author zxiaozhou
     * @date 2020-10-07 20:23
     */
    List<RbacMenuTreeDto> getMenuTree(String type, String systemId, Integer status);
}
