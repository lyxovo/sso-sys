/*
Navicat MariaDB Data Transfer

Source Server         : fapon
Source Server Version : 100017
Source Host           : localhost:3306
Source Database       : sso_data

Target Server Type    : MariaDB
Target Server Version : 100017
File Encoding         : 65001

Date: 2019-09-06 15:35:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '密码，加密存储',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('3', '张三2', '12233');
INSERT INTO `user` VALUES ('4', '张三3', '12233');
INSERT INTO `user` VALUES ('5', '张三4', '12233');
INSERT INTO `user` VALUES ('6', '张三5', '1223');
INSERT INTO `user` VALUES ('7', 'admin234', '1233');
INSERT INTO `user` VALUES ('8', '阿萨德', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('9', 'asf', 'c4ca4238a0b923820dcc509a6f75849b');
INSERT INTO `user` VALUES ('10', 'admin123', '289dff07669d7a23de0ef88d2f7129e7');
INSERT INTO `user` VALUES ('11', 'admin21', '202cb962ac59075b964b07152d234b70');
INSERT INTO `user` VALUES ('13', 'asfddd', 'c4ca4238a0b923820dcc509a6f75849b');
INSERT INTO `user` VALUES ('14', 'asdf3333', 'c4ca4238a0b923820dcc509a6f75849b');
INSERT INTO `user` VALUES ('15', 'admin23', 'c4ca4238a0b923820dcc509a6f75849b');
INSERT INTO `user` VALUES ('17', 'admin', 'c4ca4238a0b923820dcc509a6f75849b');
