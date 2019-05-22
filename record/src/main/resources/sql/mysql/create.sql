-- ----------------------------
-- Table structure for tr_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `tr_role_menu`;
CREATE TABLE `tr_role_menu` (
  `ROLE_ID` int(11) NOT NULL,
  `MENU_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tr_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tr_user_role`;
CREATE TABLE `tr_user_role` (
  `USER_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ts_menu
-- ----------------------------
DROP TABLE IF EXISTS `ts_menu`;
CREATE TABLE `ts_menu` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(64) DEFAULT NULL,
  `CODE` varchar(64) DEFAULT NULL,
  `PERMISSION_CODE` varchar(64) DEFAULT NULL,
  `PARENT_CODE` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ts_role
-- ----------------------------
DROP TABLE IF EXISTS `ts_role`;
CREATE TABLE `ts_role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_CODE` varchar(64) DEFAULT NULL,
  `ROLE_NAME` varchar(64) DEFAULT NULL,
  `MARK_FOR_DELETE` char(1) DEFAULT NULL COMMENT '逻辑删除 0存在  1删除',
  `OPT_COUNTER` int(11) DEFAULT 0 NOT NULL COMMENT '修改次数，版本',
  `CREATE_USER` int(11) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_USER` int(11) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ts_user
-- ----------------------------
DROP TABLE IF EXISTS `ts_user`;
CREATE TABLE `ts_user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACCOUNT` varchar(64) DEFAULT NULL COMMENT '账户',
  `PASSWORD` varchar(64) DEFAULT NULL COMMENT '密码',
  `HISTORY_PWD` varchar(64) DEFAULT NULL COMMENT '之前一次密码',
  `USER_NAME` varchar(32) DEFAULT NULL COMMENT '用户名',
  `PHONE` varchar(32) DEFAULT NULL COMMENT '电话',
  `EMAIL` varchar(64) DEFAULT NULL COMMENT 'e-mail',
  `GENDER` char(1) DEFAULT NULL COMMENT '性别 M|F|U  男|女|未知',
  `LAST_LOGIN_DATE` datetime DEFAULT NULL COMMENT '最后登入时间',
  `IS_ENABLED` char(1) DEFAULT NULL,
  `CREATE_USER` int(11) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_USER` int(11) DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  `MARK_FOR_DELETE` char(1) DEFAULT NULL COMMENT '逻辑删除 0存在  1删除',
  `OPT_COUNTER` int(11) DEFAULT 0 NOT NULL COMMENT '修改次数，版本',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ts_dict_entry
-- ----------------------------
DROP TABLE IF EXISTS `ts_dict_entry`;
CREATE TABLE `ts_dict_entry` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TYPE_ID` int(11) DEFAULT NULL,
  `ENTRY_CODE` varchar(64) DEFAULT NULL,
  `ENTRY_NAME` varchar(128) DEFAULT NULL,
   ENTRY_NAME_EN varchar(128) DEFAULT NULL,
  `ORDER_NO` int(11) DEFAULT NULL,
  `MARK_FOR_DELETE` char(1) DEFAULT NULL,
  `OPT_COUNTER` int(11) DEFAULT 0,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ts_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `ts_dict_type`;
CREATE TABLE `ts_dict_type` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TYPE_CODE` varchar(64) DEFAULT NULL,
  `TYPE_NAME` varchar(128) DEFAULT NULL,
  `MARK_FOR_DELETE` char(1) DEFAULT NULL,
  `OPT_COUNTER` int(11) DEFAULT 0,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ts_permission_button
-- ----------------------------
DROP TABLE IF EXISTS `ts_permission_button`;
CREATE TABLE `ts_permission_button` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PERMISSION_TABLE` varchar(64) DEFAULT NULL,
  `PERMISSION_CODE` varchar(64) DEFAULT NULL,
  `PERMISSION_NAME` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ts_permission_table
-- ----------------------------
DROP TABLE IF EXISTS `ts_permission_table`;
CREATE TABLE `ts_permission_table` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PERMISSION_TABLE` varchar(64) DEFAULT NULL,
  `PERMISSION_CODE` varchar(64) DEFAULT NULL,
  `PERMISSION_NAME` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tr_role_permission_button
-- ----------------------------
DROP TABLE IF EXISTS `tr_role_permission_button`;
CREATE TABLE `tr_role_permission_button` (
  `ROLE_ID` int(11) NOT NULL,
  `PERMISSION_BUTTON_ID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tr_role_permission_table
-- ----------------------------
DROP TABLE IF EXISTS `tr_role_permission_table`;
CREATE TABLE `tr_role_permission_table` (
  `ROLE_ID` int(11) NOT NULL,
  `PERMISSION_TABLE_ID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

