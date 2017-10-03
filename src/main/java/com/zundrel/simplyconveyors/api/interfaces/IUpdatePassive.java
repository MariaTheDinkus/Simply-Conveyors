package com.zundrel.simplyconveyors.api.interfaces;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public interface IUpdatePassive {
	/**
	 * Called passively even when no entities are on the conveyor.
	 * @param tile The tile entity calling the method.
	 * @param powered Whether or not the conveyor is powered by redstone.
	 * @param facing What direction the conveyor is facing.
	 * @param conveyorType If the conveyor is normal or inverse.
	 */
	public void updatePassive(TileEntity tile, boolean powered, EnumFacing facing, EnumFacing conveyorType);
}
