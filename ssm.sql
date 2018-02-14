/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost
 Source Database       : ssm

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : utf-8

 Date: 02/13/2018 22:58:16 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `code_set`
-- ----------------------------
DROP TABLE IF EXISTS `code_set`;
CREATE TABLE `code_set` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_idx` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `code_value`
-- ----------------------------
DROP TABLE IF EXISTS `code_value`;
CREATE TABLE `code_value` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code_set_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `menu`
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `parent_id` int(11) DEFAULT '0',
  `href` varchar(50) DEFAULT NULL,
  `icon_class` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `menu`
-- ----------------------------
BEGIN;
INSERT INTO `menu` VALUES ('1', '权限管理', '0', null, null), ('2', '菜单管理', '1', 'Menus', null), ('3', '角色管理', '1', 'Role', ''), ('4', '用户管理', '1', 'User', ''), ('5', '定时任务', '0', '', ''), ('6', '任务列表', '5', 'Job', ''), ('7', '七牛存储', '0', '', ''), ('8', '资源管理', '7', 'Resources', ''), ('9', '随便爬爬', '0', '', ''), ('10', '酷狗音乐', '9', '', ''), ('11', '歌曲搜索', '10', 'Songs', ''), ('12', '福利放送', '9', '', ''), ('13', '妹纸图鉴', '12', 'Girls', '');
COMMIT;

-- ----------------------------
--  Table structure for `quartz`
-- ----------------------------
DROP TABLE IF EXISTS `quartz`;
CREATE TABLE `quartz` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `group` varchar(255) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '0',
  `cron_expre` varchar(255) DEFAULT NULL,
  `desc` varchar(255) DEFAULT NULL,
  `job_class` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `role`
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES ('1', '超级管理员');
COMMIT;

-- ----------------------------
--  Table structure for `role_menus`
-- ----------------------------
DROP TABLE IF EXISTS `role_menus`;
CREATE TABLE `role_menus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(10) NOT NULL,
  `menu_id` int(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `role_menus`
-- ----------------------------
BEGIN;
INSERT INTO `role_menus` VALUES ('1', '1', '1'), ('2', '1', '2'), ('3', '1', '3'), ('4', '1', '4'), ('5', '1', '5'), ('6', '1', '6'), ('7', '1', '7'), ('8', '1', '8'), ('9', '1', '9'), ('10', '1', '10'), ('11', '1', '11'), ('12', '1', '12'), ('13', '1', '13');
COMMIT;

-- ----------------------------
--  Table structure for `src_ip_port`
-- ----------------------------
DROP TABLE IF EXISTS `src_ip_port`;
CREATE TABLE `src_ip_port` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `src_url` varchar(255) DEFAULT NULL,
  `ip` varchar(20) DEFAULT NULL,
  `port` int(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('1', 'caojiantao', '69f50b2ff3918d3d973492fa72796449640954834347be24c23058dd8840591d', '曹建涛');
COMMIT;

-- ----------------------------
--  Table structure for `user_roles`
-- ----------------------------
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `user_roles`
-- ----------------------------
BEGIN;
INSERT INTO `user_roles` VALUES ('1', '1', '1');
COMMIT;

-- ----------------------------
--  Table structure for `video`
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `vid` bigint(10) NOT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `poster_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `index_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `video_index`
-- ----------------------------
DROP TABLE IF EXISTS `video_index`;
CREATE TABLE `video_index` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `vid` varchar(10) NOT NULL,
  `index_url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
