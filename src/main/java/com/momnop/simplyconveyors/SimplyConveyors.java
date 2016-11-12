package com.momnop.simplyconveyors;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import com.momnop.interdictionpillar.InterdictionPillar;
import com.momnop.simplyconveyors.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.blocks.bus.tiles.TileEntityBusStop;
import com.momnop.simplyconveyors.client.render.blocks.BlockRenderRegister;
import com.momnop.simplyconveyors.events.SimplyConveyorsEventHandler;
import com.momnop.simplyconveyors.info.ModInfo;
import com.momnop.simplyconveyors.items.SimplyConveyorsItems;
import com.momnop.simplyconveyors.network.PacketDispatcher;
import com.momnop.simplyconveyors.proxies.CommonProxy;
import com.momnop.simplyconveyors.recipes.RecipeHandler;

@Mod(name = ModInfo.NAME, modid = ModInfo.MODID, version = ModInfo.VERSION)
public class SimplyConveyors
{
    @SidedProxy(clientSide = "com.momnop.simplyconveyors.proxies.ClientProxy", serverSide = "com.momnop.simplyconveyors.proxies.CommonProxy")
    public static CommonProxy proxy;
    
    @Instance(value = ModInfo.MODID)
    public static SimplyConveyors INSTANCE;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        SimplyConveyorsBlocks.load();
        SimplyConveyorsItems.load();
        proxy.initRenders();
        proxy.initTiles();
        RecipeHandler.doRecipes();
        
        ForgeChunkManager.setForcedChunkLoadingCallback(SimplyConveyors.INSTANCE, null);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	PacketDispatcher.registerPackets();
    	GameRegistry.registerTileEntity(TileEntityBusStop.class, "tileEntityBusStop");
        MinecraftForge.EVENT_BUS.register(new SimplyConveyorsEventHandler());
        if (event.getSide() == Side.CLIENT) {
        	BlockRenderRegister.registerBlockRenderer();
        }
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        
    }
    
    /**
     * @param event
     *        The event that triggered the method
     */
    @EventHandler
    public static void serverLoad(FMLServerStartingEvent event)
    {
    	
    }
}
