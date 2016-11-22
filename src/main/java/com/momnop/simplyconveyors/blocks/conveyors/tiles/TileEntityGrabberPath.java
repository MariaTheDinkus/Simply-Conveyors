package com.momnop.simplyconveyors.blocks.conveyors.tiles;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

import com.momnop.simplyconveyors.blocks.conveyors.BlockMovingSlowStairPath;

public class TileEntityGrabberPath extends TileEntity implements ITickable {

	private boolean isPowered = false;

	/**
	 * This is the name of the class the entity should be filtered to.
	 */
	private String entityFilter = "net.minecraft.entity.Entity";

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
						if (Class.forName(entityFilter).isInstance(ent)) {
							ent.setPositionAndUpdate(pos.getX() + 0.5, pos.getY() + ent.height, pos.getZ() + 0.5);
							isPowered = false;
							break;
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
						isPowered = false;
					}
				}
			}
		}
	}

	public void setPowered(boolean powered) {
		isPowered = powered;
	}

	public boolean getPowered() {
		return isPowered;
	}

	public void setEntityFilter(Class filterClass) {
		entityFilter = filterClass.getName();
	}

	public String getEntityFilter() {
		return entityFilter;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setString("entityFilter", entityFilter);
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		entityFilter = compound.getString("entityFilter");
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
}
