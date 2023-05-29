/*
 * Copyright (c) 2021-2023 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
package com.anyilanxin.anyicloud.message.modules.announcement.service;

import com.anyilanxin.anyicloud.database.datasource.base.service.BaseService;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.message.modules.announcement.controller.vo.AnnouncementRecordPageVo;
import com.anyilanxin.anyicloud.message.modules.announcement.controller.vo.AnnouncementRecordQueryVo;
import com.anyilanxin.anyicloud.message.modules.announcement.controller.vo.AnnouncementRecordVo;
import com.anyilanxin.anyicloud.message.modules.announcement.entity.AnnouncementRecordEntity;
import com.anyilanxin.anyicloud.message.modules.announcement.service.dto.AnnouncementRecordDto;
import com.anyilanxin.anyicloud.message.modules.announcement.service.dto.AnnouncementRecordPageDto;
import java.util.List;

/**
 * 系统通知公告阅读记录(AnnouncementRecord)业务层接口
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-03-29 08:35:34
 * @since 1.0.0
 */
public interface IAnnouncementRecordService extends BaseService<AnnouncementRecordEntity> {
    /**
     * 保存
     *
     * @param vo ${@link AnnouncementRecordVo} 系统通知公告阅读记录保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2022-03-29 08:35:34
     */
    void save(AnnouncementRecordVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo         ${@link AnnouncementRecordVo} 系统通知公告阅读记录更新
     * @param anntReadId ${@link String} 通知公告阅读记录id
     * @param vo         ${@link AnnouncementRecordVo} 系统通知公告阅读记录更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2022-03-29 08:35:34
     */
    void updateById(String anntReadId, AnnouncementRecordVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link AnnouncementRecordPageVo} 系统通知公告阅读记录分页查询Vo
     * @return PageDto<AnnouncementRecordPageDto> ${@link PageDto<  AnnouncementRecordPageDto  >}
     * 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2022-03-29 08:35:34
     */
    PageDto<AnnouncementRecordPageDto> pageByModel(AnnouncementRecordPageVo vo) throws RuntimeException;


    /**
     * 条件查询多条
     *
     * @param vo ${@link AnnouncementRecordQueryVo} 系统通知公告阅读记录条件查询Vo
     * @return List<AnnouncementRecordDto> ${@link List<  AnnouncementRecordDto  >} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2022-03-29 08:35:34
     */
    List<AnnouncementRecordDto> selectListByModel(AnnouncementRecordQueryVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param anntReadId ${@link String} 通知公告阅读记录id
     * @return AnnouncementRecordDto ${@link AnnouncementRecordDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2022-03-29 08:35:34
     */
    AnnouncementRecordDto getById(String anntReadId) throws RuntimeException;


    /**
     * 通过anntReadId删除
     *
     * @param anntReadId ${@link String} 通知公告阅读记录id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2022-03-29 08:35:34
     */
    void deleteById(String anntReadId) throws RuntimeException;


    /**
     * 系统通知公告阅读记录批量删除
     *
     * @param anntReadIds ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2022-03-29 08:35:34
     */
    void deleteBatch(List<String> anntReadIds) throws RuntimeException;
}
