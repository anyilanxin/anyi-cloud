package com.anyilanxin.skillfull.system.modules.rbac.entity;

import com.anyilanxin.skillfull.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * 组织表(RbacOrg)Entity
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-02 16:39:44
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName(value = "sys_rbac_org", autoResultMap = true)
public class RbacOrgEntity extends BaseEntity {
    private static final long serialVersionUID = -26667015921374659L;

    @TableId
    private String orgId;

    /**
     * 父级组织id
     */
    private String parentId;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 英文名
     */
    private String orgNameEn;

    /**
     * 缩写
     */
    private String orgNameAbbr;

    /**
     * 排序
     */
    private Integer orgOrder;

    /**
     * 组织机构类型：1-公司,2-部门
     */
    private Integer orgType;

    /**
     * 组织编码
     */
    private String orgCode;

    /**
     * 组织编码(系统)
     */
    private String orgSysCode;

    /**
     * 组织状态：0-禁用，1-启用，默认0
     */
    private Integer orgStatus;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 传真
     */
    private String fax;

    /**
     * 地址
     */
    private String address;

    /**
     * 统一社会信用代码
     */
    private String socialCode;

    /**
     * 行政区域名称
     */
    private String areaCodeName;

    /**
     * 行政区域
     */
    private String areaCode;

    /**
     * 详细地址
     */
    private String detailAddress;

    /**
     * 经验范围
     */
    private String scopeBusiness;

    /**
     * 法人
     */
    private String legalPerson;

    /**
     * 开户姓名
     */
    private String accountsName;

    /**
     * 开户银行
     */
    private String accountsBank;

    /**
     * 银行账号
     */
    private String backCard;

    /**
     * 营业执照
     */
    private String businessLicensePicture;

    /**
     * 印章
     */
    private String sealPicture;

    /**
     * 扩展信息,json object
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private Map<String, Object> additionalInformation;

    /**
     * 备注
     */
    private String remark;

    /**
     * 唯一索引帮助字段,默认1，如果删除该值为主键
     */
    private String uniqueHelp;

    /**
     * 组织机构简称
     */
    private String orgSimpleName;
}
