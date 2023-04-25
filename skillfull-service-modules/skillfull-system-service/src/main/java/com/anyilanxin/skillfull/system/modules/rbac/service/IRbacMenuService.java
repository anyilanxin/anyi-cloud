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
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacMenuPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacMenuVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacMenuEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacMenuDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacMenuPageDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacMenuTreeDto;

import java.util.List;

/**
 * 菜单表(RbacMenu)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-02 16:12:21
 * @since JDK1.8
 */
public interface IRbacMenuService extends BaseService<RbacMenuEntity> {
    /**
     * 保存
     *
     * @param vo 菜单表保存数据
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    void save(RbacMenuVo vo) throws RuntimeException;

    /**
     * 通过id更新
     *
     * @param menuId 权限id
     * @param vo     菜单表更新数据
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    void updateById(String menuId, RbacMenuVo vo) throws RuntimeException;

    /**
     * 分页查询
     *
     * @param vo 分页查询条件
     * @return PageDto<RbacMenuPageDto> 分页查询结果
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    PageDto<RbacMenuPageDto> pageByModel(RbacMenuPageVo vo) throws RuntimeException;

    /**
     * 通过id查询详情
     *
     * @param menuId 权限id
     * @return RbacMenuDto 查询结果
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    RbacMenuDto getById(String menuId) throws RuntimeException;

    /**
     * 通过menuId删除
     *
     * @param menuId 权限id
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    void deleteById(String menuId) throws RuntimeException;

    /**
     * 菜单表批量删除
     *
     * @param menuIds 权限id列表
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    void deleteBatch(List<String> menuIds) throws RuntimeException;

    /**
     * 获取权限树
     *
     * @param type     类型:0-目录,1-菜单,2-按钮，多个英文逗号隔开
     * @param systemId 系统id
     * @param status   状态:1-有效、2-所有,默认2
     * @return List<RbacMenuTreeDto>
     * @author zxiaozhou
     * @date 2020-10-07 20:23
     */
    List<RbacMenuTreeDto> getMenuTree(String type, String systemId, Integer status);
}
