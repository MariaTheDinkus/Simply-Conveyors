package com.momnop.simplyconveyors.items.modules;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextFormatting;

import com.momnop.simplyconveyors.api.EnumModule;
import com.momnop.simplyconveyors.api.IPassive;
import com.momnop.simplyconveyors.api.ItemModule;
import com.momnop.simplyconveyors.helpers.ConveyorHelper;

public class ItemTransporterModule extends ItemModule implements IPassive
{

	public ItemTransporterModule(String unlocalizedName, EnumModule enumModule)
	{
		super(unlocalizedName, enumModule);
	}

	@Override
	public boolean isCompatible(ItemModule upgrade)
	{
		incompatibles.clear();
		if(!incompatibles.contains(TextFormatting.BLUE + "Dropper Module"))
		{
			incompatibles.add(TextFormatting.BLUE + "Dropper Module");
		}

		if(upgrade instanceof ItemDropperModule)
		{
			return false;
		}
		return true;
	}

	@Override
	public void update(TileEntity tile, boolean powered, EnumFacing facing, EnumFacing conveyorType, Entity entityIn)
	{
		if(!entityIn.getEntityWorld().isRemote && entityIn instanceof EntityItem && !powered)
		{
			EntityItem item = (EntityItem) entityIn;

			ConveyorHelper.insertToFacing(facing, EnumFacing.NORTH, tile.getPos(), entityIn);
		}
	}

	@Override
	public void passiveUpdate(TileEntity tile, boolean powered, EnumFacing facing, EnumFacing conveyorType)
	{
		if(!tile.getWorld().isRemote && !powered && tile.getWorld().getTotalWorldTime() % 5 == 0)
		{
			TileEntity tile2 = tile.getWorld().getTileEntity(tile.getPos().add(facing.getOpposite().getDirectionVec()));
			if(tile2 != null && tile2 instanceof IInventory)
			{
				IInventory inventory = (IInventory) tile2;
				if(!inventory.isEmpty())
				{
					for(int i = 0; i < inventory.getSizeInventory(); i++)
					{
						if(inventory.getStackInSlot(i) != ItemStack.EMPTY)
						{
							EntityItem entityItem = new EntityItem(tile.getWorld(), tile.getPos().getX() + 0.5F, tile.getPos().getY() + (1F / 16F), tile.getPos().getZ() + 0.5F, new ItemStack(
									inventory.getStackInSlot(i).getItem(), 1, inventory.getStackInSlot(i).getMetadata()));
							if(entityItem.getEntityItem() != ItemStack.EMPTY)
							{
								tile.getWorld().spawnEntity(entityItem);
							}
							entityItem.motionX = 0;
							entityItem.motionY = 0;
							entityItem.motionZ = 0;
							entityItem.setPosition(tile.getPos().getX() + 0.5F, tile.getPos().getY() + (1F / 16F), tile.getPos().getZ() + 0.5F);
							inventory.decrStackSize(i, 1);
							break;
						}
					}
				}
			}
		}
	}

}
