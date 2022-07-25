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
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatGroupUserPageVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatGroupUserQueryVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatGroupUserVo;
import com.anyilanxin.skillfull.message.modules.chat.entity.ChatGroupUserEntity;
import com.anyilanxin.skillfull.message.modules.chat.mapper.ChatGroupUserMapper;
import com.anyilanxin.skillfull.message.modules.chat.service.IChatGroupUserService;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatGroupUserDto;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatGroupUserPageDto;
import com.anyilanxin.skillfull.message.modules.chat.service.mapstruct.ChatGroupUserCopyMap;
import com.anyilanxin.skillfull.message.modules.chat.service.mapstruct.ChatGroupUserPageCopyMap;
import com.anyilanxin.skillfull.message.modules.chat.service.mapstruct.ChatGroupUserQueryCopyMap;
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
 * 群成员信息(ChatGroupUser)业务层实现
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-03-29 23:38:27
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChatGroupUserServiceImpl extends ServiceImpl<ChatGroupUserMapper, ChatGroupUserEntity> implements IChatGroupUserService {
    private final ChatGroupUserCopyMap map;
    private final ChatGroupUserPageCopyMap pageMap;
    private final ChatGroupUserQueryCopyMap queryMap;
    private final ChatGroupUserMapper mapper;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(ChatGroupUserVo vo) throws RuntimeException {
        ChatGroupUserEntity entity = map.vToE(vo);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.SaveDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String groupUserId, ChatGroupUserVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(groupUserId);
        // 更新数据
        ChatGroupUserEntity entity = map.vToE(vo);
        entity.setGroupUserId(groupUserId);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.UpdateDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<ChatGroupUserDto> selectListByModel(ChatGroupUserQueryVo vo) throws RuntimeException {
        List<ChatGroupUserDto> list = mapper.selectListByModel(vo);
        if (CollectionUtils.isEmpty(list)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return list;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<ChatGroupUserPageDto> pageByModel(ChatGroupUserPageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public ChatGroupUserDto getById(String groupUserId) throws RuntimeException {
        ChatGroupUserEntity byId = super.getById(groupUserId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return map.eToD(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String groupUserId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(groupUserId);
        // 删除数据
        boolean b = this.removeById(groupUserId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.DeleteDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> groupUserIds) throws RuntimeException {
        List<ChatGroupUserEntity> entities = this.listByIds(groupUserIds);
        if (CollectionUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getGroupUserId()));
        int i = mapper.deleteBatchIds(waitDeleteList);
        if (i <= 0) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.BatchDeleteDataFail"));
        }
    }
}
