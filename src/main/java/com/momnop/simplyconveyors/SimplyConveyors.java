package com.momnop.simplyconveyors;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.momnop.simplyconveyors.client.ClientEventHandler;
import com.momnop.simplyconveyors.common.CommonProxy;
import com.momnop.simplyconveyors.common.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.common.blocks.tiles.TileAdvancedConveyor;
import com.momnop.simplyconveyors.common.blocks.tiles.TileModularConveyor;
import com.momnop.simplyconveyors.common.event.CommonEventHandler;
import com.momnop.simplyconveyors.common.handlers.ConfigHandler;
import com.momnop.simplyconveyors.common.handlers.GuiHandler;
import com.momnop.simplyconveyors.common.info.ModInfo;
import com.momnop.simplyconveyors.common.items.SimplyConveyorsItems;
import com.momnop.simplyconveyors.common.network.PacketDispatcher;

@Mod(name = ModInfo.NAME, modid = ModInfo.MOD_ID, version = ModInfo.VERSION, acceptedMinecraftVersions = "[1.12,1.12.1,1.12.2]")
public class SimplyConveyors
{
	@Instance(value = ModInfo.MOD_ID)
	public static SimplyConveyors INSTANCE;

	@SidedProxy(clientSide = "com.momnop.simplyconveyors.client.ClientProxy", serverSide = "com.momnop.simplyconveyors.common.CommonProxy")
	public static CommonProxy proxy;

	public static CreativeTabs tabGeneral = new CreativeTabs(ModInfo.MOD_ID) {
        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {
            return new ItemStack(SimplyConveyorsBlocks.conveyor_slow);
        }
    };
    
    public static CreativeTabs tabRoads = new CreativeTabs(ModInfo.MOD_ID + "_roads") {
        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {
            return new ItemStack(SimplyConveyorsBlocks.road_full);
        }
    };
    
    public static CreativeTabs tabSpecial = new CreativeTabs(ModInfo.MOD_ID + "_special") {
        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {
            return new ItemStack(SimplyConveyorsBlocks.conveyor_vertical_slow);
        }
    };
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		ConfigHandler.init(event.getSuggestedConfigurationFile());
		
		MinecraftForge.EVENT_BUS.register(new SimplyConveyorsBlocks());
		MinecraftForge.EVENT_BUS.register(new SimplyConveyorsItems());
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		PacketDispatcher.registerPackets();

		proxy.registerModels();

		NetworkRegistry.INSTANCE.registerGuiHandler(SimplyConveyors.INSTANCE, new GuiHandler());

		GameRegistry.registerTileEntity(TileAdvancedConveyor.class, "tile_advanced_conveyor");
		GameRegistry.registerTileEntity(TileModularConveyor.class, "tile_modular_conveyor");
		
		MinecraftForge.EVENT_BUS.register(new CommonEventHandler());
		
		if (event.getSide() == Side.CLIENT) {
			MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
		}
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{

	}

	@EventHandler
	public void serverLoad(FMLServerStartedEvent event)
	{

	}

	public static String appendId(String string)
	{
		return String.join(":", ModInfo.MOD_ID, string);
	}
}
