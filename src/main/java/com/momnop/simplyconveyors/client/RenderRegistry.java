package com.momnop.simplyconveyors.client;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.momnop.simplyconveyors.SimplyConveyors;
import com.momnop.simplyconveyors.blocks.base.BlockFoliageColored;
import com.momnop.simplyconveyors.blocks.base.BlockUpgradeCrate;
import com.momnop.simplyconveyors.blocks.roads.BlockConnectingColored;
import com.momnop.simplyconveyors.info.ModInfo;
import com.momnop.simplyconveyors.items.ItemTiered;

public final class RenderRegistry {
	public static ArrayList registry = new ArrayList();
	
	@SideOnly(Side.CLIENT)
	public static void registerRenderers() {
		for (int i = 0; i < registry.size(); i++) {
			if (registry.get(i) instanceof Block) {
				if ((Block) registry.get(i) instanceof BlockConnectingColored) {
					SimplyConveyors.proxy.registerWoolColored((Block) registry.get(i));
					for (int i2 = 0; i2 < 15; i2++) {
						register((Block) registry.get(i), i2);
					}
				}
				if ((Block) registry.get(i) instanceof BlockFoliageColored) {
					SimplyConveyors.proxy.registerFoliageColored((Block) registry.get(i));
				}
				register((Block) registry.get(i));
			} else if (registry.get(i) instanceof Item) {
				if ((Item) registry.get(i) instanceof ItemTiered) {
					SimplyConveyors.proxy.registerTierColor((Item) registry.get(i));
					for (int i2 = 0; i2 < 3; i2++) {
						register((Item) registry.get(i), i2);
					}
				}
				register((Item) registry.get(i));
			}
		}
	}

	public static void register(Block block) {
		Minecraft
				.getMinecraft()
				.getRenderItem()
				.getItemModelMesher()
				.register(
						Item.getItemFromBlock(block),
						0,
						new ModelResourceLocation(ModInfo.MOD_ID + ":"
								+ block.getUnlocalizedName().substring(5),
								"inventory"));
	}
	
	public static void register(Block block, String unlocalizedName) {
		Minecraft
				.getMinecraft()
				.getRenderItem()
				.getItemModelMesher()
				.register(
						Item.getItemFromBlock(block),
						0,
						new ModelResourceLocation(ModInfo.MOD_ID + ":"
								+ unlocalizedName,
								"inventory"));
	}

	public static void register(Block block, int meta) {
		Minecraft
				.getMinecraft()
				.getRenderItem()
				.getItemModelMesher()
				.register(
						Item.getItemFromBlock(block),
						meta,
						new ModelResourceLocation(ModInfo.MOD_ID + ":"
								+ block.getUnlocalizedName().substring(5),
								"inventory"));
	}
	
	public static void register(Item item) {
		Minecraft
				.getMinecraft()
				.getRenderItem()
				.getItemModelMesher()
				.register(
						item,
						0,
						new ModelResourceLocation(ModInfo.MOD_ID + ":"
								+ item.getUnlocalizedName().substring(5),
								"inventory"));
	}
	
	public static void register(Item item, int meta) {
		Minecraft
				.getMinecraft()
				.getRenderItem()
				.getItemModelMesher()
				.register(
						item,
						meta,
						new ModelResourceLocation(ModInfo.MOD_ID + ":"
								+ item.getUnlocalizedName().substring(5),
								"inventory"));
	}
}