

package com.anyilanxin.anyicloud.message.modules.manage.mapper;

import com.anyilanxin.anyicloud.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.anyicloud.message.modules.manage.controller.vo.ManageSendRecordPageVo;
import com.anyilanxin.anyicloud.message.modules.manage.entity.ManageSendRecordEntity;
import com.anyilanxin.anyicloud.message.modules.manage.service.dto.ManageSendRecordPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Collection;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 消息发送记录表(ManageSendRecord)持久层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-03-29 05:23:41
 * @since 1.0.0
 */
@Repository
public interface ManageSendRecordMapper extends BaseMapper<ManageSendRecordEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link ManageSendRecordPageVo} 查询条件
     * @param page ${@link Page< ManageSendRecordPageDto >} 分页信息
     * @return IPage<ManageSendRecordPageDto> ${@link IPage<ManageSendRecordPageDto>} 结果
     * @author zxh
     * @date 2022-03-29 05:23:41
     */
    IPage<ManageSendRecordPageDto> pageByModel(Page<ManageSendRecordPageDto> page, @Param("query") ManageSendRecordPageVo vo);


    /**
     * 通过消息id物理删除
     *
     * @param sendRecordId ${@link String} 消息id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxh
     * @date 2022-03-29 05:23:41
     */
    int physicalDeleteById(@Param("id") String sendRecordId);


    /**
     * 通过消息id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxh
     * @date 2022-03-29 05:23:41
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
