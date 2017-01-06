package com.momnop.simplyconveyors;

import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLMissingMappingsEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import com.momnop.simplyconveyors.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.blocks.bus.tiles.TileEntityBusStop;
import com.momnop.simplyconveyors.blocks.conveyors.tiles.TileEntityDetectorBackwardsPath;
import com.momnop.simplyconveyors.blocks.conveyors.tiles.TileEntityDetectorPath;
import com.momnop.simplyconveyors.blocks.conveyors.tiles.TileEntityGrabberPath;
import com.momnop.simplyconveyors.blocks.conveyors.tiles.TileEntityTransporterPath;
import com.momnop.simplyconveyors.client.render.blocks.BlockRenderRegister;
import com.momnop.simplyconveyors.client.render.guis.SimplyConveyorsGuiHandler;
import com.momnop.simplyconveyors.config.ConfigHandler;
import com.momnop.simplyconveyors.events.SimplyConveyorsEventHandler;
import com.momnop.simplyconveyors.info.BlockInfoOld;
import com.momnop.simplyconveyors.info.ModInfo;
import com.momnop.simplyconveyors.items.SimplyConveyorsItems;
import com.momnop.simplyconveyors.network.PacketDispatcher;
import com.momnop.simplyconveyors.proxies.CommonProxy;
import com.momnop.simplyconveyors.recipes.RecipeHandler;

@Mod(name = ModInfo.NAME, modid = ModInfo.MODID, version = ModInfo.VERSION, acceptedMinecraftVersions = "[1.9,1.12)")
public class SimplyConveyors
{
	@SidedProxy(clientSide = "com.momnop.simplyconveyors.proxies.ClientProxy", serverSide = "com.momnop.simplyconveyors.proxies.CommonProxy")
	public static CommonProxy proxy;

	@Instance(value = ModInfo.MODID)
	public static SimplyConveyors INSTANCE;

	@EventHandler
	public void onMissingMapping(FMLMissingMappingsEvent event)
	{
		for(FMLMissingMappingsEvent.MissingMapping mapping : event.get())
		{
			String resourcePath = mapping.resourceLocation.getResourcePath();
			if(mapping.type == GameRegistry.Type.BLOCK)
			{
				if(BlockInfoOld.BACKWARDS_DETECTOR.equals(resourcePath))
				{
					mapping.remap(SimplyConveyorsBlocks.blockDetectorMovingBackwardsPath);
				}
				else if(BlockInfoOld.BACKWARDS_DROPPER.equals(resourcePath))
				{
					mapping.remap(SimplyConveyorsBlocks.blockDropperMovingBackwardsPath);
				}
				else if(BlockInfoOld.BLOCK_CONVEYOR.equals(resourcePath))
				{
					mapping.remap(SimplyConveyorsBlocks.blockBlockMovingPath);
				}
				else if(BlockInfoOld.DETECTOR_CONVEYOR.equals(resourcePath))
				{
					mapping.remap(SimplyConveyorsBlocks.blockDetectorMovingPath);
				}
				else if(BlockInfoOld.DROPPER_MOVING_PATH_UNLOCALIZED_NAME.equals(resourcePath))
				{
					mapping.remap(SimplyConveyorsBlocks.blockDropperMovingPath);
				}
				else if(BlockInfoOld.FAST_MOVING_BACKWARDS_PATH_UNLOCALIZED_NAME.equals(resourcePath))
				{
					mapping.remap(SimplyConveyorsBlocks.blockFastMovingBackwardsPath);
				}
				else if(BlockInfoOld.FAST_MOVING_PATH_UNLOCALIZED_NAME.equals(resourcePath))
				{
					mapping.remap(SimplyConveyorsBlocks.blockFastMovingPath);
				}
				else if(BlockInfoOld.FAST_MOVING_STAIR_PATH_UNLOCALIZED_NAME.equals(resourcePath))
				{
					mapping.remap(SimplyConveyorsBlocks.blockFastMovingStairPath);
				}
				else if(BlockInfoOld.FAST_MOVING_VERTICAL_PATH_UNLOCALIZED_NAME.equals(resourcePath))
				{
					mapping.remap(SimplyConveyorsBlocks.blockFastMovingVerticalPath);
				}
				else if(BlockInfoOld.FASTEST_MOVING_BACKWARDS_PATH_UNLOCALIZED_NAME.equals(resourcePath))
				{
					mapping.remap(SimplyConveyorsBlocks.blockFastestMovingBackwardsPath);
				}
				else if(BlockInfoOld.FASTEST_MOVING_PATH_UNLOCALIZED_NAME.equals(resourcePath))
				{
					mapping.remap(SimplyConveyorsBlocks.blockFastestMovingPath);
				}
				else if(BlockInfoOld.FASTEST_MOVING_STAIR_PATH_UNLOCALIZED_NAME.equals(resourcePath))
				{
					mapping.remap(SimplyConveyorsBlocks.blockFastestMovingStairPath);
				}
				else if(BlockInfoOld.FASTEST_MOVING_VERTICAL_PATH_UNLOCALIZED_NAME.equals(resourcePath))
				{
					mapping.remap(SimplyConveyorsBlocks.blockFastestMovingVerticalPath);
				}
				else if(BlockInfoOld.GRABBER_CONVEYOR.equals(resourcePath))
				{
					mapping.remap(SimplyConveyorsBlocks.blockGrabberMovingPath);
				}
				else if(BlockInfoOld.HOLDING_MOVING_BACKWARDS_PATH_UNLOCALIZED_NAME.equals(resourcePath))
				{
					mapping.remap(SimplyConveyorsBlocks.blockHoldingMovingBackwardsPath);
				}
				else if(BlockInfoOld.HOLDING_MOVING_PATH_UNLOCALIZED_NAME.equals(resourcePath))
				{
					mapping.remap(SimplyConveyorsBlocks.blockHoldingMovingPath);
				}
				else if(BlockInfoOld.SLOW_MOVING_BACKWARDS_PATH_UNLOCALIZED_NAME.equals(resourcePath))
				{
					mapping.remap(SimplyConveyorsBlocks.blockSlowMovingBackwardsPath);
				}
				else if(BlockInfoOld.SLOW_MOVING_PATH_UNLOCALIZED_NAME.equals(resourcePath))
				{
					mapping.remap(SimplyConveyorsBlocks.blockSlowMovingPath);
				}
				else if(BlockInfoOld.SLOW_MOVING_STAIR_PATH_UNLOCALIZED_NAME.equals(resourcePath))
				{
					mapping.remap(SimplyConveyorsBlocks.blockSlowMovingStairPath);
				}
				else if(BlockInfoOld.SLOW_MOVING_VERTICAL_PATH_UNLOCALIZED_NAME.equals(resourcePath))
				{
					mapping.remap(SimplyConveyorsBlocks.blockSlowMovingVerticalPath);
				}
				else if(BlockInfoOld.TRANSPORTER_CONVEYOR.equals(resourcePath))
				{
					mapping.remap(SimplyConveyorsBlocks.blockTransporterMovingPath);
				}
				else if(BlockInfoOld.TRAPDOOR_CONVEYOR.equals(resourcePath))
				{
					mapping.remap(SimplyConveyorsBlocks.blockTrapDoorMovingPath);
				}
			}
			else if(mapping.type == GameRegistry.Type.ITEM)
			{
				if("conveyorWrench".equals(resourcePath))
				{
					mapping.remap(SimplyConveyorsItems.wrench);
				}
				else if("entityFilter".equals(resourcePath))
				{
					mapping.remap(SimplyConveyorsItems.entityFilter);
				}
			}
		}
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		ConfigHandler.init(event.getSuggestedConfigurationFile());

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
		GameRegistry.registerTileEntity(TileEntityDetectorPath.class, "tileEntityDetectorPath");
		GameRegistry.registerTileEntity(TileEntityDetectorBackwardsPath.class, "tileEntityDetectorBackwardsPath");
		GameRegistry.registerTileEntity(TileEntityTransporterPath.class, "tileEntityTransporterPath");
		MinecraftForge.EVENT_BUS.register(new SimplyConveyorsEventHandler());
		if(event.getSide() == Side.CLIENT)
		{
			BlockRenderRegister.registerBlockRenderer();
		}
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{

	}

	/**
	 * @param event
	 *            The event that triggered the method
	 */
	@EventHandler
	public static void serverLoad(FMLServerStartingEvent event)
	{

	}
}
