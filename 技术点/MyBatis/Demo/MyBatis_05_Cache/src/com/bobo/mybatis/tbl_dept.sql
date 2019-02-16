/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Version : 50640
 Source Host           : localhost
 Source Database       : mybatis

 Target Server Version : 50640
 File Encoding         : utf-8

 Date: 07/27/2018 22:29:43 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `tbl_dept`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_dept`;
CREATE TABLE `tbl_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dept_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `tbl_dept`
-- ----------------------------
BEGIN;
INSERT INTO `tbl_dept` VALUES ('1', '开发部'), ('2', '人事部');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
