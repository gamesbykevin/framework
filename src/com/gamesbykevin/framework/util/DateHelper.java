package com.gamesbykevin.framework.util;

import java.text.*;
import java.util.*;

public class DateHelper 
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
    
    public static synchronized String getFormatedDate()
    {   
        return getFormatedDate(FORMAT_1);
    }
    
    public static synchronized String getFormatedDate(String dateFormat)
    {
        return getFormatedDate(dateFormat, Calendar.getInstance());
    }
    
    public static synchronized String getFormatedDate(String dateFormat, Calendar calendar)
    {
        return getFormatedDate(dateFormat, calendar.getTime());
    }
    
    public static synchronized String getFormatedDate(String dateFormat, long milliSecondsElapsed)
    {   //call this method when you want to convert how much time has passed all other methods convert date to a specific format
        DateFormat df = new SimpleDateFormat(dateFormat);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df.format(milliSecondsElapsed);
    }
    
    public static synchronized String getFormatedDate(String dateFormat, Date date)
    {
        return new SimpleDateFormat(dateFormat).format(date.getTime());
    }
}