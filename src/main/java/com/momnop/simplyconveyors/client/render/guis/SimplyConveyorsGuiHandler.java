package com.momnop.simplyconveyors.client.render.guis;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class SimplyConveyorsGuiHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID == 0)
		{
			return new GuiBusMachine();
		}

		if(ID == 1)
		{
			return new GuiSetNameBusStop(new BlockPos(x, y, z), world);
		}

		if(ID == 2)
		{
			return new GuiSetNameTicket(player.getHeldItemMainhand());
		}

		return null;
	}

}
