CREATE TABLE `article`(
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`author` varchar(255) NOT NULL,
	`articleTitle` varchar(255) NOT NULL,
	`articleContent` longtext NOT NULL,
	`publishDate` varchar(255) NOT NULL,
	`updateDate` varchar(255) NOT NULL,
	PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
CREATE TABLE `user`(
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`username` varchar(255) NOT NULL,
	`password` varchar(255) NOT NULL,
	PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

INSERT INTO `article` VALUES ('0','superAdmin','The zero article','This is the content of the zero article.','2020-7-14 00:00:00')
INSERT INTO `user` VALUES ('0','superAdmin','123456')