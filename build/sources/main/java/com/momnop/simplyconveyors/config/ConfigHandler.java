package com.momnop.simplyconveyors.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler
{
    public static Configuration configuration;
    public static int normalConveyorRecipeAmount = 8;
    
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
    	normalConveyorRecipeAmount = configuration.getInt("Amount Normal Conveyor Recipes Give", Configuration.CATEGORY_GENERAL, normalConveyorRecipeAmount, 1, 64, "Change amount of conveyors you get from the normal conveyor recipes. (Non-Vertical, not Upside-Down)");
        if (configuration.hasChanged())
        {
            configuration.save();
        }
    }
}