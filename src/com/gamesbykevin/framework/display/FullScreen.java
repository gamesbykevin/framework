package com.gamesbykevin.framework.display;

import java.awt.*;
import javax.swing.*;

public class FullScreen 
{
    //the container for our full screen 
    private JFrame frame = new JFrame(); 
    
    //original container
    private Container parent;
    
    //has full screen been enabled
    private boolean enabled = false;
    
    //width-height of the full screen
    private Dimension dimension;
    
    public FullScreen()
    {
        
    }
    
    public void dispose()
    {
        if (frame != null)
            frame.dispose();
        
        frame = null;
        
        if (parent != null)
            parent.removeAll();
        
        parent = null;
    }
    
    /**
     * Is full screen enabled
     * 
     * @return boolean
     */
    public boolean isEnabled()
    {
        return this.enabled;
    }
    
    public void switchFullScreen(JApplet applet, JPanel panel)
    {
        if (!enabled)
        {
            if (parent == null)
            {
                if (applet != null)
                {
                    parent = applet.getParent();
                }
                else
                {
                    parent = panel.getParent();
                }
            }

            dimension = Toolkit.getDefaultToolkit().getScreenSize();
            
            frame.setUndecorated(true);
            
            if (applet != null)
            {
                frame.add(applet);
            }
            else
            {
                frame.add(panel);
            }
            
            frame.setVisible(true);
            frame.setSize(dimension);
            
            GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice[] device = environment.getScreenDevices();
            device[0].setFullScreenWindow(frame);            
            
            enabled = true;      
        }
        else
        {
            if (applet != null)
            {
                parent.add(applet);
            }
            else
            {
                parent.add(panel);
            }
            
            frame.dispose();
            enabled = false;
        }
        
        if (applet != null)
        {
            applet.setBounds(0, 0, applet.getParent().getSize().width, applet.getParent().getSize().height);
            applet.requestFocus();
        }
        else
        {
            panel.setBounds(0, 0, panel.getParent().getSize().width, panel.getParent().getSize().height);
            panel.requestFocus();
        }
   }
}