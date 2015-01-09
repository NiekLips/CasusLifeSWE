/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swe.database;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import swe.life.Statistics;
import swe.user.User;
import swe.user.UserRights;

/**
 *
 * @author Roy
 */
public class DatabaseTest {
    
    public DatabaseTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("setUpClass");
        System.out.println("openConnection");
        boolean expResult = true;
        boolean result = Database.openConnection();
        assertEquals(expResult, result);
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("tearDownClass");
        System.out.println("closeConnection");
        boolean expResult = true;
        boolean result = Database.closeConnection();
        assertEquals(expResult, result);
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of openConnection method, of class Database.
     * This is called in tearUpClass.
     */
    @Test
    public void testOpenConnection() {
        
    }

    /**
     * Test of closeConnection method, of class Database.
     * This is called in tearDownClass.
     */
    @Test
    public void testCloseConnection() {
        
    }

    /**
     * Test of createDatabase method, of class Database.
     */
    @Test
    public void testCreateDatabase() {
        System.out.println("createDatabase");
        boolean expResult = true;
        boolean result = Database.createDatabase();
        assertEquals(expResult, result);
    }

    /**
     * Test of userExists method, of class Database.
     * Only works with a empty database.
     */
    @Test
    public void testUserExists_String() throws Exception {
        System.out.println("userExists");
        String name = "Test";
        boolean expResult = false;
        boolean result = Database.userExists(name);
        assertEquals(expResult, result);
        
        String password = "Test";
        int rights = UserRights.toInteger(UserRights.Admin);
        expResult = true;
        result = Database.createUser(name, password, rights);
        assertEquals(expResult, result);
        
        expResult = true;
        result = Database.userExists(name);
        assertEquals(expResult, result);
        
        int expResultI = -1;
        int resultI = Database.userExists(name, password);
        assertNotSame(expResultI, resultI);
        
        User resultUser = Database.userForID(resultI);
        assertNotSame(null, resultUser);
        
        expResult = true;
        result = Database.changePassword(resultUser.getID(), password, password);
        assertEquals(expResult, result);
    }

    /**
     * Test of saveStatistics method, of class Database.
     */
    @Test
    public void testSaveStatistics() throws Exception {
        System.out.println("saveStatistics");
        int simulationID = Database.createSimulation();
        assertNotSame(-1, simulationID);

        Statistics statistics = new Statistics();
        boolean expResult = true;
        boolean result = Database.saveStatistics(simulationID, statistics);
        assertEquals(expResult, result);

        Statistics stats = Database.getStatistics(simulationID);
        assertNotSame(null, stats);
    }
}
