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
     * Note that a reference Integer is used and not a basic int.
     */
    Map<WildLife, Integer> totalCountWildLife;
    Map<WildLife, Integer> totalEnergyWildLife;

    public Statistics(int totalCountOmnivores, int totalCountCarnivores, int totalCountHerbivores, int totalCountVegetation, int totalEnergyOmnivores, int totalEnergyCarnivores, int totalEnergyHerbivores, int totalEnergyVegetation) {
        totalCountWildLife = new HashMap<>();
        totalEnergyWildLife = new HashMap<>();
        
        totalCountWildLife.put(WildLife.Omnivore, totalCountOmnivores);
        totalCountWildLife.put(WildLife.Carnivore, totalCountCarnivores);
        totalCountWildLife.put(WildLife.Herbivore, totalCountHerbivores);
        totalCountWildLife.put(WildLife.Vegetation, totalCountVegetation);
        
        totalEnergyWildLife.put(WildLife.Omnivore, totalEnergyOmnivores);
        totalEnergyWildLife.put(WildLife.Carnivore, totalEnergyCarnivores);
        totalEnergyWildLife.put(WildLife.Herbivore, totalEnergyHerbivores);
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