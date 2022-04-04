// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.logging.modules.manage.service;

import indi.zxiaozhou.skillfull.corecommon.base.model.stream.OperateLogModel;
import indi.zxiaozhou.skillfull.coredatabase.base.service.BaseService;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import indi.zxiaozhou.skillfull.logging.modules.manage.controller.vo.OperatePageVo;
import indi.zxiaozhou.skillfull.logging.modules.manage.entity.OperateEntity;
import indi.zxiaozhou.skillfull.logging.modules.manage.service.dto.OperateDto;
import indi.zxiaozhou.skillfull.logging.modules.manage.service.dto.OperatePageDto;

import java.util.List;

/**
 * 操作日志(Operate)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-01-26 19:51:07
 * @since JDK1.8
 */
public interface IOperateService extends BaseService<OperateEntity> {
    /**
     * 日志存储
     *
     * @param model ${@link OperateLogModel}
     * @author zxiaozhou
     * @date 2022-01-27 19:48
     */
    void save(OperateLogModel model) throws RuntimeException;


    /**
     * 日志批量存储
     *
     * @param models ${@link List<OperateLogModel>}
     * @author zxiaozhou
     * @date 2022-01-27 19:48
     */
    void saveBatch(List<OperateLogModel> models) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link OperatePageVo} 操作日志分页查询Vo
     * @return PageDto<OperatePageDto> ${@link PageDto<OperatePageDto>} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-01-26 19:51:07
     */
    PageDto<OperatePageDto> pageByModel(OperatePageVo vo) throws RuntimeException;

    /**
     * 通过id查询详情
     *
     * @param operateId ${@link String} 操作日志id
     * @return OperateDto ${@link OperateDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-01-26 19:51:07
     */
    OperateDto getById(String operateId) throws RuntimeException;


    /**
     * 通过operateId删除
     *
     * @param operateId ${@link String} 操作日志id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-01-26 19:51:07
     */
    void deleteById(String operateId) throws RuntimeException;


    /**
     * 操作日志批量删除
     *
     * @param operateIds ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-01-26 19:51:07
     */
    void deleteBatch(List<String> operateIds) throws RuntimeException;
}
