/*
 Navicat MySQL Data Transfer

 Source Server         : 本地mysql
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : localhost:3306
 Source Schema         : project

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 04/11/2022 16:20:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_login_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_login_log`;
CREATE TABLE `tb_login_log`  (
  `id` bigint NOT NULL,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '填写的用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '填写的密码',
  `message` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '错误信息',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '登录状态，0：成功，1：失败',
  `ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'ip',
  `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '浏览器',
  `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作系统',
  `create_time` datetime NULL DEFAULT NULL COMMENT '登录时间',
  `location` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_login_log
-- ----------------------------

-- ----------------------------
-- Table structure for tb_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_menu`;
CREATE TABLE `tb_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单的描述，如 首页',
  `path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单的路径，如 /',
  `component` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单的vue路径，如 /layout',
  `icon` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '饿了么图标名 如 el-icon-xiaolian',
  `order_num` tinyint(1) NULL DEFAULT 0 COMMENT '菜单的显示优先级，越小越靠前显示，0 ~ 9',
  `menu_type` tinyint(1) NULL DEFAULT 0 COMMENT '菜单类型：0：目录 or 1：菜单',
  `is_hidden` tinyint(1) NULL DEFAULT 0 COMMENT '是否隐藏',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '所属菜单ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1569327626948227075 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_menu
-- ----------------------------
INSERT INTO `tb_menu` VALUES (1, '首页', '/', '/home/index', 'icon-zhuye', 0, 1, 0, 0, '2022-09-07 22:03:48', '2022-09-07 22:03:49');
INSERT INTO `tb_menu` VALUES (2, '系统', '/system', 'Layout', 'icon-shezhi', 1, 0, 0, 0, '2022-09-07 22:14:25', '2022-09-27 10:25:13');
INSERT INTO `tb_menu` VALUES (3, '个人中心', '/settings', '/setting/Setting', 'icon-iconfontgerenzhongxin1', 2, 1, 0, 0, '2022-09-07 22:17:10', '2022-09-07 22:17:11');
INSERT INTO `tb_menu` VALUES (4, '用户管理', '/user', '/system/User', 'icon-biaoqing_xiao_o', 0, 1, 0, 2, '2022-09-07 22:20:38', '2022-09-27 10:25:25');
INSERT INTO `tb_menu` VALUES (5, '角色管理', '/role', '/system/Role', 'icon-denglu', 1, 1, 0, 2, '2022-09-07 22:21:20', '2022-09-07 22:21:20');
INSERT INTO `tb_menu` VALUES (6, '菜单管理', '/menu', '/system/Menu', 'icon-fenlei1', 1, 1, 0, 2, '2022-09-07 22:22:12', '2022-09-07 22:22:12');
INSERT INTO `tb_menu` VALUES (7, '日志', '/log', 'Layout', 'icon-fuzhi', 2, 0, 0, 2, '2022-09-07 22:23:09', '2022-09-12 20:29:55');
INSERT INTO `tb_menu` VALUES (8, '操作日志', '/optLog', '/log/OptLog', 'icon-line-gitpullrequestgitlaquqingqiu', 0, 1, 0, 7, '2022-09-07 22:25:06', '2022-09-07 22:25:06');
INSERT INTO `tb_menu` VALUES (9, '登录日志', '/loginLog', '/log/LoginLog', 'icon-tuichubianji', 1, 1, 0, 7, '2022-09-07 22:25:40', '2022-09-07 22:25:40');
INSERT INTO `tb_menu` VALUES (10, '在线用户', '/onlines', '/system/OnlineUser', 'icon-haoyou', 0, 1, 0, 2, '2022-09-27 12:05:13', '2022-09-27 12:05:13');
INSERT INTO `tb_menu` VALUES (11, '角色分配', '/role/:roleId/users', '/system/UserAssignment', 'icon-haoyou', 0, 1, 1, 2, '2022-09-17 12:50:06', '2022-09-17 12:50:06');
INSERT INTO `tb_menu` VALUES (12, '接口管理', '/resource', '/system/Resource', 'icon-ziyuan', 1, 1, 0, 2, '2022-09-30 09:54:02', '2022-09-30 09:54:02');

-- ----------------------------
-- Table structure for tb_menu_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_menu_role`;
CREATE TABLE `tb_menu_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  `role_id` bigint NOT NULL COMMENT '用户角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1583304627589550092 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_menu_role
-- ----------------------------
INSERT INTO `tb_menu_role` VALUES (1, 1, -1);
INSERT INTO `tb_menu_role` VALUES (3, 3, -1);
INSERT INTO `tb_menu_role` VALUES (5, 5, -1);
INSERT INTO `tb_menu_role` VALUES (6, 6, -1);
INSERT INTO `tb_menu_role` VALUES (8, 8, -1);
INSERT INTO `tb_menu_role` VALUES (9, 9, -1);
INSERT INTO `tb_menu_role` VALUES (1569302282048409602, 7, -1);
INSERT INTO `tb_menu_role` VALUES (1574585924236623873, 2, -1);
INSERT INTO `tb_menu_role` VALUES (1574585971250577410, 4, -1);
INSERT INTO `tb_menu_role` VALUES (1574586264625365003, 10, -1);
INSERT INTO `tb_menu_role` VALUES (1588445176323706882, 1, 0);
INSERT INTO `tb_menu_role` VALUES (1588445176323706883, 2, 0);
INSERT INTO `tb_menu_role` VALUES (1588445176323706884, 4, 0);
INSERT INTO `tb_menu_role` VALUES (1588445176323706885, 5, 0);
INSERT INTO `tb_menu_role` VALUES (1588445176323706886, 6, 0);
INSERT INTO `tb_menu_role` VALUES (1588445176323706887, 7, 0);
INSERT INTO `tb_menu_role` VALUES (1588445176323706888, 8, 0);
INSERT INTO `tb_menu_role` VALUES (1588445176323706889, 9, 0);
INSERT INTO `tb_menu_role` VALUES (1588445176323706890, 10, 0);
INSERT INTO `tb_menu_role` VALUES (1588445176323706891, 11, 0);
INSERT INTO `tb_menu_role` VALUES (1588445176323706892, 12, 0);
INSERT INTO `tb_menu_role` VALUES (1588445176323706893, 3, 0);
INSERT INTO `tb_menu_role` VALUES (1588445808803778562, 1, 1);
INSERT INTO `tb_menu_role` VALUES (1588445808803778563, 2, 1);
INSERT INTO `tb_menu_role` VALUES (1588445808803778564, 4, 1);
INSERT INTO `tb_menu_role` VALUES (1588445808803778565, 5, 1);
INSERT INTO `tb_menu_role` VALUES (1588445808803778566, 6, 1);
INSERT INTO `tb_menu_role` VALUES (1588445808803778567, 7, 1);
INSERT INTO `tb_menu_role` VALUES (1588445808803778568, 8, 1);
INSERT INTO `tb_menu_role` VALUES (1588445808803778569, 9, 1);
INSERT INTO `tb_menu_role` VALUES (1588445808803778570, 10, 1);
INSERT INTO `tb_menu_role` VALUES (1588445808803778571, 11, 1);
INSERT INTO `tb_menu_role` VALUES (1588445808803778572, 12, 1);
INSERT INTO `tb_menu_role` VALUES (1588445808803778573, 3, 1);

-- ----------------------------
-- Table structure for tb_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_operation_log`;
CREATE TABLE `tb_operation_log`  (
  `id` bigint NOT NULL,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `module` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '所属模块',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求地址',
  `method` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求方式， 如 POST',
  `des` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作描述',
  `params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求参数',
  `type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作类型，如 新增',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '请求状态,0:失败, 1:成功',
  `ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作人IP',
  `result` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作结果',
  `message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '错误信息',
  `user_id` bigint NULL DEFAULT NULL COMMENT '操作人ID',
  `handler` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '处理方法',
  `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '浏览器',
  `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作系统',
  `location` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作地区',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_operation_log
-- ----------------------------

-- ----------------------------
-- Table structure for tb_resource
-- ----------------------------
DROP TABLE IF EXISTS `tb_resource`;
CREATE TABLE `tb_resource`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `resource_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作名，如查询用户',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求资源路径，如/user',
  `method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求方式',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '资源所属模块ID',
  `is_anonymous` tinyint(1) NULL DEFAULT 0 COMMENT '是否可放行匿名请求，0：不放行，1：放行',
  `resource_type` tinyint NULL DEFAULT 0 COMMENT '资源类型：0:模块,1:接口',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1576133437523537922 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_resource
-- ----------------------------
INSERT INTO `tb_resource` VALUES (3, '网站信息模块', NULL, NULL, 0, 0, 0, '2022-09-05 15:03:55', '2022-09-05 15:03:54');
INSERT INTO `tb_resource` VALUES (4, '查看网站首页', '/', 'GET', 3, 1, 1, '2022-09-30 12:51:14', '2022-09-05 15:04:35');
INSERT INTO `tb_resource` VALUES (5, '查看后台信息', '/admin', 'GET', 3, 0, 1, '2022-09-05 15:04:56', '2022-09-05 15:04:57');
INSERT INTO `tb_resource` VALUES (6, '查看关于我的信息', '/about', 'GET', 17, 0, 1, '2022-09-05 16:00:07', '2022-09-05 16:00:06');
INSERT INTO `tb_resource` VALUES (7, '修改关于我的信息', '/admin/about', 'PUT', 17, 0, 1, '2022-09-05 16:00:41', '2022-09-05 16:00:41');
INSERT INTO `tb_resource` VALUES (8, '验证登录状态', '/validToken', 'POST', 17, 1, 1, '2022-09-06 11:08:01', '2022-09-06 11:08:00');
INSERT INTO `tb_resource` VALUES (9, '查看可访问路由', '/admin/user/menus', 'GET', 16, 0, 1, '2022-09-08 19:47:08', '2022-09-08 19:47:07');
INSERT INTO `tb_resource` VALUES (10, '查看后台路由信息', '/admin/menus', 'GET', 16, 0, 1, '2022-09-11 08:45:38', '2022-09-11 08:45:37');
INSERT INTO `tb_resource` VALUES (11, '删除菜单', '/admin/menu/*', 'DELETE', 16, 0, 1, '2022-09-11 21:31:51', '2022-09-11 21:31:50');
INSERT INTO `tb_resource` VALUES (12, '用户登录', '/login', 'POST', 17, 1, 1, '2022-09-12 11:28:15', '2022-09-12 11:28:15');
INSERT INTO `tb_resource` VALUES (13, '获取角色标签集合', '/admin/roles/labels', 'GET', 18, 0, 1, '2022-09-12 11:59:26', '2022-09-12 11:59:26');
INSERT INTO `tb_resource` VALUES (14, '查询菜单的角色集合', '/admin/menu/*/roles', 'GET', 16, 0, 1, '2022-09-12 15:52:27', '2022-09-12 15:52:26');
INSERT INTO `tb_resource` VALUES (15, '添加或更新菜单', '/admin/menu', 'POST', 16, 0, 1, '2022-09-12 17:52:21', '2022-09-12 17:52:20');
INSERT INTO `tb_resource` VALUES (16, '菜单模块', NULL, NULL, 0, 0, 0, '2022-09-30 14:12:20', '2022-09-15 18:48:27');
INSERT INTO `tb_resource` VALUES (17, '用户模块', NULL, NULL, 0, 0, 0, '2022-09-15 18:49:00', '2022-09-15 18:48:59');
INSERT INTO `tb_resource` VALUES (18, '角色模块', NULL, NULL, 0, 0, 0, '2022-09-15 18:49:56', '2022-09-15 18:49:56');
INSERT INTO `tb_resource` VALUES (19, '获取后台角色集合', '/admin/roles', 'GET', 18, 0, 1, '2022-09-15 18:55:49', '2022-09-15 18:55:49');
INSERT INTO `tb_resource` VALUES (20, '更新角色禁用状态', '/admin/role/disable', 'PUT', 18, 0, 1, '2022-09-15 21:41:31', '2022-09-15 21:41:31');
INSERT INTO `tb_resource` VALUES (21, '获取角色与菜单关联', '/admin/role/menus/*', 'GET', 18, 0, 1, '2022-09-16 18:43:18', '2022-09-16 18:43:18');
INSERT INTO `tb_resource` VALUES (22, '获取角色与资源关联', '/admin/role/resources/*', 'GET', 18, 0, 1, '2022-09-16 21:17:44', '2022-09-16 21:17:44');
INSERT INTO `tb_resource` VALUES (23, '删除角色授权的用户', '/admin/role/assignment/*', 'DELETE', 18, 0, 1, '2022-09-19 12:05:19', '2022-09-19 12:05:19');
INSERT INTO `tb_resource` VALUES (24, '获取授权的角色', '/admin/role/assignment', 'GET', 18, 0, 1, '2022-09-19 12:07:44', '2022-09-19 12:07:43');
INSERT INTO `tb_resource` VALUES (25, '新增或修改角色，包括其可访资源/菜单', '/admin/role', 'POST', 18, 0, 1, '2022-09-19 12:09:03', '2022-09-19 12:09:02');
INSERT INTO `tb_resource` VALUES (26, '查询未授权用户', '/admin/role/unassignment', 'GET', 18, 0, 1, '2022-09-19 13:12:43', '2022-09-19 13:12:42');
INSERT INTO `tb_resource` VALUES (27, '新增角色授权用户', '/admin/role/assignment/*', 'POST', 18, 0, 1, '2022-09-19 13:58:35', '2022-09-19 13:58:35');
INSERT INTO `tb_resource` VALUES (28, '获取后台用户集合', '/admin/users', 'GET', 17, 0, 1, '2022-09-19 20:10:14', '2022-09-19 20:10:14');
INSERT INTO `tb_resource` VALUES (29, '获取该用户的角色信息', '/admin/user/*/role', 'GET', 18, 0, 1, '2022-09-19 22:25:58', '2022-09-19 22:25:57');
INSERT INTO `tb_resource` VALUES (30, '获取在线用户列表', '/admin/online/users', 'GET', 17, 0, 1, '2022-09-27 12:02:59', '2022-09-27 12:02:59');
INSERT INTO `tb_resource` VALUES (31, '批量踢出在线用户', '/admin/online/users', 'DELETE', 17, 0, 1, '2022-09-27 12:33:36', '2022-09-27 12:33:35');
INSERT INTO `tb_resource` VALUES (32, '日志模块', '', NULL, 0, 0, 0, '2022-09-29 10:53:18', '2022-09-29 10:53:18');
INSERT INTO `tb_resource` VALUES (33, '查询操作日志', '/admin/log/operation', 'GET', 32, 0, 1, '2022-09-29 10:53:35', '2022-09-29 10:53:34');
INSERT INTO `tb_resource` VALUES (34, '添加或删除用户', '/admin/user', 'POST', 17, 0, 1, '2022-09-29 10:55:56', '2022-09-29 10:55:56');
INSERT INTO `tb_resource` VALUES (35, '批量删除用户', '/admin/user', 'DELETE', 17, 0, 1, '2022-09-29 11:13:41', '2022-09-29 11:13:41');
INSERT INTO `tb_resource` VALUES (36, '批量删除操作日志', '/admin/log/operation', 'DELETE', 32, 0, 1, '2022-09-29 18:46:36', '2022-09-29 18:46:36');
INSERT INTO `tb_resource` VALUES (37, '查询登录日志', '/admin/log/login', 'GET', 32, 0, 1, '2022-09-30 08:55:27', '2022-09-30 08:55:27');
INSERT INTO `tb_resource` VALUES (38, '批量删除登录日志', '/admin/log/login', 'DELETE', 32, 0, 1, '2022-09-30 08:56:16', '2022-09-30 08:56:16');
INSERT INTO `tb_resource` VALUES (39, '资源模块', NULL, NULL, 0, 0, 0, '2022-09-30 09:48:24', '2022-09-30 09:48:24');
INSERT INTO `tb_resource` VALUES (40, '获取资源树列表', '/admin/resource/tree', 'GET', 39, 0, 1, '2022-09-30 09:48:24', '2022-09-30 09:48:24');
INSERT INTO `tb_resource` VALUES (41, '获取资源列表', '/admin/resource', 'GET', 39, 0, 1, '2022-09-30 09:48:24', '2022-09-30 09:48:24');
INSERT INTO `tb_resource` VALUES (42, '更新资源匿名状态', '/admin/resource/anonymous/*', 'UPDATE', 39, 0, 1, '2022-09-30 11:01:58', '2022-09-30 11:01:57');
INSERT INTO `tb_resource` VALUES (43, '删除资源', '/admin/resource/*', 'DELETE', 39, 0, 1, '2022-09-30 11:18:12', '2022-09-30 11:18:11');
INSERT INTO `tb_resource` VALUES (44, '添加或更新资源', '/admin/resource', 'POST', 39, 0, 1, '2022-09-30 17:09:14', '2022-09-30 17:09:14');
INSERT INTO `tb_resource` VALUES (45, '上传模块', NULL, NULL, 0, 0, 0, '2022-10-01 11:02:17', '2022-10-01 11:02:17');
INSERT INTO `tb_resource` VALUES (46, '上传图片', '/upload/photo/user/avatar', 'POST', 45, 0, 1, '2022-10-01 15:11:46', '2022-10-01 11:03:54');
INSERT INTO `tb_resource` VALUES (47, '查询特定资源的角色', '/resource/*/roles', 'GET', 39, 0, 1, '2022-10-01 11:56:27', '2022-10-01 11:56:28');
INSERT INTO `tb_resource` VALUES (1576133437523537921, '修改后台用户信息', '/admin/userinfo', 'PUT', 17, 0, 1, '2022-10-01 16:54:29', '2022-10-01 16:54:29');
INSERT INTO `tb_resource` VALUES (1576133437523537922, '修改用户密码', '/admin/user/password', 'PUT', 17, 0, 1, '2022-10-01 19:00:21', '2022-10-01 19:00:21');

-- ----------------------------
-- Table structure for tb_resource_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_resource_role`;
CREATE TABLE `tb_resource_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `resource_id` bigint NOT NULL COMMENT '资源ID',
  `role_id` bigint NOT NULL COMMENT '用户角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1583278164417003550 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_resource_role
-- ----------------------------
INSERT INTO `tb_resource_role` VALUES (1, 3, -1);
INSERT INTO `tb_resource_role` VALUES (2, 4, -1);
INSERT INTO `tb_resource_role` VALUES (3, 5, -1);
INSERT INTO `tb_resource_role` VALUES (4, 6, -1);
INSERT INTO `tb_resource_role` VALUES (5, 7, -1);
INSERT INTO `tb_resource_role` VALUES (8, 8, -1);
INSERT INTO `tb_resource_role` VALUES (9, 9, -1);
INSERT INTO `tb_resource_role` VALUES (11, 10, -1);
INSERT INTO `tb_resource_role` VALUES (13, 11, -1);
INSERT INTO `tb_resource_role` VALUES (14, 13, -1);
INSERT INTO `tb_resource_role` VALUES (16, 12, -1);
INSERT INTO `tb_resource_role` VALUES (18, 15, -1);
INSERT INTO `tb_resource_role` VALUES (19, 19, -1);
INSERT INTO `tb_resource_role` VALUES (21, 20, -1);
INSERT INTO `tb_resource_role` VALUES (22, 21, -1);
INSERT INTO `tb_resource_role` VALUES (1570950226707251208, 14, -1);
INSERT INTO `tb_resource_role` VALUES (1570950226707251214, 23, -1);
INSERT INTO `tb_resource_role` VALUES (1570950226707251215, 24, -1);
INSERT INTO `tb_resource_role` VALUES (1570950226707251217, 25, -1);
INSERT INTO `tb_resource_role` VALUES (1570950226707251218, 27, -1);
INSERT INTO `tb_resource_role` VALUES (1570950226707251219, 26, -1);
INSERT INTO `tb_resource_role` VALUES (1570950226707251221, 28, -1);
INSERT INTO `tb_resource_role` VALUES (1570950226707251223, 29, -1);
INSERT INTO `tb_resource_role` VALUES (1574586467944251414, 22, -1);
INSERT INTO `tb_resource_role` VALUES (1574586467944251415, 30, -1);
INSERT INTO `tb_resource_role` VALUES (1574586467944251417, 31, -1);
INSERT INTO `tb_resource_role` VALUES (1574586467944251418, 33, -1);
INSERT INTO `tb_resource_role` VALUES (1574586467944251420, 34, -1);
INSERT INTO `tb_resource_role` VALUES (1574586467944251421, 36, -1);
INSERT INTO `tb_resource_role` VALUES (1574586467944251422, 37, -1);
INSERT INTO `tb_resource_role` VALUES (1574586467944251424, 38, -1);
INSERT INTO `tb_resource_role` VALUES (1574586467944251425, 40, -1);
INSERT INTO `tb_resource_role` VALUES (1574586467944251427, 41, -1);
INSERT INTO `tb_resource_role` VALUES (1574586467944251429, 42, -1);
INSERT INTO `tb_resource_role` VALUES (1574586467944251430, 43, -1);
INSERT INTO `tb_resource_role` VALUES (1574586467944251431, 44, -1);
INSERT INTO `tb_resource_role` VALUES (1574586467944251433, 47, -1);
INSERT INTO `tb_resource_role` VALUES (1576107587403051010, 46, -1);
INSERT INTO `tb_resource_role` VALUES (1576133437573869569, 1576133437523537921, -1);
INSERT INTO `tb_resource_role` VALUES (1588445509213032449, 3, 0);
INSERT INTO `tb_resource_role` VALUES (1588445509213032450, 4, 0);
INSERT INTO `tb_resource_role` VALUES (1588445509213032451, 5, 0);
INSERT INTO `tb_resource_role` VALUES (1588445509213032452, 16, 0);
INSERT INTO `tb_resource_role` VALUES (1588445509213032453, 9, 0);
INSERT INTO `tb_resource_role` VALUES (1588445509213032454, 10, 0);
INSERT INTO `tb_resource_role` VALUES (1588445509213032455, 14, 0);
INSERT INTO `tb_resource_role` VALUES (1588445509213032456, 17, 0);
INSERT INTO `tb_resource_role` VALUES (1588445509213032457, 6, 0);
INSERT INTO `tb_resource_role` VALUES (1588445509213032458, 8, 0);
INSERT INTO `tb_resource_role` VALUES (1588445509213032459, 12, 0);
INSERT INTO `tb_resource_role` VALUES (1588445509213032460, 28, 0);
INSERT INTO `tb_resource_role` VALUES (1588445509213032461, 1576133437523537921, 0);
INSERT INTO `tb_resource_role` VALUES (1588445509213032462, 1576133437523537922, 0);
INSERT INTO `tb_resource_role` VALUES (1588445509213032463, 18, 0);
INSERT INTO `tb_resource_role` VALUES (1588445509213032464, 13, 0);
INSERT INTO `tb_resource_role` VALUES (1588445509213032465, 19, 0);
INSERT INTO `tb_resource_role` VALUES (1588445509213032466, 21, 0);
INSERT INTO `tb_resource_role` VALUES (1588445509213032467, 22, 0);
INSERT INTO `tb_resource_role` VALUES (1588445509213032468, 24, 0);
INSERT INTO `tb_resource_role` VALUES (1588445509213032469, 26, 0);
INSERT INTO `tb_resource_role` VALUES (1588445509213032470, 29, 0);
INSERT INTO `tb_resource_role` VALUES (1588445509213032471, 32, 0);
INSERT INTO `tb_resource_role` VALUES (1588445509213032472, 33, 0);
INSERT INTO `tb_resource_role` VALUES (1588445509213032473, 37, 0);
INSERT INTO `tb_resource_role` VALUES (1588445509213032474, 39, 0);
INSERT INTO `tb_resource_role` VALUES (1588445509213032475, 40, 0);
INSERT INTO `tb_resource_role` VALUES (1588445509213032476, 41, 0);
INSERT INTO `tb_resource_role` VALUES (1588445509213032477, 47, 0);
INSERT INTO `tb_resource_role` VALUES (1588445509213032478, 45, 0);
INSERT INTO `tb_resource_role` VALUES (1588445509213032479, 46, 0);
INSERT INTO `tb_resource_role` VALUES (1588445793842696193, 3, 1);
INSERT INTO `tb_resource_role` VALUES (1588445793905610754, 4, 1);
INSERT INTO `tb_resource_role` VALUES (1588445793905610755, 5, 1);
INSERT INTO `tb_resource_role` VALUES (1588445793905610756, 16, 1);
INSERT INTO `tb_resource_role` VALUES (1588445793905610757, 9, 1);
INSERT INTO `tb_resource_role` VALUES (1588445793905610758, 10, 1);
INSERT INTO `tb_resource_role` VALUES (1588445793905610759, 14, 1);
INSERT INTO `tb_resource_role` VALUES (1588445793905610760, 17, 1);
INSERT INTO `tb_resource_role` VALUES (1588445793905610761, 6, 1);
INSERT INTO `tb_resource_role` VALUES (1588445793905610762, 8, 1);
INSERT INTO `tb_resource_role` VALUES (1588445793905610763, 12, 1);
INSERT INTO `tb_resource_role` VALUES (1588445793905610764, 28, 1);
INSERT INTO `tb_resource_role` VALUES (1588445793905610765, 30, 1);
INSERT INTO `tb_resource_role` VALUES (1588445793905610766, 18, 1);
INSERT INTO `tb_resource_role` VALUES (1588445793905610767, 13, 1);
INSERT INTO `tb_resource_role` VALUES (1588445793905610768, 19, 1);
INSERT INTO `tb_resource_role` VALUES (1588445793905610769, 21, 1);
INSERT INTO `tb_resource_role` VALUES (1588445793905610770, 22, 1);
INSERT INTO `tb_resource_role` VALUES (1588445793905610771, 24, 1);
INSERT INTO `tb_resource_role` VALUES (1588445793905610772, 26, 1);
INSERT INTO `tb_resource_role` VALUES (1588445793905610773, 29, 1);
INSERT INTO `tb_resource_role` VALUES (1588445793905610774, 32, 1);
INSERT INTO `tb_resource_role` VALUES (1588445793905610775, 33, 1);
INSERT INTO `tb_resource_role` VALUES (1588445793905610776, 37, 1);
INSERT INTO `tb_resource_role` VALUES (1588445793905610777, 39, 1);
INSERT INTO `tb_resource_role` VALUES (1588445793905610778, 40, 1);
INSERT INTO `tb_resource_role` VALUES (1588445793905610779, 41, 1);
INSERT INTO `tb_resource_role` VALUES (1588445793905610780, 47, 1);

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色的名字',
  `role_label` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色的标签,Security有关的',
  `is_disabled` tinyint(1) NULL DEFAULT 0 COMMENT '角色被禁用',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1576110802479681539 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES (-1, '管理员', 'admin', 0, '2022-08-31 16:06:55', '2022-08-31 16:06:56');
INSERT INTO `tb_role` VALUES (0, '普通用户', 'user', 0, '2022-08-31 16:07:11', '2022-11-04 16:18:16');
INSERT INTO `tb_role` VALUES (1, '测试角色', 'test', 0, '2022-09-17 09:22:29', '2022-11-04 16:19:27');

-- ----------------------------
-- Table structure for tb_user_auth
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_auth`;
CREATE TABLE `tb_user_auth`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '认证用户ID',
  `user_info_id` bigint NULL DEFAULT NULL,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名（登录账号名）',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户密码',
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '绑定邮箱',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  `is_disabled` tinyint(1) NULL DEFAULT 0,
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `tb_user_auth_username_uindex`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_auth
-- ----------------------------
INSERT INTO `tb_user_auth` VALUES (-1, -1, 'admin', '$2a$10$EukevBvrfQMBfn3M/Uz6Nu2uqEAqKsKMm3wtnI96xw5rXJ69C0r76', '', 0, 0, '2022-09-03 19:32:22', '2022-10-01 19:07:38');
INSERT INTO `tb_user_auth` VALUES (0, 0, '普通用户', '$2a$10$uOcHiSbXwj6sytSgZhA.8uDsRT.0bqOXGMQHYumESgDJIvwOiQ8zK', '', 0, 0, '2022-10-21 10:03:39', '2022-11-04 16:16:45');
INSERT INTO `tb_user_auth` VALUES (10, 1, 'test', '$2a$10$A8.Fhfj/9LCQXWzu7sV1Me1kzT8x7nVVVjrwoD236pq86Gap3UvwG', '', 0, 0, '2022-10-09 23:27:19', '2022-10-09 23:28:07');

-- ----------------------------
-- Table structure for tb_user_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_info`;
CREATE TABLE `tb_user_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nickname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `avatar` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'https://gcore.jsdelivr.net/gh/Shinyoki/images_repository/blog_images/user.png' COMMENT '用户头像',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '所用操作浏览器',
  `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '所用操作系统',
  `ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录IP',
  `location` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录地址',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `tb_user_info_nickname_uindex`(`nickname`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1584801082724622338 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_info
-- ----------------------------
INSERT INTO `tb_user_info` VALUES (-1, '管理员', 'https://gcore.jsdelivr.net/gh/Shinyoki/images_repository/blog_images/user.png', '2022-09-03 20:01:11', '2022-11-04 16:15:04', 'Chrome', 'Windows 10 or Windows Server 2016', '10.0.0.2', '');
INSERT INTO `tb_user_info` VALUES (0, '普通用户', 'https://gcore.jsdelivr.net/gh/Shinyoki/images_repository/blog_images/user.png', '2022-10-21 10:03:39', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tb_user_info` VALUES (1, '测试用户', 'https://gcore.jsdelivr.net/gh/Shinyoki/images_repository/blog_images/user.png', '2022-10-09 23:27:19', '2022-10-21 11:55:18', 'Chrome', 'Windows 10 or Windows Server 2016', '10.0.0.2', NULL);

-- ----------------------------
-- Table structure for tb_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_role`;
CREATE TABLE `tb_user_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '用户角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `tb_user_role_user_id_uindex`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1583277805510410244 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_role
-- ----------------------------
INSERT INTO `tb_user_role` VALUES (1, -1, -1);
INSERT INTO `tb_user_role` VALUES (1579131600966541313, 10, 1570946257658322946);
INSERT INTO `tb_user_role` VALUES (1583277805510410243, 11, 0);
INSERT INTO `tb_user_role` VALUES (1588445126784782337, 0, 0);

SET FOREIGN_KEY_CHECKS = 1;
