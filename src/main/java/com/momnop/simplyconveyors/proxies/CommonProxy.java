package com.momnop.simplyconveyors.proxies;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.momnop.simplyconveyors.blocks.conveyors.tiles.TileEntityBlockMovingPath;
import com.momnop.simplyconveyors.blocks.conveyors.tiles.TileEntityFastMovingStair;
import com.momnop.simplyconveyors.blocks.conveyors.tiles.TileEntityFastestMovingStair;
import com.momnop.simplyconveyors.blocks.conveyors.tiles.TileEntitySlowMovingStair;

public class CommonProxy
{
	public void preInitRenders()
	{

	}

	public void initTiles()
	{
		GameRegistry.registerTileEntity(TileEntitySlowMovingStair.class, "tileEntitySlowMovingStair");
		GameRegistry.registerTileEntity(TileEntityFastMovingStair.class, "tileEntityFastMovingStair");
		GameRegistry.registerTileEntity(TileEntityFastestMovingStair.class, "tileEntityFastestMovingStair");
		GameRegistry.registerTileEntity(TileEntityBlockMovingPath.class, "tileEntityBlockMovingPath");
	}

	public void initSounds()
	{

	}

	public void initRenders()
	{

	}

	public void initKeybinds()
	{

	}

	public EntityPlayer getPlayerEntity(MessageContext ctx)
	{
		return ctx.getServerHandler().playerEntity;
	}
}
