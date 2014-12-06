/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.life.objects.interfaces;

/**
 * A interface that is implemented in {@link swe.life.objects.Dynamic 'Living'} classes
 * @author Roy
 */
public interface ISim {
    public void eaten();
    public void simulate();
}
