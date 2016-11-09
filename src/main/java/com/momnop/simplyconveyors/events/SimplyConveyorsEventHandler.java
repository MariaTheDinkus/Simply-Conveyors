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
		Block block = event.getWorld().getBlockState(event.getPos()).getBlock();
		if (block instanceof BlockMovingPath || block instanceof BlockMovingVerticalPath || block instanceof BlockMovingBackwardsPath || block instanceof BlockMovingHoldingPath || block instanceof BlockMovingBackwardsHoldingPath || block instanceof BlockMovingDropperPath || block instanceof BlockMovingSlowStairPath || block instanceof BlockMovingFastStairPath || block instanceof BlockMovingFastestStairPath) {
			if (block instanceof BlockHorizontal && event.getEntityPlayer().getHeldItemMainhand() != null && event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemWrench || block instanceof BlockHorizontal && event.getEntityPlayer().getHeldItemOffhand() != null && event.getEntityPlayer().getHeldItemOffhand().getItem() instanceof ItemWrench) {
				BlockHorizontal blockHorizontal = (BlockHorizontal) block;
				if (!event.getEntityPlayer().isSneaking()) {
					event.getWorld().setBlockState(event.getPos(), block.getDefaultState().withProperty(blockHorizontal.FACING, event.getEntityPlayer().getHorizontalFacing().getOpposite()));
				} else {
					event.getWorld().setBlockState(event.getPos(), block.getDefaultState().withProperty(blockHorizontal.FACING, event.getEntityPlayer().getHorizontalFacing()));
				}
			}
		}
	}
}