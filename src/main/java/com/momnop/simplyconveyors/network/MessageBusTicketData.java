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

public class MessageBusTicketData implements IMessage
{
	private String name;

	public MessageBusTicketData()
	{
	}

	public MessageBusTicketData(String name)
	{
		this.name = name;
	}

	public void fromBytes(ByteBuf buf)
	{
		this.name = ByteBufUtils.readUTF8String(buf);
	}

	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, name);
	}

	public static class Handler extends AbstractClientMessageHandler<MessageBusTicketData>
	{
		public IMessage handleClientMessage(EntityPlayer player, MessageBusTicketData message, MessageContext ctx)
		{
			if((player != null) && (message != null) && (ctx != null))
			{
				if(player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() instanceof ItemBusTicket)
				{
					player.getHeldItemMainhand().setStackDisplayName(message.name);
				}

				if(player.getHeldItemOffhand() != null && player.getHeldItemOffhand().getItem() instanceof ItemBusTicket)
				{
					player.getHeldItemOffhand().setStackDisplayName(message.name);
				}
			}
			return null;
		}
	}
}
