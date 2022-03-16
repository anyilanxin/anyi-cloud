// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------

package indi.zxiaozhou.skillfull.auth.modules.common.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.zxiaozhou.skillfull.auth.modules.common.controller.vo.CommonDataMapPageVo;
import indi.zxiaozhou.skillfull.auth.modules.common.controller.vo.CommonDataMapQueryVo;
import indi.zxiaozhou.skillfull.auth.modules.common.controller.vo.CommonDataMapVo;
import indi.zxiaozhou.skillfull.auth.modules.common.entity.CommonDataMapEntity;
import indi.zxiaozhou.skillfull.auth.modules.common.mapper.CommonDataMapMapper;
import indi.zxiaozhou.skillfull.auth.modules.common.service.ICommonDataMapService;
import indi.zxiaozhou.skillfull.auth.modules.common.service.dto.CommonDataMapDto;
import indi.zxiaozhou.skillfull.auth.modules.common.service.dto.CommonDataMapPageDto;
import indi.zxiaozhou.skillfull.auth.modules.common.service.mapstruct.CommonDataMapCopyMap;
import indi.zxiaozhou.skillfull.auth.modules.common.service.mapstruct.CommonDataMapPageCopyMap;
import indi.zxiaozhou.skillfull.auth.modules.common.service.mapstruct.CommonDataMapQueryCopyMap;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 数据映射表(CommonDataMap)业务层实现
 *
 * @author zxiaozhou
 * @date 2021-07-28 11:53:28
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommonDataMapServiceImpl extends ServiceImpl<CommonDataMapMapper, CommonDataMapEntity> implements ICommonDataMapService {
    private final CommonDataMapCopyMap map;
    private final CommonDataMapPageCopyMap pageMap;
    private final CommonDataMapQueryCopyMap queryMap;
    private final CommonDataMapMapper mapper;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(CommonDataMapVo vo) throws RuntimeException {
        CommonDataMapEntity entity = map.vToE(vo);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存数据失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String dataMapId, CommonDataMapVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(dataMapId);
        // 更新数据
        CommonDataMapEntity entity = map.vToE(vo);
        entity.setDataMapId(dataMapId);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新数据失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<CommonDataMapDto> selectListByModel(CommonDataMapQueryVo vo) throws RuntimeException {
        List<CommonDataMapDto> list = mapper.selectListByModel(vo);
        if (CollectionUtils.isEmpty(list)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "未找到符合条件数据");
        }
        return list;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<CommonDataMapPageDto> pageByModel(CommonDataMapPageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public CommonDataMapDto getById(String dataMapId) throws RuntimeException {
        CommonDataMapEntity byId = super.getById(dataMapId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "未找到符合条件数据");
        }
        return map.eToD(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String dataMapId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(dataMapId);
        // 删除数据
        boolean b = this.removeById(dataMapId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除数据失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> dataMapIds) throws RuntimeException {
        List<CommonDataMapEntity> entities = this.listByIds(dataMapIds);
        if (CollectionUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "数据不存在或已经被别人删除");
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getDataMapId()));
        int i = mapper.deleteBatchIds(waitDeleteList);
        if (i <= 0) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "批量删除数据失败");
        }
    }
}
