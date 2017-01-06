package com.momnop.simplyconveyors.blocks.conveyors.tiles;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.blocks.conveyors.special.BlockMovingDetectorPath;

public class TileEntitySmeltingMovingPath extends TileEntity implements ITickable
{

	private int cooldown = 0;
	private int cooldownMax = 160;

	@Override
	public void update()
	{
		markDirty();

		List entities = this.getWorld().getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos.getX() - 0, pos.getY(), pos.getZ() - 0, pos.getX() + 1, pos.getY() + 2F, pos.getZ() + 1));

		for(Entity ent : (List<Entity>) entities)
		{

		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setInteger("cooldown", cooldown);
		compound.setInteger("cooldownMax", cooldownMax);
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		cooldown = compound.getInteger("cooldown");
		cooldownMax = compound.getInteger("cooldownMax");
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		return new SPacketUpdateTileEntity(this.pos, 1, this.getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag()
	{
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
	{
		return oldState.getBlock() != newSate.getBlock();
	}
}
