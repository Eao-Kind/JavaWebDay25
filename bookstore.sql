/*
 Navicat Premium Data Transfer

 Source Server         : db
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : localhost:3306
 Source Schema         : bookstore

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 27/08/2020 21:50:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `bid` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bname` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `price` decimal(5, 2) NULL DEFAULT NULL,
  `author` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `image` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cid` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `del` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`bid`) USING BTREE,
  INDEX `cid`(`cid`) USING BTREE,
  CONSTRAINT `book_ibfk_1` FOREIGN KEY (`cid`) REFERENCES `category` (`cid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('1', 'Java编程思想（第2版）', 75.76, 'qdmmy6', 'book_img/9317290-1_l.jpg', '1', 0);
INSERT INTO `book` VALUES ('2', 'Java核心技术卷1', 68.50, 'qdmmy6', 'book_img/20285763-1_l.jpg', '1', 0);
INSERT INTO `book` VALUES ('3', 'Java就业培训教程', 39.90, '张孝祥', 'book_img/8758723-1_l.jpg', '1', 0);
INSERT INTO `book` VALUES ('4', 'Head First java', 47.50, '（美）塞若', 'book_img/9265169-1_l.jpg', '1', 0);
INSERT INTO `book` VALUES ('5', 'JavaWeb开发详解', 83.30, '孙鑫', 'book_img/22788412-1_l.jpg', '2', 0);
INSERT INTO `book` VALUES ('6', 'Struts2深入详解', 63.20, '孙鑫', 'book_img/20385925-1_l.jpg', '2', 0);
INSERT INTO `book` VALUES ('7', '精通Hibernate', 30.00, '孙卫琴', 'book_img/8991366-1_l.jpg', '2', 0);
INSERT INTO `book` VALUES ('8', '精通Spring2.x', 63.20, '陈华雄', 'book_img/20029394-1_l.jpg', '2', 0);
INSERT INTO `book` VALUES ('9', 'Javascript权威指南', 93.60, '（美）弗兰纳根', 'book_img/22722790-1_l.jpg', '3', 1);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `cid` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `cname` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`cid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('1', 'JavaSE分类1');
INSERT INTO `category` VALUES ('2', 'JavaEE');
INSERT INTO `category` VALUES ('3', 'Javascript');

-- ----------------------------
-- Table structure for orderitem
-- ----------------------------
DROP TABLE IF EXISTS `orderitem`;
CREATE TABLE `orderitem`  (
  `iid` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `count` int(11) NULL DEFAULT NULL,
  `subtotal` decimal(10, 2) NULL DEFAULT NULL,
  `oid` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bid` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`iid`) USING BTREE,
  INDEX `oid`(`oid`) USING BTREE,
  INDEX `bid`(`bid`) USING BTREE,
  CONSTRAINT `orderitem_ibfk_1` FOREIGN KEY (`oid`) REFERENCES `orders` (`oid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `orderitem_ibfk_2` FOREIGN KEY (`bid`) REFERENCES `book` (`bid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orderitem
-- ----------------------------
INSERT INTO `orderitem` VALUES ('1E3E3B6F6D7240D88DE160FE5FF2D5B2', 1, 76.00, 'D17CBD98628341089C05979D137B02C7', '1');
INSERT INTO `orderitem` VALUES ('2FD1462B5F02464583AD3B624AC84555', 1, 76.00, 'A285D4F064B64B01BDDBEFDA8943C68E', '1');
INSERT INTO `orderitem` VALUES ('443AE075C62E4AFDAA15D5F58C76D497', 1, 75.60, '4549F7DC503B4E6CBE92517936626D3D', '1');
INSERT INTO `orderitem` VALUES ('52D8E0A60BEB4021A6F2F285FC1D2B26', 1, 75.60, '3C36DAD75D244BABBAE49F0189DCF247', '1');
INSERT INTO `orderitem` VALUES ('6AF9423BE30344C493F52A203CF71797', 1, 76.00, '282BF66552044692BAA26A1FFB80B5B5', '1');
INSERT INTO `orderitem` VALUES ('73E2AD9D06E343D8B1C67BF6C141E8FC', 1, 75.60, '85CFABEC0BD147D8A8B98CE16606F9BE', '1');
INSERT INTO `orderitem` VALUES ('9B0E37BAE8834042AF5ED3F2EDDC4C4B', 1, 69.00, 'C946AFE32C824C5787FA8F935A5A58D5', '2');
INSERT INTO `orderitem` VALUES ('A37C5E30824C4036953011738377B830', 1, 76.00, '3C87BE1D9AB4429BAF905727350C5479', '1');
INSERT INTO `orderitem` VALUES ('A88F3ECB5723456C9031C680872C11AC', 1, 69.00, 'BDB01F99B9FC476A9CB626B36F758123', '2');
INSERT INTO `orderitem` VALUES ('B46B85150CBD4B049483BAF73105E17F', 1, 76.00, 'F962D3D7E49F40E586A48225091BC5F4', '1');
INSERT INTO `orderitem` VALUES ('C30E93D5937A4E1DB047967EFE7C4DB3', 1, 76.00, 'C946AFE32C824C5787FA8F935A5A58D5', '1');
INSERT INTO `orderitem` VALUES ('E8A9DCBB274B49A1859196E218659EFE', 1, 76.00, 'BDB01F99B9FC476A9CB626B36F758123', '1');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `oid` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ordertime` datetime(0) NULL DEFAULT NULL,
  `total` decimal(10, 2) NULL DEFAULT NULL,
  `state` smallint(1) NULL DEFAULT NULL,
  `uid` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`oid`) USING BTREE,
  INDEX `uid`(`uid`) USING BTREE,
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `tb_user` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('282BF66552044692BAA26A1FFB80B5B5', '2020-08-25 13:22:53', 76.00, 4, '1', NULL);
INSERT INTO `orders` VALUES ('3C36DAD75D244BABBAE49F0189DCF247', '2020-08-25 13:33:06', 75.60, 1, '1', NULL);
INSERT INTO `orders` VALUES ('3C87BE1D9AB4429BAF905727350C5479', '2020-08-25 13:24:02', 76.00, 3, '1', NULL);
INSERT INTO `orders` VALUES ('4549F7DC503B4E6CBE92517936626D3D', '2020-08-25 22:39:22', 75.60, 1, '1', NULL);
INSERT INTO `orders` VALUES ('85CFABEC0BD147D8A8B98CE16606F9BE', '2020-08-25 15:44:36', 75.60, 1, '1', NULL);
INSERT INTO `orders` VALUES ('A285D4F064B64B01BDDBEFDA8943C68E', '2020-08-25 13:26:05', 76.00, 1, '1', NULL);
INSERT INTO `orders` VALUES ('BDB01F99B9FC476A9CB626B36F758123', '2020-08-25 13:19:50', 144.00, 1, '1', NULL);
INSERT INTO `orders` VALUES ('C946AFE32C824C5787FA8F935A5A58D5', '2020-08-25 13:21:15', 144.00, 1, '1', NULL);
INSERT INTO `orders` VALUES ('D17CBD98628341089C05979D137B02C7', '2020-08-25 13:23:30', 76.00, 1, '1', NULL);
INSERT INTO `orders` VALUES ('F962D3D7E49F40E586A48225091BC5F4', '2020-08-25 13:24:42', 76.00, 1, '1', NULL);

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `uid` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `code` char(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `state` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('1', 'admin', 'admin', '11', 'dada', 1);
INSERT INTO `tb_user` VALUES ('84AC3A52349C47FEA6F21D055B4584D8', 'eao', 'eao', '1772400891@qq.com', '7A5EE77327A1476482D3AE6B0D47F54223A493B7F3B342CB96363544ABB8BA77', 0);

SET FOREIGN_KEY_CHECKS = 1;
