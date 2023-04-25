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

package com.anyilanxin.skillfull.system.modules.rbac.service;

import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacSystemPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacSystemVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacSystemEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacSystemDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacSystemPageDto;
import java.util.List;

/**
 * 系统(RbacSystem)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-02 11:46:37
 * @since JDK1.8
 */
public interface IRbacSystemService extends BaseService<RbacSystemEntity> {
    /**
     * 保存
     *
     * @param vo 系统保存数据
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 11:46:37
     */
    void save(RbacSystemVo vo) throws RuntimeException;

    /**
     * 通过id更新
     *
     * @param systemId 系统id
     * @param vo 系统更新数据
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 11:46:37
     */
    void updateById(String systemId, RbacSystemVo vo) throws RuntimeException;

    /**
     * 分页查询
     *
     * @param vo 分页查询条件
     * @return PageDto<RbacSystemPageDto> 分页查询结果
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 11:46:37
     */
    PageDto<RbacSystemPageDto> pageByModel(RbacSystemPageVo vo) throws RuntimeException;

    /**
     * 查询有效的系统信息
     *
     * @return List<RbacSystemDto> 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-07-28 09:35:45
     */
    List<RbacSystemDto> selectList() throws RuntimeException;

    /**
     * 通过id查询详情
     *
     * @param systemId 系统id
     * @return RbacSystemDto 查询结果
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 11:46:37
     */
    RbacSystemDto getById(String systemId) throws RuntimeException;

    /**
     * 通过systemId删除
     *
     * @param systemId 系统id
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 11:46:37
     */
    void deleteById(String systemId) throws RuntimeException;

    /**
     * 系统批量删除
     *
     * @param systemIds 系统id列表
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 11:46:37
     */
    void deleteBatch(List<String> systemIds) throws RuntimeException;
}
