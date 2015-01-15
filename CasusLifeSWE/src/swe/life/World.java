/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.life;

import java.sql.SQLException;
import swe.life.objects.Object;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import swe.database.Database;
import swe.life.objects.Animal;
import swe.life.objects.Living;
import swe.life.objects.Vegetation;

/**
 * The world that contains a list of {@link Object objects}, the {@link Simulator simulator} and the {@link History history}.
 * @author Roy
 */
public final class World {
    public static World instance;
    
    public final static int LINE_OF_SIGHT = 15;
    
    private int id;
    private int width;
    private int height;
    private List<Object> mObjects;
    private final Simulator mSimulator;
    
    /**
     * Creates a world from a ID and the serialized objects from the database.
     * @param id 
     * @param objects 
     */
    public World(int id, List<Object> objects) {
        this(0,0);//TODO load world from db
        
        this.mObjects = objects;
        this.id = id;
    }
    
    /**
     * Creates a new world with the given parameters.
     * @param width The width of the world.
     * @param height The height of the world.
     * @param todoo 
     */
    public World(int width, int height, String todoo) {//TODO parameters for a new world
        this(width, height);
        
        try {
            this.id = Database.createSimulation();
        } catch (SQLException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            if (!Database.saveStatistics(id, getCurrentStatistics())) System.out.println("Failed to save statistics");
        } catch (SQLException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Contains the generic initializers for the world class.
     * @param width The width of the world.
     * @param height The height of the world.
     */
    private World(int width, int height) {
        this.width = width;
        this.height = height;
        
        this.mObjects = new ArrayList<>();
        mSimulator = new Simulator(this);
        
        instance = this;
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
                        if (distance < closest || closest == -1) closestObject = object;
                    }
                }
            }
        }
        return closestObject;
    }
    
    /**
     * Returns the {@link Statistics statistics} where the world was generated with.
     * @return A statistics object from the database.
     * @throws java.lang.Exception
     */
    public Statistics getStatistics() throws Exception {
        return Database.getStatistics(this.id);
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
     * Returns the width of the world.
     * @return The width as a int.
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Returns the height of the world.
     * @return The height as a int.
     */
    public int getHeight() {
        return height;
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