// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.system.modules.manage.service;

import indi.zxiaozhou.skillfull.coredatabase.base.service.BaseService;
import indi.zxiaozhou.skillfull.system.modules.manage.controller.vo.ManageRouteVo;
import indi.zxiaozhou.skillfull.system.modules.manage.entity.ManageRouteEntity;
import indi.zxiaozhou.skillfull.system.modules.manage.service.dto.ManageRouteDto;

import java.util.List;

/**
 * 路由(ManageRoute)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2021-12-19 00:22:16
 * @since JDK1.8
 */
public interface IManageRouteService extends BaseService<ManageRouteEntity> {
    /**
     * 保存
     *
     * @param vo ${@link ManageRouteVo} 路由保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-12-19 00:22:16
     */
    void save(ManageRouteVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo      ${@link ManageRouteVo} 路由更新
     * @param routeId ${@link String} 路由id
     * @param vo      ${@link ManageRouteVo} 路由更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-12-19 00:22:16
     */
    void updateById(String routeId, ManageRouteVo vo) throws RuntimeException;

    /**
     * 条件查询多条
     *
     * @param serviceId ${@link String} 服务id
     * @return List<ManageRouteDto> ${@link List<ManageRouteDto>} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-12-19 00:22:16
     */
    List<ManageRouteDto> selectList(String serviceId) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param routeId ${@link String} 路由id
     * @return ManageRouteDto ${@link ManageRouteDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-12-19 00:22:16
     */
    ManageRouteDto getById(String routeId) throws RuntimeException;


    /**
     * 通过routeId删除
     *
     * @param routeId ${@link String} 路由id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-12-19 00:22:16
     */
    void deleteById(String routeId) throws RuntimeException;


    /**
     * 通过serviceId删除
     *
     * @param serviceId ${@link String} 服务id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-12-19 00:22:16
     */
    void deleteByServiceId(String serviceId) throws RuntimeException;


    /**
     * 修改路由状态
     *
     * @param routeId ${@link String} 路由id
     * @param state   ${@link Integer} 操作类型:0-禁止,1-启用
     * @author zxiaozhou
     * @date 2021-12-19 17:41
     */
    void updateStatus(String routeId, Integer state);
}
