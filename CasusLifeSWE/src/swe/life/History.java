/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.life;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the history of previous simulations made by the {@link World world}.
 * A simulation a.k.a. scene is a serialization of the {@link World worlds} {@link Object objects}.
 * @author Roy
 */
public class History {
    private final List<String> objectScenes = new ArrayList<>();
    
    /**
     * Saves a serialized list of {@link Object objects} to the database.
     * @param scene the list of serialized objects to save.
     * @return if the saving was a success.
     */
    public boolean SaveScene(String scene) {
        if (!objectScenes.add(scene)) return false;
            
        //TODO save to DB
        return true;
    }
}
