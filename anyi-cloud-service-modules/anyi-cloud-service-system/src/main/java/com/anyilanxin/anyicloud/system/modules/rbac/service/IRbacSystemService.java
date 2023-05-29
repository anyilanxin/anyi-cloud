

package com.anyilanxin.anyicloud.system.modules.rbac.service;

import com.anyilanxin.anyicloud.database.datasource.base.service.BaseService;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacSystemPageVo;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacSystemVo;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacSystemEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacSystemDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacSystemPageDto;

import java.util.List;

/**
 * 系统(RbacSystem)业务层接口
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 11:46:37
 * @since 1.0.0
 */
public interface IRbacSystemService extends BaseService<RbacSystemEntity> {
    /**
     * 保存
     *
     * @param vo 系统保存数据
     * @throws RuntimeException
     * @author zxh
     * @date 2022-05-02 11:46:37
     */
    void save(RbacSystemVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param systemId 系统id
     * @param vo       系统更新数据
     * @throws RuntimeException
     * @author zxh
     * @date 2022-05-02 11:46:37
     */
    void updateById(String systemId, RbacSystemVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo 分页查询条件
     * @return PageDto<RbacSystemPageDto> 分页查询结果
     * @throws RuntimeException
     * @author zxh
     * @date 2022-05-02 11:46:37
     */
    PageDto<RbacSystemPageDto> pageByModel(RbacSystemPageVo vo) throws RuntimeException;


    /**
     * 查询有效的系统信息
     *
     * @return List<RbacSystemDto> 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-07-28 09:35:45
     */
    List<RbacSystemDto> selectList() throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param systemId 系统id
     * @return RbacSystemDto 查询结果
     * @throws RuntimeException
     * @author zxh
     * @date 2022-05-02 11:46:37
     */
    RbacSystemDto getById(String systemId) throws RuntimeException;


    /**
     * 通过systemId删除
     *
     * @param systemId 系统id
     * @throws RuntimeException
     * @author zxh
     * @date 2022-05-02 11:46:37
     */
    void deleteById(String systemId) throws RuntimeException;


    /**
     * 系统批量删除
     *
     * @param systemIds 系统id列表
     * @throws RuntimeException
     * @author zxh
     * @date 2022-05-02 11:46:37
     */
    void deleteBatch(List<String> systemIds) throws RuntimeException;
}
