# User 用户表
CREATE TABLE `mydb`.`User` ( `ID` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户编号' , `Name` VARCHAR(20) NULL COMMENT '姓名' , `Password` VARCHAR(45) NULL COMMENT '密码' , `Phone` VARCHAR(20) NOT NULL COMMENT '手机号' , `TimeCoin` DOUBLE NOT NULL COMMENT '时间币余额' , `TimeVol` DOUBLE NOT NULL COMMENT '志愿者时间余额' , `RegisterDate` DATE NULL COMMENT '注册时间' , PRIMARY KEY (`ID`), UNIQUE ('Phone')) ENGINE = InnoDB;

# TIMEACCOUNT 时间元表

