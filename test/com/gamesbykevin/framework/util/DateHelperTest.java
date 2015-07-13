package com.gamesbykevin.framework.util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

/**
 * Date Helper unit test
 * @author GOD
 */
public class DateHelperTest 
{
    
    @BeforeClass
    public static void setUpClass() 
    {
        assertNotNull(DateHelper.getFormattedDate());
    }
    
    @Test
    public void test()
    {
        assertNotNull(DateHelper.getFormattedDate());
        assertNotNull(DateHelper.getFormattedDate(DateHelper.FORMAT_1));
        assertNotNull(DateHelper.getFormattedDate(DateHelper.FORMAT_2));
        assertNotNull(DateHelper.getFormattedDate(DateHelper.FORMAT_3));
        assertNotNull(DateHelper.getFormattedDate(DateHelper.FORMAT_4));
        
        Calendar calendar = Calendar.getInstance();
        
        assertNotNull(DateHelper.getFormattedDate(DateHelper.FORMAT_1), calendar);
        assertNotNull(DateHelper.getFormattedDate(DateHelper.FORMAT_2), calendar);
        assertNotNull(DateHelper.getFormattedDate(DateHelper.FORMAT_3), calendar);
        assertNotNull(DateHelper.getFormattedDate(DateHelper.FORMAT_4), calendar);
        
        Date date = new Date();
        
        assertNotNull(DateHelper.getFormattedDate(DateHelper.FORMAT_1), date);
        assertNotNull(DateHelper.getFormattedDate(DateHelper.FORMAT_2), date);
        assertNotNull(DateHelper.getFormattedDate(DateHelper.FORMAT_3), date);
        assertNotNull(DateHelper.getFormattedDate(DateHelper.FORMAT_4), date);
        
        long delay = 1000000;
        
        assertNotNull(DateHelper.getFormattedDate(DateHelper.FORMAT_1), delay);
        assertNotNull(DateHelper.getFormattedDate(DateHelper.FORMAT_2), delay);
        assertNotNull(DateHelper.getFormattedDate(DateHelper.FORMAT_3), delay);
        assertNotNull(DateHelper.getFormattedDate(DateHelper.FORMAT_4), delay);
    }
}