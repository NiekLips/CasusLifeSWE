/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.life.objects;

import javafx.scene.paint.Color;
import swe.life.objects.interfaces.IAnimal;

/**
 * This class is moving living {@link Object} in the world that extends the Dynamic class.
 * It can move over {@link Land} as well {@link Water} but not {@link Obstacle obstacles}.
 * @author Roy
 */
public class Animal extends Dynamic implements IAnimal {
    public Animal(Integer x, Integer y, Color color) {
        super(x, y, color);
    }
}
