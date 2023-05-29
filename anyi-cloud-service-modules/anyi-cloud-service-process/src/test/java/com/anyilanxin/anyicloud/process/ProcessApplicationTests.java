

package com.anyilanxin.anyicloud.process;

import com.anyilanxin.anyicloud.process.modules.rbac.service.IUserService;

import java.io.InputStream;
import java.util.Collection;

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
