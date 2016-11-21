package com.momnop.simplyconveyors.entity;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBlock extends Entity implements IEntityAdditionalSpawnData {
	private IBlockState fallTile;
	public int fallTime;
	public boolean shouldDropItem = true;
	private boolean field_145808_f;
	private boolean hurtEntities;
	private int fallHurtMax = 40;
	private float fallHurtAmount = 2.0F;
	public NBTTagCompound tileEntityData;
	private static final String __OBFID = "CL_00001668";

	public EntityBlock(World worldIn) {
		super(worldIn);
	}

	public EntityBlock(World worldIn, double x, double y, double z, IBlockState fallingBlockState, NBTTagCompound tileEntityData) {
		super(worldIn);
		this.fallTile = fallingBlockState;
		this.preventEntitySpawning = true;
		this.setSize(0.98F, 0.98F);
		this.setPositionAndRotation(x, y, z, 0, 0);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
		this.tileEntityData = tileEntityData;
	}
	
	

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	public void onUpdate() {
		if (this.fallTile.getMaterial() == Material.AIR) {
			this.setDead();
		} else {
			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;

			if (!this.func_189652_ae()) {
				this.motionY -= 0.03999999910593033D;
			}

			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.55D;
			this.motionY *= 0.9800000190734863D;
			this.motionZ *= 0.55D;

			if (!this.worldObj.isRemote) {
				if (this.onGround && !this.worldObj.isAirBlock(this.getPosition().down()) && this.motionY == 0 && this.posY % 1 == 0) {
					this.worldObj.setBlockState(this.getPosition(), fallTile);
					if (this.worldObj.getTileEntity(this.getPosition()) != null) {
						tileEntityData.setDouble("x", this.posX);
						tileEntityData.setDouble("y", this.posY);
						tileEntityData.setDouble("z", this.posZ);
						TileEntity tile = TileEntity.func_190200_a(this.worldObj, tileEntityData);
						if (tile != null) {
							worldObj.getChunkFromBlockCoords(this.getPosition()).addTileEntity(tile);
							tile.markDirty();
							worldObj.notifyBlockUpdate(this.getPosition(), fallTile, fallTile, 3);
						}
					}
					this.setDead();
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public World getWorldObj() {
		return this.worldObj;
	}

	public IBlockState getBlock() {
		return this.fallTile;
	}

	@Override
	protected void entityInit() {

	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("tileEntityData", tileEntityData);
		
		compound.setInteger("blockState", Block.getStateId(fallTile));
		
		compound.setDouble("motionY", this.motionY);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		tileEntityData = compound.getCompoundTag("tileEntityData");
		
		fallTile = Block.getStateById(compound.getInteger("blockState"));
		
		motionY = compound.getDouble("motionY");
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		ByteBufUtils.writeTag(buffer, tileEntityData);
		
		buffer.writeInt(Block.getStateId(fallTile));

		buffer.writeDouble(this.motionY);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		this.tileEntityData = ByteBufUtils.readTag(additionalData);
		
		this.fallTile = Block.getStateById(additionalData.readInt());

		this.motionY = additionalData.readDouble();
	}
	
	@Override
	public boolean hitByEntity(Entity entityIn) {
		ItemStack item = new ItemStack(fallTile.getBlock(), 1, fallTile.getBlock().getMetaFromState(fallTile));
		if (entityIn instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityIn;
			player.inventory.addItemStackToInventory(item);
			this.setDead();
		}
		return false;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {}
}