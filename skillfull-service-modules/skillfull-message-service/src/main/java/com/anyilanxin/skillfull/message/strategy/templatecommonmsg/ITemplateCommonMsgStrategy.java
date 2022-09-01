// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.strategy.templatecommonmsg;

import com.anyilanxin.skillfull.message.modules.manage.entity.ManageSendRecordEntity;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageTemplateSendInfoDto;
import com.anyilanxin.skillfull.messagerpc.model.TemplateCommonMsgModel;

import java.util.List;

/**
 * 消息没了没有子类的消息处理
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-08-27 11:58
 * @since JDK11
 */
public interface ITemplateCommonMsgStrategy {
    /**
     * 发送其他消息
     *
     * @param model
     * @param sendInfo
     * @return List<ManageTemplateEntity>
     * @author zxiaozhou
     * @date 2022-08-30 09:25
     */
    List<ManageSendRecordEntity> sendMsg(TemplateCommonMsgModel model, ManageTemplateSendInfoDto sendInfo);
}
