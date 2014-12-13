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
import java.util.TimerTask;

/**
 * The world that contains a list of {@link Object objects} and the {@link History history}.
 * @author Roy
 */
public class World {
    private boolean isPaused = false;
    private int period;
    private boolean isSimulationRunning = false;
    private List<Object> objects = new ArrayList<>();
    private Timer timer = new Timer();
    
    /**
     * Used to load a scene in the world.
     * @param scene A serialized {@link Object} list.
     * @return If the loading was a success.
     */
    public boolean loadScene(String scene) {
        if (isSimulationRunning) timer.cancel();
        reset();
        
        //TODO implement
        return true;
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
        
        if (!isPaused) reset();
        
        //TODO implement, reset all if not paused
        
        return startTimer((int) (speed * 1000));
    }
    
    /**
     * Pauses the simulation.
     * @return If the simulation can be paused, like when its running.
     */
    public boolean pauseSimulation() {
        isPaused = stopSimulation();
        return isPaused;
    }
    
    /**
     * Continues the simulation from pause.
     * @return If the simulation can be continued, like when its running.
     */
    public boolean continueSimulation() {
        isPaused = !startSimulation(period);
        return !isPaused;
    }
    
    /**
     * Stops the simulation.
     * @return If the simulation can be stopped, like when its not running.
     */
    public boolean stopSimulation() {
        if (!isSimulationRunning) return false;
        isSimulationRunning = false;
        if (timer != null) timer.cancel();
        return true;
    }
    
    /**
     * Runs the simulations step once.
     * @return If the simulation can run once, like when its running.
     */
    public boolean runOnceSimulation() {
        if (isSimulationRunning) return false;
        startTimer(period);
        return true;
    }
    
    /**
     * Checks if the simulations is running.
     * @return True when the simulation is running.
     */
    public boolean isSimulationRunning() {
        return isSimulationRunning;
    }
    
    /**
     * Starts the timer with the simulation methods.
     * @param period The time in milliseconds between the task calls.
     * @return If the timer can be started, like when its not running already.
     */
    private boolean startTimer(int period) {
        this.period = period;
        if (isSimulationRunning) return false;
        if (timer == null) timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                throw new UnsupportedOperationException("TODO Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }, 0, period);
        return true;
    }
    
    /**
     * Returns the current speed of the simulation.
     * @return The speed as a double.
     */
    private double getSpeed() {
        return period / 1000;
    }
    
    /**
     * Resets the simulation so a new one can start.
     * @return If the resetting was successful.
     */
    private boolean reset() {
        if (isSimulationRunning) stopSimulation();
        //TODO implement & doc
        return true;
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
}