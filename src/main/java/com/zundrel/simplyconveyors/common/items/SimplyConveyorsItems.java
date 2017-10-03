package com.zundrel.simplyconveyors.common.items;

import java.util.ArrayList;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import com.zundrel.simplyconveyors.common.blocks.base.BlockBasic;
import com.zundrel.simplyconveyors.common.info.ModInfo;
import com.zundrel.simplyconveyors.common.items.modules.ItemDropperModule;
import com.zundrel.simplyconveyors.common.items.modules.ItemHoldingModule;
import com.zundrel.simplyconveyors.common.items.modules.ItemSpikeModule;
import com.zundrel.simplyconveyors.common.items.modules.ItemTransporterModule;
import com.zundrel.simplyconveyors.common.items.tracks.ItemSpongeTrack;
import com.zundrel.simplyconveyors.common.items.tracks.ItemWebbedTrack;

@EventBusSubscriber(modid = ModInfo.MOD_ID)
public class SimplyConveyorsItems
{
	public static IForgeRegistry<Item> registry;
	public static ArrayList<ItemBlock> itemBlocks = new ArrayList<ItemBlock>();
	
	public static Item wrench;
	
	public static Item roller;

	public static Item conveyorResistanceBoots;
	
	public static Item entityFilter;
	
	public static Item black_leather;
	
	public static Item dropper_module;
	public static Item holding_module;
	public static Item transporter_module;
	public static Item track;
	public static Item sponge_track;
	public static Item webbed_track;
	
	public static Item bus_book;
	public static Item bus_ticket;
	
//	public static Item worker_gloves;
	
	public static Item iron_spike_module, gold_spike_module, diamond_spike_module;

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		registry = event.getRegistry();
		
		wrench = register(new ItemBasic("conveyor_wrench", 1));

		conveyorResistanceBoots = register(new ItemConveyorResistanceBoots(ArmorMaterial.LEATHER, 0, EntityEquipmentSlot.FEET, "conveyor_resistance_boots"));

		roller = register(new ItemTiered("roller", 64));
		
		entityFilter = register(new ItemEntityFilter("entity_filter"));
		
		dropper_module = register(new ItemDropperModule("dropper_module"));
		holding_module = register(new ItemHoldingModule("holding_module"));
		
		track = register(new ItemBasic("track", 64));
		
		sponge_track = register(new ItemSpongeTrack("sponge_track"));
		webbed_track = register(new ItemWebbedTrack("webbed_track"));
		
//		bus_book = register(new ItemBusStopBook("bus_book");
		
//		bus_ticket = register(new ItemBusTicket("bus_ticket");
		
		black_leather = register(new ItemBasic("black_leather", 64));
		
		iron_spike_module = register(new ItemSpikeModule("iron_spike_module", 6, false, false));
		gold_spike_module = register(new ItemSpikeModule("gold_spike_module", 6, true, false));
		diamond_spike_module = register(new ItemSpikeModule("diamond_spike_module", 7, false, true));
		
		transporter_module = register(new ItemTransporterModule("transporter_module"));
		
		//worker_gloves = register(new ItemWorkerGloves("worker_gloves"));
		
		for (ItemBlock ib : itemBlocks) {
			registry.register(ib);
			if (ib.getBlock() instanceof BlockBasic) {
	    		((BlockBasic)ib.getBlock()).registerItemModel(ib);
	    	}
		}
	}

	public static <T extends Item> T register(T i)
	{
		registry.register(i);
		if (i instanceof ItemBasic) {
			((ItemBasic)i).registerItemModel(i);
		} else if (i instanceof ItemConveyorResistanceBoots) {
			((ItemConveyorResistanceBoots)i).registerItemModel(i);
		}
		return i;
	}
}