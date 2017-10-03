package com.zundrel.simplyconveyors.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.zundrel.simplyconveyors.SimplyConveyors;
import com.zundrel.simplyconveyors.common.blocks.tiles.TileModularConveyor;

public class MessageModularConveyor implements IMessage
{
	private int inventorySize;
	private NonNullList<ItemStack> inventory;
	private BlockPos pos;

	public MessageModularConveyor()
	{
	}

	public MessageModularConveyor(NonNullList<ItemStack> inventory, BlockPos pos)
	{
		this.inventorySize = inventory.size();
		this.inventory = inventory;
		this.pos = pos;
	}

	public void fromBytes(ByteBuf buf)
	{
		inventorySize = buf.readInt();
		inventory = NonNullList.withSize(inventorySize, ItemStack.EMPTY);
		for (int i = 0; i < inventorySize; i++) {
			this.inventory.add(ByteBufUtils.readItemStack(buf));
		}
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
	}

	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(inventorySize);
		for (int i = 0; i < inventory.size(); i++) {
			ByteBufUtils.writeItemStack(buf, inventory.get(i));
		}
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
	}

	public static class Handler extends AbstractClientMessageHandler<MessageModularConveyor>
	{
		public IMessage handleClientMessage(EntityPlayer player, MessageModularConveyor message, MessageContext ctx)
		{
			if((player != null) && (message != null) && (ctx != null))
			{
				EntityPlayer en = SimplyConveyors.proxy.getPlayerEntity(ctx);
				if (en.getEntityWorld().getTileEntity(message.pos) != null && en.getEntityWorld().getTileEntity(message.pos) instanceof TileModularConveyor) {
					TileModularConveyor tile = (TileModularConveyor) en.getEntityWorld().getTileEntity(message.pos);
					tile.inventory = message.inventory;
					tile.writeToNBT(new NBTTagCompound());
				}
			}
			return null;
		}
	}
}
