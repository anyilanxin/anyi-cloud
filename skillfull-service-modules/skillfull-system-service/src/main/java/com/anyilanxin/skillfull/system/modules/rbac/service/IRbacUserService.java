/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
package com.anyilanxin.skillfull.system.modules.rbac.service;

import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacEnalbeUserPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacUserPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacUserVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacUserEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacUserDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacUserPageDto;
import java.util.List;

/**
* 用户表(RbacUser)业务层接口
*
* @author zxiaozhou
* @copyright zxiaozhou（https://skillfull.divisu.com）
* @date 2022-05-02 16:12:21
* @since JDK1.8
*/
public interface IRbacUserService extends BaseService<RbacUserEntity> {
    /**
    * 保存
    *
    * @param vo 用户表保存数据
    * @throws RuntimeException
    * @author zxiaozhou
    * @date 2022-05-02 16:12:21
    */
    void save(RbacUserVo vo) throws RuntimeException;

    /**
    * 通过id更新
    *
    * @param userId 用户id
    * @param vo 用户表更新数据
    * @throws RuntimeException
    * @author zxiaozhou
    * @date 2022-05-02 16:12:21
    */
    void updateById(String userId, RbacUserVo vo) throws RuntimeException;

    /**
    * 分页查询
    *
    * @param vo 分页查询条件
    * @return PageDto<RbacUserPageDto> 分页查询结果
    * @throws RuntimeException
    * @author zxiaozhou
    * @date 2022-05-02 16:12:21
    */
    PageDto<RbacUserPageDto> pageByModel(RbacUserPageVo vo) throws RuntimeException;

    /**
    * 通过id查询详情
    *
    * @param userId 用户id
    * @param orgId 机构id
    * @return RbacUserDto 查询结果
    * @throws RuntimeException
    * @author zxiaozhou
    * @date 2022-05-02 16:12:21
    */
    RbacUserDto getById(String userId, String orgId) throws RuntimeException;

    /**
    * 通过userId删除
    *
    * @param userId 用户id
    * @throws RuntimeException
    * @author zxiaozhou
    * @date 2022-05-02 16:12:21
    */
    void deleteById(String userId) throws RuntimeException;

    /**
    * 用户表批量删除
    *
    * @param userIds 用户id列表
    * @throws RuntimeException
    * @author zxiaozhou
    * @date 2022-05-02 16:12:21
    */
    void deleteBatch(List<String> userIds) throws RuntimeException;

    /**
    * 通过用户d修改状态
    *
    * @param userId 用户id
    * @param type 类型:1-激活,2-冻结
    * @author zxiaozhou
    * @date 2022-06-03 02:35
    */
    void updateState(String userId, Integer type);

    /**
    * 重置密码
    *
    * @param userId 用户id
    * @return String 新密码
    * @author zxiaozhou
    * @date 2022-06-03 02:38
    */
    String resetPassword(String userId);

    /**
    * 分页查询可关联的用户信息
    *
    * @param vo
    * @return PageDto<RbacUserPageDto>
    * @author zxiaozhou
    * @date 2022-08-02 15:44
    */
    PageDto<RbacUserPageDto> selectEnableUserPage(RbacEnalbeUserPageVo vo);
}
