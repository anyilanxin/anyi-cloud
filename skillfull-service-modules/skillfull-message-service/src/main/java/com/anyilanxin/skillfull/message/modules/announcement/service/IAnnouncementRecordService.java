package com.anyilanxin.skillfull.message.modules.announcement.service;

import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.message.modules.announcement.controller.vo.AnnouncementRecordPageVo;
import com.anyilanxin.skillfull.message.modules.announcement.controller.vo.AnnouncementRecordQueryVo;
import com.anyilanxin.skillfull.message.modules.announcement.controller.vo.AnnouncementRecordVo;
import com.anyilanxin.skillfull.message.modules.announcement.entity.AnnouncementRecordEntity;
import com.anyilanxin.skillfull.message.modules.announcement.service.dto.AnnouncementRecordDto;
import com.anyilanxin.skillfull.message.modules.announcement.service.dto.AnnouncementRecordPageDto;

import java.util.List;

/**
 * 系统通知公告阅读记录(AnnouncementRecord)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-03-29 08:35:34
 * @since JDK1.8
 */
public interface IAnnouncementRecordService extends BaseService<AnnouncementRecordEntity> {
    /**
     * 保存
     *
     * @param vo ${@link AnnouncementRecordVo} 系统通知公告阅读记录保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 08:35:34
     */
    void save(AnnouncementRecordVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo         ${@link AnnouncementRecordVo} 系统通知公告阅读记录更新
     * @param anntReadId ${@link String} 通知公告阅读记录id
     * @param vo         ${@link AnnouncementRecordVo} 系统通知公告阅读记录更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 08:35:34
     */
    void updateById(String anntReadId, AnnouncementRecordVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link AnnouncementRecordPageVo} 系统通知公告阅读记录分页查询Vo
     * @return PageDto<AnnouncementRecordPageDto> ${@link PageDto< AnnouncementRecordPageDto >} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 08:35:34
     */
    PageDto<AnnouncementRecordPageDto> pageByModel(AnnouncementRecordPageVo vo) throws RuntimeException;

    /**
     * 条件查询多条
     *
     * @param vo ${@link AnnouncementRecordQueryVo} 系统通知公告阅读记录条件查询Vo
     * @return List<AnnouncementRecordDto> ${@link List< AnnouncementRecordDto >} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 08:35:34
     */
    List<AnnouncementRecordDto> selectListByModel(AnnouncementRecordQueryVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param anntReadId ${@link String} 通知公告阅读记录id
     * @return AnnouncementRecordDto ${@link AnnouncementRecordDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 08:35:34
     */
    AnnouncementRecordDto getById(String anntReadId) throws RuntimeException;


    /**
     * 通过anntReadId删除
     *
     * @param anntReadId ${@link String} 通知公告阅读记录id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 08:35:34
     */
    void deleteById(String anntReadId) throws RuntimeException;


    /**
     * 系统通知公告阅读记录批量删除
     *
     * @param anntReadIds ${@link List<String>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2022-03-29 08:35:34
     */
    void deleteBatch(List<String> anntReadIds) throws RuntimeException;
}
