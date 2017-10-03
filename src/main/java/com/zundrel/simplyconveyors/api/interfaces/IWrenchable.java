package com.zundrel.simplyconveyors.api.interfaces;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IWrenchable {
	/**
	 * Called upon right-clicking with a wrench from Simply Conveyors.
	 * @param world Current world.
	 * @param state The BlockState of the wrenched block.
	 * @param pos The position of the wrenched block.
	 * @param player The player who wrenched the block.
	 * @param hand The hand in which the player is holding the wrench.
	 */
	public default void onWrenched(World world, IBlockState state, BlockPos pos, EntityPlayer player, EnumHand hand) {
		if (state.getBlock() instanceof BlockHorizontal) {
			state.withProperty(BlockHorizontal.FACING, state.getValue(BlockHorizontal.FACING).rotateY());
		}
	}
}
