/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.user;

/**
 * Contains information about the logged in user like the name &amp; rights.
 * @author Roy
 */
public class User {
    private final int id;
    private final String name;
    private final UserRights rights;
    
    public User(int id, String name, UserRights rights) {
        this.id = id;
        this.name = name;
        this.rights = rights;
    }
    
    /**
     * Returns the user's ID.
     * @return The ID of the user.
     */
    public int getID() {
        return id;
    }
    
    /**
     * Returns the user's name.
     * @return The name of the user.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the rights of the user.
     * @return The rights as {@link UserRights}.
     */
    public UserRights getRights() {
        return rights;
    }
}
