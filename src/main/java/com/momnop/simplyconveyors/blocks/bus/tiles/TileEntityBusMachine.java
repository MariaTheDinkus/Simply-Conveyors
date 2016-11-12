package com.momnop.simplyconveyors.blocks.bus.tiles;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import com.momnop.simplyconveyors.helpers.BusStopManager;
import com.momnop.simplyconveyors.network.MessageBusTicketData;
import com.momnop.simplyconveyors.network.PacketDispatcher;

public class TileEntityBusMachine extends TileEntity implements ITickable {
	
	@Override
	public void update() {
		
	}
	
//	@Override
//	public SPacketUpdateTileEntity getUpdatePacket() {
//        return new SPacketUpdateTileEntity(this.pos, 1, this.getUpdateTag());
//	}
//	
//	@Override
//	public NBTTagCompound getUpdateTag() {
//		return this.writeToNBT(new NBTTagCompound());
//	}
//	
//	@Override
//	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
//		readFromNBT(pkt.getNbtCompound());
//	}
	
}
