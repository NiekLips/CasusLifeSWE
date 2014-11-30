/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.life.objects;

import javafx.scene.paint.Color;

/**
 * A non-movable non-living {@link Object} in the {@link swe.life.World world}.
 * @author Roy
 */
public abstract class Static extends Object {
    public Static(Integer x, Integer y, Color color) {
        super(x, y, color);
    }
}
