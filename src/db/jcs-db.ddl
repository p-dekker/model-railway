/*
 * JCS Database
 * JCS uses an embedded Java database (H2) to persist
 * properties, Locomotives, Accessories etc.
*/ 

/*
 * The properties table is used to store system properties.
 */
DROP TABLE if exists JCSPROPERTIES;

CREATE TABLE JCSPROPERTIES (
PKEY    VARCHAR(255) NOT NULL,
PVALUE  VARCHAR(255)
);

ALTER TABLE JCSPROPERTIES ADD CONSTRAINT PROP_PK PRIMARY KEY ( PKEY );

-- JCS has currently 1 implementation of a controller
INSERT INTO jcsproperties (PKEY,PVALUE) VALUES ('CS3','jcs.controller.cs3.MarklinCS3');

/*
 * The Locomotives
 */
DROP TABLE IF EXISTS LOCOMOTIVES CASCADE CONSTRAINTS;

CREATE TABLE LOCOMOTIVES (
ID                 BIGINT NOT NULL,
NAME               VARCHAR(255) NOT NULL,
PREVIOUSNAME       VARCHAR(255),
UID                VARCHAR(255),
MFXUID             VARCHAR(255),
ADDRESS            INTEGER NOT NULL,
ICON               VARCHAR(255),
DECODERTYPE        VARCHAR(255),
MFXSID             VARCHAR(255),
TACHOMAX           INTEGER,
VMIN               INTEGER,
ACCELERATIONDELAY  INTEGER,
BRAKEDELAY         INTEGER,
VOLUME             INTEGER,
SPM                VARCHAR(255),
VELOCITY           INTEGER,
DIRECTION          INTEGER,
MFXTYPE            VARCHAR(255),
BLOCKS             VARCHAR(255)
);

ALTER TABLE LOCOMOTIVES ADD CONSTRAINT LOCO_PK PRIMARY KEY ( ID );
ALTER TABLE LOCOMOTIVES ADD CONSTRAINT LOCO_ADDRESS_UN UNIQUE ( ADDRESS, DECODERTYPE );

/*
 * The Function(s) of a Locomotive
 */
DROP TABLE IF EXISTS FUNCTIONS CASCADE CONSTRAINTS;

CREATE TABLE FUNCTIONS (
LOCOID BIGINT NOT NULL,
NUMBER INTEGER NOT NULL,
TYPE INTEGER NOT NULL,
FVALUE INTEGER
);

ALTER TABLE FUNCTIONS ADD CONSTRAINT FUNC_PK PRIMARY KEY ( LOCOID, NUMBER );
ALTER TABLE FUNCTIONS ADD CONSTRAINT FUNC_LOCO_FK FOREIGN KEY ( LOCOID ) REFERENCES LOCOMOTIVES ( ID );

/*
 * Accessories are Signals, Turnouts, etc
 */
DROP TABLE IF EXISTS ACCESSORIES CASCADE CONSTRAINTS;

CREATE TABLE ACCESSORIES (
ID IDENTITY NOT NULL,
ADDRESS INTEGER NOT NULL,
NAME VARCHAR(255) NOT NULL,
"TYPE" VARCHAR(255) NOT NULL,
"POSITION" INTEGER,
STATES INTEGER,
SWITCHTIME INTEGER,
DECODERTYPE VARCHAR(255),
DECODER VARCHAR(255),
AGROUP VARCHAR(255),
ICON VARCHAR(255),
ICONFILE CHARACTER VARYING(255)
);

ALTER TABLE ACCESSORIES ADD CONSTRAINT ASSE_ADDRESS_UN UNIQUE (ADDRESS,DECODERTYPE);
CREATE UNIQUE INDEX ASSE_ADDRESS_UN_IDX ON ACCESSORIES (ADDRESS, DECODERTYPE);

/*
 * The Sensor table stores the properties and status of (feedback) Sensors.
 */
DROP TABLE IF EXISTS SENSORS CASCADE CONSTRAINTS;

CREATE TABLE SENSORS (
ID                IDENTITY NOT NULL,
NAME              VARCHAR(255) NOT NULL,
DEVICEID          INTEGER,
CONTACTID         INTEGER,
STATUS            INTEGER,
PREVIOUSSTATUS    INTEGER,
MILLIS            INTEGER,
LASTUPDATED       DATE
);

/*
 * Tiles are used to store the symbolic layout, which is shown.
 * A Tile has a location on the screen (grid) and a Type (Turnout, Signal, Straight, Curved etc).
 * A Tile can be linked to an Accessory or a Sensor
*/
DROP TABLE IF EXISTS TILES CASCADE CONSTRAINTS;

CREATE TABLE TILES (
ID  VARCHAR(255) NOT NULL,
TILETYPE     VARCHAR(255) NOT NULL,
ORIENTATION  VARCHAR(255) NOT NULL,
DIRECTION    VARCHAR(255) NOT NULL,
X            INTEGER NOT NULL,
Y            INTEGER NOT NULL,
SIGNALTYPE   VARCHAR(255),
ACCESSORYID  BIGINT,
SENSORID     BIGINT
);

ALTER TABLE TILES ADD CONSTRAINT TILE_PK PRIMARY KEY ( ID );
CREATE UNIQUE INDEX TILES_X_Y_IDX ON TILES ( X, Y );

ALTER TABLE TILES ADD CONSTRAINT TILE_ASSE_SENS_CK CHECK (
  (ACCESSORYID IS NULL AND SENSORID IS NULL) OR
  (ACCESSORYID IS NOT NULL AND SENSORID IS NULL) OR 
  (ACCESSORYID IS NULL AND SENSORID IS NOT NULL)
);

ALTER TABLE TILES ADD CONSTRAINT TILE_ASSE_FK FOREIGN KEY (ACCESSORYID) REFERENCES ACCESSORIES(ID);
ALTER TABLE TILES ADD CONSTRAINT TILE_SENS_FK FOREIGN KEY (SENSORID) REFERENCES SENSORS(ID);

/*
 * Routes, a route is from a block( tile) to an other block (tile).
 * The tiles which are on the route are put in the ROUTE table 
 */
DROP TABLE IF EXISTS ROUTES CASCADE CONSTRAINTS;

CREATE TABLE ROUTES (
  ID VARCHAR(255) NOT NULL,
  FROMTILEID VARCHAR(255) NOT NULL,
  TOTILEID VARCHAR(255) NOT NULL,
  COLOR VARCHAR(255)
);

ALTER TABLE ROUTES ADD CONSTRAINT ROUT_PK PRIMARY KEY ( ID );

ALTER TABLE ROUTES ADD CONSTRAINT ROUT_FROM_TO_UN UNIQUE (FROMTILEID,TOTILEID);
CREATE UNIQUE INDEX ROUT_FROM_TO_UN_IDX ON ROUTES (FROMTILEID,TOTILEID);

/*
 * Route elements the elements (and the direction etc) of al tile between 2 blocks
 */
DROP TABLE IF EXISTS ROUTEELEMENTS CASCADE CONSTRAINTS;

CREATE TABLE ROUTEELEMENTS (
ID IDENTITY NOT NULL,
ROUTEID VARCHAR(255) NOT NULL,
NODEID VARCHAR(255) NOT NULL,
TILEID VARCHAR(255) NOT NULL,
ACCESSORYVALUE VARCHAR(255),
ORDER_SEQ INTEGER NOT NULL DEFAULT 0
);

ALTER TABLE ROUTEELEMENTS ADD CONSTRAINT ROEL_UN UNIQUE (ROUTEID,TILEID,NODEID);
CREATE UNIQUE INDEX ROEL_UN_IDX ON ROUTEELEMENTS (ROUTEID,TILEID,NODEID);

ALTER TABLE ROUTEELEMENTS ADD CONSTRAINT ROEL_ROUT_FK FOREIGN KEY (ROUTEID) REFERENCES ROUTES(ID);
ALTER TABLE ROUTEELEMENTS ADD CONSTRAINT ROEL_TILE_FK FOREIGN KEY (TILEID) REFERENCES TILES(ID);










/*
 * Blocks, a Block is a part where a Train can stop
 */
DROP TABLE IF EXISTS BLOCKS CASCADE CONSTRAINTS;

CREATE TABLE BLOCKS (
TILEID VARCHAR(255) NOT NULL,
DESCRIPTION VARCHAR(255),
PLUSSENSORID BIGINT,
MINSENSORID BIGINT,
PLUSSIGNALID BIGINT,
MINSIGNALID BIGINT,
LOCOID BIGINT
);

ALTER TABLE BLOCKS ADD CONSTRAINT BLCK_PK PRIMARY KEY ( TILEID );

ALTER TABLE BLOCKS ADD CONSTRAINT BLCK_TILE_FK FOREIGN KEY (TILEID) REFERENCES TILES(ID);
ALTER TABLE BLOCKS ADD CONSTRAINT BLCK_SENS_PLS_FK FOREIGN KEY (PLUSSENSORID) REFERENCES SENSORS(ID);
ALTER TABLE BLOCKS ADD CONSTRAINT BLCK_SENS_MIN_FK FOREIGN KEY (MINSENSORID) REFERENCES SENSORS(ID);
ALTER TABLE BLOCKS ADD CONSTRAINT BLCK_ASSE_SIG_PLS_FK FOREIGN KEY (PLUSSIGNALID) REFERENCES ACCESSORIES(ID);
ALTER TABLE BLOCKS ADD CONSTRAINT BLCK_ASSE_SIG_MIN_FK FOREIGN KEY (MINSIGNALID) REFERENCES ACCESSORIES(ID);
ALTER TABLE BLOCKS ADD CONSTRAINT BLCK_LOCO_FK FOREIGN KEY ( LOCOID ) REFERENCES LOCOMOTIVES ( ID );



/*
 * =================================================================================================================================
 * Demo Values
 */
INSERT INTO LOCOMOTIVES (ID,NAME,PREVIOUSNAME,UID,MFXUID,ADDRESS,ICON,DECODERTYPE,MFXSID,TACHOMAX,VMIN,ACCELERATIONDELAY,BRAKEDELAY,VOLUME,SPM,VELOCITY,DIRECTION,MFXTYPE,BLOCKS) VALUES
	 (2,'BR 81 002','BR  81 002','2','0',2,'DB BR 81 008','mm_prg',NULL,120,1,1,2,64,NULL,0,0,NULL,NULL),
	 (8,'NS  6505','MM Lok 8','8',NULL,8,'NS DHG 6505','mm_prg',NULL,120,NULL,0,0,64,NULL,0,0,NULL,NULL),
	 (12,'BR 141 015-08','BR 141 015-08','12',NULL,12,'DB BR 141 136-2','mm_prg',NULL,120,NULL,0,0,64,NULL,0,1,NULL,NULL),
	 (23,'BR 101 003-2','BR 101 003-2','23',NULL,23,'DB BR 101 109-7','mm_prg',NULL,200,NULL,0,0,64,NULL,0,1,NULL,NULL),
	 (37,'NS 1720','S. 1700','37',NULL,37,'NS 1773','mm_prg',NULL,120,NULL,0,0,64,NULL,0,0,NULL,NULL),
	 (61,'BR 610',NULL,'61',NULL,61,'DB BR 610 015-0','mm_prg',NULL,120,NULL,0,0,64,NULL,0,1,NULL,NULL),
	 (63,'NS 6513','NS  6513','63',NULL,63,'NS 6513','mm_prg',NULL,120,NULL,0,0,64,NULL,0,0,NULL,NULL),
	 (16389,'193 304-3 DB AG',NULL,'16389','1945312555',5,'DB BR 193 304-3','mfx','0x5',160,5,15,15,255,NULL,0,0,NULL,NULL),
	 (16390,'152 119-4 DBAG',NULL,'16390','2113628077',6,'DB BR 152 119-4','mfx','0x6',140,4,28,15,255,'0',0,0,NULL,NULL),
	 (16391,'DB 640 017-9','DB 640 017-9','16391','2097006535',7,'DB BR 640 017-9','mfx','0x7',100,8,15,15,64,NULL,0,0,NULL,NULL);
	
INSERT INTO LOCOMOTIVES (ID,NAME,PREVIOUSNAME,UID,MFXUID,ADDRESS,ICON,DECODERTYPE,MFXSID,TACHOMAX,VMIN,ACCELERATIONDELAY,BRAKEDELAY,VOLUME,SPM,VELOCITY,DIRECTION,MFXTYPE,BLOCKS) VALUES
	 (16392,'BR 44 690','BR 44 690','16392','1945180592',8,'DB BR 44 100','mfx','0x8',80,5,21,12,233,NULL,0,0,NULL,NULL),
	 (16393,'Rheingold 1','Rheingold 1','16393','1945195567',9,'DB BR 18 537','mfx','0x9',81,4,12,8,255,NULL,0,0,NULL,NULL),
	 (16394,'561-05 RRF','561-05 RRF','16394','1945385732',10,'56-05 RRF','mfx','0xa',120,5,31,31,220,'0',0,0,NULL,NULL),
	 (16395,'E 186 007-8 NS',NULL,'16395','1945441079',11,'NS 186 012-8','mfx','0xb',140,5,16,16,255,NULL,0,0,NULL,NULL),
	 (16396,'BR 216 059-6',NULL,'16396','1945302187',12,'DB BR 216 059-6','mfx','0xc',120,5,13,13,64,NULL,0,0,NULL,NULL),
	 (16397,'NS 1139','NS 1139','16397','4193976353',13,'NS 1136','mfx','0xd',140,6,16,4,64,NULL,0,0,NULL,NULL),
	 (16398,'Rheingold 2','Rheingold 2','16398','1945186577',14,'DB BR 18 473','mfx','0xe',81,4,12,8,255,NULL,0,0,NULL,NULL),
	 (16399,'Bpmbdzf 107-5','Bpmbdzf 107-5','16399','1945177866',15,'DB Bpmbdzf 296.1','mfx','0xf',121,4,12,12,64,NULL,0,0,NULL,NULL),
	 (16400,'SVT 137','SVT 137','16400','4294861587',16,'DRG SVT 137 150','mfx','0x10',10,3,28,10,255,NULL,0,0,NULL,NULL),
	 (49156,'NS Plan Y','DCC Lok 4','49156',NULL,4,'NS Plan Y','dcc',NULL,120,NULL,0,0,64,NULL,0,0,NULL,NULL);

INSERT INTO LOCOMOTIVES (ID,NAME,PREVIOUSNAME,UID,MFXUID,ADDRESS,ICON,DECODERTYPE,MFXSID,TACHOMAX,VMIN,ACCELERATIONDELAY,BRAKEDELAY,VOLUME,SPM,VELOCITY,DIRECTION,MFXTYPE,BLOCKS) VALUES
	 (49159,'NS Plan V','NS Plan V','49159',NULL,7,'NS Plan V','dcc',NULL,120,NULL,0,0,64,NULL,0,1,NULL,NULL),
	 (49163,'NS 1205','NS 1205','49163',NULL,11,'NS 1211','dcc',NULL,120,NULL,0,0,64,NULL,0,0,NULL,NULL);

INSERT INTO FUNCTIONS (LOCOID,"NUMBER","TYPE",FVALUE) VALUES
	 (16390,0,1,1),
	 (16390,1,4,0),
	 (16390,2,23,0),
	 (16390,3,10,0),
	 (16390,4,18,0),
	 (16390,5,20,0),
	 (16390,6,41,0),
	 (16390,7,10,0),
	 (16390,8,42,0),
	 (16390,9,116,0);

INSERT INTO FUNCTIONS (LOCOID,"NUMBER","TYPE",FVALUE) VALUES
	 (16390,10,220,0),
	 (16390,11,153,0),
	 (16390,12,11,0),
	 (16390,13,153,0),
	 (16390,14,112,1),
	 (16389,0,1,1),
	 (16389,1,172,0),
	 (16389,2,23,0),
	 (16389,3,10,0),
	 (16389,4,18,0);

INSERT INTO FUNCTIONS (LOCOID,"NUMBER","TYPE",FVALUE) VALUES
	 (16389,5,20,0),
	 (16389,6,41,0),
	 (16389,7,10,0),
	 (16389,8,42,0),
	 (16389,9,171,0),
	 (16389,10,171,0),
	 (16389,11,29,0),
	 (16389,12,11,0),
	 (16389,13,116,0),
	 (16389,14,220,0);

INSERT INTO FUNCTIONS (LOCOID,"NUMBER","TYPE",FVALUE) VALUES
	 (16389,15,138,0),
	 (16394,0,1,1),
	 (16394,1,7,0),
	 (16394,2,23,0),
	 (16394,3,10,0),
	 (16394,4,18,0),
	 (16394,5,20,0),
	 (16394,6,41,0),
	 (16394,7,138,0),
	 (16394,8,42,0);

INSERT INTO FUNCTIONS (LOCOID,"NUMBER","TYPE",FVALUE) VALUES
	 (16394,9,48,0),
	 (16394,10,29,0),
	 (16394,11,117,0),
	 (16394,12,116,0),
	 (16394,13,118,0),
	 (16394,14,118,0),
	 (16394,15,112,0),
	 (16394,16,10,0),
	 (16394,17,138,0),
	 (16394,18,8,0);

INSERT INTO FUNCTIONS (LOCOID,"NUMBER","TYPE",FVALUE) VALUES
	 (16394,19,220,0),
	 (16394,20,236,0),
	 (16394,21,171,0),
	 (16394,22,5,0),
	 (16394,23,171,0),
	 (16394,24,45,0),
	 (16394,25,45,0),
	 (16394,26,28,0),
	 (23,0,1,1),
	 (23,2,4,0);

INSERT INTO FUNCTIONS (LOCOID,"NUMBER","TYPE",FVALUE) VALUES
	 (23,4,18,0),
	 (12,0,1,1),
	 (12,3,8,0),
	 (12,4,18,0),
	 (16396,0,1,1),
	 (16396,4,18,0),
	 (16392,0,1,1),
	 (16392,1,7,0),
	 (16392,2,23,0),
	 (16392,3,12,0);

INSERT INTO FUNCTIONS (LOCOID,"NUMBER","TYPE",FVALUE) VALUES
	 (16392,4,18,0),
	 (16392,5,20,0),
	 (16392,6,82,0),
	 (16392,7,140,0),
	 (16392,8,31,0),
	 (16392,9,106,0),
	 (16392,10,219,0),
	 (16392,11,26,0),
	 (16392,12,36,0),
	 (16392,13,111,0);

INSERT INTO FUNCTIONS (LOCOID,"NUMBER","TYPE",FVALUE) VALUES
	 (16392,14,49,0),
	 (16392,16,5,0),
	 (16392,17,5,0),
	 (16392,18,5,0),
	 (16392,19,236,0),
	 (16392,20,43,0),
	 (16392,21,8,0),
	 (16392,22,22,0),
	 (16392,24,31,0),
	 (16392,25,37,0);

INSERT INTO FUNCTIONS (LOCOID,"NUMBER","TYPE",FVALUE) VALUES
	 (16392,26,133,0),
	 (2,0,1,1),
	 (2,4,18,0),
	 (16399,0,1,1),
	 (16399,1,4,0),
	 (16399,2,32,1),
	 (16399,3,48,0),
	 (16399,4,32,1),
	 (16391,0,1,1),
	 (16391,2,23,0);

INSERT INTO FUNCTIONS (LOCOID,"NUMBER","TYPE",FVALUE) VALUES
	 (16391,3,138,0),
	 (16391,4,18,0),
	 (16395,0,1,1),
	 (16395,1,172,0),
	 (16395,2,23,0),
	 (16395,3,10,0),
	 (16395,4,18,0),
	 (16395,5,20,0),
	 (16395,6,41,0),
	 (16395,7,138,0);

INSERT INTO FUNCTIONS (LOCOID,"NUMBER","TYPE",FVALUE) VALUES
	 (16395,8,42,0),
	 (16395,9,171,0),
	 (16395,10,171,0),
	 (16395,11,220,0),
	 (16395,12,29,0),
	 (16395,13,11,0),
	 (16395,14,37,0),
	 (8,0,1,1),
	 (16397,0,1,1),
	 (16397,1,48,0);

INSERT INTO FUNCTIONS (LOCOID,"NUMBER","TYPE",FVALUE) VALUES
	 (16397,2,48,0),
	 (16397,3,8,0),
	 (16397,4,18,0),
	 (16397,5,42,0),
	 (16397,6,41,0),
	 (16397,7,118,0),
	 (37,0,1,1),
	 (37,2,10,0),
	 (37,3,10,0),
	 (37,4,18,0);

INSERT INTO FUNCTIONS (LOCOID,"NUMBER","TYPE",FVALUE) VALUES
	 (63,0,1,1),
	 (63,4,18,0),
	 (49156,0,1,1),
	 (49156,1,2,1),
	 (49156,2,2,1),
	 (49156,3,8,0),
	 (49156,4,18,0),
	 (16393,0,1,1),
	 (16393,1,7,0),
	 (16393,2,23,0);

INSERT INTO FUNCTIONS (LOCOID,"NUMBER","TYPE",FVALUE) VALUES
	 (16393,3,8,0),
	 (16393,4,18,0),
	 (16393,5,140,0),
	 (16393,6,12,0),
	 (16393,7,26,0),
	 (16393,8,5,0),
	 (16393,9,13,0),
	 (16393,10,111,0),
	 (16393,11,36,0),
	 (16393,12,173,0);

INSERT INTO FUNCTIONS (LOCOID,"NUMBER","TYPE",FVALUE) VALUES
	 (16393,13,173,0),
	 (16393,14,173,0),
	 (16393,15,37,0),
	 (16398,0,1,1),
	 (16398,1,7,0),
	 (16398,2,23,0),
	 (16398,3,8,0),
	 (16398,4,18,0),
	 (16398,5,140,0),
	 (16398,6,12,0);

INSERT INTO FUNCTIONS (LOCOID,"NUMBER","TYPE",FVALUE) VALUES
	 (16398,7,26,0),
	 (16398,8,5,0),
	 (16398,9,13,0),
	 (16398,10,111,0),
	 (16398,11,36,0),
	 (16398,12,173,0),
	 (16398,13,173,0),
	 (16398,14,173,0),
	 (16398,15,37,0),
	 (61,0,1,1);

INSERT INTO FUNCTIONS (LOCOID,"NUMBER","TYPE",FVALUE) VALUES
	 (49163,0,1,1),
	 (49163,1,118,0),
	 (49163,2,47,0),
	 (49163,3,8,0),
	 (49163,4,18,0),
	 (49163,5,41,0),
	 (49163,6,42,0),
	 (49159,0,1,1),
	 (49159,1,48,1),
	 (49159,2,48,1);

INSERT INTO FUNCTIONS (LOCOID,"NUMBER","TYPE",FVALUE) VALUES
	 (49159,3,18,0),
	 (16400,2,138,0),
	 (16400,4,156,0),
	 (16400,7,139,0),
	 (16400,8,153,0),
	 (16400,9,5,0),
	 (16400,10,133,0),
	 (16400,13,138,0),
	 (16400,14,133,0);

INSERT INTO ACCESSORIES (ADDRESS,NAME,"TYPE","POSITION",STATES,SWITCHTIME,DECODERTYPE,DECODER,AGROUP,ICON,ICONFILE) VALUES
	 (1,'W 1R','rechtsweiche',1,2,200,'mm','ein_alt','weichen','005','magicon_a_005_01.svg'),
	 (2,'W 2L','linksweiche',1,2,200,'mm','ein_alt','weichen','006','magicon_a_006_01.svg'),
	 (3,'W 3R','rechtsweiche',0,2,200,'mm','ein_alt','weichen','005','magicon_a_005_00.svg'),
	 (4,'W 4R','rechtsweiche',0,2,200,'mm','ein_alt','weichen','005','magicon_a_005_00.svg'),
	 (5,'W 5R','rechtsweiche',1,2,200,'mm','ein_alt','weichen','005','magicon_a_005_01.svg'),
	 (6,'W 6R','rechtsweiche',1,2,200,'mm','ein_alt','weichen','005','magicon_a_005_01.svg'),
	 (7,'W 7L','linksweiche',1,2,200,'mm','ein_alt','weichen','006','magicon_a_006_01.svg'),
	 (8,'W 8L','linksweiche',1,2,200,'mm','ein_alt','weichen','006','magicon_a_006_01.svg'),
	 (9,'W 9R','rechtsweiche',0,2,200,'mm','ein_alt','weichen','005','magicon_a_005_00.svg'),
	 (10,'W 10R','rechtsweiche',1,2,200,'mm','ein_alt','weichen','005','magicon_a_005_01.svg');

INSERT INTO ACCESSORIES (ADDRESS,NAME,"TYPE","POSITION",STATES,SWITCHTIME,DECODERTYPE,DECODER,AGROUP,ICON,ICONFILE) VALUES
	 (11,'W 11L','linksweiche',0,2,200,'mm','ein_alt','weichen','006','magicon_a_006_00.svg'),
	 (12,'W 12L','linksweiche',0,2,200,'mm','ein_alt','weichen','006','magicon_a_006_00.svg'),
	 (13,'W 13L','linksweiche',1,2,200,'mm','ein_alt','weichen','006','magicon_a_006_01.svg'),
	 (14,'W 14R','rechtsweiche',0,2,200,'mm','ein_alt','weichen','005','magicon_a_005_00.svg'),
	 (15,'S 15','lichtsignal_SH01',0,2,200,'mm','ein_alt','lichtsignale','019','magicon_a_019_00.svg'),
	 (16,'S 16','lichtsignal_SH01',0,2,200,'mm','ein_alt','lichtsignale','019','magicon_a_019_00.svg'),
	 (17,'W 17R','rechtsweiche',1,2,200,'mm','ein_alt','weichen','005','magicon_a_005_01.svg'),
	 (18,'W 18R','rechtsweiche',1,2,200,'mm','ein_alt','weichen','005','magicon_a_005_01.svg'),
	 (19,'S 19','lichtsignal_HP01',0,2,200,'mm','ein_alt','lichtsignale','015','magicon_a_015_00.svg'),
	 (20,'S  20','lichtsignal_HP01',0,2,200,'mm','ein_alt','lichtsignale','015','magicon_a_015_00.svg');

INSERT INTO ACCESSORIES (ADDRESS,NAME,"TYPE","POSITION",STATES,SWITCHTIME,DECODERTYPE,DECODER,AGROUP,ICON,ICONFILE) VALUES
	 (21,'S 21','lichtsignal_SH01',0,2,200,'mm','ein_alt','lichtsignale','019','magicon_a_019_00.svg'),
	 (22,'S 22','lichtsignal_SH01',0,2,200,'mm','ein_alt','lichtsignale','019','magicon_a_019_00.svg'),
	 (23,'S 23','lichtsignal_SH01',0,2,200,'mm','ein_alt','lichtsignale','019','magicon_a_019_00.svg'),
	 (24,'S 24','lichtsignal_SH01',0,2,200,'mm','ein_alt','lichtsignale','019','magicon_a_019_00.svg'),
	 (25,'S 25/26','urc_lichtsignal_HP012_SH01',0,4,200,'mm','ein_alt','lichtsignale','027','magicon_a_027_00.svg'),
	 (27,'S 27/28','urc_lichtsignal_HP012_SH01',0,4,200,'mm','ein_alt','lichtsignale','027','magicon_a_027_00.svg'),
	 (29,'S 29/30','urc_lichtsignal_HP012_SH01',0,4,200,'mm','ein_alt','lichtsignale','027','magicon_a_027_00.svg'),
	 (31,'S 31/32','urc_lichtsignal_HP012_SH01',0,4,200,'mm','ein_alt','lichtsignale','027','magicon_a_027_00.svg'),
	 (33,'S 33','lichtsignal_HP01',1,2,200,'mm','ein_alt','lichtsignale','015','magicon_a_015_01.svg'),
	 (34,'W 34','linksweiche',1,2,200,'mm','ein_alt','weichen','006','magicon_a_006_01.svg');

INSERT INTO ACCESSORIES (ADDRESS,NAME,"TYPE","POSITION",STATES,SWITCHTIME,DECODERTYPE,DECODER,AGROUP,ICON,ICONFILE) VALUES
	 (35,'S 35','lichtsignal_HP01',0,2,200,'mm','ein_alt','lichtsignale','015','magicon_a_015_00.svg'),
	 (36,'S 36','lichtsignal_HP01',0,2,200,'mm','ein_alt','lichtsignale','015','magicon_a_015_00.svg'),
	 (41,'S 41','urc_lichtsignal_HP012',0,3,200,'mm','ein_alt','lichtsignale','026','magicon_a_026_00.svg'),
	 (43,'S 43','lichtsignal_HP01',0,2,200,'mm','ein_alt','lichtsignale','015','magicon_a_015_00.svg'),
	 (47,'S 20','lichtsignal_HP01',0,2,200,'mm','ein_alt','lichtsignale','015','magicon_a_015_00.svg');


INSERT INTO SENSORS (NAME,DEVICEID,CONTACTID,STATUS,PREVIOUSSTATUS,MILLIS,LASTUPDATED) VALUES
	 ('M1',65,1,NULL,NULL,NULL,NULL),
	 ('M2',65,2,NULL,NULL,NULL,NULL);
	

INSERT INTO TILES (ID,TILETYPE,ORIENTATION,DIRECTION,X,Y,SIGNALTYPE,ACCESSORYID,SENSORID) VALUES
	 ('bk-2','Block','East','Center',420,140,NULL,NULL,NULL),
	 ('st-1','Straight','East','Center',300,180,NULL,NULL,NULL),
	 ('ct-2','Curved','East','Center',260,140,NULL,NULL,NULL),
	 ('se-6','Sensor','West','Center',500,380,NULL,NULL,NULL),
	 ('st-3','Straight','East','Center',300,140,NULL,NULL,NULL),
	 ('st-4','Straight','East','Center',540,140,NULL,NULL,NULL),
	 ('st-7','Straight','South','Center',180,220,NULL,NULL,NULL),
	 ('se-1','Sensor','East','Center',340,180,NULL,NULL,NULL),
	 ('st-20','Straight','West','Center',620,380,NULL,NULL,NULL),
	 ('se-4','Sensor','East','Center',340,140,NULL,NULL,1);

INSERT INTO TILES (ID,TILETYPE,ORIENTATION,DIRECTION,X,Y,SIGNALTYPE,ACCESSORYID,SENSORID) VALUES
	 ('st-19','Straight','West','Center',580,380,NULL,NULL,NULL),
	 ('sw-1','Switch','West','Left',260,180,NULL,null,NULL),
	 ('se-5','Sensor','West','Center',340,380,NULL,NULL,NULL),
	 ('ct-5','Curved','North','Center',180,380,NULL,NULL,NULL),
	 ('st-18','Straight','West','Center',540,380,NULL,NULL,NULL),
	 ('st-2','Straight','East','Center',540,180,NULL,NULL,NULL),
	 ('ct-3','Curved','East','Center',180,180,NULL,NULL,NULL),
	 ('st-10','Straight','South','Center',180,340,NULL,NULL,NULL),
	 ('st-15','Straight','West','Center',220,380,NULL,NULL,NULL),
	 ('st-12','Straight','South','Center',660,260,NULL,NULL,NULL);

INSERT INTO TILES (ID,TILETYPE,ORIENTATION,DIRECTION,X,Y,SIGNALTYPE,ACCESSORYID,SENSORID) VALUES
	 ('st-16','Straight','West','Center',260,380,NULL,NULL,NULL),
	 ('st-9','Straight','South','Center',180,300,NULL,NULL,NULL),
	 ('bk-3','Block','West','Center',420,380,NULL,NULL,NULL),
	 ('st-6','Straight','East','Center',220,180,NULL,NULL,NULL),
	 ('se-3','Sensor','East','Center',500,140,NULL,NULL,null),
	 ('st-8','Straight','South','Center',180,260,NULL,NULL,NULL),
	 ('st-11','Straight','South','Center',660,220,NULL,NULL,NULL),
	 ('bk-1','Block','East','Center',420,180,NULL,NULL,NULL),
	 ('st-13','Straight','South','Center',660,300,NULL,NULL,NULL),
	 ('se-2','Sensor','East','Center',500,180,NULL,NULL,NULL);

INSERT INTO TILES (ID,TILETYPE,ORIENTATION,DIRECTION,X,Y,SIGNALTYPE,ACCESSORYID,SENSORID) VALUES
	 ('ct-6','Curved','West','Center',660,380,NULL,NULL,NULL),
	 ('st-14','Straight','South','Center',660,340,NULL,NULL,NULL),
	 ('ct-4','Curved','South','Center',660,180,NULL,NULL,NULL),
	 ('sw-2','Switch','East','Right',580,180,NULL,null,NULL),
	 ('st-5','Straight','East','Center',620,180,NULL,NULL,NULL),
	 ('st-17','Straight','West','Center',300,380,NULL,NULL,NULL),
	 ('ct-1','Curved','South','Center',580,140,NULL,NULL,NULL);

COMMIT;	
	

/*
 * Old stuff here for reference only
 * remove when no longer needed... 

DROP TABLE if exists routes CASCADE CONSTRAINTS;

DROP SEQUENCE if exists drwa_seq;
DROP SEQUENCE if exists rout_seq;

CREATE SEQUENCE drwa_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE rout_seq START WITH 1 INCREMENT BY 1;


CREATE TABLE functions (
  locoid             NUMBER NOT NULL,
  number             INTEGER NOT NULL,
  type               INTEGER NOT NULL,
  value              INTEGER
);

ALTER TABLE functions ADD CONSTRAINT func_pk PRIMARY KEY ( locoid, number );

ALTER TABLE functions ADD CONSTRAINT func_loco_fk FOREIGN KEY ( locoid ) REFERENCES locomotives ( id ) NOT DEFERRABLE;  

*/

