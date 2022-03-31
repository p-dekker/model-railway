/*
 * Copyright (C) 2019 frans.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package jcs.trackservice.dao.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import jcs.util.RunUtil;
import org.tinylog.Logger;

/**
 *
 * @author frans
 */
public class DatabaseCreator {

    protected static final String DB_PATH = "~/jcs/";
    protected static final String TEST_DB_NAME = "jcs-test-db";
    protected static final String DB_NAME = "jcs-db";
    protected static final String TEST_SCHEMA = "SCHEMA=JCS";
    protected static final String SCHEMA = "SCHEMA=JCS";
    protected static final String DB_MODE = "AUTO_SERVER=TRUE";
    protected static final String ADMIN_USER = "SA";
    protected static final String ADMIN_PWD = "jcs";
    protected static final String JCS_USER = "JCS";
    protected static final String JCS_PWD = "repo";

    public DatabaseCreator() {
        super();
    }

    protected static Connection connect(String user, String password, boolean useDefaultSchema, boolean testMode) {
        String jdbcURL = "jdbc:h2:" + DB_PATH;
        if (testMode) {
            jdbcURL = jdbcURL + TEST_DB_NAME;
        } else {
            jdbcURL = jdbcURL + DB_NAME;
        }
        jdbcURL = jdbcURL + ";" + DB_MODE;
        if (useDefaultSchema) {
            if (testMode) {
                jdbcURL = jdbcURL + ";" + TEST_SCHEMA;
            } else {
                jdbcURL = jdbcURL + ";" + SCHEMA;
            }
        }

        Connection conn = null;
        Logger.trace("URL: " + jdbcURL);
        try {
            conn = DriverManager.getConnection(jdbcURL, user, password);
            String cat = conn.getCatalog();

            Logger.trace("User: " + user + " Connected to " + cat);
        } catch (SQLException sqle) {
            Logger.error("Can't connect to: " + jdbcURL + " as " + user + "...", sqle);
        }
        return conn;
    }

    private static boolean deleteDatebaseFile(boolean testMode) {
        String path = System.getProperty("user.home") + File.separator + ".jcs";
        File jcsPath = new File(path);
        boolean success;
        int fileCntDel = 0;

        String fileName = testMode ? "jcs-test-db" : "jcs-db";

        if (jcsPath.exists() && jcsPath.isDirectory()) {
            File[] files = jcsPath.listFiles((File f, String n) -> {
                return n.contains(fileName);
            });

            fileCntDel = files.length;
            for (int i = 1; i < files.length; i++) {
                Logger.trace("deleting " + files[i].getName());
                success = files[i].delete();
                if (!success) {
                    break;
                }
            }

            success = true;
            //Check again 
            files = jcsPath.listFiles((File f, String n) -> {
                return n.contains("jcs-test-db");
            });

            for (int i = 1; i < files.length; i++) {
                success = false;
            }
        } else {
            success = true;
        }

        if (fileCntDel > 0) {
            Logger.info(fileCntDel + " Database file(s) " + (success ? "successfully" : "not") + " deleted...");
        } else {
            Logger.info("No Database file(s) removed...");
        }

        //On Windows the files is not released... but the database is wiped anyway...
        if (!success && RunUtil.getOsType() == RunUtil.OS_WINDOWS) {
            return true;
        }

        return success;
    }

    private static void dropObjects(Statement stmt) throws SQLException {
        stmt.executeUpdate("DROP TABLE if exists locomotives CASCADE CONSTRAINTS");
        stmt.executeUpdate("DROP TABLE if exists functions CASCADE CONSTRAINTS");
        stmt.executeUpdate("DROP TABLE if exists jcsproperties CASCADE CONSTRAINTS;");
        stmt.executeUpdate("DROP TABLE if exists accessories CASCADE CONSTRAINTS");

        stmt.executeUpdate("DROP TABLE if exists tiles CASCADE CONSTRAINTS");

        stmt.executeUpdate("DROP TABLE if exists accessorytypes CASCADE CONSTRAINTS");
        stmt.executeUpdate("DROP TABLE if exists statustypes CASCADE CONSTRAINTS");
        stmt.executeUpdate("DROP TABLE if exists trackpower CASCADE CONSTRAINTS");
        stmt.executeUpdate("DROP TABLE if exists signalvalues CASCADE CONSTRAINTS;");
        stmt.executeUpdate("DROP TABLE if exists sensors CASCADE CONSTRAINTS");

        stmt.executeUpdate("DROP SEQUENCE if exists femo_seq");
        stmt.executeUpdate("DROP SEQUENCE if exists soac_seq");
        stmt.executeUpdate("DROP SEQUENCE if exists trpo_seq");
        stmt.executeUpdate("DROP SEQUENCE if exists sens_seq");

        Logger.trace("Existing schema objects dropped...");
    }

    private static void createTiles(Statement stmt) throws SQLException {
        stmt.executeUpdate("create table tiles ("
                + "id           VARCHAR(255) not null,"
                + "tileType     VARCHAR(255) not null,"
                + "orientation  VARCHAR(255) not null,"
                + "direction    VARCHAR(255) not null,"
                + "x            INTEGER NOT NULL,"
                + "y            INTEGER NOT NULL,"
                + "signalType   VARCHAR(255),"
                + "beanid       BIGINT)");

        stmt.executeUpdate("ALTER TABLE tiles ADD CONSTRAINT tile_pk PRIMARY KEY ( id )");
        stmt.executeUpdate("CREATE UNIQUE INDEX tiles_x_y_idx on tiles ( x, y )");
        Logger.trace("Table tiles created...");
    }

    private static void createTrackpower(Statement stmt) throws SQLException {
        stmt.executeUpdate("create table trackpower ("
                + "id             IDENTITY not null,"
                + "status         VARCHAR2(255) not null,"
                + "feedbacksource VARCHAR2(255) NOT NULL,"
                + "lastupdated    DATE)");

        Logger.trace("Table trackpower created...");
    }

    private static void accessoryTypes(Statement stmt) throws SQLException {
        stmt.executeUpdate("CREATE TABLE accessorytypes ("
                + "accessory_type   VARCHAR2(255) NOT NULL,"
                + "description      VARCHAR2(255))");

        stmt.executeUpdate("ALTER TABLE accessorytypes ADD CONSTRAINT acty_pk PRIMARY KEY ( accessory_type )");
        Logger.trace("Table accessorytypes created...");
    }

    private static void sensors(Statement stmt) throws SQLException {
        stmt.executeUpdate("CREATE TABLE sensors ("
                + "id                IDENTITY not null,"
                + "name              VARCHAR2(255) NOT NULL,"
                + "deviceid          INTEGER,"
                + "contactid         INTEGER,"
                + "status            INTEGER,"
                + "previousstatus    INTEGER,"
                + "millis            INTEGER,"
                + "lastupdated       DATE)");

        Logger.trace("Table sensors created...");
    }

    private static void locomotives(Statement stmt) throws SQLException {
        stmt.executeUpdate("CREATE TABLE locomotives ("
                + "id                 bigint NOT NULL, "
                + "name               VARCHAR(255) NOT NULL, "
                + "previousname       VARCHAR(255), "
                + "uid                VARCHAR(255), "
                + "mfxuid             VARCHAR(255), "
                + "address            INTEGER NOT NULL, "
                + "icon               VARCHAR(255), "
                + "decodertype        VARCHAR(255), "
                + "mfxsid             VARCHAR(255), "
                + "tachomax           INTEGER, "
                + "vmin               INTEGER, "
                + "accelerationDelay  INTEGER, "
                + "brakeDelay         INTEGER, "
                + "volume             INTEGER, "
                + "spm                VARCHAR(255), "
                + "velocity           INTEGER, "
                + "direction          INTEGER, "
                + "mfxtype            VARCHAR(255), "
                + "blocks             VARCHAR(255))");

        stmt.executeUpdate("ALTER TABLE locomotives ADD CONSTRAINT loco_pk PRIMARY KEY ( id )");
        stmt.executeUpdate("ALTER TABLE locomotives ADD CONSTRAINT loco_address_un UNIQUE ( address, decodertype )");
        Logger.trace("Table locomotives created...");
    }

    private static void functions(Statement stmt) throws SQLException {
        stmt.executeUpdate("CREATE TABLE functions ("
                + "locoid           bigint NOT NULL, "
                + "number           INTEGER NOT NULL, "
                + "type             INTEGER NOT NULL, "
                + "fvalue           INTEGER)");

        stmt.executeUpdate("ALTER TABLE functions ADD CONSTRAINT func_pk PRIMARY KEY ( locoid, number )");
        Logger.trace("Table functions created...");
    }

    private static void accessories(Statement stmt) throws SQLException {
        stmt.executeUpdate("CREATE TABLE accessories ("
                + "id                    bigint NOT NULL,"
                + "address               INTEGER NOT NULL,"
                + "name                  VARCHAR(255) NOT NULL,"
                + "type                  VARCHAR(255) NOT NULL,"
                + "position              INTEGER,"
                + "states                INTEGER,"
                + "switchtime            INTEGER,"
                + "decodertype           VARCHAR(255),"
                + "decoder               VARCHAR(255),"
                + "agroup                VARCHAR(255),"
                + "icon                  VARCHAR(255),"
                + "iconfile              VARCHAR(255))");

        //stmt.executeUpdate("ALTER TABLE accessories ADD CONSTRAINT soac_pk PRIMARY KEY ( id )");
        stmt.executeUpdate("ALTER TABLE accessories ADD CONSTRAINT asse_address_un UNIQUE ( address );");
        Logger.trace("Table solenoidaccessories created...");
    }

    private static void statustypes(Statement stmt) throws SQLException {
        stmt.executeUpdate("CREATE TABLE statustypes ("
                + "status_type    VARCHAR(255) NOT NULL,"
                + "description    VARCHAR(255))");

        stmt.executeUpdate("ALTER TABLE statustypes ADD CONSTRAINT stty_pk PRIMARY KEY ( status_type )");

        Logger.trace("Table statustypes created...");
    }

    private static void signalvalues(Statement stmt) throws SQLException {
        stmt.executeUpdate("CREATE TABLE signalvalues ("
                + "signal_value    VARCHAR(255) NOT NULL,"
                + "description     VARCHAR(255))");

        stmt.executeUpdate("ALTER TABLE signalvalues ADD CONSTRAINT siva_pk PRIMARY KEY ( signal_value )");

        Logger.trace("Table signalvalues created...");
    }

    private static void jcsproperties(Statement stmt) throws SQLException {
        stmt.executeUpdate("CREATE TABLE jcsproperties ("
                //+ "id         IDENTITY,"
                + "pkey    VARCHAR(255) NOT NULL,"
                + "pvalue  VARCHAR(255) )");

        stmt.executeUpdate("ALTER TABLE jcsproperties ADD CONSTRAINT prop_pk PRIMARY KEY ( pkey )");
        //stmt.executeUpdate("ALTER TABLE jcsproperties ADD CONSTRAINT prop_key_un UNIQUE ( \"key\" );");

        Logger.trace("Table jcsproperties created...");
    }

//    private static void driveways(Statement stmt) throws SQLException {
//        stmt.executeUpdate("CREATE TABLE driveways ("
//                + "id             NUMBER NOT NULL,"
//                + "address        INTEGER NOT NULL,"
//                + "name           VARCHAR2(255 CHAR),"
//                + "description    VARCHAR2(255 CHAR),"
//                + "from_lati_id   NUMBER,"
//                + "to_lati_id     NUMBER,"
//                + "loco_id        NUMBER,"
//                + "active         INTEGER NOT NULL,"
//                + "reserved       INTEGER NOT NULL,"
//                + "occupied       INTEGER NOT NULL)");
//
//        stmt.executeUpdate("ALTER TABLE driveways ADD CONSTRAINT drwa_pk PRIMARY KEY ( id )");
//
//        stmt.executeUpdate("ALTER TABLE driveways ADD CONSTRAINT drwa_address_un UNIQUE ( address )");
//
//        Logger.trace("Table driveways created...");
//    }
//    private static void routes(Statement stmt) throws SQLException {
//        stmt.executeUpdate("CREATE TABLE routes ("
//                + "id             NUMBER NOT NULL,"
//                + "address        INTEGER NOT NULL,"
//                + "name           VARCHAR2(255 CHAR),"
//                + "description    VARCHAR2(255 CHAR),"
//                + "drwa_id        NUMBER NOT NULL,"
//                + "lati_id        NUMBER NOT NULL)");
//
//        stmt.executeUpdate("ALTER TABLE routes ADD CONSTRAINT rout_pk PRIMARY KEY ( id )");
//
//        stmt.executeUpdate("ALTER TABLE routes ADD CONSTRAINT rout_address_un UNIQUE ( address )");
//
//        Logger.trace("Table routes created...");
//    }
    private static void createForeignKeys(Statement stmt) throws SQLException {
        stmt.executeUpdate("ALTER TABLE functions"
                + " ADD CONSTRAINT func_loco_fk FOREIGN KEY ( locoid )"
                + " REFERENCES locomotives ( id )"
                + " NOT DEFERRABLE");

//        stmt.executeUpdate("ALTER TABLE accessories"
//                + " ADD CONSTRAINT soac_acty_fk FOREIGN KEY ( accessory_type )"
//                + " REFERENCES accessorytypes ( accessory_type )"
//                + " NOT DEFERRABLE");
//        stmt.executeUpdate("ALTER TABLE accessories"
//                + " ADD CONSTRAINT soac_stty_fk FOREIGN KEY ( current_status_type )"
//                + " REFERENCES statustypes ( status_type )"
//                + " NOT DEFERRABLE");
//        stmt.executeUpdate("ALTER TABLE accessories"
//                + " ADD CONSTRAINT soac_soac_fk FOREIGN KEY ( soac_id )"
//                + " REFERENCES accessories ( id )"
//                + " NOT DEFERRABLE");
//        stmt.executeUpdate("ALTER TABLE accessories"
//                + " ADD CONSTRAINT soac_siva_fk FOREIGN KEY ( signal_value )"
//                + " REFERENCES signalvalues ( signal_value )"
//                + " NOT DEFERRABLE");
//        stmt.executeUpdate("ALTER TABLE layouttiles"
//                + " ADD CONSTRAINT lati_soac_fk FOREIGN KEY ( soac_id )"
//                + " REFERENCES accessories ( id )"
//                + " NOT DEFERRABLE");
//        stmt.executeUpdate("ALTER TABLE layouttiles"
//                + " ADD CONSTRAINT lati_sens_fk FOREIGN KEY ( sens_id )"
//                + " REFERENCES sensors ( id )"
//                + " NOT DEFERRABLE");
//        stmt.executeUpdate("ALTER TABLE driveways"
//                + " ADD CONSTRAINT from_lati_fk FOREIGN KEY ( from_lati_id )"
//                + " REFERENCES layouttiles ( id )"
//                + " NOT DEFERRABLE");
//        stmt.executeUpdate("ALTER TABLE driveways"
//                + " ADD CONSTRAINT to_lati_fk FOREIGN KEY ( to_lati_id )"
//                + " REFERENCES layouttiles ( id )"
//                + " NOT DEFERRABLE");
//        stmt.executeUpdate("ALTER TABLE driveways"
//                + " ADD CONSTRAINT loco_drwa_fk FOREIGN KEY ( loco_id )"
//                + " REFERENCES locomotives ( id )"
//                + " NOT DEFERRABLE");
//        stmt.executeUpdate("ALTER TABLE routes"
//                + " ADD CONSTRAINT drwa_rout_fk FOREIGN KEY ( drwa_id )"
//                + " REFERENCES driveways ( id )"
//                + " NOT DEFERRABLE");
//        stmt.executeUpdate("ALTER TABLE routes"
//                + " ADD CONSTRAINT lati_rout_fk FOREIGN KEY ( lati_id )"
//                + " REFERENCES layouttiles ( id )"
//                + " NOT DEFERRABLE");
        Logger.trace("Foreign Keys created...");
    }

    private static void insertReferenceData(Statement stmt) throws SQLException {
        stmt.executeUpdate("INSERT INTO trackpower(STATUS,FEEDBACKSOURCE,LASTUPDATED) VALUES('OFF','OTHER',null)");

        stmt.executeUpdate("INSERT INTO accessorytypes (accessory_type,description) values ('S','Signal')");
        stmt.executeUpdate("INSERT INTO accessorytypes (accessory_type,description) values ('T','Turnout')");
        stmt.executeUpdate("INSERT INTO accessorytypes (accessory_type,description) values ('G','General')");

        stmt.executeUpdate("INSERT INTO statustypes (status_type,description) values('G','Green')");
        stmt.executeUpdate("INSERT INTO statustypes (status_type,description) values('R','Red')");
        stmt.executeUpdate("INSERT INTO statustypes (status_type,description) values('O','Off')");

        stmt.executeUpdate("INSERT INTO SIGNALVALUES (SIGNAL_VALUE,DESCRIPTION) VALUES ('Hp0','Hp0')");
        stmt.executeUpdate("INSERT INTO SIGNALVALUES (SIGNAL_VALUE,DESCRIPTION) VALUES ('Hp1','Hp1')");
        stmt.executeUpdate("INSERT INTO SIGNALVALUES (SIGNAL_VALUE,DESCRIPTION) VALUES ('Hp2','Hp2')");
        stmt.executeUpdate("INSERT INTO SIGNALVALUES (SIGNAL_VALUE,DESCRIPTION) VALUES ('Hp0Sh1','Hp0Sh1')");
        stmt.executeUpdate("INSERT INTO SIGNALVALUES (SIGNAL_VALUE,DESCRIPTION) VALUES ('OFF','OFF')");

        stmt.executeUpdate("INSERT INTO jcsproperties (PKEY,PVALUE) VALUES ('S88-module-count','3')");
        stmt.executeUpdate("INSERT INTO jcsproperties (PKEY,PVALUE) VALUES ('S88-demo','jcs.feedback.DemoFeedbackService')");
        stmt.executeUpdate("INSERT INTO jcsproperties (PKEY,PVALUE) VALUES ('S88-remote','FeedbackService')");
        stmt.executeUpdate("INSERT INTO jcsproperties (PKEY,PVALUE) VALUES ('S88-CS3','jcs.controller.cs3.MarklinCS3')");
        stmt.executeUpdate("INSERT INTO jcsproperties (PKEY,PVALUE) VALUES ('activeFeedbackService','CS3FeedbackService')");

        //Supported Controllers
        stmt.executeUpdate("INSERT INTO jcsproperties (PKEY,PVALUE) VALUES ('M6050-remote','ControllerService')");
        stmt.executeUpdate("INSERT INTO jcsproperties (PKEY,PVALUE) VALUES ('CS3','jcs.controller.cs3.MarklinCS3')");
        stmt.executeUpdate("INSERT INTO jcsproperties (PKEY,PVALUE) VALUES ('M6050-demo','jcs.controller.m6050.M6050DemoController')");
        stmt.executeUpdate("INSERT INTO jcsproperties (PKEY,PVALUE) VALUES ('M6050-local','jcs.controller.m6050.M6050Controller')");
        stmt.executeUpdate("INSERT INTO jcsproperties (PKEY,PVALUE) VALUES ('activeControllerService','CS3')");

        Logger.trace("Reference Data inserted...");
    }

    private static void createSchema(boolean testMode) {
        Logger.trace(testMode ? "Test Mode: " : "" + "Creating JCS schema objects...");
        try {
            try (Connection c = connect(JCS_USER, JCS_PWD, true, testMode)) {
                Statement stmt = c.createStatement();

                stmt.executeUpdate("set SCHEMA JCS");

                dropObjects(stmt);
                createTiles(stmt);

                createTrackpower(stmt);
                accessoryTypes(stmt);
                sensors(stmt);
                locomotives(stmt);
                functions(stmt);
                accessories(stmt);
                statustypes(stmt);
                signalvalues(stmt);
                jcsproperties(stmt);
                //trackplan
                //driveways(stmt);
                //routes(stmt);

                createForeignKeys(stmt);

                insertReferenceData(stmt);

                c.commit();
            }
        } catch (SQLException ex) {
            Logger.error(ex);
        }
    }

    private static void createDatabase(boolean testMode) {
        Logger.info("Creating new " + (testMode ? "TEST " : "") + "JCS Database...");

        if (!deleteDatebaseFile(testMode)) {
            Logger.error("Could not remove database file " + DB_PATH + (testMode ? TEST_DB_NAME : DB_NAME) + " quitting.");
            System.exit(1);
        }

        try {
            try (Connection c = connect(ADMIN_USER, ADMIN_PWD, false, testMode)) {
                if (c != null) {
                    Statement stmt = c.createStatement();

                    stmt.executeUpdate("DROP SCHEMA IF EXISTS JCS CASCADE");
                    stmt.executeUpdate("DROP USER IF EXISTS JCS CASCADE");

                    //Create the JCS user and schema
                    stmt.executeUpdate("CREATE USER JCS PASSWORD 'repo' ADMIN");
                    stmt.executeUpdate("CREATE SCHEMA JCS AUTHORIZATION JCS");

                    Logger.debug("Created JCS User.");
                } else {
                    Logger.error("Could not obtain a connection!");
                }
            }
        } catch (SQLException ex) {
            Logger.error(ex);
        }
    }

    protected static void recreateTest() {
        createDatabase(true);
        createSchema(true);
        Logger.info("Created new TEST JCS Database...");
    }

    public static void create() {
        createDatabase(false);
        createSchema(false);
        Logger.info("Created new a JCS Database...");
    }

    public static void main(String[] a) {
        //Configurator.defaultConfig().level(org.pmw.tinylog.Level.TRACE).activate();

        //recreateTest();
        create();
    }

}