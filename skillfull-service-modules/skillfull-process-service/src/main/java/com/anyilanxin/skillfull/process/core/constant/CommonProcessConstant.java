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
package com.anyilanxin.skillfull.process.core.constant;

/**
* 公共常量
*
* @author zxiaozhou
* @date 2021-11-18 09:06
* @since JDK1.8
*/
public interface CommonProcessConstant {
    /** 常量前缀 */
    String CONST_PREFIX = "process_";

    String MODEL_RESOURCE_SUFFIX = ".bpmn";

    /** 流程定义id待替换key */
    String PROCESS_ID_KEY = "wait_replace_process_id";

    /** 模型流程模型 */
    String DEFAULT_BPMN_MODEL =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                    + "<bpmn:definitions xmlns:bpmn=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:dc=\"http://www.omg.org/spec/DD/20100524/DC\" id=\"Definitions_03iy6v5\" targetNamespace=\"http://bpmn.io/schema/bpmn\" exporter=\"Camunda Modeler\" exporterVersion=\"4.3.0\">\n"
                    + "  <bpmn:process id=\""
                    + PROCESS_ID_KEY
                    + "\" isExecutable=\"true\">\n"
                    + "    <bpmn:startEvent id=\"StartEvent_1\" />\n"
                    + "  </bpmn:process>\n"
                    + "  <bpmndi:BPMNDiagram id=\"BPMNDiagram_1\">\n"
                    + "    <bpmndi:BPMNPlane id=\"BPMNPlane_1\" bpmnElement=\"werwerwerwerwerwer\">\n"
                    + "      <bpmndi:BPMNShape id=\"_BPMNShape_StartEvent_2\" bpmnElement=\"StartEvent_1\">\n"
                    + "        <dc:Bounds x=\"179\" y=\"79\" width=\"36\" height=\"36\" />\n"
                    + "      </bpmndi:BPMNShape>\n"
                    + "    </bpmndi:BPMNPlane>\n"
                    + "  </bpmndi:BPMNDiagram>\n"
                    + "</bpmn:definitions>\n";
}
