/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.user;

import java.util.logging.Level;
import java.util.logging.Logger;
import swe.database.Database;

/**
 * The class for managing the {@link User users} in the database with different access.
 * @author Roy
 */
public class Users {
    private static User cUser;
    
    /**
     * Used to log in a user with the credentials, a previous logged in user will be set to null.
     * @param name The name for the user to log in.
     * @param password The password for the user to log in.
     * @return If the login was successful.
     * @throws Exception Thrown when a SQL Exception happened.
     */
    public static boolean loginUser(String name, String password) throws Exception {
        cUser = null;
        try {
            int id = Database.userExists(name, password);
            if (id > -1) {
                cUser = Database.userForID(id);
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex.getMessage());
        }
        return false;
    }
    
    /**
     * Set the current {@link User user} to null.
     */
    public static void loginUser() {
        cUser = null;
    }
    
    /**
     * Creates a new user with the given parameters.
     * @param name The name of the new user. (Length must be &gt; 2)
     * @param password The password of the new user. (Length must be &gt; 6)
     * @param rights The {@link UserRights rights} of the new user.
     * @return If the creation was a success.
     * @throws Exception If the cUser has no admin rights, the name already exists, the name or password do not match the minimum lengths or a SQL Exception happened.
     */
    public static boolean createUser(String name, String password, UserRights rights) throws Exception {
        if (cUser == null || cUser.getRights() != UserRights.Admin) throw new Exception("The current user has no permissions to create a new user!");
        if (name.length() < 2) throw new Exception("Username should be at least 2 characters!");
        if (password.length() < 6) throw new Exception("Password should be at least 6 characters!");
        
        if (Database.userExists(name)) throw new Exception("Username already exists in the database.");
        
        try {
            if (Database.createUser(name, password, UserRights.toInteger(rights))) {
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex.getMessage());
        }
        return false;
    }
    
    /**
     * Changes the password for the current user.
     * @param oldPassword The current password for the user.
     * @param newPassword The new password for the user. (Length must be &gt; 6)
     * @return If the changing went successful.
     * @throws Exception If the cUser is null, the new password does not match the minimum length or a SQL Exception happened..
     */
    public static boolean changePassword(String oldPassword, String newPassword) throws Exception {
        if (cUser == null) throw new Exception("Log in first before changing a password!");
        if (newPassword.length() < 6) throw new Exception("New password should be at least 6 characters!");
        
        try {
            if (Database.changePassword(cUser.getID(), oldPassword, newPassword)) {
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex.getMessage());
        }
        return false;
    }
    
    /**
     * Returns the logged in {@link User user}.
     * @return Null if there is no logged-in user.
     */
    public static User getCurrentUser() {
        return cUser;
    }
}
