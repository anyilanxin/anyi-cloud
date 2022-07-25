// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.base.service;

import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DeleteDesignModelVo;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DesignModelPageVo;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DesignModelVo;
import com.anyilanxin.skillfull.process.modules.base.entity.DesignModelEntity;
import com.anyilanxin.skillfull.process.modules.base.service.dto.DesignModelDeploymentStatiDto;
import com.anyilanxin.skillfull.process.modules.base.service.dto.DesignModelDto;
import com.anyilanxin.skillfull.process.modules.base.service.dto.DesignModelPageDto;

/**
 * 流程模型管理(DesignModel)业务层接口
 *
 * @author zxiaozhou
 * @date 2021-11-25 05:22:56
 * @since JDK1.8
 */
public interface IDesignModelService extends BaseService<DesignModelEntity> {
    /**
     * 保存
     *
     * @param vo ${@link DesignModelVo} 流程模型管理保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-11-25 05:22:56
     */
    void save(DesignModelVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo      ${@link DesignModelVo} 流程模型管理更新
     * @param modelId ${@link String} 模型id
     * @param vo      ${@link DesignModelVo} 流程模型管理更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-11-25 05:22:56
     */
    void updateById(String modelId, DesignModelVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link DesignModelPageVo} 流程模型管理分页查询Vo
     * @return PageDto<DesignModelPageDto> ${@link PageDto< DesignModelPageDto >} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-11-25 05:22:56
     */
    PageDto<DesignModelPageDto> pageByModel(DesignModelPageVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param modelId ${@link String} 模型id
     * @return DesignModelDto ${@link DesignModelDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-11-25 05:22:56
     */
    DesignModelDto getById(String modelId) throws RuntimeException;


    /**
     * 模型删除
     *
     * @param vo ${@link DeleteDesignModelVo} 模型删除
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-08-28 12:20
     */
    void deleteByModel(DeleteDesignModelVo vo) throws RuntimeException;


    /**
     * 模型状态统计
     *
     * @return DesignModelDeploymentStatiDto ${@link DesignModelDeploymentStatiDto}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-03-02 18:00
     */
    DesignModelDeploymentStatiDto statistics() throws RuntimeException;
}
