/**
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package com.anyilanxin.skillfull.process.modules.base.entity;

import com.anyilanxin.skillfull.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 流程模型管理(DesignModel)Entity
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-04-09 11:52:03
 * @since JDK1.8
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@TableName("act_custom_design_model")
public class DesignModelEntity extends BaseEntity {
    private static final long serialVersionUID = -12178072556417422L;

    @TableId
    private String modelId;

    /**
     * bpmn模型(转换为base64存储)
     */
    private String diagramData;

    /**
     * 流程定义key,多个逗号隔开
     */
    private String processDefinitionKeys;

    /**
     * 流程定义ids,多个逗号隔开
     */
    private String processDefinitionIds;

    /**
     * 模型名称
     */
    private String diagramNames;

    /**
     * 模型类别
     */
    private String category;

    /**
     * 模型状态:0-未部署,1-已经部署,2-新版本待部署,参考常量字段:ModelStateType
     */
    private Integer modelState;

    /**
     * 是否pool模型,0-不是,1-是。默认0
     */
    private Integer havePool;

    /**
     * 部署名称
     */
    private String deploymentName;

    /**
     * 部署id
     */
    private String deploymentId;

    /**
     * 资源名称
     */
    private String resourceNames;

    /**
     * 资源ids
     */
    private String resourceIds;

    /**
     * 部署时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime deploymentTime;

    /**
     * 当前模型版本
     */
    private Integer version;

    /**
     * 备注
     */
    private String remark;

    /**
     * 唯一索引帮助字段,默认1，如果删除该值为主键
     */
    private String uniqueHelp;
}
