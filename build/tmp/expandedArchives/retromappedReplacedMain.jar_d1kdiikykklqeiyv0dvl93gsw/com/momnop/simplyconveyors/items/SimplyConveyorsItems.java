package com.momnop.simplyconveyors.items;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.momnop.simplyconveyors.info.ItemInfo;

public class SimplyConveyorsItems {
	public static Item wrench;

	public static void load() {
		wrench = new ItemWrench(ItemInfo.CONVEYOR_WRENCH_UNLOCALIZED_NAME);
		registerItems();
	}

	public static void registerItems() {
		register(wrench);
	}

	public static void register(Item b) {
		GameRegistry.register(b);
	}
}
