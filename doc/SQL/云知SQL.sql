
-- -----------------------------------------------------
-- Table `ROLE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ROLE` (
  `roleid` INT NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `rolename` VARCHAR(64) NULL COMMENT '角色名称',
  `content` NVARCHAR(100) NULL COMMENT '角色描述',
  `createTime` DATETIME(3) NULL COMMENT '创建时间',
  `updateTime` DATETIME(3) NULL COMMENT '修改时间',
  `locked` TINYINT NULL DEFAULT 0 COMMENT '是否被锁定，0否1是',
  PRIMARY KEY (`roleid`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `USER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `USER` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` VARCHAR(20) NULL COMMENT '用户名',
  `password` VARCHAR(50) NULL COMMENT '密码',
  `phone` VARCHAR(11) NULL COMMENT '手机号',
  `signature` NVARCHAR(100) NULL COMMENT '签名',
  `createtime` DATETIME(3) NULL COMMENT '创建时间',
  `updatetime` DATETIME(3) NULL COMMENT '更新时间',
  `locked` TINYINT NULL COMMENT '是否都被锁定',
  `salt` VARCHAR(50) NULL COMMENT '密码加盐',
  `roleid` INT  COMMENT '角色ID',
  PRIMARY KEY (`id`),
  INDEX `fk_USER_ROLE1_idx` (`roleid` ASC),
  CONSTRAINT `fk_USER_ROLE1`
    FOREIGN KEY (`roleid`)
    REFERENCES `ROLE` (`roleid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `QUESTION`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QUESTION` (
  `qid` INT NOT NULL AUTO_INCREMENT COMMENT '问题ID',
  `title` NVARCHAR(200) NULL COMMENT '问题标题',
  `text` VARCHAR(500) NULL COMMENT '问题描述',
  `userid` INT NOT NULL COMMENT '提问用户ID',
  `createtime` DATETIME(3) NULL COMMENT '创建时间',
  `updatetime` DATETIME(3) NULL COMMENT '修改时间',
  PRIMARY KEY (`qid`),
  INDEX `fk_QUESTION_USER1_idx` (`userid` ASC),
  CONSTRAINT `fk_QUESTION_USER1`
    FOREIGN KEY (`userid`)
    REFERENCES `USER` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ANSWER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ANSWER` (
  `aid` INT NOT NULL AUTO_INCREMENT COMMENT '回答主键ID',
  `text` NVARCHAR(5000) NULL COMMENT '\'回答内容\'',
  `qid` INT NOT NULL COMMENT '回答的问题ID',
  `userid` INT NOT NULL COMMENT '回答的用户ID',
  `createtime` DATETIME(3) NULL COMMENT '创建时间',
  `star` INT NULL COMMENT '点赞数',
  PRIMARY KEY (`aid`),
  INDEX `fk_ANSWER_QUESTION1_idx` (`qid` ASC),
  INDEX `fk_ANSWER_USER1_idx` (`userid` ASC),
  CONSTRAINT `fk_ANSWER_QUESTION1`
    FOREIGN KEY (`qid`)
    REFERENCES `QUESTION` (`qid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ANSWER_USER1`
    FOREIGN KEY (`userid`)
    REFERENCES `USER` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `USER_ATTECTION`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `USER_ATTECTION` (
  `atid` INT NOT NULL AUTO_INCREMENT COMMENT '用户关注问题主键ID',
  `userid` INT NOT NULL COMMENT '用户ID',
  `qid` INT NOT NULL COMMENT '问题ID',
  `createtime` DATETIME(3) NULL COMMENT '关注时间',
  PRIMARY KEY (`atid`),
  INDEX `fk_USER_COLLECTION_USER_idx` (`userid` ASC),
  INDEX `fk_USER_COLLECTION_QUESTION1_idx` (`qid` ASC),
  CONSTRAINT `fk_USER_COLLECTION_USER`
    FOREIGN KEY (`userid`)
    REFERENCES `USER` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_USER_COLLECTION_QUESTION1`
    FOREIGN KEY (`qid`)
    REFERENCES `QUESTION` (`qid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `USER_COLLECTION`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `USER_COLLECTION` (
  `cid` INT NOT NULL AUTO_INCREMENT COMMENT '用户收藏回答主键ID',
  `userid` INT NOT NULL COMMENT '用户ID',
  `aid` INT NOT NULL COMMENT '回答ID',
  `createtime` DATETIME(3) NULL COMMENT '收藏时间',
  PRIMARY KEY (`cid`),
  INDEX `fk_USER_ATTENTION_USER1_idx` (`userid` ASC),
  INDEX `fk_USER_ATTENTION_ANSWER1_idx` (`aid` ASC),
  CONSTRAINT `fk_USER_ATTENTION_USER1`
    FOREIGN KEY (`userid`)
    REFERENCES `USER` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_USER_ATTENTION_ANSWER1`
    FOREIGN KEY (`aid`)
    REFERENCES `ANSWER` (`aid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `TOPIC`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TOPIC` (
  `tid` INT NOT NULL AUTO_INCREMENT COMMENT '话题ID',
  `title` VARCHAR(64) NULL COMMENT '\'话题名称\'',
  `ptid` INT NULL COMMENT '父级话题',
  PRIMARY KEY (`tid`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `QUESTION_TOPIC`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `QUESTION_TOPIC` (
  `qtid` INT NOT NULL AUTO_INCREMENT COMMENT '问题关联话题ID',
  `qid` INT NOT NULL COMMENT '问题ID',
  `tid` INT NOT NULL COMMENT '话题ID',
  PRIMARY KEY (`qtid`),
  INDEX `fk_QUESTION_has_TOPIC_TOPIC1_idx` (`tid` ASC),
  INDEX `fk_QUESTION_has_TOPIC_QUESTION1_idx` (`qid` ASC),
  CONSTRAINT `fk_QUESTION_has_TOPIC_QUESTION1`
    FOREIGN KEY (`qid`)
    REFERENCES `QUESTION` (`qid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_QUESTION_has_TOPIC_TOPIC1`
    FOREIGN KEY (`tid`)
    REFERENCES `TOPIC` (`tid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
