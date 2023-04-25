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


package com.anyilanxin.skillfull.message.modules.manage.service;

import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.message.modules.manage.controller.vo.ManageAnnouncementPageVo;
import com.anyilanxin.skillfull.message.modules.manage.controller.vo.ManageAnnouncementQueryVo;
import com.anyilanxin.skillfull.message.modules.manage.controller.vo.ManageAnnouncementVo;
import com.anyilanxin.skillfull.message.modules.manage.entity.ManageAnnouncementEntity;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageAnnouncementDto;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageAnnouncementPageDto;

import java.util.List;

/**
 * 系统通告公告管理(ManageAnnouncement)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-03-29 08:34:22
 * @since JDK1.8
 */
public interface IManageAnnouncementService extends BaseService<ManageAnnouncementEntity> {
    /**
     * 保存
     *
     * @param vo ${@link ManageAnnouncementVo} 系统通告公告管理保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 08:34:22
     */
    void save(ManageAnnouncementVo vo) throws RuntimeException;

    /**
     * 通过id更新
     *
     * @param vo     ${@link ManageAnnouncementVo} 系统通告公告管理更新
     * @param anntId ${@link String} 通知公告id
     * @param vo     ${@link ManageAnnouncementVo} 系统通告公告管理更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 08:34:22
     */
    void updateById(String anntId, ManageAnnouncementVo vo) throws RuntimeException;

    /**
     * 分页查询
     *
     * @param vo ${@link ManageAnnouncementPageVo} 系统通告公告管理分页查询Vo
     * @return PageDto<ManageAnnouncementPageDto> ${@link PageDto< ManageAnnouncementPageDto >} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 08:34:22
     */
    PageDto<ManageAnnouncementPageDto> pageByModel(ManageAnnouncementPageVo vo)
            throws RuntimeException;

    /**
     * 条件查询多条
     *
     * @param vo ${@link ManageAnnouncementQueryVo} 系统通告公告管理条件查询Vo
     * @return List<ManageAnnouncementDto> ${@link List< ManageAnnouncementDto >} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 08:34:22
     */
    List<ManageAnnouncementDto> selectListByModel(ManageAnnouncementQueryVo vo)
            throws RuntimeException;

    /**
     * 通过id查询详情
     *
     * @param anntId ${@link String} 通知公告id
     * @return ManageAnnouncementDto ${@link ManageAnnouncementDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 08:34:22
     */
    ManageAnnouncementDto getById(String anntId) throws RuntimeException;

    /**
     * 通过anntId删除
     *
     * @param anntId ${@link String} 通知公告id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 08:34:22
     */
    void deleteById(String anntId) throws RuntimeException;

    /**
     * 系统通告公告管理批量删除
     *
     * @param anntIds ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 08:34:22
     */
    void deleteBatch(List<String> anntIds) throws RuntimeException;
}
