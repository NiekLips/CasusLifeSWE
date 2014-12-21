/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Contains the methods for querying the database.
 * @author Roy
 */
public class Database {
    private static Connection connection = null;
    
    /**
     * Opens the connection with the CasusLife.db sqlite database, the database will be created if it doesn't exist.
     * @return If the opening was successful.
     */
    public static boolean openConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:CasusLife.db");
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Closes the connection with the CasusLife.db sqlite database.
     * @return If the closing was successful.
     */
    public static boolean closeConnection() {
        try {
            if (connection != null) connection.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    /**
     * Creates the database with the tables.
     * @return If the creation of the tables went successful.
     */
    public static boolean createDatabase() {
        try (Statement stmt = connection.createStatement()) {
            List<String> existingTables = new ArrayList<>();
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", null);
            while (rs.next()) {
                existingTables.add(rs.getString(3));
            }
            rs.close();
            
            String sql;
            if (!existingTables.contains("Simulation")) {
                sql = "CREATE TABLE `Simulation` (\n" +
                        "	`SimulationID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                        "	`TotalCountOmnivore`	INTEGER,\n" +
                        "	`TotalCountHerbivore`	INTEGER,\n" +
                        "	`TotalCountCarnivore`	INTEGER,\n" +
                        "	`TotalCountVegetation`	INTEGER,\n" +
                        "	`TotalEnergyOmnivore`	INTEGER,\n" +
                        "	`TotalEnergyHerbivore`	INTEGER,\n" +
                        "	`TotalEnergyCarnivore`	INTEGER,\n" +
                        "	`TotalEnergyVegetation`	INTEGER\n" +
                        ");";
                stmt.executeUpdate(sql);
                System.out.println("Table Simulation created successfully");
            }
            if (!existingTables.contains("History")) {
                sql = "CREATE TABLE `History` (\n" +
                        "	`SimulationID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                        "	`SimulationStep`	INTEGER NOT NULL,\n" +
                        "	`Data`	BLOB NOT NULL\n" +
                        ");";
                stmt.executeUpdate(sql);
                System.out.println("Table History created successfully");
            }
            if (!existingTables.contains("Users")) {
                sql = "CREATE TABLE `Users` (\n" +
                        "	`UserID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                        "	`UserName`	TEXT NOT NULL UNIQUE,\n" +
                        "	`Password`	TEXT NOT NULL,\n" +
                        "	`UserRights`	INTEGER\n" +
                        ");";
                stmt.executeUpdate(sql);
                System.out.println("Table Users created successfully");
            }
                
            return true;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }
}
