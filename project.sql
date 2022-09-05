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

 Date: 05/09/2022 21:01:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_menu`;
CREATE TABLE `tb_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单的描述，如 首页',
  `path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单的路径，如 /',
  `component` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单的vue路径，如 /layout',
  `icon` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '饿了么图标名 如 el-icon-xiaolian',
  `order_num` tinyint(1) NULL DEFAULT 0 COMMENT '菜单的显示优先级，越小越靠前显示，0 ~ 9',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '所属菜单ID',
  `is_hidden` tinyint(1) NULL DEFAULT 0 COMMENT '是否隐藏',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_menu
-- ----------------------------

-- ----------------------------
-- Table structure for tb_menu_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_menu_role`;
CREATE TABLE `tb_menu_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  `role_id` bigint NOT NULL COMMENT '用户角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_menu_role
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
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_resource
-- ----------------------------
INSERT INTO `tb_resource` VALUES (3, '网站信息模块', NULL, NULL, NULL, 1, '2022-09-05 15:03:54', '2022-09-05 15:03:55');
INSERT INTO `tb_resource` VALUES (4, '查看网站首页', '/', 'GET', 3, 1, '2022-09-05 15:04:35', '2022-09-05 15:04:36');
INSERT INTO `tb_resource` VALUES (5, '查看后台信息', '/admin', 'GET', 3, 0, '2022-09-05 15:04:57', '2022-09-05 15:04:56');
INSERT INTO `tb_resource` VALUES (6, '查看关于我的信息', '/about', 'GET', 3, 0, '2022-09-05 16:00:06', '2022-09-05 16:00:07');
INSERT INTO `tb_resource` VALUES (7, '修改关于我的信息', '/admin/about', 'PUT', 3, 0, '2022-09-05 16:00:41', '2022-09-05 16:00:41');

-- ----------------------------
-- Table structure for tb_resource_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_resource_role`;
CREATE TABLE `tb_resource_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `resource_id` bigint NOT NULL COMMENT '资源ID',
  `role_id` bigint NOT NULL COMMENT '用户角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_resource_role
-- ----------------------------
INSERT INTO `tb_resource_role` VALUES (1, 3, -1);
INSERT INTO `tb_resource_role` VALUES (2, 4, -1);
INSERT INTO `tb_resource_role` VALUES (3, 10, -1);
INSERT INTO `tb_resource_role` VALUES (4, 6, -1);
INSERT INTO `tb_resource_role` VALUES (5, 7, -1);
INSERT INTO `tb_resource_role` VALUES (6, 6, 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES (-1, '管理员', 'admin', 0, '2022-08-31 16:06:55', '2022-08-31 16:06:56');
INSERT INTO `tb_role` VALUES (0, '普通用户', 'user', 0, '2022-08-31 16:07:11', '2022-08-31 16:07:11');

-- ----------------------------
-- Table structure for tb_user_auth
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_auth`;
CREATE TABLE `tb_user_auth`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '认证用户ID',
  `user_info_id` bigint NULL DEFAULT NULL,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名（登录账号名）',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户密码',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '绑定邮箱',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `tb_user_auth_username_uindex`(`username`) USING BTREE,
  UNIQUE INDEX `tb_user_auth_email_uindex`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_auth
-- ----------------------------
INSERT INTO `tb_user_auth` VALUES (-1, -1, 'admin', '$2a$10$NSC5T38wVh3W1EgUURPITOGyMjVlSCd1YnkqGTBBENWZUx6anDjTe', '2022-09-03 19:32:22', '2022-09-03 19:32:22', NULL);
INSERT INTO `tb_user_auth` VALUES (2, 1566045290374737922, 'senko', '$2a$10$EukevBvrfQMBfn3M/Uz6Nu2uqEAqKsKMm3wtnI96xw5rXJ69C0r76', '2022-09-03 20:47:48', '2022-09-03 20:47:48', NULL);
INSERT INTO `tb_user_auth` VALUES (3, 1566410436787945473, 'senko2', '$2a$10$fDVP7//5A36RQIINyFChZOHjJGCEP/iRDOjpMeKnRkzJZTWSeu2tO', '2022-09-04 20:58:45', '2022-09-04 20:58:45', NULL);

-- ----------------------------
-- Table structure for tb_user_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_info`;
CREATE TABLE `tb_user_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nickname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `avatar` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'https://gcore.jsdelivr.net/gh/Shinyoki/images_repository/blog_images/user.png',
  `create_time` datetime NULL DEFAULT NULL,
  `last_login_time` datetime NULL DEFAULT NULL,
  `browser` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `os` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `tb_user_info_nickname_uindex`(`nickname`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1566435250730758147 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_info
-- ----------------------------
INSERT INTO `tb_user_info` VALUES (-1, '管理员', 'https://gcore.jsdelivr.net/gh/Shinyoki/images_repository/blog_images/user.png', '2022-09-03 20:01:11', '2022-09-05 19:32:04', 'Unknown', 'Unknown', '172.27.240.1');
INSERT INTO `tb_user_info` VALUES (1566045290374737922, '用户7c3e324a-37', 'https://gcore.jsdelivr.net/gh/Shinyoki/images_repository/blog_images/user.png', '2022-09-03 20:47:48', '2022-09-03 20:47:48', 'Unknown', 'Unknown', '192.168.1.6');
INSERT INTO `tb_user_info` VALUES (1566410436787945473, '用户20957232-01', 'https://gcore.jsdelivr.net/gh/Shinyoki/images_repository/blog_images/user.png', '2022-09-04 20:58:45', '2022-09-04 20:58:45', 'Unknown', 'Unknown', '192.168.1.6');
INSERT INTO `tb_user_info` VALUES (1566435250730758146, NULL, 'https://gcore.jsdelivr.net/gh/Shinyoki/images_repository/blog_images/user.png', '2022-09-04 22:37:21', NULL, 'XXX', NULL, NULL);

-- ----------------------------
-- Table structure for tb_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_role`;
CREATE TABLE `tb_user_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '用户角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_role
-- ----------------------------
INSERT INTO `tb_user_role` VALUES (1, -1, -1);
INSERT INTO `tb_user_role` VALUES (2, -1, 0);
INSERT INTO `tb_user_role` VALUES (3, 0, 0);

SET FOREIGN_KEY_CHECKS = 1;
