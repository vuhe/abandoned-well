-- MySQL dump 10.13  Distrib 8.0.16, for macos10.14 (x86_64)
--
-- Host: 127.0.0.1    Database: well_pro
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
SET NAMES utf8mb4;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `region_code`
--

DROP TABLE IF EXISTS `region_code`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `region_code`
(
    `id`     varchar(20) NOT NULL COMMENT '主键',
    `code`   varchar(20)  DEFAULT NULL COMMENT '水文地质代码',
    `name`   varchar(100) DEFAULT NULL COMMENT '水文地质名称',
    `remark` text COMMENT '注释',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='二级水文地质分区代码';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `region_code`
--

LOCK TABLES `region_code` WRITE;
/*!40000 ALTER TABLE `region_code`
    DISABLE KEYS */;
INSERT INTO `region_code`
VALUES ('1490513957209767936', '0201', '黄淮海平原水文地质亚区',
        '郑州市、开封市、洛阳市（涧西区、西工区、老城区、瀍河回族区、吉利区、洛龙区、伊川县、新安县、偃师市、孟津县、宜阳县）、安阳市、鹤壁市、新乡市、焦作市、濮阳市、许昌市、漯河市、商丘市、信阳市（潢川县、息县、淮滨县、固始县）、周口市、驻马店市、济源示范区'),
       ('1490514266162200576', '0203', '伏牛山、大别山水文地质亚区',
        '洛阳市（洛宁县、嵩县、汝阳县、栾川县）、平顶山市、三门峡市、南阳市、信阳市（浉河区、平桥区、光山县、新县、罗山县、商城县）');
/*!40000 ALTER TABLE `region_code`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dept`
--

DROP TABLE IF EXISTS `sys_dept`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `sys_dept`
(
    `dept_id`   varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门主键',
    `parent_id` varchar(20) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '父级编号',
    `dept_name` varchar(50) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '部门名称',
    `sort`      int(11)                                 DEFAULT NULL COMMENT '排序',
    `leader`    varchar(50) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '负责人',
    `phone`     varchar(20) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '联系方式',
    `email`     varchar(50) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '邮箱',
    `status`    tinyint(1)                              DEFAULT NULL COMMENT '部门状态',
    `address`   varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '详细地址',
    PRIMARY KEY (`dept_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='用户部门';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept`
--

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept`
    DISABLE KEYS */;
INSERT INTO `sys_dept`
VALUES ('1', '0', '河南总部', 0, '管理员', '0371-XXXX-XXXX', 'test@test.com', 1, '河南郑州'),
       ('1316361008259792896', '1316360459930042368', '软件部', 1, '就眠仪式', '15553726531', 'pearadmin@gmail.com', 1,
        '山东济南'),
       ('1316361192645591040', '1316360459930042368', '市场部', 1, '就眠仪式', '15553726531', 'pearadmin@gmail.com', 1,
        '山东济南'),
       ('1FgFZUYIeSNXCUR', '1', '郑州分部', 1, '测试', '测试', 'test@test.com', 1, '河南郑州'),
       ('4', '2', '软件部', 2, '就眠仪式', '15553726531', 'pearadmin@gmail.com', 1, '山东济南'),
       ('5', '2', '市场部', 2, '就眠仪式', '15553726531', 'pearadmin@gmail.com', 1, '山东济南'),
       ('9FARX0wUC4ctyqe', '1', '南阳分部', 2, '测试2', 'null', 't@t.cn', 1, '河南南阳'),
       ('j9OZa0JZ9Eum72Z', '1FgFZUYIeSNXCUR', '金水区分部', 1, '郑州-金水-测试', '无', 't@t.cn', 1, '河南郑州金水区');
/*!40000 ALTER TABLE `sys_dept`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_logging`
--

DROP TABLE IF EXISTS `sys_logging`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `sys_logging`
(
    `id`              char(19) COLLATE utf8mb4_general_ci NOT NULL COMMENT '相应消息体',
    `title`           varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标题',
    `method`          varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '请求方式',
    `business_type`   varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '业务类型',
    `operate_id`      varchar(20) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '操作人 id',
    `operate_name`    varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作人',
    `operate_url`     varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作路径',
    `operate_address` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作地址',
    `request_param`   varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '请求参数',
    `response_body`   varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '相应消息体',
    `success`         varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否成功',
    `error_msg`       text COLLATE utf8mb4_general_ci COMMENT '异常信息',
    `create_time`     datetime                                DEFAULT NULL COMMENT '创建时间',
    `description`     varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
    `request_body`    varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '请求消息体',
    `browser`         varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '使用浏览器',
    `system_os`       varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作系统',
    `logging_type`    varchar(10) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '日志类型，登录日志，操作日志',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_power`
--

DROP TABLE IF EXISTS `sys_power`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `sys_power`
(
    `power_id`   char(19) COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限主键',
    `power_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限名称',
    `power_type` char(1) COLLATE utf8mb4_general_ci      DEFAULT NULL COMMENT '权限类型',
    `power_code` char(30) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '权限标识',
    `power_url`  varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限路径',
    `open_type`  char(10) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '打开方式',
    `parent_id`  char(19) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '父类编号',
    `icon`       varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图标',
    `sort`       int(11)                                 DEFAULT NULL COMMENT '排序',
    `enable`     tinyint(1)                              DEFAULT NULL COMMENT '是否开启',
    PRIMARY KEY (`power_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='角色权限、菜单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_power`
--

LOCK TABLES `sys_power` WRITE;
/*!40000 ALTER TABLE `sys_power`
    DISABLE KEYS */;
INSERT INTO `sys_power`
VALUES ('1', '系统管理', '0', '', '', '', '0', 'layui-icon layui-icon-set-fill', 1, 1),
       ('1284020948269268992', '用户列表', '2', 'sys:user:data', '', '', '2', 'layui-icon-username', 0, 1),
       ('1284022967767924736', '用户保存', '2', 'sys:user:add', '', '', '2', 'layui-icon-username', 1, 1),
       ('1310206853057085440', '用户修改', '2', 'sys:user:edit', '', '', '2', 'layui-icon-vercode', 0, 1),
       ('1310208636370288640', '用户删除', '2', 'sys:user:remove', '', '', '2', 'layui-icon-vercode', 0, 1),
       ('1310209696916832256', '角色新增', '2', 'sys:role:add', '', '', '3', 'layui-icon-vercode', 0, 1),
       ('1310209900478988288', '角色删除', '2', 'sys:role:remove', '', '', '3', 'layui-icon-vercode', 0, 1),
       ('1310210054728712192', '角色修改', '2', 'sys:role:edit', '', '', '3', 'layui-icon-vercode', 0, 1),
       ('1310211965188046848', '角色授权', '2', 'sys:role:power', '', '', '3', 'layui-icon-vercode', 1, 1),
       ('1310226416867999744', '权限列表', '2', 'sys:power:data', '', '', '4', 'layui-icon-vercode', 0, 1),
       ('1310226976593674240', '权限新增', '2', 'sys:power:add', '', '', '4', 'layui-icon-vercode', 1, 1),
       ('1310227130998587392', '权限修改', '2', 'sys:power:edit', '', '', '4', 'layui-icon-vercode', 2, 1),
       ('1310227300935008256', '权限删除', '2', 'sys:power:remove', '', '', '4', 'layui-icon-vercode', 3, 1),
       ('1310232350285627392', '操作日志', '2', 'sys:log:operateLog', '', '', '450300705362808832',
        'layui-icon layui-icon-vercode', 0, 1),
       ('1310232462562951168', '登录日志', '2', 'sys:log:loginLog', '', '', '450300705362808832',
        'layui-icon layui-icon-vercode', 1, 1),
       ('1315584471046553600', '部门管理', '1', 'sys:dept:main', '/system/dept/main', '_iframe', '1490508760802656256',
        'layui-icon layui-icon layui-icon layui-icon layui-icon-vercode', 3, 1),
       ('1316558444790022144', '部门新增', '2', 'sys:dept:add', '', '', '1315584471046553600',
        'layui-icon layui-icon-vercode', 0, 1),
       ('1316558556102656000', '部门修改', '2', 'sys:dept:edit', '', '', '1315584471046553600',
        'layui-icon layui-icon-vercode', 1, 1),
       ('1316558685442408448', '部门删除', '2', 'sys:dept:remove', '', '', '1315584471046553600',
        'layui-icon layui-icon-vercode', 3, 1),
       ('1317555660455411712', '部门列表', '2', 'sys:dept:data', '', '', '1315584471046553600',
        'layui-icon layui-icon layui-icon layui-icon-vercode', 2, 1),
       ('1322085079861690368', '用户管理', '1', 'sys:user:main', '/system/user/main', '_iframe', '1',
        'layui-icon layui-icon layui-icon layui-icon layui-icon-rate', 0, 1),
       ('1322085270392143872', '用户列表', '2', 'sys:user:data', '', '', '1322085079861690368',
        'layui-icon layui-icon layui-icon layui-icon-vercode', 0, 1),
       ('1322085393021009920', '用户新增', '2', 'sys:user:add', '', '', '1322085079861690368',
        'layui-icon layui-icon-vercode', 0, 1),
       ('1322085497798918144', '用户修改', '2', 'sys:user:edit', '', '', '1322085079861690368',
        'layui-icon layui-icon layui-icon-vercode', 2, 1),
       ('1322085659766161408', '用户删除', '2', 'sys:user:remove', '', '', '1322085079861690368',
        'layui-icon layui-icon-rate', 3, 1),
       ('1330865171429588992', '在线用户', '1', 'sys:online:main', '/system/online/main', '_iframe', '694203021537574912',
        'layui-icon layui-icon layui-icon-username', 0, 1),
       ('1348562759603716096', '在线列表', '1', 'sys:online:data', '/system/online/data', '_iframe', '1330865171429588992',
        'layui-icon layui-icon-username', 1, 1),
       ('1349016358033031168', '环境监控', '1', 'sys:monitor:main', '/system/monitor/main', '_iframe', '694203021537574912',
        'layui-icon layui-icon-vercode', 9, 1),
       ('1490508760802656256', '废弃井管理', '0', '', '', '', '0', 'layui-icon layui-icon-water', 2, 1),
       ('1490509563256897536', '井信息管理', '1', 'well:info:main', '/well/info/main', '_iframe', '1490508760802656256',
        'layui-icon layui-icon-list', 1, 1),
       ('1490510749032775680', '辖区管理', '1', 'well:region:main', '/well/region/main', '_iframe', '1490508760802656256',
        'layui-icon layui-icon-location', 2, 1),
       ('1490513120299319296', '水文区域管理', '1', 'well:code:main', '/well/code/main', '_iframe', '1490508760802656256',
        'layui-icon layui-icon-flag', 4, 1),
       ('1490518643748896768', '信息汇总', '1', 'well:summary:main', '/well/summary/main', '_iframe', '1490508760802656256',
        'layui-icon layui-icon-template-1', 5, 1),
       ('2', '用户管理', '2', '', '', '_iframe', '1320969572051845120', 'layui-icon layui-icon layui-icon-username', 0, 1),
       ('3', '角色管理', '1', 'sys:role:main', '/system/role/main', '_iframe', '1', 'layui-icon layui-icon-user', 1, 1),
       ('4', '权限管理', '1', 'sys:power:main', '/system/power/main', '_iframe', '1', 'layui-icon layui-icon-vercode', 2,
        1),
       ('442359447487123456', '角色列表', '2', 'sys:role:data', '', '', '3', 'layui-icon layui-icon-rate', 1, 1),
       ('450300705362808832', '行为日志', '1', 'sys:log:main', '/system/log/main', '_iframe', '694203021537574912',
        'layui-icon layui-icon layui-icon layui-icon  layui-icon-chart', 7, 1),
       ('694203021537574912', '系统监控', '0', '', '', '', '0', 'layui-icon  layui-icon-console', 3, 1),
       ('694203311615639552', '接口文档', '1', 'sys:doc:main', '/system/doc/main', '_iframe', '694203021537574912',
        'layui-icon layui-icon layui-icon layui-icon layui-icon  layui-icon-chart', 8, 1),
       ('bbL1FYvrqrG1_Or', '辖区修改', '2', 'well:region:edit', '', '', '1490510749032775680', 'layui-icon', 2, 1),
       ('BiNZjv0KMOSkLnE', '辖区新增', '2', 'well:region:add', '', '', '1490510749032775680', 'layui-icon layui-icon', 1,
        1),
       ('cLFVIa2Dfm0JLf4', '代码删除', '2', 'well:code:remove', '', '', '1490513120299319296', 'layui-icon', 4, 1),
       ('cXWBbXb1XWx46PH', '代码列表', '2', 'well:code:page', '', '', '1490513120299319296', 'layui-icon', 3, 1),
       ('GxIqQGptGWBHbHc', '信息列表', '2', 'well:info:page', '', '', '1490509563256897536', 'layui-icon', 3, 1),
       ('H82xsMz7-qkkNtv', '信息新增', '2', 'well:info:add', '', '', '1490509563256897536', 'layui-icon', 1, 1),
       ('J_QnxCAhHgcRp3X', '辖区删除', '2', 'well:region:remove', '', '', '1490510749032775680', 'layui-icon', 4, 1),
       ('mOdhFMQqs2M-7c-', '信息删除', '2', 'well:info:remove', '', '', '1490509563256897536', 'layui-icon', 4, 1),
       ('OpcWfIJk_2hyhTn', '代码新增', '2', 'well:code:add', '', '', '1490513120299319296', 'layui-icon', 1, 1),
       ('Q6d2RQG59R5hmm5', '辖区列表', '2', 'well:region:page', '', '', '1490510749032775680', 'layui-icon', 3, 1),
       ('U2_ptn_z9l1Xoom', '信息修改', '2', 'well:info:edit', '', '', '1490509563256897536', 'layui-icon', 2, 1),
       ('VqmuQxN82Jijaod', '信息审核', '2', 'well:info:approve', '', '', '1490509563256897536', 'layui-icon', 5, 1),
       ('y5WoXH33iVrgbPm', '数据导出', '2', 'well:summary:export', '', '', '1490518643748896768', 'layui-icon', 1, 1),
       ('_PGdhLxwsvc-qyI', '代码修改', '2', 'well:code:edit', '', '', '1490513120299319296', 'layui-icon', 2, 1);
/*!40000 ALTER TABLE `sys_power`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `sys_role`
(
    `role_id`   char(19) COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色主键',
    `role_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色名称',
    `role_code` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色标识',
    `enable`    char(1) COLLATE utf8mb4_general_ci      DEFAULT NULL COMMENT '是否启用',
    `details`   varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '详情',
    `sort`      int(11)                                 DEFAULT NULL COMMENT '排序',
    PRIMARY KEY (`role_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='用户角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role`
    DISABLE KEYS */;
INSERT INTO `sys_role`
VALUES ('dGb-woDpwKV0Xdc', '审批员', 'approver', '1', '用于系统审批信息的角色', 1),
       ('jfjFGhAlH3TxG4c', '上报员', 'reporter', '1', '用于填报信息的角色', 2),
       ('v9CFkBSfb7QdM0K', 'test', 'test', '1', '', 3);
/*!40000 ALTER TABLE `sys_role`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_power`
--

DROP TABLE IF EXISTS `sys_role_power`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `sys_role_power`
(
    `id`       char(19) COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
    `role_id`  char(19) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色主键',
    `power_id` char(19) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限主键',
    PRIMARY KEY (`id`),
    KEY `sys_power_id_fk` (`power_id`),
    KEY `sys_role_power_sys_role_role_id_fk` (`role_id`),
    CONSTRAINT `sys_power_id_fk` FOREIGN KEY (`power_id`) REFERENCES `sys_power` (`power_id`),
    CONSTRAINT `sys_role_power_sys_role_role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='角色权限关联';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_power`
--

LOCK TABLES `sys_role_power` WRITE;
/*!40000 ALTER TABLE `sys_role_power`
    DISABLE KEYS */;
INSERT INTO `sys_role_power`
VALUES ('-R9zddqq-pKRyXY', 'v9CFkBSfb7QdM0K', '694203021537574912'),
       ('0wVobJ5a2U8EGtH', 'dGb-woDpwKV0Xdc', '1316558444790022144'),
       ('1IjkUT3X0_fv199', 'dGb-woDpwKV0Xdc', 'cXWBbXb1XWx46PH'),
       ('2c8m1jLmXAfbly8', 'dGb-woDpwKV0Xdc', 'Q6d2RQG59R5hmm5'),
       ('2f9EYnOGaGgYHVd', 'jfjFGhAlH3TxG4c', 'GxIqQGptGWBHbHc'),
       ('6outAhWpCV-9t5I', 'jfjFGhAlH3TxG4c', 'cXWBbXb1XWx46PH'),
       ('7z5zBIPKpGiFUea', 'v9CFkBSfb7QdM0K', '1'),
       ('80r4lA0ZuFCns-r', 'dGb-woDpwKV0Xdc', '1490508760802656256'),
       ('9JbgFj3JPvGmw0u', 'jfjFGhAlH3TxG4c', '1315584471046553600'),
       ('EAkF2lJuCojBWDf', 'dGb-woDpwKV0Xdc', 'GxIqQGptGWBHbHc'),
       ('FDhOkEa244n6bgs', 'dGb-woDpwKV0Xdc', '1490518643748896768'),
       ('kAsmXijNSsDwl9K', 'dGb-woDpwKV0Xdc', 'y5WoXH33iVrgbPm'),
       ('MteJTGNl5yHuwIm', 'jfjFGhAlH3TxG4c', '1490508760802656256'),
       ('pjt_j4DQIcaHKnK', 'dGb-woDpwKV0Xdc', 'mOdhFMQqs2M-7c-'),
       ('qirJGpEZhwXtVu8', 'jfjFGhAlH3TxG4c', '1317555660455411712'),
       ('RdyvbEfBBPkU2uU', 'dGb-woDpwKV0Xdc', 'OpcWfIJk_2hyhTn'),
       ('rFHXgwtrzZW4Bts', 'dGb-woDpwKV0Xdc', '1490510749032775680'),
       ('RSLAkI9DE7mGaSr', 'dGb-woDpwKV0Xdc', 'VqmuQxN82Jijaod'),
       ('SSu8-STiDvwZHfG', 'jfjFGhAlH3TxG4c', '1490510749032775680'),
       ('TczN_HHt4mdiEGj', 'dGb-woDpwKV0Xdc', 'BiNZjv0KMOSkLnE'),
       ('Uvit3BRR-2PpdMZ', 'jfjFGhAlH3TxG4c', 'U2_ptn_z9l1Xoom'),
       ('uXEiXqMswfpgGnZ', 'dGb-woDpwKV0Xdc', '1315584471046553600'),
       ('vwbxIFUpbdQUirN', 'dGb-woDpwKV0Xdc', '1317555660455411712'),
       ('wUBIYEAf8Tg0Ofs', 'dGb-woDpwKV0Xdc', '1490509563256897536'),
       ('wvnmOfZZvCLBv8D', 'jfjFGhAlH3TxG4c', '1490513120299319296'),
       ('x_0L6IHZ1VU0gFC', 'jfjFGhAlH3TxG4c', 'Q6d2RQG59R5hmm5'),
       ('ywrBc8Dls_Aaugz', 'jfjFGhAlH3TxG4c', 'H82xsMz7-qkkNtv'),
       ('zFWJ72y61hr21TB', 'dGb-woDpwKV0Xdc', '1490513120299319296'),
       ('zhjdEzzR-6PuJ8W', 'jfjFGhAlH3TxG4c', '1490509563256897536');
/*!40000 ALTER TABLE `sys_role_power`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `sys_user`
(
    `user_id`     char(19) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户主键',
    `username`    char(20) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '账户',
    `password`    char(60) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '密码',
    `status`      tinyint(1)                              DEFAULT NULL COMMENT '状态（是否锁定）',
    `real_name`   char(80) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '姓名',
    `email`       char(20) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '邮箱',
    `sex`         char(1) COLLATE utf8mb4_general_ci      DEFAULT NULL COMMENT '性别',
    `phone`       char(20) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '电话',
    `create_time` datetime                                DEFAULT NULL COMMENT '创建时间',
    `remark`      varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
    `enable`      tinyint(1)                              DEFAULT NULL COMMENT '是否启用',
    `dept_id`     char(19) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '部门主键',
    `last_time`   datetime                                DEFAULT NULL COMMENT '最后一次登录时间',
    `admin`       tinyint(1)                              DEFAULT '0' COMMENT '是否为管理员',
    PRIMARY KEY (`user_id`),
    KEY `sys_user_sys_dept_dept_id_fk` (`dept_id`),
    CONSTRAINT `sys_user_sys_dept_dept_id_fk` FOREIGN KEY (`dept_id`) REFERENCES `sys_dept` (`dept_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='用户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user`
    DISABLE KEYS */;
INSERT INTO `sys_user`
VALUES ('1309861917694623744', 'admin', '$2a$10$./QZvN.xSOci3GulO5m4IuexD7MGpHOZFkGdQDb5RlNBmEJym3tt6', 1, '管理',
        'admin@vuhe.top', '0', '无', '2020-09-26 22:26:32', '被岁月镂空，亦受其雕琢', 1, '1', '2022-05-23 15:25:06', 1),
       ('I81yC03U7N7Kncq', 'ru', '$2a$10$YhmoxLtLD7Oo/4KnRXG6MesKruNijSaXheoRXFW1GdEg9ncXiGdgm', NULL, 'test',
        'test@t.cn', '1', '123456', '2022-05-23 15:26:37', NULL, 1, '1', NULL, 0),
       ('QpW2fm3ZWpLetHU', 'ny', '$2a$10$JIQX582bIq6rJoG2vrt54uCiarjSwzcaFcTo3Xu3EhUfpGPW84wNS', NULL, '南阳上报', 't@t.cn',
        '0', '无', '2022-05-11 13:53:05', NULL, 1, '9FARX0wUC4ctyqe', '2022-05-21 16:36:11', 0),
       ('wKuoWOo6cFqbgDh', 'zz', '$2a$10$LiTT9UFx3ovyvVfn7x/E.OPA.1Ymgo5JMGzG0put8CZ.PhEuf6nQO', NULL, '郑州审批', 't@t.cn',
        '1', '无', '2022-05-11 13:52:19', NULL, 1, '1FgFZUYIeSNXCUR', '2022-05-23 15:32:01', 0);
/*!40000 ALTER TABLE `sys_user`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `sys_user_role`
(
    `id`      char(19) COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
    `user_id` char(19) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户主键',
    `role_id` char(19) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色主键',
    PRIMARY KEY (`id`),
    KEY `sys_user_id_fk` (`user_id`),
    KEY `sys_role_id_fk` (`role_id`),
    CONSTRAINT `sys_role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`),
    CONSTRAINT `sys_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='用户角色关联';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role`
    DISABLE KEYS */;
INSERT INTO `sys_user_role`
VALUES ('Q6tzKYU31mIUW18', 'I81yC03U7N7Kncq', 'dGb-woDpwKV0Xdc'),
       ('qYLVzfkuRPe-Vdc', 'wKuoWOo6cFqbgDh', 'dGb-woDpwKV0Xdc'),
       ('V9hNh-S_UiYopKY', 'QpW2fm3ZWpLetHU', 'jfjFGhAlH3TxG4c');
/*!40000 ALTER TABLE `sys_user_role`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `well_info`
--

DROP TABLE IF EXISTS `well_info`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `well_info`
(
    `id`              varchar(20) NOT NULL COMMENT '主键 id',
    `name`            varchar(200) DEFAULT NULL COMMENT '废弃并名称',
    `origin_code`     varchar(100) DEFAULT NULL COMMENT '原始编号',
    `well_type`       varchar(50)  DEFAULT NULL COMMENT '废弃井类型',
    `region_id`       varchar(20)  DEFAULT NULL COMMENT '废弃井区域 id',
    `street`          varchar(255) DEFAULT NULL COMMENT '乡镇(街道办)',
    `address`         varchar(255) DEFAULT NULL COMMENT '村(街)、门牌号',
    `company`         varchar(255) DEFAULT NULL COMMENT '井所属单位',
    `dig_time`        date         DEFAULT NULL COMMENT '建井时间',
    `contacts`        varchar(255) DEFAULT NULL COMMENT '联系人',
    `phone`           varchar(20)  DEFAULT NULL COMMENT '联系电话',
    `abandon_remark`  text COMMENT '废弃原因',
    `abandon_time`    date         DEFAULT NULL COMMENT '废弃时间',
    `lng1`            int(11)      DEFAULT NULL COMMENT '经度-度',
    `lng2`            int(11)      DEFAULT NULL COMMENT '经度-分',
    `lng3`            int(11)      DEFAULT NULL COMMENT '经度-秒',
    `lat1`            int(11)      DEFAULT NULL COMMENT '纬度-度',
    `lat2`            int(11)      DEFAULT NULL COMMENT '纬度-秒',
    `lat3`            int(11)      DEFAULT NULL COMMENT '纬度-分',
    `informer`        varchar(50)  DEFAULT NULL COMMENT '填表人',
    `investigator`    varchar(50)  DEFAULT NULL COMMENT '排查人',
    `inform_time`     date         DEFAULT NULL COMMENT '填表时间',
    `status`          varchar(20)  DEFAULT NULL COMMENT '信息状态',
    `fill_start_time` date         DEFAULT NULL COMMENT '回填开始时间',
    `fill_end_time`   date         DEFAULT NULL COMMENT '回填结束时间',
    `remark`          text COMMENT '注释',
    PRIMARY KEY (`id`),
    KEY `well_region_id_fk` (`region_id`),
    CONSTRAINT `well_region_id_fk` FOREIGN KEY (`region_id`) REFERENCES `well_region` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='废弃井信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `well_info`
--

LOCK TABLES `well_info` WRITE;
/*!40000 ALTER TABLE `well_info`
    DISABLE KEYS */;
INSERT INTO `well_info`
VALUES ('00909000000201W', 'test', '54', 'KU', '1490588892498231296', 'trrt', 'fff', 'ddd', '2022-02-02', 'ffkk', '332',
        'ff', '2022-02-02', 111, 1, 1, 32, 1, 1, '我', '我', '2022-02-15', 'NotAccepted', '2022-05-18', '2022-05-10', ''),
       ('00909000010201W', '测试2', '009', 'KP', '1490588892498231296', 'test', 'test', 'test', '2022-05-03', 'test', '无',
        '无', '2022-05-01', 111, 0, 0, 32, 0, 0, '测试', '测试', '2022-05-03', 'Reported', NULL, NULL, ''),
       ('00909000020201W', 'test', 'test', 'ZU', '1490588892498231296', 'test', 'test', 'test', '2022-05-04', '测试',
        '123456', '56', '2022-05-07', 1, 1, 1, 1, 1, 1, '特性', '侧记', '2022-05-07', 'Reported', NULL, NULL, ''),
       ('33990000010201W', 'test', 'ee', 'KU', '1493408259543924736', 'fff', 'tet', 'tet', '2022-05-01', 'ter', 'tet',
        'tet', '2022-05-06', 31, 1, 1, 100, 1, 1, '测试', '测试', '2022-05-14', 'Approved', '2022-05-26', '2022-05-27',
        'test');
/*!40000 ALTER TABLE `well_info`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `well_region`
--

DROP TABLE IF EXISTS `well_region`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `well_region`
(
    `id`            varchar(20) NOT NULL COMMENT '主键 id',
    `city`          varchar(100) DEFAULT NULL COMMENT '省辖市（含济源）',
    `county`        varchar(100) DEFAULT NULL COMMENT '区/县',
    `district_code` varchar(50)  DEFAULT NULL COMMENT '行政区划代码',
    `region_code`   varchar(50)  DEFAULT NULL COMMENT '二级水文地址代码',
    `sort`          int(11)      DEFAULT NULL COMMENT '排序码',
    `count`         int(11)      DEFAULT NULL COMMENT '当前区域井数',
    PRIMARY KEY (`id`),
    KEY `well_region_code_id_fk` (`region_code`),
    CONSTRAINT `well_region_code_id_fk` FOREIGN KEY (`region_code`) REFERENCES `region_code` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='废弃井区域信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `well_region`
--

LOCK TABLES `well_region` WRITE;
/*!40000 ALTER TABLE `well_region`
    DISABLE KEYS */;
INSERT INTO `well_region`
VALUES ('1490588892498231296', '郑州', '中原区', '009090', '1490513957209767936', 1, 2),
       ('1493408259543924736', '郑州', '测试', '339900', '1490513957209767936', 2, 1);
/*!40000 ALTER TABLE `well_region`
    ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2022-05-28  7:12:06
