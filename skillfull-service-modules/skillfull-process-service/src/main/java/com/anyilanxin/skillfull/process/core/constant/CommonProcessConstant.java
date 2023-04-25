package com.anyilanxin.skillfull.process.core.constant;

/**
 * 公共常量
 *
 * @author zxiaozhou
 * @date 2021-11-18 09:06
 * @since JDK1.8
 */
public interface CommonProcessConstant {
    /**
     * 常量前缀
     */
    String CONST_PREFIX = "process_";

    String MODEL_RESOURCE_SUFFIX = ".bpmn";

    /**
     * 流程定义id待替换key
     */
    String PROCESS_ID_KEY = "wait_replace_process_id";


    /**
     * 模型流程模型
     */
    String DEFAULT_BPMN_MODEL = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<bpmn:definitions xmlns:bpmn=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:dc=\"http://www.omg.org/spec/DD/20100524/DC\" id=\"Definitions_03iy6v5\" targetNamespace=\"http://bpmn.io/schema/bpmn\" exporter=\"Camunda Modeler\" exporterVersion=\"4.3.0\">\n" +
            "  <bpmn:process id=\"" + PROCESS_ID_KEY + "\" isExecutable=\"true\">\n" +
            "    <bpmn:startEvent id=\"StartEvent_1\" />\n" +
            "  </bpmn:process>\n" +
            "  <bpmndi:BPMNDiagram id=\"BPMNDiagram_1\">\n" +
            "    <bpmndi:BPMNPlane id=\"BPMNPlane_1\" bpmnElement=\"werwerwerwerwerwer\">\n" +
            "      <bpmndi:BPMNShape id=\"_BPMNShape_StartEvent_2\" bpmnElement=\"StartEvent_1\">\n" +
            "        <dc:Bounds x=\"179\" y=\"79\" width=\"36\" height=\"36\" />\n" +
            "      </bpmndi:BPMNShape>\n" +
            "    </bpmndi:BPMNPlane>\n" +
            "  </bpmndi:BPMNDiagram>\n" +
            "</bpmn:definitions>\n";

}
