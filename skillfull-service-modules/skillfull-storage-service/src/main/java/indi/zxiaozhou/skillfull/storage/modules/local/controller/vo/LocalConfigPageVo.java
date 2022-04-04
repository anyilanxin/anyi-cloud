package indi.zxiaozhou.skillfull.storage.modules.local.controller.vo;

import indi.zxiaozhou.skillfull.coredatabase.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 本地文件配置分页查询Request
 *
 * @author zxiaozhou
 * @date 2021-07-21 16:10:05
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@Accessors(chain = true)
@Schema
@AllArgsConstructor
@NoArgsConstructor
public class LocalConfigPageVo extends BasePageVo {
    private static final long serialVersionUID = -81555622520517304L;

    @Schema(name = "localConfigId", title = "本地配置文件id")
    private String localConfigId;

    @Schema(name = "localConfigName", title = "配置名称")
    private String localConfigName;

    @Schema(name = "localConfigDescribe", title = "配置说明")
    private String localConfigDescribe;

    @Schema(name = "saveBootDiskPath", title = "存储根路径")
    private String saveBootDiskPath;

    @Schema(name = "unifiedPrefix", title = "预览文件统一前缀")
    private String unifiedPrefix;

    @Schema(name = "configState", title = "状态：0-禁用,1-启用,2-空间满,来源于常量字典:config_state")
    private Integer configState;

    @Schema(name = "maxSize", title = "最大使用空间")
    private Long maxSize;

    @Schema(name = "userSize", title = "已经使用空间")
    private Long userSize;

    @Schema(name = "sizeUnit", title = "空间单位来源与常量字典:size_unit,0-MB,1-GB,2-TB,默认0")
    private Integer sizeUnit;

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

    @Schema(name = "createUserId", title = "创建用户id")
    private String createUserId;

    @Schema(name = "createTenantId", title = "创建租户id")
    private String createTenantId;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间")
    private LocalDateTime createTime;

    @Schema(name = "updateUserId", title = "更新用户id")
    private String updateUserId;

    @Schema(name = "updateUserName", title = "更新用户姓名")
    private String updateUserName;

    @Schema(name = "updateTime", title = "更新时间")
    private LocalDateTime updateTime;

    @Schema(name = "delFlag", title = "删除状态:0-正常,1-已删除,默认0")
    private Integer delFlag;

}
