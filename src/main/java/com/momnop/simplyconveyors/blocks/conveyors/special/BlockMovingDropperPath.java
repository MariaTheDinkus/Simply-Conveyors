package com.momnop.simplyconveyors.blocks.conveyors.special;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.SimplyConveyorsSpecialCreativeTab;
import com.momnop.simplyconveyors.blocks.BlockPoweredConveyor;
import com.momnop.simplyconveyors.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.helpers.ConveyorHelper;
import com.momnop.simplyconveyors.items.ItemConveyorResistanceBoots;

public class BlockMovingDropperPath extends BlockPoweredConveyor {
	
	private final double speed;
	private EnumFacing facing = EnumFacing.NORTH;

	public BlockMovingDropperPath(double speed, Material material, String unlocalizedName) {
		super(material);
		setCreativeTab(SimplyConveyorsSpecialCreativeTab.INSTANCE);
		setHardness(1.5F);
		setRegistryName(unlocalizedName);
		setUnlocalizedName(this.getRegistryName().toString()
				.replace("simplyconveyors:", ""));
		useNeighborBrightness = true;
		setHarvestLevel("pickaxe", 0);

		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, false));
		this.speed = speed;
	}
	
	public double getSpeed() {
		return speed;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source,
			BlockPos pos) {
		return SimplyConveyorsBlocks.CONVEYOR_AABB;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState blockState) {
		return false;
	}
	
	public static EnumFacing getFacingFromEntity(BlockPos clickedBlock,
			EntityLivingBase entity) {
		return EnumFacing.getFacingFromVector(
				(float) (entity.posX - clickedBlock.getX()),
				(float) (entity.posY - clickedBlock.getY()),
				(float) (entity.posZ - clickedBlock.getZ()));
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos,
			IBlockState blockState, Entity entity) {
		final EnumFacing direction = blockState.getValue(FACING).getOpposite();
		
		if (entity instanceof EntityPlayer) {
   			EntityPlayer player = (EntityPlayer) entity;
   			if (player.inventory.player.inventory.armorInventory.get(EntityEquipmentSlot.FEET.getIndex()) != ItemStack.EMPTY && player.inventory.armorInventory.get(EntityEquipmentSlot.FEET.getIndex()).getItem() instanceof ItemConveyorResistanceBoots) {
   				return;
   			}
   		}
		
		if (!entity.isSneaking() && !blockState.getValue(POWERED)) {
			ConveyorHelper.centerBasedOnFacing(true, pos, entity, direction);
			
            entity.motionX += this.getSpeed() * direction.getFrontOffsetX();
            ConveyorHelper.lockSpeed(false, this.getSpeed(), entity, direction);
			
			entity.motionZ += this.getSpeed() * direction.getFrontOffsetZ();
			ConveyorHelper.lockSpeed(true, this.getSpeed(), entity, direction);

			if (entity instanceof EntityItem) {
				((EntityItem)entity).setNoDespawn();
				TileEntity inventoryTile;
				EnumFacing inventoryDir = facing;
				
				double distX = Math.abs(pos.offset(facing).getX()+.5-entity.posX);
				double distZ = Math.abs(pos.offset(facing).getZ()+.5-entity.posZ);
				double treshold = .9;
				boolean contact = facing.getAxis()==Axis.Z?distZ<treshold: distX<treshold;
				
				inventoryTile = world.getTileEntity(pos.add(0,-1,0));
				contact = Math.abs(facing.getAxis()==Axis.Z?(pos.getZ()+.5-entity.posZ):(pos.getX()+.5-entity.posX))<.2;
				inventoryDir = EnumFacing.DOWN;
				
				if(!world.isRemote && inventoryTile instanceof IInventory)
				{
					if(contact && inventoryTile!=null)
					{
						ItemStack stack = ((EntityItem)entity).getEntityItem();
						if(stack != ItemStack.EMPTY)
						{
							if (TileEntityFurnace.isItemFuel(stack)) {
								ItemStack ret = ConveyorHelper.putStackInInventoryAllSlots((IInventory) inventoryTile, stack, EnumFacing.DOWN);
								if(ret==ItemStack.EMPTY)
									entity.setDead();
								else if(ret.getCount()<stack.getCount())
									((EntityItem)entity).setEntityItemStack(ret);
							} else if (!TileEntityFurnace.isItemFuel(stack)) {
								ItemStack ret = ConveyorHelper.putStackInInventoryAllSlots((IInventory) inventoryTile, stack, null);
								if(ret==ItemStack.EMPTY)
									entity.setDead();
								else if(ret.getCount()<stack.getCount())
									((EntityItem)entity).setEntityItemStack(ret);
							}
						}
					}
					else if(contact && world.isAirBlock(pos.add(0,-1,0)))
					{
						entity.motionX = 0;
						entity.motionZ = 0;
						entity.setPosition(pos.getX()+.5, pos.getY()-.5, pos.getZ()+.5);
					}
				}
			}
		}
	}
}