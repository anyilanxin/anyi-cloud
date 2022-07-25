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
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatMessageInfoPageVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatMessageInfoQueryVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatMessageInfoVo;
import com.anyilanxin.skillfull.message.modules.chat.entity.ChatMessageInfoEntity;
import com.anyilanxin.skillfull.message.modules.chat.mapper.ChatMessageInfoMapper;
import com.anyilanxin.skillfull.message.modules.chat.service.IChatMessageInfoService;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatMessageInfoDto;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatMessageInfoPageDto;
import com.anyilanxin.skillfull.message.modules.chat.service.mapstruct.ChatMessageInfoCopyMap;
import com.anyilanxin.skillfull.message.modules.chat.service.mapstruct.ChatMessageInfoPageCopyMap;
import com.anyilanxin.skillfull.message.modules.chat.service.mapstruct.ChatMessageInfoQueryCopyMap;
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
 * 聊天消息(ChatMessageInfo)业务层实现
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-03-29 23:38:29
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageInfoServiceImpl extends ServiceImpl<ChatMessageInfoMapper, ChatMessageInfoEntity> implements IChatMessageInfoService {
    private final ChatMessageInfoCopyMap map;
    private final ChatMessageInfoPageCopyMap pageMap;
    private final ChatMessageInfoQueryCopyMap queryMap;
    private final ChatMessageInfoMapper mapper;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(ChatMessageInfoVo vo) throws RuntimeException {
        ChatMessageInfoEntity entity = map.vToE(vo);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.SaveDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String chatMessageId, ChatMessageInfoVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(chatMessageId);
        // 更新数据
        ChatMessageInfoEntity entity = map.vToE(vo);
        entity.setChatMessageId(chatMessageId);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.UpdateDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<ChatMessageInfoDto> selectListByModel(ChatMessageInfoQueryVo vo) throws RuntimeException {
        List<ChatMessageInfoDto> list = mapper.selectListByModel(vo);
        if (CollectionUtils.isEmpty(list)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return list;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<ChatMessageInfoPageDto> pageByModel(ChatMessageInfoPageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public ChatMessageInfoDto getById(String chatMessageId) throws RuntimeException {
        ChatMessageInfoEntity byId = super.getById(chatMessageId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return map.eToD(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String chatMessageId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(chatMessageId);
        // 删除数据
        boolean b = this.removeById(chatMessageId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.DeleteDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> chatMessageIds) throws RuntimeException {
        List<ChatMessageInfoEntity> entities = this.listByIds(chatMessageIds);
        if (CollectionUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getChatMessageId()));
        int i = mapper.deleteBatchIds(waitDeleteList);
        if (i <= 0) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.BatchDeleteDataFail"));
        }
    }
}
