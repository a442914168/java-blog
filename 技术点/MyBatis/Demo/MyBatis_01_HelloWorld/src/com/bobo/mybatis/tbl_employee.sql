/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Version : 50640
 Source Host           : localhost
 Source Database       : mybatis

 Target Server Version : 50640
 File Encoding         : utf-8

 Date: 07/22/2018 14:49:55 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `tbl_employee`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_employee`;
CREATE TABLE `tbl_employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `last_name` varchar(255) DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `tbl_employee`
-- ----------------------------
BEGIN;
INSERT INTO `tbl_employee` VALUES ('1', 'bobo', '1', 'bobo@qq.com');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
