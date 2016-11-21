package com.momnop.simplyconveyors;

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
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import com.momnop.simplyconveyors.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.blocks.bus.tiles.TileEntityBusStop;
import com.momnop.simplyconveyors.blocks.conveyors.tiles.TileEntityGrabberPath;
import com.momnop.simplyconveyors.client.render.blocks.BlockRenderRegister;
import com.momnop.simplyconveyors.client.render.guis.SimplyConveyorsGuiHandler;
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
        ModEntities.init();
        proxy.preInitRenders();
        proxy.initRenders();
        proxy.initTiles();
        RecipeHandler.doRecipes();
        
        ForgeChunkManager.setForcedChunkLoadingCallback(SimplyConveyors.INSTANCE, null);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	NetworkRegistry.INSTANCE.registerGuiHandler(SimplyConveyors.INSTANCE, (IGuiHandler) new SimplyConveyorsGuiHandler());
    	PacketDispatcher.registerPackets();
    	GameRegistry.registerTileEntity(TileEntityBusStop.class, "tileEntityBusStop");
    	GameRegistry.registerTileEntity(TileEntityGrabberPath.class, "tileEntityGrabberPath");
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
