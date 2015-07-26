package com.gamesbykevin.framework.ai;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Node unit test
 * @author GOD
 */
public class NodeTest 
{
    private Node node;
    private Node parent;
    
    @BeforeClass
    public static void setUpClass() 
    {
        Node node = new Node(0,0, null, 0, 0);
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        Node node = new Node(0,0, null, 0, 0);
        node = null;
    }
    
    @Before
    public void setUp() 
    {
        parent = new Node(0,0, null, 0, 0);
        node = new Node(1,0, parent, 1, 10);
        
        assertNotNull(node);
        assertNotNull(parent);
    }
    
    @After
    public void tearDown() 
    {
        node = null;
        parent = null;
        
        assertNull(node);
        assertNull(parent);
    }
    
    @Test
    public void setMovementTest() 
    {
        assertFalse(node.getMovement() == 15);
        node.setMovement(15);
        assertTrue(node.getMovement() == 15);
    }
    
    @Test
    public void getMovementTest() 
    {
        assertTrue(parent.getMovement() == 0);
        parent.setMovement(4);
        assertFalse(parent.getMovement() == 0);
        assertTrue(parent.getMovement() == 4);
    }
    
    @Test
    public void setHeuristicTest() 
    {
        assertFalse(node.getHeuristic() == 15);
        node.setHeuristic(15);
        assertTrue(node.getHeuristic() == 15);
    }
    
    @Test
    public void getHeuristicTest() 
    {
        assertTrue(parent.getHeuristic() == 0);
        parent.setHeuristic(4);
        assertFalse(parent.getHeuristic() == 0);
        assertTrue(parent.getHeuristic() == 4);
    }
    
    @Test
    public void getCostTest() 
    {
        final int movement = 4;
        final int heuristic = 10;
        
        parent.setMovement(movement);
        parent.setHeuristic(heuristic);
        
        assertFalse(parent.getCost() == movement);
        assertFalse(parent.getCost() == heuristic);
        assertTrue(parent.getCost() == (movement + heuristic));
    }
    
    @Test
    public void hasLocationTest() 
    {
        int col1 = 0;
        int col2 = 2;
        int row1 = 1;
        int row2 = 2;
        
        parent = new Node(col1, row1, null, 0, 0);
        node = new Node(col2, row2, parent, 1, 10);
        
        assertFalse(parent.hasLocation(node));
        assertFalse(node.hasLocation(parent));
        
        assertFalse(node.hasLocation(col1, row1));
        assertTrue(node.hasLocation(col2, row2));
        
        parent = new Node(col1, row1, null, 0, 0);
        node = new Node(col1, row1, parent, 1, 10);
        
        assertTrue(parent.hasLocation(node));
        assertTrue(node.hasLocation(parent));
    }
    
    @Test
    public void getColumnTest() 
    {
        int col = 0;
        int row = 1;
        
        parent = new Node(col, row, null, 4, 54);
        
        assertTrue(parent.getColumn() == col);
        assertFalse(parent.getColumn() == row);
    }
    
    @Test
    public void getRowTest() 
    {
        int col = 0;
        int row = 1;
        
        parent = new Node(col, row, null, 4, 54);
        
        assertFalse(parent.getRow() == col);
        assertTrue(parent.getRow() == row);
    }
    
    @Test
    public void hasIdTest() 
    {
        parent = new Node(0, 0, null, 0, 0);
        node = new Node(0, 0, null, 0, 0);
        assertNotNull(parent.getId());
        assertNotNull(node.getId());
        
        assertTrue(node.hasId(node));
        assertTrue(node.hasId(node.getId()));
        
        assertTrue(parent.hasId(parent));
        assertTrue(parent.hasId(parent.getId()));
        
        assertFalse(node.hasId(parent));
        assertFalse(node.hasId(parent.getId()));
        assertFalse(parent.hasId(node));
        assertFalse(parent.hasId(node.getId()));
    }
    
    @Test
    public void getParentIdTest() 
    {
        parent = new Node(0, 0, null, 0, 0);
        node = new Node(0, 0, parent, 0, 0);
        
        assertNull(parent.getParentId());
        assertNotNull(node.getParentId());
        assertEquals(node.getParentId(), parent.getId());
    }
    
    @Test
    public void setParentIdTest() 
    {
        parent = new Node(0, 0, null, 0, 0);
        node = new Node(0, 0, parent, 0, 0);
        
        assertNull(parent.getParentId());
        assertNotNull(node.getParentId());
        assertEquals(node.getParentId(), parent.getId());
        parent.setParentId(node);
        assertEquals(parent.getParentId(), node.getId());
    }
}