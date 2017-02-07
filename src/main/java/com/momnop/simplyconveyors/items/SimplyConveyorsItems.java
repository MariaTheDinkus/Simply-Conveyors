package com.momnop.simplyconveyors.items;

import com.momnop.simplyconveyors.items.upgrades.ItemDropperModule;
import com.momnop.simplyconveyors.items.upgrades.ItemSpongeModule;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SimplyConveyorsItems
{
	public static Item wrench;
	
	public static Item roller;

	public static Item conveyorResistanceBoots;
	
	public static Item entityFilter;
	
	public static Item dropper_module;
	public static Item sponge_module;

	public static void load()
	{
		wrench = new ItemBasic("conveyor_wrench", 1);

		conveyorResistanceBoots = new ItemConveyorResistanceBoots(ArmorMaterial.LEATHER, 0, EntityEquipmentSlot.FEET, "conveyor_resistance_boots");

		roller = new ItemTiered("roller", 64);
		
		entityFilter = new ItemEntityFilter("entity_filter");
		
		dropper_module = new ItemDropperModule("dropper_module");
		
		sponge_module = new ItemSpongeModule("sponge_module");
	}

	public static void register(Item b)
	{
		GameRegistry.register(b);
	}
}