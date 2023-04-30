流程服务
===============

## 一、camunda基础资料

1. 引擎自带变量信息

```
nrOfInstances:实例的数目
nrOfCompletedInstances:完成实例的数目
nrOfActiveInstances:未完成实例的数目
loopCounter:循环计数器，办理人在列表中的索引。
参考地址：https://docs.camunda.org/manual/7.16/user-guide/process-engine/process-instance-migration/

```

2. 引擎上下文变量

```
多变的	             Java类型	            语境
execution	         DelegateExecution	    在 BPMN 执行上下文中可用，如服务任务、执行侦听器或序列流。
task	             DelegateTask	        在任务上下文中可用，如任务侦听器。
externalTask	     ExternalTask	        在外部任务上下文活动期间可用（例如在camunda:errorEventDefinition表达式中）。
caseExecution	     DelegateCaseExecution	在 CMMN 执行上下文中可用。
authenticatedUserId	 String	                当前经过身份验证的用户的 ID。仅当通过 IdentityService. 否则返回null。
```

3. 内部上下文函数

```
功能	                  返回类型	            描述
currentUser()	      String	            返回当前已通过身份验证的用户的用户 ID，否则当前null没有用户通过身份验证。
currentUserGroups()	  List of Strings	    返回当前经过身份验证的用户的组 ID 列表，或者当前null没有用户被授权。
now()	              Date	以 Java Date    对象的形式返回当前日期。
dateTime()	          DateTime	            返回当前日期的 Joda-Time DateTime 对象。 有关所有可用功能，请参阅 Joda-Time文档。(例如：${dateTime().plusDays(3).toDate()})
```

5. 表达式以及变量相关文档：https://docs.camunda.org/manual/latest/user-guide/process-engine/expression-language/
6. 监听器相关文档：https://docs.camunda.org/manual/7.13/user-guide/process-engine/delegation-code/#task-listener-event-lifecycle
7. 按钮说明
