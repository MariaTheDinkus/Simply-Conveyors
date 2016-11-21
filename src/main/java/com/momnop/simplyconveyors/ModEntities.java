package com.momnop.simplyconveyors;

import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry.EntityRegistration;

import com.google.common.collect.Maps;
import com.momnop.simplyconveyors.entity.EntityBlock;
import com.momnop.simplyconveyors.entity.EntityBus;

public class ModEntities
{
	public static final Map<Integer, EntityEggInfo> entityEggs = Maps.<Integer, EntityEggInfo>newLinkedHashMap();
    public static final Map<Integer, String> idToEntityName = Maps.<Integer, String>newLinkedHashMap();

    private static int nextEntityId = 1;
    
    public static void init()
    {
    	int modEntityIndex = 0;
    	
    	EntityRegistry.registerModEntity(EntityBlock.class, "entityBlock", ++modEntityIndex, SimplyConveyors.INSTANCE, 64, 10, true);
    	EntityRegistry.registerModEntity(EntityBus.class, "entityBus", ++modEntityIndex, SimplyConveyors.INSTANCE, 64, 10, true);
    }
    
    
}