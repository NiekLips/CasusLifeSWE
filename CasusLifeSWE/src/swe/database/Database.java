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
    
    /**
     * Checks if a user with name &amp; password exists in the database.
     * @param name The name for the user to search.
     * @return If the user exists with the name.
     * @throws SQLException Will be thrown when a error with the database or SQL happens.
     */
    public static boolean UserExists(String name) throws SQLException {
        return UserExists(name, null);
    }
    
    /**
     * Checks if a user with name &amp; password exists in the database.
     * @param name The name for the user to search.
     * @param password The password for the user to search, when null, do not search with password.
     * @return If the user exists with the name and optional password.
     * @throws SQLException Will be thrown when a error with the database or SQL happens.
     */
    public static boolean UserExists(String name, String password) throws SQLException {
        String sql = "SELECT COUNT(UserID) FROM Users WHERE lower(UserName) = lower(?)";
        if (password != null) sql += " and Password = ?";
        sql += ";";
        
        boolean exists = false;
        ResultSet rs = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(0, name);
            if (password != null) stmt.setString(1, stringToSHA512String(password));
 
            rs = stmt.executeQuery();
            while (rs.next()) {
                exists = (rs.getInt(1) > 0);
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new SQLException(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            if (rs != null) rs.close();
        }
        return exists;
    }
    
    /**
     * Creates a user with name, password &amp; rights exists in the database.
     * @param name The name for the new user.
     * @param password The password for the new user.
     * @param rights The rights for the new user.
     * @return If the creation was a success.
     * @throws SQLException Will be thrown when a error with the database or SQL happens.
     */
    public static boolean CreateUser(String name, String password, int rights) throws SQLException {
        String sql = "INSERT INTO Users (UserName, Password, UserRights) VALUES (?,?,?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(0, name);
            stmt.setString(1, stringToSHA512String(password));
            stmt.setInt(2, rights);
 
            return (stmt.executeUpdate() > 0); //TODO test if this works
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new SQLException(e.getClass().getName() + ": " + e.getMessage());
        }
    }
    
    /**
     * Changes the password for the given user id.
     * @param userID The user id to change the password for.
     * @param oldPassword The old password of the user for check.
     * @param newPassword The new password for the user to set.
     * @return If the password changing was successful.
     * @throws SQLException Will be thrown when a error with the database or SQL happens.
     */
    public static boolean ChangePassword(int userID, String oldPassword, String newPassword) throws SQLException {
        String sql = "UPDATE Users SET Password = ? WHERE UserID = ? AND Password = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(0, stringToSHA512String(newPassword));
            stmt.setInt(1, userID);
            stmt.setString(2, stringToSHA512String(oldPassword));
 
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
}