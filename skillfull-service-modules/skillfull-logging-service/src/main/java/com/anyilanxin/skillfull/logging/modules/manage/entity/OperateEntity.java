package com.anyilanxin.skillfull.logging.modules.manage.entity;

import com.anyilanxin.skillfull.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 操作日志(Operate)Entity
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-08-13 10:24:41
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName("logging_operate")
public class OperateEntity extends BaseEntity {
    private static final long serialVersionUID = -21170709956720345L;

    @TableId
    private String operateId;

    /**
     * 操作类型（1查询，2添加，3修改，4删除，5其他）具体与常量字典OperateType一致
     */
    private Integer operateType;

    /**
     * 请求contentType
     */
    private String contentType;

    /**
     * 操作人用户id
     */
    private String userId;

    /**
     * 操作人用户名称
     */
    private String userName;

    /**
     * 请求客户端编号
     */
    private String requestClientCode;

    /**
     * 请求客户端名称
     */
    private String requestClientName;

    /**
     * 日志编号
     */
    private String logCode;

    /**
     * 请求ip
     */
    private String requestIp;

    /**
     * 目标服务
     */
    private String targetServiceCode;

    /**
     * 目标地址
     */
    private String targetUrl;

    /**
     * 请求路径
     */
    private String requestUrl;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 请求结果
     */
    private String requestResult;

    /**
     * 日志其余内容
     */
    private String logOtherData;

    /**
     * 异常消息
     */
    private String exceptionMessage;

    /**
     * 操作状态：0-失败,1-成功
     */
    private Integer operateStatus;

    /**
     * 数据来源
     */
    private String dataSources;

    /**
     * 数据来源说明
     */
    private String dataSourcesDescribe;

    /**
     * 耗时
     */
    private Long costTime;

    /**
     * 请求开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime requestStartTime;

    /**
     * 请求结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime requestEndTime;

    /**
     * 备注
     */
    private String remark;
}
