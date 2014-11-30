/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.life.objects;

import javafx.scene.paint.Color;

/**
 * The basic object used for objects in the {@link swe.life.World world}. 
 * It contains the coordinates of the object as well as the color.
 * @author Roy
 */
public abstract class Object {
    private final Integer x;
    private final Integer y;
    private final Color color;
    
    /**
     * The base constructor for a Object.
     * @param x the X (horizontal) position of the object
     * @param y the Y (vertical) position of the object
     * @param color the color of the object
     */
    public Object(Integer x, Integer y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
}