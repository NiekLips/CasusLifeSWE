/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.user;

/**
 * The class for managing the {@link User users} in the database with different access.
 * @author Roy
 */
public class Users {
    private User cUser;
    
    /**
     * Used to log in a user with the credentials.
     * @param name The name for the user to log in.
     * @param password The password for the user to log in.
     * @return If the login was successful.
     */
    public boolean loginUser(String name, String password) {
        //TODO implement
        return true;
    }
    
    /**
     * Returns the logged in {@link User user}.
     * @return null if there is no logged-in user.
     */
    public User getCurrentUser() {
        return cUser;
    }
}
