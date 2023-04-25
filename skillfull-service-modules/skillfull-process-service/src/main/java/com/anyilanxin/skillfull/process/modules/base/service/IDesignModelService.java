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
package com.anyilanxin.skillfull.process.modules.base.service;

import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DeleteDesignModelVo;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DesignModelPageVo;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DesignModelVo;
import com.anyilanxin.skillfull.process.modules.base.entity.DesignModelEntity;
import com.anyilanxin.skillfull.process.modules.base.service.dto.DesignModelDeploymentStatiDto;
import com.anyilanxin.skillfull.process.modules.base.service.dto.DesignModelDto;
import com.anyilanxin.skillfull.process.modules.base.service.dto.DesignModelPageDto;

/**
* 流程模型管理(DesignModel)业务层接口
*
* @author zxiaozhou
* @date 2021-11-25 05:22:56
* @since JDK1.8
*/
public interface IDesignModelService extends BaseService<DesignModelEntity> {
    /**
    * 保存
    *
    * @param vo ${@link DesignModelVo} 流程模型管理保存
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2021-11-25 05:22:56
    */
    void save(DesignModelVo vo) throws RuntimeException;

    /**
    * 通过id更新
    *
    * @param vo ${@link DesignModelVo} 流程模型管理更新
    * @param modelId ${@link String} 模型id
    * @param vo ${@link DesignModelVo} 流程模型管理更新
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2021-11-25 05:22:56
    */
    void updateById(String modelId, DesignModelVo vo) throws RuntimeException;

    /**
    * 分页查询
    *
    * @param vo ${@link DesignModelPageVo} 流程模型管理分页查询Vo
    * @return PageDto<DesignModelPageDto> ${@link PageDto< DesignModelPageDto >} 分页查询结果
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2021-11-25 05:22:56
    */
    PageDto<DesignModelPageDto> pageByModel(DesignModelPageVo vo) throws RuntimeException;

    /**
    * 通过id查询详情
    *
    * @param modelId ${@link String} 模型id
    * @return DesignModelDto ${@link DesignModelDto} 查询结果
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2021-11-25 05:22:56
    */
    DesignModelDto getById(String modelId) throws RuntimeException;

    /**
    * 模型删除
    *
    * @param vo ${@link DeleteDesignModelVo} 模型删除
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2020-08-28 12:20
    */
    void deleteByModel(DeleteDesignModelVo vo) throws RuntimeException;

    /**
    * 模型状态统计
    *
    * @return DesignModelDeploymentStatiDto ${@link DesignModelDeploymentStatiDto}
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2021-03-02 18:00
    */
    DesignModelDeploymentStatiDto statistics() throws RuntimeException;
}
