// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.base.service;

import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DeleteHistoryDesignModelVo;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DesignModelHistoryPageVo;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DesignModelHistoryVo;
import com.anyilanxin.skillfull.process.modules.base.entity.DesignModelHistoryEntity;
import com.anyilanxin.skillfull.process.modules.base.service.dto.DesignModelHistoryDto;
import com.anyilanxin.skillfull.process.modules.base.service.dto.DesignModelHistoryPageDto;

/**
 * 流程模型历史(DesignModelHistory)业务层接口
 *
 * @author zxiaozhou
 * @date 2021-11-25 09:52:37
 * @since JDK1.8
 */
public interface IDesignModelHistoryService extends BaseService<DesignModelHistoryEntity> {
    /**
     * 保存
     *
     * @param vo ${@link DesignModelHistoryVo} 流程模型历史保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-11-25 09:52:37
     */
    void save(DesignModelHistoryVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link DesignModelHistoryPageVo} 流程模型历史分页查询Vo
     * @return PageDto<DesignModelHistoryPageDto> ${@link PageDto< DesignModelHistoryPageDto >} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-11-25 09:52:37
     */
    PageDto<DesignModelHistoryPageDto> pageByModel(DesignModelHistoryPageVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param historyModelId ${@link String} 历史模型id
     * @return DesignModelHistoryDto ${@link DesignModelHistoryDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-11-25 09:52:37
     */
    DesignModelHistoryDto getById(String historyModelId) throws RuntimeException;


    /**
     * 流程模型历史逻辑删除
     *
     * @param vo ${@link DeleteHistoryDesignModelVo}
     * @author zxiaozhou
     * @date 2021-11-25 19:34
     */
    void deleteByModel(DeleteHistoryDesignModelVo vo);
}
