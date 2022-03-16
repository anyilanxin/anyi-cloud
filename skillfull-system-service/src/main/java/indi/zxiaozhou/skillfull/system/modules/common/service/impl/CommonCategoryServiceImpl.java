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
import indi.zxiaozhou.skillfull.system.modules.common.controller.vo.CommonCategoryPageVo;
import indi.zxiaozhou.skillfull.system.modules.common.controller.vo.CommonCategoryVo;
import indi.zxiaozhou.skillfull.system.modules.common.entity.CommonCategoryEntity;
import indi.zxiaozhou.skillfull.system.modules.common.mapper.CommonCategoryMapper;
import indi.zxiaozhou.skillfull.system.modules.common.service.ICommonCategoryService;
import indi.zxiaozhou.skillfull.system.modules.common.service.dto.CommonCategoryDto;
import indi.zxiaozhou.skillfull.system.modules.common.service.dto.CommonCategoryPageDto;
import indi.zxiaozhou.skillfull.system.modules.common.service.dto.CommonCategoryTreeDto;
import indi.zxiaozhou.skillfull.system.modules.common.service.mapstruct.CommonCategoryDtoMap;
import indi.zxiaozhou.skillfull.system.modules.common.service.mapstruct.CommonCategoryPageDtoMap;
import indi.zxiaozhou.skillfull.system.modules.common.service.mapstruct.CommonCategoryTreeDtoMap;
import indi.zxiaozhou.skillfull.system.modules.common.service.mapstruct.CommonCategoryVoMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 分类字典表(CommonCategory)业务层实现
 *
 * @author zxiaozhou
 * @date 2021-01-07 23:40:21
 * @since JDK11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommonCategoryServiceImpl extends ServiceImpl<CommonCategoryMapper, CommonCategoryEntity> implements ICommonCategoryService {
    private final CommonCategoryDtoMap dtoMap;
    private final CommonCategoryTreeDtoMap treeDtoMap;
    private final CommonCategoryPageDtoMap pageDtoMap;
    private final CommonCategoryVoMap voMap;
    private final CommonCategoryMapper mapper;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(CommonCategoryVo vo) throws RuntimeException {
        CommonCategoryEntity entity = voMap.aToB(vo);
        this.checkData(entity);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存数据失败");
        }
    }

    /**
     * 数据检查并返回统一编码
     *
     * @param entity ${@link CommonCategoryEntity} 待检测数据
     * @author zxiaozhou
     * @date 2021-01-07 23:43
     */
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    void checkData(CommonCategoryEntity entity) {
        // 如果是子节点父节点必须传
        if (entity.getIsParent() == 1 && StringUtils.isBlank(entity.getParentId())) {
            throw new ResponseException(Status.VERIFICATION_FAILED, "添加子节点时父节点id必填");
        }
        // 查询分类统一编码是否存在
        LambdaQueryWrapper<CommonCategoryEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isBlank(entity.getParentId())) {
            entity.setParentId("");
            lambdaQueryWrapper.eq(CommonCategoryEntity::getCategoryCommonCode, entity.getCategoryCommonCode());
            List<CommonCategoryEntity> list = this.list(lambdaQueryWrapper);
            if (CollectionUtil.isNotEmpty(list)) {
                throw new ResponseException(Status.VERIFICATION_FAILED, "当前分类统一编码:" + entity.getCategoryCommonCode() + "已经存在");
            }
        }
        // 分类编码是否重复
        else {
            CommonCategoryDto byId = this.getById(entity.getParentId());
            lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(CommonCategoryEntity::getCategoryCommonCode, byId.getCategoryCommonCode())
                    .eq(CommonCategoryEntity::getCategoryCode, entity.getCategoryCode());
            // 如果分类id存在则说明为更新
            if (StringUtils.isNotBlank(entity.getCategoryId())) {
                lambdaQueryWrapper.ne(CommonCategoryEntity::getCategoryId, entity.getCategoryId());
            }
            List<CommonCategoryEntity> list = this.list(lambdaQueryWrapper);
            if (CollectionUtil.isNotEmpty(list)) {
                throw new ResponseException(Status.VERIFICATION_FAILED, "当前分类编码:" + entity.getCategoryCode() + "已经存在");
            }
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String categoryId, CommonCategoryVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(categoryId);
        // 更新数据
        CommonCategoryEntity entity = voMap.aToB(vo);
        entity.setCategoryId(categoryId);
        this.checkData(entity);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新数据失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<CommonCategoryDto> selectListByCommonCode(String categoryCommonCode) throws RuntimeException {
        LambdaQueryWrapper<CommonCategoryEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CommonCategoryEntity::getCategoryCommonCode, categoryCommonCode);
        List<CommonCategoryEntity> list = this.list(lambdaQueryWrapper);
        return dtoMap.bToA(list);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<CommonCategoryTreeDto> selectTreeListByCommonCode(String categoryCommonCode) throws RuntimeException {
        List<CommonCategoryDto> commonCategoryDtos = this.selectListByCommonCode(categoryCommonCode);
        List<CommonCategoryTreeDto> commonCategoryTreeDtos = treeDtoMap.bToA(commonCategoryDtos);
        // 获取根节点
        if (CollectionUtil.isNotEmpty(commonCategoryTreeDtos)) {
            List<CommonCategoryTreeDto> rootList = new ArrayList<>();
            for (CommonCategoryTreeDto treeDto : commonCategoryTreeDtos) {
                if (StringUtils.isBlank(treeDto.getParentId())) {
                    rootList.add(treeDto);
                    break;
                }
            }
            // 构建树形
            TreeToolUtils<CommonCategoryTreeDto> toolUtils = new TreeToolUtils<>(rootList, commonCategoryTreeDtos, new TreeToolUtils.TreeId<>() {
                @Override
                public String getId(CommonCategoryTreeDto commonCategoryTreeDto) {
                    return commonCategoryTreeDto.getCategoryId();
                }

                @Override
                public String getParentId(CommonCategoryTreeDto commonCategoryTreeDto) {
                    return commonCategoryTreeDto.getParentId();
                }
            });
            return toolUtils.getTree();
        }
        return Collections.emptyList();
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<CommonCategoryPageDto> pageByModel(CommonCategoryPageVo vo) throws RuntimeException {
        IPage<CommonCategoryPageDto> page = mapper.pageByModel(vo.getPage(), vo);
        List<CommonCategoryPageDto> records = page.getRecords();
        // 判断是否有下级
        if (CollectionUtil.isNotEmpty(records)) {
            Set<String> parentIds = new HashSet<>();
            records.forEach(v -> parentIds.add(v.getCategoryId()));
            LambdaQueryWrapper<CommonCategoryEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.in(CommonCategoryEntity::getParentId, parentIds);
            List<CommonCategoryEntity> list = this.list(lambdaQueryWrapper);
            if (CollectionUtil.isNotEmpty(list)) {
                records.forEach(v -> {
                    for (CommonCategoryEntity entity : list) {
                        if (entity.getParentId().equals(v.getCategoryId())) {
                            v.setHasChildren(true);
                            break;
                        }
                    }
                });
            }
        }
        return new PageDto<>(page, records);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<CommonCategoryPageDto> selectPageChildren(String parentId) throws RuntimeException {
        LambdaQueryWrapper<CommonCategoryEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CommonCategoryEntity::getParentId, parentId);
        List<CommonCategoryEntity> list = this.list(lambdaQueryWrapper);
        List<CommonCategoryPageDto> commonCategoryPageDtos = pageDtoMap.bToA(list);
        // 查询是否有下级
        if (CollectionUtil.isNotEmpty(commonCategoryPageDtos)) {
            Set<String> parentIds = new HashSet<>();
            commonCategoryPageDtos.forEach(v -> parentIds.add(v.getCategoryId()));
            // 下级数据
            lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.in(CommonCategoryEntity::getParentId, parentIds);
            List<CommonCategoryEntity> children = this.list(lambdaQueryWrapper);
            if (CollectionUtil.isNotEmpty(children)) {
                commonCategoryPageDtos.forEach(v -> {
                    for (CommonCategoryEntity entity : children) {
                        if (entity.getParentId().equals(v.getCategoryId())) {
                            v.setHasChildren(true);
                            break;
                        }
                    }
                });
            }
        }
        return commonCategoryPageDtos;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public CommonCategoryDto getById(String categoryId) throws RuntimeException {
        CommonCategoryEntity byId = super.getById(categoryId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "未找到符合条件数据");
        }
        return dtoMap.bToA(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String categoryId) throws RuntimeException {
        // 查询数据是否存在
        CommonCategoryDto byId = this.getById(categoryId);
        // 查看是否存在下级
        LambdaQueryWrapper<CommonCategoryEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CommonCategoryEntity::getParentId, byId.getCategoryId());
        List<CommonCategoryEntity> list = this.list(lambdaQueryWrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            throw new ResponseException(Status.VERIFICATION_FAILED, "存在下级,请先删除下级");
        }
        boolean b = this.removeById(categoryId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除数据失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> categoryIds) throws RuntimeException {
        List<CommonCategoryEntity> entities = this.listByIds(categoryIds);
        if (CollectionUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "数据不存在或已经被别人删除");
        }
        List<String> waitDeleteList = new ArrayList<>();
        entities.forEach(v -> waitDeleteList.add(v.getCategoryId()));
        int i = mapper.deleteBatchIds(waitDeleteList);
        if (i <= 0) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "批量删除数据失败");
        }
    }


    @Override
    public List<CommonCategoryTreeDto> selectAllTree() throws RuntimeException {
        // 获取根节点
        LambdaQueryWrapper<CommonCategoryEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.or(v -> v.isNull(CommonCategoryEntity::getParentId).or().eq(CommonCategoryEntity::getParentId, ""));
        List<CommonCategoryEntity> list = this.list(lambdaQueryWrapper);
        List<CommonCategoryTreeDto> rootList = treeDtoMap.bToA(dtoMap.bToA(list));
        // 获取子节点
        if (CollectionUtil.isNotEmpty(rootList)) {
            lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.isNotNull(CommonCategoryEntity::getParentId);
            list = this.list(lambdaQueryWrapper);
            List<CommonCategoryTreeDto> subList = treeDtoMap.bToA(dtoMap.bToA(list));
            // 构建树
            TreeToolUtils<CommonCategoryTreeDto> toolUtils = new TreeToolUtils<>(rootList, subList, new TreeToolUtils.TreeId<>() {
                @Override
                public String getId(CommonCategoryTreeDto commonCategoryTreeDto) {
                    return commonCategoryTreeDto.getCategoryId();
                }

                @Override
                public String getParentId(CommonCategoryTreeDto commonCategoryTreeDto) {
                    return commonCategoryTreeDto.getParentId();
                }
            });
            return toolUtils.getTree();
        }
        return Collections.emptyList();
    }
}