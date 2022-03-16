BEGIN;

/*==============================================================*/
/* 权限服务(skillfull_auth_service)                                         */
/*==============================================================*/
-- 初始化用户
INSERT INTO `auth_rbac_user` (`user_id`, `user_name`, `nick_name`, `real_name`, `password`, `salt`, `birthday`,
                              `user_status`, `enable_delete`)
VALUES ('1444190920879161344', 'admin', '大头虾', 'zxiaozhou',
        '$2a$10$nW2zo/n3Zi7ips2R6AJ4Y.lijQ4YULCrYFU4NL2SyZcLWZmTh6Qty', 'f5e032fa7afd627627c1a047db77926e',
        '1987-10-26', 1, 1);
-- 初始化角色
INSERT INTO `auth_rbac_role` (`role_id`, `role_name`, `role_sys_code`, `role_code`, `enable_delete`, `auto_bind`,
                              `role_status`, `remark`)
VALUES ('1314235356968173568', '超级管理员', 'A0001', 'SUPER_ROLE', 0, 0, 1, '系统最高权限,不可修改');
-- 初始化角色用户关联
INSERT INTO `auth_rbac_correlate_role` (`correlate_role_id`, `correlate_id`, `correlate_type`, `role_id`)
VALUES ('1444195681527447552', '1444190920879161344', 3, '1314235356968173568');
-- 初始化系统信息表
INSERT INTO `auth_common_system` (`system_id`, `system_name`, `system_code`)
VALUES ('1444196303513370624', '基础系统', 'BASE_SYSTEM');

COMMIT;
