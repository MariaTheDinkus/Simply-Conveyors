package com.momnop.simplyconveyors.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.momnop.simplyconveyors.blocks.bus.tiles.TileEntityBusStop;
import com.momnop.simplyconveyors.items.ItemBusTicket;

public class MessageBusStopData implements IMessage
{
	private String name;
	private BlockPos pos;

	public MessageBusStopData()
	{
	}

	public MessageBusStopData(String name, BlockPos pos)
	{
		this.name = name;
		this.pos = pos;
	}

	public void fromBytes(ByteBuf buf)
	{
		this.name = ByteBufUtils.readUTF8String(buf);
		this.pos = new BlockPos(buf.readFloat(), buf.readFloat(), buf.readFloat());
	}

	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, name);
		buf.writeFloat(pos.getX());
		buf.writeFloat(pos.getY());
		buf.writeFloat(pos.getZ());
	}

	public static class Handler extends AbstractClientMessageHandler<MessageBusStopData>
	{
		public IMessage handleClientMessage(EntityPlayer player, MessageBusStopData message, MessageContext ctx)
		{
			if((player != null) && (message != null) && (ctx != null))
			{
				if(player.getEntityWorld().getTileEntity(message.pos) instanceof TileEntityBusStop)
				{
					TileEntityBusStop busStop = (TileEntityBusStop) player.getEntityWorld().getTileEntity(message.pos);
					busStop.setName(message.name);
				}
			}
			return null;
		}
	}
}
