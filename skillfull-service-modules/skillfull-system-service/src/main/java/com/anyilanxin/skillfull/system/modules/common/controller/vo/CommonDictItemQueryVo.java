// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.common.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 数据字典配置项表条件查询Request
 *
 * @author zxiaozhou
 * @date 2020-11-02 09:25:25
 * @since JDK11
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@Schema
public class CommonDictItemQueryVo implements Serializable {
    private static final long serialVersionUID = 478308547266973115L;

    @Schema(name = "itemId", title = "字典项id")
    private String itemId;

    @Schema(name = "dictId", title = "字典id")
    private String dictId;

    @Schema(name = "itemText", title = "字典项名称")
    private String itemText;

    @Schema(name = "itemValue", title = "字典项值")
    private String itemValue;

    @Schema(name = "dictCode", title = "字典编码")
    private String dictCode;

    @Schema(name = "sortOrder", title = "排序,越小越靠前,默认0")
    private Integer sortOrder;

    @Schema(name = "itemStatus", title = "字典项状态：1启用，0不启用，默认0")
    private Integer itemStatus;

    @Schema(name = "uniqueHelp", title = "唯一索引帮助字段,默认1，如果删除改值未主键")
    private String uniqueHelp;

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

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "delFlag", title = "删除状态:0-正常,1-已删除,默认0")
    private Integer delFlag;

}