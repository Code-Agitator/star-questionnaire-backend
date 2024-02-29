/*
 Navicat Premium Data Transfer

 Source Server         : docker
 Source Server Type    : MySQL
 Source Server Version : 80200 (8.2.0)
 Source Host           : localhost:3306
 Source Schema         : questionnaire

 Target Server Type    : MySQL
 Target Server Version : 80200 (8.2.0)
 File Encoding         : 65001

 Date: 29/02/2024 18:35:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for acl_class
-- ----------------------------
DROP TABLE IF EXISTS `acl_class`;
CREATE TABLE `acl_class`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `class` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_uk_2`(`class` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of acl_class
-- ----------------------------
INSERT INTO `acl_class` VALUES (1, 'pers.star.questionnaire.pojo.user.SysUser');

-- ----------------------------
-- Table structure for acl_entry
-- ----------------------------
DROP TABLE IF EXISTS `acl_entry`;
CREATE TABLE `acl_entry`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `acl_object_identity` bigint NOT NULL,
  `ace_order` int NOT NULL,
  `sid` bigint NOT NULL,
  `mask` int NOT NULL,
  `granting` tinyint(1) NOT NULL,
  `audit_success` tinyint(1) NOT NULL,
  `audit_failure` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_uk_4`(`acl_object_identity` ASC, `ace_order` ASC) USING BTREE,
  INDEX `sid`(`sid` ASC) USING BTREE,
  CONSTRAINT `acl_entry_ibfk_1` FOREIGN KEY (`acl_object_identity`) REFERENCES `acl_object_identity` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `acl_entry_ibfk_2` FOREIGN KEY (`sid`) REFERENCES `acl_sid` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of acl_entry
-- ----------------------------
INSERT INTO `acl_entry` VALUES (1, 1, 1, 1, 1, 1, 1, 1);

-- ----------------------------
-- Table structure for acl_object_identity
-- ----------------------------
DROP TABLE IF EXISTS `acl_object_identity`;
CREATE TABLE `acl_object_identity`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `object_id_class` bigint NOT NULL,
  `object_id_identity` bigint NOT NULL,
  `parent_object` bigint NULL DEFAULT NULL,
  `owner_sid` bigint NULL DEFAULT NULL,
  `entries_inheriting` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_uk_3`(`object_id_class` ASC, `object_id_identity` ASC) USING BTREE,
  INDEX `parent_object`(`parent_object` ASC) USING BTREE,
  INDEX `owner_sid`(`owner_sid` ASC) USING BTREE,
  CONSTRAINT `acl_object_identity_ibfk_1` FOREIGN KEY (`parent_object`) REFERENCES `acl_object_identity` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `acl_object_identity_ibfk_2` FOREIGN KEY (`object_id_class`) REFERENCES `acl_class` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `acl_object_identity_ibfk_3` FOREIGN KEY (`owner_sid`) REFERENCES `acl_sid` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of acl_object_identity
-- ----------------------------
INSERT INTO `acl_object_identity` VALUES (1, 1, 1, NULL, 1, 0);

-- ----------------------------
-- Table structure for acl_sid
-- ----------------------------
DROP TABLE IF EXISTS `acl_sid`;
CREATE TABLE `acl_sid`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `principal` tinyint(1) NOT NULL,
  `sid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_uk_1`(`sid` ASC, `principal` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of acl_sid
-- ----------------------------
INSERT INTO `acl_sid` VALUES (2, 0, 'ROLE_ADMIN');
INSERT INTO `acl_sid` VALUES (1, 1, 'root');

-- ----------------------------
-- Table structure for answer
-- ----------------------------
DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `question_id` int NOT NULL,
  `answers` json NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of answer
-- ----------------------------

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` bigint NOT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `desc` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `js` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `css` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `is_published` tinyint(1) NULL DEFAULT NULL,
  `component_list` json NULL,
  `is_star` tinyint(1) NULL DEFAULT 0,
  `is_deleted` tinyint(1) NULL DEFAULT 0,
  `answer_count` int NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES (3, '新的问卷', '问卷的详细描述', NULL, NULL, 1, '[{\"feId\": \"c2\", \"type\": \"questionTitle\", \"props\": {\"text\": \"个人信息调研\", \"level\": 1, \"isCenter\": false}, \"title\": \"标题\", \"hidden\": false, \"locked\": false}, {\"feId\": \"c1\", \"type\": \"questionInfo\", \"props\": {\"desc\": \"问卷描述\", \"title\": \"问卷标题\"}, \"title\": \"问卷标题\", \"hidden\": false, \"locked\": false}, {\"feId\": \"c2\", \"type\": \"questionTitle\", \"props\": {\"text\": \"个人信息调研\", \"level\": 1, \"isCenter\": false}, \"title\": \"标题\", \"hidden\": false, \"locked\": false}, {\"feId\": null, \"type\": \"questionRadio\", \"props\": {\"title\": \"单选标题\", \"value\": \"\", \"options\": [{\"text\": \"A\", \"value\": \"item1\"}, {\"text\": \"B\", \"value\": \"item2\"}, {\"text\": \"C\", \"value\": \"item3\"}], \"isVertical\": false}, \"title\": \"单选标题\", \"hidden\": false, \"locked\": false}, {\"feId\": \"c2\", \"type\": \"questionTitle\", \"props\": {\"text\": \"个人信息调研\", \"level\": 1, \"isCenter\": false}, \"title\": \"标题\", \"hidden\": false, \"locked\": false}, {\"feId\": \"c2\", \"type\": \"questionTitle\", \"props\": {\"text\": \"个人信息调研\", \"level\": 1, \"isCenter\": false}, \"title\": \"标题\", \"hidden\": false, \"locked\": false}]', 1, 0, 0);
INSERT INTO `question` VALUES (4, '新的问卷', '问卷的详细描述', NULL, NULL, 0, '[{\"feId\": \"c1\", \"type\": \"questionInfo\", \"props\": {\"desc\": \"问卷描述\", \"title\": \"问卷标题\"}, \"title\": \"问卷标题\", \"hidden\": false, \"locked\": false}, {\"feId\": \"c2\", \"type\": \"questionTitle\", \"props\": {\"text\": \"个人信息调研\", \"level\": 1, \"isCenter\": false}, \"title\": \"标题\", \"hidden\": false, \"locked\": false}, {\"feId\": \"c3\", \"type\": \"questionInput\", \"props\": {\"title\": \"你的姓名\", \"placeholder\": \"请输入姓名\"}, \"title\": \"输入框1\", \"hidden\": false, \"locked\": false}]', 0, 0, 0);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `wx_openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$10$DtcuHViRcrAGbnXd7.TQeu823Wt84Gid2HF1yN9n0idlrSkHYTGJ6', NULL, '管理员');

SET FOREIGN_KEY_CHECKS = 1;
