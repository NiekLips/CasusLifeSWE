/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.life.objects;

import javafx.scene.paint.Color;
import swe.life.World;
import swe.life.objects.enumerations.Digestion;
import swe.life.objects.enumerations.Direction;
import swe.life.objects.enumerations.Sex;

/**
 * This class is moving living {@link Object} in the world that extends the Dynamic class.
 * It can move over {@link Land} as well {@link Water} but not {@link Obstacle obstacles}.
 * @author Roy
 */
public class Animal extends Living {
    public static final int OPTIMAL_NUMBER_OF_LEGS = 5;
    
    private Direction moveDiretion;
    private Digestion digestion;
    private Sex sex;
    private int energy;
    private int legs;
    private int moveThreshold;
    private int reproductionCosts;
    private int stamina;
    private int strength;
    private int swimThreshold;
    
    public Animal(int x, int y, World world, Digestion digestion, Sex sex, int energy, int legs, int moveThreshold, int reproductionCosts, int stamina, int strength, int swimThreshold) {
        this(x, y, getColorForDigestion(digestion), world);
        this.moveDiretion = Direction.None;
        this.digestion = digestion;
        this.sex = sex;
        this.energy = energy;
        this.legs = legs;
        this.moveThreshold = moveThreshold;
        this.reproductionCosts = reproductionCosts;
        this.stamina = stamina;
        this.strength = strength;
        this.swimThreshold = swimThreshold;
    }
    
    /**
     * Returns the color for the animal in the grid.
     * This is a static method since it is used in the constructor.
     * @return The color for the cell.
     */
    private static Color getColorForDigestion(Digestion digestion) {
        Color color;
        switch (digestion) {
            case Carnivorous: color = Color.RED; break;
            case Herbivorous: color = Color.BROWN; break;
            default: color = Color.YELLOW; break; //Omnivorous
        }
        return color;
    }
    
    private Animal(int x, int y, Color color, World world) {
        super(x, y, color, world);
    }

    /**
     * Returns the energy eaten.
     * This will cause the animal to 'die' &amp; get removed from the world object list.
     * @return The energy the animal had.
     */
    @Override
    public int eaten() {
        world.removeObject(this);
        return energy;
    }

    @Override
    public void simulate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        
    }
    
    /** Returns the calculated weight of the animal.
     * @return Legs * 10 + (energy-strength)
     */
    public double getWeight() {
        return (legs * 10 + (energy-strength));
    }
    
    /** Returns the calculated speed of the animal.
     * @return 1 - (Math.abs(OPTIMAL_NUMBER_OF_LEGS - Legs) / 5)
     */
    public double getSpeed() {
        return (1 - (Math.abs(OPTIMAL_NUMBER_OF_LEGS - legs) / 5));
    }
    
    /** Costs as much energy as the weight of the animal.
     * 
     */
    public void move() {
        //TODO implement
    }
    
    /**
     * When the animal eats something.
     * @param living The {@link Animal} or {@link Vegetation} that gets eaten.
     */
    public void eat(Living living) {
        energy += living.eaten();
        //TODO implement
    }
}
