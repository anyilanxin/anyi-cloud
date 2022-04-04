// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.message.modules.message.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.zxiaozhou.skillfull.coredatabase.base.mapper.BaseMapper;
import indi.zxiaozhou.skillfull.message.modules.message.controller.vo.MessageUserRecordPageVo;
import indi.zxiaozhou.skillfull.message.modules.message.entity.MessageUserRecordEntity;
import indi.zxiaozhou.skillfull.message.modules.message.service.dto.MessageUserRecordPageDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * 用户消息记录(MessageUserRecord)持久层
 *
 * @author zxiaozhou
 * @date 2021-01-26 16:48:16
 * @since JDK11
 */
@Repository
public interface MessageUserRecordMapper extends BaseMapper<MessageUserRecordEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link MessageUserRecordPageVo} 查询条件
     * @param page ${@link Page<MessageUserRecordPageDto>} 分页信息
     * @return IPage<MessageUserRecordPageDto> ${@link IPage<MessageUserRecordPageDto>} 结果
     * @author zxiaozhou
     * @date 2021-01-26 16:48:16
     */
    IPage<MessageUserRecordPageDto> pageByModel(Page<MessageUserRecordPageDto> page, @Param("query") MessageUserRecordPageVo vo);
    

    /**
     * 通过消息记录id物理删除
     *
     * @param msgId ${@link String} 消息记录id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2020-08-28 11:36
     */
    int physicalDeleteById(@Param("id") String msgId);


    /**
     * 通过消息记录id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2020-08-28 11:36
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}