/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.life.objects;

import java.util.Random;
import javafx.scene.paint.Color;
import swe.life.World;
import swe.life.objects.enumerations.Digestion;
import swe.life.objects.enumerations.Direction;
import swe.life.objects.enumerations.Goal;
import swe.life.objects.enumerations.Sex;

/**
 * This class is moving living {@link Object} in the world that extends the Dynamic class.
 * It can move over {@link Land} as well {@link Water} but not {@link Obstacle obstacles}.
 * @author Roy
 */
public class Animal extends Living {
    public static final double REACH = 1;
    public static final int OPTIMAL_NUMBER_OF_LEGS = 5;
    
    private Activity activity;
    private Direction moveDiretion;
    private Digestion digestion;
    private Sex sex;
    private int energy;
    private int legs;
    private int mateThreshold; //% of stamina a animal wants to mate
    private int moveThreshold;
    private int reproductionCosts; //% of stamina that gets transmitted to the child
    private int stamina; //Maximum amount of energy
    private int strength;
    private int swimThreshold; //% of stamina a animal wants to swim
    
    public Animal(double x, double y, World world, Digestion digestion, Sex sex, int energy, int legs, int mateThreshold, int moveThreshold, int reproductionCosts, int stamina, int strength, int swimThreshold) {
        this(x, y, getColorForDigestion(digestion), world);
        this.activity = new Activity();
        this.moveDiretion = Direction.None;
        this.digestion = digestion;
        this.sex = sex;
        this.energy = energy;
        this.legs = legs;
        this.mateThreshold = mateThreshold;
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
    
    private Animal(double x, double y, Color color, World world) {
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
        return energy; //TODO correct this (should be hunter.strength - self.stamina)
    }

    /**
     * State-machine based on energy.
     */
    @Override
    public void simulate() {
        //Find or goto a mate
        if (energy > mateThreshold) {
            if (activity.getGoal() != Goal.FindPartner) {
                activity.setGoal(Goal.FindPartner);
            }
            
            if (activity.getTarget() == null) {
                activity.setTarget(world.getNearestObjectKindFrom(Animal.class, x, y, false));
                move();
            }
            else { //A potential mate has been found
                if (isWithinReach(activity.getTarget()))
                    propagate((Animal)activity.getTarget());
                else
                    move();
            }
        }
        //Find or goto a food on land
        else if (energy > swimThreshold) {
            if (activity.getGoal() != Goal.FindFood) {
                activity.setGoal(Goal.FindFood);
            }
            
            if (activity.getTarget() == null) {
                switch (digestion) {
                    case Carnivorous: {
                        activity.setTarget(world.getNearestObjectKindFrom(Animal.class, x, y, false));
                        break;
                    }
                    case Herbivorous: {
                        activity.setTarget(world.getNearestObjectKindFrom(Vegetation.class, x, y, false));
                        break;
                    }
                    case OmnivorousPreferMeat: {
                        Object target = world.getNearestObjectKindFrom(Animal.class, x, y, false);
                        if (target == null) target = world.getNearestObjectKindFrom(Vegetation.class, x, y, false);
                        activity.setTarget((Living) target);
                        break;
                    }
                    case OmnivorousPreferVegetation: {
                        Object target = world.getNearestObjectKindFrom(Vegetation.class, x, y, false);
                        if (target == null) target = world.getNearestObjectKindFrom(Animal.class, x, y, false);
                        activity.setTarget(target);
                        break;
                    }
                    /*
                    case OmnivorousNoPreference: {
                        Object target = world.getNearestObjectKindFrom(Living.class, x, y, true);
                        activity.setTarget((Living) target);
                        break;
                    }
                    */
                }
                
                move();
            }
            else { //A potential meal has been found
                if (isWithinReach(activity.getTarget()))
                    eat((Living)activity.getTarget());
                else
                    move();
            }
        }
        //Find or goto a food on land and water
        else if (energy > moveThreshold) {
            if (activity.getGoal() != Goal.FindFood) {
                activity.setGoal(Goal.FindFood);
            }
            
            if (activity.getTarget() == null) {
                switch (digestion) {
                    case Carnivorous: {
                        activity.setTarget(world.getNearestObjectKindFrom(Animal.class, x, y, true));
                        break;
                    }
                    case Herbivorous: {
                        activity.setTarget(world.getNearestObjectKindFrom(Vegetation.class, x, y, true));
                        break;
                    }
                    case OmnivorousPreferMeat: {
                        Object target = world.getNearestObjectKindFrom(Animal.class, x, y, true);
                        if (target == null) target = world.getNearestObjectKindFrom(Vegetation.class, x, y, true);
                        activity.setTarget((Living) target);
                        break;
                    }
                    case OmnivorousPreferVegetation: {
                        Object target = world.getNearestObjectKindFrom(Vegetation.class, x, y, true);
                        if (target == null) target = world.getNearestObjectKindFrom(Animal.class, x, y, true);
                        activity.setTarget(target);
                        break;
                    }
                    /*
                    case OmnivorousNoPreference: {
                        Object target = world.getNearestObjectKindFrom(Living.class, x, y, true);
                        activity.setTarget((Living) target);
                        break;
                    }
                    */
                }
            
                move();
            }
            else { //A potential meal has been found
                if (isWithinReach(activity.getTarget()))
                    eat((Living)activity.getTarget());
                else
                    move();
            }
        }
        //Wait for food to spawn
        else if (energy > 0) {
            if (activity.getGoal() != Goal.FindFood) {
                activity.setGoal(Goal.FindFood);
            } //TODO re-search at some point
            
            if (isWithinReach(activity.getTarget()))
                eat((Living)activity.getTarget());
        }
        //Die
        else {
            world.removeObject(this);
        }
    }
    
    /**
     * Checks if the object is within reach of eating/mating.
     * @param object The object to check if its in reach.
     * @return If the object is within reach.
     */
    private boolean isWithinReach(Object object) {
        double cx = Math.pow(x - object.getX(), 2);
        double cy = Math.pow(y - object.getY(), 2);
        return (Math.sqrt(cx + cy) < REACH);
    }
    
    /** Returns the calculated weight of the animal.
     * @return Legs * 10 + (energy-strength)
     */
    public double getWeight() {
        if (energy - strength > 0)
            return (legs * 10 + (energy-strength));
        else
            return (legs * 10);
    }
    
    /** Returns the calculated speed of the animal.
     * @return 1 - (Math.abs(OPTIMAL_NUMBER_OF_LEGS - Legs) / 5)
     */
    public double getSpeed() {
        return (1 - (Math.abs(OPTIMAL_NUMBER_OF_LEGS - legs) / 5));
    }
    
    /** Returns the {@link Digestion digestion} of the animal.
     * @return Digestion.
     */
    public Digestion getDigestion() {
        return digestion;
    }
    
    /** Returns the energy of the animal.
     * @return The energy as a int.
     */
    public int getEnergy() {
        return energy;
    }
    
    /** Returns the amount of legs of the animal.
     * @return The legs as a int.
     */
    public int getLegs() {
        return legs;
    }
    
    /** Returns the mate threshold of the animal.
     * @return The mate threshold as a int.
     */
    public int getMateThreshold() {
        return mateThreshold;
    }
    
    /** Returns the move threshold of the animal.
     * @return The move threshold as a int.
     */
    public int getMoveThreshold() {
        return moveThreshold;
    }
    
    /** Returns the reproduction costs of the animal.
     * @return The reproduction costs as a int.
     */
    public int getReproductionCosts() {
        return reproductionCosts;
    }
    
    /** Returns the stamina of the animal.
     * @return The stamina as a int.
     */
    public int getStamina() {
        return stamina;
    }
    
    /** Returns the strength of the animal.
     * @return The strength as a int.
     */
    public int getStrength() {
        return strength;
    }
    
    /** Returns the swim threshold of the animal.
     * @return The swim threshold as a int.
     */
    public int getSwimThreshold() {
        return swimThreshold;
    }
    
    /**
     * When the animal propagates with another one, the female will spawn a new Animal bases on their properties.
     * @param animal The partner.
     */
    public void propagate(Animal animal) {
        if (sex == Sex.Female) {
            Random random = new Random();
            Digestion lDigestion = (random.nextBoolean() ? animal.getDigestion() : getDigestion());
            Sex lSex = (random.nextBoolean() ? Sex.Male : Sex.Female);
            int lEnergy = getPropagateValue(animal.getEnergy(), getEnergy(), random);
            int lLegs = getPropagateValue(animal.getLegs(), getLegs(), random);
            int lMateThreshold = getPropagateValue(animal.getMateThreshold(), getMateThreshold(), random);
            int lMoveThreshold = getPropagateValue(animal.getMoveThreshold(), getMoveThreshold(), random);
            int lReproductionCosts = getPropagateValue(animal.getReproductionCosts(), getReproductionCosts(), random);
            int lStamina = getPropagateValue(animal.getStamina(), getStamina(), random);
            int lStrength = getPropagateValue(animal.getStrength(), getStrength(), random);
            int lSwimThreshold = getPropagateValue(animal.getStrength(), getStrength(), random);
            Animal child = new Animal(x, y, world, lDigestion, lSex, lEnergy, lLegs, lMateThreshold, lMoveThreshold, lReproductionCosts, lStamina, lStrength, lSwimThreshold);
        }
    }
    
    /**
     * Returns the average value of the maleValue & femaleValue with a maximum difference of 10%.
     * @param maleValue The value of the male animal.
     * @param femaleValue The value of the female animal.
     * @param random The Random so it doesn't have to be created every time.
     * @return A double casted to int.
     */
    private int getPropagateValue(double maleValue, double femaleValue, Random random) {
        double average = (maleValue + femaleValue / 2);
        return (int) average * (1 + ((random.nextInt(20) - 10) / 100));
    }
    
    /**
     * If the activity has a target it will move towards the target, else it will wander in a searching direction.
     * Costs as much energy as the weight of the animal.
     */
    public void move() {
        double direction;
        
        if (activity.getTarget() != null) {
            double deltaX = activity.getTarget().getX() - x;
            double deltaY = activity.getTarget().getY() - y;
            direction = Math.atan(deltaY / deltaX);
        }
        else {
            direction = Math.toRadians(new Random().nextInt(360));
        }
        
        double newX = x + (getSpeed() * Math.cos(direction));
        double newY = y + (getSpeed() * Math.sin(direction));
        if (newX < 0) newX += world.getWidth();
        else if (newX > world.getWidth()) newX -= world.getWidth();
        if (newY < 0) newY += world.getHeight();
        else if (newY > world.getHeight()) newY -= world.getHeight();
        
        //direction = Math.toDegrees(direction);
        //If a collisions happens at x & y, scan 180 degrees in the direction for a collision
        //TODO collision with obstacles, animals and water

        x = newX;
        y = newY;
        
        energy -= getWeight();
    }
    
    /** When the animal eats something.
     * @param living The {@link Animal} or {@link Vegetation} that gets eaten.
     */
    public void eat(Living living) {
        energy += living.eaten();
        if (energy > stamina) energy = stamina;
    }
}
