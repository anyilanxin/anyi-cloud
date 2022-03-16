// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.common.service;

import indi.zxiaozhou.skillfull.auth.modules.common.controller.vo.CommonUserIdentityPageVo;
import indi.zxiaozhou.skillfull.auth.modules.common.controller.vo.CommonUserIdentityQueryVo;
import indi.zxiaozhou.skillfull.auth.modules.common.controller.vo.CommonUserIdentityVo;
import indi.zxiaozhou.skillfull.auth.modules.common.entity.CommonUserIdentityEntity;
import indi.zxiaozhou.skillfull.auth.modules.common.service.dto.CommonUserIdentityDto;
import indi.zxiaozhou.skillfull.auth.modules.common.service.dto.CommonUserIdentityPageDto;
import indi.zxiaozhou.skillfull.coredatabase.base.service.BaseService;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;

import java.util.List;

/**
 * 实名信息表(CommonUserIdentity)业务层接口
 *
 * @author zxiaozhou
 * @date 2021-02-12 19:41:42
 * @since JDK1.8
 */
public interface ICommonUserIdentityService extends BaseService<CommonUserIdentityEntity> {
    /**
     * 保存
     *
     * @param vo ${@link CommonUserIdentityVo} 实名信息表保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-02-12 19:41:42
     */
    void save(CommonUserIdentityVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo         ${@link CommonUserIdentityVo} 实名信息表更新
     * @param identityId ${@link String} 实名信息id
     * @param vo         ${@link CommonUserIdentityVo} 实名信息表更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-02-12 19:41:42
     */
    void updateById(String identityId, CommonUserIdentityVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link CommonUserIdentityPageVo} 实名信息表分页查询Vo
     * @return PageDto<CommonUserIdentityPageDto> ${@link PageDto<CommonUserIdentityPageDto>} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-02-12 19:41:42
     */
    PageDto<CommonUserIdentityPageDto> pageByModel(CommonUserIdentityPageVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link CommonUserIdentityPageVo} 实名信息表分页查询Vo
     * @return PageDto<CommonUserIdentityPageDto> ${@link PageDto<CommonUserIdentityPageDto>} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-02-12 19:41:42
     */
    PageDto<CommonUserIdentityPageDto> page(CommonUserIdentityPageVo vo) throws RuntimeException;


    /**
     * 条件查询多条
     *
     * @param vo ${@link CommonUserIdentityQueryVo} 实名信息表条件查询Vo
     * @return List<CommonUserIdentityDto> ${@link List<CommonUserIdentityDto>} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-02-12 19:41:42
     */
    List<CommonUserIdentityDto> selectListByModel(CommonUserIdentityQueryVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param identityId ${@link String} 实名信息id
     * @return CommonUserIdentityDto ${@link CommonUserIdentityDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-02-12 19:41:42
     */
    CommonUserIdentityDto getById(String identityId) throws RuntimeException;


    /**
     * 通过identityId删除
     *
     * @param identityId ${@link String} 实名信息id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-08-28 12:20
     */
    void deleteById(String identityId) throws RuntimeException;


    /**
     * 实名信息表批量删除
     *
     * @param identityIds ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-10-24 22:59
     */
    void deleteBatch(List<String> identityIds) throws RuntimeException;
}