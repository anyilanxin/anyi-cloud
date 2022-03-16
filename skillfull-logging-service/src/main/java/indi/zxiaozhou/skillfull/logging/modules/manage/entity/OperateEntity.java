// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.logging.modules.manage.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import indi.zxiaozhou.skillfull.coredatabase.base.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 操作日志(Operate)Entity
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-01-26 19:51:06
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
@TableName("logging_operate")
@Schema
public class OperateEntity extends BaseEntity {
    private static final long serialVersionUID = 385504254545258142L;

    @TableId
    private String operateId;

    @Schema(name = "operateType", title = " 操作类型（1查询，2添加，3修改，4删除，5其他）")
    private Integer operateType;

    @Schema(name = "logType", title = "日志类型")
    private String logType;

    @Schema(name = "logTypeDescribe", title = "日志类型说明")
    private String logTypeDescribe;

    @Schema(name = "userId", title = "操作人用户id")
    private String userId;

    @Schema(name = "userName", title = "操作人用户名称")
    private String userName;

    @Schema(name = "logCode", title = "日志编号")
    private String logCode;

    @Schema(name = "requestIp", title = "请求ip")
    private String requestIp;

    @Schema(name = "requestUrl", title = "请求路径")
    private String requestUrl;

    @Schema(name = "requestMethod", title = "请求方法")
    private String requestMethod;

    @Schema(name = "requestParam", title = "请求参数")
    private String requestParam;

    @Schema(name = "requestResult", title = "请求结果")
    private String requestResult;

    @Schema(name = "requestClientCode", title = "请求客户端编号")
    private String requestClientCode;

    @Schema(name = "requestClientName", title = "请求客户端名称")
    private String requestClientName;

    @Schema(name = "logOtherData", title = "日志其余内容")
    private String logOtherData;

    @Schema(name = "operateStatus", title = "操作状态：0-失败,1-成功")
    private Integer operateStatus;

    @Schema(name = "dataSources", title = "数据来源")
    private String dataSources;

    @Schema(name = "dataSourcesDescribe", title = "数据来源说明")
    private String dataSourcesDescribe;

    @Schema(name = "costTime", title = "耗时")
    private Long costTime;

    @Schema(name = "requestStartTime", title = "请求开始时间")
    private LocalDateTime requestStartTime;

    @Schema(name = "requestEndTime", title = "请求结束时间")
    private LocalDateTime requestEndTime;

    @Schema(name = "exceptionMessage", title = "异常消息")
    private String exceptionMessage;

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

    @Schema(name = "remark", title = "备注")
    private String remark;
}
