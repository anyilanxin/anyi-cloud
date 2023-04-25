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
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacUserIdentityPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacUserIdentityVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacUserIdentityEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacUserIdentityDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacUserIdentityPageDto;

import java.util.List;

/**
 * 实名信息表(RbacUserIdentity)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-02 16:12:21
 * @since JDK1.8
 */
public interface IRbacUserIdentityService extends BaseService<RbacUserIdentityEntity> {
    /**
     * 保存
     *
     * @param vo 实名信息表保存数据
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    void save(RbacUserIdentityVo vo) throws RuntimeException;


    /**
     * 实名审核
     *
     * @param identityId 实名信息id
     * @param vo         实名信息表更新数据
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    void audit(String identityId, RbacUserIdentityVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo 分页查询条件
     * @return PageDto<RbacUserIdentityPageDto> 分页查询结果
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    PageDto<RbacUserIdentityPageDto> pageByModel(RbacUserIdentityPageVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param identityId 实名信息id
     * @return RbacUserIdentityDto 查询结果
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    RbacUserIdentityDto getById(String identityId) throws RuntimeException;


    /**
     * 通过identityId删除
     *
     * @param identityId 实名信息id
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    void deleteById(String identityId) throws RuntimeException;


    /**
     * 实名信息表批量删除
     *
     * @param identityIds 实名信息id列表
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    void deleteBatch(List<String> identityIds) throws RuntimeException;
}
