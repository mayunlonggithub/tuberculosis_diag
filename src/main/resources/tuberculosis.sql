/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50646
Source Host           : localhost:3306
Source Database       : tuberculosis

Target Server Type    : MYSQL
Target Server Version : 50646
File Encoding         : 65001

Date: 2021-03-06 12:27:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for patient_user
-- ----------------------------
DROP TABLE IF EXISTS `patient_user`;
CREATE TABLE `patient_user` (
  `id` int(10) NOT NULL COMMENT '患者id',
  `description` varchar(60) DEFAULT NULL,
  `judge` char(2) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` int(11) DEFAULT NULL COMMENT '是否删除',
  `doctor_num` int(11) DEFAULT NULL,
  `patient_num` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of patient_user
-- ----------------------------
INSERT INTO `patient_user` VALUES ('53', '近日一直在咳嗽啊啊啊', '是', '2021-03-04 17:26:55', '2021-03-04 17:47:51', '1', '252', '253');
INSERT INTO `patient_user` VALUES ('54', '近日一直在咳嗽啊啊啊', '否', '2021-03-05 09:35:25', '2021-03-05 09:35:30', '1', '254', '253');

-- ----------------------------
-- Table structure for r_user_role
-- ----------------------------
DROP TABLE IF EXISTS `r_user_role`;
CREATE TABLE `r_user_role` (
  `id` int(11) NOT NULL DEFAULT '0' COMMENT 'id',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of r_user_role
-- ----------------------------
INSERT INTO `r_user_role` VALUES ('1', '1', '1');
INSERT INTO `r_user_role` VALUES ('2', '2', '2');
INSERT INTO `r_user_role` VALUES ('3', '3', '2');
INSERT INTO `r_user_role` VALUES ('4', '4', '2');
INSERT INTO `r_user_role` VALUES ('5', '5', '2');
INSERT INTO `r_user_role` VALUES ('6', '6', '3');
INSERT INTO `r_user_role` VALUES ('7', '7', '3');
INSERT INTO `r_user_role` VALUES ('8', '8', '3');
INSERT INTO `r_user_role` VALUES ('9', '9', '3');

-- ----------------------------
-- Table structure for tuber_image
-- ----------------------------
DROP TABLE IF EXISTS `tuber_image`;
CREATE TABLE `tuber_image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `doctor_num` double DEFAULT NULL COMMENT '医生ID',
  `patient_num` double DEFAULT NULL COMMENT '病患ID',
  `relative_path` varchar(100) DEFAULT NULL COMMENT '图片相对路径',
  `image_name` varchar(255) DEFAULT NULL COMMENT '图片名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(11) DEFAULT NULL COMMENT '是否删除',
  `diagnosis_record` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=311 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tuber_image
-- ----------------------------
INSERT INTO `tuber_image` VALUES ('308', '253', '252', 'image/254/253/20210305170623_测试图片1 - 副本.png', '20210305170623_测试图片1 - 副本.png', '2021-03-05 17:06:24', '2021-03-06 09:43:55', '0', '修改后的诊疗记录');
INSERT INTO `tuber_image` VALUES ('309', '254', '252', 'image/254/253/20210305170623_测试图片1.png', '20210305170623_测试图片1.png', '2021-03-05 17:06:24', '2021-03-05 17:06:24', '1', '诊断记录嗷嗷');
INSERT INTO `tuber_image` VALUES ('310', '253', '254', 'image/254/253/20210305170623_测试图片2.png', '20210305170623_测试图片2.png', '2021-03-05 17:06:24', '2021-03-05 17:23:28', '1', '诊断记录嗷嗷');

-- ----------------------------
-- Table structure for t_id_generator
-- ----------------------------
DROP TABLE IF EXISTS `t_id_generator`;
CREATE TABLE `t_id_generator` (
  `id_key` varchar(30) NOT NULL,
  `id_value` int(11) NOT NULL,
  PRIMARY KEY (`id_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_id_generator
-- ----------------------------
INSERT INTO `t_id_generator` VALUES ('doctorUser', '350');
INSERT INTO `t_id_generator` VALUES ('patientInf', '150');
INSERT INTO `t_id_generator` VALUES ('tuberImage', '400');
INSERT INTO `t_id_generator` VALUES ('user', '350');
INSERT INTO `t_id_generator` VALUES ('userRole', '350');

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `id` int(11) NOT NULL COMMENT 'id',
  `pattern` varchar(255) DEFAULT NULL COMMENT 'URL',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` int(11) DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES ('1', '/cde/getUser/**', null, null, '1');
INSERT INTO `t_menu` VALUES ('2', '/overView/**', null, null, '1');
INSERT INTO `t_menu` VALUES ('3', '/patient/**', null, null, '1');
INSERT INTO `t_menu` VALUES ('4', '/patient/getList', null, null, null);
INSERT INTO `t_menu` VALUES ('5', '/image/**', null, null, null);
INSERT INTO `t_menu` VALUES ('6', '/image/url/**', null, null, null);

-- ----------------------------
-- Table structure for t_menu_role
-- ----------------------------
DROP TABLE IF EXISTS `t_menu_role`;
CREATE TABLE `t_menu_role` (
  `id` int(11) NOT NULL,
  `menu_id` int(11) DEFAULT NULL COMMENT '资源id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_menu_role
-- ----------------------------
INSERT INTO `t_menu_role` VALUES ('1', '1', '1');
INSERT INTO `t_menu_role` VALUES ('2', '2', '2');
INSERT INTO `t_menu_role` VALUES ('3', '3', '3');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int(11) NOT NULL COMMENT '角色Id',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `description` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` int(11) DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', 'admin', '管理员', null, null, '1');
INSERT INTO `t_role` VALUES ('2', 'doctor', '医生', null, null, '1');
INSERT INTO `t_role` VALUES ('3', 'patient', '病人', null, null, '1');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL COMMENT '用户ID',
  `nickname` varchar(50) DEFAULT NULL COMMENT '用户昵称',
  `email` varchar(30) DEFAULT NULL COMMENT '用户邮箱',
  `phone` varchar(50) DEFAULT NULL COMMENT '用户电话',
  `account` varchar(50) DEFAULT NULL COMMENT '用户账号',
  `password` varchar(255) DEFAULT NULL COMMENT '用户密码',
  `create_time` datetime NOT NULL COMMENT '添加时间',
  `modify_time` datetime NOT NULL COMMENT '编辑时间',
  `del_flag` int(11) DEFAULT NULL COMMENT '是否删除（1：存在；0：删除）',
  `role_type` int(11) DEFAULT NULL COMMENT '角色类型',
  `employee_id` varchar(50) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `if_access` int(11) DEFAULT NULL,
  `gender` char(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('252', '张三', '21860218@zju.edu.cn', '18251930771', '340122199403286174', 'b24140cbc2487839a3ab0ecb48f5a379', '2021-03-04 11:08:16', '2021-03-05 10:38:09', '1', '2', '21860218', '22', '1', '男');
INSERT INTO `t_user` VALUES ('253', '张三', '1104096818', '18251930771', '340122199403286175', 'b24140cbc2487839a3ab0ecb48f5a379', '2021-03-04 11:11:44', '2021-03-05 10:32:03', '1', '3', '21860218', '27', '1', '女');
INSERT INTO `t_user` VALUES ('254', '张三', '1104096818', '18251930771', '340122199403286176', 'b24140cbc2487839a3ab0ecb48f5a379', '2021-03-04 11:11:54', '2021-03-04 11:11:54', '1', '2', '21860218', '56', '1', '女');
