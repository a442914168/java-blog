/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Version : 50640
 Source Host           : localhost
 Source Database       : mybatis

 Target Server Version : 50640
 File Encoding         : utf-8

 Date: 07/27/2018 22:30:29 PM
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
  `d_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_emp_dept` (`d_id`),
  CONSTRAINT `fk_emp_dept` FOREIGN KEY (`d_id`) REFERENCES `tbl_dept` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `tbl_employee`
-- ----------------------------
BEGIN;
INSERT INTO `tbl_employee` VALUES ('11', 'jerry4', '1', 'hbyouxiang@qq.com', '1'), ('12', 'jerry4', '1', 'xjyouxiang@qq.com', '2'), ('13', 'jerry4', '1', 'xjyouxiang@qq.com', '1');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
