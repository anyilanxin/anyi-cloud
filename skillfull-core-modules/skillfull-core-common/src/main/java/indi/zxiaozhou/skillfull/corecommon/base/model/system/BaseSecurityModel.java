// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.corecommon.base.model.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 密钥相关基础
 *
 * @author zxiaozhou
 * @date 2020-10-20 21:47
 * @since JDK11
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BaseSecurityModel implements Serializable {
    private static final long serialVersionUID = 4280855850409329888L;

    @Schema(name = "privateKey", title = "私钥")
    private String privateKey;


    @Schema(name = "privateKey", title = "公钥")
    private String publicKey;

}
