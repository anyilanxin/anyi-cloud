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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import indi.zxiaozhou.skillfull.corecommon.utils.tree.TreeToolUtils;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import indi.zxiaozhou.skillfull.system.modules.common.controller.vo.CommonAreaPageVo;
import indi.zxiaozhou.skillfull.system.modules.common.controller.vo.CommonAreaVo;
import indi.zxiaozhou.skillfull.system.modules.common.entity.CommonAreaEntity;
import indi.zxiaozhou.skillfull.system.modules.common.mapper.CommonAreaMapper;
import indi.zxiaozhou.skillfull.system.modules.common.service.ICommonAreaService;
import indi.zxiaozhou.skillfull.system.modules.common.service.dto.CommonAreaDto;
import indi.zxiaozhou.skillfull.system.modules.common.service.dto.CommonAreaPageDto;
import indi.zxiaozhou.skillfull.system.modules.common.service.mapstruct.CommonAreaDtoMap;
import indi.zxiaozhou.skillfull.system.modules.common.service.mapstruct.CommonAreaPageDtoMap;
import indi.zxiaozhou.skillfull.system.modules.common.service.mapstruct.CommonAreaVoMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 区域表(CommonArea)业务层实现
 *
 * @author zxiaozhou
 * @date 2020-11-02 09:25:04
 * @since JDK11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommonAreaServiceImpl extends ServiceImpl<CommonAreaMapper, CommonAreaEntity> implements ICommonAreaService {
    private final CommonAreaDtoMap dtoMap;
    private final CommonAreaVoMap voMap;
    private final CommonAreaPageDtoMap pageDtoMap;
    private final CommonAreaMapper mapper;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(CommonAreaVo vo) throws RuntimeException {
        CommonAreaEntity entity = voMap.aToB(vo);
        // 数据校验
        this.checkData(entity);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存数据失败");
        }
    }

    /**
     * 数据校验
     *
     * @param entity ${@link CommonAreaEntity} 待校验数据
     * @author zxiaozhou
     * @date 2021-01-07 20:54
     */
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    void checkData(CommonAreaEntity entity) {
        // 如果最上层，区域属于第一部分行政区域(前6位有效，每两位为一级)
        if (StringUtils.isBlank(entity.getParentId())) {
            String substring = entity.getAreaId().substring(0, 3).replaceAll("0+$", "");
            if (StringUtils.isNotBlank(substring)) {
                throw new ResponseException(Status.VERIFICATION_FAILED, "顶级区域id只能前2为非零");
            }
            entity.setAreaLevel(1);
            entity.setParentId("");
        }
        // 如果存在上级区域id,判断区域id格式
        else {
            String[] parentValidInfo = this.getParentValid(entity.getParentId());
            if (!entity.getAreaId().startsWith(parentValidInfo[0])) {
                throw new ResponseException(Status.VERIFICATION_FAILED, "下级id必须包含上级id末尾除外的部分");
            }
            entity.setAreaLevel(Integer.parseInt(parentValidInfo[1]) + 1);
        }
        // 查询当前区域编码是否已经存在
        LambdaQueryWrapper<CommonAreaEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CommonAreaEntity::getAreaId, entity.getAreaId());
        List<CommonAreaEntity> list = this.list(lambdaQueryWrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            throw new ResponseException(Status.VERIFICATION_FAILED, "当前区域id已经存在" + entity.getAreaId());
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String areaId, CommonAreaVo vo) throws RuntimeException {
        // 查询数据是否存在
        CommonAreaDto byId = this.getById(areaId);
        // 更新数据
        CommonAreaEntity entity = voMap.aToB(vo);
        if (!entity.getParentId().equals(byId.getParentId())) {
            throw new ResponseException(Status.VERIFICATION_FAILED, "上级id不允许改动");
        }
        // 数据校验
        this.checkData(entity);
        entity.setAreaId(areaId);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新数据失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<CommonAreaDto> selectList(String parentId) throws RuntimeException {
        LambdaQueryWrapper<CommonAreaEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(parentId)) {
            lambdaQueryWrapper.eq(CommonAreaEntity::getParentId, parentId);
        } else {
            lambdaQueryWrapper.eq(CommonAreaEntity::getParentId, "");
        }
        List<CommonAreaEntity> list = this.list(lambdaQueryWrapper);
        // 处理是否含有下级
        Set<String> parentIds = new HashSet<>();
        list.forEach(v -> parentIds.add(v.getAreaId()));
        lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(CommonAreaEntity::getParentId, parentIds);
        List<CommonAreaEntity> subList = this.list(lambdaQueryWrapper);
        List<CommonAreaDto> result = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(list)) {
            list.forEach(v -> {
                CommonAreaDto commonAreaDto = dtoMap.bToA(v);
                subList.forEach(sv -> {
                    if (v.getAreaId().equals(sv.getParentId())) {
                        commonAreaDto.setHasChildren(true);
                    }
                });
                result.add(commonAreaDto);
            });
        }
        if (CollectionUtil.isEmpty(result)) {
            return Collections.emptyList();
        }
        return result;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<CommonAreaPageDto> pageByModel(CommonAreaPageVo vo) throws RuntimeException {
        vo.getAscs().add("areaId");
        IPage<CommonAreaPageDto> page = mapper.pageByModel(vo.getPage(), vo);
        List<CommonAreaPageDto> records = page.getRecords();
        /*
         * 1. 获取根节点
         * 2. 构建树形
         */
        // 获取根节点
        if (CollectionUtil.isNotEmpty(records)) {
            List<CommonAreaPageDto> rootRecords = new ArrayList<>(records.size());
            List<CommonAreaPageDto> childRecords = new ArrayList<>(records.size());
            while (records.size() > 0) {
                CommonAreaPageDto pageDto = records.remove(0);
                String parentId = pageDto.getParentId();
                boolean isParent = true;
                if (StringUtils.isNotBlank(parentId)) {
                    for (CommonAreaPageDto dto : records) {
                        if (parentId.equals(dto.getAreaId())) {
                            isParent = false;
                            break;
                        }
                    }
                }

                if (isParent) {
                    rootRecords.add(pageDto);
                    // 添加所有子类
                    String areaId = getEffectiveAreaId(pageDto.getAreaId(), pageDto.getAreaLevel());
                    List<CommonAreaPageDto> collect = records.stream().filter(v -> v.getAreaId().startsWith(areaId)).collect(Collectors.toList());
                    if (CollectionUtil.isNotEmpty(collect)) {
                        records.removeAll(collect);
                        childRecords.addAll(collect);
                    }
                } else {
                    childRecords.add(pageDto);
                }
            }
            // 构建树形
            TreeToolUtils<CommonAreaPageDto> treeToolUtils = new TreeToolUtils<>(rootRecords, childRecords, new TreeToolUtils.TreeId<>() {
                @Override
                public String getId(CommonAreaPageDto orgTreePageDto) {
                    return orgTreePageDto.getAreaId();
                }

                @Override
                public String getParentId(CommonAreaPageDto orgTreePageDto) {
                    return orgTreePageDto.getParentId();
                }
            });
            records = treeToolUtils.getTree();
        }
        return new PageDto<>(page, records);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<CommonAreaPageDto> selectPageChildren(String parentId) throws RuntimeException {
        LambdaQueryWrapper<CommonAreaEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CommonAreaEntity::getParentId, parentId);
        List<CommonAreaEntity> list = this.list(lambdaQueryWrapper);
        List<CommonAreaPageDto> pageDtoList = pageDtoMap.bToA(list);
        // 处理是否含有下级
        if (CollectionUtil.isNotEmpty(pageDtoList)) {
            Set<String> parentIds = new HashSet<>();
            pageDtoList.forEach(v -> parentIds.add(v.getAreaId()));
            lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.in(CommonAreaEntity::getParentId, parentIds);
            List<CommonAreaEntity> children = this.list(lambdaQueryWrapper);
            if (CollectionUtil.isNotEmpty(children)) {
                pageDtoList.forEach(v -> {
                    for (CommonAreaEntity entity : children) {
                        if (entity.getParentId().equals(v.getAreaId())) {
                            v.setHasChildren(true);
                            break;
                        }
                    }
                });
            }
        }
        return pageDtoList;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public CommonAreaDto getById(String areaId) throws RuntimeException {
        CommonAreaEntity byId = super.getById(areaId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "未找到符合条件数据");
        }
        return dtoMap.bToA(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String areaId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(areaId);
        // 判断是否有下级
        LambdaQueryWrapper<CommonAreaEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CommonAreaEntity::getParentId, areaId);
        List<CommonAreaEntity> list = this.list(lambdaQueryWrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            throw new ResponseException(Status.VERIFICATION_FAILED, "区域id:" + areaId + "存在下级,请先删除下级");
        }
        boolean b = this.removeById(areaId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除数据失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> areaIds) throws RuntimeException {
        List<CommonAreaEntity> entities = this.listByIds(areaIds);
        if (CollectionUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "数据不存在或已经被别人删除");
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getAreaId()));
        int i = mapper.deleteBatchIds(waitDeleteList);
        if (i <= 0) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "批量删除数据失败");
        }
    }


    /**
     * 获取有效的上级区域id信息
     *
     * @param parentId ${@link String} 上级区域编码
     * @return String[] ${@link String[]} 0-上级有效区域编码,1-上级区域编码等级
     * @author zxiaozhou
     * @date 2021-01-07 23:18
     */
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    String[] getParentValid(String parentId) {
        CommonAreaDto byId = this.getById(parentId);
        // 数据第一部分行政区域(前6位，每两位代表一级)
        if (byId.getAreaLevel() <= 3) {
            parentId = byId.getAreaId().substring(0, byId.getAreaLevel() * 2 + 1);
        }
        // 属于第一部分+第二部分区域(前6位位一级，每两位代表一级，后6位位二级，每3位为一级)
        else {
            parentId = byId.getAreaId().substring(0, 3 * (byId.getAreaLevel() - 1));
        }
        return new String[]{parentId, byId.getAreaLevel() + ""};
    }


    /**
     * 获取有效的区域id
     *
     * @param areaId    ${@link String}
     * @param areaLevel ${@link Integer} 区域级别
     * @return String ${@link String}
     * @author zxiaozhou
     * @date 2021-08-22 20:21
     */
    String getEffectiveAreaId(String areaId, int areaLevel) {
        String effectiveAreaId = areaId;
        if (areaLevel <= 3) {
            effectiveAreaId = areaId.substring(0, areaLevel * 2 + 1);
        }
        // 属于第一部分+第二部分区域(前6位位一级，每两位代表一级，后6位位二级，每3位为一级)
        else {
            effectiveAreaId = areaId.substring(0, 3 * (areaLevel - 1));
        }
        return effectiveAreaId;
    }
}