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
package com.anyilanxin.anyicloud.system.modules.manage.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.corecommon.exception.ResponseException;
import com.anyilanxin.anyicloud.system.modules.manage.controller.vo.ManageSpecialUrlVo;
import com.anyilanxin.anyicloud.system.modules.manage.entity.ManageSpecialUrlEntity;
import com.anyilanxin.anyicloud.system.modules.manage.mapper.ManageSpecialUrlMapper;
import com.anyilanxin.anyicloud.system.modules.manage.service.IManageSpecialUrlService;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.ManageSpecialUrlDto;
import com.anyilanxin.anyicloud.system.modules.manage.service.mapstruct.ManageSpecialUrlCopyMap;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 路由特殊地址(ManageSpecialUrl)业务层实现
 *
 * @author zxh zxiaozhou
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2021-12-19 09:34:50
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ManageSpecialUrlServiceImpl extends ServiceImpl<ManageSpecialUrlMapper, ManageSpecialUrlEntity> implements IManageSpecialUrlService {
    private final ManageSpecialUrlCopyMap map;
    private final ManageSpecialUrlMapper mapper;

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteAndSave(List<ManageSpecialUrlVo> vo, String customFilterId) throws RuntimeException {
        // 删除历史
        LambdaQueryWrapper<ManageSpecialUrlEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ManageSpecialUrlEntity::getCustomFilterId, customFilterId);
        this.remove(lambdaQueryWrapper);
        // 添加新的
        if (CollUtil.isNotEmpty(vo)) {
            List<ManageSpecialUrlEntity> manageSpecialUrlEntities = map.vToE(vo);
            manageSpecialUrlEntities.forEach(v -> v.setCustomFilterId(customFilterId));
            boolean b = this.saveBatch(manageSpecialUrlEntities);
            if (!b) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存特殊url失败");
            }
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<ManageSpecialUrlDto> selectByCustomFilterId(String customFilterId) throws RuntimeException {
        LambdaQueryWrapper<ManageSpecialUrlEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ManageSpecialUrlEntity::getCustomFilterId, customFilterId);
        return map.eToD(this.list(lambdaQueryWrapper));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public Map<String, List<ManageSpecialUrlDto>> selectByCustomFilterIds(Set<String> customFilterIds) throws RuntimeException {
        LambdaQueryWrapper<ManageSpecialUrlEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(ManageSpecialUrlEntity::getCustomFilterId, customFilterIds);
        Map<String, List<ManageSpecialUrlDto>> stringListMap = new HashMap<>();
        List<ManageSpecialUrlDto> manageSpecialUrls = map.eToD(this.list(lambdaQueryWrapper));
        if (CollUtil.isNotEmpty(manageSpecialUrls)) {
            manageSpecialUrls.forEach(v -> {
                List<ManageSpecialUrlDto> manageSpecialUrlList = stringListMap.get(v.getCustomFilterId());
                if (CollectionUtil.isEmpty(manageSpecialUrlList)) {
                    manageSpecialUrlList = new ArrayList<>();
                }
                manageSpecialUrlList.add(v);
                stringListMap.put(v.getCustomFilterId(), manageSpecialUrlList);
            });
        }
        return stringListMap;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteByCustomFilterId(String customFilterId) throws RuntimeException {
        // 查询是否存在数据
        LambdaQueryWrapper<ManageSpecialUrlEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ManageSpecialUrlEntity::getCustomFilterId, customFilterId);
        List<ManageSpecialUrlEntity> list = this.list(lambdaQueryWrapper);
        if (CollUtil.isNotEmpty(list)) {
            // 删除特殊url
            boolean remove = this.remove(lambdaQueryWrapper);
            if (!remove) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除特殊url失败");
            }
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteByCustomFilterIds(Set<String> customFilterIds) throws RuntimeException {
        // 查询是否存在数据
        LambdaQueryWrapper<ManageSpecialUrlEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(ManageSpecialUrlEntity::getCustomFilterId, customFilterIds);
        List<ManageSpecialUrlEntity> list = this.list(lambdaQueryWrapper);
        if (CollUtil.isNotEmpty(list)) {
            // 删除特殊url
            boolean remove = this.remove(lambdaQueryWrapper);
            if (!remove) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除特殊url失败");
            }
        }
    }
}
