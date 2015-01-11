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
        this();
        
        totalCountWildLife.put(WildLife.Omnivore, totalCountOmnivores);
        totalCountWildLife.put(WildLife.Carnivore, totalCountCarnivores);
        totalCountWildLife.put(WildLife.Herbivore, totalCountHerbivores);
        totalCountWildLife.put(WildLife.Vegetation, totalCountVegetation);
        
        totalEnergyWildLife.put(WildLife.Omnivore, totalEnergyOmnivores);
        totalEnergyWildLife.put(WildLife.Carnivore, totalEnergyCarnivores);
        totalEnergyWildLife.put(WildLife.Herbivore, totalEnergyHerbivores);
        totalEnergyWildLife.put(WildLife.Vegetation, totalEnergyVegetation);
    }

    public Statistics() {
        totalCountWildLife = new HashMap<>();
        totalEnergyWildLife = new HashMap<>();
    }
    
    /**
     * Get the total count of the given {@link WildLife}.
     * @param wildLife The requested type.
     * @return A int with the total.
     */
    public int getTotalCount(WildLife wildLife) {
        if (totalCountWildLife.containsKey(wildLife))
            return totalCountWildLife.get(wildLife);
        else
            return 0;
    }
    
    /**
     * Get the total energy of the given {@link WildLife}.
     * @param wildLife The requested type.
     * @return A int with the total.
     */
    public int getTotalEnergy(WildLife wildLife) {
        if (totalEnergyWildLife.containsKey(wildLife))
            return totalEnergyWildLife.get(wildLife);
        else
            return 0;
    }
    
    /**
     * Set the total count of the given {@link WildLife} if not put yet.
     * @param wildLife The type to set.
     * @param count The amount int to set.
     */
    public void setTotalCount(WildLife wildLife, int count) {
        totalCountWildLife.putIfAbsent(wildLife, count);
    }
    
    /**
     * Set the total energy of the given {@link WildLife} if not put yet.
     * @param wildLife The type to set.
     * @param energy The energy int to set.
     */
    public void setTotalEnergy(WildLife wildLife, int energy) {
        totalEnergyWildLife.putIfAbsent(wildLife, energy);
    }
    
    /**
     * Gets the average energy of the given {@link WildLife}.
     * @param wildLife The requested type.
     * @return A double with the average.
     */
    public double getAverageEnergy(WildLife wildLife) {
        return totalEnergyWildLife.get(wildLife) / totalCountWildLife.get(wildLife);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null && obj instanceof Statistics) {
            return false;
        }
        Statistics statistics = (Statistics)obj;
        if (statistics.getTotalCount(WildLife.Omnivore) != this.getTotalCount(WildLife.Omnivore)) return false;
        if (statistics.getTotalCount(WildLife.Carnivore) != this.getTotalCount(WildLife.Carnivore)) return false;
        if (statistics.getTotalCount(WildLife.Herbivore) != this.getTotalCount(WildLife.Herbivore)) return false;
        if (statistics.getTotalCount(WildLife.Vegetation) != this.getTotalCount(WildLife.Vegetation)) return false;
        
        if (statistics.getTotalEnergy(WildLife.Omnivore) != this.getTotalEnergy(WildLife.Omnivore)) return false;
        if (statistics.getTotalEnergy(WildLife.Carnivore) != this.getTotalEnergy(WildLife.Carnivore)) return false;
        if (statistics.getTotalEnergy(WildLife.Herbivore) != this.getTotalEnergy(WildLife.Herbivore)) return false;
        if (statistics.getTotalEnergy(WildLife.Vegetation) != this.getTotalEnergy(WildLife.Vegetation)) return false;
        
        return true;
    }
}