/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
package com.anyilanxin.skillfull.process.modules.base.entity;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

import com.anyilanxin.skillfull.database.datasource.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
* 流程模型历史(DesignModelHistory)Entity
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
@TableName("act_custom_design_model_history")
public class DesignModelHistoryEntity extends BaseEntity {
    private static final long serialVersionUID = -68433941014688158L;

    @TableId private String historyModelId;

    /** 模型id */
    private String modelId;

    /** 流程定义key,多个逗号隔开 */
    private String processDefinitionKeys;

    /** 流程定义ids,多个逗号隔开 */
    private String processDefinitionIds;

    /** bpmn模型(转换为base64存储) */
    private String diagramData;

    /** 部署名称 */
    private String deploymentName;

    /** 模型名称 */
    private String diagramNames;

    /** 部署id */
    private String deploymentId;

    /** 资源名称 */
    private String resourceNames;

    /** 资源ids */
    private String resourceIds;

    /** 是否pool模型,0-不是,1-是。默认0 */
    private Integer havePool;

    /** 部署时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime deploymentTime;

    /** 模型类别 */
    private String category;

    /** 当前模型版本 */
    private Integer version;

    /** 备注 */
    private String remark;
}
