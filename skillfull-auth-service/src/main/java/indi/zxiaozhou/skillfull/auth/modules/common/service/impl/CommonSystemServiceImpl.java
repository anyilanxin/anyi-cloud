package indi.zxiaozhou.skillfull.auth.modules.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.zxiaozhou.skillfull.auth.modules.common.controller.vo.CommonSystemPageVo;
import indi.zxiaozhou.skillfull.auth.modules.common.controller.vo.CommonSystemVo;
import indi.zxiaozhou.skillfull.auth.modules.common.entity.CommonSystemEntity;
import indi.zxiaozhou.skillfull.auth.modules.common.mapper.CommonSystemMapper;
import indi.zxiaozhou.skillfull.auth.modules.common.service.ICommonSystemService;
import indi.zxiaozhou.skillfull.auth.modules.common.service.dto.CommonSystemDto;
import indi.zxiaozhou.skillfull.auth.modules.common.service.dto.CommonSystemPageDto;
import indi.zxiaozhou.skillfull.auth.modules.common.service.mapstruct.CommonSystemCopyMap;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 系统(CommonSystem)业务层实现
 *
 * @author zxiaozhou
 * @date 2021-07-28 09:35:45
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommonSystemServiceImpl extends ServiceImpl<CommonSystemMapper, CommonSystemEntity> implements ICommonSystemService {
    private final CommonSystemCopyMap map;
    private final CommonSystemMapper mapper;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(CommonSystemVo vo) throws RuntimeException {
        CommonSystemEntity entity = map.vToE(vo);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存数据失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String systemId, CommonSystemVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(systemId);
        // 更新数据
        CommonSystemEntity entity = map.vToE(vo);
        entity.setSystemId(systemId);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新数据失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<CommonSystemDto> selectList() throws RuntimeException {
        List<CommonSystemEntity> list = this.list();
        return map.eToD(list);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<CommonSystemPageDto> pageByModel(CommonSystemPageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public CommonSystemDto getById(String systemId) throws RuntimeException {
        CommonSystemEntity byId = super.getById(systemId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "未找到符合条件数据");
        }
        return map.eToD(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String systemId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(systemId);
        // 删除数据
        boolean b = this.removeById(systemId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除数据失败");
        }
    }
}
