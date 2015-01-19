/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.life;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import swe.life.objects.Living;
import swe.ui.WorldViewController;

/**
 * Handles the simulation in the {@link World}.
 * @author Roy
 */
public class Simulator {
    private final World world;
    private boolean isPaused = false;
    private int period;
    private boolean isSimulationRunning = false;
    private Timer timer = new Timer();
    
    /**
     * Set world to keep a reference to it for calls like reset.
     * @param world The {@link World} where the simulator runs in.
     */
    public Simulator(World world) {
        this.world = world;
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
        
        if (!isPaused) world.reset();
        
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
    
    private Object object;
    
    /**
     * Starts the timer with the simulation methods.
     * @param period The time in milliseconds between the task calls.
     * @return If the timer can be started, like when its not running already.
     */
    private boolean startTimer(int period) {
        object = new Object();
        
        this.period = period;
        if (isSimulationRunning) return false;
        if (timer == null) timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (object) {
                    Iterator<swe.life.objects.Object> it = world.getObjects().iterator();
                   // Object object;
                    while (it.hasNext()) { //TODO manage a seperate list with Living objects
                        Object object = it.next();
                        if (object instanceof Living) {
                            ((Living)object).simulate();
                        }
                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (WorldViewController.instance != null) WorldViewController.instance.draw();
                        }
                    });
                }
            }
        }, 0, period);
        return true;
    }
    
    /**
     * Returns the current speed of the simulation.
     * @return The speed as a double.
     */
    public double getSpeed() {
        return period / 1000;
    }
}
