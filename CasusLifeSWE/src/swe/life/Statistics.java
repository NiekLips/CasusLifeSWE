/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.life;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains information about the number of {@link WildLife} and energy.
 * @author Roy
 */
public class Statistics {
    /**
     * Note that a reference Integer is used and not a int.
     */
    Map<WildLife, Integer> totalCountWildLife;
    Map<WildLife, Integer> totalEnergyWildLife;

    public Statistics(int totalCountOmnivore, int totalCountCarnivore, int totalCountHerbivore, int totalCountVegetation, int totalEnergyOmnivore, int totalEnergyCarnivore, int totalEnergyHerbivore, int totalEnergyVegetation) {
        totalCountWildLife = new HashMap<>();
        totalEnergyWildLife = new HashMap<>();
        
        totalCountWildLife.put(WildLife.Omnivore, totalCountOmnivore);
        totalCountWildLife.put(WildLife.Carnivore, totalCountCarnivore);
        totalCountWildLife.put(WildLife.Herbivore, totalCountHerbivore);
        totalCountWildLife.put(WildLife.Vegetation, totalCountVegetation);
        
        totalEnergyWildLife.put(WildLife.Omnivore, totalEnergyOmnivore);
        totalEnergyWildLife.put(WildLife.Carnivore, totalEnergyCarnivore);
        totalEnergyWildLife.put(WildLife.Herbivore, totalEnergyHerbivore);
        totalEnergyWildLife.put(WildLife.Vegetation, totalEnergyVegetation);
    }
    
    /**
     * Gets the total count of the given {@link WildLife}.
     * @param wildLife The requested type.
     * @return A int with the total.
     */
    public int getTotalCount(WildLife wildLife) {
        return totalCountWildLife.get(wildLife);
    }
    
    /**
     * Gets the total energy of the given {@link WildLife}.
     * @param wildLife The requested type.
     * @return A int with the total.
     */
    public int getTotalEnergy(WildLife wildLife) {
        return totalEnergyWildLife.get(wildLife);
    }
    
    /**
     * Gets the average energy of the given {@link WildLife}.
     * @param wildLife The requested type.
     * @return A double with the average.
     */
    public double getAverageEnergy(WildLife wildLife) {
        return totalEnergyWildLife.get(wildLife) / totalCountWildLife.get(wildLife);
    }
}