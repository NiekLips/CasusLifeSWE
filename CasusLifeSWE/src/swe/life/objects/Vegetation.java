/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.life.objects;

import javafx.scene.paint.Color;
import swe.life.World;

/**
 * This class is non-moving living {@link Object} in the world that extends the Dynamic class.
 * It can't move but can be eaten and live.
 * @author Roy
 */
public class Vegetation extends Living {
    public static final int TIMES_EATEN_BEFORE_DEAD = 10;
    public static final int NUMBER_OF_DAYS_DEAD = 100;
    
    private int energy;
    private int timesDead;
    private int timesEaten;
    
    public Vegetation(int x, int y, World world, int energy, int timesEaten) {
        this(x, y, Color.GREEN, world);
        this.energy = energy;
        this.timesDead = 0;
        this.timesEaten = timesEaten;
    }
    
    private Vegetation(int x, int y, Color color, World world) {
        super(x, y, color, world);
    }

    /** Returns the energy eaten.
     * Plants can be eaten {@value #TIMES_EATEN_BEFORE_DEAD} before being dead at 0 energy.
     * @return Energy / (TIMES_EATEN_BEFORE_DEAD - 10)
     */
    @Override
    public int eaten() {
        timesEaten++;
        int energyEaten = energy / (TIMES_EATEN_BEFORE_DEAD - 10);
        energy -= energyEaten;
        return energyEaten;
    }

    /** Performs the simulation step.
     * Will wait when dead &amp; grow when alive.
     */
    @Override
    public void simulate() { //TODO laddermodel
        if (energy == 0) { //Dead
            if (timesDead++ >= NUMBER_OF_DAYS_DEAD) {
                timesDead = 0;
                timesEaten = 0;
            }
        }
        else { //Alive
            energy++;
        }
    }
}