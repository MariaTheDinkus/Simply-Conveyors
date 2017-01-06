package com.momnop.simplyconveyors.items;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.momnop.simplyconveyors.info.ItemInfo;

public class SimplyConveyorsItems
{
	public static Item wrench;
	public static Item entityFilter;
	public static Item busStopBook;
	public static Item busTicket;

	public static Item conveyorResistanceBoots;

	public static void load()
	{
		wrench = new ItemWrench(ItemInfo.CONVEYOR_WRENCH_UNLOCALIZED_NAME);
		entityFilter = new ItemEntityFilter("entity_filter");
		busStopBook = new ItemBusStopBook("bus_stop_book");
		busTicket = new ItemBusTicket("bus_ticket");

		conveyorResistanceBoots = new ItemConveyorResistanceBoots(ArmorMaterial.LEATHER, 0, EntityEquipmentSlot.FEET, "conveyor_resistance_boots");

		registerItems();
	}

	public static void registerItems()
	{
		register(wrench);
		register(entityFilter);
		register(busStopBook);
		register(busTicket);

		register(conveyorResistanceBoots);
	}

	public static void register(Item b)
	{
		GameRegistry.register(b);
	}
}