package com.anyilanxin.skillfull.message.modules.manage.service;

import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.message.modules.manage.controller.vo.ManageAnnouncementPageVo;
import com.anyilanxin.skillfull.message.modules.manage.controller.vo.ManageAnnouncementQueryVo;
import com.anyilanxin.skillfull.message.modules.manage.controller.vo.ManageAnnouncementVo;
import com.anyilanxin.skillfull.message.modules.manage.entity.ManageAnnouncementEntity;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageAnnouncementDto;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageAnnouncementPageDto;

import java.util.List;

/**
 * 系统通告公告管理(ManageAnnouncement)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-03-29 08:34:22
 * @since JDK1.8
 */
public interface IManageAnnouncementService extends BaseService<ManageAnnouncementEntity> {
    /**
     * 保存
     *
     * @param vo ${@link ManageAnnouncementVo} 系统通告公告管理保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 08:34:22
     */
    void save(ManageAnnouncementVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo     ${@link ManageAnnouncementVo} 系统通告公告管理更新
     * @param anntId ${@link String} 通知公告id
     * @param vo     ${@link ManageAnnouncementVo} 系统通告公告管理更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 08:34:22
     */
    void updateById(String anntId, ManageAnnouncementVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link ManageAnnouncementPageVo} 系统通告公告管理分页查询Vo
     * @return PageDto<ManageAnnouncementPageDto> ${@link PageDto< ManageAnnouncementPageDto >} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 08:34:22
     */
    PageDto<ManageAnnouncementPageDto> pageByModel(ManageAnnouncementPageVo vo) throws RuntimeException;

    /**
     * 条件查询多条
     *
     * @param vo ${@link ManageAnnouncementQueryVo} 系统通告公告管理条件查询Vo
     * @return List<ManageAnnouncementDto> ${@link List< ManageAnnouncementDto >} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 08:34:22
     */
    List<ManageAnnouncementDto> selectListByModel(ManageAnnouncementQueryVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param anntId ${@link String} 通知公告id
     * @return ManageAnnouncementDto ${@link ManageAnnouncementDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 08:34:22
     */
    ManageAnnouncementDto getById(String anntId) throws RuntimeException;


    /**
     * 通过anntId删除
     *
     * @param anntId ${@link String} 通知公告id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 08:34:22
     */
    void deleteById(String anntId) throws RuntimeException;


    /**
     * 系统通告公告管理批量删除
     *
     * @param anntIds ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 08:34:22
     */
    void deleteBatch(List<String> anntIds) throws RuntimeException;
}
