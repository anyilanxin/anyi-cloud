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
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatMsgSessionAssociatedPageVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatMsgSessionAssociatedQueryVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatMsgSessionAssociatedVo;
import com.anyilanxin.skillfull.message.modules.chat.entity.ChatMsgSessionAssociatedEntity;
import com.anyilanxin.skillfull.message.modules.chat.mapper.ChatMsgSessionAssociatedMapper;
import com.anyilanxin.skillfull.message.modules.chat.service.IChatMsgSessionAssociatedService;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatMsgSessionAssociatedDto;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatMsgSessionAssociatedPageDto;
import com.anyilanxin.skillfull.message.modules.chat.service.mapstruct.ChatMsgSessionAssociatedCopyMap;
import com.anyilanxin.skillfull.message.modules.chat.service.mapstruct.ChatMsgSessionAssociatedPageCopyMap;
import com.anyilanxin.skillfull.message.modules.chat.service.mapstruct.ChatMsgSessionAssociatedQueryCopyMap;
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
 * 聊天会话关系表(ChatMsgSessionAssociated)业务层实现
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-03-29 23:38:30
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMsgSessionAssociatedServiceImpl extends ServiceImpl<ChatMsgSessionAssociatedMapper, ChatMsgSessionAssociatedEntity> implements IChatMsgSessionAssociatedService {
    private final ChatMsgSessionAssociatedCopyMap map;
    private final ChatMsgSessionAssociatedPageCopyMap pageMap;
    private final ChatMsgSessionAssociatedQueryCopyMap queryMap;
    private final ChatMsgSessionAssociatedMapper mapper;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(ChatMsgSessionAssociatedVo vo) throws RuntimeException {
        ChatMsgSessionAssociatedEntity entity = map.vToE(vo);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.SaveDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String correlationMsgSessionId, ChatMsgSessionAssociatedVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(correlationMsgSessionId);
        // 更新数据
        ChatMsgSessionAssociatedEntity entity = map.vToE(vo);
        entity.setCorrelationMsgSessionId(correlationMsgSessionId);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.UpdateDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<ChatMsgSessionAssociatedDto> selectListByModel(ChatMsgSessionAssociatedQueryVo vo) throws RuntimeException {
        List<ChatMsgSessionAssociatedDto> list = mapper.selectListByModel(vo);
        if (CollectionUtils.isEmpty(list)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return list;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<ChatMsgSessionAssociatedPageDto> pageByModel(ChatMsgSessionAssociatedPageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public ChatMsgSessionAssociatedDto getById(String correlationMsgSessionId) throws RuntimeException {
        ChatMsgSessionAssociatedEntity byId = super.getById(correlationMsgSessionId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return map.eToD(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String correlationMsgSessionId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(correlationMsgSessionId);
        // 删除数据
        boolean b = this.removeById(correlationMsgSessionId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.DeleteDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> correlationMsgSessionIds) throws RuntimeException {
        List<ChatMsgSessionAssociatedEntity> entities = this.listByIds(correlationMsgSessionIds);
        if (CollectionUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getCorrelationMsgSessionId()));
        int i = mapper.deleteBatchIds(waitDeleteList);
        if (i <= 0) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.BatchDeleteDataFail"));
        }
    }
}
