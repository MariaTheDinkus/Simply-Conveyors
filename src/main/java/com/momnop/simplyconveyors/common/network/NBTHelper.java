package com.momnop.simplyconveyors.common.network;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;

public class NBTHelper
{
	public static NBTTagCompound writeAxisAlignedBB(String name, AxisAlignedBB bb)
	{
		NBTTagCompound compound = new NBTTagCompound();
		compound.setDouble(name + "_minX", bb.minX);
		compound.setDouble(name + "_minY", bb.minY);
		compound.setDouble(name + "_minZ", bb.minZ);
		compound.setDouble(name + "_maxX", bb.maxX);
		compound.setDouble(name + "_maxY", bb.maxY);
		compound.setDouble(name + "_maxZ", bb.maxZ);
		return compound;
	}

	public static AxisAlignedBB getAxisAlignedBBFromNBT(NBTTagCompound compound, String name)
	{
		NBTTagCompound compound1 = compound;
		double minX = compound1.getDouble(name + "_minX");
		double minY = compound1.getDouble(name + "_minY");
		double minZ = compound1.getDouble(name + "_minZ");
		double maxX = compound1.getDouble(name + "_maxX");
		double maxY = compound.getDouble(name + "_maxY");
		double maxZ = compound1.getDouble(name + "_maxZ");
		return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
	}
}
