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
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DeleteHistoryDesignModelVo;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DesignModelHistoryPageVo;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DesignModelHistoryVo;
import com.anyilanxin.skillfull.process.modules.base.entity.DesignModelHistoryEntity;
import com.anyilanxin.skillfull.process.modules.base.service.dto.DesignModelHistoryDto;
import com.anyilanxin.skillfull.process.modules.base.service.dto.DesignModelHistoryPageDto;

/**
* 流程模型历史(DesignModelHistory)业务层接口
*
* @author zxiaozhou
* @date 2021-11-25 09:52:37
* @since JDK1.8
*/
public interface IDesignModelHistoryService extends BaseService<DesignModelHistoryEntity> {
    /**
    * 保存
    *
    * @param vo ${@link DesignModelHistoryVo} 流程模型历史保存
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2021-11-25 09:52:37
    */
    void save(DesignModelHistoryVo vo) throws RuntimeException;

    /**
    * 分页查询
    *
    * @param vo ${@link DesignModelHistoryPageVo} 流程模型历史分页查询Vo
    * @return PageDto<DesignModelHistoryPageDto> ${@link PageDto< DesignModelHistoryPageDto >} 分页查询结果
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2021-11-25 09:52:37
    */
    PageDto<DesignModelHistoryPageDto> pageByModel(DesignModelHistoryPageVo vo)
            throws RuntimeException;

    /**
    * 通过id查询详情
    *
    * @param historyModelId ${@link String} 历史模型id
    * @return DesignModelHistoryDto ${@link DesignModelHistoryDto} 查询结果
    * @throws RuntimeException ${@link RuntimeException}
    * @author zxiaozhou
    * @date 2021-11-25 09:52:37
    */
    DesignModelHistoryDto getById(String historyModelId) throws RuntimeException;

    /**
    * 流程模型历史逻辑删除
    *
    * @param vo ${@link DeleteHistoryDesignModelVo}
    * @author zxiaozhou
    * @date 2021-11-25 19:34
    */
    void deleteByModel(DeleteHistoryDesignModelVo vo);
}
