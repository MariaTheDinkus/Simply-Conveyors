package com.zundrel.simplyconveyors.common.event;

import java.io.File;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

import com.zundrel.simplyconveyors.SimplyConveyors;
import com.zundrel.simplyconveyors.api.interfaces.IWrenchable;
import com.zundrel.simplyconveyors.common.blocks.modular.BlockFlatModularConveyor;
import com.zundrel.simplyconveyors.common.blocks.tiles.TileModularConveyor;
import com.zundrel.simplyconveyors.common.helpers.BusStopManager;
import com.zundrel.simplyconveyors.common.info.ModInfo;
import com.zundrel.simplyconveyors.common.items.ItemEntityFilter;
import com.zundrel.simplyconveyors.common.items.ItemWorkerGloves;
import com.zundrel.simplyconveyors.common.items.SimplyConveyorsItems;
import com.zundrel.simplyconveyors.common.items.tracks.ItemSpongeTrack;

@EventBusSubscriber(modid = ModInfo.MOD_ID)
public class CommonEventHandler
{
	@SubscribeEvent
	public static void onPlayerInteract(PlayerInteractEvent.EntityInteract event)
	{
		if(event.getItemStack() != ItemStack.EMPTY && event.getItemStack().getItem() instanceof ItemEntityFilter)
		{
			ItemEntityFilter filter = (ItemEntityFilter) event.getItemStack().getItem();
			event.getItemStack().getTagCompound().setString("filter", event.getTarget().getClass().getName());
		}
	}
	
	@SubscribeEvent
	public static void onBlockActivated(PlayerInteractEvent.RightClickBlock event) {
		if (event.getItemStack() != ItemStack.EMPTY && event.getItemStack().getItem() == SimplyConveyorsItems.wrench) {
			if (event.getWorld().getBlockState(event.getPos()).getBlock() instanceof IWrenchable) {
				IWrenchable wrenchable = (IWrenchable) event.getWorld().getBlockState(event.getPos()).getBlock();
				wrenchable.onWrenched(event.getWorld(), event.getWorld().getBlockState(event.getPos()), event.getPos(), event.getEntityPlayer(), event.getHand());
			}
		}
	}

	@SubscribeEvent
	public static void damaged(LivingAttackEvent event)
	{
		if(event.getSource() == DamageSource.FALL && event.getEntityLiving().getEntityWorld().getBlockState(event.getEntityLiving().getPosition()).getBlock() instanceof BlockFlatModularConveyor)
		{
			TileModularConveyor modular = (TileModularConveyor) event.getEntityLiving().getEntityWorld().getTileEntity(event.getEntityLiving().getPosition());

			if(modular.getStackInSlot(3) != ItemStack.EMPTY && modular.getStackInSlot(3).getItem() instanceof ItemSpongeTrack)
			{
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public static void onWorldLoaded(PlayerLoggedInEvent event)
	{
		BusStopManager.busStops.clear();
		BusStopManager.busStopsNames.clear();
		File busData = new File(DimensionManager.getCurrentSaveRootDirectory(), "busstops.dat");
		if(busData.exists())
		{
			try
			{
				BusStopManager.writeData(event.player.getEntityWorld());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}