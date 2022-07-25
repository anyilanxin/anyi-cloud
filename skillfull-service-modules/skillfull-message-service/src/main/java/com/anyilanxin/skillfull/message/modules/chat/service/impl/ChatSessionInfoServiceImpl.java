// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.modules.chat.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatSessionInfoPageVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatSessionInfoQueryVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatSessionInfoVo;
import com.anyilanxin.skillfull.message.modules.chat.entity.ChatSessionInfoEntity;
import com.anyilanxin.skillfull.message.modules.chat.mapper.ChatSessionInfoMapper;
import com.anyilanxin.skillfull.message.modules.chat.service.IChatSessionInfoService;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatSessionInfoDto;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatSessionInfoPageDto;
import com.anyilanxin.skillfull.message.modules.chat.service.mapstruct.ChatSessionInfoCopyMap;
import com.anyilanxin.skillfull.message.modules.chat.service.mapstruct.ChatSessionInfoPageCopyMap;
import com.anyilanxin.skillfull.message.modules.chat.service.mapstruct.ChatSessionInfoQueryCopyMap;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 聊天会话信息(ChatSessionInfo)业务层实现
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-04-08 05:39:30
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChatSessionInfoServiceImpl extends ServiceImpl<ChatSessionInfoMapper, ChatSessionInfoEntity> implements IChatSessionInfoService {
    private final ChatSessionInfoCopyMap map;
    private final ChatSessionInfoPageCopyMap pageMap;
    private final ChatSessionInfoQueryCopyMap queryMap;
    private final ChatSessionInfoMapper mapper;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(ChatSessionInfoVo vo) throws RuntimeException {
        ChatSessionInfoEntity entity = map.vToE(vo);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.SaveDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String sessionInfoId, ChatSessionInfoVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(sessionInfoId);
        // 更新数据
        ChatSessionInfoEntity entity = map.vToE(vo);
        entity.setSessionInfoId(sessionInfoId);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.UpdateDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<ChatSessionInfoDto> selectListByModel(ChatSessionInfoQueryVo vo) throws RuntimeException {
        List<ChatSessionInfoDto> list = mapper.selectListByModel(vo);
        if (CollectionUtils.isEmpty(list)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return list;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<ChatSessionInfoPageDto> pageByModel(ChatSessionInfoPageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public ChatSessionInfoDto getById(String sessionInfoId) throws RuntimeException {
        ChatSessionInfoEntity byId = super.getById(sessionInfoId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return map.eToD(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String sessionInfoId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(sessionInfoId);
        // 删除数据
        boolean b = this.removeById(sessionInfoId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.DeleteDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> sessionInfoIds) throws RuntimeException {
        List<ChatSessionInfoEntity> entities = this.listByIds(sessionInfoIds);
        if (CollectionUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getSessionInfoId()));
        int i = mapper.deleteBatchIds(waitDeleteList);
        if (i <= 0) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.BatchDeleteDataFail"));
        }
    }
}
