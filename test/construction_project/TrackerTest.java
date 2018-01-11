/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package construction_project;

import java.io.File;
import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mohammad.y
 */
public class TrackerTest {
    
    public TrackerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getFile method, of class Tracker.
     */
   /* @Test
    public void testGetFile() {
        System.out.println("getFile");
        Tracker instance = new Tracker();
        File expResult = null;
        File result = instance.getFile();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of checkPortionType method, of class Tracker.
     */
   /* @Test
    public void testCheckPortionType() {
        System.out.println("checkPortionType");
        String PortionURL = "C:/machine1.birzeit.edu/DB.txtsegment1";
        Tracker instance = new Tracker();
        String expResult = "segment";
        String result = instance.checkPortionType(PortionURL);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
*/
    /**
     * Test of getPortionsLocation method, of class Tracker.
     */
    @Test
    public void testGetPortionsLocation() {
        System.out.println("getPortionsLocation");
        File file = null;
        Tracker instance = new Tracker();
        LinkedList<FilePortion> expResult = null;
        LinkedList<FilePortion> result = instance.getPortionsLocation(file);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
