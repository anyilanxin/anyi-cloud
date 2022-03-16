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

import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacOrgPageVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacOrgVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacOrgEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacOrgDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacOrgHasChildrenDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacOrgTreeDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacOrgTreePageDto;
import indi.zxiaozhou.skillfull.coredatabase.base.service.BaseService;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;

import java.util.List;

/**
 * 组织表(RbacOrg)业务层接口
 *
 * @author zxiaozhou
 * @date 2021-01-19 12:59:55
 * @since JDK11
 */
public interface IRbacOrgService extends BaseService<RbacOrgEntity> {
    /**
     * 保存
     *
     * @param vo ${@link RbacOrgVo} 组织表保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-19 12:59:55
     */
    void save(RbacOrgVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo    ${@link RbacOrgVo} 组织表更新
     * @param orgId ${@link String} 组织id
     * @param vo    ${@link RbacOrgVo} 组织表更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-19 12:59:55
     */
    void updateById(String orgId, RbacOrgVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link RbacOrgPageVo} 组织表分页查询Vo
     * @return PageDto<RbacOrgTreePageDto> ${@link PageDto< RbacOrgTreePageDto >} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-19 12:59:55
     */
    PageDto<RbacOrgTreePageDto> pageByModel(RbacOrgPageVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param orgId ${@link String} 组织id
     * @return RbacOrgDto ${@link RbacOrgDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-19 12:59:55
     */
    RbacOrgDto getById(String orgId) throws RuntimeException;


    /**
     * 通过orgId删除
     *
     * @param orgId ${@link String} 组织id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-08-28 12:20
     */
    void deleteById(String orgId) throws RuntimeException;


    /**
     * 获取组织机构信息
     *
     * @param type     ${@link Integer} 类型:0-所有,1-有效,默认1
     * @param parentId ${@link String} 父级id,为空时查顶级
     * @return List<RbacOrgHasChildrenDto> ${@link  List< RbacOrgHasChildrenDto >}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-30 23:34
     */
    List<RbacOrgHasChildrenDto> selectOrgList(Integer type, String parentId) throws RuntimeException;


    /**
     * 通过机构id修改机构状态
     *
     * @param orgId ${@link String} 机构id
     * @param type  ${@link Integer} 类型:0-禁用,1-启用
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-31 00:55
     */
    void updateOrgState(String orgId, Integer type) throws RuntimeException;


    /**
     * 获取组织机构树
     *
     * @param type ${@link Integer} 类型:0-所有,1-有效,默认1
     * @return List<RbacOrgTreeDto> ${@link List<RbacOrgTreeDto>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-02-02 16:02
     */
    List<RbacOrgTreeDto> selectOrgTreeList(Integer type) throws RuntimeException;
}