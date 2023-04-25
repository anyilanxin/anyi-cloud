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
package com.anyilanxin.skillfull.system.modules.rbac.mapper;

import com.anyilanxin.skillfull.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacUserAgentPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacUserAgentQueryVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacUserAgentEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacUserAgentDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacUserAgentPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Collection;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* 用户-代理人表(RbacUserAgent)持久层
*
* @author zxiaozhou
* @copyright zxiaozhou（https://skillfull.divisu.com）
* @date 2022-05-02 16:12:20
* @since JDK1.8
*/
@Repository
public interface RbacUserAgentMapper extends BaseMapper<RbacUserAgentEntity> {
    /**
    * 分页查询
    *
    * @param vo 查询条件
    * @param page 分页信息
    * @return IPage<RbacUserAgentPageDto> 查询结果
    * @author zxiaozhou
    * @date 2022-05-02 16:12:20
    */
    IPage<RbacUserAgentPageDto> pageByModel(
            Page<RbacUserAgentPageDto> page, @Param("query") RbacUserAgentPageVo vo);

    /**
    * 条件查询多条
    *
    * @param vo 查询条件
    * @return List<RbacUserAgentDto> 查询结果
    * @author zxiaozhou
    * @date 2022-05-02 16:12:20
    */
    List<RbacUserAgentDto> selectListByModel(RbacUserAgentQueryVo vo);

    /**
    * 通过代理id物理删除
    *
    * @param agentId 代理id
    * @return int 成功状态:0-失败,1-成功
    * @author zxiaozhou
    * @date 2022-05-02 16:12:20
    */
    int physicalDeleteById(@Param("id") String agentId);

    /**
    * 通过代理id物理批量删除
    *
    * @param idList 代理id列表
    * @return int 成功状态:0-失败,大于1-成功
    * @author zxiaozhou
    * @date 2022-05-02 16:12:20
    */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
