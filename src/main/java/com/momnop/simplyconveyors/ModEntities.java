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

public class ModEntities
{
	public static final Map<Integer, EntityEggInfo> entityEggs = Maps.<Integer, EntityEggInfo>newLinkedHashMap();
    public static final Map<Integer, String> idToEntityName = Maps.<Integer, String>newLinkedHashMap();

    private static int nextEntityId = 1;
    
    public static void init()
    {
    	registerEntity(EntityBlock.class, "entityBlock", 64, 10, true);
    }
    
    // register an entity
    public static int registerEntity(Class<? extends Entity> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
    {
        int entityId = nextEntityId;
        nextEntityId++;
        EntityRegistry.registerModEntity(entityClass, entityName, entityId, SimplyConveyors.INSTANCE, trackingRange, updateFrequency, sendsVelocityUpdates);
        idToEntityName.put(entityId, entityName);
        return entityId;
    }
    
    public static Entity createEntityByID(int tanEntityId, World worldIn)
    {
        Entity entity = null;
        ModContainer mc = FMLCommonHandler.instance().findContainerFor(SimplyConveyors.INSTANCE);
        EntityRegistration er = EntityRegistry.instance().lookupModSpawn(mc, tanEntityId);
        if (er != null)
        {
            Class<? extends Entity> clazz = er.getEntityClass();
            try
            {
                if (clazz != null)
                {
                    entity = (Entity)clazz.getConstructor(new Class[] {World.class}).newInstance(new Object[] {worldIn});
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }            
        }       
        return entity;
    }
    
    
}