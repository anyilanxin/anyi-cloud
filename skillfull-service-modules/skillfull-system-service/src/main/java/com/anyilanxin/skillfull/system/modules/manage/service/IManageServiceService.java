// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.manage.service;

import com.anyilanxin.skillfull.corecommon.model.system.ManageSwaggerInfoModel;
import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.system.modules.manage.controller.vo.ManageServicePageVo;
import com.anyilanxin.skillfull.system.modules.manage.controller.vo.ManageServiceVo;
import com.anyilanxin.skillfull.system.modules.manage.entity.ManageServiceEntity;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.ManageServiceDto;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.ManageServicePageDto;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.SystemStatDto;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.ValidServiceInfoDto;

import java.util.List;
import java.util.Map;

/**
 * 服务管理(ManageService)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2021-12-19 00:22:20
 * @since JDK1.8
 */
public interface IManageServiceService extends BaseService<ManageServiceEntity> {
    /**
     * 保存
     *
     * @param vo ${@link ManageServiceVo} 服务管理保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-12-19 00:22:20
     */
    void save(ManageServiceVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo        ${@link ManageServiceVo} 服务管理更新
     * @param serviceId ${@link String} 服务id
     * @param vo        ${@link ManageServiceVo} 服务管理更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-12-19 00:22:20
     */
    void updateById(String serviceId, ManageServiceVo vo) throws RuntimeException;

    /**
     * 获取swagger信息
     *
     * @return Map<String, ManageSwaggerInfoModel> ${@link Map <String,  ManageSwaggerInfoModel >}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-09-14 03:19
     */
    Map<String, ManageSwaggerInfoModel> selectSwaggerInfo() throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link ManageServicePageVo} 服务管理分页查询Vo
     * @return PageDto<ManageServicePageDto> ${@link PageDto< ManageServicePageDto >} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-12-19 00:22:20
     */
    PageDto<ManageServicePageDto> pageByModel(ManageServicePageVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param serviceId ${@link String} 服务id
     * @return ManageServiceDto ${@link ManageServiceDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-12-19 00:22:20
     */
    ManageServiceDto getById(String serviceId) throws RuntimeException;


    /**
     * 通过serviceId删除
     *
     * @param serviceId ${@link String} 服务id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-12-19 00:22:20
     */
    void deleteById(String serviceId) throws RuntimeException;


    /**
     * 获取系统统计
     *
     * @return SystemStatDto ${@link SystemStatDto}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou zxiaozhou
     * @date 2021-01-27 16:55
     */
    SystemStatDto systemStat() throws RuntimeException;


    /**
     * 获取有效的服务列表
     *
     * @return List<ValidServiceInfoDto> ${@link List< ValidServiceInfoDto >}
     * @author zxiaozhou
     * @date 2021-12-23 23:05
     */
    List<ValidServiceInfoDto> getValidServiceInfo();
}
