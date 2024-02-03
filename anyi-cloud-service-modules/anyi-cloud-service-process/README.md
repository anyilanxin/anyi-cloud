流程服务
===============
一、camunda建模器信息
http://127.0.0.1:8088/process/engine-rest
扩展说明:如果没有${}同时没有指定则为自定义变量，否则为系统变量或者非变量 bpmn扩展属性:，1-指定值，2-系统变量,3-自定义变量

1. initiatorType:
2. candidateStarterGroupsType:
3. candidateStarterUsersType:

4. assigneeType:
5. candidateUsersType:
6. candidateGroupsType:

二、针对会签引擎会自动生成变量</br>

1. 引擎自带变量信息

```
nrOfInstances:实例的数目
nrOfCompletedInstances:完成实例的数目
nrOfActiveInstances:未完成实例的数目
loopCounter:循环计数器，办理人在列表中的索引。
参考地址：https://docs.camunda.org/manual/7.19/reference/bpmn20/tasks/task-markers/

```

2. 流程流转自定义变量信息

```
nrOfApprovalInstances：会签同意实例数
nrOfNotApprovalInstances：会签不同意实例数
nrOfStatusInstances: 审批任务状态：待审批，审批中，作废，拒绝，通过，归还，转办，驳回，委派，
nrOfStatusProcessInstances: 流程实例状态：0-待审核,5-审核中,10-审核不通过,15-审核通过
```

3. 引擎上下文变量

```
多变的	             Java类型	            语境
execution	         DelegateExecution	    在 BPMN 执行上下文中可用，如服务任务、执行侦听器或序列流。
task	             DelegateTask	        在任务上下文中可用，如任务侦听器。
externalTask	     ExternalTask	        在外部任务上下文活动期间可用（例如在camunda:errorEventDefinition表达式中）。
caseExecution	     DelegateCaseExecution	在 CMMN 执行上下文中可用。
authenticatedUserId	 String	                当前经过身份验证的用户的 ID。仅当通过 IdentityService. 否则返回null。
```

4. 内部上下文函数

```
功能	                  返回类型	            描述
currentUser()	      String	            返回当前已通过身份验证的用户的用户 ID，否则当前null没有用户通过身份验证。
currentUserGroups()	  List of Strings	    返回当前经过身份验证的用户的组 ID 列表，或者当前null没有用户被授权。
now()	              Date	以 Java Date    对象的形式返回当前日期。
dateTime()	          DateTime	            返回当前日期的 Joda-Time DateTime 对象。 有关所有可用功能，请参阅 Joda-Time文档。(例如：${dateTime().plusDays(3).toDate()})
```

5. 表达式以及变量相关文档：https://docs.camunda.org/manual/latest/user-guide/process-engine/expression-language/
6.

监听器相关文档：https://docs.camunda.org/manual/7.13/user-guide/process-engine/delegation-code/#task-listener-event-lifecycle

7. 按钮说明

```
办理弹框------->通过、不通过，打回，拒绝(可选抄送人)
驳回弹框------>需要选择驳回节点，并可选择处理人(可选抄送人)
转办弹框------->需要选择转办处理人(可选抄送人)
归还-------->主页面，按钮
签收---------主页面，分页
委托弹框---------需要选择处理人(可选抄送人)



撤回--------->已办任务中



作废---流程发起人
```
