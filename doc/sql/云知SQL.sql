-- -----------------------------------------------------
-- 云知建表SQL
-- -----------------------------------------------------

-- -----------------------------------------------------
-- 角色表
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ROLE` (
  `ROLEID` INT NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `ROLENAME` VARCHAR(64) NULL COMMENT '角色名称',
  `CONTENT` NVARCHAR(100) NULL COMMENT '角色描述',
  `CREATETIME` DATETIME(3) NULL COMMENT '创建时间',
  `UPDATETIME` DATETIME(3) NULL COMMENT '修改时间',
  `LOCKED` TINYINT NULL DEFAULT 0 COMMENT '是否被锁定，0否1是',
  PRIMARY KEY (`ROLEID`))
;

-- -----------------------------------------------------
-- 用户表
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `USER` (
  `ID` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `USERNAME` VARCHAR(20) NULL COMMENT '用户名',
  `PASSWORD` VARCHAR(50) NULL COMMENT '密码',
  `PHONE` VARCHAR(11) NULL COMMENT '手机号',
  `SIGNATURE` NVARCHAR(100) NULL COMMENT '签名',
  `CREATETIME` DATETIME(3) NULL COMMENT '创建时间',
  `UPDATETIME` DATETIME(3) NULL COMMENT '更新时间',
  `LOCKED` TINYINT NULL COMMENT '是否都被锁定',
  `SALT` VARCHAR(50) NULL COMMENT '密码加盐',
  `ROLEID` INT  COMMENT '角色ID',
  PRIMARY KEY (`ID`))
;

-- -----------------------------------------------------
-- 问题表
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QUESTION` (
  `QID` INT NOT NULL AUTO_INCREMENT COMMENT '问题ID',
  `TITLE` NVARCHAR(200) NULL COMMENT '问题标题',
  `TEXT` VARCHAR(500) NULL COMMENT '问题描述',
  `USERID` INT NOT NULL COMMENT '提问用户ID',
  `CREATETIME` DATETIME(3) NULL COMMENT '创建时间',
  `UPDATETIME` DATETIME(3) NULL COMMENT '修改时间',
  PRIMARY KEY (`QID`))
;

-- -----------------------------------------------------
-- 回答表
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ANSWER` (
  `AID` INT NOT NULL AUTO_INCREMENT COMMENT '回答主键ID',
  `TEXT` NVARCHAR(5000) NULL COMMENT '\'回答内容\'',
  `QID` INT NOT NULL COMMENT '回答的问题ID',
  `USERID` INT NOT NULL COMMENT '回答的用户ID',
  `CREATETIME` DATETIME(3) NULL COMMENT '创建时间',
  `STAR` INT NULL COMMENT '点赞数',
  PRIMARY KEY (`AID`))
;

-- -----------------------------------------------------
-- 关注表
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `USER_ATTECTION` (
  `ATID` INT NOT NULL AUTO_INCREMENT COMMENT '用户关注问题主键ID',
  `USERID` INT NOT NULL COMMENT '用户ID',
  `QID` INT NOT NULL COMMENT '问题ID',
  `CREATETIME` DATETIME(3) NULL COMMENT '关注时间',
  PRIMARY KEY (`ATID`))
;


-- -----------------------------------------------------
-- 用户收藏表
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `USER_COLLECTION` (
  `CID` INT NOT NULL AUTO_INCREMENT COMMENT '用户收藏回答主键ID',
  `USERID` INT NOT NULL COMMENT '用户ID',
  `AID` INT NOT NULL COMMENT '回答ID',
  `CREATETIME` DATETIME(3) NULL COMMENT '收藏时间',
  PRIMARY KEY (`CID`))
;

-- -----------------------------------------------------
-- 话题表
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TOPIC` (
  `TID` INT NOT NULL AUTO_INCREMENT COMMENT '话题ID',
  `TITLE` VARCHAR(64) NULL COMMENT '\'话题名称\'',
  `PTID` INT NULL COMMENT '父级话题',
  PRIMARY KEY (`TID`))
;


-- -----------------------------------------------------
-- 问题、话题关联表
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QUESTION_TOPIC` (
  `QTID` INT NOT NULL AUTO_INCREMENT COMMENT '问题关联话题ID',
  `QID` INT NOT NULL COMMENT '问题ID',
  `TID` INT NOT NULL COMMENT '话题ID',
  PRIMARY KEY (`QTID`))
;

-- -----------------------------------------------------
-- 附件表
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS FILEUPLOAD
(
	FID 	INT NOT NULL AUTO_INCREMENT COMMENT '文件记录主键ID',
	USERID 	INT 			COMMENT '关联用户ID',
	FNAME 	VARCHAR(200)	COMMENT '文件名称',
	FPATH 	VARCHAR(500)	COMMENT '文件绝对路径',
	FSPATH 	VARCHAR(500)	COMMENT '文件相对路径',
	FCODE 	VARCHAR(100)	COMMENT '文件关联的业务标识',
	FTYPE 	VARCHAR(50)		COMMENT '文件类型',
	TIME 	DATETIME(3)		COMMENT '文件上传时间',
	SIZE 	DECIMAL			COMMENT '文件大小',
	PRIMARY KEY (`FID`)
);

