/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.life;

import swe.life.objects.Object;
import java.util.ArrayList;
import java.util.List;
import swe.life.objects.Animal;
import swe.life.objects.Living;
import swe.life.objects.Vegetation;

/**
 * The world that contains a list of {@link Object objects}, the {@link Simulator simulator} and the {@link History history}.
 * @author Roy
 */
public class World {
    public final static int LINE_OF_SIGHT = 10;
    
    private int id;
    private List<Object> mObjects;
    private final Simulator mSimulator;
    
    /**
     * Creates a world from a ID and the serialized objects from the database.
     * @param id 
     * @param objects 
     */
    public World(int id, List<Object> objects) {
        this();
        
        this.mObjects = objects;
        this.id = id;
    }
    
    /**
     * Creates a new world with the given parameters.
     * @param todoo 
     */
    public World(String todoo) {//TODO parameters for a new world
        this();
        
        this.mObjects = new ArrayList<>();
        //this.id = id; TODO new ID from DB
    }
    
    /**
     * Contains the generic initializers for the world class.
     */
    private World() {
        mSimulator = new Simulator(this);
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
        if (mSimulator != null && mSimulator.isSimulationRunning()) mSimulator.stopSimulation();
        //TODO implement & doc
        return true;
    }
    
    /**
     * Returns the objects in the world.
     * @return A list of all the objects.
     */
    public List<Object> getObjects() {
        return mObjects;
    }
    
    /**
     * Gets the {@link Object object(s)} on the given x &amp; y.
     * @param x The X location on the grid.
     * @param y The Y location on the grid.
     * @return A list with the object(s), will return a empty list when nothing is found.
     */
    public List<Object> getObjectsForXY(int x, int y) {
        List<Object> objects = new ArrayList<>();
        for (Object object : mObjects) {
            if (Math.round(object.getX()) == x && Math.round(object.getY()) == y) objects.add(object);
        }
        return objects;
    }
    
    /**
     * Gets the nearest {@link Object object} with the requested kind.
     * Checks also within a distance
     * TODO check optimizations
     * @param objectKind The type of object looking for.
     * @param x The X location of the current position.
     * @param y The Y location of the current position.
     * @param crossWater If the object may go over water.
     * @return Multiple objects when they are equally far away.
     */
    public Object getNearestObjectKindFrom(Class objectKind, double x, double y, boolean crossWater) {
        double closest = -1;
        Object closestObject = null;
        for (Object object : mObjects) {
            if (object.getClass() == objectKind) {
                double cx = Math.pow(x - object.getX(), 2);
                double cy = Math.pow(y - object.getY(), 2);
                if (cx + cy < Math.pow(LINE_OF_SIGHT, 2)) {
                    double distance =  Math.sqrt(cx + cy);
                    if (distance <= closest || closest == -1) {
                        //TODO check for water
                        if (distance < closest) closestObject = object;
                    }
                }
            }
        }
        return closestObject;
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
     * @throws java.lang.Exception Will be thrown when a  {@link Living} object is in the mObjects that is not a {@link Animal} or  {@link Vegetation}
     */
    public Statistics getCurrentStatistics() throws Exception {
        int totalCountOmnivores = 0, totalCountCarnivores = 0, totalCountHerbivores = 0, totalCountVegetation = 0, totalEnergyOmnivores = 0, totalEnergyCarnivores = 0, totalEnergyHerbivores = 0, totalEnergyVegetation = 0;
        for (Object object : mObjects) {
            if (object instanceof Living) { //TODO Living instead of Object
                if (object instanceof Animal) {
                    Animal animal = (Animal)object;
                    switch (animal.getDigestion()) {
                        case Carnivorous: totalCountCarnivores++; totalEnergyCarnivores += animal.getEnergy(); break;
                        case Herbivorous: totalCountHerbivores++; totalEnergyHerbivores += animal.getEnergy(); break;
                        default: totalCountOmnivores++; totalEnergyOmnivores += animal.getEnergy(); break;
                    }
                }
                else if (object instanceof Vegetation) {
                    Vegetation vegetation = (Vegetation)object;
                    totalCountVegetation++; totalEnergyVegetation += vegetation.getEnergy();
                }
                else {
                    throw new Exception("Unknown instance in getCurrentStatistics");
                }
            }
        }
        return new Statistics(totalCountOmnivores, totalCountCarnivores, totalCountHerbivores, totalCountVegetation, totalEnergyOmnivores, totalEnergyCarnivores, totalEnergyHerbivores, totalEnergyVegetation);
    }
    
    /**
     * Adds a {@link swe.life.objects.Object object} to the objects list.
     * @param object The object to add to the list.
     * @return The result of the add.
     */
    public boolean addObject(Object object) {
        return mObjects.add(object);
    }
    
    /**
     * Removes a {@link swe.life.objects.Object object} to the objects list.
     * @param object The object to remove from the list.
     * @return The result of the remove.
     */
    public boolean removeObject(Object object) {
        return mObjects.remove(object);
    }
    
    /**
     * Returns the {@link Simulator} for the world.
     * @return The simulator that can be controlled.
     */
    public Simulator getSimulator() {
        return mSimulator;
    }
}