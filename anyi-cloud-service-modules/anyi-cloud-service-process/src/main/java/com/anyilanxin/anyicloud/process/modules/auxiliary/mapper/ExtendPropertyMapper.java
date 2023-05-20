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

package com.anyilanxin.anyicloud.process.modules.auxiliary.mapper;

import com.anyilanxin.anyicloudee.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.anyicloudee.process.modules.auxiliary.entity.ExtendPropertyEntity;
import java.util.Collection;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 流程定义扩展属性信息(ExtendProperty)持久层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-11-18 10:38:07
 * @since 1.0.0
 */
@Repository
public interface ExtendPropertyMapper extends BaseMapper<ExtendPropertyEntity> {

    /**
     * 通过扩展属性id物理删除
     *
     * @param propertyId 扩展属性id
     * @return int 成功状态:0-失败,1-成功
     * @author zxh
     * @date 2022-11-18 10:38:07
     */
    int physicalDeleteById(@Param("id") String propertyId);


    /**
     * 通过扩展属性id物理批量删除
     *
     * @param idList 扩展属性id列表
     * @return int 成功状态:0-失败,大于1-成功
     * @author zxh
     * @date 2022-11-18 10:38:07
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}