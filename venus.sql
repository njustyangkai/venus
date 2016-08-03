/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50630
Source Host           : localhost:3306
Source Database       : venus

Target Server Type    : MYSQL
Target Server Version : 50630
File Encoding         : 65001

Date: 2016-07-31 19:48:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `v_event`
-- ----------------------------
DROP TABLE IF EXISTS `v_event`;
CREATE TABLE `v_event` (
  `id` varchar(100) NOT NULL,
  `student_id` varchar(100) NOT NULL,
  `student_name` varchar(100) NOT NULL,
  `teacher_id` varchar(100) NOT NULL,
  `teacher_name` varchar(100) NOT NULL,
  `color` varchar(100) NOT NULL,
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of v_event
-- ----------------------------

-- ----------------------------
-- Table structure for `v_student`
-- ----------------------------
DROP TABLE IF EXISTS `v_student`;
CREATE TABLE `v_student` (
  `id` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `grade` varchar(100) NOT NULL,
  `sex` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `birthday` date NOT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `parent_phone` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of v_student
-- ----------------------------

-- ----------------------------
-- Table structure for `v_user`
-- ----------------------------
DROP TABLE IF EXISTS `v_user`;
CREATE TABLE `v_user` (
  `id` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `lastlogtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `state` int(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of v_user
-- ----------------------------
INSERT INTO `v_user` VALUES ('c20a89a9-8632-47d1-ab0b-2bf2946c37b0', 'hanxiao', 'hanxiao75', '2016-07-17 15:45:10', '2016-07-31 19:43:04', '1');

-- ----------------------------
-- Table structure for `v_user_auth`
-- ----------------------------
DROP TABLE IF EXISTS `v_user_auth`;
CREATE TABLE `v_user_auth` (
  `user_id` varchar(100) NOT NULL,
  `auth_type` int(10) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of v_user_auth
-- ----------------------------
INSERT INTO `v_user_auth` VALUES ('c20a89a9-8632-47d1-ab0b-2bf2946c37b0', '0');
