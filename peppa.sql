/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50528
Source Host           : 127.0.0.1:3306
Source Database       : peppa

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2018-04-10 14:19:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for dict_set
-- ----------------------------
DROP TABLE IF EXISTS `dict_set`;
CREATE TABLE `dict_set` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT '' COMMENT '唯一code标识，用作单查条件',
  `name` varchar(255) DEFAULT NULL,
  `desc` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dict_set
-- ----------------------------

-- ----------------------------
-- Table structure for dict_value
-- ----------------------------
DROP TABLE IF EXISTS `dict_value`;
CREATE TABLE `dict_value` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `set_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `order` int(11) DEFAULT '0' COMMENT '排序',
  `desc` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  KEY `idx_name` (`value`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dict_value
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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '权限管理', '0', null, null);
INSERT INTO `menu` VALUES ('2', '菜单管理', '1', 'Menus', null);
INSERT INTO `menu` VALUES ('3', '角色管理', '1', 'Role', null);
INSERT INTO `menu` VALUES ('4', '用户管理', '1', 'User', null);
INSERT INTO `menu` VALUES ('5', '定时任务', '0', '', null);
INSERT INTO `menu` VALUES ('6', '任务列表', '5', 'Job', null);
INSERT INTO `menu` VALUES ('7', '七牛存储', '0', '', null);
INSERT INTO `menu` VALUES ('8', '资源管理', '7', 'Resources', null);
INSERT INTO `menu` VALUES ('9', '随便爬爬', '0', '', null);
INSERT INTO `menu` VALUES ('10', '酷狗音乐', '9', '', null);
INSERT INTO `menu` VALUES ('11', '歌曲搜索', '10', 'Songs', null);
INSERT INTO `menu` VALUES ('12', '福利放送', '9', '', null);
INSERT INTO `menu` VALUES ('13', '妹纸图鉴', '12', 'Girls', null);
INSERT INTO `menu` VALUES ('15', '系统管理', '0', '', null);
INSERT INTO `menu` VALUES ('16', '数据字典', '15', '', null);
INSERT INTO `menu` VALUES ('17', '字典集管理', '16', 'DictSet', null);
INSERT INTO `menu` VALUES ('18', '字典值管理', '16', 'DictValue', null);

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
  `job_class` varchar(255) DEFAULT NULL,
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
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '超级管理员');

-- ----------------------------
-- Table structure for role_menus
-- ----------------------------
DROP TABLE IF EXISTS `role_menus`;
CREATE TABLE `role_menus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(10) NOT NULL,
  `menu_id` int(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_menus
-- ----------------------------
INSERT INTO `role_menus` VALUES ('44', '1', '1');
INSERT INTO `role_menus` VALUES ('45', '1', '2');
INSERT INTO `role_menus` VALUES ('46', '1', '3');
INSERT INTO `role_menus` VALUES ('47', '1', '4');
INSERT INTO `role_menus` VALUES ('48', '1', '5');
INSERT INTO `role_menus` VALUES ('49', '1', '6');
INSERT INTO `role_menus` VALUES ('50', '1', '7');
INSERT INTO `role_menus` VALUES ('51', '1', '8');
INSERT INTO `role_menus` VALUES ('52', '1', '9');
INSERT INTO `role_menus` VALUES ('53', '1', '10');
INSERT INTO `role_menus` VALUES ('54', '1', '11');
INSERT INTO `role_menus` VALUES ('55', '1', '12');
INSERT INTO `role_menus` VALUES ('56', '1', '13');
INSERT INTO `role_menus` VALUES ('57', '1', '15');
INSERT INTO `role_menus` VALUES ('58', '1', '16');
INSERT INTO `role_menus` VALUES ('59', '1', '17');
INSERT INTO `role_menus` VALUES ('60', '1', '18');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'caojiantao', '69f50b2ff3918d3d973492fa72796449640954834347be24c23058dd8840591d', '曹建涛');

-- ----------------------------
-- Table structure for user_roles
-- ----------------------------
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_roles
-- ----------------------------
INSERT INTO `user_roles` VALUES ('1', '1', '1');
