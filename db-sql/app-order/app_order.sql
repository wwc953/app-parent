/*
 Navicat MySQL Data Transfer

 Source Server         : localhost_root_123456
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost
 Source Database       : apporder

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : utf-8

 Date: 07/21/2020 22:32:07 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `app_order`
-- ----------------------------
DROP TABLE IF EXISTS `app_order`;
CREATE TABLE `app_order` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int DEFAULT NULL COMMENT '用户ID',
  `goods_id` int DEFAULT NULL COMMENT '商品ID',
  `order_id` varchar(256) NOT NULL COMMENT '订单流水ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单更新时间',
  `flag` int DEFAULT '1' COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `app_order`
-- ----------------------------
BEGIN;
INSERT INTO `app_order` VALUES ('1', '7', '1', '20200721221113', '2020-07-21 22:11:17', '2020-07-21 22:11:20', '1');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
