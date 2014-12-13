/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.life.objects;

import javafx.scene.paint.Color;
import swe.life.World;

/**
 * A non-movable non-living {@link Object} in the {@link swe.life.World world}.
 * @author Roy
 */
public abstract class Landscape extends Object {
    public Landscape(Integer x, Integer y, Color color, World world) {
        super(x, y, color, world);
    }
}
