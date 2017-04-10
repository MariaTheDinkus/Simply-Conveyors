package com.momnop.simplyconveyors;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLMissingMappingsEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.momnop.simplyconveyors.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.blocks.tiles.TileAdvancedConveyor;
import com.momnop.simplyconveyors.blocks.tiles.TileBusStop;
import com.momnop.simplyconveyors.blocks.tiles.TileModularConveyor;
import com.momnop.simplyconveyors.entity.ModEntities;
import com.momnop.simplyconveyors.event.SimplyConveyorsEventHandler;
import com.momnop.simplyconveyors.handlers.GuiHandler;
import com.momnop.simplyconveyors.handlers.RecipeHandler;
import com.momnop.simplyconveyors.info.ModInfo;
import com.momnop.simplyconveyors.items.SimplyConveyorsItems;
import com.momnop.simplyconveyors.network.PacketDispatcher;
import com.momnop.simplyconveyors.proxies.CommonProxy;

@Mod(name = ModInfo.NAME, modid = ModInfo.MOD_ID, version = ModInfo.VERSION)
public class SimplyConveyors
{
	@Instance(value = ModInfo.MOD_ID)
	public static SimplyConveyors INSTANCE;
	
	@SidedProxy(clientSide = "com.momnop.simplyconveyors.proxies.ClientProxy", serverSide = "com.momnop.simplyconveyors.proxies.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void onMissingMapping(FMLMissingMappingsEvent event)
	{
		for(FMLMissingMappingsEvent.MissingMapping mapping : event.get())
		{
			String resourcePath = mapping.resourceLocation.getResourcePath();
			if(mapping.type == GameRegistry.Type.BLOCK)
			{
				if("conveyor_fastest".equals(resourcePath)) {
					mapping.remap(SimplyConveyorsBlocks.conveyor_fast);
				} else if ("conveyor_vertical_fastest".equals(resourcePath)) {
					mapping.remap(SimplyConveyorsBlocks.conveyor_vertical_fast);
				} else if ("conveyor_vertical_fastest_down".equals(resourcePath)) {
					mapping.remap(SimplyConveyorsBlocks.conveyor_vertical_fast_down);
				} else if ("conveyor_backwards_fastest".equals(resourcePath)) {
					mapping.remap(SimplyConveyorsBlocks.conveyor_inverse_fast);
				}
			} else if (mapping.type == GameRegistry.Type.ITEM) {
				if("conveyor_fastest".equals(resourcePath)) {
					mapping.remap(Item.getItemFromBlock(SimplyConveyorsBlocks.conveyor_fast));
				} else if ("conveyor_vertical_fastest".equals(resourcePath)) {
					mapping.remap(Item.getItemFromBlock(SimplyConveyorsBlocks.conveyor_vertical_fast));
				} else if ("conveyor_vertical_fastest_down".equals(resourcePath)) {
					mapping.remap(Item.getItemFromBlock(SimplyConveyorsBlocks.conveyor_vertical_fast_down));
				} else if ("conveyor_backwards_fastest".equals(resourcePath)) {
					mapping.remap(Item.getItemFromBlock(SimplyConveyorsBlocks.conveyor_inverse_fast));
				} else if ("conveyor_transporter".equals(resourcePath)) {
					mapping.remap(SimplyConveyorsItems.transporter_module);
				} else if ("conveyor_foam_slow".equals(resourcePath)) {
					mapping.remap(SimplyConveyorsItems.sponge_track);
				} else if ("conveyor_foam_fast".equals(resourcePath)) {
					mapping.remap(SimplyConveyorsItems.sponge_track);
				} else if ("conveyor_foam_fastest".equals(resourcePath)) {
					mapping.remap(SimplyConveyorsItems.sponge_track);
				} else if ("conveyor_spike_slow".equals(resourcePath)) {
					mapping.remap(SimplyConveyorsItems.iron_spike_module);
				} else if ("conveyor_spike_fast".equals(resourcePath)) {
					mapping.remap(SimplyConveyorsItems.gold_spike_module);
				} else if ("conveyor_spike_fastest".equals(resourcePath)) {
					mapping.remap(SimplyConveyorsItems.diamond_spike_module);
				}
			}
		}
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		SimplyConveyorsBlocks.load();
		SimplyConveyorsItems.load();
		RecipeHandler.loadRecipes();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		PacketDispatcher.registerPackets();
		
		proxy.registerModels();
		
		ModEntities.init();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(SimplyConveyors.INSTANCE, new GuiHandler());
		
		GameRegistry.registerTileEntity(TileAdvancedConveyor.class, "tile_advanced_conveyor");
		GameRegistry.registerTileEntity(TileModularConveyor.class, "tile_modular_conveyor");
		GameRegistry.registerTileEntity(TileBusStop.class, "tile_bus_Stop");
		
		MinecraftForge.EVENT_BUS.register(new SimplyConveyorsEventHandler());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
	
	@EventHandler
	public void serverLoad(FMLServerStartedEvent event) {
		
	}
}
