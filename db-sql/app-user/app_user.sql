/*
 Navicat MySQL Data Transfer

 Source Server         : localhost_root_123456
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost
 Source Database       : appuser

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : utf-8

 Date: 07/21/2020 21:17:24 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `app_user`
-- ----------------------------
DROP TABLE IF EXISTS `app_user`;
CREATE TABLE `app_user` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL COMMENT '名字',
  `address` varchar(256) DEFAULT NULL COMMENT '地址',
  `mobile` varchar(16) DEFAULT NULL COMMENT '手机号',
  `create_time` timestamp NOT NULL COMMENT '创建时间',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` timestamp NOT NULL COMMENT '更新时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
--  Records of `app_user`
-- ----------------------------
BEGIN;
INSERT INTO `app_user` VALUES ('7', '小明', '南京', '13337717149', '2020-07-21 11:36:28', null, '2020-07-21 11:36:34', null), ('8', '11', '1', '1', '2020-07-21 11:37:42', null, '2020-07-21 11:37:47', null);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
