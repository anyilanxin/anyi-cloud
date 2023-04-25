package com.anyilanxin.skillfull.system.modules.common.entity;

import com.anyilanxin.skillfull.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 区域表(CommonArea)Entity
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-04-09 11:59:50
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName("sys_common_area")
public class CommonAreaEntity extends BaseEntity {
    private static final long serialVersionUID = -25020041841128565L;

    @TableId
    private String areaId;

    /**
     * 所属省级id
     */
    private String provinceId;

    /**
     * 中文简称
     */
    private String simpleName;

    /**
     * 区域级别:1为省级，2为市级，3为县级
     */
    private Integer areaLevel;

    /**
     * 区域名称
     */
    private String areaName;

    /**
     * 区号
     */
    private String areaCode;

    /**
     * 所属城市id
     */
    private String cityId;

    /**
     * 上级区域id
     */
    private String parentId;

    /**
     * 本区域经度
     */
    private String lon;

    /**
     * 本区域纬度
     */
    private String lat;

    /**
     * 邮编
     */
    private String zipCode;

    /**
     * 完整名称
     */
    private String wholeName;

    /**
     * 区域名称拼音的第一个字母
     */
    private String prePinYin;

    /**
     * 名称全拼
     */
    private String pinYin;

    /**
     * 首字母简拼
     */
    private String simplePy;

    /**
     * 区县id
     */
    private String countyId;

    /**
     * 备注
     */
    private String remark;
}
