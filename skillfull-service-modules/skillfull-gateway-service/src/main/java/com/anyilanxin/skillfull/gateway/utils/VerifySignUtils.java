// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.gateway.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 验签工具
 *
 * @author zxiaozhou
 * @date 2021-07-29 15:03
 * @since JDK1.8
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class VerifySignUtils {
    private static VerifySignUtils utils;

    @PostConstruct
    private void init() {
        utils = this;
    }
}
