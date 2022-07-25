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
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatGroupPageVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatGroupQueryVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatGroupVo;
import com.anyilanxin.skillfull.message.modules.chat.entity.ChatGroupEntity;
import com.anyilanxin.skillfull.message.modules.chat.mapper.ChatGroupMapper;
import com.anyilanxin.skillfull.message.modules.chat.service.IChatGroupService;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatGroupDto;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatGroupPageDto;
import com.anyilanxin.skillfull.message.modules.chat.service.mapstruct.ChatGroupCopyMap;
import com.anyilanxin.skillfull.message.modules.chat.service.mapstruct.ChatGroupPageCopyMap;
import com.anyilanxin.skillfull.message.modules.chat.service.mapstruct.ChatGroupQueryCopyMap;
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
 * 消息群(ChatGroup)业务层实现
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-03-29 23:38:23
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChatGroupServiceImpl extends ServiceImpl<ChatGroupMapper, ChatGroupEntity> implements IChatGroupService {
    private final ChatGroupCopyMap map;
    private final ChatGroupPageCopyMap pageMap;
    private final ChatGroupQueryCopyMap queryMap;
    private final ChatGroupMapper mapper;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(ChatGroupVo vo) throws RuntimeException {
        ChatGroupEntity entity = map.vToE(vo);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.SaveDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String chatGroupId, ChatGroupVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(chatGroupId);
        // 更新数据
        ChatGroupEntity entity = map.vToE(vo);
        entity.setChatGroupId(chatGroupId);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.UpdateDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<ChatGroupDto> selectListByModel(ChatGroupQueryVo vo) throws RuntimeException {
        List<ChatGroupDto> list = mapper.selectListByModel(vo);
        if (CollectionUtils.isEmpty(list)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return list;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<ChatGroupPageDto> pageByModel(ChatGroupPageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public ChatGroupDto getById(String chatGroupId) throws RuntimeException {
        ChatGroupEntity byId = super.getById(chatGroupId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return map.eToD(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String chatGroupId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(chatGroupId);
        // 删除数据
        boolean b = this.removeById(chatGroupId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.DeleteDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> chatGroupIds) throws RuntimeException {
        List<ChatGroupEntity> entities = this.listByIds(chatGroupIds);
        if (CollectionUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getChatGroupId()));
        int i = mapper.deleteBatchIds(waitDeleteList);
        if (i <= 0) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.BatchDeleteDataFail"));
        }
    }
}
