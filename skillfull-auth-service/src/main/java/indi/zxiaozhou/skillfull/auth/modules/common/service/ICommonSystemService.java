package indi.zxiaozhou.skillfull.auth.modules.common.service;

import indi.zxiaozhou.skillfull.auth.modules.common.controller.vo.CommonSystemPageVo;
import indi.zxiaozhou.skillfull.auth.modules.common.controller.vo.CommonSystemVo;
import indi.zxiaozhou.skillfull.auth.modules.common.entity.CommonSystemEntity;
import indi.zxiaozhou.skillfull.auth.modules.common.service.dto.CommonSystemDto;
import indi.zxiaozhou.skillfull.auth.modules.common.service.dto.CommonSystemPageDto;
import indi.zxiaozhou.skillfull.coredatabase.base.service.BaseService;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;

import java.util.List;

/**
 * 系统(CommonSystem)业务层接口
 *
 * @author zxiaozhou
 * @date 2021-07-28 09:35:45
 * @since JDK1.8
 */
public interface ICommonSystemService extends BaseService<CommonSystemEntity> {
    /**
     * 保存
     *
     * @param vo ${@link CommonSystemVo} 系统保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-07-28 09:35:45
     */
    void save(CommonSystemVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo       ${@link CommonSystemVo} 系统更新
     * @param systemId ${@link String} 系统id
     * @param vo       ${@link CommonSystemVo} 系统更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-07-28 09:35:45
     */
    void updateById(String systemId, CommonSystemVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link CommonSystemPageVo} 系统分页查询Vo
     * @return PageDto<CommonSystemPageDto> ${@link PageDto<CommonSystemPageDto>} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-07-28 09:35:45
     */
    PageDto<CommonSystemPageDto> pageByModel(CommonSystemPageVo vo) throws RuntimeException;


    /**
     * 查询有效的系统信息
     *
     * @return List<CommonSystemDto> ${@link List<CommonSystemDto>} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-07-28 09:35:45
     */
    List<CommonSystemDto> selectList() throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param systemId ${@link String} 系统id
     * @return CommonSystemDto ${@link CommonSystemDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-07-28 09:35:45
     */
    CommonSystemDto getById(String systemId) throws RuntimeException;


    /**
     * 通过systemId删除
     *
     * @param systemId ${@link String} 系统id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-07-28 09:35:45
     */
    void deleteById(String systemId) throws RuntimeException;
}
