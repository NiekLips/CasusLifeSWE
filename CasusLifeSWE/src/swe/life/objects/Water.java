/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.life.objects;

import javafx.scene.paint.Color;

/**
 * This class is regular water in the world that extends the Static class.
 * The water can be swim on by {@link Animal} and {@link Vegetation} can't grow on it.
 * @author Roy
 */
public class Water extends Static {
    public Water(Integer x, Integer y, Color color) {
        super(x, y, color);
    }
}
