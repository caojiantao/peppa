/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : ssm

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2018-01-31 17:53:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for code_set
-- ----------------------------
DROP TABLE IF EXISTS `code_set`;
CREATE TABLE `code_set` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '字典集code，不重复（方便代码英文语义化）',
  `name` varchar(255) DEFAULT NULL COMMENT '字典集名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_idx` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of code_set
-- ----------------------------

-- ----------------------------
-- Table structure for code_value
-- ----------------------------
DROP TABLE IF EXISTS `code_value`;
CREATE TABLE `code_value` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
  `code_set_id` int(11) NOT NULL COMMENT '字典集ID',
  `name` varchar(255) DEFAULT NULL COMMENT '字典名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of code_value
-- ----------------------------

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `parent_id` int(11) DEFAULT '0',
  `href` varchar(50) DEFAULT NULL,
  `icon_class` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '权限模块', '0', '', 'el-icon-tickets');
INSERT INTO `menu` VALUES ('2', '菜单管理', '1', 'Menus', null);
INSERT INTO `menu` VALUES ('3', '角色管理', '1', 'Role', '');
INSERT INTO `menu` VALUES ('4', '用户管理', '1', 'User', '');
INSERT INTO `menu` VALUES ('5', '随便爬爬', '0', null, null);
INSERT INTO `menu` VALUES ('6', '酷狗', '5', null, null);
INSERT INTO `menu` VALUES ('7', '歌曲下载', '6', 'Songs', null);
INSERT INTO `menu` VALUES ('8', '福利', '0', '', '');
INSERT INTO `menu` VALUES ('9', '学日语', '8', 'Videos', '');

-- ----------------------------
-- Table structure for quartz
-- ----------------------------
DROP TABLE IF EXISTS `quartz`;
CREATE TABLE `quartz` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `group` varchar(255) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '0',
  `cron_expre` varchar(255) DEFAULT NULL,
  `desc` varchar(255) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of quartz
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '超级管理员');
INSERT INTO `role` VALUES ('7', '福利');
INSERT INTO `role` VALUES ('8', '曹建涛');

-- ----------------------------
-- Table structure for role_menus
-- ----------------------------
DROP TABLE IF EXISTS `role_menus`;
CREATE TABLE `role_menus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(10) NOT NULL,
  `menu_id` int(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_menus
-- ----------------------------
INSERT INTO `role_menus` VALUES ('33', '8', '1');
INSERT INTO `role_menus` VALUES ('34', '8', '4');
INSERT INTO `role_menus` VALUES ('35', '7', '5');
INSERT INTO `role_menus` VALUES ('36', '7', '6');
INSERT INTO `role_menus` VALUES ('37', '7', '7');
INSERT INTO `role_menus` VALUES ('38', '1', '1');
INSERT INTO `role_menus` VALUES ('39', '1', '2');
INSERT INTO `role_menus` VALUES ('40', '1', '3');
INSERT INTO `role_menus` VALUES ('41', '1', '4');
INSERT INTO `role_menus` VALUES ('42', '1', '5');
INSERT INTO `role_menus` VALUES ('43', '1', '6');
INSERT INTO `role_menus` VALUES ('44', '1', '7');
INSERT INTO `role_menus` VALUES ('45', '1', '8');
INSERT INTO `role_menus` VALUES ('46', '1', '9');

-- ----------------------------
-- Table structure for src_ip_port
-- ----------------------------
DROP TABLE IF EXISTS `src_ip_port`;
CREATE TABLE `src_ip_port` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `src_url` varchar(255) DEFAULT NULL,
  `ip` varchar(20) DEFAULT NULL,
  `port` int(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of src_ip_port
-- ----------------------------
INSERT INTO `src_ip_port` VALUES ('2', 'https://www.ku188.top/video/4479', '123.207.25.143', '3128');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
  `username` varchar(20) DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'caojiantao', '69f50b2ff3918d3d973492fa72796449640954834347be24c23058dd8840591d', '曹建涛');
INSERT INTO `user` VALUES ('3', 'liuxinquan', '69f50b2ff3918d3d973492fa72796449640954834347be24c23058dd8840591d', '刘新全');

-- ----------------------------
-- Table structure for user_roles
-- ----------------------------
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_roles
-- ----------------------------
INSERT INTO `user_roles` VALUES ('12', '1', '1');
INSERT INTO `user_roles` VALUES ('13', '3', '1');
