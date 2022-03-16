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

import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacPositionPageVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacPositionVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacPositionEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacPositionDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacPositionPageDto;
import indi.zxiaozhou.skillfull.coredatabase.base.service.BaseService;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;

import java.util.List;

/**
 * 职位表(RbacPosition)业务层接口
 *
 * @author zxiaozhou
 * @date 2021-01-19 18:17:57
 * @since JDK11
 */
public interface IRbacPositionService extends BaseService<RbacPositionEntity> {
    /**
     * 保存
     *
     * @param vo ${@link RbacPositionVo} 职位表保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-19 18:17:57
     */
    void save(RbacPositionVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo         ${@link RbacPositionVo} 职位表更新
     * @param positionId ${@link String} 职位id
     * @param vo         ${@link RbacPositionVo} 职位表更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-19 18:17:57
     */
    void updateById(String positionId, RbacPositionVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link RbacPositionPageVo} 职位表分页查询Vo
     * @return PageDto<RbacPositionPageDto> ${@link PageDto<RbacPositionPageDto>} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-19 18:17:57
     */
    PageDto<RbacPositionPageDto> pageByModel(RbacPositionPageVo vo) throws RuntimeException;


    /**
     * 条件查询多条
     *
     * @return List<RbacPositionDto> ${@link List<RbacPositionDto>} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-19 18:17:57
     */
    List<RbacPositionDto> getAllList() throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param positionId ${@link String} 职位id
     * @return RbacPositionDto ${@link RbacPositionDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-19 18:17:57
     */
    RbacPositionDto getById(String positionId) throws RuntimeException;


    /**
     * 通过positionId删除
     *
     * @param positionId ${@link String} 职位id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-08-28 12:20
     */
    void deleteById(String positionId) throws RuntimeException;

    
    /**
     * 修改职位状态
     *
     * @param positionId ${@link String} 职位id
     * @param type       ${@link Integer} 类型:0-禁用,1-启用
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-30 00:39
     */
    void updatePositionState(String positionId, Integer type) throws RuntimeException;
}