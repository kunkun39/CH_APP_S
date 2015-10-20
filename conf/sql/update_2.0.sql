alter table `market_app` add column `app_scores` int(11) default 8;

/*建立开机广告数据库*/
DROP TABLE IF EXISTS `boot_image`;
CREATE TABLE `boot_image` (
  `id` int(11) NOT NULL auto_increment,
  `actual_filename` varchar(20),
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;