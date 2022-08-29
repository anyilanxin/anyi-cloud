// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.manage.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.system.modules.manage.controller.vo.ManageSpecialUrlVo;
import com.anyilanxin.skillfull.system.modules.manage.entity.ManageSpecialUrlEntity;
import com.anyilanxin.skillfull.system.modules.manage.mapper.ManageSpecialUrlMapper;
import com.anyilanxin.skillfull.system.modules.manage.service.IManageSpecialUrlService;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.ManageSpecialUrlDto;
import com.anyilanxin.skillfull.system.modules.manage.service.mapstruct.ManageSpecialUrlCopyMap;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 路由特殊地址(ManageSpecialUrl)业务层实现
 *
 * @author zxiaozhou zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2021-12-19 09:34:50
 * @since JDK1.8
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
