package com.momnop.simplyconveyors.blocks.conveyors.tiles;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.blocks.conveyors.special.BlockMovingBackwardsDetectorPath;
import com.momnop.simplyconveyors.blocks.conveyors.special.BlockMovingBackwardsDetectorPath;
import com.momnop.simplyconveyors.blocks.conveyors.special.BlockMovingDetectorPath;

public class TileEntityDetectorBackwardsPath extends TileEntity implements ITickable {
	
	/**
	 * This is the name of the class the entity should be filtered to.
	 */
	private ArrayList<String> entityFilter = new ArrayList<String>();
	private int size = 0;
	private boolean isBlacklisted = false;

	@Override
	public void update() {
		
		List entities = this.worldObj.getEntitiesWithinAABB(Entity.class,
				new AxisAlignedBB(pos.getX() - 0, pos.getY() - 1, pos.getZ() - 0,
						pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1));
		
		for (int i = 0; i < entities.size(); i++) {
			Object obj = entities.get(i);
			if (obj instanceof Entity) {
				Entity ent = (Entity) obj;
				try {
					if (isBlacklisted == false) {
						if (!entityFilter.isEmpty()) {
							for (String string : entityFilter) {
								if (Class.forName(string).isInstance(ent)) {
									this.getWorld().setBlockState(this.getPos(), this.getWorld().getBlockState(this.getPos()).withProperty(((BlockMovingBackwardsDetectorPath) blockType).FACING, this.getWorld().getBlockState(this.getPos()).getValue(((BlockMovingBackwardsDetectorPath) blockType).FACING)).withProperty(((BlockMovingBackwardsDetectorPath) blockType).POWERED, true));
									return;
								}
							}
						} 

						if (entityFilter.isEmpty()) {
							this.getWorld().setBlockState(this.getPos(), this.getWorld().getBlockState(this.getPos()).withProperty(((BlockMovingBackwardsDetectorPath) blockType).FACING, this.getWorld().getBlockState(this.getPos()).getValue(((BlockMovingBackwardsDetectorPath) blockType).FACING)).withProperty(((BlockMovingBackwardsDetectorPath) blockType).POWERED, true));
							return;
						}
					} else {
						boolean isABlacklistedEntity = false;
						if (!entityFilter.isEmpty()) {
							for (String string : entityFilter) {
								if (Class.forName(string).isInstance(ent)) {
									isABlacklistedEntity = true;
								}
							}
						}
					
						if (isABlacklistedEntity == false) {
							this.getWorld().setBlockState(this.getPos(), this.getWorld().getBlockState(this.getPos()).withProperty(((BlockMovingBackwardsDetectorPath) blockType).FACING, this.getWorld().getBlockState(this.getPos()).getValue(((BlockMovingBackwardsDetectorPath) blockType).FACING)).withProperty(((BlockMovingBackwardsDetectorPath) blockType).POWERED, true));
							return;
						}
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		this.getWorld().setBlockState(this.getPos(), this.getWorld().getBlockState(this.getPos()).withProperty(((BlockMovingBackwardsDetectorPath) blockType).FACING, this.getWorld().getBlockState(this.getPos()).getValue(((BlockMovingBackwardsDetectorPath) blockType).FACING)).withProperty(((BlockMovingBackwardsDetectorPath) blockType).POWERED, false));
		
		markDirty();
	}
	
	public void setBlacklisted(boolean blacklisted) {
		isBlacklisted = blacklisted;
	}
	
	public boolean getBlacklisted() {
		return isBlacklisted;
	}

	public void addEntityFilter(Class filterClass) {
		entityFilter.add(filterClass.getName());
	}
	
	public void setEntityFilter(int index, Class filterClass) {
		entityFilter.set(index, filterClass.getName());
	}

	public String getEntityFilter(int index) {
		return entityFilter.get(index);
	}
	
	public ArrayList<String> getFilterList() {
		return entityFilter;
	}
	
	public void setFilterList(ArrayList<String> filterList) {
		entityFilter = filterList;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		int i = 0;
		compound.setInteger("size", entityFilter.size());
		for (String filter : entityFilter) {
			compound.setString("entityFilter" + i, filter);
			i++;
		}
		compound.setBoolean("isBlacklisted", isBlacklisted);
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		size = compound.getInteger("size");
		for (int i = 0; i < size; i++) {
			entityFilter.add(compound.getString("entityFilter" + i));
		}
		isBlacklisted = compound.getBoolean("isBlacklisted");
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 1, this.getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos,
			IBlockState oldState, IBlockState newSate) {
		return oldState.getBlock() != newSate.getBlock();
	}
}
