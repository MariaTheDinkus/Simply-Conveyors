package com.momnop.simplyconveyors.blocks.tiles;

import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

import com.momnop.simplyconveyors.helpers.BusStopManager;

public class TileBusStop extends TileEntity implements ITickable
{

	private String name = "Bus Stop";

	public void setName(String name)
	{
		this.name = name;
		for(int i = 0; i < BusStopManager.busStops.size(); i++)
		{
			if(pos.equals(BusStopManager.busStops.get(i)))
			{
				BusStopManager.busStopsNames.put(pos, name);
				try
				{
					BusStopManager.saveData();
				}
				catch (IOException e)
				{
					// e.printStackTrace();
				}
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
		for(int i = 0; i < BusStopManager.busStops.size(); i++)
		{
			if(pos != null && pos.equals(this.getPos()) && BusStopManager.busStopsNames.get(pos) != null && !getName().equals(BusStopManager.busStopsNames.get(pos)))
			{
				setName(BusStopManager.busStopsNames.get(pos));
				try
				{
					BusStopManager.saveData();
				}
				catch (IOException e)
				{
					
				}
				break;
			}
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
