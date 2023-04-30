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

package com.anyilanxin.anyicloud.system.modules.manage.service;

import com.anyilanxin.anyicloud.corecommon.model.system.ManageSwaggerInfoModel;
import com.anyilanxin.anyicloud.database.datasource.base.service.BaseService;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.system.modules.manage.controller.vo.ManageServicePageVo;
import com.anyilanxin.anyicloud.system.modules.manage.controller.vo.ManageServiceVo;
import com.anyilanxin.anyicloud.system.modules.manage.entity.ManageServiceEntity;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.ManageServiceDto;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.ManageServicePageDto;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.SystemStatDto;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.ValidServiceInfoDto;

import java.util.List;
import java.util.Map;

/**
 * 服务管理(ManageService)业务层接口
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2021-12-19 00:22:20
 * @since 1.0.0
 */
public interface IManageServiceService extends BaseService<ManageServiceEntity> {
    /**
     * 保存
     *
     * @param vo ${@link ManageServiceVo} 服务管理保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-12-19 00:22:20
     */
    void save(ManageServiceVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo        ${@link ManageServiceVo} 服务管理更新
     * @param serviceId ${@link String} 服务id
     * @param vo        ${@link ManageServiceVo} 服务管理更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-12-19 00:22:20
     */
    void updateById(String serviceId, ManageServiceVo vo) throws RuntimeException;


    /**
     * 获取swagger信息
     *
     * @return Map<String, ManageSwaggerInfoModel> ${@link Map <String, ManageSwaggerInfoModel >}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2020-09-14 03:19
     */
    Map<String, ManageSwaggerInfoModel> selectSwaggerInfo() throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link ManageServicePageVo} 服务管理分页查询Vo
     * @return PageDto<ManageServicePageDto> ${@link PageDto< ManageServicePageDto >} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-12-19 00:22:20
     */
    PageDto<ManageServicePageDto> pageByModel(ManageServicePageVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param serviceId ${@link String} 服务id
     * @return ManageServiceDto ${@link ManageServiceDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-12-19 00:22:20
     */
    ManageServiceDto getById(String serviceId) throws RuntimeException;


    /**
     * 通过serviceId删除
     *
     * @param serviceId ${@link String} 服务id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-12-19 00:22:20
     */
    void deleteById(String serviceId) throws RuntimeException;


    /**
     * 获取系统统计
     *
     * @return SystemStatDto ${@link SystemStatDto}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh zxiaozhou
     * @date 2021-01-27 16:55
     */
    SystemStatDto systemStat() throws RuntimeException;


    /**
     * 获取有效的服务列表
     *
     * @return List<ValidServiceInfoDto> ${@link List< ValidServiceInfoDto >}
     * @author zxh
     * @date 2021-12-23 23:05
     */
    List<ValidServiceInfoDto> getValidServiceInfo();
}
