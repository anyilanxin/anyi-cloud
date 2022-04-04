// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.common.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.zxiaozhou.skillfull.auth.modules.common.controller.vo.CommonUserAgentPageVo;
import indi.zxiaozhou.skillfull.auth.modules.common.controller.vo.CommonUserAgentQueryVo;
import indi.zxiaozhou.skillfull.auth.modules.common.entity.CommonUserAgentEntity;
import indi.zxiaozhou.skillfull.auth.modules.common.service.dto.CommonUserAgentDto;
import indi.zxiaozhou.skillfull.auth.modules.common.service.dto.CommonUserAgentPageDto;
import indi.zxiaozhou.skillfull.coredatabase.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 用户-代理人表(CommonUserAgent)持久层
 *
 * @author zxiaozhou
 * @date 2021-05-17 23:12:37
 * @since JDK1.8
 */
@Repository
public interface CommonUserAgentMapper extends BaseMapper<CommonUserAgentEntity>{
   /**
     * 分页查询
     *
     * @param vo ${@link CommonUserAgentPageVo} 查询条件
     * @param page ${@link Page<CommonUserAgentPageDto>} 分页信息
     * @return IPage<CommonUserAgentPageDto> ${@link IPage<CommonUserAgentPageDto>} 结果
     * @author zxiaozhou
     * @date 2021-05-17 23:12:37
     */
    IPage<CommonUserAgentPageDto> pageByModel(Page<CommonUserAgentPageDto> page, @Param("query") CommonUserAgentPageVo vo);


    /**
     * 条件查询多条
     *
     * @param vo ${@link CommonUserAgentQueryVo} 查询条件
     * @return List<CommonUserAgentDto> ${@link List<CommonUserAgentDto>} 结果
     * @author zxiaozhou
     * @date 2021-05-17 23:12:37
     */
    List<CommonUserAgentDto> selectListByModel(CommonUserAgentQueryVo vo);
    
    
    /**
     * 通过代理id物理删除
     *
     * @param agentId ${@link String} 代理id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2021-05-17 23:12:37
     */
    int physicalDeleteById(@Param("id") String agentId);
    
    
     /**
     * 通过代理id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2021-05-17 23:12:37
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}