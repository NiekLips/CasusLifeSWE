/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.life.objects.interfaces;

import swe.life.objects.enumerations.Direction;

/**
 * A interface that is implemented in {@link swe.life.objects.Animal Animal} classes
 * @author Roy
 */
public interface IAnimal {
    double weight = 0;
    double speed = 0;
    double hunger = 0;
    Direction direction = Direction.None;
}
 