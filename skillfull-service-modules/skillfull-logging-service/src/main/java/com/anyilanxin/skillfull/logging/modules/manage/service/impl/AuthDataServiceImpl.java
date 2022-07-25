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

import cn.hutool.core.collection.CollectionUtil;
import com.anyilanxin.skillfull.corecommon.base.model.stream.AuthLogModel;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
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
import org.springframework.beans.factory.annotation.Value;
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
    private final AuthDataMapper mapper;
    @Value("${app.delete-log-type:1}")
    private Integer deleteLogType;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(AuthLogModel model) throws RuntimeException {
        AuthDataEntity authDataEntity = map.vToE(model);
        boolean save = this.save(authDataEntity);
        if (!save) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "授权日志存储失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void saveBatch(List<AuthLogModel> models) throws RuntimeException {
        List<AuthDataEntity> authDataEntities = map.vToE(models);
        boolean save = this.saveBatch(authDataEntities);
        if (!save) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "授权日志批量存储失败");
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
