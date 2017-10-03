package com.zundrel.simplyconveyors;

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

import com.zundrel.simplyconveyors.client.ClientEventHandler;
import com.zundrel.simplyconveyors.common.CommonProxy;
import com.zundrel.simplyconveyors.common.blocks.SimplyConveyorsBlocks;
import com.zundrel.simplyconveyors.common.blocks.tiles.TileAdvancedConveyor;
import com.zundrel.simplyconveyors.common.blocks.tiles.TileModularConveyor;
import com.zundrel.simplyconveyors.common.event.CommonEventHandler;
import com.zundrel.simplyconveyors.common.handlers.ConfigHandler;
import com.zundrel.simplyconveyors.common.handlers.GuiHandler;
import com.zundrel.simplyconveyors.common.info.ModInfo;
import com.zundrel.simplyconveyors.common.items.SimplyConveyorsItems;
import com.zundrel.simplyconveyors.common.network.PacketDispatcher;

@Mod(name = ModInfo.NAME, modid = ModInfo.MOD_ID, version = ModInfo.VERSION, acceptedMinecraftVersions = "[1.12,1.12.1,1.12.2]")
public class SimplyConveyors
{
	@Instance(value = ModInfo.MOD_ID)
	public static SimplyConveyors INSTANCE;

	@SidedProxy(clientSide = "com.zundrel.simplyconveyors.client.ClientProxy", serverSide = "com.zundrel.simplyconveyors.common.CommonProxy")
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
