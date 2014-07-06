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
    
    private GraphicsEnvironment environment;
    private GraphicsDevice[] device;
    
    public FullScreen()
    {
        this.environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        this.device = environment.getScreenDevices();
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
    
    public void switchFullScreen(JApplet applet)
    {
        if (!enabled)
        {
            if (parent == null)
                parent = applet.getParent();

            dimension = Toolkit.getDefaultToolkit().getScreenSize();
            
            frame.setUndecorated(true);
            frame.add(applet);
            frame.setVisible(true);
            frame.setSize(dimension);
            
            device[0].setFullScreenWindow(frame);            
            
            enabled = true;      
        }
        else
        {
            parent.add(applet);
            frame.dispose();
            enabled = false;
        }
        
        applet.setBounds(0, 0, applet.getParent().getSize().width, applet.getParent().getSize().height);
        applet.requestFocus();
   }
    
    public void switchFullScreen(JPanel panel)
    {
        if (!enabled)
        {
            if (parent == null)
                parent = panel.getParent();

            dimension = Toolkit.getDefaultToolkit().getScreenSize();
            
            frame.setUndecorated(true);
            frame.add(panel);
            frame.setVisible(true);
            frame.setSize(dimension);
            
            device[0].setFullScreenWindow(frame);            
            
            enabled = true;      
        }
        else
        {
            parent.add(panel);
            frame.dispose();
            enabled = false;
        }
        
        panel.setBounds(0, 0, panel.getParent().getSize().width, panel.getParent().getSize().height);
        panel.requestFocus();
   }
}