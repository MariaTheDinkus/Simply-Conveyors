package com.momnop.simplyconveyors.items;

import java.util.List;

import javax.swing.JComboBox.KeySelectionManager;

import org.lwjgl.input.Keyboard;

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

import com.momnop.simplyconveyors.blocks.conveyors.BlockMovingGrabberPath;
import com.momnop.simplyconveyors.blocks.conveyors.tiles.TileEntityGrabberPath;

public class ItemEntityFilter extends Item
{
	
    public ItemEntityFilter(String unlocalizedName)
    {
        super();
        setRegistryName(unlocalizedName);
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
    	tooltip.add("Works with the Grabber Conveyor.");
    	if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
    		if (stack.hasTagCompound()) {
    			tooltip.add("Right-clicking on a Grabber Conveyor makes it filter the mob of this filter's type.");
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
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn,
    		World worldIn, EntityPlayer playerIn, EnumHand hand) {
    	if (playerIn.isSneaking()) {
    		itemStackIn.getTagCompound().setString("filter", "net.minecraft.entity.Entity");
    		return ActionResult.newResult(EnumActionResult.PASS, itemStackIn);
    	}
		return ActionResult.newResult(EnumActionResult.FAIL, itemStackIn);
    }
    
    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn,
    		World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing,
    		float hitX, float hitY, float hitZ) {
    	if (worldIn.getBlockState(pos).getBlock() instanceof BlockMovingGrabberPath) {
    		TileEntityGrabberPath grabber = (TileEntityGrabberPath) worldIn.getTileEntity(pos);
    		try {
				grabber.setEntityFilter(Class.forName(stack.getTagCompound().getString("filter")));
				if (!worldIn.isRemote) {
					playerIn.addChatMessage(new TextComponentString("Now filtering: " + Class.forName(stack.getTagCompound().getString("filter")).getSimpleName()));
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