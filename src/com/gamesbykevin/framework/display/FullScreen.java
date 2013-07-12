package com.gamesbykevin.framework.display;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FullScreen 
{
    private JFrame frame = new JFrame(); 
    private Container parent;
    private boolean fullscreen = false;
    
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
    
    public boolean isFullScreen()
    {
        return this.fullscreen;
    }
    
    public void switchFullScreen(JApplet applet, JPanel panel)
    {
        if (!fullscreen)
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

            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            
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
            frame.setSize(dimension.width, dimension.height);
            
            GraphicsEnvironment graphicsenvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice agraphicsdevice[] = graphicsenvironment.getScreenDevices();
            agraphicsdevice[0].setFullScreenWindow(frame);            
            
            fullscreen = true;      
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
            fullscreen = false;
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