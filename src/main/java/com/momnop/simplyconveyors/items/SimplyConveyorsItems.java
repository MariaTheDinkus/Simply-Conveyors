package com.momnop.simplyconveyors.items;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.momnop.simplyconveyors.api.EnumModule;
import com.momnop.simplyconveyors.api.EnumTrack;
import com.momnop.simplyconveyors.items.modules.ItemDropperModule;
import com.momnop.simplyconveyors.items.modules.ItemHoldingModule;
import com.momnop.simplyconveyors.items.modules.ItemSpikeModule;
import com.momnop.simplyconveyors.items.modules.ItemTransporterModule;
import com.momnop.simplyconveyors.items.tracks.ItemSpongeTrack;
import com.momnop.simplyconveyors.items.tracks.ItemWebbedTrack;

public class SimplyConveyorsItems
{
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
	
	public static Item iron_spike_module, gold_spike_module, diamond_spike_module;

	public static void load()
	{
		wrench = new ItemBasic("conveyor_wrench", 1);

		conveyorResistanceBoots = new ItemConveyorResistanceBoots(ArmorMaterial.LEATHER, 0, EntityEquipmentSlot.FEET, "conveyor_resistance_boots");

		roller = new ItemTiered("roller", 64);
		
		entityFilter = new ItemEntityFilter("entity_filter");
		
		dropper_module = new ItemDropperModule("dropper_module", EnumModule.DROPPER);
		holding_module = new ItemHoldingModule("holding_module", EnumModule.HOLDING);
		
		track = new ItemBasic("track", 64);
		
		sponge_track = new ItemSpongeTrack("sponge_track", EnumTrack.SPONGE);
		webbed_track = new ItemWebbedTrack("webbed_track", EnumTrack.WEBBED);
		
//		bus_book = new ItemBusStopBook("bus_book");
		
//		bus_ticket = new ItemBusTicket("bus_ticket");
		
		black_leather = new ItemBasic("black_leather", 64);
		
		iron_spike_module = new ItemSpikeModule("iron_spike_module", EnumModule.SPIKEIRON, 6, false, false);
		gold_spike_module = new ItemSpikeModule("gold_spike_module", EnumModule.SPIKEGOLD, 6, true, false);
		diamond_spike_module = new ItemSpikeModule("diamond_spike_module", EnumModule.SPIKEDIAMOND, 7, false, true);
		
		transporter_module = new ItemTransporterModule("transporter_module", EnumModule.TRANSPORTER);
		
		register(wrench);
		
		register(conveyorResistanceBoots);
		
		register(roller);
		
		register(entityFilter);
		
		register(dropper_module);
		register(holding_module);
		
		register(track);
		
		register(sponge_track);
		register(webbed_track);
		
		register(black_leather);
		
		register(iron_spike_module);
		register(gold_spike_module);
		register(diamond_spike_module);
		
		register(transporter_module);
	}

	public static void register(Item b)
	{
		GameRegistry.register(b);
	}
}