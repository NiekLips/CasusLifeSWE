/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.life.objects;

import java.io.Serializable;
import javafx.scene.paint.Color;
import swe.life.World;

/**
 * The basic object used for objects in the {@link swe.life.World world}.
 * It contains the coordinates of the object as well as the color.
 * @author Roy
 */
public abstract class Object implements Serializable {
    double x;
    double y;
    private Color color;
    protected World world;
    
    /**
     * The base constructor for a Object.
     * @param x The X (horizontal) position of the object.
     * @param y The Y (vertical) position of the object.
     * @param color The color of the object.
     * @param world The world where the object in exists.
     */
    public Object(double x, double y, Color color, World world) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.world = world;
        
        world.addObject(this);
    }
    
    /**
     * Returns the rounded x position of the object.
     * @return A integer cast of the x double.
     */
    public double getX() {
        return x;
    }
    
    /**
     * Returns the rounded y position of the object.
     * @return A integer cast of the y double.
     */
    public double getY() {
        return y;
    }
    
    /**
     * Returns the color of the object.
     * @return See the documentation for the color-table.
     */
    public Color getColor() {
        return color;
    }
}