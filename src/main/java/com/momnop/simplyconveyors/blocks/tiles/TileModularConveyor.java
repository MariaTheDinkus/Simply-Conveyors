package com.momnop.simplyconveyors.blocks.tiles;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import com.momnop.simplyconveyors.api.IPassive;
import com.momnop.simplyconveyors.api.ItemModule;
import com.momnop.simplyconveyors.api.ItemTrack;
import com.momnop.simplyconveyors.blocks.base.BlockConveyor;
import com.momnop.simplyconveyors.blocks.base.BlockPoweredConveyor;
import com.momnop.simplyconveyors.blocks.modular.BlockFlatModularConveyor;

public class TileModularConveyor extends TileEntity implements ITickable, IInventory
{

	public NonNullList<ItemStack> inventory;

	public TileModularConveyor()
	{
		this.inventory = NonNullList.<ItemStack> withSize(this.getSizeInventory(), ItemStack.EMPTY);
	}

	@Override
	public void update()
	{
		int x = this.getPos().getX();
		int y = this.getPos().getY();
		int z = this.getPos().getZ();
		List<Entity> entities = this.getWorld().getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1));
		
		markDirty();
		
		// Modules
		for(int i = 0; i < 3; i++)
		{
			if(getStackInSlot(i) != ItemStack.EMPTY && getStackInSlot(i).getItem() instanceof ItemModule)
			{
				ItemModule upgrade = (ItemModule) getStackInSlot(i).getItem();
				if(this.getWorld().getBlockState(this.getPos()).getBlock() == this.blockType)
				{
					IBlockState state = this.getWorld().getBlockState(this.getPos());

					EnumFacing conveyorType = null;
					if(this.blockType instanceof BlockFlatModularConveyor)
					{
						conveyorType = EnumFacing.DOWN;
					}
					
					if (upgrade instanceof IPassive) {
						((IPassive)upgrade).passiveUpdate(this, state.getValue(BlockPoweredConveyor.POWERED), state.getValue(BlockConveyor.FACING), conveyorType);
						for(Entity entity : entities)
						{
							upgrade.update(this, state.getValue(BlockPoweredConveyor.POWERED), state.getValue(BlockConveyor.FACING), conveyorType, entity);
						}
					} else {
						for(Entity entity : entities)
						{
							upgrade.update(this, state.getValue(BlockPoweredConveyor.POWERED), state.getValue(BlockConveyor.FACING), conveyorType, entity);
						}
					}
				}
			}
		}
		
		// Track
		if(getStackInSlot(3) != ItemStack.EMPTY && getStackInSlot(3).getItem() instanceof ItemTrack)
		{
			ItemTrack upgrade = (ItemTrack) getStackInSlot(3).getItem();
			if(this.getWorld().getBlockState(this.getPos()).getBlock() == this.blockType)
			{
				IBlockState state = this.getWorld().getBlockState(this.getPos());

				EnumFacing conveyorType = null;
				if(this.blockType instanceof BlockFlatModularConveyor)
				{
					conveyorType = EnumFacing.DOWN;
				}

				for(Entity entity : entities)
				{
					upgrade.update(this, state.getValue(BlockPoweredConveyor.POWERED), state.getValue(BlockConveyor.FACING), conveyorType, entity);
				}
			}
		}
	}

	@Override
	public String getName()
	{
		return "Modular Conveyor";
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public int getSizeInventory()
	{
		return 4;
	}

	@Override
	public boolean isEmpty()
	{
		return false;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return this.inventory.get(index);
	}

	@Override
	public ItemStack decrStackSize(int slot, int count)
	{
		if(this.getStackInSlot(slot) != ItemStack.EMPTY)
		{
			ItemStack itemstack;

			if(this.getStackInSlot(slot).getCount() <= count)
			{
				itemstack = this.getStackInSlot(slot);
				this.setInventorySlotContents(slot, ItemStack.EMPTY);
				this.markDirty();
				return itemstack;
			}
			else
			{
				itemstack = this.getStackInSlot(slot).splitStack(count);

				if(this.getStackInSlot(slot).getCount() <= 0)
				{
					this.setInventorySlotContents(slot, ItemStack.EMPTY);
				}
				else
				{
					this.setInventorySlotContents(slot, this.getStackInSlot(slot));
				}

				this.markDirty();
				return itemstack;
			}
		}
		else
		{
			return ItemStack.EMPTY;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		ItemStack stack = this.getStackInSlot(index);
		this.setInventorySlotContents(index, ItemStack.EMPTY);
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		if(index < 0 || index >= this.getSizeInventory())
		{
			return;
		}

		if(stack != ItemStack.EMPTY && stack.getCount() > this.getInventoryStackLimit())
		{
			stack.setCount(this.getInventoryStackLimit());
		}

		if(stack != ItemStack.EMPTY && stack.getCount() == 0)
		{
			stack = ItemStack.EMPTY;
		}

		this.inventory.set(index, stack);
		this.markDirty();
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return !this.isInvalid() && this.getDistanceSq(player.posX, player.posY, player.posZ) < 64;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{

	}

	@Override
	public void closeInventory(EntityPlayer player)
	{

	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return true;
	}

	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public void setField(int id, int value)
	{

	}

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public void clear()
	{
		for(int i = 0; i < this.getSizeInventory(); ++i)
		{
			this.setInventorySlotContents(i, ItemStack.EMPTY);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		this.inventory = NonNullList.<ItemStack> withSize(this.getSizeInventory(), ItemStack.EMPTY);

		ItemStackHelper.loadAllItems(compound, this.inventory);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		ItemStackHelper.saveAllItems(compound, this.inventory);

		return compound;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		NBTTagCompound updateTagDescribingTileEntityState = getUpdateTag();
		final int METADATA = 0;
		return new SPacketUpdateTileEntity(this.pos, METADATA, updateTagDescribingTileEntityState);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		NBTTagCompound updateTagDescribingTileEntityState = pkt.getNbtCompound();
		handleUpdateTag(updateTagDescribingTileEntityState);
	}

	@Override
	public NBTTagCompound getUpdateTag()
	{
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		return nbtTagCompound;
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag)
	{
		this.readFromNBT(tag);
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return oldState.getBlock() != newState.getBlock();
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return true;

		return super.hasCapability(capability, facing);
	}

	IItemHandler insertionHandler = new ConveyorInventoryHandler(this);

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T) insertionHandler;
		return super.getCapability(capability, facing);
	}

	public static class ConveyorInventoryHandler implements IItemHandlerModifiable
	{
		TileModularConveyor conveyor;

		public ConveyorInventoryHandler(TileModularConveyor conveyor)
		{
			this.conveyor = conveyor;
		}

		@Override
		public int getSlots()
		{
			return 1;
		}

		@Override
		public int getSlotLimit(int slot)
		{
			return 64;
		}

		@Override
		public ItemStack getStackInSlot(int slot)
		{
			List<EntityItem> list = conveyor.getWorld().getEntitiesWithinAABB(
					EntityItem.class,
					new AxisAlignedBB(conveyor.getPos().getX(), conveyor.getPos().getY(), conveyor.getPos().getZ(), conveyor.getPos().getX() + 1, conveyor.getPos().getY() + 1, conveyor.getPos()
							.getZ() + 1));
			if(!list.isEmpty())
			{
				for(EntityItem item : list)
				{
					if(item.getEntityItem() != ItemStack.EMPTY)
					{
						return item.getEntityItem();
					}
				}
			}
			return ItemStack.EMPTY;
		}

		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
		{
			if(!simulate)
			{
				EntityItem entity = new EntityItem(conveyor.getWorld(), conveyor.getPos().getX() + .5, conveyor.getPos().getY() + .1875, conveyor.getPos().getZ() + .5, stack.copy());
				entity.motionX = 0;
				entity.motionY = 0;
				entity.motionZ = 0;
				conveyor.getWorld().spawnEntity(entity);
			}
			return ItemStack.EMPTY;
		}

		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate)
		{
			List<EntityItem> list = conveyor.getWorld().getEntitiesWithinAABB(
					EntityItem.class,
					new AxisAlignedBB(conveyor.getPos().getX(), conveyor.getPos().getY(), conveyor.getPos().getZ(), conveyor.getPos().getX() + 1, conveyor.getPos().getY() + 1, conveyor.getPos()
							.getZ() + 1));
			if(!list.isEmpty() && !conveyor.getWorld().isRemote)
			{
				for(EntityItem item : list)
				{
					if(item.getEntityItem() != ItemStack.EMPTY)
					{
						ItemStack stack = item.getEntityItem().copy();
						stack.setCount(amount);
						if (!simulate && item.getEntityItem().getCount() != 1) {
							item.getEntityItem().setCount(item.getEntityItem().getCount() - amount);
						} else if (!simulate) {
							item.setDead();
						}
						return stack;
					}
				}
			}
			return ItemStack.EMPTY;
		}

		@Override
		public void setStackInSlot(int slot, ItemStack stack)
		{
		}
	}

}
