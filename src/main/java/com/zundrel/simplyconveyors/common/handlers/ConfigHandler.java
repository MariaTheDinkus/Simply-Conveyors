package com.zundrel.simplyconveyors.common.handlers;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler
{
	public static Configuration configuration;
    public static boolean compass = false;
    public static boolean stopWhileSneaking = true;
    
    public static void init(File file)
    {
        if (configuration == null)
        {
            configuration = new Configuration(file);
            loadConfiguration();
        }
    }
    
    public static void loadConfiguration()
    {	
    	compass = configuration.getBoolean("Enable the Compass", Configuration.CATEGORY_GENERAL, compass, "Enables the compass which shows the direction you are going while sneaking");
    	stopWhileSneaking = configuration.getBoolean("Stop Pushing While Sneaking", Configuration.CATEGORY_GENERAL, stopWhileSneaking, "Makes conveyors stop pushing you while you are sneaking");
    	if (configuration.hasChanged())
        {
            configuration.save();
        }
    }
}
