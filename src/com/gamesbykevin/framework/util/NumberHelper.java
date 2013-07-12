package com.gamesbykevin.framework.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class NumberHelper 
{   //this class can format a given number including rounding capabilities
    
    public enum Format
    {
        Currency, Comma
    }
    
    public static String getFormatValue(double value, Format format)
    {
        DecimalFormat df = null;
        
        switch(format)
        {
            case Currency:  //value as money
                df = new DecimalFormat("#,###.00");
                break;

            case Comma:     //value comma friendly
                df = new DecimalFormat("#,###");
                break;

            default:
                break;
        }
        
        return df.format(value);
    }
    
    public static String round(double value)
    {
        return round(value, 0);
    }
    
    public static String round(double value, int numDecimalPlaces)
    {
        String formatStr = "#";
        
        if (numDecimalPlaces > 0)
        {
            formatStr += ".";
            
            for (int i=0; i < numDecimalPlaces; i++)
            {
                formatStr += "0";
            }
        }
        
        DecimalFormat tmp = new DecimalFormat(formatStr);
        tmp.setRoundingMode(RoundingMode.UP);
        return tmp.format(value);
    }
}