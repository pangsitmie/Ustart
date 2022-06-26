DROP DATABASE project;
CREATE SCHEMA `project` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `project`.`member` (
    `iacc` INT NOT NULL AUTO_INCREMENT,
    `iuid` varchar(20) not null,
    `icode` varchar(20) not null,
    `nname` varchar(20) not null,
    `isex` int(2) not null,
    `ephone` varchar(20) not null,
    `eemail` varchar(50) not null,
    `qpoint` float not null,
    `dlogin` datetime not null,
    `dadddate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    PRIMARY KEY (`iacc`),
    UNIQUE KEY(`iuid`),
    UNIQUE KEY(`ephone`),
    index(`iacc`,`iuid`,`ephone`)
  );

CREATE TABLE `project`.`purd_type` (
    `itype` INT NOT NULL AUTO_INCREMENT,
    `nname` varchar(50) not null,
    `ememo` text,
    PRIMARY KEY (`itype`),
    UNIQUE KEY(`nname`),
    index(`itype`)
  );

CREATE TABLE `project`.`purd_caption` (
    `ipd` varchar(255) NOT NULL,
    `itype` varchar(10) not null,
    `ememo` text,
    index(`ipd`,`itype`)
  );
  
CREATE TABLE `project`.`purd_pic` (
    `ipd` varchar(255) NOT NULL,
    `itype` varchar(10) not null,
    `eurl` text,
    index(`ipd`,`itype`)
);

CREATE TABLE `project`.`vender` (
    `ivender` varchar(20) NOT NULL,
    `nname` varchar(50) not null,
    `nman` varchar(20) not null,
    `eemail` varchar(50),
    `ephone` varchar(20),
    `eaddress` varchar(100),
    `dadddate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `dchgdate` datetime not null,
    `istatus` char(1) NOT NULL DEFAULT 'T',
    index(`ivender`,`istatus`)
);

CREATE TABLE `project`.`purd_data` (
  `ipd` INT NOT NULL AUTO_INCREMENT,
  `ivender` varchar(20) not null,
  `nname` varchar(50) not null,
  `qprice` float not null,
  `qquantity` int not null,
  `itype` varchar(50) not null,
  `iunit` varchar(50) not null,
  `dindate` datetime not null,
  `dlinedate` datetime not null,
  `dfinalprice` float not null,
  `dchgdate` datetime not null,
  `dadddate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `istatus` char(1) NOT NULL DEFAULT 'T',
  PRIMARY KEY (`ipd`)
  );

CREATE TABLE `project`.`unit` (
  `iunit` INT NOT NULL AUTO_INCREMENT,
  `nname` varchar(50) not null,
  `ememo` text,
  PRIMARY KEY (`iunit`),
  UNIQUE KEY(`nname`),
  index(`iunit`)
  );

CREATE TABLE `project`.`purd_status` (
  `istatus` char(1) NOT NULL,
  `nstatus` varchar(20) not null,
  `ememo` text,
  PRIMARY KEY (`istatus`),
  UNIQUE KEY(`nstatus`),
  index(`istatus`)
  );

INSERT INTO `project`.`purd_status` (`istatus`,`nstatus`,`ememo`)
VALUES ('T','啟用','商品上架、呈現'),('F','停用','商品下架、隱藏'),('D','刪除','商品已被刪除');