// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.corecommon.model.auth;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * oauth2 客户端扩展扩展信息
 *
 * @author zxiaozhou
 * @date 2022-03-06 23:34
 * @since JDK1.8
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class StoredRequestExtension implements Serializable {
    public final static String EXTENSION_KEY = "STORED_REQUEST_EXTENSION";

    /**
     * 限制授权资源：0-不限制，1-限制。默认1
     */
    private Integer limitResource;
}
