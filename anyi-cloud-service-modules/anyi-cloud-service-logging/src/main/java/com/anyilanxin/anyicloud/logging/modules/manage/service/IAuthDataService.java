

package com.anyilanxin.anyicloud.logging.modules.manage.service;

import com.anyilanxin.anyicloud.database.datasource.base.service.BaseService;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.logging.modules.manage.controller.vo.AuthDataPageVo;
import com.anyilanxin.anyicloud.logging.modules.manage.entity.AuthDataEntity;
import com.anyilanxin.anyicloud.logging.modules.manage.service.dto.AuthDataDto;
import com.anyilanxin.anyicloud.logging.modules.manage.service.dto.AuthDataPageDto;

import java.util.List;

/**
 * 登录日志(AuthData)业务层接口
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-01-26 21:53:03
 * @since 1.0.0
 */
public interface IAuthDataService extends BaseService<AuthDataEntity> {

    /**
     * 日志入库
     *
     * @author zxh
     * @date 2022-08-13 11:11
     */
    void storage();


    /**
     * 分页查询
     *
     * @param vo ${@link AuthDataPageVo} 登录日志分页查询Vo
     * @return PageDto<AuthDataPageDto> ${@link PageDto<  AuthDataPageDto  >} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2022-01-26 21:53:03
     */
    PageDto<AuthDataPageDto> pageByModel(AuthDataPageVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param authLogId ${@link String} 授权日志id
     * @return AuthDataDto ${@link AuthDataDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2022-01-26 21:53:03
     */
    AuthDataDto getById(String authLogId) throws RuntimeException;


    /**
     * 通过authLogId删除
     *
     * @param authLogId ${@link String} 授权日志id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2022-01-26 21:53:03
     */
    void deleteById(String authLogId) throws RuntimeException;


    /**
     * 登录日志批量删除
     *
     * @param authLogIds ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2022-01-26 21:53:03
     */
    void deleteBatch(List<String> authLogIds) throws RuntimeException;
}
