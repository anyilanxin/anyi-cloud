// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.logging.modules.manage.mapper;

import com.anyilanxin.skillfull.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.skillfull.logging.modules.manage.controller.vo.AuthDataPageVo;
import com.anyilanxin.skillfull.logging.modules.manage.entity.AuthDataEntity;
import com.anyilanxin.skillfull.logging.modules.manage.service.dto.AuthDataPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 登录日志(AuthData)持久层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-01-26 21:53:03
 * @since JDK1.8
 */
@Repository
public interface AuthDataMapper extends BaseMapper<AuthDataEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link AuthDataPageVo} 查询条件
     * @param page ${@link Page< AuthDataPageDto >} 分页信息
     * @return IPage<AuthDataPageDto> ${@link IPage<AuthDataPageDto>} 结果
     * @author zxiaozhou
     * @date 2022-01-26 21:53:03
     */
    IPage<AuthDataPageDto> pageByModel(Page<AuthDataPageDto> page, @Param("query") AuthDataPageVo vo);

    /**
     * 批量插入日志信息
     *
     * @param waitInsertData
     * @author zxiaozhou
     * @date 2022-05-23 15:47
     */
    int insertBatch(List<AuthDataEntity> waitInsertData);

    /**
     * 通过授权日志id物理删除
     *
     * @param authLogId ${@link String} 授权日志id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2022-01-26 21:53:03
     */
    int physicalDeleteById(@Param("id") String authLogId);


    /**
     * 通过授权日志id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2022-01-26 21:53:03
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
