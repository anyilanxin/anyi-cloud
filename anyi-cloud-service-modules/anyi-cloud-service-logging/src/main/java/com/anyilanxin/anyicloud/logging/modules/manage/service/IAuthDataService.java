/*
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   8.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   9.若您的项目无法满足以上几点，可申请商业授权。
 */

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
