// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.system.modules.manage.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import indi.zxiaozhou.skillfull.coredatabase.base.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * 路由特殊地址(ManageSpecialUrl)Entity
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2021-12-19 09:34:50
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_manage_special_url")
@Schema
public class ManageSpecialUrlEntity extends BaseEntity {
    private static final long serialVersionUID = 465326742721077371L;

    @TableId
    private String specialUrlId;

    @Schema(name = "urlName", title = "接口名称")
    private String urlName;

    @Schema(name = "urlDescribe", title = "接口描述")
    private String urlDescribe;

    @Schema(name = "urlType", title = "地址类型:0-系统,1-自定义,默认0")
    private Integer urlType;

    @Schema(name = "specialStatus", title = "特殊地址状态:0-禁用,1-启用，默认0")
    private Integer specialStatus;

    @Schema(name = "limitMethod", title = "限制请求方法：0-不限制,1-限制")
    private Integer limitMethod;

    @Schema(name = "requestMethod", title = "允许的请求方法,多个英文逗号隔开")
    private String requestMethod;

    @Schema(name = "url", title = "地址,服务地址或http地址")
    private String url;

    @Schema(name = "enableDelete", title = "是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)")
    private Integer enableDelete;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "createAreaCode", title = "创建区域编码")
    private String createAreaCode;

    @Schema(name = "createPositionCode", title = "创建职位编码")
    private String createPositionCode;

    @Schema(name = "createOrgSysCode", title = "创建机构系统编码")
    private String createOrgSysCode;

    @Schema(name = "createSystemCode", title = "创建系统编码")
    private String createSystemCode;

    @Schema(name = "createTenantId", title = "创建租户id")
    private String createTenantId;

    @Schema(name = "customFilterId", title = "自定义过滤器id")
    private String customFilterId;
}
