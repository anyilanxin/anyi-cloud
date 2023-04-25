/**
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package com.anyilanxin.skillfull.system.modules.rbac.service;

import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacUserAgentPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacUserAgentQueryVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacUserAgentVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacUserAgentEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacUserAgentDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacUserAgentPageDto;

import java.util.List;

/**
 * 用户-代理人表(RbacUserAgent)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-02 16:12:20
 * @since JDK1.8
 */
public interface IRbacUserAgentService extends BaseService<RbacUserAgentEntity> {
    /**
     * 保存
     *
     * @param vo 用户-代理人表保存数据
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:20
     */
    void save(RbacUserAgentVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param agentId 代理id
     * @param vo      用户-代理人表更新数据
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:20
     */
    void updateById(String agentId, RbacUserAgentVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo 分页查询条件
     * @return PageDto<RbacUserAgentPageDto> 分页查询结果
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:20
     */
    PageDto<RbacUserAgentPageDto> pageByModel(RbacUserAgentPageVo vo) throws RuntimeException;

    /**
     * 条件查询多条
     *
     * @param vo 用户-代理人表查询条件
     * @return List<RbacUserAgentDto> 查询结果
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:20
     */
    List<RbacUserAgentDto> selectListByModel(RbacUserAgentQueryVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param agentId 代理id
     * @return RbacUserAgentDto 查询结果
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:20
     */
    RbacUserAgentDto getById(String agentId) throws RuntimeException;


    /**
     * 通过agentId删除
     *
     * @param agentId 代理id
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:20
     */
    void deleteById(String agentId) throws RuntimeException;


    /**
     * 用户-代理人表批量删除
     *
     * @param agentIds 代理id列表
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:20
     */
    void deleteBatch(List<String> agentIds) throws RuntimeException;
}
