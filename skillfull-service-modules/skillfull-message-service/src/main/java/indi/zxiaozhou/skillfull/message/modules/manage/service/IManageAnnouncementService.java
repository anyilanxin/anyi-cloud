// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.message.modules.manage.service;


import indi.zxiaozhou.skillfull.coredatabase.base.service.BaseService;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import indi.zxiaozhou.skillfull.message.modules.manage.controller.vo.ManageAnnouncementPageVo;
import indi.zxiaozhou.skillfull.message.modules.manage.controller.vo.ManageAnnouncementQueryVo;
import indi.zxiaozhou.skillfull.message.modules.manage.controller.vo.ManageAnnouncementVo;
import indi.zxiaozhou.skillfull.message.modules.manage.entity.ManageAnnouncementEntity;
import indi.zxiaozhou.skillfull.message.modules.manage.service.dto.ManageAnnouncementDto;
import indi.zxiaozhou.skillfull.message.modules.manage.service.dto.ManageAnnouncementPageDto;

import java.util.List;

/**
 * 系统通告管理(ManageAnnouncement)业务层接口
 *
 * @author zxiaozhou
 * @date 2021-02-12 19:57:08
 * @since JDK1.8
 */
public interface IManageAnnouncementService extends BaseService<ManageAnnouncementEntity> {
    /**
     * 保存
     *
     * @param vo ${@link ManageAnnouncementVo} 系统通告管理保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-02-12 19:57:08
     */
    void save(ManageAnnouncementVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo     ${@link ManageAnnouncementVo} 系统通告管理更新
     * @param anntId ${@link String} 公告id
     * @param vo     ${@link ManageAnnouncementVo} 系统通告管理更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-02-12 19:57:08
     */
    void updateById(String anntId, ManageAnnouncementVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link ManageAnnouncementPageVo} 系统通告管理分页查询Vo
     * @return PageDto<ManageAnnouncementPageDto> ${@link PageDto<ManageAnnouncementPageDto>} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-02-12 19:57:08
     */
    PageDto<ManageAnnouncementPageDto> pageByModel(ManageAnnouncementPageVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link ManageAnnouncementPageVo} 系统通告管理分页查询Vo
     * @return PageDto<ManageAnnouncementPageDto> ${@link PageDto<ManageAnnouncementPageDto>} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-02-12 19:57:08
     */
    PageDto<ManageAnnouncementPageDto> page(ManageAnnouncementPageVo vo) throws RuntimeException;


    /**
     * 条件查询多条
     *
     * @param vo ${@link ManageAnnouncementQueryVo} 系统通告管理条件查询Vo
     * @return List<ManageAnnouncementDto> ${@link List<ManageAnnouncementDto>} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-02-12 19:57:08
     */
    List<ManageAnnouncementDto> selectListByModel(ManageAnnouncementQueryVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param anntId ${@link String} 公告id
     * @return ManageAnnouncementDto ${@link ManageAnnouncementDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-02-12 19:57:08
     */
    ManageAnnouncementDto getById(String anntId) throws RuntimeException;


    /**
     * 通过anntId删除
     *
     * @param anntId ${@link String} 公告id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-08-28 12:20
     */
    void deleteById(String anntId) throws RuntimeException;


    /**
     * 系统通告管理批量删除
     *
     * @param anntIds ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-10-24 22:59
     */
    void deleteBatch(List<String> anntIds) throws RuntimeException;
}