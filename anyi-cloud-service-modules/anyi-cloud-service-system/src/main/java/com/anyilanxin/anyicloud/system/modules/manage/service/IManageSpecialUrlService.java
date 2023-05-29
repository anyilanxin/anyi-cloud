/*
 * Copyright (c) 2021-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Zeebe EE、AnYi Cloud EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */
package com.anyilanxin.anyicloud.system.modules.manage.service;

import com.anyilanxin.anyicloud.database.datasource.base.service.BaseService;
import com.anyilanxin.anyicloud.system.modules.manage.controller.vo.ManageSpecialUrlVo;
import com.anyilanxin.anyicloud.system.modules.manage.entity.ManageSpecialUrlEntity;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.ManageSpecialUrlDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 路由特殊地址(ManageSpecialUrl)业务层接口
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2021-12-19 09:34:50
 * @since 1.0.0
 */
public interface IManageSpecialUrlService extends BaseService<ManageSpecialUrlEntity> {
    /**
     * 先删除后保存新的
     *
     * @param vo             ${@link List< ManageSpecialUrlVo >} 待保存数据
     * @param customFilterId ${@link String} 自定义过滤器id
     * @author zxh zxiaozhou
     * @date 2021-12-19 09:44
     */
    void deleteAndSave(List<ManageSpecialUrlVo> vo, String customFilterId) throws RuntimeException;


    /**
     * 条件查询多条
     *
     * @param customFilterId ${@link String} 自定义过滤器id
     * @return List<ManageSpecialUrlDto> ${@link List< ManageSpecialUrlDto >} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-12-19 09:34:50
     */
    List<ManageSpecialUrlDto> selectByCustomFilterId(String customFilterId) throws RuntimeException;


    /**
     * 条件查询多条
     *
     * @param customFilterIds ${@link Set<String>} 自定义过滤器ids
     * @return Map<String, List < ManageSpecialUrlDto>> ${@link Map<String,
     * List<ManageSpecialUrlDto>>} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-12-19 09:34:50
     */
    Map<String, List<ManageSpecialUrlDto>> selectByCustomFilterIds(Set<String> customFilterIds) throws RuntimeException;


    /**
     * 删除特殊url
     *
     * @param customFilterId ${@link String} 自定义过滤器id
     * @author zxh zxiaozhou
     * @date 2021-12-19 09:59
     */
    void deleteByCustomFilterId(String customFilterId) throws RuntimeException;


    /**
     * 删除特殊url
     *
     * @param customFilterIds ${@link Set<String>} 自定义过滤器ids
     * @author zxh zxiaozhou
     * @date 2021-12-19 09:59
     */
    void deleteByCustomFilterIds(Set<String> customFilterIds) throws RuntimeException;
}
