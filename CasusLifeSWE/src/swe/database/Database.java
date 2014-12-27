/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.database;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import swe.life.Statistics;
import swe.life.WildLife;
import swe.user.User;
import swe.user.UserRights;

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
            try (ResultSet rs = md.getTables(null, null, "%", null)) {
                while (rs.next()) {
                    existingTables.add(rs.getString(3));
                }
            }
            
            String sql;
            if (!existingTables.contains("Simulations")) {
                sql = "CREATE TABLE `Statistics` (\n" +
                        "	`ID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                        ");";
                stmt.executeUpdate(sql);
                System.out.println("Table Simulation created successfully");
            }
            if (!existingTables.contains("Statistics")) {
                sql = "CREATE TABLE `Statistics` (\n" +
                        "	`SimulationID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                        "	`TotalCount`	INTEGER,\n" +
                        "	`TotalEnergy`	INTEGER,\n" +
                        "	`WildLife`	INTEGER,\n" +
                        ");";
                stmt.executeUpdate(sql);
                System.out.println("Table Statistics created successfully");
            }
            if (!existingTables.contains("History")) {
                sql = "CREATE TABLE `History` (\n" +
                        "	`SimulationID`	INTEGER NOT NULL,\n" +
                        "	`SimulationStep`INTEGER NOT NULL,\n" +
                        "	`Data`          BLOB NOT NULL\n" +
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
    
    /**
     * Checks if a user with name &amp; password exists in the database.
     * @param name The name for the user to search.
     * @return If the user exists with the name.
     * @throws SQLException Will be thrown when a error with the database or SQL happens.
     */
    public static boolean userExists(String name) throws SQLException {
        return (userExists(name, null) > -1);
    }
    
    /**
     * Checks if a user with name &amp; password exists in the database and returns the id.
     * @param name The name for the user to search.
     * @param password The password for the user to search, when null, do not search with password.
     * @return The id of the user if it exists, else -1.
     * @throws SQLException Will be thrown when a error with the database or SQL happens.
     */
    public static int userExists(String name, String password) throws SQLException {
        String sql = "SELECT UserID FROM Users WHERE lower(UserName) = lower(?)";
        if (password != null) sql += " and Password = ?";
        sql += ";";
        
        int id = -1;
        ResultSet rs = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            if (password != null) stmt.setString(2, stringToSHA512String(password));
 
            rs = stmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new SQLException(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            if (rs != null) rs.close();
        }
        return id;
    }
    
    /**
     * Creates a user with name, password &amp; rights exists in the database.
     * @param name The name for the new user.
     * @param password The password for the new user.
     * @param rights The rights for the new user.
     * @return If the creation was a success.
     * @throws SQLException Will be thrown when a error with the database or SQL happens.
     */
    public static boolean createUser(String name, String password, int rights) throws SQLException {
        String sql = "INSERT INTO Users (UserName, Password, UserRights) VALUES (?,?,?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, stringToSHA512String(password));
            stmt.setInt(3, rights);
 
            return (stmt.executeUpdate() > 0); //TODO test if this works
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new SQLException(e.getClass().getName() + ": " + e.getMessage());
        }
    }
    
    /**
     * Gets the user for the given ID.
     * @param id The ID of the user.
     * @return A {@link User} object.
     * @throws SQLException Will be thrown when a error with the database or SQL happens.
     */
    public static User userForID(int id) throws SQLException {
        String sql = "SELECT UserID, UserName, UserRights FROM Users WHERE UserID = ?;";
        
        User u = null;
        ResultSet rs = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
 
            rs = stmt.executeQuery();
            if (rs.next()) {
                u = new User(rs.getInt(1), rs.getString(2), UserRights.fromInteger(rs.getInt(3)));
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new SQLException(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            if (rs != null) rs.close();
        }
        return u;
    }
    
    /**
     * Changes the password for the given user id.
     * @param userID The user id to change the password for.
     * @param oldPassword The old password of the user for check.
     * @param newPassword The new password for the user to set.
     * @return If the password changing was successful.
     * @throws SQLException Will be thrown when a error with the database or SQL happens.
     */
    public static boolean changePassword(int userID, String oldPassword, String newPassword) throws SQLException {
        String sql = "UPDATE Users SET Password = ? WHERE UserID = ? AND Password = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, stringToSHA512String(newPassword));
            stmt.setInt(2, userID);
            stmt.setString(3, stringToSHA512String(oldPassword));
 
            return (stmt.executeUpdate() > 0);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new SQLException(e.getClass().getName() + ": " + e.getMessage());
        }
    }
    
    /**
     * Get the SHA512 hash from the given string with salt.
     * @param str The string to hash.
     * @return The result of the hash, will return "" when the string was empty or a exception occurred.
     */
    private static String stringToSHA512String(String str) {
        if (str == null) return "";
        
        str = "!|01-K+3OK,o" + str + "DJSF*(OUJ#MFE";
        
        try {
            MessageDigest mda = MessageDigest.getInstance("SHA-512", "BC");
            byte [] digesta = mda.digest(str.getBytes());
            return Arrays.toString(digesta);
        } catch (NoSuchAlgorithmException | NoSuchProviderException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    /**
     * Saves the {@link swe.life.Statistics statistics} given.
     * @param simulationID The simulation ID of the statistics.
     * @param statistics The statistics to save.
     * @return A boolean if the saving was successful.
     * @throws SQLException Will be thrown when a error with the database or SQL happens.
     */
    public static boolean saveStatistics(int simulationID, Statistics statistics) throws SQLException {
        WildLife wildLife;
        String sql = "INSERT INTO Statistics VALUES (?,?,?,?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            wildLife = WildLife.Carnivore;
            stmt.setInt(1, simulationID);
            stmt.setInt(2, statistics.getTotalCount(wildLife));
            stmt.setInt(3, statistics.getTotalEnergy(wildLife));
            stmt.setInt(4, WildLife.toInteger(wildLife));
            if (stmt.executeUpdate() == 0) return false; //TODO test if this works
            
            wildLife = WildLife.Herbivore;
            stmt.setInt(1, simulationID);
            stmt.setInt(2, statistics.getTotalCount(wildLife));
            stmt.setInt(3, statistics.getTotalEnergy(wildLife));
            stmt.setInt(4, WildLife.toInteger(wildLife));
            if (stmt.executeUpdate() == 0) return false;
            
            wildLife = WildLife.Omnivore;
            stmt.setInt(1, simulationID);
            stmt.setInt(2, statistics.getTotalCount(wildLife));
            stmt.setInt(3, statistics.getTotalEnergy(wildLife));
            stmt.setInt(4, WildLife.toInteger(wildLife));
            if (stmt.executeUpdate() == 0) return false;
            
            wildLife = WildLife.Vegetation;
            stmt.setInt(1, simulationID);
            stmt.setInt(2, statistics.getTotalCount(wildLife));
            stmt.setInt(3, statistics.getTotalEnergy(wildLife));
            stmt.setInt(4, WildLife.toInteger(wildLife));
            if (stmt.executeUpdate() == 0) return false;
            
            return true;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new SQLException(e.getClass().getName() + ": " + e.getMessage());
        }
    }
    
    /**
     * Get the start {@link swe.life.Statistics statistics} for the given simulation ID.
     * @param simulationID The ID to retrieve the statistics from.
     * @return The statistics for the ID, will return null if none found.
     * @throws SQLException Will be thrown when a error with the database or SQL happens.
     */
    public static Statistics getStatistics(int simulationID) throws SQLException {
        String sql = "SELECT TotalCount, TotalEnergy, WildLife FROM Statistics WHERE SimulationID = ?";
        Statistics statistics = null;
        
        ResultSet rs = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, simulationID);
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                if (statistics == null) statistics = new Statistics();
                WildLife wildLife = WildLife.fromInteger(rs.getObject("WildLife", Integer.class));
                statistics.setTotalCount(wildLife, rs.getObject("TotalCount", Integer.class));
                statistics.setTotalEnergy(wildLife, rs.getObject("TotalEnergy", Integer.class));
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new SQLException(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            if (rs != null) rs.close();
        }
        return statistics;
    }
    
    /**
     * Creates a new simulation and returns its ID.
     * @return The new ID of the simulation, returns -1 on fail.
     * @throws SQLException Will be thrown when a error with the database or SQL happens.
     */
    public static int createSimulation() throws SQLException {
        String sql = "INSERT INTO Simulation VALUES ()";
        
        int id = -1;
        ResultSet rs = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.execute();
            
            sql = "SELECT MAX(ID) FROM Simulation";
            rs = stmt.executeQuery(sql);
            
            if (rs.first()) id = rs.getObject(1, Integer.class);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new SQLException(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            if (rs != null) rs.close();
        }
        return id;
    }
}