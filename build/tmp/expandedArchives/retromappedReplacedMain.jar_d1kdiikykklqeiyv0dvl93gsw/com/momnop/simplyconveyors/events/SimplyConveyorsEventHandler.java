package com.momnop.simplyconveyors.events;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.momnop.simplyconveyors.blocks.BlockMovingBackwardsHoldingPath;
import com.momnop.simplyconveyors.blocks.BlockMovingBackwardsPath;
import com.momnop.simplyconveyors.blocks.BlockMovingDropperPath;
import com.momnop.simplyconveyors.blocks.BlockMovingFastStairPath;
import com.momnop.simplyconveyors.blocks.BlockMovingFastestStairPath;
import com.momnop.simplyconveyors.blocks.BlockMovingHoldingPath;
import com.momnop.simplyconveyors.blocks.BlockMovingPath;
import com.momnop.simplyconveyors.blocks.BlockMovingSlowStairPath;
import com.momnop.simplyconveyors.blocks.BlockMovingVerticalPath;
import com.momnop.simplyconveyors.items.ItemWrench;

public class SimplyConveyorsEventHandler {
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
		Block block = event.getWorld().func_180495_p(event.getPos()).func_177230_c();
		if (block instanceof BlockMovingPath || block instanceof BlockMovingVerticalPath || block instanceof BlockMovingBackwardsPath || block instanceof BlockMovingHoldingPath || block instanceof BlockMovingBackwardsHoldingPath || block instanceof BlockMovingDropperPath || block instanceof BlockMovingSlowStairPath || block instanceof BlockMovingFastStairPath || block instanceof BlockMovingFastestStairPath) {
			if (block instanceof BlockHorizontal && event.getEntityPlayer().func_184614_ca() != null && event.getEntityPlayer().func_184614_ca().func_77973_b() instanceof ItemWrench || block instanceof BlockHorizontal && event.getEntityPlayer().func_184592_cb() != null && event.getEntityPlayer().func_184592_cb().func_77973_b() instanceof ItemWrench) {
				BlockHorizontal blockHorizontal = (BlockHorizontal) block;
				if (!event.getEntityPlayer().func_70093_af()) {
					event.getWorld().func_175656_a(event.getPos(), block.func_176223_P().func_177226_a(blockHorizontal.field_185512_D, event.getEntityPlayer().func_174811_aO().func_176734_d()));
				} else {
					event.getWorld().func_175656_a(event.getPos(), block.func_176223_P().func_177226_a(blockHorizontal.field_185512_D, event.getEntityPlayer().func_174811_aO()));
				}
			}
		}
	}
}
