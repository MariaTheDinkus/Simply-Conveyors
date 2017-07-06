package com.momnop.simplyconveyors.common;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
	
	public void registerItemRenderer(Item i, int meta, String id) {
    	
    }
	
	public EntityPlayer getPlayerEntity(MessageContext ctx)
	{
		return ctx.getServerHandler().playerEntity;
	}
	
//	public void setExtraReach(EntityLivingBase entity, float reach) {
//		if(entity instanceof EntityPlayerMP) {
//			((EntityPlayerMP) entity).interactionManager.setBlockReachDistance(5F + reach);
//		}
//	}
}
