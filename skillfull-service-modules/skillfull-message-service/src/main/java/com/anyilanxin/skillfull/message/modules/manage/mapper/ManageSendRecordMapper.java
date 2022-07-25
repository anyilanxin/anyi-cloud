// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.modules.manage.mapper;

import com.anyilanxin.skillfull.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.skillfull.message.modules.manage.controller.vo.ManageSendRecordPageVo;
import com.anyilanxin.skillfull.message.modules.manage.entity.ManageSendRecordEntity;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageSendRecordPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * 消息发送记录表(ManageSendRecord)持久层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-03-29 05:23:41
 * @since JDK1.8
 */
@Repository
public interface ManageSendRecordMapper extends BaseMapper<ManageSendRecordEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link ManageSendRecordPageVo} 查询条件
     * @param page ${@link Page< ManageSendRecordPageDto >} 分页信息
     * @return IPage<ManageSendRecordPageDto> ${@link IPage<ManageSendRecordPageDto>} 结果
     * @author zxiaozhou
     * @date 2022-03-29 05:23:41
     */
    IPage<ManageSendRecordPageDto> pageByModel(Page<ManageSendRecordPageDto> page, @Param("query") ManageSendRecordPageVo vo);


    /**
     * 通过消息id物理删除
     *
     * @param sendRecordId ${@link String} 消息id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2022-03-29 05:23:41
     */
    int physicalDeleteById(@Param("id") String sendRecordId);


    /**
     * 通过消息id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2022-03-29 05:23:41
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
