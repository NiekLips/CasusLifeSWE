/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.life;

import swe.life.objects.Object;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * The world that contains a list of {@link Object objects} and the {@link History history}.
 * @author Roy
 */
public class World {
    private List<Object> objects = new ArrayList<>();
    private Timer timer = new Timer();
    
    /**
     * Used to load a scene in the world.
     * @param scene serialized {@link Object} list
     * @return if the loading was a success
     */
    public boolean loadScene(String scene) {
        //TODO implement
    }
    
    /**
     * Change the speed of the simulation.
     * @param speed The speed (1 = 1 step per second), must be higher than 0.
     * @throws Exception Thrown when the speed is lower or equal to 0.
     */
    public void setSpeed(double speed) throws Exception {
        if (speed <= 0) throw new Exception("Speed must be higher than 0"); //TODO localize strings and custom exception
        //TODO implement
    }
    
    /**
     * Starts the simulation.
     * @param speed The speed of the simulation (1 = 1 step per second), must be higher than 0.
     * @return If the simulation can be started, like when its not running already.
     */
    public boolean startSimulation(double speed) {
        if (speed == 0) speed = 0.5;
        else if (speed < 0) speed = -1;
        
        //TODO implement
    }
    
    /**
     * Pauses the simulation.
     * @return If the simulation can be paused, like when its running.
     */
    public boolean pauseSimulation() {
        
    }
    
    /**
     * Stops the simulation.
     * @return If the simulation can be stopped, like when its not running.
     */
    public boolean stopSimulation() {
        
    }
    
    /**
     * Runs the simulations step once.
     * @return If the simulation can run once, like when its running.
     */
    public boolean runOnceSimulation() {
        
    }
    
    /**
     * TODO doc
     * @return 
     */
    public boolean isSimulationRunning() {
        
    }
}