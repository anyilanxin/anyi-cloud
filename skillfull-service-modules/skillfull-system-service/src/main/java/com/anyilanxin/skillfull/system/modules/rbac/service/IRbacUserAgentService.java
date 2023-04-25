package com.anyilanxin.skillfull.system.modules.rbac.service;

import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacUserAgentPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacUserAgentQueryVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacUserAgentVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacUserAgentEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacUserAgentDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacUserAgentPageDto;

import java.util.List;

/**
 * 用户-代理人表(RbacUserAgent)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-02 16:12:20
 * @since JDK1.8
 */
public interface IRbacUserAgentService extends BaseService<RbacUserAgentEntity> {
    /**
     * 保存
     *
     * @param vo 用户-代理人表保存数据
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:20
     */
    void save(RbacUserAgentVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param agentId 代理id
     * @param vo      用户-代理人表更新数据
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:20
     */
    void updateById(String agentId, RbacUserAgentVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo 分页查询条件
     * @return PageDto<RbacUserAgentPageDto> 分页查询结果
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:20
     */
    PageDto<RbacUserAgentPageDto> pageByModel(RbacUserAgentPageVo vo) throws RuntimeException;

    /**
     * 条件查询多条
     *
     * @param vo 用户-代理人表查询条件
     * @return List<RbacUserAgentDto> 查询结果
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:20
     */
    List<RbacUserAgentDto> selectListByModel(RbacUserAgentQueryVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param agentId 代理id
     * @return RbacUserAgentDto 查询结果
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:20
     */
    RbacUserAgentDto getById(String agentId) throws RuntimeException;


    /**
     * 通过agentId删除
     *
     * @param agentId 代理id
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:20
     */
    void deleteById(String agentId) throws RuntimeException;


    /**
     * 用户-代理人表批量删除
     *
     * @param agentIds 代理id列表
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:20
     */
    void deleteBatch(List<String> agentIds) throws RuntimeException;
}
