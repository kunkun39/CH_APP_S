alter table `market_app` add column `app_scores` int(11) default 8;
alter table `market_app` add column `app_recommend` tinyint(1) default '0' COMMENT '1 for YES or 0 for NO';



