package com.momnop.simplyconveyors.items;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.momnop.simplyconveyors.info.ItemInfo;

public class SimplyConveyorsItems {
	public static Item wrench;
	public static Item entityFilter;
	public static Item busStopBook;
	public static Item busTicket;

	public static void load() {
		wrench = new ItemWrench(ItemInfo.CONVEYOR_WRENCH_UNLOCALIZED_NAME);
		entityFilter = new ItemEntityFilter("entityFilter");
		busStopBook = new ItemBusStopBook("busStopBook");
		busTicket = new ItemBusTicket("busTicket");
		
		registerItems();
	}

	public static void registerItems() {
		register(wrench);
		register(entityFilter);
		register(busStopBook);
		register(busTicket);
	}

	public static void register(Item b) {
		GameRegistry.register(b);
	}
}