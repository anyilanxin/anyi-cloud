// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.storage.modules.local.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.zxiaozhou.skillfull.coredatabase.base.mapper.BaseMapper;
import indi.zxiaozhou.skillfull.storage.modules.local.controller.vo.LocalFilePageVo;
import indi.zxiaozhou.skillfull.storage.modules.local.entity.LocalFileEntity;
import indi.zxiaozhou.skillfull.storage.modules.local.service.dto.LocalFileDto;
import indi.zxiaozhou.skillfull.storage.modules.local.service.dto.LocalFilePageDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 本地文件服务(LocalFile)持久层
 *
 * @author zxiaozhou
 * @date 2020-10-23 22:25:30
 * @since JDK11
 */
@Repository
public interface LocalFileMapper extends BaseMapper<LocalFileEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link LocalFilePageVo} 查询条件
     * @param page ${@link Page<LocalFilePageDto>} 分页信息
     * @return IPage<LocalFilePageDto> ${@link IPage<LocalFilePageDto>} 结果
     * @author zxiaozhou
     * @date 2020-10-23 22:25:30
     */
    IPage<LocalFilePageDto> pageByModel(Page<LocalFilePageDto> page, @Param("query") LocalFilePageVo vo, String sysPrefix);

    /**
     * 通过文件id获取文件详细信息
     *
     * @param localFileId ${@link String}
     * @return LocalFileDto ${@link LocalFileDto}
     * @author zxiaozhou
     * @date 2021-07-22 11:33
     */
    LocalFileDto getFileById(String localFileId);

    /**
     * 通过文件预览地址获取文件详细信息
     *
     * @param previewPath ${@link String}
     * @return LocalFileDto ${@link LocalFileDto}
     * @author zxiaozhou
     * @date 2021-07-22 11:33
     */
    LocalFileDto getFileByPreviewPath(String previewPath);

    /**
     * 通过文件id物理删除
     *
     * @param localFileId ${@link String} 文件id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2020-08-28 11:36
     */
    int physicalDeleteById(@Param("id") String localFileId);
}