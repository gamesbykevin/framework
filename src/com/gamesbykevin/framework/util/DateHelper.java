package com.gamesbykevin.framework.util;

import java.text.*;
import java.util.*;

public final class DateHelper
{
    /*
    LEGEND HOW TO FORMAT DATE
    Letter 	Date or Time Component 	Presentation            Examples
    G           Era designator          Text                    AD
    y           Year                    Year                    1996; 96
    M           Month in year           Month                   July; Jul; 07
    w           Week in year            Number                  27
    W           Week in month           Number                  2
    D           Day in year             Number                  189
    d           Day in month            Number                  10
    F           Day of week in month 	Number                  2
    E           Day in week             Text                    Tuesday; Tue
    a           Am/pm marker            Text                    PM
    H           Hour in day (0-23) 	Number                  0
    k           Hour in day (1-24) 	Number                  24
    K           Hour in am/pm (0-11) 	Number                  0
    h           Hour in am/pm (1-12) 	Number                  12
    m           Minute in hour          Number                  30
    s           Second in minute 	Number                  55
    S           Millisecond             Number                  978
    z           Time zone               General time zone 	Pacific Standard Time; PST; GMT-08:00
    Z           Time zone               RFC 822 time zone 	-0800    
    */
    
    /**
     * Date format "MM/dd/yyyy HH:mm:ss.SSS"
     */
    public static final String FORMAT_1 = "MM/dd/yyyy HH:mm:ss.SSS";
    
    /**
     * Date format "MM/dd/yyyy HH:mm:ss"
     */
    public static final String FORMAT_2 = "MM/dd/yyyy HH:mm:ss";
    
    /**
     * Date format "HH:mm:ss"
     */
    public static final String FORMAT_3 = "HH:mm:ss";
    
    /**
     * Date format "HH:mm:ss.SSS"
     */
    public static final String FORMAT_4 = "HH:mm:ss.SSS";
    
    /**
     * Get formatted date of the current time
     * @return The current time format as "MM/dd/yyyy HH:mm:ss.SSS"
     */
    public static final String getFormattedDate()
    {   
        return getFormattedDate(FORMAT_1);
    }
    
    /**
     * Get the formatted date of the current time
     * @param dateFormat The desired date format
     * @return The current time in the specified format
     */
    public static final String getFormattedDate(String dateFormat)
    {
        return getFormattedDate(dateFormat, Calendar.getInstance());
    }
    
    /**
     * Get the formatted date of the specified time
     * @param dateFormat The format we want to see our result
     * @param calendar Object containing the desired time
     * @return The current time in the specified format
     */
    public static synchronized String getFormattedDate(String dateFormat, Calendar calendar)
    {
        return getFormattedDate(dateFormat, calendar.getTime());
    }
    
    /**
     * Get the formatted date of the specified time
     * @param dateFormat The format we want to see our result
     * @param date Object containing the desired time
     * @return The current time in the specified format
     */
    public static synchronized String getFormattedDate(final String dateFormat, final Date date)
    {
        return getFormattedDate(dateFormat, date.getTime());
    }
    
    /**
     * Get formatted date
     * @param dateFormat The format we want to see our result
     * @param milliSecondsElapsed Time elapsed in milliseconds
     * @return The formatted date
     */
    public static synchronized String getFormattedDate(String dateFormat, long milliSecondsElapsed)
    {
        //create new date format
        DateFormat df = new SimpleDateFormat(dateFormat);
        
        //set the assume UTC
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        
        //return the formatted date
        return df.format(milliSecondsElapsed);
    }
}