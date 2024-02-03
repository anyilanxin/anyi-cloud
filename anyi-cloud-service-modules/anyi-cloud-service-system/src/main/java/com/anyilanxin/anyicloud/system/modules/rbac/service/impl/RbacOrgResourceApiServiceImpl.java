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

package com.anyilanxin.anyicloud.system.modules.rbac.service.impl;

import com.anyilanxin.anyicloud.corecommon.constant.AnYiResultStatus;
import com.anyilanxin.anyicloud.corecommon.exception.AnYiResponseException;
import com.anyilanxin.anyicloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.anyicloud.database.utils.PageUtils;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacOrgResourceApiPageQuery;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacOrgResourceApiEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.RbacOrgResourceApiMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacOrgResourceApiService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacOrgResourceApiPageDto;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 机构-资源表(RbacOrgResourceApi)业务层实现
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-07-02 23:01:20
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RbacOrgResourceApiServiceImpl extends ServiceImpl<RbacOrgResourceApiMapper, RbacOrgResourceApiEntity> implements IRbacOrgResourceApiService {
    private final RbacOrgResourceApiMapper mapper;
    private final RbacResourceMapper resourceMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(String orgId, Set<String> resourceApiIds) throws RuntimeException {
        if (CollUtil.isEmpty(resourceApiIds)) {
            // 删除所有
            mapper.physicalDeleteById(orgId);
        } else {
            // 删除不包含的资源
            mapper.physicalDeleteNotInIds(orgId, resourceApiIds);
            // 添加新的
            List<RbacOrgResourceApiEntity> list = new ArrayList<>(resourceApiIds.size());
            resourceApiIds.forEach(v -> {
                RbacOrgResourceApiEntity resourceApi = RbacOrgResourceApiEntity.builder().apiId(v).orgId(orgId).build();
                list.add(resourceApi);
            });
            boolean b = this.saveBatch(list);
            if (!b) {
                throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, "保存机构资源关联关系失败");
            }
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String orgId) throws RuntimeException {
        mapper.physicalDeleteById(orgId);
    }


    @Override
    public AnYiPageResult<RbacOrgResourceApiPageDto> selectResourcePage(RbacOrgResourceApiPageQuery vo) {
        return PageUtils.toPageData(mapper.selectResourcePage(PageUtils.getPage(vo), vo));
    }
}
