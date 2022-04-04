package indi.zxiaozhou.skillfull.storage.modules.local.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import indi.zxiaozhou.skillfull.storage.modules.local.controller.vo.LocalConfigVo;
import indi.zxiaozhou.skillfull.storage.modules.local.entity.LocalConfigEntity;
import indi.zxiaozhou.skillfull.storage.modules.local.mapper.LocalConfigMapper;
import indi.zxiaozhou.skillfull.storage.modules.local.service.ILocalConfigService;
import indi.zxiaozhou.skillfull.storage.modules.local.service.dto.LocalConfigDto;
import indi.zxiaozhou.skillfull.storage.modules.local.service.mapstruct.LocalConfigMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 本地文件配置(LocalConfig)业务层实现
 *
 * @author zxiaozhou
 * @date 2021-07-21 16:10:05
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LocalConfigServiceImpl extends ServiceImpl<LocalConfigMapper, LocalConfigEntity> implements ILocalConfigService {
    private final LocalConfigMap map;
    private final LocalConfigMapper mapper;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(LocalConfigVo vo) throws RuntimeException {
        LocalConfigEntity entity = map.vToE(vo);
        boolean result = super.save(entity);

        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存数据失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String localConfigId, LocalConfigVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(localConfigId);
        // 更新数据
        LocalConfigEntity entity = map.vToE(vo);
        entity.setLocalConfigId(localConfigId);
        boolean result = super.updateById(entity);

        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新数据失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<LocalConfigDto> selectListByModel() throws RuntimeException {
        List<LocalConfigEntity> list = this.list();

        return map.eToD(list);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public LocalConfigDto getById(String localConfigId) throws RuntimeException {
        LocalConfigEntity byId = super.getById(localConfigId);

        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "未找到符合条件数据");
        }
        return map.eToD(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String localConfigId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(localConfigId);
        // 删除数据
        boolean b = this.removeById(localConfigId);

        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除数据失败");
        }
    }
}
