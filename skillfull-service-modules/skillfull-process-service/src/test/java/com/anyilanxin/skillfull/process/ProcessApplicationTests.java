// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process;

import com.anyilanxin.skillfull.process.modules.rbac.service.IUserService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.ExtensionElements;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.camunda.bpm.model.xml.type.ModelElementType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;
import java.util.Collection;

@SpringBootTest
public class ProcessApplicationTests {
    @Autowired
    private IUserService service;

    @Autowired
    private RepositoryService repositoryService;


    @Test
    void getUser() {
        InputStream processModel = repositoryService.getProcessModel("Process_0l1itr1:3:5f310a62-b197-11ec-a2f2-3ef1726bb0dc");
        BpmnModelInstance modelInstance = Bpmn.readModelFromStream(processModel);
        ModelElementType type = modelInstance.getModel().getType(UserTask.class);
        Collection<ModelElementInstance> taskInstances = modelInstance.getModelElementsByType(type);
        taskInstances.forEach(v -> {
            UserTask userTask = (UserTask) v;
            ExtensionElements extensionElements = userTask.getExtensionElements();
        });
    }

}


