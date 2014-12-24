/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.user;

/**
 * The list of different access &amp; control levels for the {@link User users}.
 * @author Roy
 */
public enum UserRights {
    Viewer,
    Controller,
    Admin;
    
    /**
     * Returns a right for the given integer.
     * @param x The integer for the right.
     * @return A {@link UserRights userright}.
     */
    public static UserRights fromInteger(int x) {
        switch(x) {
        case 0:
            return Viewer;
        case 1:
            return Controller;
        case 2:
            return Admin;
        }
        return null;
    }
    
    /**
     * Returns a integer value for the right.
     * @param x A {@link UserRights userright}.
     * @return A integer value.
     */
    public static int toInteger(UserRights x) {
        switch(x) {
        case Viewer:
            return 0;
        case Controller:
            return 1;
        case Admin:
            return 2;
        }
        return -1;
    }
}
