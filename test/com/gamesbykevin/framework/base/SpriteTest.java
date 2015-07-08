package com.gamesbykevin.framework.base;

import com.gamesbykevin.framework.base.Animation;
import com.gamesbykevin.framework.util.Timers;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.UUID;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Sprite unit test
 * @author GOD
 */
public class SpriteTest 
{
    //our test Sprite objects
    private Sprite sprite, sprite1;
    
    //use this as a test image
    public static final BufferedImage TEST_IMAGE = new BufferedImage(2000,2000, BufferedImage.TYPE_INT_ARGB);
    
    @BeforeClass
    public static void setUpClass() 
    {
        Sprite sprite = new Sprite();
        assertNotNull(sprite);
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        Sprite sprite = new Sprite();
        sprite.dispose();
        sprite = null;
        assertNull(sprite);
    }
    
    @Before
    public void setUp() 
    {
        sprite = new Sprite();
        sprite1 = new Sprite();
    }
    
    @After
    public void tearDown() 
    {
        sprite.dispose();
        sprite = null;
        assertNull(sprite);
        
        sprite1.dispose();
        sprite1 = null;
        assertNull(sprite1);
    }
    
    @Test
    public void createSpriteSheetTest() 
    {
        assertNull(sprite.getSpriteSheet());
        sprite.createSpriteSheet();
        assertNotNull(sprite.getSpriteSheet());
    }
    
    @Test
    public void getParentIdTest() 
    {
        assertNull(sprite.getParentId());
        
        UUID uuid = UUID.randomUUID();
        
        assertNull(sprite.getParentId());
        sprite.setParentId(uuid);
        assertTrue(sprite.getParentId().equals(uuid));
    }
    
    @Test
    public void setParentIdTest() 
    {
        assertNull(sprite.getParentId());
        
        UUID uuid = UUID.randomUUID();
        UUID uuid1 = UUID.randomUUID();
        
        assertNull(sprite.getParentId());
        sprite.setParentId(uuid);
        assertTrue(sprite.getParentId().equals(uuid));
        assertFalse(sprite.getParentId().equals(uuid1));
        sprite.setParentId(uuid1);
        assertFalse(sprite.getParentId().equals(uuid));
        assertTrue(sprite.getParentId().equals(uuid1));
    }
    
    @Test
    public void getIdTest() 
    {
        UUID uuid = UUID.randomUUID();
        
        assertNotNull(sprite.getId());
        assertFalse(sprite.getId().equals(uuid));
        assertTrue(sprite.getId().equals(sprite.getId()));
    }
    
    @Test
    public void hasIdTest() 
    {
        UUID uuid = UUID.randomUUID();
        
        assertFalse(sprite.hasId(uuid));
        assertFalse(sprite1.hasId(uuid));
        assertFalse(sprite.hasId(sprite1));
        assertFalse(sprite1.hasId(sprite));
        
        assertTrue(sprite.hasId(sprite.getId()));
        assertTrue(sprite1.hasId(sprite1.getId()));
    }
    
    @Test 
    public void disposeTest()
    {
        sprite.dispose();
        
        //assume objects are recycled
        assertNull(sprite.getImage());
        assertNull(sprite.getSpriteSheet());
    }
    
    @Test 
    public void getSpriteSheetTest()
    {
        assertNull(sprite.getSpriteSheet());
        sprite.createSpriteSheet();
        assertNotNull(sprite.getSpriteSheet());
    }
    
    @Test
    public void getCenterTest()
    {
        assertTrue(sprite.getCenter().x == 0);
        assertTrue(sprite.getCenter().y == 0);
        
        sprite.setWidth(10);
        sprite.setHeight(18);
        
        assertFalse(sprite.getCenter().x == 0);
        assertFalse(sprite.getCenter().y == 0);
        
        assertTrue(sprite.getCenter().x == 5);
        assertTrue(sprite.getCenter().y == 9);
    }
    
    @Test
    public void getPointTest()
    {
        assertTrue(sprite.getPoint().x == 0);
        assertTrue(sprite.getPoint().y == 0);
        
        sprite.setX(2.5);
        sprite.setY(7.33445);
        
        assertFalse(sprite.getPoint().x == 2.5);
        assertFalse(sprite.getPoint().y == 7.33445);
        
        assertTrue(sprite.getPoint().x == 2);
        assertTrue(sprite.getPoint().y == 7);
    }
    
    @Test
    public void setImageTest()
    {
        assertNull(sprite.getImage());
        sprite.setImage(TEST_IMAGE);
        assertNotNull(sprite.getImage());
    }
    
    @Test
    public void getImageTest()
    {
        assertNull(sprite.getImage());
        sprite.setImage(TEST_IMAGE);
        assertNotNull(sprite.getImage());
        assertTrue(sprite.getImage().getWidth(null) == 2000);
        assertTrue(sprite.getImage().getHeight(null) == 2000);
    }

    @Test
    public void setVerticalFlipTest()
    {
        sprite.setVerticalFlip(true);
        assertTrue(sprite.hasVerticalFlip());
        sprite.setVerticalFlip(false);
        assertFalse(sprite.hasVerticalFlip());
    }

    @Test
    public void hasVerticalFlipTest()
    {
        assertFalse(sprite.hasVerticalFlip());
        sprite.setVerticalFlip(true);
        assertTrue(sprite.hasVerticalFlip());
        sprite.setVerticalFlip(false);
        assertFalse(sprite.hasVerticalFlip());
    }

    @Test
    public void setHorizontalFlipTest()
    {
        sprite.setHorizontalFlip(true);
        assertTrue(sprite.hasHorizontalFlip());
        sprite.setHorizontalFlip(false);
        assertFalse(sprite.hasHorizontalFlip());
    }

    @Test
    public void hasVHorizontalFlipTest()
    {
        assertFalse(sprite.hasHorizontalFlip());
        sprite.setHorizontalFlip(true);
        assertTrue(sprite.hasHorizontalFlip());
        sprite.setHorizontalFlip(false);
        assertFalse(sprite.hasHorizontalFlip());
    }

    @Test
    public void setLocationTest()
    {
        int x = 76;
        int y = 233;
        
        sprite1.setLocation(x, y);
        
        sprite.setLocation(sprite1);
        assertTrue(sprite.getX() == x);
        assertTrue(sprite.getY() == y);
        
        x = 12;
        y = 1000;
        sprite.setLocation(x, y);
        assertTrue(sprite.getX() == x);
        assertTrue(sprite.getY() == y);
        
        double x1 = 2.6;
        double y1 = 763.5666;
        
        sprite.setLocation(x1, y1);
        assertTrue(sprite.getX() == x1);
        assertTrue(sprite.getY() == y1);
        
        Point p = new Point(x, y);
        sprite.setLocation(p);
        assertTrue(sprite.getX() == p.x);
        assertTrue(sprite.getY() == p.y);
        
        Rectangle r = new Rectangle(50, 50, 100, 150);
        sprite.setLocation(r);
        assertTrue(sprite.getX() == 100);
        assertTrue(sprite.getY() == 125);
    }

    @Test
    public void setXTest()
    {
        sprite.setX(3.5);
        assertTrue(sprite.getX() == 3.5);
        sprite.setX(7);
        assertTrue(sprite.getX() == 7);
    }

    @Test
    public void getXTest()
    {
        assertTrue(sprite.getX() == 0);
        sprite.setX(3.5);
        assertTrue(sprite.getX() == 3.5);
        sprite.setX(7);
        assertTrue(sprite.getX() == 7);
    }

    @Test
    public void setZTest()
    {
        sprite.setZ(3.5);
        assertTrue(sprite.getZ() == 3.5);
        sprite.setZ(7);
        assertTrue(sprite.getZ() == 7);
    }

    @Test
    public void getZTest()
    {
        assertTrue(sprite.getZ() == 0);
        sprite.setZ(3.5);
        assertTrue(sprite.getZ() == 3.5);
        sprite.setZ(7);
        assertTrue(sprite.getZ() == 7);
    }

    @Test
    public void setYTest()
    {
        sprite.setY(3.5);
        assertTrue(sprite.getY() == 3.5);
        sprite.setY(7);
        assertTrue(sprite.getY() == 7);
    }

    @Test
    public void getYTest()
    {
        assertTrue(sprite.getY() == 0);
        sprite.setY(3.5);
        assertTrue(sprite.getY() == 3.5);
        sprite.setY(7);
        assertTrue(sprite.getY() == 7);
    }

    @Test
    public void getDistanceTest()
    {
        double x1, x2, y1, y2;
        
        x1 = 246;
        x2 = 646;
        y1 = 102;
        y2 = 9;
        
        //set and calculate the distance
        sprite.setLocation(x1, y1);
        sprite1.setLocation(x2, y2);
        double distance = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        assertTrue(sprite.getDistance(sprite1) == distance);
        
        
        //set and calculate the distance
        Point p = new Point(145, 334);
        sprite.setLocation(x1, y1);
        distance = Math.sqrt(Math.pow(x1 - p.x, 2) + Math.pow(y1 - p.y, 2));
        assertTrue(sprite.getDistance(p) == distance);
        
        
        //set and calculate the distance
        sprite.setLocation(x1, y1);
        distance = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        assertTrue(sprite.getDistance(x2, y2) == distance);
        
        //set and calculate the distance
        int x3 = 90;
        int y3 = 24;
        sprite.setLocation(x1, y1);
        distance = Math.sqrt(Math.pow(x1 - x3, 2) + Math.pow(y1 - y3, 2));
        assertTrue(sprite.getDistance(x3, y3) == distance);
    }
    
    @Test
    public void setDimensionsTest() throws Exception
    {
        int w = 135;
        int h = 25;
        
        sprite1.setWidth(w);
        sprite1.setHeight(h);
        sprite.setDimensions(sprite1);
        assertTrue(sprite.getWidth() == w);
        assertTrue(sprite.getHeight() == h);
        
        sprite.setDimensions(TEST_IMAGE);
        assertTrue(sprite.getWidth() == TEST_IMAGE.getWidth());
        assertTrue(sprite.getHeight() == TEST_IMAGE.getHeight());
        
        sprite.setDimensions(h);
        assertTrue(sprite.getWidth() == h);
        assertTrue(sprite.getHeight() == h);
        
        sprite.setDimensions(w, h);
        assertTrue(sprite.getWidth() == w);
        assertTrue(sprite.getHeight() == h);
        
        double w1 = 54.34543;
        double h1 = 8533.4434;
        sprite.setDimensions(w1, h1);
        assertTrue(sprite.getWidth() == w1);
        assertTrue(sprite.getHeight() == h1);
        
        w = 54;
        h = 99;
        Rectangle r = new Rectangle(0,0, w, h);
        sprite.setDimensions(r);
        assertTrue(sprite.getWidth() == w);
        assertTrue(sprite.getHeight() == h);
        
        w = 105;
        h = 43;
        Dimension d = new Dimension(w, h);
        sprite.setDimensions(d);
        assertTrue(sprite.getWidth() == w);
        assertTrue(sprite.getHeight() == h);
        
        //test the spritesheet assigned animaton
        final String KEY = "Default";
        final long delay = Timers.toNanoSeconds(1);
        w = 175;
        h = 93;
        sprite.createSpriteSheet();
        sprite.getSpriteSheet().add(0, 0, w, h, delay, KEY);
        sprite.getSpriteSheet().setCurrent(KEY);
        sprite.setDimensions();
        assertTrue(sprite.getWidth() == w);
        assertTrue(sprite.getHeight() == h);
    }
    
    @Test
    public void setWidthTest() 
    {
        double w1 = 434.43434;
        assertFalse(sprite.getWidth() == w1);
        sprite.setWidth(w1);
        assertTrue(sprite.getWidth() == w1);
        
        int w2 = 90;
        assertFalse(sprite.getWidth() == w2);
        sprite.setWidth(w2);
        assertTrue(sprite.getWidth() == w2);
    }
    
    @Test
    public void getWidthTest() 
    {
        assertTrue(sprite.getWidth() == 0);
        
        int w2 = 90;
        sprite.setWidth(w2);
        assertTrue(sprite.getWidth() == w2);
        
        double w1 = 434.43434;
        sprite.setWidth(w1);
        assertTrue(sprite.getWidth() == w1);
    }
    
    
    @Test
    public void setHeightTest() 
    {
        double h1 = 5464.43646464;
        assertFalse(sprite.getHeight()== h1);
        sprite.setHeight(h1);
        assertTrue(sprite.getHeight() == h1);
        
        int h2 = 9323240;
        assertFalse(sprite.getHeight() == h2);
        sprite.setHeight(h2);
        assertTrue(sprite.getHeight() == h2);
    }
    
    @Test
    public void getHeightTest() 
    {
        assertTrue(sprite.getHeight() == 0);
        
        double h1 = 5464.43646464;
        assertFalse(sprite.getHeight() == h1);
        sprite.setHeight(h1);
        assertTrue(sprite.getHeight() == h1);
        
        int h2 = 9323240;
        assertFalse(sprite.getHeight() == h2);
        sprite.setHeight(h2);
        assertTrue(sprite.getHeight() == h2);
    }
    
    @Test
    public void resetVelocityTest()
    {
        int vx = 5;
        int vy = -8;
        int vz = 76;
        
        sprite.setVelocityX(vx);
        sprite.setVelocityY(vy);
        sprite.setVelocityZ(vz);
        
        assertTrue(sprite.getVelocityX() == vx);
        assertTrue(sprite.getVelocityY() == vy);
        assertTrue(sprite.getVelocityZ() == vz);
        sprite.resetVelocity();
        assertTrue(sprite.getVelocityX() == 0);
        assertTrue(sprite.getVelocityY() == 0);
        assertTrue(sprite.getVelocityZ() == 0);
        
        sprite.setVelocityX(vx);
        sprite.setVelocityY(vy);
        sprite.setVelocityZ(vz);
        sprite.resetVelocityX();
        assertTrue(sprite.getVelocityX() == 0);
        assertFalse(sprite.getVelocityY() == 0);
        assertFalse(sprite.getVelocityZ() == 0);
        
        sprite.setVelocityX(vx);
        sprite.setVelocityY(vy);
        sprite.setVelocityZ(vz);
        sprite.resetVelocityY();
        assertFalse(sprite.getVelocityX() == 0);
        assertTrue(sprite.getVelocityY() == 0);
        assertFalse(sprite.getVelocityZ() == 0);
        
        sprite.setVelocityX(vx);
        sprite.setVelocityY(vy);
        sprite.setVelocityZ(vz);
        sprite.resetVelocityZ();
        assertFalse(sprite.getVelocityX() == 0);
        assertFalse(sprite.getVelocityY() == 0);
        assertTrue(sprite.getVelocityZ() == 0);
    }
    
    @Test
    public void setVelocityTest()
    {
        int vx1 = 5;
        double vx2 = -65.34;
        
        int vy1 = -8;
        double vy2 = 100;
        
        int vz1 = 76;
        double vz2 = 23.7642;
        
        sprite.resetVelocity();
        sprite.setVelocityX(vx1);
        sprite.setVelocityY(vy1);
        sprite.setVelocityZ(vz1);
        assertTrue(sprite.getVelocityX() == vx1);
        assertTrue(sprite.getVelocityY() == vy1);
        assertTrue(sprite.getVelocityZ() == vz1);
        
        sprite.resetVelocity();
        sprite.setVelocityX(vx2);
        sprite.setVelocityY(vy2);
        sprite.setVelocityZ(vz2);
        assertTrue(sprite.getVelocityX() == vx2);
        assertTrue(sprite.getVelocityY() == vy2);
        assertTrue(sprite.getVelocityZ() == vz2);
        
        sprite1.resetVelocity();
        sprite1.setVelocityX(vx2);
        sprite1.setVelocityY(vy2);
        sprite1.setVelocityZ(vz2);
        sprite.resetVelocity();
        sprite.setVelocity(sprite1);
        assertTrue(sprite.getVelocityX() == sprite1.getVelocityX());
        assertTrue(sprite.getVelocityY() == sprite1.getVelocityY());
        assertTrue(sprite.getVelocityZ() == sprite1.getVelocityZ());
        
        sprite.resetVelocity();
        sprite.setVelocity(vx2, vy2, vz2);
        assertTrue(sprite.getVelocityX() == vx2);
        assertTrue(sprite.getVelocityY() == vy2);
        assertTrue(sprite.getVelocityZ() == vz2);
        
        sprite.resetVelocity();
        sprite.setVelocity(vx1, vy1, vz1);
        assertTrue(sprite.getVelocityX() == vx1);
        assertTrue(sprite.getVelocityY() == vy1);
        assertTrue(sprite.getVelocityZ() == vz1);
        
        sprite.resetVelocity();
        sprite.setVelocity(vx2, vy2);
        assertTrue(sprite.getVelocityX() == vx2);
        assertTrue(sprite.getVelocityY() == vy2);
        assertFalse(sprite.getVelocityZ() == vz2);
        
        sprite.resetVelocity();
        sprite.setVelocity(vx1, vy1);
        assertTrue(sprite.getVelocityX() == vx1);
        assertTrue(sprite.getVelocityY() == vy1);
        assertFalse(sprite.getVelocityZ() == vz1);
        
        Point p = new Point(vx1, vy1);
        sprite.resetVelocity();
        sprite.setVelocity(p);
        assertTrue(sprite.getVelocityX() == vx1);
        assertTrue(sprite.getVelocityY() == vy1);
        assertFalse(sprite.getVelocityZ() == vz1);
        
        sprite1.resetVelocity();
        sprite1.setVelocityX(vx2);
        sprite1.setVelocityY(vy2);
        sprite1.setVelocityZ(vz2);
        sprite.resetVelocity();
        sprite.setVelocityX(sprite1);
        assertTrue(sprite.getVelocityX() == sprite1.getVelocityX());
        assertFalse(sprite.getVelocityY() == sprite1.getVelocityY());
        assertFalse(sprite.getVelocityZ() == sprite1.getVelocityZ());
        
        sprite1.resetVelocity();
        sprite1.setVelocityX(vx2);
        sprite1.setVelocityY(vy2);
        sprite1.setVelocityZ(vz2);
        sprite.resetVelocity();
        sprite.setVelocityY(sprite1);
        assertFalse(sprite.getVelocityX() == sprite1.getVelocityX());
        assertTrue(sprite.getVelocityY() == sprite1.getVelocityY());
        assertFalse(sprite.getVelocityZ() == sprite1.getVelocityZ());
        
        sprite1.resetVelocity();
        sprite1.setVelocityX(vx2);
        sprite1.setVelocityY(vy2);
        sprite1.setVelocityZ(vz2);
        sprite.resetVelocity();
        sprite.setVelocityZ(sprite1);
        assertFalse(sprite.getVelocityX() == sprite1.getVelocityX());
        assertFalse(sprite.getVelocityY() == sprite1.getVelocityY());
        assertTrue(sprite.getVelocityZ() == sprite1.getVelocityZ());
    }
    
    @Test
    public void getVelocityXTest()
    {
        double velocity = 52121.12455;
        sprite.resetVelocity();
        sprite.setVelocityX(velocity);
        assertTrue(sprite.getVelocityX() == velocity);
    }
    
    @Test
    public void getVelocityYTest()
    {
        double velocity = 52121.12455;
        sprite.resetVelocity();
        sprite.setVelocityY(velocity);
        assertTrue(sprite.getVelocityY() == velocity);
    }
    
    @Test
    public void getVelocityZTest()
    {
        double velocity = 52121.12455;
        sprite.resetVelocity();
        sprite.setVelocityZ(velocity);
        assertTrue(sprite.getVelocityZ() == velocity);
    }
    
    @Test
    public void hasVelocityZTest()
    {
        final double velocity = 134.341;
        
        sprite.resetVelocity();
        sprite.setVelocityX(velocity);
        assertTrue(sprite.hasVelocity());
        assertTrue(sprite.hasVelocityX());
        assertFalse(sprite.hasVelocityY());
        assertFalse(sprite.hasVelocityZ());
        
        sprite.resetVelocity();
        sprite.setVelocityY(velocity);
        assertTrue(sprite.hasVelocity());
        assertFalse(sprite.hasVelocityX());
        assertTrue(sprite.hasVelocityY());
        assertFalse(sprite.hasVelocityZ());
        
        sprite.resetVelocity();
        sprite.setVelocityZ(velocity);
        assertTrue(sprite.hasVelocity());
        assertFalse(sprite.hasVelocityX());
        assertFalse(sprite.hasVelocityY());
        assertTrue(sprite.hasVelocityZ());
    }
    
    @Test
    public void getRectangleTest()
    {
        final int x = 53, y = 291, w = 75, h = 25;
        
        sprite.setX(x);
        sprite.setY(y);
        sprite.setWidth(w);
        sprite.setHeight(h);
        
        assertTrue(sprite.getRectangle().x == x);
        assertTrue(sprite.getRectangle().y == y);
        assertTrue(sprite.getRectangle().width == w);
        assertTrue(sprite.getRectangle().height == h);
    }
    
    @Test
    public void update()
    {
        double x = 230.34;
        double y = 54;
        double z = 10.435;
        sprite.setX(x);
        sprite.setY(y);
        sprite.setZ(z);
        
        double vx = -329;
        double vy = 43;
        double vz = 1.5423;
        sprite.setVelocityX(vx);
        sprite.setVelocityY(vy);
        sprite.setVelocityZ(vz);
        
        sprite.update();
        
        assertTrue(sprite.getX() == (x + vx));
        assertTrue(sprite.getY() == (y + vy));
        assertTrue(sprite.getZ() == (z + vz));
    }
    
    @Test
    public void drawTest() throws Exception
    {
        final Graphics g = TEST_IMAGE.createGraphics();
        
        sprite.setImage(TEST_IMAGE);
        sprite.setWidth(100);
        sprite.setHeight(100);
        sprite.draw(g);
        sprite.setImage(null);
        
        sprite.draw(g, TEST_IMAGE);
        
        Rectangle r = new Rectangle(100, 456, 1000, 750);
        
        sprite.draw(g, TEST_IMAGE, null);
        sprite.setHorizontalFlip(true);
        sprite.setVerticalFlip(false);
        sprite.draw(g, TEST_IMAGE, r);
        
        sprite.setHorizontalFlip(false);
        sprite.setVerticalFlip(true);
        sprite.draw(g, TEST_IMAGE, r);
        
        sprite.setHorizontalFlip(true);
        sprite.setVerticalFlip(true);
        sprite.draw(g, TEST_IMAGE, r);

        
        sprite.setImage(TEST_IMAGE);
        
        sprite.setHorizontalFlip(false);
        sprite.setVerticalFlip(false);
        sprite.draw(g, r);
        
        sprite.setHorizontalFlip(true);
        sprite.setVerticalFlip(false);
        sprite.draw(g, r);
        
        sprite.setHorizontalFlip(false);
        sprite.setVerticalFlip(true);
        sprite.draw(g, r);
        
        sprite.setHorizontalFlip(true);
        sprite.setVerticalFlip(true);
        sprite.draw(g, r);
        
        final String KEY = "Default";
        int x = 75, y = 10, w = 500, h = 100;
        long d = Timers.toNanoSeconds(1l);
        
        
        Animation a = new Animation(x, y, w, h, d);
        sprite.createSpriteSheet();
        sprite.getSpriteSheet().add(a, KEY);
        sprite.getSpriteSheet().setCurrent(KEY);
        sprite.setImage(TEST_IMAGE);
        sprite.draw(g);
        
        sprite.setImage(null);
        sprite.draw(g, TEST_IMAGE);
        
        int dx1 = 10, dy1 = 10;
        int dx2 = 100, dy2 = 20;
        int sx1 = 950, sy1 = 0;
        int sx2 = 1764, sy2 = 324;
        
        sprite.draw(g, TEST_IMAGE, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2);
        
        sx1 = 1752;
        sy1 = 100;
        sx2 = 100;
        sy2 = 1;
        
        sprite.draw(g, TEST_IMAGE, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2);
    }
}