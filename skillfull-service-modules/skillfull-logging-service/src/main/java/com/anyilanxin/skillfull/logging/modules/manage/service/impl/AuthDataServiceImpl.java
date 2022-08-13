// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.logging.modules.manage.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Snowflake;
import com.alibaba.fastjson2.JSONObject;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.logging.core.constant.LoggingCommonConstant;
import com.anyilanxin.skillfull.logging.modules.manage.controller.vo.AuthDataPageVo;
import com.anyilanxin.skillfull.logging.modules.manage.entity.AuthDataEntity;
import com.anyilanxin.skillfull.logging.modules.manage.mapper.AuthDataMapper;
import com.anyilanxin.skillfull.logging.modules.manage.service.IAuthDataService;
import com.anyilanxin.skillfull.logging.modules.manage.service.dto.AuthDataDto;
import com.anyilanxin.skillfull.logging.modules.manage.service.dto.AuthDataPageDto;
import com.anyilanxin.skillfull.logging.modules.manage.service.mapstruct.AuthDataCopyMap;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 登录日志(AuthData)业务层实现
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-01-26 21:53:03
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthDataServiceImpl extends ServiceImpl<AuthDataMapper, AuthDataEntity> implements IAuthDataService {
    private final AuthDataCopyMap map;
    private final StringRedisTemplate stringRedisTemplate;
    private final AuthDataMapper mapper;
    private final Snowflake snowflake;
    @Value("${app.log-delete-type:1}")
    private Integer deleteLogType;

    @Value("${app.log-auth-save-min:10}")
    private Integer authSaveMin;

    @Override
    @Async
    public void storage() {
        Long size = stringRedisTemplate.opsForList().size(LoggingCommonConstant.AUTH_LOG_KEY_PREFIX);
        int saveMax = 200;
        if (Objects.nonNull(size)) {
            // 循环读取100条
            if (size < saveMax) {
                saveMax = size.intValue();
            }
            if (size >= authSaveMin) {
                List<AuthDataEntity> logEntityList = new ArrayList<>(saveMax);
                for (int i = 0; i < saveMax; i++) {
                    String logStr = stringRedisTemplate.opsForList().rightPop(LoggingCommonConstant.AUTH_LOG_KEY_PREFIX);
                    if (StringUtils.isNotBlank(logStr)) {
                        AuthDataEntity logModel = JSONObject.parseObject(logStr, AuthDataEntity.class);
                        logEntityList.add(logModel);
                    }
                }
                if (CollUtil.isNotEmpty(logEntityList)) {
                    mapper.insertBatch(logEntityList);
                }
            }
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<AuthDataPageDto> pageByModel(AuthDataPageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public AuthDataDto getById(String authLogId) throws RuntimeException {
        AuthDataEntity byId = super.getById(authLogId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return map.eToD(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String authLogId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(authLogId);
        // 删除数据
        if (deleteLogType == 0) {
            int i = mapper.physicalDeleteById(authLogId);
            if (i <= 0) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, "物理删除数据失败");
            }
        } else {
            boolean b = this.removeById(authLogId);
            if (!b) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.DeleteDataFail"));
            }
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> authLogIds) throws RuntimeException {
        List<AuthDataEntity> entities = this.listByIds(authLogIds);
        if (CollectionUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getAuthLogId()));
        if (deleteLogType == 0) {
            int i = mapper.physicalDeleteBatchIds(waitDeleteList);
            if (i <= 0) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, "批量物理删除数据失败");
            }
        } else {
            boolean b = this.removeByIds(waitDeleteList);
            if (!b) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.BatchDeleteDataFail"));
            }
        }
    }
}
