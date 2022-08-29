// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.manage.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.EventListener;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.anyilanxin.skillfull.system.modules.manage.entity.ManageServiceEntity;
import com.anyilanxin.skillfull.system.modules.manage.mapper.ManageServiceMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * nacos服务变更订阅处理
 *
 * @author zxiaozhou zxiaozhou
 * @date 2020-10-11 15:21
 * @since JDK1.8
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NacosEventListener implements EventListener {
    private final ManageServiceMapper systemMapper;


    @Override
    public void onEvent(Event event) {
        NamingEvent namingEvent = (NamingEvent) event;
        this.sendMsg(namingEvent);
    }


    /**
     * 监听消息通知处理
     *
     * @param event ${@link NamingEvent} 服务事件
     * @author zxiaozhou zxiaozhou
     * @date 2021-01-28 17:06
     */
    private void sendMsg(NamingEvent event) {
        log.info("------------NacosEventListener------服务发生了变化------>sendMsg:{}", event.getServiceName());
        LambdaQueryWrapper<ManageServiceEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ManageServiceEntity::getServiceCode, event.getServiceName());
        List<ManageServiceEntity> serviceEntities = systemMapper.selectList(lambdaQueryWrapper);
        if (CollUtil.isNotEmpty(serviceEntities)) {
            log.info("------------NacosEventListener------服务信息------>sendMsg:{}", serviceEntities);
        }
    }
}
