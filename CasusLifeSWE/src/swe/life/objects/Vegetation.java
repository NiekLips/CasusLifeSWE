/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.life.objects;

import javafx.scene.paint.Color;

/**
 * This class is non-moving living {@link Object} in the world that extends the Dynamic class.
 * It can move over {@link Land} as well {@link Water} but not {@link Obstacle obstacles}.
 * @author Roy
 */
public class Vegetation extends Dynamic {
    public Vegetation(Integer x, Integer y, Color color) {
        super(x, y, color);
    }
}