package com.momnop.simplyconveyors.blocks.conveyors.tiles;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.blocks.conveyors.normal.BlockMovingSlowStairPath;
import com.momnop.simplyconveyors.blocks.conveyors.special.BlockMovingBackwardsDetectorPath;

public class TileEntityGrabberPath extends TileEntity implements ITickable {

	private boolean isPowered = false;
	private boolean isBlacklisted = false;

	/**
	 * This is the name of the class the entity should be filtered to.
	 */
	private ArrayList<String> entityFilter = new ArrayList<String>();
	private int size = 0;

	@Override
	public void update() {
		markDirty();
		
		List entities = this.worldObj.getEntitiesWithinAABB(
				Entity.class,
				new AxisAlignedBB(pos.getX() - 2, pos.getY(),
						pos.getZ() - 2, pos.getX() + 3, pos.getY() + 2F, pos
								.getZ() + 3));

		if (isPowered == true) {
			for (int i = 0; i < entities.size(); i++) {
				Object obj = entities.get(i);
				if (obj instanceof Entity) {
					Entity ent = (Entity) obj;
					isPowered = false;
					try {
						if (isBlacklisted == false) {
							if (!entityFilter.isEmpty()) {
								for (String string : entityFilter) {
									if (Class.forName(string).isInstance(ent)) {
										ent.setPositionAndUpdate(pos.getX() + 0.5, pos.getY() + ent.height, pos.getZ() + 0.5);
										isPowered = false;
										return;
									}
								}	
							} 

							if (entityFilter.isEmpty()) {
								ent.setPositionAndUpdate(pos.getX() + 0.5, pos.getY() + ent.height, pos.getZ() + 0.5);
								isPowered = false;
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
								ent.setPositionAndUpdate(pos.getX() + 0.5, pos.getY() + ent.height, pos.getZ() + 0.5);
								isPowered = false;
								return;
							}
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
						isPowered = false;
					}
				}
			}
		}
	}
	
	public void setBlacklisted(boolean blacklisted) {
		isBlacklisted = blacklisted;
	}
	
	public boolean getBlacklisted() {
		return isBlacklisted;
	}

	public void setPowered(boolean powered) {
		isPowered = powered;
	}

	public boolean getPowered() {
		return isPowered;
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
