package com.momnop.simplyconveyors.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler
{
    public static Configuration configuration;
    public static float tier1Speed = 0.2F;
    public static float tier2Speed = 0.4F;
    public static float tier3Speed = 0.7F;
    
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
    	tier1Speed = configuration.getFloat("Tier One Speed", Configuration.CATEGORY_GENERAL, tier1Speed, 0F, 32F, "Change speed of Tier One conveyors.");
    	tier2Speed = configuration.getFloat("Tier Two Speed", Configuration.CATEGORY_GENERAL, tier2Speed, 0F, 32F, "Change speed of Tier Two conveyors.");
    	tier3Speed = configuration.getFloat("Tier Three Speed", Configuration.CATEGORY_GENERAL, tier3Speed, 0F, 32F, "Change speed of Tier Three conveyors.");
    	normalConveyorRecipeAmount = configuration.getInt("Amount Normal Conveyor Recipes Give", Configuration.CATEGORY_GENERAL, normalConveyorRecipeAmount, 1, 64, "Change amount of conveyors you get from the normal conveyor recipes. (Non-Vertical, not Upside-Down)");
        if (configuration.hasChanged())
        {
            configuration.save();
        }
    }
}