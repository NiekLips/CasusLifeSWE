/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.life.objects;

import javafx.scene.paint.Color;
import swe.life.objects.interfaces.ISim;

/**
 * A living {@link swe.life.objects.Object Object} in a {@link swe.life.World world}.
 * @author Roy
 */
public abstract class Dynamic extends Object implements ISim {
    public Dynamic(Integer x, Integer y, Color color) {
        super(x, y, color);
    }
}
