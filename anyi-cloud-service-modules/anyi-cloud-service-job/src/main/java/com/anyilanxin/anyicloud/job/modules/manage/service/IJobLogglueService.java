/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.job.modules.manage.service;

import com.anyilanxin.anyicloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.anyicloud.database.datasource.base.service.BaseService;
import com.anyilanxin.anyicloud.job.modules.manage.controller.vo.JobLoggluePageQuery;
import com.anyilanxin.anyicloud.job.modules.manage.controller.vo.JobLogglueQueryVo;
import com.anyilanxin.anyicloud.job.modules.manage.controller.vo.JobLogglueVo;
import com.anyilanxin.anyicloud.job.modules.manage.entity.JobLogglueEntity;
import com.anyilanxin.anyicloud.job.modules.manage.service.dto.JobLogglueDto;
import com.anyilanxin.anyicloud.job.modules.manage.service.dto.JobLoggluePageDto;

import java.util.List;

/**
 * job logglue(JobLogglue)业务层接口
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-06-25 00:42:02
 * @since 1.0.0
 */
public interface IJobLogglueService extends BaseService<JobLogglueEntity> {
    /**
     * 保存
     *
     * @param vo job logglue保存数据
     * @throws RuntimeException
     * @author zxh
     * @date 2022-06-25 00:42:02
     */
    void save(JobLogglueVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param id logglue id
     * @param vo job logglue更新数据
     * @throws RuntimeException
     * @author zxh
     * @date 2022-06-25 00:42:02
     */
    void updateById(Integer id, JobLogglueVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo 分页查询条件
     * @return AnYiPageResult<JobLoggluePageDto> 分页查询结果
     * @throws RuntimeException
     * @author zxh
     * @date 2022-06-25 00:42:02
     */
    AnYiPageResult<JobLoggluePageDto> pageByModel(JobLoggluePageQuery vo) throws RuntimeException;


    /**
     * 条件查询多条
     *
     * @param vo job logglue查询条件
     * @return List<JobLogglueDto> 查询结果
     * @throws RuntimeException
     * @author zxh
     * @date 2022-06-25 00:42:02
     */
    List<JobLogglueDto> selectListByModel(JobLogglueQueryVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param id logglue id
     * @return JobLogglueDto 查询结果
     * @throws RuntimeException
     * @author zxh
     * @date 2022-06-25 00:42:02
     */
    JobLogglueDto getById(Integer id) throws RuntimeException;


    /**
     * 通过id删除
     *
     * @param id logglue id
     * @throws RuntimeException
     * @author zxh
     * @date 2022-06-25 00:42:02
     */
    void deleteById(Integer id) throws RuntimeException;


    /**
     * job logglue批量删除
     *
     * @param ids logglue id列表
     * @throws RuntimeException
     * @author zxh
     * @date 2022-06-25 00:42:02
     */
    void deleteBatch(List<Integer> ids) throws RuntimeException;
}
