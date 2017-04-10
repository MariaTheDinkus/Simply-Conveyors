package com.momnop.simplyconveyors.proxies;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CommonProxy
{
	public void registerModels() {
		
	}
	
	public void registerWoolColored(Block block) {

	}
	
	public void registerFoliageColored(Block block) {
		
	}
	
	public void registerTierColor(Item item) {
		
	}

	public void registerFilterColor(Item item)
	{
		
	}
	
	public EntityPlayer getPlayerEntity(MessageContext ctx)
	{
		return ctx.getServerHandler().playerEntity;
	}
}
