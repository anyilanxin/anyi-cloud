// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.system.modules.common.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import indi.zxiaozhou.skillfull.system.modules.common.controller.vo.CommonDictPageVo;
import indi.zxiaozhou.skillfull.system.modules.common.controller.vo.CommonDictVo;
import indi.zxiaozhou.skillfull.system.modules.common.entity.CommonDictEntity;
import indi.zxiaozhou.skillfull.system.modules.common.entity.CommonDictItemEntity;
import indi.zxiaozhou.skillfull.system.modules.common.mapper.CommonDictItemMapper;
import indi.zxiaozhou.skillfull.system.modules.common.mapper.CommonDictMapper;
import indi.zxiaozhou.skillfull.system.modules.common.service.ICommonDictService;
import indi.zxiaozhou.skillfull.system.modules.common.service.dto.CommonDictDto;
import indi.zxiaozhou.skillfull.system.modules.common.service.dto.CommonDictPageDto;
import indi.zxiaozhou.skillfull.system.modules.common.service.mapstruct.CommonDictDtoMap;
import indi.zxiaozhou.skillfull.system.modules.common.service.mapstruct.CommonDictVoMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 数据字典表(CommonDict)业务层实现
 *
 * @author zxiaozhou
 * @date 2020-11-02 09:25:18
 * @since JDK11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommonDictServiceImpl extends ServiceImpl<CommonDictMapper, CommonDictEntity> implements ICommonDictService {
    private final CommonDictDtoMap dtoMap;
    private final CommonDictVoMap voMap;
    private final CommonDictMapper mapper;
    private final CommonDictItemMapper itemMapper;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(CommonDictVo vo) throws RuntimeException {
        CommonDictEntity entity = voMap.aToB(vo);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存数据失败");
        }
        log.info("------------CommonDictServiceImpl------------>save:{}", entity);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String dictId, CommonDictVo vo) throws RuntimeException {
        // 查询数据是否存在
        CommonDictDto byId = this.getById(dictId);
        // 更新数据
        CommonDictEntity entity = voMap.aToB(vo);
        entity.setDictId(dictId);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新数据字典失败");
        }
        // 判断编码是否有变动，变动则更新子项
        if (!byId.getDictCode().equals(vo.getDictCode())) {
            // 查询是否有子项
            LambdaQueryWrapper<CommonDictItemEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(CommonDictItemEntity::getDictId, dictId);
            List<CommonDictItemEntity> commonDictItemEntities = itemMapper.selectList(lambdaQueryWrapper);
            // 有子项则更新所有子项编码
            if (CollectionUtil.isNotEmpty(commonDictItemEntities)) {
                CommonDictItemEntity itemEntity = new CommonDictItemEntity();
                itemEntity.setDictCode(vo.getDictCode());
                int update = itemMapper.update(itemEntity, lambdaQueryWrapper);
                if (update <= 0) {
                    throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新数据字典子项编码失败");
                }
            }
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<CommonDictPageDto> pageByModel(CommonDictPageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public CommonDictDto getById(String dictId) throws RuntimeException {
        CommonDictEntity byId = super.getById(dictId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "未找到符合条件数据");
        }
        return dtoMap.bToA(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String dictId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(dictId);
        boolean b = this.removeById(dictId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除数据失败");
        }
        // 查询是否有子项,有子项则删除子项
        // 查询是否有子项
        LambdaQueryWrapper<CommonDictItemEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CommonDictItemEntity::getDictId, dictId);
        List<CommonDictItemEntity> commonDictItemEntities = itemMapper.selectList(lambdaQueryWrapper);
        // 有子项则更新所有子项编码
        if (CollectionUtil.isNotEmpty(commonDictItemEntities)) {
            int delete = itemMapper.delete(lambdaQueryWrapper);
            if (delete <= 0) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除数据字典子项失败");
            }
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> dictIds) throws RuntimeException {
        List<CommonDictEntity> entities = this.listByIds(dictIds);
        if (CollectionUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "数据不存在或已经被别人删除");
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getDictId()));
        int i = mapper.deleteBatchIds(waitDeleteList);
        if (i <= 0) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "批量删除数据失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateDictState(String dictId, Integer type) throws RuntimeException {
        // 查询数据是否存在
        this.getById(dictId);
        CommonDictEntity entity = new CommonDictEntity();
        entity.setDictId(dictId);
        entity.setDictStatus(type == 1 ? 1 : 0);
        boolean b = this.updateById(entity);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新字典状态失败");
        }
    }
}