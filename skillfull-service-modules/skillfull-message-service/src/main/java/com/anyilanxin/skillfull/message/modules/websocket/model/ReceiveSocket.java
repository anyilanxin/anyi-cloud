// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.modules.websocket.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

/**
 * 接收socket消息
 *
 * @author zxiaozhou
 * @date 2021-01-08 15:57
 * @since JDK11
 */
@Getter
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ReceiveSocket implements Serializable {
    private static final long serialVersionUID = -7991672291592110700L;


}
