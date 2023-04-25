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
package com.anyilanxin.skillfull.system.modules.common.service;

import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.system.modules.common.controller.vo.CommonAreaPageVo;
import com.anyilanxin.skillfull.system.modules.common.controller.vo.CommonAreaVo;
import com.anyilanxin.skillfull.system.modules.common.entity.CommonAreaEntity;
import com.anyilanxin.skillfull.system.modules.common.service.dto.CommonAreaDto;
import com.anyilanxin.skillfull.system.modules.common.service.dto.CommonAreaPageDto;
import com.anyilanxin.skillfull.system.modules.common.service.dto.CommonAreaTreeDto;
import java.util.List;

/**
* 区域表(CommonArea)业务层接口
*
* @author zxiaozhou
* @date 2020-11-02 09:25:04
* @since JDK11
*/
public interface ICommonAreaService extends BaseService<CommonAreaEntity> {
    /**
    * 保存
    *
    * @param vo ${@link CommonAreaVo} 区域表保存
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2020-11-02 09:25:04
    */
    void save(CommonAreaVo vo) throws RuntimeException;

    /**
    * 通过id更新
    *
    * @param vo ${@link CommonAreaVo} 区域表更新
    * @param areaId ${@link String} 区域id
    * @param vo ${@link CommonAreaVo} 区域表更新
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2020-11-02 09:25:04
    */
    void updateById(String areaId, CommonAreaVo vo) throws RuntimeException;

    /**
    * 分页查询
    *
    * @param vo ${@link CommonAreaPageVo} 区域表分页查询Vo
    * @return PageDto<CommonAreaPageDto> ${@link PageDto< CommonAreaPageDto >} 分页查询结果
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2020-11-02 09:25:04
    */
    PageDto<CommonAreaPageDto> pageByModel(CommonAreaPageVo vo) throws RuntimeException;

    /**
    * 区域查询下级
    *
    * @param parentId ${@link String} 上级区域id
    * @return List<CommonAreaPageDto> ${@link List<CommonAreaPageDto>}
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2021-01-07 20:13
    */
    List<CommonAreaPageDto> selectPageChildren(String parentId) throws RuntimeException;

    /**
    * 条件查询多条
    *
    * @param parentId ${@link String} 上级区域id
    * @return List<CommonAreaTreeDto> ${@link List< CommonAreaTreeDto >} 查询结果
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2020-11-02 09:25:04
    */
    List<CommonAreaTreeDto> selectList(String parentId, String activateAreaId)
            throws RuntimeException;

    /**
    * 通过id查询详情
    *
    * @param areaId ${@link String} 区域id
    * @return CommonAreaDto ${@link CommonAreaDto} 查询结果
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2020-11-02 09:25:04
    */
    CommonAreaDto getById(String areaId) throws RuntimeException;

    /**
    * 通过areaId删除
    *
    * @param areaId ${@link String} 区域id
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2020-08-28 12:20
    */
    void deleteById(String areaId) throws RuntimeException;

    /**
    * 文件批量删除
    *
    * @param areaIds ${@link List<String>}
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2020-10-24 22:59
    */
    void deleteBatch(List<String> areaIds) throws RuntimeException;
}
