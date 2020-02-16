/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : mysecurity

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 22/01/2020 23:53:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `href` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '',
  `type` tinyint(1) NOT NULL,
  `permission` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '',
  `sort` int(11) NOT NULL,
  `parentId` int(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12360 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (111, '普通用户浏览', '/user/query', 4, 'sys:user:query', 2, 112);
INSERT INTO `sys_permission` VALUES (112, '普通用户管理', '', 1, '', 1, 0);
INSERT INTO `sys_permission` VALUES (123, '系统设置', NULL, 1, '', 1, 0);
INSERT INTO `sys_permission` VALUES (12349, '角色管理', '/pages/sys/roleList.html', 1, '', 1, 123);
INSERT INTO `sys_permission` VALUES (12350, '菜单管理', '/pages/menu/menuList.html', 1, '', 1, 123);
INSERT INTO `sys_permission` VALUES (12351, '用户查询', '/pages/user/userList.html', 1, '', 1, 112);
INSERT INTO `sys_permission` VALUES (12352, '查询', '', 1, 'sys:user:query', 1, 12351);
INSERT INTO `sys_permission` VALUES (12353, '删除', '', 1, 'sys:user:delete', 1, 12351);
INSERT INTO `sys_permission` VALUES (12354, '修改', '', 1, 'sys:user:edit', 1, 12351);
INSERT INTO `sys_permission` VALUES (12355, '添加', '', 1, 'sys:user:add', 1, 12351);
INSERT INTO `sys_permission` VALUES (12356, '查询', '', 1, 'sys:role:query', 1, 12349);
INSERT INTO `sys_permission` VALUES (12357, '删除', '', 1, 'sys:role:delete', 1, 12349);
INSERT INTO `sys_permission` VALUES (12358, '修改', '', 1, 'sys:role:edit', 1, 12349);
INSERT INTO `sys_permission` VALUES (12359, '添加', '', 1, 'sys:role:add', 1, 12349);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `createTime` datetime(0) NULL,
  `updateTime` datetime(0) NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 790 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (111, '运维', '这是运维', '2020-01-15 21:26:10', '2020-01-15 21:26:13');
INSERT INTO `sys_role` VALUES (222, '开发人员', '这是开发人员', '2020-01-15 21:26:44', '2020-01-15 21:26:46');
INSERT INTO `sys_role` VALUES (456, '管理员', '这是角色描述', '2019-12-29 13:11:58', '2019-12-25 13:12:02');
INSERT INTO `sys_role` VALUES (789, '普通用户', '这是普通用户', '2019-12-11 13:17:11', '2019-12-19 13:17:15');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  `permissionId` int(11) NOT NULL,
  PRIMARY KEY (`roleId`, `permissionId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (9, 456, 112);
INSERT INTO `sys_role_permission` VALUES (7, 456, 123);
INSERT INTO `sys_role_permission` VALUES (1, 456, 12345);
INSERT INTO `sys_role_permission` VALUES (2, 456, 12346);
INSERT INTO `sys_role_permission` VALUES (3, 456, 12347);
INSERT INTO `sys_role_permission` VALUES (4, 456, 12348);
INSERT INTO `sys_role_permission` VALUES (8, 456, 12349);
INSERT INTO `sys_role_permission` VALUES (10, 456, 12350);
INSERT INTO `sys_role_permission` VALUES (11, 456, 12351);
INSERT INTO `sys_role_permission` VALUES (6, 789, 111);
INSERT INTO `sys_role_permission` VALUES (5, 789, 222);

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user`  (
  `userId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  PRIMARY KEY (`userId`, `roleId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES (1, 456);
INSERT INTO `sys_role_user` VALUES (2, 789);
INSERT INTO `sys_role_user` VALUES (3, 456);
INSERT INTO `sys_role_user` VALUES (4, 789);
INSERT INTO `sys_role_user` VALUES (5, 456);
INSERT INTO `sys_role_user` VALUES (6, 789);
INSERT INTO `sys_role_user` VALUES (7, 789);
INSERT INTO `sys_role_user` VALUES (9, 789);
INSERT INTO `sys_role_user` VALUES (10, 456);
INSERT INTO `sys_role_user` VALUES (13, 789);
INSERT INTO `sys_role_user` VALUES (14, 789);
INSERT INTO `sys_role_user` VALUES (17, 222);
INSERT INTO `sys_role_user` VALUES (18, 111);
INSERT INTO `sys_role_user` VALUES (20, 111);
INSERT INTO `sys_role_user` VALUES (21, 456);
INSERT INTO `sys_role_user` VALUES (22, 456);
INSERT INTO `sys_role_user` VALUES (23, 111);
INSERT INTO `sys_role_user` VALUES (24, 111);
INSERT INTO `sys_role_user` VALUES (25, 111);
INSERT INTO `sys_role_user` VALUES (26, 111);
INSERT INTO `sys_role_user` VALUES (27, 111);
INSERT INTO `sys_role_user` VALUES (28, 222);
INSERT INTO `sys_role_user` VALUES (29, 111);
INSERT INTO `sys_role_user` VALUES (30, 111);
INSERT INTO `sys_role_user` VALUES (31, 456);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `openid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `headImgUrl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `telephone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `birthday` date NOT NULL,
  `sex` char(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT 1,
  `createTime` datetime(0) NULL,
  `updateTime` datetime(0) NULL,
  `isLocked` tinyint(1) NOT NULL DEFAULT 0,
  `isEnabled` tinyint(1) NOT NULL DEFAULT 0,
  `isExpired` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '123', '$2a$10$f91HxFUF97fui41KY.JD9OoHeP7rKucTr2A3pJjryVHNBLwKmD9SS', 'nickname', '/asdfasdf/asdf', '12412414', '112', 'wenfan@qq.com', '2019-11-26', '1', 1, '2019-12-12 13:13:03', '2020-01-18 09:57:24', 1, 0, 1);
INSERT INTO `sys_user` VALUES (2, 'user', '123124', '111', 'nickname1', '/asdf', '123', '12', 'wenfan@qq.com1', '2019-11-27', '2', 1, '2020-01-02 13:15:24', '2019-12-10 13:15:26', 1, 0, 1);
INSERT INTO `sys_user` VALUES (3, 'user12y13', '123', '$2a$10$f91HxFUF97fui41KY.JD9OoHeP7rKucTr2A3pJjryVHNBLwKmD9SS', 'nickname1', '/asdfasdf/asdf', '1415', '112', 'wuzhangai@qq.com', '2019-11-26', '2', 1, '2019-12-12 13:13:03', '2019-12-05 13:13:06', 1, 0, 1);
INSERT INTO `sys_user` VALUES (4, 'user123', '123124', '111', 'nickname1', '/asdf', '1415', '12', 'wef', '2019-11-27', '2', 1, '2020-01-02 13:15:24', '2019-12-10 13:15:26', 1, 0, 1);
INSERT INTO `sys_user` VALUES (5, 'user123rg', '123', '$2a$10$f91HxFUF97fui41KY.JD9OoHeP7rKucTr2A3pJjryVHNBLwKmD9SS', 'nickname1', '/asdfasdf/asdf', '1415', '112', 'wef', '2019-11-26', '2', 1, '2019-12-12 13:13:03', '2019-12-05 13:13:06', 1, 0, 1);
INSERT INTO `sys_user` VALUES (6, 'user12', '123124', '111', 'nickname1', '/asdf', '1415', '12', 'wef', '2019-11-27', '2', 1, '2020-01-02 13:15:24', '2019-12-10 13:15:26', 1, 0, 1);
INSERT INTO `sys_user` VALUES (7, 'hahhhhsfd', '123', '$2a$10$f91HxFUF97fui41KY.JD9OoHeP7rKucTr2A3pJjryVHNBLwKmD9SS', 'nickname', '/asdfasdf/asdf', '12412414', '112', 'wenfan@qq.com', '2019-11-26', '1', 1, '2019-12-12 13:13:03', '2019-12-05 13:13:06', 1, 0, 1);
INSERT INTO `sys_user` VALUES (8, 'user1231', '123124', '111', 'nickname1', '/asdf', '1415', '12', 'wef', '2019-11-27', '2', 1, '2020-01-02 13:15:24', '2019-12-10 13:15:26', 1, 0, 1);
INSERT INTO `sys_user` VALUES (9, 'adminq1', '123', '$2a$10$f91HxFUF97fui41KY.JD9OoHeP7rKucTr2A3pJjryVHNBLwKmD9SS', 'nickname', '/asdfasdf/asdf', '12412414', '112', 'wenfan@qq.com', '2019-11-26', '1', 1, '2019-12-12 13:13:03', '2020-01-15 20:16:37', 1, 0, 1);
INSERT INTO `sys_user` VALUES (10, 'xiugai', '123124', '111', 'nickname1', '/asdf', '1415', '12', 'wef', '2019-11-27', '2', 1, '2020-01-02 13:15:24', '2019-12-10 13:15:26', 1, 0, 1);
INSERT INTO `sys_user` VALUES (11, 'hahhhhe', '123', '$2a$10$f91HxFUF97fui41KY.JD9OoHeP7rKucTr2A3pJjryVHNBLwKmD9SS', 'nickname', '/asdfasdf/asdf', '12412414', '112', 'wenfan@qq.com', '2019-11-26', '1', 1, '2019-12-12 13:13:03', '2019-12-05 13:13:06', 1, 0, 1);
INSERT INTO `sys_user` VALUES (12, 'user123r', '123124', '111', 'nickname1', '/asdf', '1415', '12', 'wef', '2019-11-27', '2', 1, '2020-01-02 13:15:24', '2019-12-10 13:15:26', 1, 0, 1);
INSERT INTO `sys_user` VALUES (13, 'user123r13', '123', '$2a$10$f91HxFUF97fui41KY.JD9OoHeP7rKucTr2A3pJjryVHNBLwKmD9SS', 'nickname1', '/asdfasdf/asdf', '1415', '112', 'wef', '2019-11-26', '2', 1, '2019-12-12 13:13:03', '2019-12-05 13:13:06', 1, 0, 1);
INSERT INTO `sys_user` VALUES (14, '冯建强', '123124', '111', '手术', '/asdf', '1415', '12', 'wuzhangai@qq.com', '2019-11-27', '2', 1, '2020-01-02 13:15:24', '2020-01-17 17:06:30', 1, 0, 1);
INSERT INTO `sys_user` VALUES (15, 'hahhhhsfda', '123', '$2a$10$f91HxFUF97fui41KY.JD9OoHeP7rKucTr2A3pJjryVHNBLwKmD9SS', 'nickname', '/asdfasdf/asdf', '12412414', '112', 'wenfan@qq.com', '2019-11-26', '1', 1, '2019-12-12 13:13:03', '2019-12-05 13:13:06', 1, 1, 1);
INSERT INTO `sys_user` VALUES (16, 'user1231s', '123124', '111', 'nickname1', '/asdf', '1415', '12', 'wef', '2019-11-27', '2', 1, '2020-01-02 13:15:24', '2019-12-10 13:15:26', 1, 1, 1);
INSERT INTO `sys_user` VALUES (17, 'body', '', '$2a$10$nAxZbMRPLiVnesbFx9KiLeXkEpyMTyrW7DNzh2P2byngyfMpEZ9xu', 'hhhhasd', '/images/defaultHead.jpg', '18419211361', '', 'guwenfsnasdf@qq.com', '1998-03-25', '1', 1, '2020-01-17 18:57:40', '2020-01-18 07:35:06', 0, 0, 0);
INSERT INTO `sys_user` VALUES (18, 'body1', '', '$2a$10$qOPWEEjSIPIF/bQcgtTUeOYs899rauXXPdx9Y4a0aqQlMxcQL6lBi', 'hhhhasd', '/images/defaultHead.jpg', '18419211361', '', 'guwenfsnasdf@qq.com', '1998-03-25', '1', 1, '2020-01-17 19:01:27', '2020-01-18 07:34:57', 0, 0, 0);
INSERT INTO `sys_user` VALUES (20, 'baidu', '', '$2a$10$f8MFMFMF1bGhWidpIF4.U.WI1ziJklqCtAR8fyE2Lwrm5CXmacjw2', '123', '/images/defaultHead.jpg', '18419211345', '', '989sd@qq.com', '1995-08-09', '2', 1, '2020-01-18 07:42:55', '2020-01-18 07:42:55', 0, 0, 0);
INSERT INTO `sys_user` VALUES (21, 'body1v', '', '$2a$10$xqBekZag8NfvACdL9FxqweeG.C8iPf/c3JpfsMiMM9peRAO34Zpim', 'hhhhasd', '/images/defaultHead.jpg', '18419211361', '', 'guwenfsnasdf@qq.com', '1998-03-25', '1', 1, '2020-01-18 07:54:21', '2020-01-18 07:54:21', 0, 0, 0);
INSERT INTO `sys_user` VALUES (22, 'body1x', '', '$2a$10$caE54SgJQIWOwNs0vUXOMu32d4pgZgJliPEo8PuF8V3uOklJDZlCC', 'hhhhasd', '/images/defaultHead.jpg', '18419211361', '', 'guwenfsnasdf@qq.com', '1998-03-25', '1', 1, '2020-01-18 07:54:54', '2020-01-18 07:54:54', 0, 0, 0);
INSERT INTO `sys_user` VALUES (23, 'chixurizhi', '', '$2a$10$Y099UZ1pkYpdIZS5eTnGjeezeJvfOJvY4bCA7WxvNJI1RLqJvYOgq', '123', '/images/defaultHead.jpg', '1841921345', '', 'tttooo@qq.com', '1994-08-24', '1', 1, '2020-01-18 07:57:31', '2020-01-18 07:58:08', 0, 0, 0);
INSERT INTO `sys_user` VALUES (24, 'ijoijghbn', '', '$2a$10$E5OcYiX.bXKjfKErWqb/duu.KXdQLIZ3icu/GFNuQxUiY2xC5h7SC', '123', '/images/defaultHead.jpg', '18419345217', '', 'uuilasf@qq.com', '1994-04-09', '1', 1, '2020-01-18 08:07:05', '2020-01-18 08:07:05', 0, 0, 0);
INSERT INTO `sys_user` VALUES (25, 'yyasdf', '', '$2a$10$hsZA0M66/ON9tGx1JDWhDOz6F8C/6TfOBnD2sJJkQdvquXUDPVMdO', '123', '/images/defaultHead.jpg', '18491299124', '', 'sadf@qq.com', '1994-05-09', '1', 1, '2020-01-18 08:10:03', '2020-01-18 08:10:03', 0, 0, 0);
INSERT INTO `sys_user` VALUES (26, '4usdf8', '', '$2a$10$Zp6gI5l93AW3d1R51Kr00uAyWACJ5C09ZiLAdXtUdOAyZQvpWjIu.', '123', '/images/defaultHead.jpg', '124124124', '', 'sdfnnn@qq.com', '1995-07-08', '2', 1, '2020-01-18 08:13:27', '2020-01-18 08:13:27', 0, 0, 0);
INSERT INTO `sys_user` VALUES (27, 'yougun', '', '$2a$10$CjoZUaCWxpEqP1V8VK0xeOGzIG/29D0pWwZfY23RQ/ayAN9tAwc0W', '123', '/images/defaultHead.jpg', '18419211363', '', 'safkkk@qq.com', '1995-09-09', '1', 1, '2020-01-18 08:35:31', '2020-01-18 08:35:31', 0, 0, 0);
INSERT INTO `sys_user` VALUES (28, 'sadf555', '', '$2a$10$TT5o.HcR2ZANtiLbBDcCo.ccMUYPFWUStft9Ajvy9u7YZCGRnmbc.', '123', '/images/defaultHead.jpg', '12125233616', '', '12512@qq.com', '1993-09-09', '1', 1, '2020-01-18 08:37:17', '2020-01-18 08:46:00', 0, 0, 0);
INSERT INTO `sys_user` VALUES (29, '888ok', '', '$2a$10$yFarMZIo/Sjq2wt08JoUkOXwO8IDBzjy11TctPVboLn.dqmWyEP7S', '123', '/images/defaultHead.jpg', '121512515', '', 'rrr@qq.com', '1995-09-09', '1', 1, '2020-01-18 08:47:10', '2020-01-18 08:47:10', 0, 0, 0);
INSERT INTO `sys_user` VALUES (30, '00099sdf', '', '$2a$10$Nt2bDSLCvsz5nvHJCpxyKeWsrBW3h3gGZ..sYCNG9wya8BL.IaDtC', '123', '/images/defaultHead.jpg', '18189581598', '', 'asdfkljkl@qq.com', '1999-09-09', '1', 1, '2020-01-18 08:48:13', '2020-01-18 08:48:13', 0, 0, 0);
INSERT INTO `sys_user` VALUES (31, 'ttttd', '', '$2a$10$ZQQoGRqYRJ9xJD3ku9AMAeCxzzi5TgDi0vqvi6.txyTlqgI4zmDLW', '123', '/images/defaultHead.jpg', '8888124', '', 'jjsd@qq.com', '1999-09-09', '2', 1, '2020-01-18 08:50:04', '2020-01-18 11:49:36', 0, 1, 0);

SET FOREIGN_KEY_CHECKS = 1;
