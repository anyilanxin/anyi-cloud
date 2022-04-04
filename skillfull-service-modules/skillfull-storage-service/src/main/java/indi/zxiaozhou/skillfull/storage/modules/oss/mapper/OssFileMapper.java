// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.storage.modules.oss.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.zxiaozhou.skillfull.coredatabase.base.mapper.BaseMapper;
import indi.zxiaozhou.skillfull.storage.modules.oss.controller.vo.OssFilePageVo;
import indi.zxiaozhou.skillfull.storage.modules.oss.entity.OssFileEntity;
import indi.zxiaozhou.skillfull.storage.modules.oss.service.dto.OssFilePageDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * oss文件(OssFile)持久层
 *
 * @author zxiaozhou
 * @date 2020-10-26 12:25:51
 * @since JDK11
 */
@Repository
public interface OssFileMapper extends BaseMapper<OssFileEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link OssFilePageVo} 查询条件
     * @param page ${@link Page<OssFilePageDto>} 分页信息
     * @return IPage<OssFilePageDto> ${@link IPage<OssFilePageDto>} 结果
     * @author zxiaozhou
     * @date 2020-10-26 12:25:51
     */
    IPage<OssFilePageDto> pageByModel(Page<OssFilePageDto> page, @Param("query") OssFilePageVo vo);


    /**
     * 通过文件id物理删除
     *
     * @param ossFileId ${@link String} 文件id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2020-08-28 11:36
     */
    int physicalDeleteById(@Param("id") String ossFileId);
}