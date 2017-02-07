package com.momnop.simplyconveyors.event;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import com.momnop.simplyconveyors.SimplyConveyors;
import com.momnop.simplyconveyors.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.blocks.base.BlockConveyor;
import com.momnop.simplyconveyors.blocks.tiles.TileAdvancedConveyor;
import com.momnop.simplyconveyors.blocks.tiles.TileAdvancedConveyor.ConveyorInventoryHandler;
import com.momnop.simplyconveyors.info.ModInfo;
import com.momnop.simplyconveyors.items.ItemBasic;
import com.momnop.simplyconveyors.items.ItemEntityFilter;
import com.momnop.simplyconveyors.items.SimplyConveyorsItems;

public class SimplyConveyorsEventHandler
{
	public static boolean followingNearest = false;

	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent.RightClickBlock event)
	{
		Block block = event.getWorld().getBlockState(event.getPos()).getBlock();
		if(block instanceof BlockConveyor)
		{
			if(block instanceof BlockHorizontal && event.getEntityPlayer().getHeldItem(event.getHand()) != null
					&& event.getEntityPlayer().getHeldItem(event.getHand()).getItem() == SimplyConveyorsItems.wrench)
			{
				BlockHorizontal blockHorizontal = (BlockHorizontal) block;
				if(!event.getEntityPlayer().isSneaking())
				{
					event.getWorld().setBlockState(event.getPos(),
							block.getDefaultState().withProperty(blockHorizontal.FACING, event.getWorld().getBlockState(event.getPos()).getValue(blockHorizontal.FACING).rotateY()));
				}
				else
				{
					if(block == SimplyConveyorsBlocks.conveyor_vertical_slow)
					{
						event.getWorld().setBlockState(
								event.getPos(),
								SimplyConveyorsBlocks.conveyor_vertical_slow_down.getDefaultState().withProperty(BlockHorizontal.FACING,
										event.getWorld().getBlockState(event.getPos()).getValue(BlockHorizontal.FACING)));
					}
					else if(block == SimplyConveyorsBlocks.conveyor_vertical_intermediate)
					{
						event.getWorld().setBlockState(
								event.getPos(),
								SimplyConveyorsBlocks.conveyor_vertical_intermediate_down.getDefaultState().withProperty(BlockHorizontal.FACING,
										event.getWorld().getBlockState(event.getPos()).getValue(BlockHorizontal.FACING)));
					}
					else if(block == SimplyConveyorsBlocks.conveyor_vertical_fast)
					{
						event.getWorld().setBlockState(
								event.getPos(),
								SimplyConveyorsBlocks.conveyor_vertical_fast_down.getDefaultState().withProperty(BlockHorizontal.FACING,
										event.getWorld().getBlockState(event.getPos()).getValue(BlockHorizontal.FACING)));
					}
					else if(block == SimplyConveyorsBlocks.conveyor_vertical_slow_down)
					{
						event.getWorld().setBlockState(
								event.getPos(),
								SimplyConveyorsBlocks.conveyor_vertical_slow.getDefaultState().withProperty(BlockHorizontal.FACING,
										event.getWorld().getBlockState(event.getPos()).getValue(BlockHorizontal.FACING)));
					}
					else if(block == SimplyConveyorsBlocks.conveyor_vertical_intermediate_down)
					{
						event.getWorld().setBlockState(
								event.getPos(),
								SimplyConveyorsBlocks.conveyor_vertical_intermediate.getDefaultState().withProperty(BlockHorizontal.FACING,
										event.getWorld().getBlockState(event.getPos()).getValue(BlockHorizontal.FACING)));
					}
					else if(block == SimplyConveyorsBlocks.conveyor_vertical_fast_down)
					{
						event.getWorld().setBlockState(
								event.getPos(),
								SimplyConveyorsBlocks.conveyor_vertical_fast.getDefaultState().withProperty(BlockHorizontal.FACING,
										event.getWorld().getBlockState(event.getPos()).getValue(BlockHorizontal.FACING)));
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent.EntityInteract event)
	{
		if(event.getItemStack() != null && event.getItemStack().getItem() instanceof ItemEntityFilter)
		{
			ItemEntityFilter filter = (ItemEntityFilter) event.getItemStack().getItem();
			event.getItemStack().getTagCompound().setString("filter", event.getTarget().getClass().getName());
		}
	}
	
//	@SubscribeEvent
//	public void attachCapability(AttachCapabilitiesEvent<TileEntity> event) {
//		System.out.println("BLECH");
//		if (event.getObject() instanceof TileAdvancedConveyor) {
//			System.out.println("BLECH");
//			event.addCapability(new ResourceLocation(ModInfo.MOD_ID, "item_capability"), new ICapabilityProvider()
//			{
//				@Override
//				public boolean hasCapability(Capability<?> capability, EnumFacing facing)
//				{
//					if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
//						return true;
//					
//					return false;
//				}
//				IItemHandler insertionHandler = new TileAdvancedConveyor.ConveyorInventoryHandler((TileAdvancedConveyor) event.getObject());
//				@Override
//				public <T> T getCapability(Capability<T> capability, EnumFacing facing)
//				{
//					if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
//						return (T)insertionHandler;
//					return null;
//				}
//			});
//		}
//	}
}