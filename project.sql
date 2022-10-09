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

 Date: 09/10/2022 23:29:13
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
INSERT INTO `tb_login_log` VALUES (1575503951119556610, 'admin', '1234567', '登录成功', 1, '127.0.0.1', 'Unknown', 'Unknown', '2022-09-29 23:13:08', NULL);
INSERT INTO `tb_login_log` VALUES (1575637425453277185, 'admin', '1234567', '登录成功', 1, '10.0.0.2', 'Chrome', 'Windows 10 or Windows Server 2016', '2022-09-30 08:03:31', '内网IP');
INSERT INTO `tb_login_log` VALUES (1575638352788725762, 'admin', '12345672', '用户名或密码错误！', 0, '127.0.0.1', 'Unknown', 'Unknown', '2022-09-30 08:07:12', '内网IP');
INSERT INTO `tb_login_log` VALUES (1575638357398265857, 'admin', '12345672', '用户名或密码错误！', 0, '127.0.0.1', 'Unknown', 'Unknown', '2022-09-30 08:07:13', '内网IP');
INSERT INTO `tb_login_log` VALUES (1575638360720154626, 'admin', '12345672', '用户名或密码错误！', 0, '127.0.0.1', 'Unknown', 'Unknown', '2022-09-30 08:07:14', '内网IP');
INSERT INTO `tb_login_log` VALUES (1575638363605835778, 'admin', '12345672', '用户名或密码错误！', 0, '127.0.0.1', 'Unknown', 'Unknown', '2022-09-30 08:07:14', '内网IP');
INSERT INTO `tb_login_log` VALUES (1575638367263268865, 'admin', '12345672', '用户名或密码错误！', 0, '127.0.0.1', 'Unknown', 'Unknown', '2022-09-30 08:07:15', '内网IP');
INSERT INTO `tb_login_log` VALUES (1575638370794872834, 'admin', '12345672', '密码错误次数过多，请稍后再试！', 0, '127.0.0.1', 'Unknown', 'Unknown', '2022-09-30 08:07:16', '内网IP');
INSERT INTO `tb_login_log` VALUES (1575638384724156417, 'admin', '12345672', '密码错误次数过多，请稍后再试！', 0, '127.0.0.1', 'Unknown', 'Unknown', '2022-09-30 08:07:20', '内网IP');
INSERT INTO `tb_login_log` VALUES (1575651067754270721, 'admin', '1234567', '登录成功', 1, '10.0.0.2', 'Chrome', 'Windows 10 or Windows Server 2016', '2022-09-30 08:57:43', '内网IP');
INSERT INTO `tb_login_log` VALUES (1575665503407816705, 'admin', '1234567', '登录成功', 1, '10.0.0.2', 'Chrome', 'Windows 10 or Windows Server 2016', '2022-09-30 09:55:05', '内网IP');
INSERT INTO `tb_login_log` VALUES (1575676710676135937, 'admin', '1234567', '登录成功', 1, '10.0.0.2', 'Chrome', 'Windows 10 or Windows Server 2016', '2022-09-30 10:39:37', '内网IP');
INSERT INTO `tb_login_log` VALUES (1575686627285856258, 'admin', '12345672', '用户名或密码错误！', 0, '127.0.0.1', 'Unknown', 'Unknown', '2022-09-30 11:19:01', '内网IP');
INSERT INTO `tb_login_log` VALUES (1575686639071850498, 'admin', '1234567', '登录成功', 1, '127.0.0.1', 'Unknown', 'Unknown', '2022-09-30 11:19:04', '内网IP');
INSERT INTO `tb_login_log` VALUES (1575709724500094977, 'admin', '1234567', '登录成功', 1, '10.0.0.2', 'Chrome', 'Windows 10 or Windows Server 2016', '2022-09-30 12:50:48', '内网IP');
INSERT INTO `tb_login_log` VALUES (1575725435087810561, 'admin', '1234567', '登录成功', 1, '10.0.0.2', 'Chrome', 'Windows 10 or Windows Server 2016', '2022-09-30 13:53:14', '内网IP');
INSERT INTO `tb_login_log` VALUES (1575761935024320514, 'admin', '1234567', '登录成功', 1, '10.0.0.2', 'Chrome', 'Windows 10 or Windows Server 2016', '2022-09-30 16:18:16', '内网IP');
INSERT INTO `tb_login_log` VALUES (1576000569287643138, 'admin', '1234567', '登录成功', 1, '127.0.0.1', 'Chrome', 'Windows 10 or Windows Server 2016', '2022-10-01 08:06:31', '内网IP');
INSERT INTO `tb_login_log` VALUES (1576044698667405313, 'admin', '1234567', '登录成功', 1, '127.0.0.1', 'Chrome', 'Windows 10 or Windows Server 2016', '2022-10-01 11:01:52', '内网IP');
INSERT INTO `tb_login_log` VALUES (1576059550349729794, 'admin', '1234567', '登录成功', 1, '127.0.0.1', 'Chrome', 'Windows 10 or Windows Server 2016', '2022-10-01 12:00:53', '内网IP');
INSERT INTO `tb_login_log` VALUES (1576061398699847681, 'admin', '1234567', '登录成功', 1, '127.0.0.1', 'Unknown', 'Unknown', '2022-10-01 12:08:14', '内网IP');
INSERT INTO `tb_login_log` VALUES (1576102194559901697, 'admin', '1234567', '登录成功', 1, '127.0.0.1', 'Unknown', 'Unknown', '2022-10-01 14:50:20', '内网IP');
INSERT INTO `tb_login_log` VALUES (1576102680394555394, 'admin', '1234567', '登录成功', 1, '127.0.0.1', 'Unknown', 'Unknown', '2022-10-01 14:52:16', '内网IP');
INSERT INTO `tb_login_log` VALUES (1576103492000768002, 'admin', '1234567', '登录成功', 1, '127.0.0.1', 'Chrome', 'Windows 10 or Windows Server 2016', '2022-10-01 14:55:30', '内网IP');
INSERT INTO `tb_login_log` VALUES (1576107027685765122, 'admin', '1234567', '登录成功', 1, '127.0.0.1', 'Chrome', 'Windows 10 or Windows Server 2016', '2022-10-01 15:09:33', '内网IP');
INSERT INTO `tb_login_log` VALUES (1576110159316717570, 'admin', '1234567', '登录成功', 1, '127.0.0.1', 'Chrome', 'Windows 10 or Windows Server 2016', '2022-10-01 15:21:59', '内网IP');
INSERT INTO `tb_login_log` VALUES (1576124212755894274, 'admin', '1234567', '登录成功', 1, '127.0.0.1', 'Chrome', 'Windows 10 or Windows Server 2016', '2022-10-01 16:17:50', '内网IP');
INSERT INTO `tb_login_log` VALUES (1576137297839296514, 'admin', '1234567', '登录成功', 1, '127.0.0.1', 'Chrome', 'Windows 10 or Windows Server 2016', '2022-10-01 17:09:50', '内网IP');
INSERT INTO `tb_login_log` VALUES (1576165521566769153, 'admin', '1234567', '登录成功', 1, '127.0.0.1', 'Chrome', 'Windows 10 or Windows Server 2016', '2022-10-01 19:01:59', '内网IP');
INSERT INTO `tb_login_log` VALUES (1576166841497485314, 'admin', '134567', '登录成功', 1, '127.0.0.1', 'Chrome', 'Windows 10 or Windows Server 2016', '2022-10-01 19:07:13', '内网IP');
INSERT INTO `tb_login_log` VALUES (1576166961110646785, 'admin', '123456', '登录成功', 1, '127.0.0.1', 'Chrome', 'Windows 10 or Windows Server 2016', '2022-10-01 19:07:42', '内网IP');
INSERT INTO `tb_login_log` VALUES (1576853026515460098, 'admin', '123456', '用户名或密码错误！', 0, '127.0.0.1', 'Chrome', 'Windows 10 or Windows Server 2016', '2022-10-03 16:33:53', '内网IP');
INSERT INTO `tb_login_log` VALUES (1576853043942793218, 'admin', '1234567', '登录成功', 1, '127.0.0.1', 'Chrome', 'Windows 10 or Windows Server 2016', '2022-10-03 16:33:57', '内网IP');
INSERT INTO `tb_login_log` VALUES (1579114297604448257, 'admin', '1234567', '登录成功', 1, '10.0.0.2', 'Chrome', 'Windows 10 or Windows Server 2016', '2022-10-09 22:19:22', '内网IP');
INSERT INTO `tb_login_log` VALUES (1579122068076908546, 'admin', '1234567', '登录成功', 1, '10.0.0.2', 'Chrome', 'Windows 10 or Windows Server 2016', '2022-10-09 22:50:14', '内网IP');

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
) ENGINE = InnoDB AUTO_INCREMENT = 1569327626948227076 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 1574586264625365005 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_menu_role
-- ----------------------------
INSERT INTO `tb_menu_role` VALUES (1, 1, -1);
INSERT INTO `tb_menu_role` VALUES (3, 3, -1);
INSERT INTO `tb_menu_role` VALUES (5, 5, -1);
INSERT INTO `tb_menu_role` VALUES (6, 6, -1);
INSERT INTO `tb_menu_role` VALUES (8, 8, -1);
INSERT INTO `tb_menu_role` VALUES (9, 9, -1);
INSERT INTO `tb_menu_role` VALUES (10, 1, 0);
INSERT INTO `tb_menu_role` VALUES (11, 3, 0);
INSERT INTO `tb_menu_role` VALUES (1569302282048409602, 7, -1);
INSERT INTO `tb_menu_role` VALUES (1574585924236623873, 2, -1);
INSERT INTO `tb_menu_role` VALUES (1574585924236623874, 2, 0);
INSERT INTO `tb_menu_role` VALUES (1574585971250577410, 4, -1);
INSERT INTO `tb_menu_role` VALUES (1574585971250577411, 4, 0);
INSERT INTO `tb_menu_role` VALUES (1574586264625364993, 1, 1570946257658322946);
INSERT INTO `tb_menu_role` VALUES (1574586264625364994, 2, 1570946257658322946);
INSERT INTO `tb_menu_role` VALUES (1574586264625364995, 4, 1570946257658322946);
INSERT INTO `tb_menu_role` VALUES (1574586264625364996, 5, 1570946257658322946);
INSERT INTO `tb_menu_role` VALUES (1574586264625364997, 6, 1570946257658322946);
INSERT INTO `tb_menu_role` VALUES (1574586264625364998, 7, 1570946257658322946);
INSERT INTO `tb_menu_role` VALUES (1574586264625364999, 8, 1570946257658322946);
INSERT INTO `tb_menu_role` VALUES (1574586264625365000, 9, 1570946257658322946);
INSERT INTO `tb_menu_role` VALUES (1574586264625365001, 1569327626948227074, 1570946257658322946);
INSERT INTO `tb_menu_role` VALUES (1574586264625365002, 3, 1570946257658322946);
INSERT INTO `tb_menu_role` VALUES (1574586264625365003, 10, -1);
INSERT INTO `tb_menu_role` VALUES (1574586264625365004, 10, 0);

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
INSERT INTO `tb_operation_log` VALUES (1575492310105661441, 'admin', '角色Controller', '/admin/role', 'POST', '新增或修改角色，包括其可访资源/菜单', NULL, 'SAVE_OR_UPDATE', 1, '127.0.0.1', '{\"code\":200,\"flag\":true,\"message\":\"操作成功！\"}', '操作成功！', -1, 'com.senko.controller.system.SysRoleController#saveOrUpdateRole', 'Chrome', 'Windows 10 or Windows Server 2016', NULL, '2022-09-29 22:26:53');
INSERT INTO `tb_operation_log` VALUES (1575492419245645825, 'admin', '角色Controller', '/admin/roles', 'DELETE', '删除角色', NULL, 'REMOVE', 1, '127.0.0.1', '{\"code\":200,\"flag\":true,\"message\":\"删除角色成功！\"}', '删除角色成功！', -1, 'com.senko.controller.system.SysRoleController#deleteBathByIds', 'Chrome', 'Windows 10 or Windows Server 2016', NULL, '2022-09-29 22:27:19');
INSERT INTO `tb_operation_log` VALUES (1575637583167496193, 'admin', '角色Controller', '/admin/role', 'POST', '新增或修改角色，包括其可访资源/菜单', NULL, 'SAVE_OR_UPDATE', 1, '10.0.0.2', '{\"code\":200,\"flag\":true,\"message\":\"操作成功！\"}', '操作成功！', -1, 'com.senko.controller.system.SysRoleController#saveOrUpdateRole', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-09-30 08:04:08');
INSERT INTO `tb_operation_log` VALUES (1575637590285230081, 'admin', '角色Controller', '/admin/roles', 'DELETE', '删除角色', NULL, 'REMOVE', 1, '10.0.0.2', '{\"code\":200,\"flag\":true,\"message\":\"删除角色成功！\"}', '删除角色成功！', -1, 'com.senko.controller.system.SysRoleController#deleteBathByIds', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-09-30 08:04:10');
INSERT INTO `tb_operation_log` VALUES (1575686804180582401, 'admin', '资源Controller', '/admin/resource/-1', 'DELETE', '删除资源', NULL, 'REMOVE', 1, '127.0.0.1', '{\"code\":200,\"flag\":true,\"message\":\"删除资源成功\"}', '删除资源成功', -1, 'com.senko.controller.system.SysResourceController#deleteResource', 'Unknown', 'Unknown', '内网IP', '2022-09-30 11:19:44');
INSERT INTO `tb_operation_log` VALUES (1575686949710393345, 'admin', '资源Controller', '/admin/resource/-1', 'DELETE', '删除资源', NULL, 'REMOVE', 1, '127.0.0.1', '{\"code\":200,\"flag\":true,\"message\":\"删除资源成功\"}', '删除资源成功', -1, 'com.senko.controller.system.SysResourceController#deleteResource', 'Unknown', 'Unknown', '内网IP', '2022-09-30 11:20:18');
INSERT INTO `tb_operation_log` VALUES (1575709813482254338, 'admin', '资源Controller', '/admin/resource/anonymous/0', 'PUT', '更新资源匿名状态', NULL, 'UPDATE', 1, '10.0.0.2', '{\"code\":200,\"flag\":true,\"message\":\"更新匿名状态成功\"}', '更新匿名状态成功', -1, 'com.senko.controller.system.SysResourceController#updateAnonymousStatus', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-09-30 12:51:09');
INSERT INTO `tb_operation_log` VALUES (1575709832067215361, 'admin', '资源Controller', '/admin/resource/anonymous/1', 'PUT', '更新资源匿名状态', NULL, 'UPDATE', 1, '10.0.0.2', '{\"code\":200,\"flag\":true,\"message\":\"更新匿名状态成功\"}', '更新匿名状态成功', -1, 'com.senko.controller.system.SysResourceController#updateAnonymousStatus', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-09-30 12:51:14');
INSERT INTO `tb_operation_log` VALUES (1575730231949717505, 'admin', '资源Controller', '/admin/resource/anonymous/1', 'PUT', '更新资源匿名状态', NULL, 'UPDATE', 1, '10.0.0.2', '{\"code\":200,\"flag\":true,\"message\":\"更新匿名状态成功\"}', '更新匿名状态成功', -1, 'com.senko.controller.system.SysResourceController#updateAnonymousStatus', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-09-30 14:12:18');
INSERT INTO `tb_operation_log` VALUES (1575730241118466050, 'admin', '资源Controller', '/admin/resource/anonymous/0', 'PUT', '更新资源匿名状态', NULL, 'UPDATE', 1, '10.0.0.2', '{\"code\":200,\"flag\":true,\"message\":\"更新匿名状态成功\"}', '更新匿名状态成功', -1, 'com.senko.controller.system.SysResourceController#updateAnonymousStatus', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-09-30 14:12:20');
INSERT INTO `tb_operation_log` VALUES (1575769591961198593, 'admin', '资源Controller', '/admin/resource', 'POST', '保存或更新资源', NULL, 'SAVE_OR_UPDATE', 0, '10.0.0.2', 'null', 'Cannot invoke \"com.senko.common.core.entity.SysResource.getResourceType()\" because \"sysResource\" is null', -1, 'com.senko.controller.system.SysResourceController#saveOrUpdateResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-09-30 16:48:42');
INSERT INTO `tb_operation_log` VALUES (1575770069113565186, 'admin', '资源Controller', '/admin/resource', 'POST', '保存或更新资源', NULL, 'SAVE_OR_UPDATE', 0, '10.0.0.2', 'null', 'Cannot invoke \"com.senko.common.core.entity.SysResource.getResourceType()\" because \"sysResource\" is null', -1, 'com.senko.controller.system.SysResourceController#saveOrUpdateResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-09-30 16:50:36');
INSERT INTO `tb_operation_log` VALUES (1575770692294922241, 'admin', '资源Controller', '/admin/resource', 'POST', '保存或更新资源', NULL, 'SAVE_OR_UPDATE', 0, '10.0.0.2', 'null', 'Cannot invoke \"com.senko.common.core.entity.SysResource.getResourceType()\" because \"sysResource\" is null', -1, 'com.senko.controller.system.SysResourceController#saveOrUpdateResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-09-30 16:53:04');
INSERT INTO `tb_operation_log` VALUES (1575770790735167490, 'admin', '资源Controller', '/admin/resource', 'POST', '保存或更新资源', NULL, 'SAVE_OR_UPDATE', 1, '10.0.0.2', '{\"code\":200,\"flag\":true,\"message\":\"操作成功\"}', '操作成功', -1, 'com.senko.controller.system.SysResourceController#saveOrUpdateResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-09-30 16:53:28');
INSERT INTO `tb_operation_log` VALUES (1575772329646276609, 'admin', '资源Controller', '/admin/resource/1575770790538035202', 'DELETE', '删除资源', NULL, 'REMOVE', 1, '10.0.0.2', '{\"code\":200,\"flag\":true,\"message\":\"删除资源成功\"}', '删除资源成功', -1, 'com.senko.controller.system.SysResourceController#deleteResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-09-30 16:59:34');
INSERT INTO `tb_operation_log` VALUES (1575773313025982465, 'admin', '资源Controller', '/admin/resource', 'POST', '保存或更新资源', NULL, 'SAVE_OR_UPDATE', 1, '10.0.0.2', '{\"code\":200,\"flag\":true,\"message\":\"操作成功\"}', '操作成功', -1, 'com.senko.controller.system.SysResourceController#saveOrUpdateResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-09-30 17:03:29');
INSERT INTO `tb_operation_log` VALUES (1575773573332877313, 'admin', '资源Controller', '/admin/resource/1575773312782712833', 'DELETE', '删除资源', NULL, 'REMOVE', 1, '10.0.0.2', '{\"code\":200,\"flag\":true,\"message\":\"删除资源成功\"}', '删除资源成功', -1, 'com.senko.controller.system.SysResourceController#deleteResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-09-30 17:04:31');
INSERT INTO `tb_operation_log` VALUES (1575773716773879810, 'admin', '资源Controller', '/admin/resource', 'POST', '保存或更新资源', NULL, 'SAVE_OR_UPDATE', 1, '10.0.0.2', '{\"code\":200,\"flag\":true,\"message\":\"操作成功\"}', '操作成功', -1, 'com.senko.controller.system.SysResourceController#saveOrUpdateResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-09-30 17:05:05');
INSERT INTO `tb_operation_log` VALUES (1575773938388320258, 'admin', '资源Controller', '/admin/resource/1575773716643856386', 'DELETE', '删除资源', NULL, 'REMOVE', 1, '10.0.0.2', '{\"code\":200,\"flag\":true,\"message\":\"删除资源成功\"}', '删除资源成功', -1, 'com.senko.controller.system.SysResourceController#deleteResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-09-30 17:05:58');
INSERT INTO `tb_operation_log` VALUES (1575773960081260546, 'admin', '资源Controller', '/admin/resource', 'POST', '保存或更新资源', NULL, 'SAVE_OR_UPDATE', 1, '10.0.0.2', '{\"code\":200,\"flag\":true,\"message\":\"操作成功\"}', '操作成功', -1, 'com.senko.controller.system.SysResourceController#saveOrUpdateResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-09-30 17:06:03');
INSERT INTO `tb_operation_log` VALUES (1575774020194021377, 'admin', '资源Controller', '/admin/resource/1575773959884128258', 'DELETE', '删除资源', NULL, 'REMOVE', 1, '10.0.0.2', '{\"code\":200,\"flag\":true,\"message\":\"删除资源成功\"}', '删除资源成功', -1, 'com.senko.controller.system.SysResourceController#deleteResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-09-30 17:06:18');
INSERT INTO `tb_operation_log` VALUES (1575774160355078146, 'admin', '资源Controller', '/admin/resource', 'POST', '保存或更新资源', NULL, 'SAVE_OR_UPDATE', 1, '10.0.0.2', '{\"code\":200,\"flag\":true,\"message\":\"操作成功\"}', '操作成功', -1, 'com.senko.controller.system.SysResourceController#saveOrUpdateResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-09-30 17:06:51');
INSERT INTO `tb_operation_log` VALUES (1575774330207682562, 'admin', '资源Controller', '/admin/resource', 'POST', '保存或更新资源', NULL, 'SAVE_OR_UPDATE', 1, '10.0.0.2', '{\"code\":200,\"flag\":true,\"message\":\"操作成功\"}', '操作成功', -1, 'com.senko.controller.system.SysResourceController#saveOrUpdateResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-09-30 17:07:31');
INSERT INTO `tb_operation_log` VALUES (1575774503180779522, 'admin', '资源Controller', '/admin/resource', 'POST', '保存或更新资源', NULL, 'SAVE_OR_UPDATE', 1, '10.0.0.2', '{\"code\":200,\"flag\":true,\"message\":\"操作成功\"}', '操作成功', -1, 'com.senko.controller.system.SysResourceController#saveOrUpdateResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-09-30 17:08:13');
INSERT INTO `tb_operation_log` VALUES (1575774532117282818, 'admin', '资源Controller', '/admin/resource/1575774160250220546', 'DELETE', '删除资源', NULL, 'REMOVE', 1, '10.0.0.2', '{\"code\":200,\"flag\":true,\"message\":\"删除资源成功\"}', '删除资源成功', -1, 'com.senko.controller.system.SysResourceController#deleteResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-09-30 17:08:20');
INSERT INTO `tb_operation_log` VALUES (1575774552862310402, 'admin', '资源Controller', '/admin/resource/1575774503054950402', 'DELETE', '删除资源', NULL, 'REMOVE', 1, '10.0.0.2', '{\"code\":200,\"flag\":true,\"message\":\"删除资源成功\"}', '删除资源成功', -1, 'com.senko.controller.system.SysResourceController#deleteResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-09-30 17:08:25');
INSERT INTO `tb_operation_log` VALUES (1575774561116700673, 'admin', '资源Controller', '/admin/resource/1575774329951830018', 'DELETE', '删除资源', NULL, 'REMOVE', 1, '10.0.0.2', '{\"code\":200,\"flag\":true,\"message\":\"删除资源成功\"}', '删除资源成功', -1, 'com.senko.controller.system.SysResourceController#deleteResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-09-30 17:08:27');
INSERT INTO `tb_operation_log` VALUES (1576044801008422913, 'admin', '资源Controller', '/admin/resource', 'POST', '保存或更新资源', NULL, 'SAVE_OR_UPDATE', 1, '127.0.0.1', '{\"code\":200,\"flag\":true,\"message\":\"操作成功\"}', '操作成功', -1, 'com.senko.controller.system.SysResourceController#saveOrUpdateResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-10-01 11:02:17');
INSERT INTO `tb_operation_log` VALUES (1576045207138684929, 'admin', '资源Controller', '/admin/resource', 'POST', '保存或更新资源', NULL, 'SAVE_OR_UPDATE', 1, '127.0.0.1', '{\"code\":200,\"flag\":true,\"message\":\"操作成功\"}', '操作成功', -1, 'com.senko.controller.system.SysResourceController#saveOrUpdateResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-10-01 11:03:54');
INSERT INTO `tb_operation_log` VALUES (1576065003649531906, 'admin', '资源Controller', '/admin/resource', 'POST', '保存或更新资源', NULL, 'SAVE_OR_UPDATE', 1, '127.0.0.1', '{\"code\":200,\"flag\":true,\"message\":\"操作成功\"}', '操作成功', -1, 'com.senko.controller.system.SysResourceController#saveOrUpdateResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-10-01 12:22:33');
INSERT INTO `tb_operation_log` VALUES (1576065102538637314, 'admin', '资源Controller', '/admin/resource', 'POST', '保存或更新资源', NULL, 'SAVE_OR_UPDATE', 1, '127.0.0.1', '{\"code\":200,\"flag\":true,\"message\":\"操作成功\"}', '操作成功', -1, 'com.senko.controller.system.SysResourceController#saveOrUpdateResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-10-01 12:22:57');
INSERT INTO `tb_operation_log` VALUES (1576065241781141506, 'admin', '资源Controller', '/admin/resource/1576065003217518594', 'DELETE', '删除资源', NULL, 'REMOVE', 1, '127.0.0.1', '{\"code\":200,\"flag\":true,\"message\":\"删除资源成功\"}', '删除资源成功', -1, 'com.senko.controller.system.SysResourceController#deleteResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-10-01 12:23:30');
INSERT INTO `tb_operation_log` VALUES (1576065271485202434, 'admin', '资源Controller', '/admin/resource', 'POST', '保存或更新资源', NULL, 'SAVE_OR_UPDATE', 1, '127.0.0.1', '{\"code\":200,\"flag\":true,\"message\":\"操作成功\"}', '操作成功', -1, 'com.senko.controller.system.SysResourceController#saveOrUpdateResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-10-01 12:23:37');
INSERT INTO `tb_operation_log` VALUES (1576065528906416130, 'admin', '资源Controller', '/admin/resource/1576065271355179009', 'DELETE', '删除资源', NULL, 'REMOVE', 1, '127.0.0.1', '{\"code\":200,\"flag\":true,\"message\":\"删除资源成功\"}', '删除资源成功', -1, 'com.senko.controller.system.SysResourceController#deleteResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-10-01 12:24:39');
INSERT INTO `tb_operation_log` VALUES (1576065539090186242, 'admin', '资源Controller', '/admin/resource', 'POST', '保存或更新资源', NULL, 'SAVE_OR_UPDATE', 1, '127.0.0.1', '{\"code\":200,\"flag\":true,\"message\":\"操作成功\"}', '操作成功', -1, 'com.senko.controller.system.SysResourceController#saveOrUpdateResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-10-01 12:24:41');
INSERT INTO `tb_operation_log` VALUES (1576065635076833282, 'admin', '资源Controller', '/admin/resource', 'POST', '保存或更新资源', NULL, 'SAVE_OR_UPDATE', 1, '127.0.0.1', '{\"code\":200,\"flag\":true,\"message\":\"操作成功\"}', '操作成功', -1, 'com.senko.controller.system.SysResourceController#saveOrUpdateResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-10-01 12:25:04');
INSERT INTO `tb_operation_log` VALUES (1576065978703601666, 'admin', '资源Controller', '/admin/resource/1576065634946809857', 'DELETE', '删除资源', NULL, 'REMOVE', 1, '127.0.0.1', '{\"code\":200,\"flag\":true,\"message\":\"删除资源成功\"}', '删除资源成功', -1, 'com.senko.controller.system.SysResourceController#deleteResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-10-01 12:26:26');
INSERT INTO `tb_operation_log` VALUES (1576066017165369346, 'admin', '资源Controller', '/admin/resource', 'POST', '保存或更新资源', NULL, 'SAVE_OR_UPDATE', 1, '127.0.0.1', '{\"code\":200,\"flag\":true,\"message\":\"操作成功\"}', '操作成功', -1, 'com.senko.controller.system.SysResourceController#saveOrUpdateResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-10-01 12:26:35');
INSERT INTO `tb_operation_log` VALUES (1576066852263878657, 'admin', '资源Controller', '/admin/resource/1576066016972431361', 'DELETE', '删除资源', NULL, 'REMOVE', 1, '127.0.0.1', '{\"code\":200,\"flag\":true,\"message\":\"删除资源成功\"}', '删除资源成功', -1, 'com.senko.controller.system.SysResourceController#deleteResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-10-01 12:29:54');
INSERT INTO `tb_operation_log` VALUES (1576066862007246849, 'admin', '资源Controller', '/admin/resource/1576065102417002497', 'DELETE', '删除资源', NULL, 'REMOVE', 1, '127.0.0.1', '{\"code\":200,\"flag\":true,\"message\":\"删除资源成功\"}', '删除资源成功', -1, 'com.senko.controller.system.SysResourceController#deleteResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-10-01 12:29:56');
INSERT INTO `tb_operation_log` VALUES (1576107092370321410, 'admin', '资源Controller', '/admin/resource', 'POST', '保存或更新资源', NULL, 'SAVE_OR_UPDATE', 1, '127.0.0.1', '{\"code\":200,\"flag\":true,\"message\":\"操作成功\"}', '操作成功', -1, 'com.senko.controller.system.SysResourceController#saveOrUpdateResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-10-01 15:09:48');
INSERT INTO `tb_operation_log` VALUES (1576107587608571906, 'admin', '资源Controller', '/admin/resource', 'POST', '保存或更新资源', NULL, 'SAVE_OR_UPDATE', 1, '127.0.0.1', '{\"code\":200,\"flag\":true,\"message\":\"操作成功\"}', '操作成功', -1, 'com.senko.controller.system.SysResourceController#saveOrUpdateResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-10-01 15:11:46');
INSERT INTO `tb_operation_log` VALUES (1576110802630676481, 'admin', '角色Controller', '/admin/role', 'POST', '新增或修改角色，包括其可访资源/菜单', NULL, 'SAVE_OR_UPDATE', 1, '127.0.0.1', '{\"code\":200,\"flag\":true,\"message\":\"操作成功！\"}', '操作成功！', -1, 'com.senko.controller.system.SysRoleController#saveOrUpdateRole', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-10-01 15:24:33');
INSERT INTO `tb_operation_log` VALUES (1576110825430913026, 'admin', '角色Controller', '/admin/roles', 'DELETE', '删除角色', NULL, 'REMOVE', 1, '127.0.0.1', '{\"code\":200,\"flag\":true,\"message\":\"删除角色成功！\"}', '删除角色成功！', -1, 'com.senko.controller.system.SysRoleController#deleteBathByIds', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-10-01 15:24:38');
INSERT INTO `tb_operation_log` VALUES (1576133437800361986, 'admin', '资源Controller', '/admin/resource', 'POST', '保存或更新资源', NULL, 'SAVE_OR_UPDATE', 1, '127.0.0.1', '{\"code\":200,\"flag\":true,\"message\":\"操作成功\"}', '操作成功', -1, 'com.senko.controller.system.SysResourceController#saveOrUpdateResource', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-10-01 16:54:29');
INSERT INTO `tb_operation_log` VALUES (1576133741912563713, 'admin', '系统用户模块', '/admin/userinfo', 'PUT', '修改当前用户信息', NULL, 'UPDATE', 1, '127.0.0.1', '{\"code\":200,\"flag\":true,\"message\":\"更新用户信息成功！\"}', '更新用户信息成功！', -1, 'com.senko.controller.system.SysUserController#updateUserInfo', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-10-01 16:55:42');
INSERT INTO `tb_operation_log` VALUES (1576135095196356609, 'admin', '系统用户模块', '/admin/userinfo', 'PUT', '修改当前用户信息', NULL, 'UPDATE', 1, '127.0.0.1', '{\"code\":200,\"flag\":true,\"message\":\"更新用户信息成功！\"}', '更新用户信息成功！', -1, 'com.senko.controller.system.SysUserController#updateUserInfo', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-10-01 17:01:05');
INSERT INTO `tb_operation_log` VALUES (1579114352432390146, 'admin', '系统用户模块', '/admin/user', 'DELETE', '批量删除用户', NULL, 'REMOVE', 1, '10.0.0.2', '{\"code\":200,\"flag\":true,\"message\":\"批量删除用户成功！\"}', '批量删除用户成功！', -1, 'com.senko.controller.system.SysUserController#deleteUsers', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-10-09 22:19:35');
INSERT INTO `tb_operation_log` VALUES (1579114362117038082, 'admin', '系统用户模块', '/admin/user', 'DELETE', '批量删除用户', NULL, 'REMOVE', 1, '10.0.0.2', '{\"code\":200,\"flag\":true,\"message\":\"批量删除用户成功！\"}', '批量删除用户成功！', -1, 'com.senko.controller.system.SysUserController#deleteUsers', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-10-09 22:19:37');
INSERT INTO `tb_operation_log` VALUES (1579122241469435905, 'admin', '角色Controller', '/admin/role', 'POST', '新增或修改角色，包括其可访资源/菜单', NULL, 'SAVE_OR_UPDATE', 1, '10.0.0.2', '{\"code\":200,\"flag\":true,\"message\":\"操作成功！\"}', '操作成功！', -1, 'com.senko.controller.system.SysRoleController#saveOrUpdateRole', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-10-09 22:50:56');
INSERT INTO `tb_operation_log` VALUES (1579122445543297025, 'admin', '角色Controller', '/admin/role', 'POST', '新增或修改角色，包括其可访资源/菜单', NULL, 'SAVE_OR_UPDATE', 1, '10.0.0.2', '{\"code\":200,\"flag\":true,\"message\":\"操作成功！\"}', '操作成功！', -1, 'com.senko.controller.system.SysRoleController#saveOrUpdateRole', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-10-09 22:51:44');
INSERT INTO `tb_operation_log` VALUES (1579127681016262657, 'admin', '系统用户模块', '/admin/user', 'POST', '添加或更新用户', NULL, 'SAVE_OR_UPDATE', 1, '10.0.0.2', '{\"code\":200,\"flag\":true,\"message\":\"添加或更新用户成功！\"}', '添加或更新用户成功！', -1, 'com.senko.controller.system.SysUserController#saveOrUpdateUser', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-10-09 23:12:33');
INSERT INTO `tb_operation_log` VALUES (1579128915437572097, 'admin', '系统用户模块', '/admin/user', 'POST', '添加或更新用户', NULL, 'SAVE_OR_UPDATE', 0, '10.0.0.2', 'null', 'Cannot invoke \"com.senko.common.core.entity.SysUser.getUserInfoId()\" because \"one\" is null', -1, 'com.senko.controller.system.SysUserController#saveOrUpdateUser', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-10-09 23:17:27');
INSERT INTO `tb_operation_log` VALUES (1579130928254681089, 'admin', '系统用户模块', '/admin/user', 'POST', '添加或更新用户', NULL, 'SAVE_OR_UPDATE', 0, '10.0.0.2', 'null', 'Cannot invoke \"com.senko.common.core.entity.SysUser.getUserInfoId()\" because \"one\" is null', -1, 'com.senko.controller.system.SysUserController#saveOrUpdateUser', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-10-09 23:25:27');
INSERT INTO `tb_operation_log` VALUES (1579131397614100481, 'admin', '系统用户模块', '/admin/user', 'POST', '添加或更新用户', NULL, 'SAVE_OR_UPDATE', 1, '10.0.0.2', '{\"code\":200,\"flag\":true,\"message\":\"添加或更新用户成功！\"}', '添加或更新用户成功！', -1, 'com.senko.controller.system.SysUserController#saveOrUpdateUser', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-10-09 23:27:19');
INSERT INTO `tb_operation_log` VALUES (1579131601092370433, 'admin', '系统用户模块', '/admin/user', 'POST', '添加或更新用户', NULL, 'SAVE_OR_UPDATE', 1, '10.0.0.2', '{\"code\":200,\"flag\":true,\"message\":\"添加或更新用户成功！\"}', '添加或更新用户成功！', -1, 'com.senko.controller.system.SysUserController#saveOrUpdateUser', 'Chrome', 'Windows 10 or Windows Server 2016', '内网IP', '2022-10-09 23:28:07');

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
) ENGINE = InnoDB AUTO_INCREMENT = 1576133437523537923 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 1579122445413273605 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `tb_resource_role` VALUES (1579122241213583362, 3, 0);
INSERT INTO `tb_resource_role` VALUES (1579122241213583363, 4, 0);
INSERT INTO `tb_resource_role` VALUES (1579122241213583364, 5, 0);
INSERT INTO `tb_resource_role` VALUES (1579122241213583365, 16, 0);
INSERT INTO `tb_resource_role` VALUES (1579122241213583366, 9, 0);
INSERT INTO `tb_resource_role` VALUES (1579122241213583367, 10, 0);
INSERT INTO `tb_resource_role` VALUES (1579122241213583368, 14, 0);
INSERT INTO `tb_resource_role` VALUES (1579122241213583369, 17, 0);
INSERT INTO `tb_resource_role` VALUES (1579122241213583370, 6, 0);
INSERT INTO `tb_resource_role` VALUES (1579122241213583371, 8, 0);
INSERT INTO `tb_resource_role` VALUES (1579122241213583372, 12, 0);
INSERT INTO `tb_resource_role` VALUES (1579122241213583373, 28, 0);
INSERT INTO `tb_resource_role` VALUES (1579122241213583374, 30, 0);
INSERT INTO `tb_resource_role` VALUES (1579122241213583375, 1576133437523537921, 0);
INSERT INTO `tb_resource_role` VALUES (1579122241213583376, 18, 0);
INSERT INTO `tb_resource_role` VALUES (1579122241213583377, 13, 0);
INSERT INTO `tb_resource_role` VALUES (1579122241213583378, 19, 0);
INSERT INTO `tb_resource_role` VALUES (1579122241213583379, 21, 0);
INSERT INTO `tb_resource_role` VALUES (1579122241213583380, 22, 0);
INSERT INTO `tb_resource_role` VALUES (1579122241213583381, 24, 0);
INSERT INTO `tb_resource_role` VALUES (1579122241213583382, 26, 0);
INSERT INTO `tb_resource_role` VALUES (1579122241213583383, 29, 0);
INSERT INTO `tb_resource_role` VALUES (1579122241213583384, 32, 0);
INSERT INTO `tb_resource_role` VALUES (1579122241213583385, 33, 0);
INSERT INTO `tb_resource_role` VALUES (1579122241213583386, 37, 0);
INSERT INTO `tb_resource_role` VALUES (1579122241213583387, 39, 0);
INSERT INTO `tb_resource_role` VALUES (1579122241213583388, 40, 0);
INSERT INTO `tb_resource_role` VALUES (1579122241213583389, 41, 0);
INSERT INTO `tb_resource_role` VALUES (1579122241213583390, 47, 0);
INSERT INTO `tb_resource_role` VALUES (1579122241213583391, 45, 0);
INSERT INTO `tb_resource_role` VALUES (1579122241213583392, 46, 0);
INSERT INTO `tb_resource_role` VALUES (1579122445354553345, 3, 1570946257658322946);
INSERT INTO `tb_resource_role` VALUES (1579122445354553346, 4, 1570946257658322946);
INSERT INTO `tb_resource_role` VALUES (1579122445354553347, 5, 1570946257658322946);
INSERT INTO `tb_resource_role` VALUES (1579122445354553348, 16, 1570946257658322946);
INSERT INTO `tb_resource_role` VALUES (1579122445354553349, 9, 1570946257658322946);
INSERT INTO `tb_resource_role` VALUES (1579122445354553350, 10, 1570946257658322946);
INSERT INTO `tb_resource_role` VALUES (1579122445354553351, 14, 1570946257658322946);
INSERT INTO `tb_resource_role` VALUES (1579122445354553352, 17, 1570946257658322946);
INSERT INTO `tb_resource_role` VALUES (1579122445354553353, 6, 1570946257658322946);
INSERT INTO `tb_resource_role` VALUES (1579122445354553354, 8, 1570946257658322946);
INSERT INTO `tb_resource_role` VALUES (1579122445354553355, 12, 1570946257658322946);
INSERT INTO `tb_resource_role` VALUES (1579122445354553356, 28, 1570946257658322946);
INSERT INTO `tb_resource_role` VALUES (1579122445354553357, 30, 1570946257658322946);
INSERT INTO `tb_resource_role` VALUES (1579122445354553358, 18, 1570946257658322946);
INSERT INTO `tb_resource_role` VALUES (1579122445354553359, 13, 1570946257658322946);
INSERT INTO `tb_resource_role` VALUES (1579122445354553360, 19, 1570946257658322946);
INSERT INTO `tb_resource_role` VALUES (1579122445354553361, 21, 1570946257658322946);
INSERT INTO `tb_resource_role` VALUES (1579122445354553362, 22, 1570946257658322946);
INSERT INTO `tb_resource_role` VALUES (1579122445354553363, 24, 1570946257658322946);
INSERT INTO `tb_resource_role` VALUES (1579122445354553364, 26, 1570946257658322946);
INSERT INTO `tb_resource_role` VALUES (1579122445354553365, 29, 1570946257658322946);
INSERT INTO `tb_resource_role` VALUES (1579122445354553366, 32, 1570946257658322946);
INSERT INTO `tb_resource_role` VALUES (1579122445354553367, 33, 1570946257658322946);
INSERT INTO `tb_resource_role` VALUES (1579122445354553368, 37, 1570946257658322946);
INSERT INTO `tb_resource_role` VALUES (1579122445354553369, 39, 1570946257658322946);
INSERT INTO `tb_resource_role` VALUES (1579122445354553370, 40, 1570946257658322946);
INSERT INTO `tb_resource_role` VALUES (1579122445413273601, 41, 1570946257658322946);
INSERT INTO `tb_resource_role` VALUES (1579122445413273602, 47, 1570946257658322946);
INSERT INTO `tb_resource_role` VALUES (1579122445413273603, 45, 1570946257658322946);
INSERT INTO `tb_resource_role` VALUES (1579122445413273604, 46, 1570946257658322946);

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
INSERT INTO `tb_role` VALUES (0, '普通用户', 'user', 0, '2022-08-31 16:07:11', '2022-10-09 22:50:56');
INSERT INTO `tb_role` VALUES (1570946257658322946, '测试角色', 'test', 0, '2022-09-17 09:22:29', '2022-10-09 22:51:44');

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
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `is_disabled` tinyint(1) NULL DEFAULT 0,
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `tb_user_auth_username_uindex`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_auth
-- ----------------------------
INSERT INTO `tb_user_auth` VALUES (-1, -1, 'admin', '$2a$10$EukevBvrfQMBfn3M/Uz6Nu2uqEAqKsKMm3wtnI96xw5rXJ69C0r76', '', '2022-09-03 19:32:22', 0, '2022-10-01 19:07:38');
INSERT INTO `tb_user_auth` VALUES (10, 1579131397341470722, 'test', '$2a$10$A8.Fhfj/9LCQXWzu7sV1Me1kzT8x7nVVVjrwoD236pq86Gap3UvwG', '', '2022-10-09 23:27:19', 0, '2022-10-09 23:28:07');

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
) ENGINE = InnoDB AUTO_INCREMENT = 1566435250730758153 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_info
-- ----------------------------
INSERT INTO `tb_user_info` VALUES (-1, '管理员', 'https://gcore.jsdelivr.net/gh/Shinyoki/images_repository/blog_images/user.png', '2022-09-03 20:01:11', '2022-10-09 22:50:14', 'Chrome', 'Windows 10 or Windows Server 2016', '10.0.0.2', '');
INSERT INTO `tb_user_info` VALUES (1579131397341470722, '测试用户', 'https://gcore.jsdelivr.net/gh/Shinyoki/images_repository/blog_images/user.png', '2022-10-09 23:27:19', NULL, NULL, NULL, NULL, NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 1574586178235285506 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_role
-- ----------------------------
INSERT INTO `tb_user_role` VALUES (1, -1, -1);
INSERT INTO `tb_user_role` VALUES (1579131600966541313, 10, 1570946257658322946);

SET FOREIGN_KEY_CHECKS = 1;
