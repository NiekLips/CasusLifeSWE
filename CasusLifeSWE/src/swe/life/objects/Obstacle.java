/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.life.objects;

import javafx.scene.paint.Color;

/**
 * This class is a obstacle in the world that extends the Static class.
 * The obstacle can't be walked on by {@link Animal} and {@link Vegetation} can't grow on it.
 * @author Roy
 */
public class Obstacle extends Static {
    public Obstacle(Integer x, Integer y, Color color) {
        super(x, y, color);
    }
}
