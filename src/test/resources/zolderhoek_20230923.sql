delete from blocks;
delete from route_elements;
delete from routes;
delete from tiles;
delete from sensors;
delete from accessories;

alter table accessories alter column id restart with 1;
alter table sensors alter column id restart with 1;
alter table route_elements alter column id restart with 1;
alter table blocks alter column id restart with 1;

commit;


INSERT INTO jcs.accessories (address,name,"type","position",states,switch_time,decoder_type,decoder,accessory_group,icon,icon_file) VALUES
	 (20,'S  20','lichtsignal_HP01',0,2,200,'mm','ein_alt','lichtsignale','015','magicon_a_015_00.svg'),
	 (2,'W 2R','rechtsweiche',1,2,200,'mm','ein_alt','weichen','005','magicon_a_005_01.svg'),
	 (16,'S 16','lichtsignal_SH01',0,2,200,'mm','ein_alt','lichtsignale','019','magicon_a_019_00.svg'),
	 (3,'W 3R','rechtsweiche',0,2,200,'mm','ein_alt','weichen','005','magicon_a_005_00.svg'),
	 (47,'S 20','lichtsignal_HP01',0,2,200,'mm','ein_alt','lichtsignale','015','magicon_a_015_00.svg'),
	 (4,'W 4R','rechtsweiche',0,2,200,'mm','ein_alt','weichen','005','magicon_a_005_00.svg'),
	 (22,'S 22','lichtsignal_SH01',0,2,200,'mm','ein_alt','lichtsignale','019','magicon_a_019_00.svg'),
	 (5,'W 5R','rechtsweiche',0,2,200,'mm','ein_alt','weichen','005','magicon_a_005_00.svg'),
	 (24,'S 24','lichtsignal_SH01',0,2,200,'mm','ein_alt','lichtsignale','019','magicon_a_019_00.svg'),
	 (6,'W 6R','rechtsweiche',0,2,200,'mm','ein_alt','weichen','005','magicon_a_005_00.svg');
INSERT INTO jcs.accessories (address,name,"type","position",states,switch_time,decoder_type,decoder,accessory_group,icon,icon_file) VALUES
	 (27,'S 27/28','urc_lichtsignal_HP012_SH01',0,4,200,'mm','ein_alt','lichtsignale','027','magicon_a_027_00.svg'),
	 (7,'W 7L','linksweiche',1,2,200,'mm','ein_alt','weichen','006','magicon_a_006_01.svg'),
	 (31,'S 31/32','urc_lichtsignal_HP012_SH01',0,4,200,'mm','ein_alt','lichtsignale','027','magicon_a_027_00.svg'),
	 (8,'W 8L','linksweiche',1,2,200,'mm','ein_alt','weichen','006','magicon_a_006_01.svg'),
	 (35,'S 35','lichtsignal_HP01',0,2,200,'mm','ein_alt','lichtsignale','015','magicon_a_015_00.svg'),
	 (9,'W 9L','linksweiche',0,2,200,'mm','ein_alt','weichen','006','magicon_a_006_00.svg'),
	 (41,'S 41','urc_lichtsignal_HP012',0,3,200,'mm','ein_alt','lichtsignale','026','magicon_a_026_00.svg'),
	 (10,'W 10L','linksweiche',1,2,200,'mm','ein_alt','weichen','006','magicon_a_006_01.svg'),
	 (49,'S 49','urc_lichtsignal_HP012',0,3,200,'mm','ein_alt','lichtsignale','026','magicon_a_026_00.svg'),
	 (11,'W 11L','linksweiche',0,2,200,'mm','ein_alt','weichen','006','magicon_a_006_00.svg');
INSERT INTO jcs.accessories (address,name,"type","position",states,switch_time,decoder_type,decoder,accessory_group,icon,icon_file) VALUES
	 (53,'S 53','lichtsignal_HP01',0,2,200,'mm','ein_alt','lichtsignale','015','magicon_a_015_00.svg'),
	 (12,'W 12L','linksweiche',0,2,200,'mm','ein_alt','weichen','006','magicon_a_006_00.svg'),
	 (13,'W 13L','linksweiche',1,2,200,'mm','ein_alt','weichen','006','magicon_a_006_01.svg'),
	 (14,'W 14L','linksweiche',0,2,200,'mm','ein_alt','weichen','006','magicon_a_006_00.svg'),
	 (17,'W 17R','rechtsweiche',1,2,200,'mm','ein_alt','weichen','005','magicon_a_005_01.svg'),
	 (18,'W 18R','rechtsweiche',1,2,200,'mm','ein_alt','weichen','005','magicon_a_005_01.svg'),
	 (1,'W 1R','rechtsweiche',1,2,200,'mm','ein_alt','weichen','005','magicon_a_005_01.svg'),
	 (15,'S 15','lichtsignal_SH01',1,2,200,'mm','ein_alt','lichtsignale','019','magicon_a_019_01.svg'),
	 (19,'S 19','lichtsignal_HP01',0,2,200,'mm','ein_alt','lichtsignale','015','magicon_a_015_00.svg'),
	 (21,'S 21','lichtsignal_SH01',0,2,200,'mm','ein_alt','lichtsignale','019','magicon_a_019_00.svg');
INSERT INTO jcs.accessories (address,name,"type","position",states,switch_time,decoder_type,decoder,accessory_group,icon,icon_file) VALUES
	 (23,'S 23','lichtsignal_SH01',0,2,200,'mm','ein_alt','lichtsignale','019','magicon_a_019_00.svg'),
	 (25,'S 25/26','urc_lichtsignal_HP012_SH01',0,4,200,'mm','ein_alt','lichtsignale','027','magicon_a_027_00.svg'),
	 (29,'S 29/30','urc_lichtsignal_HP012_SH01',0,4,200,'mm','ein_alt','lichtsignale','027','magicon_a_027_00.svg'),
	 (33,'S 33','lichtsignal_HP01',1,2,200,'mm','ein_alt','lichtsignale','015','magicon_a_015_01.svg'),
	 (36,'S 36','lichtsignal_HP01',0,2,200,'mm','ein_alt','lichtsignale','015','magicon_a_015_00.svg'),
	 (43,'S 43','lichtsignal_HP01',0,2,200,'mm','ein_alt','lichtsignale','015','magicon_a_015_00.svg'),
	 (51,'S 51','lichtsignal_HP01',0,2,200,'mm','ein_alt','lichtsignale','015','magicon_a_015_00.svg'),
	 (34,'W 34','linksweiche',1,2,200,'mm','ein_alt','weichen','006','magicon_a_006_01.svg');

commit;

INSERT INTO jcs.sensors (name,device_id,contact_id,status,previous_status,millis,last_updated) VALUES
	 ('M1001',65,1001,0,1,200,'2023-09-23'),
	 ('M2019',65,2019,0,1,5800,'2023-09-23'),
	 ('M2020',65,2020,0,1,200,'2023-09-23'),
	 ('M2001',65,2001,0,1,2500,'2023-09-23'),
	 ('M2002',65,2002,0,1,3000,'2023-09-23'),
	 ('M2009',65,2009,0,1,1100,'2023-09-23'),
	 ('M2010',65,2010,0,1,5300,'2023-09-23'),
	 ('M1',65,1,0,1,200,'2023-09-23'),
	 ('M2',65,2,0,1,7100,'2023-09-23'),
	 ('M6',65,6,0,1,100,'2023-09-23');
INSERT INTO jcs.sensors (name,device_id,contact_id,status,previous_status,millis,last_updated) VALUES
	 ('M5',65,5,0,1,200,'2023-09-23'),
	 ('M13',65,13,1,0,655350,'2023-09-23'),
	 ('M7',65,7,0,1,300,'2023-09-23'),
	 ('M14',65,14,0,1,51100,'2023-09-23'),
	 ('M15',65,15,0,1,200,'2023-09-23'),
	 ('M16',65,16,0,1,300,'2023-09-23'),
	 ('M1007',65,1007,0,1,5100,'2023-09-23'),
	 ('M1008',65,1008,0,1,4300,'2023-09-23'),
	 ('M1009',65,1009,0,1,4700,'2023-09-23'),
	 ('M1005',65,1005,0,1,4900,'2023-09-23');
INSERT INTO jcs.sensors (name,device_id,contact_id,status,previous_status,millis,last_updated) VALUES
	 ('M2014',65,2014,0,1,5300,'2023-09-23'),
	 ('M2013',65,2013,1,0,655350,'2023-09-23'),
	 ('M2016',65,2016,0,1,300,'2023-09-23'),
	 ('M2015',65,2015,0,1,500,'2023-09-23'),
	 ('M2006',65,2006,0,1,6200,'2023-09-23'),
	 ('M2005',65,2005,1,0,1300,'2023-09-23'),
	 ('M2008',65,2008,0,1,600,'2023-09-23'),
	 ('M2007',65,2007,0,1,37800,'2023-09-23'),
	 ('M2031',65,2031,0,1,5100,'2023-09-23'),
	 ('M1012',65,1012,1,0,900,'2023-09-23');
INSERT INTO jcs.sensors (name,device_id,contact_id,status,previous_status,millis,last_updated) VALUES
	 ('M1011',65,1011,0,1,100,'2023-09-23'),
	 ('M2032',65,2032,0,1,5100,'2023-09-23'),
	 ('M2028',65,2028,NULL,NULL,NULL,NULL),
	 ('M2021',65,2021,0,1,200,'2023-09-23'),
	 ('M2022',65,2022,0,1,5500,'2023-09-23'),
	 ('M1002',65,1002,0,1,200,'2023-09-23'),
	 ('M1013',65,1013,0,1,5100,'2023-09-23'),
	 ('M1014',65,1014,0,1,1000,'2023-09-23'),
	 ('M11',65,11,0,1,200,'2023-09-23'),
	 ('M12',65,12,NULL,NULL,NULL,NULL);
INSERT INTO jcs.sensors (name,device_id,contact_id,status,previous_status,millis,last_updated) VALUES
	 ('M3',65,3,0,1,39500,'2023-09-23'),
	 ('M4',65,4,NULL,NULL,NULL,NULL),
	 ('M8',65,8,0,1,400,'2023-09-23'),
	 ('M9',65,9,NULL,NULL,NULL,NULL),
	 ('M1006',65,1006,0,1,1600,'2023-09-23'),
	 ('M1004',65,1004,NULL,NULL,NULL,NULL),
	 ('M2017',65,2017,0,1,10600,'2023-09-23'),
	 ('M10',65,10,0,1,1900,'2023-09-23'),
	 ('M2025',65,2025,1,0,107200,'2023-09-23'),
	 ('M1003',65,1003,0,1,200,'2023-09-23');
INSERT INTO jcs.sensors (name,device_id,contact_id,status,previous_status,millis,last_updated) VALUES
	 ('M2003',65,2003,0,1,3000,'2023-09-23'),
	 ('M2004',65,2004,0,1,2800,'2023-09-23'),
	 ('M2012',65,2012,0,1,5300,'2023-09-23'),
	 ('M2011',65,2011,0,1,1200,'2023-09-23');

commit;

INSERT INTO jcs.tiles (id,tile_type,orientation,direction,x,y,signal_type,accessory_id,sensor_id) VALUES
	 ('st-21','Straight','West','Center',660,340,NULL,NULL,NULL),
	 ('se-6','Sensor','South','Center',140,340,NULL,NULL,2),
	 ('st-42','Straight','West','Center',620,540,NULL,NULL,NULL),
	 ('sd-14','StraightDirection','East','Center',380,580,NULL,NULL,NULL),
	 ('st-66','Straight','West','Center',740,540,NULL,NULL,NULL),
	 ('st-24','Straight','West','Center',620,140,NULL,NULL,NULL),
	 ('si-3','Signal','West','Center',340,300,NULL,12,NULL),
	 ('st-97','Straight','South','Center',980,700,NULL,NULL,NULL),
	 ('sw-13','Switch','East','Right',820,780,NULL,35,NULL),
	 ('se-34','Sensor','West','Center',580,580,NULL,NULL,37);
INSERT INTO jcs.tiles (id,tile_type,orientation,direction,x,y,signal_type,accessory_id,sensor_id) VALUES
	 ('st-34','Straight','West','Center',700,100,NULL,NULL,NULL),
	 ('st-77','Straight','East','Center',420,740,NULL,NULL,NULL),
	 ('se-20','Sensor','East','Center',340,380,NULL,NULL,51),
	 ('st-25','Straight','West','Center',860,180,NULL,NULL,NULL),
	 ('se-39','Sensor','West','Center',620,660,NULL,NULL,43),
	 ('se-3','Sensor','East','Center',460,60,NULL,NULL,18),
	 ('st-32','Straight','West','Center',820,60,NULL,NULL,NULL),
	 ('st-47','Straight','West','Center',900,60,NULL,NULL,NULL),
	 ('se-9','Sensor','West','Center',300,180,NULL,NULL,31),
	 ('bk-2','Block','South','Center',180,220,NULL,NULL,NULL);
INSERT INTO jcs.tiles (id,tile_type,orientation,direction,x,y,signal_type,accessory_id,sensor_id) VALUES
	 ('st-53','Straight','South','Center',980,260,NULL,NULL,NULL),
	 ('st-43','Straight','West','Center',660,700,NULL,NULL,NULL),
	 ('se-27','Sensor','East','Center',660,420,NULL,NULL,7),
	 ('st-83','Straight','South','Center',940,620,NULL,NULL,NULL),
	 ('se-16','Sensor','West','Center',540,300,NULL,NULL,27),
	 ('se-2','Sensor','South','Center',140,180,NULL,NULL,1),
	 ('ct-31','Curved','North','Center',260,660,NULL,NULL,NULL),
	 ('se-40','Sensor','West','Center',620,700,NULL,NULL,44),
	 ('si-11','Signal','East','Center',620,580,NULL,20,NULL),
	 ('st-79','Straight','East','Center',340,740,NULL,NULL,NULL);
INSERT INTO jcs.tiles (id,tile_type,orientation,direction,x,y,signal_type,accessory_id,sensor_id) VALUES
	 ('bk-8','Block','East','Center',500,380,NULL,NULL,NULL),
	 ('sd-20','StraightDirection','East','Center',460,780,NULL,NULL,NULL),
	 ('ct-5','Curved','West','Center',740,220,NULL,NULL,NULL),
	 ('sw-1','Switch','West','Right',340,60,NULL,28,NULL),
	 ('ct-2','Curved','East','Center',180,100,NULL,NULL,NULL),
	 ('se-46','Sensor','West','Center',700,740,NULL,NULL,12),
	 ('st-14','Straight','West','Center',700,380,NULL,NULL,NULL),
	 ('ct-33','Curved','South','Center',780,660,NULL,NULL,NULL),
	 ('st-62','Straight','South','Center',980,620,NULL,NULL,NULL),
	 ('ct-30','Curved','East','Center',220,540,NULL,NULL,NULL);
INSERT INTO jcs.tiles (id,tile_type,orientation,direction,x,y,signal_type,accessory_id,sensor_id) VALUES
	 ('bk-17','Block','East','Center',620,780,NULL,NULL,NULL),
	 ('et-2','End','West','Center',260,180,NULL,NULL,NULL),
	 ('st-87','Straight','West','Center',300,660,NULL,NULL,NULL),
	 ('sd-9','StraightDirection','East','Center',660,580,NULL,NULL,NULL),
	 ('st-45','Straight','West','Center',740,700,NULL,NULL,NULL),
	 ('st-96','Straight','South','Center',980,660,NULL,NULL,NULL),
	 ('sd-21','StraightDirection','East','Center',900,780,NULL,NULL,NULL),
	 ('ct-23','Curved','East','Center',300,420,NULL,NULL,NULL),
	 ('ct-28','Curved','North','Center',340,700,NULL,NULL,NULL),
	 ('sw-6','Switch','East','Left',740,180,NULL,25,NULL);
INSERT INTO jcs.tiles (id,tile_type,orientation,direction,x,y,signal_type,accessory_id,sensor_id) VALUES
	 ('si-13','Signal','West','Center',420,700,NULL,7,NULL),
	 ('se-18','Sensor','West','Center',620,300,NULL,NULL,28),
	 ('st-74','Straight','West','Center',940,780,NULL,NULL,NULL),
	 ('st-39','Straight','West','Center',660,660,NULL,NULL,NULL),
	 ('ct-29','Curved','North','Center',380,780,NULL,NULL,NULL),
	 ('st-3','Straight','East','Center',660,60,NULL,NULL,NULL),
	 ('se-37','Sensor','West','Center',460,660,NULL,NULL,41),
	 ('bk-7','Block','West','Center',460,340,NULL,NULL,NULL),
	 ('sw-12','Switch','West','Right',380,740,NULL,33,NULL),
	 ('st-57','Straight','South','Center',980,420,NULL,NULL,NULL);
INSERT INTO jcs.tiles (id,tile_type,orientation,direction,x,y,signal_type,accessory_id,sensor_id) VALUES
	 ('sw-16','Switch','West','Left',780,100,NULL,36,NULL),
	 ('ct-22','Curved','East','Center',260,380,NULL,NULL,NULL),
	 ('st-36','Straight','East','Center',340,580,NULL,NULL,NULL),
	 ('st-63','Straight','South','Center',820,420,NULL,NULL,NULL),
	 ('se-53','Sensor','South','Center',940,180,NULL,NULL,45),
	 ('ct-1','Curved','East','Center',140,60,NULL,NULL,NULL),
	 ('bk-15','Block','South','Center',900,420,NULL,NULL,NULL),
	 ('st-52','Straight','South','Center',980,220,NULL,NULL,NULL),
	 ('st-4','Straight','East','Center',300,100,NULL,NULL,NULL),
	 ('se-12','Sensor','East','Center',300,300,NULL,NULL,23);
INSERT INTO jcs.tiles (id,tile_type,orientation,direction,x,y,signal_type,accessory_id,sensor_id) VALUES
	 ('st-91','Straight','South','Center',220,620,NULL,NULL,NULL),
	 ('st-31','Straight','East','Center',780,380,NULL,NULL,NULL),
	 ('sw-18','Switch','West','Right',700,540,NULL,30,NULL),
	 ('ct-13','Curved','East','Center',660,260,NULL,NULL,NULL),
	 ('st-78','Straight','East','Center',420,780,NULL,NULL,NULL),
	 ('se-26','Sensor','East','Center',660,380,NULL,NULL,53),
	 ('st-10','Straight','East','Center',620,100,NULL,NULL,NULL),
	 ('se-1','Sensor','South','Center',180,140,NULL,NULL,19),
	 ('si-4','Signal','West','Center',340,340,NULL,11,NULL),
	 ('sd-11','StraightDirection','East','Center',860,100,NULL,NULL,NULL);
INSERT INTO jcs.tiles (id,tile_type,orientation,direction,x,y,signal_type,accessory_id,sensor_id) VALUES
	 ('si-19','Signal','North','Center',900,300,NULL,16,NULL),
	 ('se-28','Sensor','West','Center',460,500,NULL,NULL,49),
	 ('st-22','Straight','West','Center',780,180,NULL,NULL,NULL),
	 ('sd-10','StraightDirection','West','Center',660,540,NULL,NULL,NULL),
	 ('bk-10','Block','West','Center',540,500,NULL,NULL,NULL),
	 ('ct-38','Curved','West','Center',900,580,NULL,NULL,NULL),
	 ('sw-14','Switch','East','Right',700,580,NULL,29,NULL),
	 ('st-89','Straight','West','Center',900,100,NULL,NULL,NULL),
	 ('se-29','Sensor','West','Center',620,500,NULL,NULL,50),
	 ('ct-34','Curved','South','Center',980,60,NULL,NULL,NULL);
INSERT INTO jcs.tiles (id,tile_type,orientation,direction,x,y,signal_type,accessory_id,sensor_id) VALUES
	 ('st-72','Straight','West','Center',860,580,NULL,NULL,NULL),
	 ('sd-15','StraightDirection','West','Center',700,660,NULL,NULL,NULL),
	 ('st-61','Straight','South','Center',980,580,NULL,NULL,NULL),
	 ('si-2','Signal','South','Center',140,380,NULL,13,NULL),
	 ('ct-36','Curved','South','Center',820,380,NULL,NULL,NULL),
	 ('st-13','Straight','East','Center',260,60,NULL,NULL,NULL),
	 ('si-6','Signal','East','Center',620,420,NULL,9,NULL),
	 ('ct-8','Curved','East','Center',260,300,NULL,NULL,NULL),
	 ('st-11','Straight','East','Center',660,100,NULL,NULL,NULL),
	 ('st-85','Straight','West','Center',860,700,NULL,NULL,NULL);
INSERT INTO jcs.tiles (id,tile_type,orientation,direction,x,y,signal_type,accessory_id,sensor_id) VALUES
	 ('se-8','Sensor','West','Center',300,140,NULL,NULL,30),
	 ('se-32','Sensor','West','Center',500,540,NULL,NULL,8),
	 ('st-82','Straight','South','Center',940,580,NULL,NULL,NULL),
	 ('st-90','Straight','North','Center',940,220,NULL,NULL,NULL),
	 ('ct-4','Curved','West','Center',540,180,NULL,NULL,NULL),
	 ('se-23','Sensor','East','Center',420,420,NULL,NULL,5),
	 ('sd-3','StraightDirection','West','Center',380,60,NULL,NULL,NULL),
	 ('st-56','Straight','South','Center',980,380,NULL,NULL,NULL),
	 ('sd-7','StraightDirection','East','Center',380,380,NULL,NULL,NULL),
	 ('se-25','Sensor','East','Center',580,420,NULL,NULL,6);
INSERT INTO jcs.tiles (id,tile_type,orientation,direction,x,y,signal_type,accessory_id,sensor_id) VALUES
	 ('et-1','End','West','Center',260,140,NULL,NULL,NULL),
	 ('st-40','Straight','West','Center',540,540,NULL,NULL,NULL),
	 ('ct-35','Curved','West','Center',940,700,NULL,NULL,NULL),
	 ('se-4','Sensor','East','Center',620,60,NULL,NULL,17),
	 ('ct-14','Curved','East','Center',740,260,NULL,NULL,NULL),
	 ('bk-1','Block','East','Center',540,60,NULL,NULL,NULL),
	 ('si-5','Signal','East','Center',620,380,NULL,10,NULL),
	 ('se-10','Sensor','West','Center',460,140,NULL,NULL,29),
	 ('st-44','Straight','West','Center',740,660,NULL,NULL,NULL),
	 ('st-9','Straight','East','Center',580,100,NULL,NULL,NULL);
INSERT INTO jcs.tiles (id,tile_type,orientation,direction,x,y,signal_type,accessory_id,sensor_id) VALUES
	 ('sw-15','Switch','East','Left',780,60,NULL,37,NULL),
	 ('se-35','Sensor','East','Center',380,660,NULL,NULL,39),
	 ('sw-3','Switch','West','Left',260,340,NULL,38,NULL),
	 ('st-46','Straight','West','Center',860,780,NULL,NULL,NULL),
	 ('si-8','Signal','East','Center',500,140,NULL,3,NULL),
	 ('se-21','Sensor','East','Center',340,420,NULL,NULL,4),
	 ('se-43','Sensor','South','Center',900,220,NULL,NULL,33),
	 ('si-12','Signal','West','Center',420,660,NULL,5,NULL),
	 ('bk-16','Block','East','Center',620,740,NULL,NULL,NULL),
	 ('ct-37','Curved','West','Center',820,540,NULL,NULL,NULL);
INSERT INTO jcs.tiles (id,tile_type,orientation,direction,x,y,signal_type,accessory_id,sensor_id) VALUES
	 ('sw-8','Switch','West','Left',220,460,NULL,22,NULL),
	 ('st-20','Straight','South','Center',140,100,NULL,NULL,NULL),
	 ('sd-6','StraightDirection','West','Center',580,340,NULL,NULL,NULL),
	 ('bk-3','Block','South','Center',140,260,NULL,NULL,NULL),
	 ('ct-3','Curved','North','Center',180,340,NULL,NULL,NULL),
	 ('se-22','Sensor','East','Center',420,380,NULL,NULL,52),
	 ('si-1','Signal','West','Center',420,60,NULL,18,NULL),
	 ('ct-32','Curved','East','Center',260,580,NULL,NULL,NULL),
	 ('sd-19','StraightDirection','East','Center',460,740,NULL,NULL,NULL),
	 ('ct-15','Curved','West','Center',820,220,NULL,NULL,NULL);
INSERT INTO jcs.tiles (id,tile_type,orientation,direction,x,y,signal_type,accessory_id,sensor_id) VALUES
	 ('st-17','Straight','East','Center',220,60,NULL,NULL,NULL),
	 ('bk-9','Block','East','Center',500,420,NULL,NULL,NULL),
	 ('st-41','Straight','West','Center',580,540,NULL,NULL,NULL),
	 ('si-14','Signal','South','Center',900,540,NULL,19,NULL),
	 ('st-28','Straight','East','Center',260,460,NULL,NULL,NULL),
	 ('se-30','Sensor','East','Center',260,540,NULL,NULL,10),
	 ('st-37','Straight','South','Center',260,620,NULL,NULL,NULL),
	 ('st-7','Straight','East','Center',500,100,NULL,NULL,NULL),
	 ('se-33','Sensor','West','Center',420,580,NULL,NULL,38),
	 ('sd-13','StraightDirection','South','Center',220,580,NULL,NULL,NULL);
INSERT INTO jcs.tiles (id,tile_type,orientation,direction,x,y,signal_type,accessory_id,sensor_id) VALUES
	 ('sw-9','Switch','West','Left',300,460,NULL,26,NULL),
	 ('st-51','Straight','South','Center',980,180,NULL,NULL,NULL),
	 ('st-6','Straight','East','Center',460,100,NULL,NULL,NULL),
	 ('ct-43','Curved','West','Center',980,780,NULL,NULL,NULL),
	 ('se-13','Sensor','East','Center',300,340,NULL,NULL,21),
	 ('sd-12','StraightDirection','West','Center',860,60,NULL,NULL,NULL),
	 ('sd-1','StraightDirection','West','Center',220,340,NULL,NULL,NULL),
	 ('st-33','Straight','West','Center',740,100,NULL,NULL,NULL),
	 ('ct-7','Curved','West','Center',700,260,NULL,NULL,NULL),
	 ('st-8','Straight','East','Center',540,100,NULL,NULL,NULL);
INSERT INTO jcs.tiles (id,tile_type,orientation,direction,x,y,signal_type,accessory_id,sensor_id) VALUES
	 ('ct-18','Curved','North','Center',660,180,NULL,NULL,NULL),
	 ('st-38','Straight','East','Center',300,580,NULL,NULL,NULL),
	 ('sw-10','Switch','East','Left',740,380,NULL,24,NULL),
	 ('sd-4','StraightDirection','East','Center',380,100,NULL,NULL,NULL),
	 ('bk-4','Block','West','Center',380,140,NULL,NULL,NULL),
	 ('st-60','Straight','South','Center',980,540,NULL,NULL,NULL),
	 ('se-49','Sensor','East','Center',540,780,NULL,NULL,13),
	 ('se-52','Sensor','South','Center',940,540,NULL,NULL,48),
	 ('st-5','Straight','East','Center',420,100,NULL,NULL,NULL),
	 ('ct-17','Curved','West','Center',780,260,NULL,NULL,NULL);
INSERT INTO jcs.tiles (id,tile_type,orientation,direction,x,y,signal_type,accessory_id,sensor_id) VALUES
	 ('bk-11','Block','West','Center',420,540,NULL,NULL,NULL),
	 ('ct-40','Curved','South','Center',820,740,NULL,NULL,NULL),
	 ('ct-41','Curved','South','Center',940,100,NULL,NULL,NULL),
	 ('st-29','Straight','East','Center',300,380,NULL,NULL,NULL),
	 ('se-5','Sensor','South','Center',180,300,NULL,NULL,20),
	 ('si-16','Signal','East','Center',740,780,NULL,7,NULL),
	 ('st-27','Straight','East','Center',180,460,NULL,NULL,NULL),
	 ('st-71','Straight','South','Center',940,140,NULL,NULL,NULL),
	 ('sd-2','StraightDirection','East','Center',260,100,NULL,NULL,NULL),
	 ('st-35','Straight','West','Center',820,100,NULL,NULL,NULL);
INSERT INTO jcs.tiles (id,tile_type,orientation,direction,x,y,signal_type,accessory_id,sensor_id) VALUES
	 ('st-55','Straight','South','Center',980,340,NULL,NULL,NULL),
	 ('bk-14','Block','West','Center',540,700,NULL,NULL,NULL),
	 ('sd-8','StraightDirection','East','Center',380,420,NULL,NULL,NULL),
	 ('st-73','Straight','West','Center',500,780,NULL,NULL,NULL),
	 ('st-65','Straight','South','Center',820,500,NULL,NULL,NULL),
	 ('se-11','Sensor','West','Center',460,180,NULL,NULL,32),
	 ('si-9','Signal','East','Center',500,180,NULL,4,NULL),
	 ('sw-4','Switch','East','Right',780,700,NULL,34,NULL),
	 ('si-10','Signal','West','Center',300,540,NULL,15,NULL),
	 ('et-3','End','East','Center',660,500,NULL,NULL,NULL);
INSERT INTO jcs.tiles (id,tile_type,orientation,direction,x,y,signal_type,accessory_id,sensor_id) VALUES
	 ('st-92','Straight','South','Center',220,660,NULL,NULL,NULL),
	 ('se-36','Sensor','East','Center',380,700,NULL,NULL,40),
	 ('ct-6','Curved','East','Center',700,220,NULL,NULL,NULL),
	 ('st-30','Straight','East','Center',380,500,NULL,NULL,NULL),
	 ('se-42','Sensor','North','Center',900,340,NULL,NULL,35),
	 ('se-14','Sensor','West','Center',380,300,NULL,NULL,24),
	 ('ct-10','Curved','West','Center',700,340,NULL,NULL,NULL),
	 ('sd-5','StraightDirection','West','Center',580,300,NULL,NULL,NULL),
	 ('se-24','Sensor','East','Center',580,380,NULL,NULL,54),
	 ('se-45','Sensor','West','Center',780,780,NULL,NULL,16);
INSERT INTO jcs.tiles (id,tile_type,orientation,direction,x,y,signal_type,accessory_id,sensor_id) VALUES
	 ('se-31','Sensor','West','Center',340,540,NULL,NULL,9),
	 ('st-98','Straight','South','Center',980,740,NULL,NULL,NULL),
	 ('st-95','Straight','East','Center',300,740,NULL,NULL,NULL),
	 ('st-19','Straight','East','Center',220,100,NULL,NULL,NULL),
	 ('se-44','Sensor','West','Center',780,740,NULL,NULL,15),
	 ('se-54','Sensor','North','Center',900,260,NULL,NULL,34),
	 ('st-59','Straight','South','Center',980,500,NULL,NULL,NULL),
	 ('st-2','Straight','East','Center',740,60,NULL,NULL,NULL),
	 ('bk-18','Block','South','Center',940,380,NULL,NULL,NULL),
	 ('ct-39','Curved','South','Center',900,180,NULL,NULL,NULL);
INSERT INTO jcs.tiles (id,tile_type,orientation,direction,x,y,signal_type,accessory_id,sensor_id) VALUES
	 ('st-67','Straight','West','Center',740,580,NULL,NULL,NULL),
	 ('se-55','Sensor','North','Center',940,300,NULL,NULL,46),
	 ('se-51','Sensor','North','Center',940,460,NULL,NULL,47),
	 ('bk-5','Block','West','Center',380,180,NULL,NULL,NULL),
	 ('st-54','Straight','North','Center',980,300,NULL,NULL,NULL),
	 ('st-50','Straight','South','Center',980,140,NULL,NULL,NULL),
	 ('si-7','Signal','West','Center',420,500,NULL,21,NULL),
	 ('st-84','Straight','South','Center',940,660,NULL,NULL,NULL),
	 ('se-48','Sensor','East','Center',540,740,NULL,NULL,11),
	 ('ct-19','Curved','South','Center',660,140,NULL,NULL,NULL);
INSERT INTO jcs.tiles (id,tile_type,orientation,direction,x,y,signal_type,accessory_id,sensor_id) VALUES
	 ('ct-24','Curved','North','Center',140,460,NULL,NULL,NULL),
	 ('se-7','Sensor','South','Center',140,420,NULL,NULL,3),
	 ('st-69','Straight','West','Center',820,580,NULL,NULL,NULL),
	 ('st-1','Straight','East','Center',700,60,NULL,NULL,NULL),
	 ('ct-26','Curved','South','Center',340,460,NULL,NULL,NULL),
	 ('st-16','Straight','West','Center',580,140,NULL,NULL,NULL),
	 ('sw-11','Switch','West','Right',340,660,NULL,32,NULL),
	 ('se-17','Sensor','West','Center',540,340,NULL,NULL,25),
	 ('st-18','Straight','East','Center',180,60,NULL,NULL,NULL),
	 ('st-75','Straight','West','Center',900,700,NULL,NULL,NULL);
INSERT INTO jcs.tiles (id,tile_type,orientation,direction,x,y,signal_type,accessory_id,sensor_id) VALUES
	 ('sw-5','Switch','East','Left',540,140,NULL,31,NULL),
	 ('st-26','Straight','North','Center',140,140,NULL,NULL,NULL),
	 ('se-19','Sensor','West','Center',620,340,NULL,NULL,26),
	 ('st-15','Straight','West','Center',700,420,NULL,NULL,NULL),
	 ('st-76','Straight','East','Center',500,740,NULL,NULL,NULL),
	 ('sd-16','StraightDirection','West','Center',700,700,NULL,NULL,NULL),
	 ('st-64','Straight','West','Center',780,540,NULL,NULL,NULL),
	 ('bk-12','Block','West','Center',500,580,NULL,NULL,NULL),
	 ('sw-7','Switch','East','Left',820,180,NULL,23,NULL),
	 ('bk-13','Block','West','Center',540,660,NULL,NULL,NULL);
INSERT INTO jcs.tiles (id,tile_type,orientation,direction,x,y,signal_type,accessory_id,sensor_id) VALUES
	 ('ct-20','Curved','East','Center',220,420,NULL,NULL,NULL),
	 ('ct-16','Curved','East','Center',780,220,NULL,NULL,NULL),
	 ('ct-12','Curved','West','Center',740,300,NULL,NULL,NULL),
	 ('st-48','Straight','West','Center',940,60,NULL,NULL,NULL),
	 ('st-70','Straight','North','Center',940,260,NULL,NULL,NULL),
	 ('ct-42','Curved','North','Center',220,740,NULL,NULL,NULL),
	 ('ct-25','Curved','West','Center',740,420,NULL,NULL,NULL),
	 ('st-93','Straight','South','Center',220,700,NULL,NULL,NULL),
	 ('ct-27','Curved','North','Center',340,500,NULL,NULL,NULL),
	 ('si-17','Signal','South','Center',940,500,NULL,14,NULL);
INSERT INTO jcs.tiles (id,tile_type,orientation,direction,x,y,signal_type,accessory_id,sensor_id) VALUES
	 ('st-58','Straight','South','Center',980,460,NULL,NULL,NULL),
	 ('ct-9','Curved','West','Center',660,300,NULL,NULL,NULL),
	 ('se-38','Sensor','West','Center',460,700,NULL,NULL,42),
	 ('se-15','Sensor','West','Center',380,340,NULL,NULL,22),
	 ('se-47','Sensor','West','Center',700,780,NULL,NULL,14),
	 ('si-15','Signal','East','Center',740,740,NULL,8,NULL),
	 ('se-41','Sensor','South','Center',900,500,NULL,NULL,36),
	 ('sw-2','Switch','East','Right',340,100,NULL,27,NULL),
	 ('sd-18','StraightDirection','South','Center',820,460,NULL,NULL,NULL),
	 ('st-23','Straight','West','Center',700,180,NULL,NULL,NULL);
INSERT INTO jcs.tiles (id,tile_type,orientation,direction,x,y,signal_type,accessory_id,sensor_id) VALUES
	 ('ct-21','Curved','West','Center',260,420,NULL,NULL,NULL),
	 ('st-68','Straight','West','Center',780,580,NULL,NULL,NULL),
	 ('st-12','Straight','East','Center',300,60,NULL,NULL,NULL),
	 ('st-94','Straight','East','Center',260,740,NULL,NULL,NULL),
	 ('bk-6','Block','West','Center',460,300,NULL,NULL,NULL),
	 ('ct-11','Curved','East','Center',700,300,NULL,NULL,NULL),
	 ('st-49','Straight','South','Center',980,100,NULL,NULL,NULL),
	 ('st-86','Straight','West','Center',820,700,NULL,NULL,NULL);

commit;

