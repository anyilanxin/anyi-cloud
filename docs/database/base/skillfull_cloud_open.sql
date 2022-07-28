/*
 Navicat Premium Data Transfer

 Source Server         : MySQL_localhost_8
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : skillfull_cloud_open

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 28/07/2022 09:13:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for act_custom_design_model
-- ----------------------------
DROP TABLE IF EXISTS `act_custom_design_model`;
CREATE TABLE `act_custom_design_model`
(
    `model_id`                varchar(32)  NOT NULL COMMENT '模型id',
    `diagram_data`            longtext COMMENT 'bpmn模型(转换为base64存储)',
    `process_definition_keys` varchar(512)                                           DEFAULT NULL COMMENT '流程定义key,如果出现池会有多个，逗号隔开',
    `process_definition_ids`  varchar(512)                                           DEFAULT NULL COMMENT '流程定义ids,如果出现池会有多个，逗号隔开',
    `diagram_names`           varchar(512)                                           DEFAULT NULL COMMENT '模型名称,如果出现池会有多个，逗号隔开',
    `category`                varchar(256) NOT NULL COMMENT '模型类别',
    `model_state`             tinyint(1)   NOT NULL                                  DEFAULT '0' COMMENT '模型状态:0-未部署,1-已经部署,2-新版本待部署,参考常量字段:ModelStateType',
    `have_pool`               tinyint(1)   NOT NULL                                  DEFAULT '0' COMMENT '是否pool模型,0-不是,1-是。默认0',
    `deployment_name`         varchar(256)                                           DEFAULT NULL COMMENT '部署名称',
    `deployment_id`           varchar(36)                                            DEFAULT NULL COMMENT '部署id',
    `resource_names`          varchar(256)                                           DEFAULT NULL COMMENT '资源名称',
    `resource_ids`            varchar(36)                                            DEFAULT NULL COMMENT '资源ids',
    `deployment_time`         datetime                                               DEFAULT NULL COMMENT '部署时间',
    `version`                 int          NOT NULL                                  DEFAULT '1' COMMENT '当前模型版本',
    `remark`                  varchar(255)                                           DEFAULT NULL COMMENT '备注',
    `unique_help`             varchar(32)  NOT NULL                                  DEFAULT '1' COMMENT '唯一索引帮助字段,默认1，如果删除该值为主键',
    `create_area_code`        varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code`    varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建系统编码',
    `create_tenant_id`        varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建租户id',
    `create_user_id`          varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`        varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户姓名',
    `create_time`             datetime     NOT NULL                                  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`          varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`        varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`             datetime                                               DEFAULT NULL COMMENT '更新时间',
    `del_flag`                tinyint(1)   NOT NULL                                  DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`model_id`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_model_state` (`model_state`),
    KEY `Index_model_name` (`diagram_names`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`),
    KEY `Index_category` (`category`),
    KEY `Index_have_pool` (`have_pool`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='流程模型管理';

-- ----------------------------
-- Table structure for act_custom_design_model_history
-- ----------------------------
DROP TABLE IF EXISTS `act_custom_design_model_history`;
CREATE TABLE `act_custom_design_model_history`
(
    `history_model_id`        varchar(32)  NOT NULL COMMENT '历史模型id',
    `model_id`                varchar(32)  NOT NULL COMMENT '模型id',
    `process_definition_keys` varchar(512)                                           DEFAULT NULL COMMENT '流程定义key,如果出现池会有多个，逗号隔开',
    `process_definition_ids`  varchar(512)                                           DEFAULT NULL COMMENT '流程定义ids,如果出现池会有多个，逗号隔开',
    `diagram_data`            longtext COMMENT 'bpmn模型(转换为base64存储)',
    `deployment_name`         varchar(256) NOT NULL COMMENT '部署名称',
    `diagram_names`           varchar(256)                                           DEFAULT NULL COMMENT '模型名称',
    `deployment_id`           varchar(36)  NOT NULL COMMENT '部署id',
    `resource_names`          varchar(256) NOT NULL COMMENT '资源名称',
    `resource_ids`            varchar(36)  NOT NULL COMMENT '资源ids',
    `have_pool`               tinyint(1)   NOT NULL                                  DEFAULT '0' COMMENT '是否pool模型,0-不是,1-是。默认0',
    `deployment_time`         datetime                                               DEFAULT NULL COMMENT '部署时间',
    `category`                varchar(256) NOT NULL COMMENT '模型类别',
    `version`                 int          NOT NULL                                  DEFAULT '1' COMMENT '当前模型版本',
    `remark`                  varchar(255)                                           DEFAULT NULL COMMENT '备注',
    `create_area_code`        varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code`    varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建系统编码',
    `create_tenant_id`        varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建租户id',
    `create_user_id`          varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`        varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户姓名',
    `create_time`             datetime     NOT NULL                                  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`          varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`        varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`             datetime                                               DEFAULT NULL COMMENT '更新时间',
    `del_flag`                tinyint(1)   NOT NULL                                  DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`history_model_id`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_model_name` (`diagram_names`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`),
    KEY `Index_category` (`category`),
    KEY `Index_deployment_name` (`deployment_name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='流程模型历史';

-- ----------------------------
-- Table structure for act_custom_process_category
-- ----------------------------
DROP TABLE IF EXISTS `act_custom_process_category`;
CREATE TABLE `act_custom_process_category`
(
    `category_id`          varchar(32)  NOT NULL COMMENT '类别id',
    `category_code`        varchar(128) NOT NULL COMMENT '类别编码(唯一)',
    `category_name`        varchar(128) NOT NULL COMMENT '类别名称',
    `category_state`       tinyint(1)   NOT NULL                                  DEFAULT '0' COMMENT '类别状态:0-禁用,1-启用。默认0',
    `category_describe`    varchar(256)                                           DEFAULT NULL COMMENT '类别描述',
    `pictures`             varchar(4000)                                          DEFAULT NULL COMMENT '类别logo',
    `remark`               varchar(255)                                           DEFAULT NULL COMMENT '备注',
    `create_area_code`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建系统编码',
    `create_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户姓名',
    `create_tenant_id`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建租户id',
    `create_time`          datetime     NOT NULL                                  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`          datetime                                               DEFAULT NULL COMMENT '更新时间',
    `del_flag`             tinyint(1)   NOT NULL                                  DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`category_id`),
    UNIQUE KEY `Unique_category_code` (`category_code`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`),
    KEY `Index_category_state` (`category_state`),
    KEY `Index_category_name` (`category_name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='流程类别';

-- ----------------------------
-- Table structure for logging_auth_data
-- ----------------------------
DROP TABLE IF EXISTS `logging_auth_data`;
CREATE TABLE `logging_auth_data`
(
    `auth_log_id`          varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '授权日志id',
    `log_code`             varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '日志编号',
    `request_ip`           varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci         DEFAULT NULL COMMENT '请求ip',
    `auth_type`            varchar(32)                                            NOT NULL COMMENT '授权类型，具体参考授权服务中AuthType常量字典',
    `auth_type_describe`   varchar(256)                                           NOT NULL COMMENT '授权类型描述',
    `auth_user_id`         varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '授权用户id',
    `auth_user_name`       varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci         DEFAULT NULL COMMENT '授权用户名称',
    `auth_client_code`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '授权客户端编号',
    `auth_client_name`     varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci         DEFAULT NULL COMMENT '授权客户端名称',
    `auth_status`          smallint                                                        DEFAULT NULL COMMENT '授权状态：0-失败,1-成功',
    `log_data`             longtext COMMENT '日志内容',
    `log_other_data`       longtext COMMENT '日志其余内容',
    `exception_message`    longtext COMMENT '异常消息',
    `cost_time`            bigint                                                          DEFAULT NULL COMMENT '耗时',
    `request_start_time`   datetime                                                        DEFAULT NULL COMMENT '请求开始时间',
    `request_end_time`     datetime                                                        DEFAULT NULL COMMENT '请求结束时间',
    `create_area_code`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建系统编码',
    `create_tenant_id`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建租户id',
    `create_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建用户姓名',
    `create_time`          datetime                                                        DEFAULT NULL COMMENT '创建时间',
    `update_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`          datetime                                                        DEFAULT NULL COMMENT '更新时间',
    `remark`               varchar(255)                                                    DEFAULT NULL COMMENT '备注',
    `del_flag`             tinyint(1)                                             NOT NULL DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`auth_log_id`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`),
    KEY `Index_login_user_name` (`auth_user_name`),
    KEY `Index_request_start_time` (`request_start_time`),
    KEY `Index_request_end_time` (`request_end_time`),
    KEY `Index_log_code` (`log_code`),
    KEY `Index_request_ip` (`request_ip`),
    KEY `Index_cost_time` (`cost_time`),
    KEY `Index_auth_type` (`auth_type`),
    KEY `Index_auth_client_code` (`auth_client_code`),
    KEY `Index_auth_status` (`auth_status`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='登录日志';

-- ----------------------------
-- Table structure for logging_operate
-- ----------------------------
DROP TABLE IF EXISTS `logging_operate`;
CREATE TABLE `logging_operate`
(
    `operate_id`            varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作日志id',
    `operate_type`          smallint                                                        DEFAULT NULL COMMENT ' 操作类型（1查询，2添加，3修改，4删除，5其他）',
    `log_type`              varchar(32)                                                     DEFAULT NULL COMMENT '日志类型',
    `log_type_describe`     varchar(256)                                                    DEFAULT NULL COMMENT '日志类型说明',
    `user_id`               varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '操作人用户id',
    `user_name`             varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci         DEFAULT NULL COMMENT '操作人用户名称',
    `request_client_code`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '请求客户端编号',
    `request_client_name`   varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci         DEFAULT NULL COMMENT '请求客户端名称',
    `log_code`              varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '日志编号',
    `request_ip`            varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci         DEFAULT NULL COMMENT '请求ip',
    `request_url`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci         DEFAULT NULL COMMENT '请求路径',
    `request_method`        varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '请求方法',
    `request_param`         longtext COMMENT '请求参数',
    `request_result`        longtext COMMENT '请求结果',
    `log_other_data`        longtext COMMENT '日志其余内容',
    `exception_message`     longtext COMMENT '异常消息',
    `operate_status`        smallint                                                        DEFAULT NULL COMMENT '操作状态：0-失败,1-成功',
    `data_sources`          varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci         DEFAULT NULL COMMENT '数据来源',
    `data_sources_describe` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci         DEFAULT NULL COMMENT '数据来源说明',
    `cost_time`             bigint                                                          DEFAULT NULL COMMENT '耗时',
    `request_start_time`    datetime                                                        DEFAULT NULL COMMENT '请求开始时间',
    `request_end_time`      datetime                                                        DEFAULT NULL COMMENT '请求结束时间',
    `create_area_code`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`    varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建系统编码',
    `create_user_id`        varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建用户id',
    `create_tenant_id`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建租户id',
    `create_user_name`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建用户姓名',
    `create_time`           datetime                                                        DEFAULT NULL COMMENT '创建时间',
    `update_user_id`        varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`           datetime                                                        DEFAULT NULL COMMENT '更新时间',
    `remark`                varchar(255)                                                    DEFAULT NULL COMMENT '备注',
    `del_flag`              tinyint(1)                                             NOT NULL DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`operate_id`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`),
    KEY `Index_request_ip` (`request_ip`),
    KEY `Index_request_url` (`request_url`),
    KEY `Index_data_sources` (`data_sources`),
    KEY `Index_user_name` (`user_name`),
    KEY `Index_log_code` (`log_code`),
    KEY `Index_request_start_time` (`request_start_time`),
    KEY `Index_request_end_time` (`request_end_time`),
    KEY `Index_cost_time` (`cost_time`),
    KEY `Index_request_client_code` (`request_client_code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='操作日志';

-- ----------------------------
-- Table structure for msg_announcement_record
-- ----------------------------
DROP TABLE IF EXISTS `msg_announcement_record`;
CREATE TABLE `msg_announcement_record`
(
    `annt_read_id`         varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '通知公告阅读记录id',
    `annt_id`              varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '通知公告id',
    `read_status`          tinyint(1)                                                      DEFAULT '0' COMMENT '阅读状态：0-未读，1-已经。默认0',
    `read_time`            datetime                                                        DEFAULT NULL COMMENT '阅读时间',
    `create_area_code`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建系统编码',
    `create_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建用户id',
    `create_tenant_id`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建租户id',
    `create_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建用户姓名',
    `create_time`          datetime                                               NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`          datetime                                                        DEFAULT NULL COMMENT '更新时间',
    `remark`               varchar(255)                                                    DEFAULT NULL COMMENT '备注',
    `del_flag`             tinyint(1)                                             NOT NULL DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`annt_read_id`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`),
    KEY `Index_annt_id` (`annt_id`),
    KEY `Index_read_status` (`read_status`),
    KEY `Index_read_time` (`read_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='系统通知公告阅读记录';

-- ----------------------------
-- Table structure for msg_chat_group
-- ----------------------------
DROP TABLE IF EXISTS `msg_chat_group`;
CREATE TABLE `msg_chat_group`
(
    `chat_group_id`        varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '群id',
    `enable_history_msg`   tinyint(1)                                             NOT NULL DEFAULT '0' COMMENT '新成员查看历史消息：0-不能,1-能。默认0',
    `group_name`           varchar(256)                                           NOT NULL COMMENT '群名称',
    `group_no`             varchar(32)                                            NOT NULL COMMENT '群号',
    `describe`             varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci         DEFAULT NULL COMMENT '群描述',
    `group_img`            varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci        DEFAULT NULL COMMENT '群图标',
    `group_user_num`       int                                                    NOT NULL DEFAULT '1' COMMENT '群成员数量',
    `manager_user_id`      varchar(32)                                            NOT NULL COMMENT '群主id',
    `create_area_code`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建系统编码',
    `create_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建用户姓名',
    `create_tenant_id`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建租户id',
    `create_time`          datetime                                               NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`          datetime                                                        DEFAULT NULL COMMENT '更新时间',
    `remark`               varchar(255)                                                    DEFAULT NULL COMMENT '备注',
    `del_flag`             tinyint(1)                                             NOT NULL DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`chat_group_id`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`),
    KEY `Index_template_type` (`manager_user_id`),
    KEY `Index_group_name` (`group_name`),
    KEY `Unique_group_no` (`group_no`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='消息群';

-- ----------------------------
-- Table structure for msg_chat_group_user
-- ----------------------------
DROP TABLE IF EXISTS `msg_chat_group_user`;
CREATE TABLE `msg_chat_group_user`
(
    `group_user_id`        varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '群成员id',
    `chat_group_id`        varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '群id',
    `user_id`              varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
    `administrator`        smallint                                               NOT NULL DEFAULT '0' COMMENT '是否管理员:0-不是，1-是。默认0',
    `manager`              smallint                                               NOT NULL DEFAULT '0' COMMENT '是否群主：0-不是，1-是，默认0',
    `join_time`            datetime                                               NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
    `join_success_time`    datetime                                                        DEFAULT NULL COMMENT '加入成功',
    `create_area_code`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建系统编码',
    `create_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建用户姓名',
    `create_tenant_id`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建租户id',
    `create_time`          datetime                                               NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`          datetime                                                        DEFAULT NULL COMMENT '更新时间',
    `remark`               varchar(255)                                                    DEFAULT NULL COMMENT '备注',
    `del_flag`             tinyint(1)                                             NOT NULL DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`group_user_id`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`),
    KEY `Index_chat_group_id` (`chat_group_id`),
    KEY `Index_user_id` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='群成员信息';

-- ----------------------------
-- Table structure for msg_chat_message_info
-- ----------------------------
DROP TABLE IF EXISTS `msg_chat_message_info`;
CREATE TABLE `msg_chat_message_info`
(
    `chat_message_id`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '聊天消息id',
    `chat_business_id`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '聊天业务id,单聊时为好友会话id,群聊时为群id',
    `at_user_id`           varchar(32)                                                     DEFAULT NULL COMMENT 'at用户id，群聊时存在',
    `at_type`              smallint                                                        DEFAULT NULL COMMENT 'at类型:1-指定人,2-所有，群聊时存在',
    `msg_type`             smallint                                               NOT NULL COMMENT '消息类型：1-文字，2-图片，3-文件，4-表情',
    `chat_type`            smallint                                               NOT NULL COMMENT '聊天类型:1-单聊，2-群聊',
    `send_msg_content`     text                                                   NOT NULL COMMENT '消息内容',
    `send_user_id`         varchar(32)                                            NOT NULL COMMENT '消息发送人id',
    `msg_send_time`        datetime                                               NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '消息发送时间',
    `create_area_code`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建系统编码',
    `create_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建用户姓名',
    `create_tenant_id`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建租户id',
    `create_time`          datetime                                               NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`          datetime                                                        DEFAULT NULL COMMENT '更新时间',
    `remark`               varchar(255)                                                    DEFAULT NULL COMMENT '备注',
    `del_flag`             tinyint(1)                                             NOT NULL DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`chat_message_id`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`),
    KEY `Index_chat_type` (`chat_type`),
    KEY `Index_msg_send_time` (`msg_send_time`),
    KEY `Index_chat_business_id` (`chat_business_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='聊天消息';

-- ----------------------------
-- Table structure for msg_chat_msg_session_associated
-- ----------------------------
DROP TABLE IF EXISTS `msg_chat_msg_session_associated`;
CREATE TABLE `msg_chat_msg_session_associated`
(
    `correlation_msg_session_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消息会话关联id',
    `chat_message_id`            varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '聊天消息id',
    `chat_business_id`           varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '聊天业务id,单聊时为好友会话id,群聊时为群id',
    `chat_type`                  smallint                                               NOT NULL COMMENT '聊天类型:1-单聊，2-群聊',
    `read_status`                tinyint(1)                                             NOT NULL DEFAULT '0' COMMENT '阅读状态：0-未读，1-已经。默认0',
    `read_time`                  datetime                                                        DEFAULT NULL COMMENT '阅读时间',
    `my_send`                    tinyint(1)                                                      DEFAULT '0' COMMENT '是否我发送的:0-不是，1-是.默认0',
    `create_area_code`           varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`        varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`         varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建系统编码',
    `create_user_id`             varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`           varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建用户姓名',
    `create_tenant_id`           varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建租户id',
    `create_time`                datetime                                               NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`             varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`           varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`                datetime                                                        DEFAULT NULL COMMENT '更新时间',
    `remark`                     varchar(255)                                                    DEFAULT NULL COMMENT '备注',
    `del_flag`                   tinyint(1)                                             NOT NULL DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`correlation_msg_session_id`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`),
    KEY `Index_read_status` (`read_status`),
    KEY `Index_chat_message_id` (`chat_message_id`),
    KEY `Index_chat_business_id` (`chat_business_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='聊天会话关系表';

-- ----------------------------
-- Table structure for msg_chat_session_info
-- ----------------------------
DROP TABLE IF EXISTS `msg_chat_session_info`;
CREATE TABLE `msg_chat_session_info`
(
    `session_info_id`          varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会话id',
    `chat_type`                smallint                                               NOT NULL COMMENT '聊天类型:1-单聊，2-群聊',
    `chat_business_id`         varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '聊天业务id,单聊时为好友会话id,群聊时为群id',
    `receiver_id`              varchar(32)                                            NOT NULL COMMENT '接收id,单例为用户id,聊为群id',
    `user_id`                  varchar(32)                                            NOT NULL COMMENT '用户id',
    `no_read_msg_num`          int                                                    NOT NULL DEFAULT '0' COMMENT '未读消息数量',
    `current_receive_msg_time` datetime                                               NOT NULL COMMENT '最近接收消息时间',
    `read_msg_num`             int                                                    NOT NULL DEFAULT '0' COMMENT '已读消息数量',
    `create_area_code`         varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建系统编码',
    `create_user_id`           varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`         varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建用户姓名',
    `create_tenant_id`         varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建租户id',
    `create_time`              datetime                                               NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`           varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`         varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`              datetime                                                        DEFAULT NULL COMMENT '更新时间',
    `remark`                   varchar(255)                                                    DEFAULT NULL COMMENT '备注',
    `del_flag`                 tinyint(1)                                             NOT NULL DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`session_info_id`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`),
    KEY `Index_chat_type` (`chat_type`),
    KEY `Index_chat_business_id` (`chat_business_id`),
    KEY `Index_current_receive_msg_time` (`current_receive_msg_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='聊天会话信息';

-- ----------------------------
-- Table structure for msg_friend
-- ----------------------------
DROP TABLE IF EXISTS `msg_friend`;
CREATE TABLE `msg_friend`
(
    `friend_id`            varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '模板id',
    `user_id`              varchar(32)                                            NOT NULL COMMENT '用户id',
    `chat_session_id`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '聊天会话id,好友列表公用会话id',
    `friend_status`        smallint                                               NOT NULL COMMENT '申请状态：0-申请待通过，1-通过',
    `apply_time`           datetime                                               NOT NULL COMMENT '申请时间',
    `success_time`         datetime                                                        DEFAULT NULL COMMENT '申请通过时间',
    `friend_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '好友用户id',
    `create_area_code`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建系统编码',
    `create_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建用户姓名',
    `create_tenant_id`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建租户id',
    `create_time`          datetime                                               NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`          datetime                                                        DEFAULT NULL COMMENT '更新时间',
    `remark`               varchar(255)                                                    DEFAULT NULL COMMENT '备注',
    `del_flag`             tinyint(1)                                             NOT NULL DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`friend_id`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`),
    KEY `Index_user_id` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='好友列表';

-- ----------------------------
-- Table structure for msg_manage_announcement
-- ----------------------------
DROP TABLE IF EXISTS `msg_manage_announcement`;
CREATE TABLE `msg_manage_announcement`
(
    `annt_id`              varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '通知公告id',
    `title`                varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
    `msg_abstract`         varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '摘要',
    `msg_content`          text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '内容',
    `sender_user_name`     varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '发布人姓名',
    `sender_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '发布人',
    `announcement_type`    smallint                                                NOT NULL COMMENT '通知公告类型：1-系统公告，2-待办事项通知',
    `receive_user_id`      varchar(32)                                                      DEFAULT NULL COMMENT '接收用户id',
    `receive_area_code`    varchar(32)                                                      DEFAULT NULL COMMENT '接收区域编码',
    `receive_org_id`       varchar(32)                                                      DEFAULT NULL COMMENT '接收组织机构id',
    `receive_org_code`     varchar(64)                                                      DEFAULT NULL COMMENT '接收组织机构编码',
    `send_type`            smallint                                                NOT NULL COMMENT '发布方式：0-手动,1-自动',
    `auto_send_time`       datetime                                                         DEFAULT NULL COMMENT '自动发布时间',
    `send_time`            datetime                                                         DEFAULT NULL COMMENT '发布时间',
    `cancel_time`          datetime                                                         DEFAULT NULL COMMENT '撤销时间',
    `send_status`          smallint                                                NOT NULL DEFAULT '0' COMMENT '发布状态：0未发布，1已发布，2已撤销，默认0',
    `page_url`             varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci         DEFAULT NULL COMMENT '页面url',
    `create_area_code`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建系统编码',
    `create_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建用户id',
    `create_tenant_id`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建租户id',
    `create_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建用户姓名',
    `create_time`          datetime                                                NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`          datetime                                                         DEFAULT NULL COMMENT '更新时间',
    `remark`               varchar(255)                                                     DEFAULT NULL COMMENT '备注',
    `del_flag`             tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`annt_id`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_send_type` (`send_type`),
    KEY `Index_create_tenant_id` (`create_tenant_id`),
    KEY `Index_receive_user_id` (`receive_user_id`),
    KEY `Index_receive_area_code` (`receive_area_code`),
    KEY `Index_receive_org_id` (`receive_org_id`),
    KEY `Index_receive_org_code` (`receive_org_code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='系统通告公告管理';

-- ----------------------------
-- Table structure for msg_manage_send_record
-- ----------------------------
DROP TABLE IF EXISTS `msg_manage_send_record`;
CREATE TABLE `msg_manage_send_record`
(
    `send_record_id`         varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发送记录id',
    `template_id`            varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '模板id',
    `template_third_code`    varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '三方系统模板编码',
    `template_code`          varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '模板code',
    `send_batch_no`          varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发送批次号',
    `business_id`            varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '业务id',
    `template_original_data` json                                                            DEFAULT NULL COMMENT '模板原始数据,json',
    `send_type`              smallint                                                        DEFAULT NULL COMMENT '发送方式:1-微信模板,2-短信,3-邮件',
    `send_receiver`          varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '接收人',
    `send_content`           text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '发送内容',
    `send_time`              datetime                                                        DEFAULT NULL COMMENT '发送时间',
    `send_status`            varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT '0' COMMENT '发送状态:0-失败，1-成功，2-多次发送后失败。默认0',
    `send_results`           text COMMENT '发送失败原因，json数组',
    `send_num`               int                                                    NOT NULL DEFAULT '1' COMMENT '已经发送次数，默认1',
    `send_max_num`           int                                                    NOT NULL DEFAULT '1' COMMENT '最大发送次数，默认1',
    `create_area_code`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`    varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建系统编码',
    `create_user_id`         varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建用户姓名',
    `create_tenant_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建租户id',
    `create_time`            datetime                                               NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`         varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`            datetime                                                        DEFAULT NULL COMMENT '更新时间',
    `remark`                 varchar(255)                                                    DEFAULT NULL COMMENT '备注',
    `del_flag`               tinyint(1)                                             NOT NULL DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`send_record_id`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_template_code` (`template_code`),
    KEY `Index_template_id` (`template_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='消息发送记录表';

-- ----------------------------
-- Table structure for msg_manage_template
-- ----------------------------
DROP TABLE IF EXISTS `msg_manage_template`;
CREATE TABLE `msg_manage_template`
(
    `template_id`               varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '模板id',
    `template_name`             varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '模板名称',
    `template_status`           smallint                                               NOT NULL DEFAULT '0' COMMENT '模板状态:0-禁用,1-启用',
    `template_code`             varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '模板code',
    `template_third_code`       varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '三方系统模板编码',
    `send_max_num`              int                                                    NOT NULL DEFAULT '0' COMMENT '最大重试次数,默认0-不重试',
    `template_type`             smallint                                               NOT NULL COMMENT '模板类型:1-微信模板,2-短信,3-邮件,来源于常量字典：MsgTemplateType',
    `limit_send`                tinyint(1)                                             NOT NULL DEFAULT '0' COMMENT '是否限制发送次数：0-不限制,1-限制。默认0',
    `max_send_num`              int                                                             DEFAULT '10' COMMENT '每天允许最大发送次数,当启用限制次数时有效，默认10',
    `template_content`          text COMMENT '模板内容',
    `template_content_describe` text COMMENT '模板字段说明信息',
    `unique_help`               varchar(32)                                            NOT NULL DEFAULT '1' COMMENT '唯一索引帮助字段,默认1，如果删除该值为主键',
    `create_area_code`          varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`        varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建系统编码',
    `create_user_id`            varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`          varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建用户姓名',
    `create_tenant_id`          varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建租户id',
    `create_time`               datetime                                               NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`            varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`          varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`               datetime                                                        DEFAULT NULL COMMENT '更新时间',
    `remark`                    varchar(255)                                                    DEFAULT NULL COMMENT '备注',
    `del_flag`                  tinyint(1)                                             NOT NULL DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`template_id`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`),
    KEY `Index_template_type` (`template_type`),
    KEY `Unique_template_code` (`template_code`, `unique_help`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='消息模板';

-- ----------------------------
-- Table structure for seata_undo_log
-- ----------------------------
DROP TABLE IF EXISTS `seata_undo_log`;
CREATE TABLE `seata_undo_log`
(
    `id`            bigint       NOT NULL AUTO_INCREMENT,
    `branch_id`     bigint       NOT NULL,
    `xid`           varchar(100) NOT NULL,
    `context`       varchar(128) NOT NULL,
    `rollback_info` longblob     NOT NULL,
    `log_status`    int          NOT NULL,
    `log_created`   datetime     NOT NULL,
    `log_modified`  datetime     NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 78
  DEFAULT CHARSET = utf8mb3;

-- ----------------------------
-- Table structure for storage_info_file
-- ----------------------------
DROP TABLE IF EXISTS `storage_info_file`;
CREATE TABLE `storage_info_file`
(
    `file_id`              varchar(32)  NOT NULL COMMENT '文件id',
    `file_original_name`   varchar(256)                                           DEFAULT NULL COMMENT '原始文件名(不包括扩展名)',
    `file_type`            varchar(32)                                            DEFAULT NULL COMMENT '文件类型',
    `file_dir_prefix`      varchar(256)                                           DEFAULT NULL COMMENT '存放文件夹名称',
    `file_storage_type`    smallint     NOT NULL COMMENT '文件引擎类型：1-本地，2-ali oss,3-minio',
    `content_type`         varchar(256)                                           DEFAULT NULL COMMENT '文件流类型',
    `file_size`            varchar(256) NOT NULL COMMENT '文件大小',
    `file_size_detail`     bigint       NOT NULL                                  DEFAULT '0' COMMENT '文件详细大小',
    `file_md5`             varchar(256)                                           DEFAULT NULL COMMENT '文件md5值',
    `file_relative_path`   varchar(512) NOT NULL COMMENT '文件相对路径',
    `endpoint`             varchar(128) NOT NULL                                  DEFAULT '0' COMMENT 'endpoint',
    `file_host`            varchar(512) NOT NULL                                  DEFAULT '0' COMMENT '文件域名(主要用于非本地存储使用)',
    `remark`               varchar(255)                                           DEFAULT NULL COMMENT '备注',
    `create_area_code`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建系统编码',
    `create_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户id',
    `create_tenant_id`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建租户id',
    `create_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户姓名',
    `create_time`          datetime     NOT NULL                                  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`          datetime                                               DEFAULT NULL COMMENT '更新时间',
    `del_flag`             tinyint(1)   NOT NULL                                  DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`file_id`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`),
    KEY `Index_file_original_name` (`file_original_name`),
    KEY `Index_file_size` (`file_size`),
    KEY `Index_file_dir_prefix` (`file_dir_prefix`),
    KEY `Index_file_storage_type` (`file_storage_type`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='本地文件服务';

-- ----------------------------
-- Table structure for sys_common_area
-- ----------------------------
DROP TABLE IF EXISTS `sys_common_area`;
CREATE TABLE `sys_common_area`
(
    `area_id`              varchar(32) NOT NULL COMMENT '区域id',
    `parent_id`            varchar(32)                                            DEFAULT NULL COMMENT '上级区域id',
    `pre_pin_yin`          varchar(128)                                           DEFAULT NULL COMMENT '区域名称拼音的第一个字母',
    `simple_py`            varchar(128)                                           DEFAULT NULL COMMENT '首字母简拼',
    `pin_yin`              varchar(128)                                           DEFAULT NULL COMMENT '区域名称拼音',
    `whole_name`           varchar(128)                                           DEFAULT NULL COMMENT '完整名称',
    `province_id`          varchar(128)                                           DEFAULT NULL COMMENT '所属省级id',
    `simple_name`          varchar(128)                                           DEFAULT NULL COMMENT '中文简称',
    `area_level`           smallint                                               DEFAULT NULL COMMENT '级别：1为省级，2为市级，3为县级, 4为乡, 5为村',
    `area_name`            varchar(128)                                           DEFAULT NULL COMMENT '区域名称',
    `area_code`            varchar(128)                                           DEFAULT NULL COMMENT '区号',
    `city_id`              varchar(128)                                           DEFAULT NULL COMMENT '所属城市id',
    `lon`                  varchar(32)                                            DEFAULT NULL COMMENT '本区域经度',
    `lat`                  varchar(32)                                            DEFAULT NULL COMMENT '本区域纬度',
    `zip_code`             varchar(128)                                           DEFAULT NULL COMMENT '邮编',
    `county_id`            varchar(128)                                           DEFAULT NULL COMMENT '区县id',
    `remark`               varchar(256)                                           DEFAULT NULL COMMENT '备注/说明',
    `create_area_code`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建系统编码',
    `create_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户id',
    `create_tenant_id`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建租户id',
    `create_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户姓名',
    `create_time`          datetime    NOT NULL                                   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`          datetime                                               DEFAULT NULL COMMENT '更新时间',
    `del_flag`             tinyint(1)  NOT NULL                                   DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`area_id`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`),
    KEY `Index_parent_id` (`parent_id`),
    KEY `Index_area_name` (`area_name`),
    KEY `Index_area_level` (`area_level`),
    KEY `Index_province_id` (`province_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='区域表';

-- ----------------------------
-- Table structure for sys_common_category
-- ----------------------------
DROP TABLE IF EXISTS `sys_common_category`;
CREATE TABLE `sys_common_category`
(
    `category_id`          varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '分类id',
    `parent_id`            varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '父级id',
    `category_name`        varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类名称',
    `category_common_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '分类统一编码',
    `category_code`        varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类编码',
    `is_parent`            tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '是否父节:0-不是，1-是，默认0',
    `unique_help`          varchar(32)                                             NOT NULL DEFAULT '1' COMMENT '唯一索引帮助字段,默认1，如果删除改值未主键',
    `create_area_code`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建系统编码',
    `create_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建用户id',
    `create_tenant_id`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建租户id',
    `create_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建用户姓名',
    `create_time`          datetime                                                NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`          datetime                                                         DEFAULT NULL COMMENT '更新时间',
    `remark`               varchar(255)                                                     DEFAULT NULL COMMENT '备注',
    `del_flag`             tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`category_id`),
    UNIQUE KEY `unique_category_code` (`category_code`, `unique_help`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_category_common_code` (`category_common_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='分类字典表';

-- ----------------------------
-- Table structure for sys_common_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_common_dict`;
CREATE TABLE `sys_common_dict`
(
    `dict_id`              varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '字典id',
    `dict_name`            varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典名称',
    `dict_code`            varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典编码',
    `dict_status`          tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '字典状态：1启用，0禁用，默认0',
    `dict_type`            smallint                                                NOT NULL DEFAULT '0' COMMENT '字典类型：0-字符串,1-数字,2-布尔。默认0',
    `unique_help`          varchar(32)                                             NOT NULL DEFAULT '1' COMMENT '唯一索引帮助字段,默认1，如果删除改值未主键',
    `remark`               varchar(255)                                                     DEFAULT NULL COMMENT '备注',
    `create_area_code`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建系统编码',
    `create_tenant_id`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建租户id',
    `create_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建用户姓名',
    `create_time`          datetime                                                NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`          datetime                                                         DEFAULT NULL COMMENT '更新时间',
    `del_flag`             tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`dict_id`),
    UNIQUE KEY `Unique_dict` (`dict_code`, `unique_help`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='数据字典表';

-- ----------------------------
-- Table structure for sys_common_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_common_dict_item`;
CREATE TABLE `sys_common_dict_item`
(
    `item_id`              varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '字典项id',
    `dict_id`              varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '字典id',
    `item_text`            varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '字典项名称',
    `item_value`           varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '字典项值',
    `dict_code`            varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典编码',
    `sort_order`           int                                                     NOT NULL DEFAULT '0' COMMENT '排序,越小越靠前,默认0',
    `item_status`          tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '字典项状态：1启用，0禁用，默认0',
    `unique_help`          varchar(32)                                             NOT NULL DEFAULT '1' COMMENT '唯一索引帮助字段,默认1，如果删除改值未主键',
    `create_area_code`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建系统编码',
    `create_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建用户id',
    `create_tenant_id`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建租户id',
    `create_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建用户姓名',
    `create_time`          datetime                                                NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`          datetime                                                         DEFAULT NULL COMMENT '更新时间',
    `remark`               varchar(255)                                                     DEFAULT NULL COMMENT '备注',
    `del_flag`             tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`item_id`),
    UNIQUE KEY `Unique_dict_item` (`dict_id`, `item_value`, `unique_help`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_dict_code` (`dict_code`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='数据字典配置项表';

-- ----------------------------
-- Table structure for sys_manage_custom_filter
-- ----------------------------
DROP TABLE IF EXISTS `sys_manage_custom_filter`;
CREATE TABLE `sys_manage_custom_filter`
(
    `custom_filter_id`     varchar(32)  NOT NULL COMMENT '自定义过滤器id',
    `service_id`           varchar(32)  NOT NULL COMMENT '服务id',
    `filter_name`          varchar(256) NOT NULL COMMENT '过滤器名称',
    `filter_type_name`     varchar(256) NOT NULL COMMENT '过滤器类型名称',
    `filter_type`          varchar(256) NOT NULL COMMENT '过滤器类型',
    `filter_status`        smallint     NOT NULL                                  DEFAULT '0' COMMENT '过滤器状态:0-禁用,1-启用，默认0',
    `rules`                json                                                   DEFAULT NULL COMMENT '过滤器规则:{key:value}',
    `have_special`         tinyint(1)   NOT NULL                                  DEFAULT '0' COMMENT '是否有特殊url:0-没有,1-有。默认0',
    `enable_delete`        tinyint(1)   NOT NULL                                  DEFAULT '1' COMMENT '是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)',
    `remark`               varchar(255)                                           DEFAULT NULL COMMENT '备注',
    `create_area_code`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建系统编码',
    `create_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户id',
    `create_tenant_id`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建租户id',
    `create_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户姓名',
    `create_time`          datetime     NOT NULL                                  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`          datetime                                               DEFAULT NULL COMMENT '更新时间',
    `del_flag`             tinyint(1)   NOT NULL                                  DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`custom_filter_id`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`),
    KEY `Index_service_id` (`service_id`),
    KEY `Index_have_special` (`have_special`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='自定义过滤器';

-- ----------------------------
-- Table structure for sys_manage_route
-- ----------------------------
DROP TABLE IF EXISTS `sys_manage_route`;
CREATE TABLE `sys_manage_route`
(
    `route_id`             varchar(32)  NOT NULL COMMENT '路由id',
    `route_code`           varchar(256) NOT NULL COMMENT '路由编码(唯一)',
    `service_id`           varchar(32)  NOT NULL COMMENT '服务id',
    `service_code`         varchar(255)                                           DEFAULT NULL COMMENT '服务编码,当选择负载均衡器时使用必填',
    `route_name`           varchar(256) NOT NULL COMMENT '路由名称',
    `url`                  varchar(256)                                           DEFAULT NULL COMMENT '路由url地址,当选择非负载均衡器时必填',
    `is_load_balancer`     tinyint(1)   NOT NULL                                  DEFAULT '0' COMMENT '是否负载均衡器:0-不是,1-是，默认0。选择均衡器时监听信息才可以使用,同时该字段与路由对应',
    `load_balancer_type`   varchar(32)                                            DEFAULT NULL COMMENT '负载均衡器类型:0-lb,1-lb:ws,2-lb:wss,来自常量字典:gateway-service:LbType',
    `metadata_json`        json                                                   DEFAULT NULL COMMENT '路由元数据,数据库json存储,入库前转为字符串',
    `route_order`          int          NOT NULL                                  DEFAULT '0' COMMENT '路由排序,越小越靠前，默认0',
    `enable_delete`        tinyint(1)   NOT NULL                                  DEFAULT '1' COMMENT '是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)',
    `route_state`          tinyint(1)   NOT NULL                                  DEFAULT '0' COMMENT '路由状态:0-禁用,1-启用。默认0',
    `remark`               varchar(255)                                           DEFAULT NULL COMMENT '备注',
    `unique_help`          varchar(32)  NOT NULL                                  DEFAULT '1' COMMENT '唯一索引帮助字段,默认1，如果删除该值为主键',
    `create_area_code`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建系统编码',
    `create_tenant_id`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建租户id',
    `create_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户姓名',
    `create_time`          datetime     NOT NULL                                  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`          datetime                                               DEFAULT NULL COMMENT '更新时间',
    `del_flag`             tinyint(1)   NOT NULL                                  DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`route_id`),
    UNIQUE KEY `unique_route_code` (`route_code`, `unique_help`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`),
    KEY `Index_service_id` (`service_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='路由';

-- ----------------------------
-- Table structure for sys_manage_route_custom_filter
-- ----------------------------
DROP TABLE IF EXISTS `sys_manage_route_custom_filter`;
CREATE TABLE `sys_manage_route_custom_filter`
(
    `route_custom_filter_id` varchar(32)  NOT NULL COMMENT '路由自定义过滤器id',
    `custom_filter_id`       varchar(32)  NOT NULL COMMENT '自定义过滤器id',
    `route_id`               varchar(32)  NOT NULL COMMENT '路由id',
    `filter_type`            varchar(256) NOT NULL COMMENT '过滤器类型:来自网关常量FilterCustomPostType,FilterCustomPreType',
    PRIMARY KEY (`route_custom_filter_id`),
    KEY `Index_custom_filter_id` (`custom_filter_id`),
    KEY `Index_route_id` (`route_id`),
    KEY `Unique_route_id_custom_filter_id_filter_type` (`custom_filter_id`, `route_id`, `filter_type`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='路由-自定义过滤器表';

-- ----------------------------
-- Table structure for sys_manage_route_filter
-- ----------------------------
DROP TABLE IF EXISTS `sys_manage_route_filter`;
CREATE TABLE `sys_manage_route_filter`
(
    `filter_id`            varchar(32)  NOT NULL COMMENT '过滤器id',
    `route_id`             varchar(32)  NOT NULL COMMENT '路由id',
    `service_id`           varchar(32)  NOT NULL COMMENT '服务id',
    `filter_type`          varchar(64)  NOT NULL COMMENT '过滤器类型',
    `filter_type_name`     varchar(256) NOT NULL COMMENT '过滤器类型名称',
    `filter_name`          varchar(128) NOT NULL COMMENT '过滤器名称',
    `rules`                json                                                   DEFAULT NULL COMMENT '过滤器规则:{key:value}',
    `enable_delete`        tinyint(1)   NOT NULL                                  DEFAULT '1' COMMENT '是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)',
    `remark`               varchar(255)                                           DEFAULT NULL COMMENT '备注',
    `create_area_code`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建系统编码',
    `create_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户id',
    `create_tenant_id`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建租户id',
    `create_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户姓名',
    `create_time`          datetime     NOT NULL                                  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`          datetime                                               DEFAULT NULL COMMENT '更新时间',
    `del_flag`             tinyint(1)   NOT NULL                                  DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`filter_id`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`),
    KEY `Index_service_id` (`service_id`),
    KEY `Index_route_id` (`route_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='系统路由过滤器';

-- ----------------------------
-- Table structure for sys_manage_route_predicate
-- ----------------------------
DROP TABLE IF EXISTS `sys_manage_route_predicate`;
CREATE TABLE `sys_manage_route_predicate`
(
    `predicate_id`         varchar(32)  NOT NULL COMMENT '断言id',
    `service_id`           varchar(32)  NOT NULL COMMENT '服务id',
    `route_id`             varchar(32)  NOT NULL COMMENT '路由id',
    `predicate_type`       varchar(64)  NOT NULL COMMENT '断言类型',
    `predicate_type_name`  varchar(256) NOT NULL COMMENT '断言类型名称',
    `predicate_name`       varchar(128) NOT NULL COMMENT '断言名称',
    `rules`                json                                                   DEFAULT NULL COMMENT '断言规则:[{ruleName:规则名称,ruleValue:规则值}]',
    `enable_delete`        tinyint(1)   NOT NULL                                  DEFAULT '1' COMMENT '是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)',
    `remark`               varchar(255)                                           DEFAULT NULL COMMENT '备注',
    `create_area_code`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建系统编码',
    `create_tenant_id`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建租户id',
    `create_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户姓名',
    `create_time`          datetime     NOT NULL                                  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`          datetime                                               DEFAULT NULL COMMENT '更新时间',
    `del_flag`             tinyint(1)   NOT NULL                                  DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`predicate_id`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_route_id` (`route_id`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`),
    KEY `Index_service_id` (`service_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='路由断言';

-- ----------------------------
-- Table structure for sys_manage_service
-- ----------------------------
DROP TABLE IF EXISTS `sys_manage_service`;
CREATE TABLE `sys_manage_service`
(
    `service_id`            varchar(32)  NOT NULL COMMENT '服务id',
    `service_code`          varchar(128) NOT NULL COMMENT '服务编码',
    `service_name`          varchar(128) NOT NULL COMMENT '服务名',
    `is_load_balancer`      tinyint(1)   NOT NULL                                  DEFAULT '0' COMMENT '是否负载均衡器:0-不是,1-是，默认0。选择均衡器时监听信息才可以使用,同时该字段与路由对应',
    `enable_swagger`        tinyint(1)   NOT NULL                                  DEFAULT '0' COMMENT '聚合swagger:0-不聚合,1-聚合，默认0',
    `swagger_config_url`    varchar(256)                                           DEFAULT '0' COMMENT 'swagger配置地址',
    `subscribe_change`      tinyint(1)   NOT NULL                                  DEFAULT '0' COMMENT '是否监听系统变化:0-不订阅,1-订阅,默认0',
    `notice_change`         tinyint(1)                                             DEFAULT '0' COMMENT '是否发送变化通知:0-不通知,1-通知。默认0',
    `notice_type`           smallint                                               DEFAULT NULL COMMENT '通知类型:0-邮件,1-短信,2-微信消息，当选择监听服务变化并且通知时必填',
    `service_state`         tinyint(1)   NOT NULL                                  DEFAULT '0' COMMENT '服务状态:0-禁用,1-启用。默认0',
    `service_metadata_json` json                                                   DEFAULT NULL COMMENT '服务元数据,数据库json存储,入库前转为字符串',
    `notice_template_id`    varchar(32)                                            DEFAULT '0' COMMENT '通知模板id，当选择监听服务变化并且通知时必填',
    `head_user_name`        varchar(32)                                            DEFAULT NULL COMMENT '负责人姓名，当选择监听服务变化并且通知时必填',
    `head_user_id`          varchar(32)                                            DEFAULT NULL COMMENT '负责人用户id，当选择监听服务变化并且通知时必填',
    `enable_delete`         tinyint(1)   NOT NULL                                  DEFAULT '1' COMMENT '是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)',
    `remark`                varchar(255)                                           DEFAULT NULL COMMENT '备注',
    `unique_help`           varchar(32)  NOT NULL                                  DEFAULT '1' COMMENT '唯一索引帮助字段,默认1，如果删除该值为主键',
    `create_area_code`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`    varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建系统编码',
    `create_tenant_id`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建租户id',
    `create_user_id`        varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户姓名',
    `create_time`           datetime     NOT NULL                                  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`        varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`           datetime                                               DEFAULT NULL COMMENT '更新时间',
    `del_flag`              tinyint(1)   NOT NULL                                  DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`service_id`),
    UNIQUE KEY `Unique_service_code` (`unique_help`, `service_code`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='服务管理';

-- ----------------------------
-- Table structure for sys_manage_special_url
-- ----------------------------
DROP TABLE IF EXISTS `sys_manage_special_url`;
CREATE TABLE `sys_manage_special_url`
(
    `special_url_id`       varchar(32)  NOT NULL COMMENT '特殊过滤器id',
    `custom_filter_id`     varchar(32)  NOT NULL COMMENT '自定义过滤器id',
    `url_name`             varchar(32)  NOT NULL COMMENT '接口名称',
    `url_describe`         varchar(256)                                           DEFAULT NULL COMMENT '接口描述',
    `url_type`             tinyint(1)   NOT NULL                                  DEFAULT '0' COMMENT '地址类型:0-系统,1-自定义,默认0',
    `special_url_type`     tinyint(1)   NOT NULL COMMENT '特殊url类型:1-白名单(放行url),2-黑名单(只处理url)',
    `special_status`       smallint     NOT NULL                                  DEFAULT '0' COMMENT '特殊地址状态:0-禁用,1-启用，默认0',
    `limit_method`         tinyint(1)   NOT NULL                                  DEFAULT '0' COMMENT '限制请求方法：0-不限制,1-限制',
    `request_method`       varchar(256)                                           DEFAULT NULL COMMENT '允许的请求方法,多个英文逗号隔开',
    `url`                  varchar(512) NOT NULL COMMENT '地址,服务地址或http地址',
    `enable_delete`        tinyint(1)   NOT NULL                                  DEFAULT '1' COMMENT '是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)',
    `remark`               varchar(256)                                           DEFAULT NULL COMMENT '备注',
    `create_area_code`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建系统编码',
    `create_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户姓名',
    `create_tenant_id`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建租户id',
    `create_time`          datetime     NOT NULL                                  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`          datetime                                               DEFAULT NULL COMMENT '更新时间',
    `del_flag`             tinyint(1)   NOT NULL                                  DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`special_url_id`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`),
    KEY `Index_url_type` (`url_type`),
    KEY `Index_special_status` (`special_status`),
    KEY `Index_custom_filter_id` (`custom_filter_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='路由特殊地址';

-- ----------------------------
-- Table structure for sys_rbac_client_details
-- ----------------------------
DROP TABLE IF EXISTS `sys_rbac_client_details`;
CREATE TABLE `sys_rbac_client_details`
(
    `client_detail_id`               varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '客户端信息id',
    `client_id`                      varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户端id',
    `client_name`                    varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户端名称',
    `client_ico`                     varchar(256)                                                     DEFAULT NULL COMMENT '客户端图标',
    `client_security`                varchar(256)                                                     DEFAULT NULL COMMENT '客户端密码',
    `limit_resource`                 tinyint(1)                                              NOT NULL DEFAULT '1' COMMENT '限制授权资源：0-不限制，1-限制。默认1',
    `resource_ids`                   json                                                             DEFAULT NULL COMMENT '授权资源ids,json array，json array',
    `signature_required`             tinyint(1)                                              NOT NULL DEFAULT '1' COMMENT '是否验签:0-不验签，1-验签，默认1',
    `signature_key`                  varchar(256)                                                     DEFAULT NULL COMMENT '数据签名key，当需要验签时必填',
    `authorized_grant_types`         json                                                    NOT NULL COMMENT '允许授权类型，来源与授权中心常量字典AuthorizedGrantType,json array',
    `hava_scoped`                    tinyint(1)                                                       DEFAULT NULL COMMENT '是否领域，0-不是,1-是。默认0',
    `scopes`                         json                                                             DEFAULT NULL COMMENT '领域,json array',
    `endpoints`                      json                                                    NOT NULL COMMENT '允许登录端点,json array',
    `single_login`                   tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '是否单设备登录：0-不是,1-是，默认0',
    `single_login_type`              tinyint(1)                                                       DEFAULT NULL COMMENT '单设备登录方式：1-同一用户只能在一个endpoint登录,2-同一用户可以在不同endpoint登录',
    `web_registered_redirect_uri`    varchar(256)                                                     DEFAULT NULL COMMENT '授权后跳转的URI（授权码模式必填）',
    `authority_infos`                json                                                             DEFAULT NULL COMMENT '授权权限,json array',
    `inner_system`                   tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '是否内部系统：0-不是，1-是，默认0',
    `last_auth_time`                 datetime                                                         DEFAULT NULL COMMENT '上次授权时间',
    `limit_error`                    tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '限制授权错误次数:0-不限制,1-限制。默认0',
    `max_error_num`                  int                                                              DEFAULT NULL COMMENT '允许最大授权错误次数，当限制授权错误时必填',
    `client_status`                  tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '状态：0-未启用,1-启用，2-锁定，默认0',
    `hava_auto_approve`              tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '是否自动批准：0-不自动，1-自动,默认0',
    `access_token_validity_seconds`  int                                                     NOT NULL DEFAULT '1800' COMMENT '访问token的有效时长(单位s)，默认1800秒',
    `refresh_token_validity_seconds` int                                                     NOT NULL DEFAULT '604800' COMMENT '刷新token的有效时长(单位s)，默认604800秒,即7天',
    `code_validity_seconds`          int                                                     NOT NULL DEFAULT '300' COMMENT '授权码有效时常(单位s)，默认300秒',
    `additional_information`         json                                                             DEFAULT NULL COMMENT '扩展信息,json object',
    `unique_help`                    varchar(32)                                             NOT NULL DEFAULT '1' COMMENT '唯一索引帮助字段,默认1，如果删除该值为主键',
    `remark`                         varchar(255)                                                     DEFAULT NULL COMMENT '备注',
    `create_area_code`               varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code`           varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`            varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`             varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建系统编码',
    `create_user_id`                 varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`               varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建用户姓名',
    `create_tenant_id`               varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建租户id',
    `create_time`                    datetime                                                NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`                 varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`               varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`                    datetime                                                         DEFAULT NULL COMMENT '更新时间',
    `del_flag`                       tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`client_detail_id`),
    UNIQUE KEY `Unique_client_id` (`client_id`, `unique_help`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`),
    KEY `Index_client_name` (`client_name`),
    KEY `Index_inner_system` (`inner_system`),
    KEY `Index_auto_approve` (`hava_auto_approve`),
    KEY `Index_client_status` (`client_status`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='授权客户端信息';

-- ----------------------------
-- Table structure for sys_rbac_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_rbac_menu`;
CREATE TABLE `sys_rbac_menu`
(
    `menu_id`                varchar(32)                                             NOT NULL COMMENT '权限id',
    `parent_id`              varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '父id',
    `path`                   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT '' COMMENT '路径',
    `component`              varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '前端组件',
    `path_name`              varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '路由名称',
    `redirect`               varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '重定向地址',
    `menu_type`              smallint                                                NOT NULL COMMENT '权限类型(0:目录; 1:菜单:2:按钮),来源于常量字典:PermissionType',
    `iframe`                 tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '是否外连接,实际为boolean',
    `iframe_type`            smallint                                                         DEFAULT NULL COMMENT '外连接类型:0-内嵌,1-外链接',
    `menu_status`            smallint                                                NOT NULL DEFAULT '0' COMMENT '菜单状态:0-禁用,1-启用',
    `meta_title`             varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
    `ignore_auth`            tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '是否忽略权限，只在权限模式为Role的时候有效,实际为boolean',
    `ignore_keep_alive`      tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '是否忽略KeepAlive缓存,实际为boolean',
    `affix`                  tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '是否固定标签,实际为boolean',
    `icon`                   varchar(256)                                                     DEFAULT NULL COMMENT '图标',
    `icon_type`              smallint                                                NOT NULL DEFAULT '0' COMMENT '图标类型:0-系统图标(基于icon),1-自定义图标(基于图片路径)',
    `frame_src`              varchar(512)                                                     DEFAULT NULL COMMENT '内嵌iframe的地址',
    `transition_name`        varchar(128)                                                     DEFAULT NULL COMMENT '路由切换的动画名',
    `hide_breadcrumb`        tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '隐藏该路由在面包屑上面的显示,实际为boolean',
    `carry_param`            tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '是否携带参数并在tab上显示,实际为boolean',
    `hide_children_in_menu`  tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '隐藏所有子菜单,实际为boolean',
    `current_active_menu`    varchar(512)                                                     DEFAULT NULL COMMENT '当前激活的菜单。用于配置详情页时左侧激活的菜单路径',
    `hide_tab`               tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '当前路由不再标签页显示,实际为boolean',
    `hide_menu`              tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '当前路由不再菜单显示,实际为boolean',
    `order_no`               int                                                     NOT NULL DEFAULT '0' COMMENT '菜单排序',
    `ignore_route`           tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '忽略路由。用于在ROUTE_MAPPING以及BACK权限模式下，生成对应的菜单而忽略路由,实际为boolean',
    `show_tag`               tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '显示tag,0-不显示,1-显示，实际为boolean',
    `type`                   varchar(16)                                                      DEFAULT NULL COMMENT 'tag类型：primary、error、warn、success',
    `content`                varchar(16)                                                      DEFAULT NULL COMMENT 'tag内容',
    `dot`                    tinyint(1)                                                       DEFAULT '0' COMMENT '是否圆点,默认不是,实际为boolean',
    `hide_path_for_children` tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '是否在子级菜单的完整path中忽略本级path,实际为boolean',
    `button_action_tag`      varchar(32)                                                      DEFAULT NULL COMMENT '按钮权限标识',
    `button_express`         varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '鉴权表达式，不需要鉴权时默认为：permitAll()',
    `button_action`          varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '鉴权指令，只有表达式为非角色是使用',
    `system_id`              varchar(32)                                             NOT NULL COMMENT '所属系统',
    `enable_delete`          smallint                                                NOT NULL DEFAULT '1' COMMENT '是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)',
    `menu_sys_code`          varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '系统内置编码(系统自动生成)',
    `unique_help`            varchar(32)                                             NOT NULL DEFAULT '1' COMMENT '唯一索引帮助字段,默认1，如果删除该值为主键',
    `remark`                 varchar(255)                                                     DEFAULT NULL COMMENT '备注',
    `create_area_code`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`    varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建系统编码',
    `create_tenant_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建租户id',
    `create_user_id`         varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建用户姓名',
    `create_time`            datetime                                                NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`         varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`            datetime                                                         DEFAULT NULL COMMENT '更新时间',
    `del_flag`               tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`menu_id`),
    UNIQUE KEY `Unique_menu_sys_code` (`menu_sys_code`, `unique_help`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_menu_status` (`menu_status`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`),
    KEY `Index_system_id` (`system_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='菜单表';

-- ----------------------------
-- Table structure for sys_rbac_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_rbac_org`;
CREATE TABLE `sys_rbac_org`
(
    `org_id`                   varchar(32)                                             NOT NULL COMMENT '组织id',
    `parent_id`                varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '父级组织id',
    `org_name`                 varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组织名称',
    `org_simple_name`          varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '组织机构简称',
    `org_name_en`              varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '英文名',
    `org_name_abbr`            varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '缩写',
    `org_order`                int                                                              DEFAULT '0' COMMENT '排序',
    `org_type`                 smallint                                                NOT NULL COMMENT '组织机构类型：1-公司,2-部门',
    `org_code`                 varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组织编码',
    `org_sys_code`             varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组织编码(系统)',
    `org_status`               tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '组织状态：0-禁用，1-启用，默认0',
    `email`                    varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '邮箱',
    `phone`                    varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '手机号',
    `fax`                      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '传真',
    `address`                  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '地址',
    `social_code`              varchar(32)                                                      DEFAULT NULL COMMENT '统一社会信用代码',
    `area_code_name`           varchar(256)                                                     DEFAULT NULL COMMENT '行政区域名称',
    `area_code`                varchar(128)                                                     DEFAULT NULL COMMENT '行政区域',
    `detail_address`           varchar(256)                                                     DEFAULT NULL COMMENT '详细地址',
    `scope_business`           varchar(256)                                                     DEFAULT NULL COMMENT '经验范围',
    `legal_person`             varchar(30)                                                      DEFAULT NULL COMMENT '法人',
    `accounts_name`            varchar(30)                                                      DEFAULT NULL COMMENT '开户姓名',
    `accounts_bank`            varchar(30)                                                      DEFAULT NULL COMMENT '开户银行',
    `back_card`                varchar(32)                                                      DEFAULT NULL COMMENT '银行账号',
    `business_license_picture` varchar(1024)                                                    DEFAULT NULL COMMENT '营业执照',
    `seal_picture`             varchar(1024)                                                    DEFAULT NULL COMMENT '印章',
    `additional_information`   json                                                             DEFAULT NULL COMMENT '扩展信息,json object',
    `remark`                   varchar(255)                                                     DEFAULT NULL COMMENT '备注',
    `unique_help`              varchar(32)                                             NOT NULL DEFAULT '1' COMMENT '唯一索引帮助字段,默认1，如果删除该值为主键',
    `create_area_code`         varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建系统编码',
    `create_user_id`           varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建用户id',
    `create_tenant_id`         varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建租户id',
    `create_user_name`         varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建用户姓名',
    `create_time`              datetime                                                NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`           varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`         varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`              datetime                                                         DEFAULT NULL COMMENT '更新时间',
    `del_flag`                 tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`org_id`),
    UNIQUE KEY `Unique_org_sys_code` (`org_sys_code`, `unique_help`),
    UNIQUE KEY `Unique_org_code` (`org_code`, `unique_help`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`),
    KEY `Unique_social_code` (`social_code`, `unique_help`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='组织表';

-- ----------------------------
-- Table structure for sys_rbac_org_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_rbac_org_menu`;
CREATE TABLE `sys_rbac_org_menu`
(
    `role_menu_id` varchar(32) NOT NULL COMMENT '权限角色id',
    `menu_id`      varchar(32) NOT NULL COMMENT '权限id',
    `org_id`       varchar(32) NOT NULL COMMENT '机构id',
    PRIMARY KEY (`role_menu_id`),
    KEY `Index_menu_id` (`menu_id`),
    KEY `Index_org_id` (`org_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='机构-菜单表';

-- ----------------------------
-- Table structure for sys_rbac_org_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_rbac_org_role`;
CREATE TABLE `sys_rbac_org_role`
(
    `org_role_id`           varchar(32)                                             NOT NULL COMMENT '机构角色id',
    `role_name`             varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
    `org_id`                varchar(32)                                             NOT NULL COMMENT '机构id',
    `data_auth_type`        smallint                                                         DEFAULT NULL COMMENT '数据权限类型：1-全部,2-机构,3-机构及以下,4-机构自定义,5-区域,6-区域及以下,7-区域自定义,6-仅自己',
    `custom_data_auth_data` json                                                             DEFAULT NULL COMMENT '自定义类角色数据权限,权限ids json array',
    `role_code`             varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色编码',
    `auto_bind`             tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '绑定方式:0-手动,1-自动。默认0，挂接机构时自动挂接',
    `role_status`           tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '角色状态:0-禁用,1-启用,默认0',
    `remark`                varchar(255)                                                     DEFAULT NULL COMMENT '备注',
    `unique_help`           varchar(32)                                             NOT NULL DEFAULT '1' COMMENT '唯一索引帮助字段,默认1，如果删除该值为主键',
    `create_area_code`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`    varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建系统编码',
    `create_tenant_id`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建租户id',
    `create_user_id`        varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建用户姓名',
    `create_time`           datetime                                                NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`        varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`           datetime                                                         DEFAULT NULL COMMENT '更新时间',
    `del_flag`              tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`org_role_id`),
    UNIQUE KEY `Unique_role_code_org_id` (`role_code`, `unique_help`, `org_id`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_role_status` (`role_status`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`),
    KEY `Index_org_id` (`org_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='机构角色表';

-- ----------------------------
-- Table structure for sys_rbac_org_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_rbac_org_role_menu`;
CREATE TABLE `sys_rbac_org_role_menu`
(
    `org_role_menu_id` varchar(32) NOT NULL COMMENT '机构权限角色id',
    `menu_id`          varchar(32) NOT NULL COMMENT '权限id',
    `org_role_id`      varchar(32) NOT NULL COMMENT '机构角色id',
    `org_id`           varchar(32) NOT NULL COMMENT '机构id',
    PRIMARY KEY (`org_role_menu_id`),
    KEY `Index_menu_id` (`menu_id`),
    KEY `Index_org_role_id` (`org_role_id`),
    KEY `Index_org_id` (`org_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='机构角色-菜单表';

-- ----------------------------
-- Table structure for sys_rbac_org_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_rbac_org_role_user`;
CREATE TABLE `sys_rbac_org_role_user`
(
    `role_user_id` varchar(32)                                            NOT NULL COMMENT '角色用户id',
    `org_role_id`  varchar(32)                                            NOT NULL COMMENT '机构角色id',
    `org_id`       varchar(32)                                            NOT NULL COMMENT '机构id',
    `user_id`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
    PRIMARY KEY (`role_user_id`),
    KEY `Index_org_role_id` (`org_role_id`),
    KEY `Index_user_id` (`user_id`),
    KEY `Index_org_id` (`org_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='机构角色-用户';

-- ----------------------------
-- Table structure for sys_rbac_org_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_rbac_org_user`;
CREATE TABLE `sys_rbac_org_user`
(
    `org_user_id` varchar(32)                                            NOT NULL COMMENT '机构用户id',
    `org_id`      varchar(32)                                            NOT NULL COMMENT '机构id',
    `user_id`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
    PRIMARY KEY (`org_user_id`),
    UNIQUE KEY `Unique_org_id_user_id` (`org_id`, `user_id`),
    KEY `Index_org_id` (`org_id`),
    KEY `Index_user_id` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='机构-用户';

-- ----------------------------
-- Table structure for sys_rbac_position
-- ----------------------------
DROP TABLE IF EXISTS `sys_rbac_position`;
CREATE TABLE `sys_rbac_position`
(
    `position_id`          varchar(32)                                             NOT NULL COMMENT '职位id',
    `position_code`        varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '职位编码',
    `position_name`        varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '职位名称',
    `position_rank`        int                                                              DEFAULT NULL COMMENT '职级',
    `position_status`      tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '职位状态：0-无效，1-有效，默认0',
    `unique_help`          varchar(32)                                             NOT NULL DEFAULT '1' COMMENT '唯一索引帮助字段,默认1，如果删除该值为主键',
    `remark`               varchar(255)                                                     DEFAULT NULL COMMENT '备注',
    `create_area_code`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建系统编码',
    `create_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建用户id',
    `create_tenant_id`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建租户id',
    `create_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建用户姓名',
    `create_time`          datetime                                                NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`          datetime                                                         DEFAULT NULL COMMENT '更新时间',
    `del_flag`             tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`position_id`),
    UNIQUE KEY `unique_position` (`position_code`, `unique_help`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_position_status` (`position_status`),
    KEY `Index_create_tenant_id` (`create_tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='职位表';

-- ----------------------------
-- Table structure for sys_rbac_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_rbac_role`;
CREATE TABLE `sys_rbac_role`
(
    `role_id`               varchar(32)                                             NOT NULL COMMENT '角色id',
    `role_name`             varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
    `role_sys_code`         varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色系统编码(系统自动创建)',
    `data_auth_type`        smallint                                                         DEFAULT NULL COMMENT '数据权限类型：1-全部,2-机构,3-机构及以下,4-机构自定义,5-区域,6-区域及以下,7-区域自定义,6-仅自己',
    `custom_data_auth_data` json                                                             DEFAULT NULL COMMENT '自定义类角色数据权限,权限ids json array',
    `role_code`             varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色编码',
    `parent_role_id`        varchar(32)                                                      DEFAULT NULL COMMENT '上级角色id',
    `enable_delete`         tinyint(1)                                              NOT NULL DEFAULT '1' COMMENT '是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)',
    `auto_bind`             tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '绑定方式:0-手动,1-自动。默认0(用户创建时自动挂接)',
    `role_status`           tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '角色状态:0-禁用,1-启用,默认0',
    `remark`                varchar(255)                                                     DEFAULT NULL COMMENT '备注',
    `unique_help`           varchar(32)                                             NOT NULL DEFAULT '1' COMMENT '唯一索引帮助字段,默认1，如果删除该值为主键',
    `create_area_code`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`    varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建系统编码',
    `create_tenant_id`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建租户id',
    `create_user_id`        varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建用户姓名',
    `create_time`           datetime                                                NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`        varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`           datetime                                                         DEFAULT NULL COMMENT '更新时间',
    `del_flag`              tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`role_id`),
    UNIQUE KEY `Unique_role` (`role_code`, `unique_help`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_role_status` (`role_status`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Unique_role_sys_code` (`role_sys_code`, `unique_help`),
    KEY `Index_create_tenant_id` (`create_tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='角色表';

-- ----------------------------
-- Table structure for sys_rbac_role_client
-- ----------------------------
DROP TABLE IF EXISTS `sys_rbac_role_client`;
CREATE TABLE `sys_rbac_role_client`
(
    `role_client`      varchar(32)                                            NOT NULL COMMENT '客户端角色id',
    `role_id`          varchar(32)                                            NOT NULL COMMENT '角色id',
    `client_detail_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户端信息id',
    PRIMARY KEY (`role_client`),
    KEY `Index_role_id` (`role_id`),
    KEY `Index_client_detail_id` (`client_detail_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='角色-客户端';

-- ----------------------------
-- Table structure for sys_rbac_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_rbac_role_menu`;
CREATE TABLE `sys_rbac_role_menu`
(
    `role_menu_id` varchar(32) NOT NULL COMMENT '权限角色id',
    `menu_id`      varchar(32) NOT NULL COMMENT '权限id',
    `role_id`      varchar(32) NOT NULL COMMENT '角色id',
    PRIMARY KEY (`role_menu_id`),
    KEY `Index_menu_id` (`menu_id`),
    KEY `Index_role_id` (`role_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='角色-菜单表';

-- ----------------------------
-- Table structure for sys_rbac_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_rbac_role_user`;
CREATE TABLE `sys_rbac_role_user`
(
    `role_user_id` varchar(32)                                            NOT NULL COMMENT '角色用户id',
    `role_id`      varchar(32)                                            NOT NULL COMMENT '角色id',
    `user_id`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
    PRIMARY KEY (`role_user_id`),
    KEY `Index_role_id` (`role_id`),
    KEY `Index_user_id` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='角色-客户端';

-- ----------------------------
-- Table structure for sys_rbac_system
-- ----------------------------
DROP TABLE IF EXISTS `sys_rbac_system`;
CREATE TABLE `sys_rbac_system`
(
    `system_id`            varchar(32)  NOT NULL COMMENT '系统id',
    `system_name`          varchar(128) NOT NULL COMMENT '系统名称',
    `system_code`          varchar(32)  NOT NULL COMMENT '系统编码',
    `system_describe`      varchar(512)                                           DEFAULT NULL COMMENT '系统描述',
    `icon`                 text COMMENT '系统图标',
    `remark`               varchar(255)                                           DEFAULT NULL COMMENT '备注',
    `unique_help`          varchar(32)  NOT NULL                                  DEFAULT '1' COMMENT '唯一索引帮助字段,默认1，如果删除该值为主键',
    `create_area_code`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建系统编码',
    `create_tenant_id`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建租户id',
    `create_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户姓名',
    `create_time`          datetime     NOT NULL                                  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`          datetime                                               DEFAULT NULL COMMENT '更新时间',
    `del_flag`             tinyint(1)   NOT NULL                                  DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`system_id`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`),
    KEY `Unique_system_code` (`unique_help`, `system_code`),
    KEY `Index_system_name` (`system_name`),
    KEY `Index_system_code` (`system_code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='系统';

-- ----------------------------
-- Table structure for sys_rbac_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_rbac_user`;
CREATE TABLE `sys_rbac_user`
(
    `user_id`                varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '用户id',
    `user_name`              varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
    `open_id`                varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '开放id',
    `nick_name`              varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '用户昵称',
    `real_name`              varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '真实姓名',
    `password`               varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
    `salt`                   varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '密码盐',
    `short_profile`          varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '个人简介',
    `avatar`                 varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '头像',
    `birthday`               date                                                             DEFAULT NULL COMMENT '生日',
    `sex`                    tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '性别:0-默认未知,1-男,2-女,默认0',
    `email`                  varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '邮件',
    `is_initial_password`    tinyint(1)                                              NOT NULL DEFAULT '1' COMMENT '是否初始密码:0-不是,1-是,默认1',
    `phone`                  varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '电话号码',
    `register_source`        varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '来源',
    `user_status`            tinyint(1)                                              NOT NULL DEFAULT '1' COMMENT '状态:0-未激活,1-正常,2-冻结,默认1',
    `work_no`                varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '工号，唯一键',
    `telephone`              varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '座机号',
    `enable_delete`          tinyint(1)                                              NOT NULL DEFAULT '1' COMMENT '是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)',
    `position_ids`           json                                                             DEFAULT NULL COMMENT '职位,org_id array',
    `manager_org_ids`        json                                                             DEFAULT NULL COMMENT '负责机构,org_id array',
    `additional_information` json                                                             DEFAULT NULL COMMENT '扩展信息,json object',
    `login_fail_error_num`   int                                                     NOT NULL DEFAULT '0' COMMENT '连续登录错误次数',
    `current_org_id`         varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '当前登录部门id',
    `current_login_ip`       varchar(128)                                                     DEFAULT '' COMMENT '最后登录IP',
    `current_login_date`     datetime                                                         DEFAULT NULL COMMENT '最后登录时间',
    `unique_help`            varchar(32)                                             NOT NULL DEFAULT '1' COMMENT '唯一索引帮助字段,默认1，如果删除该值为主键',
    `remark`                 varchar(255)                                                     DEFAULT NULL COMMENT '备注',
    `create_area_code`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`    varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建系统编码',
    `create_tenant_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建租户id',
    `create_user_id`         varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '创建用户姓名',
    `create_time`            datetime                                                NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`         varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci           DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`            datetime                                                         DEFAULT NULL COMMENT '更新时间',
    `del_flag`               tinyint(1)                                              NOT NULL DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `Unique_user_name` (`user_name`, `unique_help`),
    UNIQUE KEY `Unique_phone` (`phone`, `unique_help`),
    UNIQUE KEY `Unique_open_id` (`open_id`, `unique_help`),
    UNIQUE KEY `Unique_user_name_phone_open_id` (`user_name`, `phone`, `open_id`, `unique_help`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户表';

-- ----------------------------
-- Table structure for sys_rbac_user_agent
-- ----------------------------
DROP TABLE IF EXISTS `sys_rbac_user_agent`;
CREATE TABLE `sys_rbac_user_agent`
(
    `agent_id`             varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代理id',
    `user_id`              varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名id',
    `agent_user_id`        varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代理人用户id',
    `is_limit`             tinyint(1)                                             NOT NULL DEFAULT '0' COMMENT '是否限制时间:0-不限制,1-限制，默认0',
    `agent_start_time`     datetime                                                        DEFAULT NULL COMMENT '代理开始时间',
    `agent_end_time`       datetime                                                        DEFAULT NULL COMMENT '代理结束时间',
    `agent_status`         tinyint(1)                                             NOT NULL DEFAULT '0' COMMENT '状态：0-无效，1-有效,默认0',
    `unique_help`          varchar(32)                                            NOT NULL DEFAULT '1' COMMENT '唯一索引帮助字段,默认1，如果删除该值为主键',
    `remark`               varchar(255)                                                    DEFAULT NULL COMMENT '备注',
    `create_area_code`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建系统编码',
    `create_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建用户姓名',
    `create_tenant_id`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '创建租户id',
    `create_time`          datetime                                               NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci          DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`          datetime                                                        DEFAULT NULL COMMENT '更新时间',
    `del_flag`             tinyint(1)                                             NOT NULL DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`agent_id`),
    UNIQUE KEY `Unique_agent` (`user_id`, `agent_user_id`, `unique_help`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`),
    KEY `Index_agent_status` (`agent_status`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户-代理人表';

-- ----------------------------
-- Table structure for sys_rbac_user_identity
-- ----------------------------
DROP TABLE IF EXISTS `sys_rbac_user_identity`;
CREATE TABLE `sys_rbac_user_identity`
(
    `identity_id`           varchar(32) NOT NULL COMMENT '实名信息id',
    `user_id`               varchar(32) NOT NULL COMMENT '用户id',
    `real_name`             varchar(64) NOT NULL COMMENT '真实姓名',
    `sex`                   tinyint(1)  NOT NULL                                   DEFAULT '0' COMMENT '性别:0-默认未知,1-男,2-女,默认0',
    `nationality`           varchar(32)                                            DEFAULT NULL COMMENT '名族',
    `id_card`               varchar(32)                                            DEFAULT NULL COMMENT '身份证件号码',
    `id_card_issue`         varchar(256)                                           DEFAULT NULL COMMENT '身份证件发证机关',
    `id_card_effective`     datetime                                               DEFAULT NULL COMMENT '身份证书有效期开始',
    `id_card_effective_end` datetime                                               DEFAULT NULL COMMENT '身份证有效期结束',
    `positive_photo`        varchar(256)                                           DEFAULT NULL COMMENT '正面照',
    `back_photo`            varchar(256)                                           DEFAULT NULL COMMENT '反面照',
    `handheld_photo`        varchar(256)                                           DEFAULT NULL COMMENT '证件手持照',
    `identity_status`       smallint    NOT NULL                                   DEFAULT '0' COMMENT '实名状态:0-待提交,1-审核中，2-未通过(审核失败)，3-通过(审核成功),默认0',
    `audit_start_time`      datetime                                               DEFAULT NULL COMMENT '审核开始时间',
    `audit_end_time`        datetime                                               DEFAULT NULL COMMENT '审核结束时间',
    `bank_card_positive`    varchar(256)                                           DEFAULT NULL COMMENT '银行卡正面',
    `bank_card_back`        varchar(256)                                           DEFAULT NULL COMMENT '银行卡反面',
    `bank_card_num`         varchar(32)                                            DEFAULT NULL COMMENT '银行卡号',
    `bank_reserve_phone`    varchar(32)                                            DEFAULT NULL COMMENT '银行预留手机号码',
    `belong_area`           varchar(256)                                           DEFAULT NULL COMMENT '银行卡归属地',
    `remark`                varchar(255)                                           DEFAULT NULL COMMENT '备注',
    `create_area_code`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建区域编码',
    `create_position_code`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建职位编码',
    `create_org_sys_code`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建机构系统编码',
    `create_system_code`    varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建系统编码',
    `create_tenant_id`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建租户id',
    `create_user_id`        varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户id',
    `create_user_name`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户姓名',
    `create_time`           datetime    NOT NULL                                   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id`        varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新用户id',
    `update_user_name`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新用户姓名',
    `update_time`           datetime                                               DEFAULT NULL COMMENT '更新时间',
    `del_flag`              tinyint(1)  NOT NULL                                   DEFAULT '0' COMMENT '删除状态:0-正常,1-已删除,默认0',
    PRIMARY KEY (`identity_id`),
    KEY `Index_del_flag` (`del_flag`),
    KEY `Index_create_time` (`create_time`),
    KEY `Index_create_user_id` (`create_user_id`),
    KEY `Index_create_area_code` (`create_area_code`),
    KEY `Index_create_position_code` (`create_position_code`),
    KEY `Index_create_org_sys_code` (`create_org_sys_code`),
    KEY `Index_create_system_code` (`create_system_code`),
    KEY `Index_create_tenant_id` (`create_tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='实名信息表';

SET FOREIGN_KEY_CHECKS = 1;
