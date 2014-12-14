/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.life;

import swe.life.objects.Object;
import java.util.ArrayList;
import java.util.List;

/**
 * The world that contains a list of {@link Object objects} and the {@link History history}.
 * @author Roy
 */
public class World {
    private final List<Object> objects = new ArrayList<>();
    private final Simulator simulator;
    
    public World() {//TODO parameters
        simulator = new Simulator(this);
    }
    
    /**
     * Used to load a scene in the world.
     * @param scene A serialized {@link Object} list.
     * @return If the loading was a success.
     */
    public boolean loadScene(String scene) {
        reset();
        
        //TODO implement
        return true;
    }
    
    /**
     * Resets the simulation so a new one can start.
     * @return If the resetting was successful.
     */
    boolean reset() { //TODO check if reset can be called from outside swe.life
        if (simulator != null && simulator.isSimulationRunning()) simulator.stopSimulation();
        //TODO implement & doc
        return true;
    }
    
    /**
     * Gets the {@link Object object(s)} on the given x & y.
     * @param x The X location on the grid.
     * @param y The Y location on the grid.
     * @return A list with the object(s), will return a empty list when nothing is found.
     */
    public List<Object> getObjectsForXY(int x, int y) {
        return null; //TODO
    }
    
    /**
     * Gets the nearest {@link Object object} with the requested kind.
     * TODO check within a distance
     * @param object The type of object looking for.
     * @param x The X location of the current position.
     * @param y The Y location of the current position.
     * @param crossWater If the object may go over water.
     * @return Multiple objects when they are equally far away.
     */
    public List<Object> getNearestObjectsKindFrom(Object object, int x, int y, boolean crossWater) {
        return null; //TODO
    }
    
    /**
     * Returns the {@link Statistics statistics} where the world was generated with.
     * @return A statistics object from the database.
     */
    public Statistics getStatistics() {
        return null; //TODO
    }
    
    /**
     * Returns the current {@link Statistics statistics}.
     * @return A new statistics object with the current data.
     */
    public Statistics getCurrentStatistics() {
        return null; //TODO
    }
    
    /**
     * Adds a {@link swe.life.objects.Object object} to the objects list.
     * @param object The object to add to the list.
     * @return The result of the add.
     */
    public boolean addObject(Object object) {
        return objects.add(object);
    }
    
    /**
     * Removes a {@link swe.life.objects.Object object} to the objects list.
     * @param object The object to remove from the list.
     * @return The result of the remove.
     */
    public boolean removeObject(Object object) {
        return objects.remove(object);
    }
    
    /**
     * Returns the {@link Simulator} for the world.
     * @return The simulator that can be controlled.
     */
    public Simulator getSimulator() {
        return simulator;
    }
}