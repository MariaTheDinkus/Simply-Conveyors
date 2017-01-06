package com.momnop.simplyconveyors.blocks.bus.tiles;

import java.io.IOException;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.ForgeChunkManager;

import com.momnop.simplyconveyors.helpers.BusStopManager;
import com.momnop.simplyconveyors.network.MessageBusTicketData;
import com.momnop.simplyconveyors.network.PacketDispatcher;

public class TileEntityBusStop extends TileEntity implements ITickable
{

	private String name = "Bus Stop";

	public void setName(String name)
	{
		this.name = name;
		for(int i = 0; i < BusStopManager.busStops.size(); i++)
		{
			if(pos.equals(BusStopManager.busStops.get(i)))
			{
				BusStopManager.busStopsNames.set(i, name);
				// if (!worldObj.isRemote) {
				try
				{
					BusStopManager.saveData();
				}
				catch (IOException e)
				{
					// e.printStackTrace();
				}
				// }
			}
		}
	}

	public String getName()
	{
		return name;
	}

	@Override
	public void update()
	{
		markDirty();
		int i = 0;
		for(BlockPos pos : BusStopManager.busStops)
		{
			if(pos.equals(this.getPos()) && getName() != BusStopManager.busStopsNames.get(i))
			{
				setName(BusStopManager.busStopsNames.get(i));
			}
			i++;
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setString("name", name);
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		name = compound.getString("name");
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

}
