/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.life;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import swe.life.objects.Animal;
import swe.life.objects.Object;
import swe.life.objects.enumerations.Digestion;
import swe.life.objects.enumerations.Sex;

/**
 *
 * @author Roy
 */
public class WorldTest {
    private static World world = null;
    private static List<Object> objects;
    
    public WorldTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("setUpClass");
        
        world = new World(25, 25, "");
        
        objects = new ArrayList<>();
        objects.add(new Animal(0, 0, world, Digestion.Carnivorous, Sex.Male, 100, 4, 70, 10, 20, 25, 10, 20));
        objects.add(new Animal(5, 5, world, Digestion.Herbivorous, Sex.Male, 100, 4, 70, 10, 20, 25, 10, 20));
        objects.add(new Animal(0, 9, world, Digestion.Herbivorous, Sex.Female, 100, 4, 70, 10, 20, 25, 10, 20));
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("tearDownClass");
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getObjects method, of class World.
     */
    @Test
    public void testGetObjects() {
        System.out.println("getObjects");
        
        List<Object> expResult = objects;
        List<Object> result = world.getObjects();
        assertEquals(expResult, result);
    }

    /**
     * Test of getObjectsForXY method, of class World.
     */
    @Test
    public void testGetObjectsForXY() {
        System.out.println("getObjectsForXY");
        
        Object expResult = objects.get(0);
        List<Object> result = world.getObjectsForXY((int)expResult.getX(), (int)expResult.getY());
        assertEquals(1, result.size());
        assertEquals(expResult, result.get(0));
    }

    /**
     * Test of getNearestObjectKindFrom method, of class World.
     */
    @Test
    public void testGetNearestObjectKindFrom() {
        System.out.println("getNearestObjectKindFrom");
        
        Object searcher = objects.get(0);
        Object result = world.getNearestObjectKindFrom(Animal.class, searcher.getX(), searcher.getY(), true);
        assertEquals(objects.get(2), result);
    }

    /**
     * Test of getStatistics method, of class World.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetStatistics() throws Exception {
        System.out.println("getStatistics");
        
        Statistics expResult = new Statistics(0, 0, 0, 0, 0, 0, 0, 0);
        Statistics result = world.getStatistics();
        assertEquals(expResult, result);
        
        expResult = new Statistics(0, 1, 2, 0, 0, 100, 200, 0);
        result = world.getCurrentStatistics();
        assertEquals(expResult, result);
    }
}
