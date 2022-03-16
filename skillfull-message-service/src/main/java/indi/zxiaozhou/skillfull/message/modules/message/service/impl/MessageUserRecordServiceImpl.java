// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.message.modules.message.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import indi.zxiaozhou.skillfull.corewebflux.utils.ContextHolderUtils;
import indi.zxiaozhou.skillfull.message.modules.message.controller.vo.MessageUserRecordPageVo;
import indi.zxiaozhou.skillfull.message.modules.message.controller.vo.MessageUserRecordVo;
import indi.zxiaozhou.skillfull.message.modules.message.entity.MessageUserRecordEntity;
import indi.zxiaozhou.skillfull.message.modules.message.mapper.MessageUserRecordMapper;
import indi.zxiaozhou.skillfull.message.modules.message.service.IMessageUserRecordService;
import indi.zxiaozhou.skillfull.message.modules.message.service.dto.MessageUserNoReadRecordDto;
import indi.zxiaozhou.skillfull.message.modules.message.service.dto.MessageUserRecordDto;
import indi.zxiaozhou.skillfull.message.modules.message.service.dto.MessageUserRecordPageDto;
import indi.zxiaozhou.skillfull.message.modules.message.service.mapstruct.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 用户消息记录(MessageUserRecord)业务层实现
 *
 * @author zxiaozhou
 * @date 2021-01-26 16:48:39
 * @since JDK11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageUserRecordServiceImpl extends ServiceImpl<MessageUserRecordMapper, MessageUserRecordEntity> implements IMessageUserRecordService {
    private final MessageUserRecordDtoMap dtoMap;
    private final MessageUserRecordPageDtoMap pageDtoMap;
    private final MessageUserRecordVoMap voMap;
    private final MessageUserRecordQueryVoMap queryVoMap;
    private final MessageUserRecordPageVoMap pageVoMap;
    private final MessageUserRecordMapper mapper;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(MessageUserRecordVo vo) throws RuntimeException {
        MessageUserRecordEntity entity = voMap.aToB(vo);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存数据失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String msgId, MessageUserRecordVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(msgId);
        // 更新数据
        MessageUserRecordEntity entity = voMap.aToB(vo);
        entity.setMsgId(msgId);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新数据失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<MessageUserRecordPageDto> pageByModel(MessageUserRecordPageVo vo) throws RuntimeException {
        vo.setUserId(ContextHolderUtils.getUserId());
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public MessageUserRecordDto getById(String msgId) throws RuntimeException {
        MessageUserRecordEntity byId = super.getById(msgId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "未找到符合条件数据");
        }
        return dtoMap.bToA(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String msgId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(msgId);
        boolean b = this.removeById(msgId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除数据失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> msgIds) throws RuntimeException {
        List<MessageUserRecordEntity> entities = this.listByIds(msgIds);
        if (CollectionUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "数据不存在或已经被别人删除");
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getMsgId()));
        int i = mapper.deleteBatchIds(waitDeleteList);
        if (i <= 0) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "批量删除数据失败");
        }
    }


    @Override
    public MessageUserNoReadRecordDto getListNoRead() throws RuntimeException {
        String userId = ContextHolderUtils.getUserId();
        // 获取系统为读取消息
        LambdaQueryWrapper<MessageUserRecordEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(MessageUserRecordEntity::getUserId, userId)
                .eq(MessageUserRecordEntity::getReadStatus, 0)
                .eq(MessageUserRecordEntity::getMsgType, 0)
                .orderByDesc(MessageUserRecordEntity::getCreateTime);
        List<MessageUserRecordEntity> systemMsgs = this.list(lambdaQueryWrapper);
        // 获取代办通知
        lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(MessageUserRecordEntity::getUserId, userId)
                .eq(MessageUserRecordEntity::getReadStatus, 0)
                .eq(MessageUserRecordEntity::getMsgType, 1)
                .orderByDesc(MessageUserRecordEntity::getCreateTime);
        List<MessageUserRecordEntity> waitHandleMsgs = this.list(lambdaQueryWrapper);
        // 数据组装
        MessageUserNoReadRecordDto dto = new MessageUserNoReadRecordDto();
        if (CollectionUtil.isNotEmpty(systemMsgs)) {
            dto.setSystemMessages(dtoMap.bToA(systemMsgs));
        }
        if (CollectionUtil.isNotEmpty(waitHandleMsgs)) {
            dto.setWaitHandleMessages(dtoMap.bToA(waitHandleMsgs));
        }
        return dto;
    }


    @Override
    public void clearMsg(Integer type) throws RuntimeException {
        String userId = ContextHolderUtils.getUserId();
        // 获取系统为读取消息
        LambdaQueryWrapper<MessageUserRecordEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(MessageUserRecordEntity::getUserId, userId)
                .eq(MessageUserRecordEntity::getReadStatus, 0);
        if (Objects.nonNull(type)) {
            lambdaQueryWrapper.eq(MessageUserRecordEntity::getMsgType, type);
        }
        MessageUserRecordEntity entity = new MessageUserRecordEntity();
        entity.setReadStatus(1);
        boolean update = this.update(entity, lambdaQueryWrapper);
        if (!update) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "清空消息失败");
        }
    }
}