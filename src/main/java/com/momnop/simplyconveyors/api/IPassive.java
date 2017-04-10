package com.momnop.simplyconveyors.api;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public interface IPassive
{
	public void passiveUpdate(TileEntity tile, boolean powered, EnumFacing facing, EnumFacing conveyorType);
}
