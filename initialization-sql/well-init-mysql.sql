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
    `id`     varchar(20) NOT NULL,
    `code`   varchar(20) DEFAULT NULL,
    `remark` text,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `region_code`
--

LOCK TABLES `region_code` WRITE;
/*!40000 ALTER TABLE `region_code`
    DISABLE KEYS */;
INSERT INTO `region_code`
VALUES ('1490513957209767936', '0201',
        '黄淮海平原水文地质亚区；\n郑州市、开封市、洛阳市（涧西区、西工区、老城区、瀍河回族区、吉利区、洛龙区、伊川县、新安县、偃师市、孟津县、宜阳县）、安阳市、鹤壁市、新乡市、焦作市、濮阳市、许昌市、漯河市、商丘市、信阳市（潢川县、息县、淮滨县、固始县）、周口市、驻马店市、济源示范区'),
       ('1490514266162200576', '0203',
        '伏牛山、大别山 水文地质亚区；\n洛阳市（洛宁县、嵩县、汝阳县、栾川县）、平顶山市、三门峡市、南阳市、信阳市（浉河区、平桥区、光山县、新县、罗山县、商城县）');
/*!40000 ALTER TABLE `region_code`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule_job`
--

DROP TABLE IF EXISTS `schedule_job`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `schedule_job`
(
    `job_id`          char(19) NOT NULL COMMENT '任务id',
    `bean_name`       varchar(200)  DEFAULT NULL COMMENT 'spring bean名称',
    `params`          varchar(2000) DEFAULT NULL COMMENT '参数',
    `cron_expression` varchar(100)  DEFAULT NULL COMMENT 'cron表达式',
    `status`          tinyint(4)    DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
    `remark`          varchar(255)  DEFAULT NULL COMMENT '备注',
    `create_time`     datetime      DEFAULT NULL COMMENT '创建时间',
    `job_name`        varchar(255)  DEFAULT NULL,
    PRIMARY KEY (`job_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='定时任务';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule_job`
--

LOCK TABLES `schedule_job` WRITE;
/*!40000 ALTER TABLE `schedule_job`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `schedule_job`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule_log`
--

DROP TABLE IF EXISTS `schedule_log`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `schedule_log`
(
    `log_id`      char(19)   NOT NULL COMMENT '任务日志id',
    `job_id`      char(19)   NOT NULL COMMENT '任务id',
    `bean_name`   varchar(200)  DEFAULT NULL COMMENT 'spring bean名称',
    `params`      varchar(2000) DEFAULT NULL COMMENT '参数',
    `status`      tinyint(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
    `error`       varchar(2000) DEFAULT NULL COMMENT '失败信息',
    `times`       int(11)    NOT NULL COMMENT '耗时(单位：毫秒)',
    `create_time` datetime      DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`log_id`),
    KEY `job_id` (`job_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='定时任务日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule_log`
--

LOCK TABLES `schedule_log` WRITE;
/*!40000 ALTER TABLE `schedule_log`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `schedule_log`
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
    `dept_id`     varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门名称',
    `parent_id`   varchar(20) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '父级编号',
    `dept_name`   varchar(50) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '部门名称',
    `sort`        int(11)                                 DEFAULT NULL COMMENT '排序',
    `leader`      varchar(50) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '负责人',
    `phone`       varchar(20) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '联系方式',
    `email`       varchar(50) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '邮箱',
    `status`      tinyint(1)                              DEFAULT NULL COMMENT '部门状态',
    `create_by`   varchar(20) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '创建人',
    `create_time` datetime                                DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(20) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '修改人',
    `update_time` datetime                                DEFAULT NULL COMMENT '修改时间',
    `remark`      text COLLATE utf8mb4_general_ci COMMENT '备注',
    `address`     varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '详细地址',
    PRIMARY KEY (`dept_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept`
--

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept`
    DISABLE KEYS */;
INSERT INTO `sys_dept`
VALUES ('1', '0', '济南总公司', 1, '就眠仪式', '15553726531', 'pearadmin@gmail.com', 1, NULL, NULL, NULL, NULL, NULL, '山东济南'),
       ('10', '8', '设计部', 3, '就眠仪式', '15553726531', 'pearadmin@gmail.com', 1, NULL, NULL, NULL, NULL, NULL, '山东济南'),
       ('1316361008259792896', '1316360459930042368', '软件部', 1, '就眠仪式', '15553726531', 'pearadmin@gmail.com', 1, NULL,
        NULL, NULL, NULL, NULL, '山东济南'),
       ('1316361192645591040', '1316360459930042368', '市场部', 1, '就眠仪式', '15553726531', 'pearadmin@gmail.com', 1, NULL,
        NULL, NULL, NULL, NULL, '山东济南'),
       ('1377824449830584320', '3', '财务部', 1, '就眠仪式', '15553726531', '854085467@qq.com', 1, NULL, NULL, NULL, NULL,
        NULL, '山东济南'),
       ('1377825171905183744', '8', '财务部', 1, '就眠仪式', '15553726531', '854085467@qq.com', 1, NULL, NULL, NULL, NULL,
        NULL, '山东济南'),
       ('3', '1', '杭州分公司', 1, '就眠仪式', '15553726531', 'pearadmin@gmail.com', 1, NULL, NULL, NULL, NULL, NULL, '浙江杭州'),
       ('4', '2', '软件部', 2, '就眠仪式', '15553726531', 'pearadmin@gmail.com', 1, NULL, NULL, NULL, NULL, NULL, '山东济南'),
       ('5', '2', '市场部', 2, '就眠仪式', '15553726531', 'pearadmin@gmail.com', 1, NULL, NULL, NULL, NULL, NULL, '山东济南'),
       ('6', '3', '软件部', 3, '就眠仪式', '15553726531', 'pearadmin@gmail.com', 1, NULL, NULL, NULL, NULL, NULL, '浙江杭州'),
       ('7', '3', '设计部', 4, '就眠仪式', '15553726531', 'pearadmin@gmail.com', 1, NULL, NULL, NULL, NULL, NULL, '山东济南'),
       ('8', '1', '深圳分公司', 3, '就眠仪式', '15553726531', 'pearadmin@gmail.com', 1, NULL, NULL, NULL, NULL, NULL, '山东济南'),
       ('9', '8', '软件部', 3, '就眠仪式', '15553726531', 'pearadmin@gmail.com', 1, NULL, NULL, NULL, NULL, NULL, '山东济南');
/*!40000 ALTER TABLE `sys_dept`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_data`
--

DROP TABLE IF EXISTS `sys_dict_data`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `sys_dict_data`
(
    `data_id`     char(19) COLLATE utf8mb4_general_ci NOT NULL COMMENT '标识',
    `data_label`  char(19) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '字典标签',
    `data_value`  char(20) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '字典值',
    `type_code`   char(20) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '所属类型',
    `is_default`  char(1) COLLATE utf8mb4_general_ci      DEFAULT NULL COMMENT '是否默认',
    `update_by`   char(19) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '修改人',
    `update_time` datetime                                DEFAULT NULL COMMENT '修改时间',
    `create_by`   varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
    `create_time` datetime                                DEFAULT NULL COMMENT '创建时间',
    `remark`      varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
    `enable`      tinyint(1)                              DEFAULT NULL COMMENT '是否启用',
    PRIMARY KEY (`data_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_data`
--

LOCK TABLES `sys_dict_data` WRITE;
/*!40000 ALTER TABLE `sys_dict_data`
    DISABLE KEYS */;
INSERT INTO `sys_dict_data`
VALUES ('1', '男', '0', 'system_user_sex', '0', NULL, NULL, NULL, NULL, '描述', 1),
       ('1302833449496739840', '字典名称', '字典值', 'dict_code', '1', NULL, NULL, NULL, NULL, 'aw', 0),
       ('1317401149287956480', '男', 'boy', 'user_sex', NULL, NULL, NULL, NULL, NULL, '男 : body', 0),
       ('1317402976670711808', '女', 'girl', 'user_sex', NULL, NULL, NULL, NULL, NULL, '女 : girl', 0),
       ('1370411072367886336', '公告', 'public', 'sys_notice_type', NULL, NULL, NULL, NULL, NULL, '公告', 0),
       ('1370411179544936448', '私信', 'private', 'sys_notice_type', NULL, NULL, NULL, NULL, NULL, '私信', 0),
       ('1387076909438861312', '短信', 'smmm', 'sys_notice_type', NULL, NULL, NULL, NULL, NULL, NULL, 0),
       ('2', '女', '1', 'system_user_sex', '1', NULL, NULL, NULL, NULL, '描述', 0),
       ('447572898392182784', 'awd', 'awd', 'dict_code', '1', NULL, NULL, NULL, NULL, 'awd', 0);
/*!40000 ALTER TABLE `sys_dict_data`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_type`
--

DROP TABLE IF EXISTS `sys_dict_type`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `sys_dict_type`
(
    `id`          char(19) COLLATE utf8mb4_general_ci NOT NULL COMMENT '标识',
    `type_name`   varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字典类型名称',
    `type_code`   varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字典类型标识',
    `description` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字典类型描述',
    `enable`      tinyint(1)                              DEFAULT NULL COMMENT '是否启用',
    `create_by`   char(19) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '创建人',
    `create_time` datetime                                DEFAULT NULL COMMENT '创建时间',
    `update_by`   char(19) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '修改人',
    `update_time` datetime                                DEFAULT NULL COMMENT '修改时间',
    `remark`      varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_type`
--

LOCK TABLES `sys_dict_type` WRITE;
/*!40000 ALTER TABLE `sys_dict_type`
    DISABLE KEYS */;
INSERT INTO `sys_dict_type`
VALUES ('1317360314219495424', '登录类型', 'sys_notice_type', '登录类型', 0, NULL, NULL, NULL, NULL, NULL),
       ('1317400519127334912', '用户类型', 'user_status', '用户类型', 0, NULL, NULL, NULL, NULL, NULL),
       ('1317400823096934400', '配置类型', 'config_type', '配置类型', 0, NULL, NULL, '1309861917694623744',
        '2022-01-21 15:21:31', NULL),
       ('1370410853110644736', '消息类型', 'sys_notice_type', '消息类型', 0, NULL, NULL, NULL, NULL, NULL),
       ('455184568505470976', '用户性别', 'user_sex', '用户性别', 0, NULL, NULL, NULL, NULL, NULL),
       ('455184935989415936', '全局状态', 'sys_status', '状态描述\n', 0, NULL, NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `sys_dict_type`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_file`
--

DROP TABLE IF EXISTS `sys_file`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `sys_file`
(
    `id`          varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '标识',
    `file_name`   varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件名称',
    `file_desc`   varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件描述',
    `file_path`   varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件路径',
    `file_type`   varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件类型',
    `create_by`   varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
    `create_time` datetime                                DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改人',
    `update_time` datetime                                DEFAULT NULL COMMENT '修改时间',
    `remark`      varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
    `target_date` datetime                                DEFAULT NULL COMMENT '所属时间',
    `file_size`   varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件大小',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_file`
--

LOCK TABLES `sys_file` WRITE;
/*!40000 ALTER TABLE `sys_file`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_file`
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
    `request_method`  varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '请求方法',
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
-- Dumping data for table `sys_logging`
--

LOCK TABLES `sys_logging` WRITE;
/*!40000 ALTER TABLE `sys_logging`
    DISABLE KEYS */;
INSERT INTO `sys_logging`
VALUES ('1493488784229007360', '登录', '/login', 'OTHER', 'POST', '1309861917694623744', 'admin', '/login', '127.0.0.1',
        '', '', '1', '', '2022-02-15 15:34:08', '登录成功', '', 'Safari浏览器', 'Mac', 'LOGIN'),
       ('1493488789866151936', '主页', '/index', 'ADD', 'GET', '1309861917694623744', 'admin', '/index', '127.0.0.1', '',
        '', '1', '', '2022-02-15 15:34:10', '返回 Index 主页视图', '', 'Safari浏览器', 'Mac', 'OPERATE'),
       ('1493490949001576448', '登录', '/login', 'OTHER', 'POST', '1309861917694623744', 'admin', '/login', '127.0.0.1',
        '', '', '1', '', '2022-02-15 15:42:45', '登录成功', '', 'Safari浏览器', 'Mac', 'LOGIN'),
       ('1493490954638721024', '主页', '/index', 'ADD', 'GET', '1309861917694623744', 'admin', '/index', '127.0.0.1', '',
        '', '1', '', '2022-02-15 15:42:46', '返回 Index 主页视图', '', 'Safari浏览器', 'Mac', 'OPERATE'),
       ('1493493572303847424', '登录', '/login', 'OTHER', 'POST', '1309861917694623744', 'admin', '/login', '127.0.0.1',
        '', '', '1', '', '2022-02-15 15:53:10', '登录成功', '', 'Safari浏览器', 'Mac', 'LOGIN'),
       ('1493493577886466048', '主页', '/index', 'ADD', 'GET', '1309861917694623744', 'admin', '/index', '127.0.0.1', '',
        '', '1', '', '2022-02-15 15:53:11', '返回 Index 主页视图', '', 'Safari浏览器', 'Mac', 'OPERATE'),
       ('1493494521021857792', '登录', '/login', 'OTHER', 'POST', '', '未登录用户', '/login', '127.0.0.1', '', '', '0', '',
        '2022-02-15 15:56:56', '验证码错误!', '', 'Safari浏览器', 'Mac', 'LOGIN'),
       ('1493494537161539584', '登录', '/login', 'OTHER', 'POST', '1309861917694623744', 'admin', '/login', '127.0.0.1',
        '', '', '1', '', '2022-02-15 15:57:00', '登录成功', '', 'Safari浏览器', 'Mac', 'LOGIN'),
       ('1493494542375059456', '主页', '/index', 'ADD', 'GET', '1309861917694623744', 'admin', '/index', '127.0.0.1', '',
        '', '1', '', '2022-02-15 15:57:01', '返回 Index 主页视图', '', 'Safari浏览器', 'Mac', 'OPERATE');
/*!40000 ALTER TABLE `sys_logging`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_notice`
--

DROP TABLE IF EXISTS `sys_notice`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `sys_notice`
(
    `id`          char(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '编号',
    `title`       varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标题',
    `content`     text COLLATE utf8mb4_general_ci COMMENT '内容',
    `sender`      char(20) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '发送人',
    `accept`      char(20) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '接收者',
    `type`        char(10) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '类型',
    `create_by`   char(20) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '创建人',
    `create_time` datetime                                DEFAULT NULL COMMENT '创建时间',
    `update_by`   char(20) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '修改人',
    `update_time` datetime                                DEFAULT NULL COMMENT '修改时间',
    `remark`      varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_notice`
--

LOCK TABLES `sys_notice` WRITE;
/*!40000 ALTER TABLE `sys_notice`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_notice`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_power`
--

DROP TABLE IF EXISTS `sys_power`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `sys_power`
(
    `power_id`    char(19) COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限编号',
    `power_name`  varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限名称',
    `power_type`  char(1) COLLATE utf8mb4_general_ci      DEFAULT NULL COMMENT '权限类型',
    `power_code`  char(30) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '权限标识',
    `power_url`   varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限路径',
    `open_type`   char(10) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '打开方式',
    `parent_id`   char(19) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '父类编号',
    `icon`        varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图标',
    `sort`        int(11)                                 DEFAULT NULL COMMENT '排序',
    `create_by`   char(19) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '创建人',
    `create_time` datetime                                DEFAULT NULL COMMENT '创建时间',
    `update_by`   char(19) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '修改人',
    `update_time` datetime                                DEFAULT NULL COMMENT '修改时间',
    `remark`      varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
    `enable`      tinyint(1)                              DEFAULT NULL COMMENT '是否开启',
    PRIMARY KEY (`power_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_power`
--

LOCK TABLES `sys_power` WRITE;
/*!40000 ALTER TABLE `sys_power`
    DISABLE KEYS */;
INSERT INTO `sys_power`
VALUES ('1', '系统管理', '0', '', '', NULL, '0', 'layui-icon layui-icon-set-fill', 1, NULL, NULL, NULL, NULL, NULL, 1),
       ('1284020948269268992', '用户列表', '2', 'sys:user:data', '', '', '2', 'layui-icon-username', 0, NULL, NULL, NULL,
        NULL, NULL, 1),
       ('1284022967767924736', '用户保存', '2', 'sys:user:add', '', '', '2', 'layui-icon-username', 1, NULL, NULL, NULL,
        NULL, NULL, 1),
       ('1304387665067507712', '数据字典', '1', 'sys:dictType:main', '/system/dictType/main', '_iframe', '1',
        'layui-icon layui-icon layui-icon layui-icon layui-icon-flag', 4, NULL, NULL, NULL, NULL, NULL, 1),
       ('1304793451996381184', '文件管理', '1', 'sys:file:main', '/system/file/main', '_iframe', '1',
        'layui-icon layui-icon layui-icon-read', 5, NULL, NULL, NULL, NULL, NULL, 1),
       ('1305870685385523200', '百度一下', '1', '', 'http://www.baidu.com', '0', '474356044148117504', 'layui-icon-search',
        2, NULL, NULL, NULL, NULL, NULL, NULL),
       ('1305875436139446272', '百度一下', '1', 'http://www.baidu.com', 'http://www.baidu.com', '0', '451002662209589248',
        'layui-icon-search', 1, NULL, NULL, NULL, NULL, NULL, 1),
       ('1310206853057085440', '用户修改', '2', 'sys:user:edit', '', '', '2', 'layui-icon-vercode', 0, NULL, NULL, NULL,
        NULL, NULL, 1),
       ('1310208636370288640', '用户删除', '2', 'sys:user:remove', '', '', '2', 'layui-icon-vercode', 0, NULL, NULL, NULL,
        NULL, NULL, 1),
       ('1310209696916832256', '角色新增', '2', 'sys:role:add', '', '', '3', 'layui-icon-vercode', 0, NULL, NULL, NULL,
        NULL, NULL, 1),
       ('1310209900478988288', '角色删除', '2', 'sys:role:remove', '', '', '3', 'layui-icon-vercode', 0, NULL, NULL, NULL,
        NULL, NULL, 1),
       ('1310210054728712192', '角色修改', '2', 'sys:role:edit', '', '', '3', 'layui-icon-vercode', 0, NULL, NULL, NULL,
        NULL, NULL, 1),
       ('1310211965188046848', '角色授权', '2', 'sys:role:power', '', '', '3', 'layui-icon-vercode', 1, NULL, NULL, NULL,
        NULL, NULL, 1),
       ('1310226416867999744', '权限列表', '2', 'sys:power:data', '', '', '4', 'layui-icon-vercode', 0, NULL, NULL, NULL,
        NULL, NULL, 1),
       ('1310226976593674240', '权限新增', '2', 'sys:power:add', '', '', '4', 'layui-icon-vercode', 1, NULL, NULL, NULL,
        NULL, NULL, 1),
       ('1310227130998587392', '权限修改', '2', 'sys:power:edit', '', '', '4', 'layui-icon-vercode', 2, NULL, NULL, NULL,
        NULL, NULL, 1),
       ('1310227300935008256', '权限删除', '2', 'sys:power:remove', '', '', '4', 'layui-icon-vercode', 3, NULL, NULL, NULL,
        NULL, NULL, 1),
       ('1310232350285627392', '操作日志', '2', 'sys:log:operateLog', '', '', '450300705362808832',
        'layui-icon layui-icon-vercode', 0, NULL, NULL, NULL, NULL, NULL, 1),
       ('1310232462562951168', '登录日志', '2', 'sys:log:loginLog', '', '', '450300705362808832',
        'layui-icon layui-icon-vercode', 1, NULL, NULL, NULL, NULL, NULL, 1),
       ('1310243862937075712', '文件列表', '2', 'sys:file:data', '', '', '1304793451996381184', 'layui-icon-vercode', 0,
        NULL, NULL, NULL, NULL, NULL, 1),
       ('1310244103824343040', '文件新增', '2', 'sys:file:add', '', '', '1304793451996381184', 'layui-icon-vercode', 1,
        NULL, NULL, NULL, NULL, NULL, 1),
       ('1310244248884346880', '文件删除', '2', 'sys:file:remove', '', '', '1304793451996381184', 'layui-icon-vercode', 3,
        NULL, NULL, NULL, NULL, NULL, 1),
       ('1310390699333517312', '任务列表', '2', 'sch:job:data', '', '', '442650770626711552', 'layui-icon-vercode', 0, NULL,
        NULL, NULL, NULL, NULL, 1),
       ('1310390994826428416', '任务新增', '2', 'sch:job:add', '', '', '442650770626711552', 'layui-icon-vercode', 0, NULL,
        NULL, NULL, NULL, NULL, 1),
       ('1310391095670079488', '任务修改', '2', 'sch:job:edit', '', '', '442650770626711552', 'layui-icon-vercode', 2, NULL,
        NULL, NULL, NULL, NULL, 1),
       ('1310391707069579264', '任务删除', '2', 'sch:job:remove', '', '', '442650770626711552', 'layui-icon-vercode', 3,
        NULL, NULL, NULL, NULL, NULL, 1),
       ('1310395250908332032', '日志列表', '2', 'sch:log:data', '', '', '442651158935375872', 'layui-icon-vercode', 0, NULL,
        NULL, NULL, NULL, NULL, 1),
       ('1310397832091402240', '任务恢复', '2', 'sch:job:resume', '', '', '442650770626711552', 'layui-icon-vercode', NULL,
        NULL, NULL, NULL, NULL, NULL, 1),
       ('1310398020692475904', '任务停止', '2', 'sch:job:pause', '', '', '442650770626711552', 'layui-icon-vercode', 3,
        NULL, NULL, NULL, NULL, NULL, 1),
       ('1310398158974484480', '任务运行', '2', 'sch:job:run', '', '', '442650770626711552', 'layui-icon-vercode', 4, NULL,
        NULL, NULL, NULL, NULL, 1),
       ('1310402491631796224', '数据类型列表', '2', 'sys:dictType:data', '', '', '1304387665067507712', 'layui-icon-vercode',
        0, NULL, NULL, NULL, NULL, NULL, 1),
       ('1310402688881524736', '数据类型新增', '2', 'sys:dictType:add', '', '', '1304387665067507712', 'layui-icon-vercode',
        1, NULL, NULL, NULL, NULL, NULL, 1),
       ('1310402817776680960', '数据类型修改', '2', 'sys:dictType:edit', '', '', '1304387665067507712', 'layui-icon-vercode',
        3, NULL, NULL, NULL, NULL, NULL, 1),
       ('1310403004406431744', '数据类型删除', '2', 'sys:dictType:remove', '', '', '1304387665067507712',
        'layui-icon-vercode', 3, NULL, NULL, NULL, NULL, NULL, 1),
       ('1310404584291696640', '数据字典视图', '2', 'sys:dictData:main', '', '', '1304387665067507712', 'layui-icon-vercode',
        0, NULL, NULL, NULL, NULL, NULL, 1),
       ('1310404705934901248', '数据字典列表', '2', 'sys:dictData:data', '', '', '1304387665067507712', 'layui-icon-vercode',
        1, NULL, NULL, NULL, NULL, NULL, 1),
       ('1310404831407505408', '数据字典新增', '2', 'sys:dictData:add', '', '', '1304387665067507712', 'layui-icon-vercode',
        5, NULL, NULL, NULL, NULL, NULL, 1),
       ('1310404999599095808', '数据字典删除', '2', 'sys:dictData:remove', '', '', '1304387665067507712',
        'layui-icon-vercode', 6, NULL, NULL, NULL, NULL, NULL, 1),
       ('1310405161587310592', '数据字典修改', '2', 'sys:dictData:edit', '', '', '1304387665067507712', 'layui-icon-vercode',
        0, NULL, NULL, NULL, NULL, NULL, 1),
       ('1313142510486290432', '公告列表', '1', 'sys:notice:data', '/system/notice/data', '0', '1313142171393589248',
        'layui-icon-notice', 1, NULL, NULL, NULL, NULL, NULL, 1),
       ('1313482983558086656', '公告新增', '2', 'sys:notice:add', '', '', '1313142171393589248', 'layui-icon-vercode', 1,
        NULL, NULL, NULL, NULL, NULL, 1),
       ('1313483090852577280', '公告修改', '2', 'sys:notice:edit', '', '', '1313142171393589248', 'layui-icon-vercode', 2,
        NULL, NULL, NULL, NULL, NULL, 1),
       ('1313483189850734592', '公告删除', '2', 'sys:notice:remove', '', '', '1313142171393589248', 'layui-icon-vercode', 3,
        NULL, NULL, NULL, NULL, NULL, 1),
       ('1315584471046553600', '部门管理', '1', 'sys:dept:main', '/system/dept/main', '_iframe', '1490508760802656256',
        'layui-icon layui-icon layui-icon layui-icon layui-icon-vercode', 3, NULL, NULL, '1309861917694623744',
        '2022-02-07 10:21:06', NULL, 1),
       ('1316558444790022144', '部门新增', '2', 'sys:dept:add', '', '', '1315584471046553600',
        'layui-icon layui-icon-vercode', 0, NULL, NULL, NULL, NULL, NULL, 1),
       ('1316558556102656000', '部门修改', '2', 'sys:dept:edit', '', '', '1315584471046553600',
        'layui-icon layui-icon-vercode', 1, NULL, NULL, NULL, NULL, NULL, 1),
       ('1316558685442408448', '部门删除', '2', 'sys:dept:remove', '', '', '1315584471046553600',
        'layui-icon layui-icon-vercode', 3, NULL, NULL, NULL, NULL, NULL, 1),
       ('1317555660455411712', '部门列表', '2', 'sys:dept:data', '', '', '1315584471046553600',
        'layui-icon layui-icon layui-icon layui-icon-vercode', 2, NULL, NULL, NULL, NULL, NULL, 1),
       ('1320969572051845120', '111111', '2', '', '', '', '1284020948269268992', 'layui-icon-login-qq', NULL, NULL,
        NULL, NULL, NULL, NULL, NULL),
       ('1322085079861690368', '用户管理', '1', 'sys:user:main', '/system/user/main', '_iframe', '1',
        'layui-icon layui-icon layui-icon layui-icon layui-icon-rate', 0, NULL, NULL, NULL, NULL, NULL, 1),
       ('1322085270392143872', '用户列表', '2', 'sys:user:data', '', '', '1322085079861690368',
        'layui-icon layui-icon layui-icon layui-icon-vercode', 0, NULL, NULL, NULL, NULL, NULL, 1),
       ('1322085393021009920', '用户新增', '2', 'sys:user:add', '', '', '1322085079861690368',
        'layui-icon layui-icon-vercode', NULL, NULL, NULL, NULL, NULL, NULL, 1),
       ('1322085497798918144', '用户修改', '2', 'sys:user:edit', '', '', '1322085079861690368',
        'layui-icon layui-icon layui-icon-vercode', 2, NULL, NULL, NULL, NULL, NULL, 1),
       ('1322085659766161408', '用户删除', '2', 'sys:user:remove', '', '', '1322085079861690368',
        'layui-icon layui-icon-rate', 3, NULL, NULL, NULL, NULL, NULL, 1),
       ('1329349076189184000', '', '1', '', '', '', '451002662209589248', 'layui-icon', NULL, NULL, NULL, NULL, NULL,
        NULL, 1),
       ('1330865171429588992', '在线用户', '1', 'sys:online:main', '/system/online/main', '_iframe', '694203021537574912',
        'layui-icon layui-icon layui-icon-username', 0, NULL, NULL, NULL, NULL, NULL, 1),
       ('1348562759603716096', '在线列表', '1', 'sys:online:data', '/system/online/data', '_iframe', '1330865171429588992',
        'layui-icon layui-icon-username', 1, NULL, NULL, NULL, NULL, NULL, 1),
       ('1349016358033031168', '环境监控', '1', 'sys:monitor:main', '/system/monitor/main', '_iframe', '694203021537574912',
        'layui-icon layui-icon-vercode', 9, NULL, NULL, NULL, NULL, NULL, 1),
       ('1370412169610395648', '站内消息', '1', 'system:notice:main', '/system/notice/main', '_iframe', '1',
        'layui-icon layui-icon layui-icon layui-icon-set-fill', 8, NULL, NULL, NULL, NULL, NULL, 1),
       ('1370412169610395649', '消息列表', '2', 'system:notice:data', '', NULL, '1370412169610395648',
        'layui-icon layui-icon-set-fill', 1, NULL, NULL, NULL, NULL, NULL, 1),
       ('1370412169610395650', '消息新增', '2', 'system:notice:add', '', NULL, '1370412169610395648',
        'layui-icon layui-icon-set-fill', 1, NULL, NULL, NULL, NULL, NULL, 1),
       ('1370412169610395651', '消息修改', '2', 'system:notice:edit', '', NULL, '1370412169610395648',
        'layui-icon layui-icon-set-fill', 1, NULL, NULL, NULL, NULL, NULL, 1),
       ('1370412169610395652', '消息删除', '2', 'system:notice:remove', '', NULL, '1370412169610395648',
        'layui-icon layui-icon-set-fill', 1, NULL, NULL, NULL, NULL, NULL, 1),
       ('1490508760802656256', '废弃井管理', '0', '', '', '', '0', 'layui-icon layui-icon-water', 2, '1309861917694623744',
        '2022-02-07 10:12:36', '', NULL, '', 1),
       ('1490509563256897536', '井信息管理', '1', 'well:info:main', '/well/info/main', '_iframe', '1490508760802656256',
        'layui-icon layui-icon-list', 1, '1309861917694623744', '2022-02-07 10:15:47', '', NULL, '', 1),
       ('1490510749032775680', '辖区管理', '1', 'well:region:main', '/well/region/main', '_iframe', '1490508760802656256',
        'layui-icon layui-icon-location', 2, '1309861917694623744', '2022-02-07 10:20:30', '', NULL, '', 1),
       ('1490513120299319296', '水文区域管理', '1', 'well:code:main', '/well/code/main', '_iframe', '1490508760802656256',
        'layui-icon layui-icon-flag', 4, '1309861917694623744', '2022-02-07 10:29:55', '', NULL, '', 1),
       ('1490518643748896768', '信息汇总', '1', 'well:summary:main', '/well/summary/main', '_iframe', '1490508760802656256',
        'layui-icon layui-icon-template-1', 5, '1309861917694623744', '2022-02-07 10:51:52', '', NULL, '', 1),
       ('1490526206204837888', '操作日志', '1', 'well:log:main', '/well/log/main', '_iframe', '1490508760802656256',
        'layui-icon layui-icon-survey', 6, '1309861917694623744', '2022-02-07 11:21:55', '', NULL, '', 1),
       ('2', '用户管理', '2', '', '', '_iframe', '1320969572051845120', 'layui-icon layui-icon layui-icon-username', 0,
        NULL, NULL, NULL, NULL, NULL, 1),
       ('3', '角色管理', '1', 'sys:role:main', '/system/role/main', '_iframe', '1', 'layui-icon layui-icon-user', 1, NULL,
        NULL, NULL, NULL, NULL, 1),
       ('4', '权限管理', '1', 'sys:power:main', '/system/power/main', '_iframe', '1', 'layui-icon layui-icon-vercode', 2,
        NULL, NULL, NULL, NULL, NULL, 1),
       ('442359447487123456', '角色列表', '2', 'sys:role:data', '', '', '3', 'layui-icon layui-icon-rate', 1, NULL, NULL,
        NULL, NULL, NULL, 1),
       ('442650770626711552', '定时任务', '1', 'sch:job:main', '/schedule/job/main', '_iframe', '694203021537574912',
        'layui-icon layui-icon layui-icon layui-icon  layui-icon-chat', 0, NULL, NULL, NULL, NULL, NULL, 1),
       ('442651158935375872', '任务日志', '1', 'sch:log:main', '/schedule/log/main', '_iframe', '694203021537574912',
        'layui-icon layui-icon layui-icon  layui-icon-file', 1, NULL, NULL, NULL, NULL, NULL, 1),
       ('450300705362808832', '行为日志', '1', 'sys:log:main', '/system/log/main', '_iframe', '694203021537574912',
        'layui-icon layui-icon layui-icon layui-icon  layui-icon-chart', 7, NULL, NULL, NULL, NULL, NULL, 1),
       ('451002662209589248', '工作空间', '1', '', '', '', '451002662209589248', 'layui-icon layui-icon layui-icon-home', 0,
        NULL, NULL, NULL, NULL, NULL, 1),
       ('451003242072117248', '项目总览', '1', 'process:model:main', '/console', '_iframe', '451002662209589248',
        'layui-icon  layui-icon-component', 0, NULL, NULL, NULL, NULL, NULL, 1),
       ('474356363552755712', '项目介绍', '1', 'home', '/console', '_iframe', '474356044148117504',
        'layui-icon layui-icon-home', 1, NULL, NULL, NULL, NULL, NULL, 0),
       ('694203021537574912', '系统监控', '0', '', '', '', '0', 'layui-icon  layui-icon-console', 3, NULL, NULL, NULL, NULL,
        NULL, 1),
       ('694203311615639552', '接口文档', '1', 'sys:doc:main', '/system/doc/main', '_iframe', '694203021537574912',
        'layui-icon layui-icon layui-icon layui-icon layui-icon  layui-icon-chart', 8, NULL, NULL,
        '1309861917694623744', '2022-02-07 10:31:03', NULL, 1);
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
    `role_id`     char(19) COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色编号',
    `role_name`   varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色名称',
    `role_code`   varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色标识',
    `enable`      char(1) COLLATE utf8mb4_general_ci      DEFAULT NULL COMMENT '是否启用',
    `create_time` datetime                                DEFAULT NULL COMMENT '创建时间',
    `create_by`   char(19) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '创建人',
    `update_time` datetime                                DEFAULT NULL COMMENT '修改时间',
    `update_by`   char(19) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '修改人',
    `remark`      varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
    `details`     varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '详情',
    `sort`        int(11)                                 DEFAULT NULL COMMENT '排序',
    PRIMARY KEY (`role_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role`
    DISABLE KEYS */;
INSERT INTO `sys_role`
VALUES ('1309851245195821056', '超级管理员', 'admin', '1', NULL, NULL, NULL, NULL, NULL, '超级管理员', 1),
       ('1313761100243664896', '普通管理员', 'manager', '1', NULL, NULL, NULL, NULL, NULL, '普通管理员', 2),
       ('1356112133691015168', '应急管理员', 'users', '1', NULL, NULL, NULL, NULL, NULL, '应急管理员', 2);
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
    `id`          char(19) COLLATE utf8mb4_general_ci NOT NULL,
    `role_id`     char(19) COLLATE utf8mb4_general_ci     DEFAULT NULL,
    `power_id`    char(19) COLLATE utf8mb4_general_ci     DEFAULT NULL,
    `create_by`   char(19) COLLATE utf8mb4_general_ci     DEFAULT NULL,
    `create_time` datetime                                DEFAULT NULL,
    `update_by`   char(19) COLLATE utf8mb4_general_ci     DEFAULT NULL,
    `update_time` datetime                                DEFAULT NULL,
    `remark`      varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_power`
--

LOCK TABLES `sys_role_power` WRITE;
/*!40000 ALTER TABLE `sys_role_power`
    DISABLE KEYS */;
INSERT INTO `sys_role_power`
VALUES ('1284022485632679936', '3', '474356044148117504', NULL, NULL, NULL, NULL, NULL),
       ('1284022485632679937', '3', '474356363552755712', NULL, NULL, NULL, NULL, NULL),
       ('1284022485632679938', '3', '1', NULL, NULL, NULL, NULL, NULL),
       ('1284022485632679939', '3', '3', NULL, NULL, NULL, NULL, NULL),
       ('1284022485632679940', '3', '442359447487123456', NULL, NULL, NULL, NULL, NULL),
       ('1284022485632679941', '3', '4', NULL, NULL, NULL, NULL, NULL),
       ('1284022485632679942', '3', '442722702474743808', NULL, NULL, NULL, NULL, NULL),
       ('1284022485632679943', '3', '2', NULL, NULL, NULL, NULL, NULL),
       ('1284022485632679944', '3', '1284020948269268992', NULL, NULL, NULL, NULL, NULL),
       ('1284022485632679947', '3', '694203021537574912', NULL, NULL, NULL, NULL, NULL),
       ('1284022485632679948', '3', '450300705362808832', NULL, NULL, NULL, NULL, NULL),
       ('1284022485632679949', '3', '442520236248403968', NULL, NULL, NULL, NULL, NULL),
       ('1284022485632679950', '3', '694203311615639552', NULL, NULL, NULL, NULL, NULL),
       ('1284022485632679951', '3', '442650387514789888', NULL, NULL, NULL, NULL, NULL),
       ('1284022485632679952', '3', '442650770626711552', NULL, NULL, NULL, NULL, NULL),
       ('1284022485632679953', '3', '442651158935375872', NULL, NULL, NULL, NULL, NULL),
       ('1284022485632679954', '3', '451002662209589248', NULL, NULL, NULL, NULL, NULL),
       ('1284022485632679955', '3', '451003242072117248', NULL, NULL, NULL, NULL, NULL),
       ('1305379650364506112', '2', '474356044148117504', NULL, NULL, NULL, NULL, NULL),
       ('1305379650368700416', '2', '474356363552755712', NULL, NULL, NULL, NULL, NULL),
       ('1305379650368700417', '2', '2', NULL, NULL, NULL, NULL, NULL),
       ('1305379650368700418', '2', '1284020948269268992', NULL, NULL, NULL, NULL, NULL),
       ('1305379650368700419', '2', '450300705362808832', NULL, NULL, NULL, NULL, NULL),
       ('1305379650368700422', '2', '694203021537574912', NULL, NULL, NULL, NULL, NULL),
       ('1305379650368700423', '2', '442520236248403968', NULL, NULL, NULL, NULL, NULL),
       ('1305379650368700424', '2', '694203311615639552', NULL, NULL, NULL, NULL, NULL),
       ('1305379650368700425', '2', '442650387514789888', NULL, NULL, NULL, NULL, NULL),
       ('1305379650368700426', '2', '442650770626711552', NULL, NULL, NULL, NULL, NULL),
       ('1305379650368700427', '2', '442651158935375872', NULL, NULL, NULL, NULL, NULL),
       ('1305379650368700428', '2', '451002662209589248', NULL, NULL, NULL, NULL, NULL),
       ('1305379650368700429', '2', '451003242072117248', NULL, NULL, NULL, NULL, NULL),
       ('1308571532737380352', '1', '451002662209589248', NULL, NULL, NULL, NULL, NULL),
       ('1308571532737380353', '1', '451003242072117248', NULL, NULL, NULL, NULL, NULL),
       ('1308571532737380354', '1', '1305875436139446272', NULL, NULL, NULL, NULL, NULL),
       ('1308571532737380355', '1', '1', NULL, NULL, NULL, NULL, NULL),
       ('1308571532737380356', '1', '2', NULL, NULL, NULL, NULL, NULL),
       ('1308571532737380357', '1', '1284020948269268992', NULL, NULL, NULL, NULL, NULL),
       ('1308571532737380358', '1', '1284022967767924736', NULL, NULL, NULL, NULL, NULL),
       ('1308571532737380359', '1', '3', NULL, NULL, NULL, NULL, NULL),
       ('1308571532737380360', '1', '442359447487123456', NULL, NULL, NULL, NULL, NULL),
       ('1308571532737380361', '1', '4', NULL, NULL, NULL, NULL, NULL),
       ('1308571532737380362', '1', '1304387665067507712', NULL, NULL, NULL, NULL, NULL),
       ('1308571532737380363', '1', '450300705362808832', NULL, NULL, NULL, NULL, NULL),
       ('1308571532737380364', '1', '1304793451996381184', NULL, NULL, NULL, NULL, NULL),
       ('1308571532737380366', '1', '442650387514789888', NULL, NULL, NULL, NULL, NULL),
       ('1308571532737380367', '1', '442650770626711552', NULL, NULL, NULL, NULL, NULL),
       ('1308571532737380368', '1', '442651158935375872', NULL, NULL, NULL, NULL, NULL),
       ('1308571532737380369', '1', '694203021537574912', NULL, NULL, NULL, NULL, NULL),
       ('1308571532737380370', '1', '442520236248403968', NULL, NULL, NULL, NULL, NULL),
       ('1308571532737380371', '1', '694203311615639552', NULL, NULL, NULL, NULL, NULL),
       ('1313147486356897792', '1310215420371795968', '451002662209589248', NULL, NULL, NULL, NULL, NULL),
       ('1313147486356897793', '1310215420371795968', '1', NULL, NULL, NULL, NULL, NULL),
       ('1313147486356897794', '1310215420371795968', '2', NULL, NULL, NULL, NULL, NULL),
       ('1313147486356897795', '1310215420371795968', '3', NULL, NULL, NULL, NULL, NULL),
       ('1313147486356897796', '1310215420371795968', '4', NULL, NULL, NULL, NULL, NULL),
       ('1313147486356897797', '1310215420371795968', '1304387665067507712', NULL, NULL, NULL, NULL, NULL),
       ('1313147486356897798', '1310215420371795968', '450300705362808832', NULL, NULL, NULL, NULL, NULL),
       ('1313147486356897799', '1310215420371795968', '1304793451996381184', NULL, NULL, NULL, NULL, NULL),
       ('1313147486356897801', '1310215420371795968', '1313142171393589248', NULL, NULL, NULL, NULL, NULL),
       ('1313147486356897802', '1310215420371795968', '1313142510486290432', NULL, NULL, NULL, NULL, NULL),
       ('1313147486356897803', '1310215420371795968', '442650387514789888', NULL, NULL, NULL, NULL, NULL),
       ('1313147486356897804', '1310215420371795968', '694203021537574912', NULL, NULL, NULL, NULL, NULL),
       ('1320969221462556672', '1320969145759563776', '451002662209589248', NULL, NULL, NULL, NULL, NULL),
       ('1320969221462556673', '1320969145759563776', '451003242072117248', NULL, NULL, NULL, NULL, NULL),
       ('1320969221462556674', '1320969145759563776', '1305875436139446272', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778112', '1309851245195821056', '1', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778113', '1309851245195821056', '1322085079861690368', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778114', '1309851245195821056', '1322085393021009920', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778115', '1309851245195821056', '1322085270392143872', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778116', '1309851245195821056', '1322085497798918144', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778117', '1309851245195821056', '1322085659766161408', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778118', '1309851245195821056', '3', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778119', '1309851245195821056', '1310209696916832256', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778120', '1309851245195821056', '1310209900478988288', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778121', '1309851245195821056', '1310210054728712192', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778122', '1309851245195821056', '442359447487123456', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778123', '1309851245195821056', '1310211965188046848', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778124', '1309851245195821056', '4', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778125', '1309851245195821056', '1310226416867999744', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778126', '1309851245195821056', '1310226976593674240', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778127', '1309851245195821056', '1310227130998587392', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778128', '1309851245195821056', '1310227300935008256', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778129', '1309851245195821056', '1315584471046553600', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778130', '1309851245195821056', '1316558444790022144', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778131', '1309851245195821056', '1316558556102656000', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778132', '1309851245195821056', '1317555660455411712', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778133', '1309851245195821056', '1316558685442408448', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778134', '1309851245195821056', '1304387665067507712', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778135', '1309851245195821056', '1310405161587310592', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778136', '1309851245195821056', '1310402491631796224', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778137', '1309851245195821056', '1310404584291696640', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778138', '1309851245195821056', '1310404705934901248', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778139', '1309851245195821056', '1310402688881524736', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778140', '1309851245195821056', '1310402817776680960', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778141', '1309851245195821056', '1310403004406431744', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778142', '1309851245195821056', '1310404831407505408', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778143', '1309851245195821056', '1310404999599095808', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778144', '1309851245195821056', '1304793451996381184', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778145', '1309851245195821056', '1310243862937075712', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778146', '1309851245195821056', '1310244103824343040', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778147', '1309851245195821056', '1310244248884346880', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778159', '1309851245195821056', '694203021537574912', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778160', '1309851245195821056', '1330865171429588992', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778161', '1309851245195821056', '1348562759603716096', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778162', '1309851245195821056', '442650770626711552', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778163', '1309851245195821056', '1310397832091402240', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778164', '1309851245195821056', '1310390699333517312', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778165', '1309851245195821056', '1310390994826428416', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778166', '1309851245195821056', '1310391095670079488', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778167', '1309851245195821056', '1310391707069579264', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778168', '1309851245195821056', '1310398020692475904', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778169', '1309851245195821056', '1310398158974484480', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778170', '1309851245195821056', '442651158935375872', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778171', '1309851245195821056', '1310395250908332032', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778172', '1309851245195821056', '450300705362808832', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778173', '1309851245195821056', '1310232350285627392', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778174', '1309851245195821056', '1310232462562951168', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778175', '1309851245195821056', '1349016358033031168', NULL, NULL, NULL, NULL, NULL),
       ('1355962953458778179', '1309851245195821056', '694203311615639552', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712128', '1313761100243664896', '1', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712129', '1313761100243664896', '1322085079861690368', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712130', '1313761100243664896', '1322085393021009920', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712131', '1313761100243664896', '1322085270392143872', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712132', '1313761100243664896', '1322085497798918144', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712133', '1313761100243664896', '1322085659766161408', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712134', '1313761100243664896', '3', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712135', '1313761100243664896', '1310209696916832256', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712136', '1313761100243664896', '1310209900478988288', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712137', '1313761100243664896', '1310210054728712192', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712138', '1313761100243664896', '442359447487123456', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712139', '1313761100243664896', '1310211965188046848', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712140', '1313761100243664896', '4', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712141', '1313761100243664896', '1310226416867999744', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712142', '1313761100243664896', '1310226976593674240', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712143', '1313761100243664896', '1310227130998587392', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712144', '1313761100243664896', '1310227300935008256', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712145', '1313761100243664896', '1315584471046553600', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712146', '1313761100243664896', '1316558444790022144', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712147', '1313761100243664896', '1316558556102656000', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712148', '1313761100243664896', '1317555660455411712', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712149', '1313761100243664896', '1316558685442408448', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712150', '1313761100243664896', '1304387665067507712', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712151', '1313761100243664896', '1310402491631796224', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712152', '1313761100243664896', '1310404584291696640', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712153', '1313761100243664896', '1310405161587310592', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712154', '1313761100243664896', '1310402688881524736', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712155', '1313761100243664896', '1310404705934901248', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712156', '1313761100243664896', '1310402817776680960', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712157', '1313761100243664896', '1310403004406431744', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712158', '1313761100243664896', '1310404831407505408', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712159', '1313761100243664896', '1310404999599095808', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712160', '1313761100243664896', '1304793451996381184', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712161', '1313761100243664896', '1310243862937075712', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712162', '1313761100243664896', '1310244103824343040', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712163', '1313761100243664896', '1310244248884346880', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712176', '1313761100243664896', '694203021537574912', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712177', '1313761100243664896', '1330865171429588992', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712178', '1313761100243664896', '442650770626711552', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712179', '1313761100243664896', '442651158935375872', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712180', '1313761100243664896', '450300705362808832', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712181', '1313761100243664896', '1349016358033031168', NULL, NULL, NULL, NULL, NULL),
       ('1370974927745712185', '1313761100243664896', '694203311615639552', NULL, NULL, NULL, NULL, NULL),
       ('1417395897527959552', '1356112133691015168', '694203021537574912', NULL, NULL, NULL, NULL, NULL),
       ('1417395897527959553', '1356112133691015168', '442650770626711552', NULL, NULL, NULL, NULL, NULL),
       ('1417395897527959554', '1356112133691015168', '1310397832091402240', NULL, NULL, NULL, NULL, NULL),
       ('1417395897527959555', '1356112133691015168', '1310390699333517312', NULL, NULL, NULL, NULL, NULL),
       ('1417395897527959556', '1356112133691015168', '1310390994826428416', NULL, NULL, NULL, NULL, NULL),
       ('1417395897527959557', '1356112133691015168', '1310391095670079488', NULL, NULL, NULL, NULL, NULL),
       ('1417395897527959558', '1356112133691015168', '1310391707069579264', NULL, NULL, NULL, NULL, NULL),
       ('1417395897527959559', '1356112133691015168', '1310398020692475904', NULL, NULL, NULL, NULL, NULL),
       ('1417395897527959560', '1356112133691015168', '1310398158974484480', NULL, NULL, NULL, NULL, NULL),
       ('1417395897527959561', '1356112133691015168', '442651158935375872', NULL, NULL, NULL, NULL, NULL),
       ('1417395897527959562', '1356112133691015168', '1310395250908332032', NULL, NULL, NULL, NULL, NULL),
       ('442062615250866176', '693913251020275712', '1', NULL, NULL, NULL, NULL, NULL),
       ('442062615250866177', '693913251020275712', '2', NULL, NULL, NULL, NULL, NULL),
       ('442062615250866178', '693913251020275712', '3', NULL, NULL, NULL, NULL, NULL),
       ('442062615250866179', '693913251020275712', '4', NULL, NULL, NULL, NULL, NULL);
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
    `user_id`     char(19) COLLATE utf8mb4_general_ci NOT NULL COMMENT '编号',
    `username`    char(20) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '账户',
    `password`    char(60) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '密码',
    `salt`        char(10) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '姓名',
    `status`      tinyint(1)                              DEFAULT NULL COMMENT '状态（是否锁定）',
    `real_name`   char(8) COLLATE utf8mb4_general_ci      DEFAULT NULL COMMENT '姓名',
    `email`       char(20) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '邮箱',
    `avatar`      varchar(30) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '头像',
    `sex`         char(1) COLLATE utf8mb4_general_ci      DEFAULT NULL COMMENT '性别',
    `phone`       char(11) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '电话',
    `create_time` datetime                                DEFAULT NULL COMMENT '创建时间',
    `create_by`   char(20) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '创建人',
    `update_time` datetime                                DEFAULT NULL COMMENT '修改时间',
    `update_by`   char(20) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '修改人',
    `remark`      varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
    `enable`      tinyint(1)                              DEFAULT NULL COMMENT '是否启用',
    `login`       tinyint(1)                              DEFAULT NULL COMMENT '是否登录',
    `dept_id`     char(19) COLLATE utf8mb4_general_ci     DEFAULT NULL COMMENT '部门编号',
    `last_time`   datetime                                DEFAULT NULL COMMENT '最后一次登录时间',
    `admin`       tinyint(1)                              DEFAULT '0' COMMENT '是否为管理员',
    PRIMARY KEY (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user`
    DISABLE KEYS */;
INSERT INTO `sys_user`
VALUES ('1306230031168569344', 'feng', '$2a$10$jjf2h8Cx2lkFMKy3NY9pguADYAMewyPr2IJw8YAI5zSH2/0R/9Kra', NULL, 1, '风筝',
        'feng@gmail.com', NULL, '0', '15553726531', '2000-02-09 00:00:00', NULL, NULL, NULL, '被岁月镂空，亦受其雕琢', 1, NULL,
        '1', NULL, 0),
       ('1309861917694623744', 'admin', '$2a$10$./QZvN.xSOci3GulO5m4IuexD7MGpHOZFkGdQDb5RlNBmEJym3tt6', NULL, 1, '管理',
        'Jmys1992@qq.com', '1485430306889531392', '0', '15553726531', '2020-09-26 22:26:32', NULL,
        '2022-02-15 15:57:00', '1309861917694623744', '被岁月镂空，亦受其雕琢', 1, NULL, '1', '2022-02-15 15:57:00', 1),
       ('1310409555649232897', 'ruhua', '$2a$10$pkvLdCLdFp2sXZpmK34wveekbWvHinW2ldBnic4SqjiKO8jK4Etka', NULL, 1, '如花',
        'ruhua@gmail.com', NULL, '0', '15553726531', '2020-09-28 10:42:39', NULL, NULL, NULL, NULL, 1, NULL, '1', NULL,
        0),
       ('1349016976730619905', 'mwj', '$2a$10$mD0pnwOGjmOKihboidaTveUdrqcDYoluzfCOA0Ho87iwr9PKrDA6i', NULL, 1, '风筝', '',
        NULL, '1', '666666666', '2021-01-12 23:34:45', NULL, NULL, NULL, NULL, 1, NULL, '6', '2021-01-12 23:35:12', 0),
       ('1349021166525743105', 'xiana', '$2a$10$6VuyGmiEbIix/gPDU8oe3O7DZSxGVByjXCHQGtyEMoRAt74M/daee', NULL, 1, '夏娜',
        'xiana@gmail.com', NULL, '0', '15553726531', '2021-01-12 23:51:24', NULL, NULL, NULL, NULL, 1, NULL, '1', NULL,
        0),
       ('1355966975355912193', 'sanman', '$2a$10$AD3QnQMRhYY7RUDHd1EEL.KHaDW8/S66SsESwh.9ta8bLiUXrZcJe', NULL, 1, '散漫',
        'sanman@gmail.com', NULL, '0', '15553726531', '2021-02-01 03:51:34', NULL, NULL, NULL, NULL, 1, NULL, '1', NULL,
        0);
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
    `id`      char(19) COLLATE utf8mb4_general_ci NOT NULL COMMENT '标识',
    `user_id` char(19) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户编号',
    `role_id` char(19) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色编号',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role`
    DISABLE KEYS */;
INSERT INTO `sys_user_role`
VALUES ('1302196622322565120', '1302196622007992320', '1'),
       ('1304443027040763904', '1304443026482921472', '1'),
       ('1304443027044958208', '1304443026482921472', '2'),
       ('1304443027044958209', '1304443026482921472', '3'),
       ('1304443307404820480', '1304443306888921088', '1'),
       ('1304443307404820481', '1304443306888921088', '2'),
       ('1305359805342285824', '1305359804906078208', ''),
       ('1305359807724650496', '1305359807296831488', ''),
       ('1305390235135246336', '1305390234694844416', ''),
       ('1306229860422647808', '1306229859755753472', '1'),
       ('1306229892144168960', '1306229891624075264', '1'),
       ('1306243520893288448', '1306243520482246656', ''),
       ('1308074663896678400', '1308074663313670144', '1'),
       ('1308074663896678401', '1308074663313670144', '1306230258952830976'),
       ('1308074663896678402', '1308074663313670144', '2'),
       ('1308075167091523584', '1308075166433017856', '1'),
       ('1308075167091523585', '1308075166433017856', '1306230258952830976'),
       ('1308075241188098048', '1308074939114323968', '1'),
       ('1308075241188098049', '1308074939114323968', '1306230258952830976'),
       ('1308075407685189632', '1308075407114764288', '1'),
       ('1308075407685189633', '1308075407114764288', '1306230258952830976'),
       ('1308075638158000128', '1308075637621129216', '1306230258952830976'),
       ('1308328954523811840', '1308328954045661184', '1'),
       ('1308328954523811841', '1308328954045661184', '1306230258952830976'),
       ('1308328954523811842', '1308328954045661184', '2'),
       ('1308571264494862336', '1308076162903179264', '1306230258952830976'),
       ('1309445423668133888', '1309444883659882496', '1306230258952830976'),
       ('1309445423668133889', '1309444883659882496', '1309121036125470720'),
       ('1309445423668133890', '1309444883659882496', '2'),
       ('1309752526945386496', '1', '1306230258952830976'),
       ('1309752526945386497', '1', '1309121036125470720'),
       ('1309752526945386498', '1', '2'),
       ('1309860016655695872', '1309860016043327488', '1309851245195821056'),
       ('1309860554432577536', '1309860553891512320', '1309851245195821056'),
       ('1309861324494209024', '1309861323898617856', '1309851245195821056'),
       ('1309861325593116672', '1309861324909445120', '1309851245195821056'),
       ('1310080380040118272', '1310080379331280896', '1306230258952830976'),
       ('1310080380589572096', '1310080379935260672', '1306230258952830976'),
       ('1310080718918909952', '1310080718256209920', '1306230258952830976'),
       ('1310080719917154304', '1310080719208316928', '1306230258952830976'),
       ('1310082314557980672', '1310082313954000896', '1306230258952830976'),
       ('1310082315195514880', '1310082314545397760', '1306230258952830976'),
       ('1310083089153654784', '1310083088511926272', '1309121036125470720'),
       ('1310083089828937728', '1310083089216569344', '1309121036125470720'),
       ('1310083324709961728', '1310083324110176256', '1306230258952830976'),
       ('1310208453033066496', '1310208452424892416', '1309121036125470720'),
       ('1310209026096627712', '1310209025576534016', '1306230258952830976'),
       ('1310209026096627713', '1310209025576534016', '1309121036125470720'),
       ('1310381721815875584', '1306229381332467712', '1309851245195821056'),
       ('1310424875067768832', '1310421836906889217', '1310421428759166976'),
       ('1314015448013996032', '1304491590080790528', '1309851245195821056'),
       ('1314410103465574400', '1314410059245027329', '1309851245195821056'),
       ('1314416691479838720', '1314416690875858945', ''),
       ('1316251185065230336', '1306230031168569344', '1309851245195821056'),
       ('1316275764227735552', '1316275763711836161', '1309851245195821056'),
       ('1316275764227735553', '1316275763711836161', '1313761100243664896'),
       ('1316275899439513600', '1315827004456566785', '1309851245195821056'),
       ('1316275899439513601', '1315827004456566785', '1313761100243664896'),
       ('1316275930657718272', '1315829324519047169', '1309851245195821056'),
       ('1316276059032780800', '1310409555649232897', ''),
       ('1316410619078901760', '1306229606205882368', '1309851245195821056'),
       ('1316410619078901761', '1306229606205882368', '1313761100243664896'),
       ('1316410619078901762', '1306229606205882368', '1316407534105395200'),
       ('1316410619078901763', '1306229606205882368', '1316408008376320000'),
       ('1318205966671413248', '1318205965996130305', ''),
       ('1320899195875360768', '1320899195225243649', ''),
       ('1329795580615983104', '1329795579919728641', '1309851245195821056'),
       ('1329795580615983105', '1329795579919728641', '1313761100243664896'),
       ('1329795614484987904', '1329795613730013185', '1309851245195821056'),
       ('1329795688124383232', '1329795687465877505', '1313761100243664896'),
       ('1329795704863850496', '1329795703882383361', '1313761100243664896'),
       ('1329795716930863104', '1329795716255580161', '1313761100243664896'),
       ('1329795741211688960', '1329795740536406017', '1309851245195821056'),
       ('1349021014649995264', '1349016976730619905', '1313761100243664896'),
       ('1349021167326855168', '1349021166525743105', '1309851245195821056'),
       ('1349021167326855169', '1349021166525743105', '1313761100243664896'),
       ('1355967256000987136', '1355966975355912193', '1309851245195821056'),
       ('1355967256000987137', '1355966975355912193', '1313761100243664896'),
       ('1360858458609418240', '1309861917694623744', '1309851245195821056'),
       ('1360858458609418241', '1309861917694623744', '1313761100243664896'),
       ('442110794142978048', NULL, '1'),
       ('442110794142978049', NULL, '2'),
       ('442110794142978050', NULL, '3'),
       ('442114944884936704', '442114944884936704', '1'),
       ('442114944884936705', '442114944884936704', '2'),
       ('442114944884936706', '442114944884936704', '3'),
       ('442114944884936707', '442114944884936704', '693913251020275712'),
       ('442114944884936708', '442114944884936704', '693949793801601024'),
       ('442114944884936709', '442114944884936704', '694106517393113088'),
       ('442127724396548096', '3', '1'),
       ('442127724396548097', '3', '2'),
       ('442127724396548098', '3', '3'),
       ('445004989551742976', '442492965651353600', '1'),
       ('445004989551742977', '442492965651353600', '2'),
       ('445005010271604736', '444226209941950464', '1'),
       ('445005010271604737', '444226209941950464', '2'),
       ('445005010271604738', '444226209941950464', '3'),
       ('447196043407396864', '447196042723725312', '1'),
       ('447196043407396865', '447196042723725312', '2'),
       ('447197132043194368', '447197131518906368', '1'),
       ('447197773046091776', '447197772274339840', '1'),
       ('447200144400715776', '447199996320813056', '1'),
       ('447200144400715777', '447199996320813056', '2'),
       ('449248198469488640', '449248198058446848', '3'),
       ('463926002653990912', '463926002318446592', '3'),
       ('463926371165540352', '442488661347536896', '3');
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
    PRIMARY KEY (`id`)
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
VALUES ('00909000000201W', 'test', '54', 'ZO', '1490588892498231296', 'trrt', 'fff', 'ddd', '2022-02-02', 'ffkk', '332',
        'ff', '2022-02-02', 111, 1, 1, 32, 1, 1, '我', '我', '2022-02-15', 'Reported', NULL, NULL),
       ('1', '1', '1', 'KU', '1490588892498231296', '1', '1', '1', '2022-02-07', '1', '11', '11', '2022-02-07', 1, 2, 3,
        4, 5, 6, 'fd', 'fd', '2022-02-07', 'Approved', '2022-02-05', '2022-02-10');
/*!40000 ALTER TABLE `well_info`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `well_log`
--

DROP TABLE IF EXISTS `well_log`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `well_log`
(
    `id`       varchar(20) NOT NULL COMMENT '主键 id',
    `well_id`  varchar(20) DEFAULT NULL COMMENT '井信息 id',
    `log_type` varchar(30) DEFAULT NULL COMMENT '更新类型',
    `username` varchar(50) DEFAULT NULL COMMENT '更新人名称',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='动态更新日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `well_log`
--

LOCK TABLES `well_log` WRITE;
/*!40000 ALTER TABLE `well_log`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `well_log`
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
    PRIMARY KEY (`id`)
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
VALUES ('1490588892498231296', '郑州', '中原区', '009090', '1490513957209767936', 1, 0),
       ('1493408259543924736', '郑州', '测试', '339900', '1490513957209767936', 2, 0);
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

-- Dump completed on 2022-02-15 17:01:56
