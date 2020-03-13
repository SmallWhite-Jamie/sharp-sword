/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 50631
 Source Host           : 127.0.0.1:3306
 Source Schema         : springboot

 Target Server Type    : MySQL
 Target Server Version : 50631
 File Encoding         : 65001

 Date: 24/02/2020 11:02:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menus
-- ----------------------------
DROP TABLE IF EXISTS `sys_menus`;
CREATE TABLE `sys_menus`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单权限值或者code值',
  `directions` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `router` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路由地址',
  `enable` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '状态',
  `pid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级菜单',
  `level` int(6) NULL DEFAULT NULL COMMENT '级别',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `code`(`code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of sys_menus
-- ----------------------------
BEGIN;
INSERT INTO `sys_menus` VALUES ('', '', NULL, NULL, NULL, '1', NULL, NULL, NULL, NULL, NULL, '2020-01-25 21:30:02', NULL), ('1', '权限管理', 'permission_manager', '权限管理', '/auth/permission', '1', '3', 2, 1, 'lock', NULL, '2020-01-24 20:29:45', NULL), ('2', '角色管理', 'role_manager', '角色管理', '/auth/role', '1', '3', 2, 2, NULL, NULL, '2020-01-23 20:29:51', NULL), ('3', '权限认证', 'auth', NULL, '/auth', '1', NULL, 1, 0, NULL, NULL, '2020-01-25 20:29:55', NULL), ('4', '菜单管理', NULL, '系统菜单管理界面', '/menus', '1', NULL, 1, NULL, NULL, NULL, '2020-01-22 20:29:58', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_menus_per
-- ----------------------------
DROP TABLE IF EXISTS `sys_menus_per`;
CREATE TABLE `sys_menus_per`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `per_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `menu_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of sys_menus_per
-- ----------------------------
BEGIN;
INSERT INTO `sys_menus_per` VALUES ('1', '3', '1');
COMMIT;

-- ----------------------------
-- Table structure for sys_menus_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_menus_role`;
CREATE TABLE `sys_menus_role`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `menu_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of sys_menus_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_menus_role` VALUES ('1', '1', '2'), ('2', '1', '3'), ('3', '1', '4'), ('4', '1', '68464823564763136');
COMMIT;

-- ----------------------------
-- Table structure for sys_op_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_op_log`;
CREATE TABLE `sys_op_log`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户ID',
  `url` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求url',
  `method` int(1) NULL DEFAULT NULL COMMENT 'request方式 枚举类',
  `class_method` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方法名',
  `module_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模块名',
  `description` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `client_type` int(1) NULL DEFAULT NULL COMMENT '客户端类型 枚举类',
  `op` int(1) NULL DEFAULT NULL COMMENT '操作类型 枚举类',
  `crt_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of sys_op_log
-- ----------------------------
BEGIN;
INSERT INTO `sys_op_log` VALUES ('70365004258344960', NULL, '/api/test/deferredResult', 0, 'com.jamie.framework.controller.TestController.deferredResult', '测试', '测试DeferredResult', 0, 3, '2020-02-16 16:21:37'), ('70365403745878016', NULL, '/api/test/deferredResult', 0, 'com.jamie.framework.controller.TestController.deferredResult', '测试', '测试DeferredResult', 0, 3, '2020-02-16 16:27:58'), ('70365421054722048', NULL, '/api/test/deferredResult', 0, 'com.jamie.framework.controller.TestController.deferredResult', '测试', '测试DeferredResult', 0, 3, '2020-02-16 16:28:15'), ('70365421520289792', NULL, '/api/test/deferredResult', 0, 'com.jamie.framework.controller.TestController.deferredResult', '测试', '测试DeferredResult', 0, 3, '2020-02-16 16:28:15'), ('70365480879128576', NULL, '/api/test/deferredResult', 0, 'com.jamie.framework.controller.TestController.deferredResult', '测试', '测试DeferredResult', 0, 3, '2020-02-16 16:29:12'), ('70365501723770880', NULL, '/api/test/deferredResult', 0, 'com.jamie.framework.controller.TestController.deferredResult', '测试', '测试DeferredResult', 0, 3, '2020-02-16 16:29:32'), ('70365549721288704', NULL, '/api/test/deferredResult', 0, 'com.jamie.framework.controller.TestController.deferredResult', '测试', '测试DeferredResult', 0, 3, '2020-02-16 16:30:18'), ('70365578740629504', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-16 16:30:45'), ('70365596295888896', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-16 16:31:02'), ('70365620525334528', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-16 16:31:25'), ('70365735363280896', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-16 16:33:15'), ('70365761697218560', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-16 16:33:40'), ('70365793511014400', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-16 16:34:10'), ('70365829599854592', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-16 16:34:44'), ('70365945402490880', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-16 16:36:35'), ('70366270360387584', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-16 16:41:45'), ('70366301299671040', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-16 16:42:14'), ('70366883457531904', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-16 16:51:29'), ('70366904776130560', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-16 16:51:50'), ('70366966854975488', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-16 16:52:49'), ('70379256231755776', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-16 20:08:09'), ('70379793090084864', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-16 20:16:41'), ('70379793428774912', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-16 20:16:41'), ('70379794095669248', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-16 20:16:42'), ('70379794712231936', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-16 20:16:43'), ('70379795227082752', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-16 20:16:43'), ('70379795706281984', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-16 20:16:44'), ('70379796256784384', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-16 20:16:44'), ('70379796868104192', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-16 20:16:45'), ('70472505761988608', NULL, '/api/test/deferredResult', 0, 'com.jamie.framework.controller.TestController.deferredResult', '测试', '测试DeferredResult', 0, 3, '2020-02-17 20:50:19'), ('70472513750040576', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-17 20:50:26'), ('70472527842902016', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-17 20:50:40'), ('70472955146010624', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-17 20:57:27'), ('70472962059272192', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-17 20:57:34'), ('70473001809739776', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-17 20:58:12'), ('70473005191397376', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-17 20:58:15'), ('70473006091075584', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-17 20:58:16'), ('70473007183691776', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-17 20:58:17'), ('70473114315653120', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-17 20:59:59'), ('70910677770829824', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-22 16:54:52'), ('70910686436261888', NULL, '/api/test/deferredResult', 0, 'com.jamie.framework.controller.TestController.deferredResult', '测试', '测试DeferredResult', 0, 3, '2020-02-22 16:55:00'), ('70910711604183040', NULL, '/api/test/deferredResult', 0, 'com.jamie.framework.controller.TestController.deferredResult', '测试', '测试DeferredResult', 0, 3, '2020-02-22 16:55:24'), ('70913488299491328', NULL, '/api/test/deferredResult', 0, 'com.jamie.framework.controller.TestController.deferredResult', '测试', '测试DeferredResult', 0, 3, '2020-02-22 17:39:32'), ('71002961948442624', NULL, '/api/test/deferredResult', 0, 'com.jamie.framework.controller.TestController.deferredResult', '测试', '测试DeferredResult', 0, 3, '2020-02-23 17:21:41'), ('71003442497191936', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-23 17:29:19'), ('71003466716151808', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-23 17:29:43'), ('71003468988416000', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-23 17:29:45'), ('71003484430794752', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-23 17:29:59'), ('71003486197645312', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-23 17:30:01'), ('71003487639437312', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-23 17:30:03'), ('71003489566720000', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-23 17:30:04'), ('71003490202157056', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-23 17:30:05'), ('71003490667724800', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-23 17:30:05'), ('71003491241295872', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-23 17:30:06'), ('71003491690086400', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-23 17:30:06'), ('71003492120002560', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-23 17:30:07'), ('71003492602347520', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-23 17:30:07'), ('71003493187452928', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-23 17:30:08'), ('71003493740052480', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-23 17:30:08'), ('71003494325157888', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-23 17:30:09'), ('71003494871465984', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-23 17:30:09'), ('71003495516340224', NULL, '/api/test/redisServiceTest', 0, 'com.jamie.framework.controller.TestController.redisServiceTest', '测试', '测试redis序列化', 2, 3, '2020-02-23 17:30:10'), ('71003520534315008', NULL, '/api/test/deferredResult', 0, 'com.jamie.framework.controller.TestController.deferredResult', '测试', '测试DeferredResult', 0, 3, '2020-02-23 17:30:34');
COMMIT;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sort` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_permission` VALUES ('1', 'add', '新增', '1', 1), ('2', 'del', '删除', '1', 2), ('3', 'view', '查看', '1', 3);
COMMIT;

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `name` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `suffix` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '后缀',
  `size` bigint(20) NULL DEFAULT NULL COMMENT '大小',
  `file_type` int(2) NULL DEFAULT NULL COMMENT '文件类型',
  `storage_type` int(1) NULL DEFAULT NULL COMMENT '存储类型 1 网络（url生效） 2 服务器文件（filepath生效）',
  `status` int(2) NULL DEFAULT NULL COMMENT '状态',
  `url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '网络位置',
  `filepath` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件路径',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `crteater` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
BEGIN;
INSERT INTO `sys_resource` VALUES ('1', '测试', 'txt', 45682, 1, 2, 1, 'http://e.hiphotos.baidu.com/zhidao/pic/item/d62a6059252dd42a1c362a29033b5bb5c9eab870.jpg', 'D:\\logs\\spring.log', 1, 'admin', '2020-02-02 12:21:14'), ('70567922613878784', '我的简历.docx', '.docx', 36069, NULL, 2, 1, NULL, 'C:\\resource\\2020-02-18\\70567791372009472', 0, 'admin', '2020-02-18 22:06:43'), ('70568825395871744', '系统优化加速工具V1.37.812169', '.exe', 2055272, NULL, 2, 1, NULL, 'C:\\resource\\2020-02-18\\70568819628703744', 0, 'admin', '2020-02-18 22:21:16');
COMMIT;

-- ----------------------------
-- Table structure for sys_resource_biz
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource_biz`;
CREATE TABLE `sys_resource_biz`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `resid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源ID',
  `bizid` varchar(0) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '业务ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Table structure for sys_role_per
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_per`;
CREATE TABLE `sys_role_per`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色ID',
  `per_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of sys_role_per
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_per` VALUES ('1', '1', '1'), ('2', '1', '2'), ('3', '2', '3');
COMMIT;

-- ----------------------------
-- Table structure for sys_roles
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles`;
CREATE TABLE `sys_roles`  (
  `ROLEID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ROLECODE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ROLETYPE` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ROLENAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ROLESTATUS` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `SORT` int(11) NULL DEFAULT NULL,
  `REMARK` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PERMISSION` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PARENTID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `SQL` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ROLEID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of sys_roles
-- ----------------------------
BEGIN;
INSERT INTO `sys_roles` VALUES ('1', 'admin', 'admin', '管理员', '1', 1, '', NULL, NULL, NULL), ('2', 'yk', 'yk', '游客', '1', 2, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `userid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '123456',
  `pw_hash` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `enable` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1',
  `usertype` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `slot` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('1', 'admin', '管理员', '123456', 'E10ADC3949BA59ABBE56E057F20F883E', '1', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_per
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_per`;
CREATE TABLE `sys_user_per`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `per_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of sys_user_per
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_per` VALUES ('1', 'admin', '3');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_roles
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_roles`;
CREATE TABLE `sys_user_roles`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of sys_user_roles
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_roles` VALUES ('1', 'admin', '1');
COMMIT;

-- ----------------------------
-- Table structure for t_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_goods`;
CREATE TABLE `t_goods`  (
  `id` varchar(46) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `ms` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of t_goods
-- ----------------------------
BEGIN;
INSERT INTO `t_goods` VALUES ('1', '小米3', '小米手机3'), ('2', '魅族', '不知道是第几代');
COMMIT;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `age` int(3) NULL DEFAULT NULL,
  `addr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Table structure for sys_login_error
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_error`;
CREATE TABLE `sys_login_error` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户ID',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(64) DEFAULT NULL COMMENT '更新人',
  `revision` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁',
  `is_deleted` varchar(1) NOT NULL DEFAULT '0' COMMENT '删除标记 0正常1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户登录错误次数记录 用户登录错误次数记录表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
BEGIN;
INSERT INTO `t_user` VALUES ('59678581638299648', '驱蚊器无群', 15, '上海杨浦区');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
