package com.momnop.simplyconveyors.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class EntityBus extends Entity
{

	public EntityBus(World worldIn)
	{
		super(worldIn);
	}

	public EntityBus(World worldIn, double x, double y, double z, EnumFacing facing)
	{
		super(worldIn);
		this.setSize(5, 3);
		this.setPositionAndRotation(x, y, z, facing.getHorizontalAngle(), 0);
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
	}

	@Override
	public boolean canBeCollidedWith()
	{
		return true;
	}

	@Override
	public boolean canBePushed()
	{
		return false;
	}

	@Override
	protected void entityInit()
	{

	}

	@Override
	public void onUpdate()
	{
		if(this.ticksExisted > 200)
		{
			setDead();
		}
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound)
	{

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound)
	{

	}

}
