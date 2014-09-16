package com.gamesbykevin.framework.util;

import com.gamesbykevin.framework.resources.Disposable;

import java.text.*;
import java.util.*;

public final class DateHelper implements Disposable 
{
    /*
     * LEGEND HOW TO FORMAT DATE
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
    
    public static final String FORMAT_1 = "MM/dd/yyyy HH:mm:ss.SSS";
    public static final String FORMAT_2 = "MM/dd/yyyy HH:mm:ss";
    public static final String FORMAT_3 = "HH:mm:ss";
    public static final String FORMAT_4 = "HH:mm:ss.SSS";
    
    //date format object
    private DateFormat df;
    
    @Override
    public void dispose()
    {
        df = null;
    }
    
    /**
     * Get formatted date of the current time
     * @return The current time format as "MM/dd/yyyy HH:mm:ss.SSS"
     */
    public final String getFormatedDate()
    {   
        return getFormatedDate(FORMAT_1);
    }
    
    public final String getFormatedDate(String dateFormat)
    {
        return getFormatedDate(dateFormat, Calendar.getInstance());
    }
    
    public synchronized String getFormatedDate(String dateFormat, Calendar calendar)
    {
        return getFormatedDate(dateFormat, calendar.getTime());
    }
    
    /**
     * Get formatted date
     * @param dateFormat The format we want to see our result
     * @param milliSecondsElapsed Time elapsed in milliseconds
     * @return The formatted date
     */
    public synchronized String getFormatedDate(String dateFormat, long milliSecondsElapsed)
    {   //call this method when you want to convert how much time has passed all other methods convert date to a specific format
        if (df == null)
        {
            df = new SimpleDateFormat(dateFormat);
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
        }
        
        return df.format(milliSecondsElapsed);
    }
    
    public synchronized String getFormatedDate(String dateFormat, Date date)
    {
        return new SimpleDateFormat(dateFormat).format(date.getTime());
    }
}