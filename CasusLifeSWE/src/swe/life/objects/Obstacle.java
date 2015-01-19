/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.life.objects;

import javafx.scene.paint.Color;
import swe.life.World;

/**
 * This class is a obstacle in the world that extends the Static class.
 * The obstacle can't be walked on by {@link Animal} and {@link Vegetation} can't grow on it.
 * @author Roy
 */
public class Obstacle extends Landscape {
    public Obstacle(int x, int y) {
        this(x, y, Color.BLACK, World.instance);
    }
    
    private Obstacle(int x, int y, Color color, World world) {
        super(x, y, color, world);
    }
}
