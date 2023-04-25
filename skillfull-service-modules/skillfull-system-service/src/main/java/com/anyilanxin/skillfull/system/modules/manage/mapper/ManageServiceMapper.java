package com.anyilanxin.skillfull.system.modules.manage.mapper;

import com.anyilanxin.skillfull.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.skillfull.system.modules.manage.controller.vo.ManageServicePageVo;
import com.anyilanxin.skillfull.system.modules.manage.entity.ManageServiceEntity;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.ManageServicePageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * 服务管理(ManageService)持久层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2021-12-19 00:22:19
 * @since JDK1.8
 */
@Repository
public interface ManageServiceMapper extends BaseMapper<ManageServiceEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link ManageServicePageVo} 查询条件
     * @param page ${@link Page< ManageServicePageDto >} 分页信息
     * @return IPage<ManageServicePageDto> ${@link IPage<ManageServicePageDto>} 结果
     * @author zxiaozhou
     * @date 2021-12-19 00:22:19
     */
    IPage<ManageServicePageDto> pageByModel(Page<ManageServicePageDto> page, @Param("query") ManageServicePageVo vo);

    /**
     * 通过服务id物理删除
     *
     * @param serviceId ${@link String} 服务id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2021-12-19 00:22:19
     */
    int physicalDeleteById(@Param("id") String serviceId);


    /**
     * 通过服务id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2021-12-19 00:22:19
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
