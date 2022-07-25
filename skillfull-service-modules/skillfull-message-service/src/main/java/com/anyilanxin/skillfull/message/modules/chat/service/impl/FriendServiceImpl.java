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
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.FriendPageVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.FriendQueryVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.FriendVo;
import com.anyilanxin.skillfull.message.modules.chat.entity.FriendEntity;
import com.anyilanxin.skillfull.message.modules.chat.mapper.FriendMapper;
import com.anyilanxin.skillfull.message.modules.chat.service.IFriendService;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.FriendDto;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.FriendPageDto;
import com.anyilanxin.skillfull.message.modules.chat.service.mapstruct.FriendCopyMap;
import com.anyilanxin.skillfull.message.modules.chat.service.mapstruct.FriendPageCopyMap;
import com.anyilanxin.skillfull.message.modules.chat.service.mapstruct.FriendQueryCopyMap;
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
 * 好友列表(Friend)业务层实现
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-03-29 23:38:25
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FriendServiceImpl extends ServiceImpl<FriendMapper, FriendEntity> implements IFriendService {
    private final FriendCopyMap map;
    private final FriendPageCopyMap pageMap;
    private final FriendQueryCopyMap queryMap;
    private final FriendMapper mapper;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(FriendVo vo) throws RuntimeException {
        FriendEntity entity = map.vToE(vo);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.SaveDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String friendId, FriendVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(friendId);
        // 更新数据
        FriendEntity entity = map.vToE(vo);
        entity.setFriendId(friendId);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.UpdateDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<FriendDto> selectListByModel(FriendQueryVo vo) throws RuntimeException {
        List<FriendDto> list = mapper.selectListByModel(vo);
        if (CollectionUtils.isEmpty(list)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return list;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<FriendPageDto> pageByModel(FriendPageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public FriendDto getById(String friendId) throws RuntimeException {
        FriendEntity byId = super.getById(friendId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return map.eToD(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String friendId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(friendId);
        // 删除数据
        boolean b = this.removeById(friendId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.DeleteDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> friendIds) throws RuntimeException {
        List<FriendEntity> entities = this.listByIds(friendIds);
        if (CollectionUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getFriendId()));
        int i = mapper.deleteBatchIds(waitDeleteList);
        if (i <= 0) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.BatchDeleteDataFail"));
        }
    }
}
