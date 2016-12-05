package com.momnop.simplyconveyors;

import java.util.Map;

import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import com.google.common.collect.Maps;
import com.momnop.simplyconveyors.entity.EntityBlock;

public class ModEntities
{
	public static final Map<Integer, EntityEggInfo> entityEggs = Maps.<Integer, EntityEggInfo>newLinkedHashMap();
    public static final Map<Integer, String> idToEntityName = Maps.<Integer, String>newLinkedHashMap();

    private static int nextEntityId = 1;
    
    public static void init()
    {
    	int modEntityIndex = 0;
    	
    	EntityRegistry.registerModEntity(new ResourceLocation("simplyconveyors", "entityBlock"), EntityBlock.class, "entityBlock", ++modEntityIndex, SimplyConveyors.INSTANCE, 64, 10, true);
    	//EntityRegistry.registerModEntity(EntityBus.class, "entityBus", ++modEntityIndex, SimplyConveyors.INSTANCE, 64, 10, true, 0xFFFFFF, 0xFFFFFF);
    }
    
    
}