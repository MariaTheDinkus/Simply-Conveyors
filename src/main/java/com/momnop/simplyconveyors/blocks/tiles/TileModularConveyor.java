package com.momnop.simplyconveyors.blocks.tiles;

import java.util.List;

import mcjty.lib.tools.ItemStackList;
import mcjty.lib.tools.ItemStackTools;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import com.momnop.simplyconveyors.api.IPassive;
import com.momnop.simplyconveyors.api.ItemModule;
import com.momnop.simplyconveyors.api.ItemTrack;
import com.momnop.simplyconveyors.blocks.base.BlockConveyor;
import com.momnop.simplyconveyors.blocks.base.BlockPoweredConveyor;
import com.momnop.simplyconveyors.blocks.modular.BlockFlatModularConveyor;
import com.momnop.simplyconveyors.blocks.modular.BlockInverseModularConveyor;

public class TileModularConveyor extends TileEntity implements ITickable, IInventory
{

	public ItemStackList inventory = ItemStackList.create(this.getSizeInventory());

	public TileModularConveyor()
	{
		
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
			if(getStackInSlot(i) != ItemStackTools.getEmptyStack() && getStackInSlot(i).getItem() instanceof ItemModule)
			{
				ItemModule upgrade = (ItemModule) getStackInSlot(i).getItem();
				if(this.getWorld().getBlockState(this.getPos()).getBlock() == this.blockType)
				{
					IBlockState state = this.getWorld().getBlockState(this.getPos());

					EnumFacing conveyorType = null;
					if(this.blockType instanceof BlockFlatModularConveyor)
					{
						conveyorType = EnumFacing.DOWN;
					} else if (this.blockType instanceof BlockInverseModularConveyor) {
						conveyorType = EnumFacing.UP;
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
		if(getStackInSlot(3) != ItemStackTools.getEmptyStack() && getStackInSlot(3).getItem() instanceof ItemTrack)
		{
			ItemTrack upgrade = (ItemTrack) getStackInSlot(3).getItem();
			if(this.getWorld().getBlockState(this.getPos()).getBlock() == this.blockType)
			{
				IBlockState state = this.getWorld().getBlockState(this.getPos());

				EnumFacing conveyorType = null;
				if(this.blockType instanceof BlockFlatModularConveyor)
				{
					conveyorType = EnumFacing.DOWN;
				} else if (this.blockType instanceof BlockInverseModularConveyor) {
					conveyorType = EnumFacing.UP;
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
		if(this.getStackInSlot(slot) != ItemStackTools.getEmptyStack())
		{
			ItemStack itemstack;

			if(ItemStackTools.getStackSize(this.getStackInSlot(slot)) <= count)
			{
				itemstack = this.getStackInSlot(slot);
				this.setInventorySlotContents(slot, ItemStackTools.getEmptyStack());
				this.markDirty();
				return itemstack;
			}
			else
			{
				itemstack = this.getStackInSlot(slot).splitStack(count);

				if(ItemStackTools.getStackSize(this.getStackInSlot(slot)) <= 0)
				{
					this.setInventorySlotContents(slot, ItemStackTools.getEmptyStack());
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
			return ItemStackTools.getEmptyStack();
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		ItemStack stack = this.getStackInSlot(index);
		this.setInventorySlotContents(index, ItemStackTools.getEmptyStack());
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		if(index < 0 || index >= this.getSizeInventory())
		{
			return;
		}

		if(stack != ItemStackTools.getEmptyStack() && ItemStackTools.getStackSize(stack) > this.getInventoryStackLimit())
		{
			ItemStackTools.setStackSize(stack, this.getInventoryStackLimit());
		}

		if(stack != ItemStackTools.getEmptyStack() && ItemStackTools.getStackSize(stack) == 0)
		{
			stack = ItemStackTools.getEmptyStack();
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
			this.setInventorySlotContents(i, ItemStackTools.getEmptyStack());
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		this.inventory = ItemStackList.create(this.getSizeInventory());
		
		NBTTagList items = compound.getTagList("ItemInventory", Constants.NBT.TAG_COMPOUND);

		for (int i = 0; i < items.tagCount(); ++i)
		{
			NBTTagCompound item = (NBTTagCompound) items.getCompoundTagAt(i);
			int slot = item.getInteger("Slot");

			if (slot >= 0 && slot < getSizeInventory()) {
				inventory.set(slot, ItemStackTools.loadFromNBT(item));
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		NBTTagList items = new NBTTagList();

		for (int i = 0; i < getSizeInventory(); ++i)
		{
			if (getStackInSlot(i) != ItemStackTools.getEmptyStack())
			{
				NBTTagCompound item = new NBTTagCompound();
				item.setInteger("Slot", i);
				getStackInSlot(i).writeToNBT(item);

				items.appendTag(item);
			}
		}
		compound.setTag("ItemInventory", items);

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
					if(item.getEntityItem() != ItemStackTools.getEmptyStack())
					{
						return item.getEntityItem();
					}
				}
			}
			return ItemStackTools.getEmptyStack();
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
			return ItemStackTools.getEmptyStack();
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
					if(item.getEntityItem() != ItemStackTools.getEmptyStack())
					{
						ItemStack stack = item.getEntityItem().copy();
						ItemStackTools.setStackSize(stack, amount);
						if (!simulate && ItemStackTools.getStackSize(item.getEntityItem()) != 1) {
							ItemStackTools.setStackSize(item.getEntityItem(), ItemStackTools.getStackSize(item.getEntityItem()) - amount);
						} else if (!simulate) {
							item.setDead();
						}
						return stack;
					}
				}
			}
			return ItemStackTools.getEmptyStack();
		}

		@Override
		public void setStackInSlot(int slot, ItemStack stack)
		{
		}
	}

}
