

package com.anyilanxin.anyicloud.logging.modules.manage.mapper;

import com.anyilanxin.anyicloud.database.datasource.base.mapper.MysqlBaseMapper;
import com.anyilanxin.anyicloud.logging.modules.manage.controller.vo.AuthDataPageVo;
import com.anyilanxin.anyicloud.logging.modules.manage.entity.AuthDataEntity;
import com.anyilanxin.anyicloud.logging.modules.manage.service.dto.AuthDataPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Collection;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 登录日志(AuthData)持久层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-01-26 21:53:03
 * @since 1.0.0
 */
@Repository
public interface AuthDataMapper extends MysqlBaseMapper<AuthDataEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link AuthDataPageVo} 查询条件
     * @param page ${@link Page<  AuthDataPageDto  >} 分页信息
     * @return IPage<AuthDataPageDto> ${@link IPage<AuthDataPageDto>} 结果
     * @author zxh
     * @date 2022-01-26 21:53:03
     */
    IPage<AuthDataPageDto> pageByModel(Page<AuthDataPageDto> page, @Param("query") AuthDataPageVo vo);


    /**
     * 通过授权日志id物理删除
     *
     * @param authLogId ${@link String} 授权日志id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxh
     * @date 2022-01-26 21:53:03
     */
    int physicalDeleteById(@Param("id") String authLogId);


    /**
     * 通过授权日志id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxh
     * @date 2022-01-26 21:53:03
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
