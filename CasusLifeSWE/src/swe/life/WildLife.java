/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.life;

/**
 * The different types where the information can be requested from.
 * @author Roy
 */
public enum WildLife {
    Omnivore,
    Carnivore,
    Herbivore,
    Vegetation;
    
    /**
     * Returns a right for the given integer.
     * @param x The integer for the right.
     * @return A {@link WildLife wildlife}.
     */
    public static WildLife fromInteger(int x) {
        switch(x) {
        case 0:
            return Omnivore;
        case 1:
            return Carnivore;
        case 2:
            return Herbivore;
        case 3:
            return Vegetation;
        }
        return null;
    }
    
    /**
     * Returns a integer value for the right.
     * @param x A {@link WildLife wildlife}.
     * @return A integer value.
     */
    public static int toInteger(WildLife x) {
        switch(x) {
        case Omnivore:
            return 0;
        case Carnivore:
            return 1;
        case Herbivore:
            return 2;
        case Vegetation:
            return 3;
        }
        return -1;
    }
}
