package com.momnop.simplyconveyors;

import mcjty.lib.compat.CompatCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLMissingMappingsEvent;
import net.minecraftforge.fml.common.event.FMLMissingMappingsEvent.MissingMapping;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.momnop.simplyconveyors.common.CommonProxy;
import com.momnop.simplyconveyors.common.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.common.blocks.base.BlockUpgradeCrate;
import com.momnop.simplyconveyors.common.blocks.tiles.TileAdvancedConveyor;
import com.momnop.simplyconveyors.common.blocks.tiles.TileModularConveyor;
import com.momnop.simplyconveyors.common.event.SimplyConveyorsEventHandler;
import com.momnop.simplyconveyors.common.handlers.ConfigHandler;
import com.momnop.simplyconveyors.common.handlers.GuiHandler;
import com.momnop.simplyconveyors.common.handlers.RecipeHandler;
import com.momnop.simplyconveyors.common.info.ModInfo;
import com.momnop.simplyconveyors.common.items.SimplyConveyorsItems;
import com.momnop.simplyconveyors.common.network.PacketDispatcher;

@Mod(name = ModInfo.NAME, modid = ModInfo.MOD_ID, version = ModInfo.VERSION, acceptedMinecraftVersions = "[1.9,1.12)", dependencies = "required-after:compatlayer")
public class SimplyConveyors
{
	@Instance(value = ModInfo.MOD_ID)
	public static SimplyConveyors INSTANCE;

	@SidedProxy(clientSide = "com.momnop.simplyconveyors.client.ClientProxy", serverSide = "com.momnop.simplyconveyors.common.CommonProxy")
	public static CommonProxy proxy;

	public static CreativeTabs tabGeneral = new CompatCreativeTabs(ModInfo.MOD_ID) {
        @Override
        @SideOnly(Side.CLIENT)
        public Item getItem() {
            return Item.getItemFromBlock(SimplyConveyorsBlocks.conveyor_slow);
        }
    };
    
    public static CreativeTabs tabRoads = new CompatCreativeTabs(ModInfo.MOD_ID + "_roads") {
        @Override
        @SideOnly(Side.CLIENT)
        public Item getItem() {
            return Item.getItemFromBlock(SimplyConveyorsBlocks.road_full);
        }
    };
    
    public static CreativeTabs tabSpecial = new CompatCreativeTabs(ModInfo.MOD_ID + "_special") {
        @Override
        @SideOnly(Side.CLIENT)
        public Item getItem() {
            return Item.getItemFromBlock(SimplyConveyorsBlocks.conveyor_vertical_slow);
        }
    };
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		RecipeHandler.loadList();
		
		SimplyConveyorsItems.load();
		SimplyConveyorsBlocks.load();

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

		MinecraftForge.EVENT_BUS.register(new SimplyConveyorsEventHandler());
		
		RecipeHandler.loadRecipes();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{

	}

	@EventHandler
	public void serverLoad(FMLServerStartedEvent event)
	{

	}

	@EventHandler
	public void onMissingMapping(FMLMissingMappingsEvent event)
	{
		for(MissingMapping mapping : event.get())
		{
			remap(mapping, "conveyor_fastest");
			remap(mapping, "conveyor_dropper");
			remap(mapping, "conveyor_holding");
			remap(mapping, "conveyor_vertical_fastest");
			remap(mapping, "conveyor_vertical_fastest_down");
			remap(mapping, "conveyor_backwards_slow");
			remap(mapping, "conveyor_backwards_fast");
			remap(mapping, "conveyor_backwards_fastest");
			remap(mapping, "conveyor_backwards_holding");
			remap(mapping, "conveyor_stair_fastest");
			remap(mapping, "conveyor_transporter");
			remap(mapping, "conveyor_foam_slow");
			remap(mapping, "conveyor_foam_fast");
			remap(mapping, "conveyor_foam_fastest");
			remap(mapping, "conveyor_spike_slow");
			remap(mapping, "conveyor_spike_fast");
			remap(mapping, "conveyor_spike_fastest");
		}
	}

	public static void remap(MissingMapping mapping, String newId)
	{
		if(mapping.type == GameRegistry.Type.BLOCK)
		{
			if(mapping.name.equals(ModInfo.MOD_ID + ":" + newId))
			{
				System.out.println("Remapped Block: " + mapping.name + " to " + "upgrade_" + newId);
				mapping.remap(Block.REGISTRY.getObject(new ResourceLocation(ModInfo.MOD_ID + ":" + "upgrade_" + newId)));
			}
		} 
		else
		{
			if(mapping.name.equals(ModInfo.MOD_ID + ":" + newId))
			{
				if (Block.REGISTRY.getObject(new ResourceLocation(ModInfo.MOD_ID + ":" + "upgrade_" + newId)) instanceof BlockUpgradeCrate) {
					System.out.println("Remapped Item: " + mapping.name + " to " + "upgrade_" + newId);
					BlockUpgradeCrate crate = (BlockUpgradeCrate) Block.REGISTRY.getObject(new ResourceLocation(ModInfo.MOD_ID + ":" + "upgrade_" + newId));
					mapping.remap(crate.getItemBlock());
				}
			}
		}
		
	}

	public static String appendId(String string)
	{
		return ModInfo.MOD_ID + ":" + string;
	}
}
