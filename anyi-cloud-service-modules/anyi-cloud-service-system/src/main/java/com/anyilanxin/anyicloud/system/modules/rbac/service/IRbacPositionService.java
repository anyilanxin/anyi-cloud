

package com.anyilanxin.anyicloud.system.modules.rbac.service;

import com.anyilanxin.anyicloud.database.datasource.base.service.BaseService;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacPositionPageVo;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacPositionVo;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacPositionEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacPositionDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacPositionPageDto;

import java.util.List;

/**
 * 职位表(RbacPosition)业务层接口
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:20
 * @since 1.0.0
 */
public interface IRbacPositionService extends BaseService<RbacPositionEntity> {
    /**
     * 保存
     *
     * @param vo 职位表保存数据
     * @throws RuntimeException
     * @author zxh
     * @date 2022-05-02 16:12:20
     */
    void save(RbacPositionVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param positionId 职位id
     * @param vo         职位表更新数据
     * @throws RuntimeException
     * @author zxh
     * @date 2022-05-02 16:12:20
     */
    void updateById(String positionId, RbacPositionVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo 分页查询条件
     * @return PageDto<RbacPositionPageDto> 分页查询结果
     * @throws RuntimeException
     * @author zxh
     * @date 2022-05-02 16:12:20
     */
    PageDto<RbacPositionPageDto> pageByModel(RbacPositionPageVo vo) throws RuntimeException;


    /**
     * 条件查询多条
     *
     * @return List<RbacPositionDto> 查询结果
     * @throws RuntimeException
     * @author zxh
     * @date 2021-01-19 18:17:57
     */
    List<RbacPositionDto> getAllList() throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param positionId 职位id
     * @return RbacPositionDto 查询结果
     * @throws RuntimeException
     * @author zxh
     * @date 2022-05-02 16:12:20
     */
    RbacPositionDto getById(String positionId) throws RuntimeException;


    /**
     * 通过positionId删除
     *
     * @param positionId 职位id
     * @throws RuntimeException
     * @author zxh
     * @date 2022-05-02 16:12:20
     */
    void deleteById(String positionId) throws RuntimeException;


    /**
     * 职位表批量删除
     *
     * @param positionIds 职位id列表
     * @throws RuntimeException
     * @author zxh
     * @date 2022-05-02 16:12:20
     */
    void deleteBatch(List<String> positionIds) throws RuntimeException;


    /**
     * 修改职位状态
     *
     * @param positionId ${@link String} 职位id
     * @param type       ${@link Integer} 类型:0-禁用,1-启用
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-01-30 00:39
     */
    void updatePositionState(String positionId, Integer type) throws RuntimeException;
}
