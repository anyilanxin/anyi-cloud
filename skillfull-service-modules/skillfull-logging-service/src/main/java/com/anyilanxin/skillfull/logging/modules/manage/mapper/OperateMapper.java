package com.anyilanxin.skillfull.logging.modules.manage.mapper;

import com.anyilanxin.skillfull.database.datasource.base.mapper.MysqlBaseMapper;
import com.anyilanxin.skillfull.logging.modules.manage.controller.vo.OperatePageVo;
import com.anyilanxin.skillfull.logging.modules.manage.entity.OperateEntity;
import com.anyilanxin.skillfull.logging.modules.manage.service.dto.OperatePageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * 操作日志(Operate)持久层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-01-26 19:51:06
 * @since JDK1.8
 */
@Repository
public interface OperateMapper extends MysqlBaseMapper<OperateEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link OperatePageVo} 查询条件
     * @param page ${@link Page< OperatePageDto >} 分页信息
     * @return IPage<OperatePageDto> ${@link IPage<OperatePageDto>} 结果
     * @author zxiaozhou
     * @date 2022-01-26 19:51:06
     */
    IPage<OperatePageDto> pageByModel(Page<OperatePageDto> page, @Param("query") OperatePageVo vo);


    /**
     * 通过操作日志id物理删除
     *
     * @param operateId ${@link String} 操作日志id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2022-01-26 19:51:06
     */
    int physicalDeleteById(@Param("id") String operateId);


    /**
     * 通过操作日志id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2022-01-26 19:51:06
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
