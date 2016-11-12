package com.momnop.simplyconveyors.client.render.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.momnop.simplyconveyors.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.info.ModInfo;
import com.momnop.simplyconveyors.items.SimplyConveyorsItems;

public final class BlockRenderRegister {
	@SideOnly(Side.CLIENT)
	public static void registerBlockRenderer() {
		register(SimplyConveyorsItems.wrench);

		register(SimplyConveyorsBlocks.blockSlowMovingPath);
		register(SimplyConveyorsBlocks.blockFastMovingPath);
		register(SimplyConveyorsBlocks.blockFastestMovingPath);
		register(SimplyConveyorsBlocks.blockDropperMovingPath);
		register(SimplyConveyorsBlocks.blockHoldingMovingPath);

		register(SimplyConveyorsBlocks.blockSlowMovingVerticalPath);
		register(SimplyConveyorsBlocks.blockFastMovingVerticalPath);
		register(SimplyConveyorsBlocks.blockFastestMovingVerticalPath);

		register(SimplyConveyorsBlocks.blockSlowMovingBackwardsPath);
		register(SimplyConveyorsBlocks.blockFastMovingBackwardsPath);
		register(SimplyConveyorsBlocks.blockFastestMovingBackwardsPath);
		register(SimplyConveyorsBlocks.blockHoldingMovingBackwardsPath);

		register(SimplyConveyorsBlocks.blockSlowMovingStairPath);
		register(SimplyConveyorsBlocks.blockFastMovingStairPath);
		register(SimplyConveyorsBlocks.blockFastestMovingStairPath);
		
		register(SimplyConveyorsBlocks.busStop);
	}

	public static void register(Block block) {
		Minecraft
				.getMinecraft()
				.getRenderItem()
				.getItemModelMesher()
				.register(
						Item.getItemFromBlock(block),
						0,
						new ModelResourceLocation(ModInfo.MODID + ":"
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
						new ModelResourceLocation(ModInfo.MODID + ":"
								+ item.getUnlocalizedName().substring(5),
								"inventory"));
	}
}