package com.momnop.simplyconveyors.items;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import com.momnop.simplyconveyors.SimplyConveyorsCreativeTab;
import com.momnop.simplyconveyors.blocks.conveyors.special.BlockMovingBackwardsDetectorPath;
import com.momnop.simplyconveyors.blocks.conveyors.special.BlockMovingDetectorPath;
import com.momnop.simplyconveyors.blocks.conveyors.special.BlockMovingGrabberPath;
import com.momnop.simplyconveyors.blocks.conveyors.tiles.TileEntityDetectorBackwardsPath;
import com.momnop.simplyconveyors.blocks.conveyors.tiles.TileEntityDetectorPath;
import com.momnop.simplyconveyors.blocks.conveyors.tiles.TileEntityGrabberPath;

public class ItemEntityFilter extends Item
{
	
    public ItemEntityFilter(String unlocalizedName)
    {
        super();
        setRegistryName(unlocalizedName);
        setCreativeTab(SimplyConveyorsCreativeTab.INSTANCE);
        setUnlocalizedName(this.getRegistryName().toString().replace("simplyconveyors:", ""));
        setMaxStackSize(1);
    }
    
    public void setFilter(ItemStack stack, Class filterClass) {
    	stack.getTagCompound().setString("filter", filterClass.getName());
    }
    
    public Class getFilter(ItemStack stack) {
    	try {
			return Class.forName(stack.getTagCompound().getString("filter"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn,
    		List<String> tooltip, boolean advanced) {
    	tooltip.add("Right click on a mob to set the filter!");
    	tooltip.add("Works with the Grabber Conveyor and Detector Conveyors.");
    	if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
    		if (stack.hasTagCompound()) {
    			tooltip.add("Right-clicking on a compatible conveyor adds this filter's type to the filter list of the conveyor.");
    		}
    	} else {
    		if (stack.hasTagCompound()) {
    			tooltip.add("Press " + TextFormatting.WHITE + "Shift " + TextFormatting.GRAY + "to see more information.");
    		}
    	}
    	if (stack.hasTagCompound()) {
    		try {
    			tooltip.add("Filter: " + TextFormatting.WHITE + Class.forName(stack.getTagCompound().getString("filter")).getSimpleName());
    		} catch (ClassNotFoundException e) {
    			e.printStackTrace();
    		}
    	}
    }
    
    @Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn,
    		EntityPlayer playerIn, EnumHand hand) {
    	if (playerIn.isSneaking()) {
    		playerIn.getHeldItem(hand).getTagCompound().setString("filter", "net.minecraft.entity.Entity");
    		return ActionResult.newResult(EnumActionResult.PASS, playerIn.getHeldItem(hand));
    	}
		return ActionResult.newResult(EnumActionResult.FAIL, playerIn.getHeldItem(hand));
    }
    
    @Override
	public EnumActionResult onItemUse(EntityPlayer playerIn,
    		World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing,
    		float hitX, float hitY, float hitZ) {
    	if (worldIn.getBlockState(pos).getBlock() instanceof BlockMovingGrabberPath) {
    		TileEntityGrabberPath grabber = (TileEntityGrabberPath) worldIn.getTileEntity(pos);
    		try {
				grabber.addEntityFilter(Class.forName(playerIn.getHeldItem(hand).getTagCompound().getString("filter")));
				if (!worldIn.isRemote) {
					playerIn.addChatMessage(new TextComponentString("Now filtering: " + Class.forName(playerIn.getHeldItem(hand).getTagCompound().getString("filter")).getSimpleName()));
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
    		return EnumActionResult.PASS;
    	} else if (worldIn.getBlockState(pos).getBlock() instanceof BlockMovingDetectorPath) {
    		TileEntityDetectorPath grabber = (TileEntityDetectorPath) worldIn.getTileEntity(pos);
    		try {
				grabber.addEntityFilter(Class.forName(playerIn.getHeldItem(hand).getTagCompound().getString("filter")));
				if (!worldIn.isRemote) {
					playerIn.addChatMessage(new TextComponentString("Now filtering: " + Class.forName(playerIn.getHeldItem(hand).getTagCompound().getString("filter")).getSimpleName()));
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
    		return EnumActionResult.PASS;
    	} else if (worldIn.getBlockState(pos).getBlock() instanceof BlockMovingBackwardsDetectorPath) {
    		TileEntityDetectorBackwardsPath grabber = (TileEntityDetectorBackwardsPath) worldIn.getTileEntity(pos);
    		try {
				grabber.addEntityFilter(Class.forName(playerIn.getHeldItem(hand).getTagCompound().getString("filter")));
				if (!worldIn.isRemote) {
					playerIn.addChatMessage(new TextComponentString("Now filtering: " + Class.forName(playerIn.getHeldItem(hand).getTagCompound().getString("filter")).getSimpleName()));
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
    		return EnumActionResult.PASS;
    	}
    	return EnumActionResult.FAIL;
    }
    
    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn,
    		int itemSlot, boolean isSelected) {
    	initTagCompound(stack);
    }
    
    public void initTagCompound(ItemStack stack) {
    	if (!stack.hasTagCompound()) {
    		NBTTagCompound nbt = new NBTTagCompound();
    		nbt.setString("filter", "net.minecraft.entity.Entity");
    		stack.setTagCompound(nbt);
    	}
    }
}